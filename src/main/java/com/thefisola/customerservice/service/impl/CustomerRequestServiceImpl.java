package com.thefisola.customerservice.service.impl;

import com.thefisola.customerservice.constant.CustomerRequestStatus;
import com.thefisola.customerservice.constant.MessageOwner;
import com.thefisola.customerservice.dto.AttendToCustomerRequestDto;
import com.thefisola.customerservice.dto.CustomerRequestDto;
import com.thefisola.customerservice.dto.query.CustomerRequestFilterOptions;
import com.thefisola.customerservice.exception.NotFoundException;
import com.thefisola.customerservice.model.AgentCustomerRequest;
import com.thefisola.customerservice.model.CustomerRequest;
import com.thefisola.customerservice.model.CustomerRequestConversation;
import com.thefisola.customerservice.repository.*;
import com.thefisola.customerservice.service.CustomerRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
public class CustomerRequestServiceImpl implements CustomerRequestService {

    private final AgentRepository agentRepository;
    private final UserRepository userRepository;
    private final CustomerRequestRepository customerRequestRepository;
    private final AgentCustomerRequestRepository agentCustomerRequestRepository;
    private final CustomerRequestConversationRepository customerRequestConversationRepository;

    @Autowired
    public CustomerRequestServiceImpl(AgentRepository agentRepository, UserRepository userRepository, CustomerRequestRepository customerRequestRepository, AgentCustomerRequestRepository agentCustomerRequestRepository, CustomerRequestConversationRepository customerRequestConversationRepository) {
        this.agentRepository = agentRepository;
        this.userRepository = userRepository;
        this.customerRequestRepository = customerRequestRepository;
        this.agentCustomerRequestRepository = agentCustomerRequestRepository;
        this.customerRequestConversationRepository = customerRequestConversationRepository;
    }

    @Override
    public Page<CustomerRequest> getCustomerRequests(CustomerRequestFilterOptions filterOptions) {
        Pageable pageable = PageRequest.of(filterOptions.getPageNumber() > 0 ?
                filterOptions.getPageNumber() - 1 : filterOptions.getPageNumber(), filterOptions.getPageSize());
        return customerRequestRepository.filterByMessageAndTypeAndStatus(filterOptions.getMessage(),
                filterOptions.getCustomerRequestStatus(),
                filterOptions.getCustomerRequestType(),
                pageable);
    }

    @Override
    public CustomerRequest getCustomerRequest(String customerRequestId) {
        return customerRequestRepository.findById(customerRequestId)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    @Transactional
    public CustomerRequest createCustomerRequest(CustomerRequestDto customerRequestDto) {
        var customerRequest = new CustomerRequest().fromDto(customerRequestDto);
        var user = userRepository.findById(customerRequestDto.getUserId()).orElseThrow(NotFoundException::new);
        customerRequest.setUser(user);
        return customerRequestRepository.save(customerRequest);
    }

    @Override
    @Transactional
    public CustomerRequest attendToCustomerRequest(AttendToCustomerRequestDto attendToCustomerRequestDto) {
        var customerRequest = getCustomerRequest(attendToCustomerRequestDto.getCustomerRequestId());
        var agent = agentRepository.findById(attendToCustomerRequestDto.getAgentId())
                .orElseThrow(NotFoundException::new);
        initiateCustomerRequestConversation(customerRequest);
        customerRequest.setStatus(CustomerRequestStatus.ATTENDING_TO_REQUEST);
        agentCustomerRequestRepository.save(new AgentCustomerRequest(agent, customerRequest));
        return customerRequestRepository.save(customerRequest);
    }

    @Override
    @Transactional
    public CustomerRequest markRequestAsFinalised(String customerRequestId) {
        var customerRequest = customerRequestRepository.findById(customerRequestId)
                .orElseThrow(NotFoundException::new);
        customerRequest.setStatus(CustomerRequestStatus.FINALISED_REQUEST);
        customerRequest.setFinalisedOn(new Date());
        return customerRequestRepository.save(customerRequest);
    }

    private void initiateCustomerRequestConversation(CustomerRequest customerRequest) {
        var customerRequestConversation = CustomerRequestConversation.builder()
                .customerRequest(customerRequest)
                .message(customerRequest.getMessage())
                .messageOwner(MessageOwner.CUSTOMER)
                .build();
        customerRequestConversationRepository.save(customerRequestConversation);
    }
}
