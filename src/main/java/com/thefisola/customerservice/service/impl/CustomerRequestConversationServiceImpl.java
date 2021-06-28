package com.thefisola.customerservice.service.impl;

import com.thefisola.customerservice.constant.CustomerRequestStatus;
import com.thefisola.customerservice.constant.MessageOwner;
import com.thefisola.customerservice.dto.SendChatMessageDto;
import com.thefisola.customerservice.exception.InvalidMessageOwnerException;
import com.thefisola.customerservice.exception.NotFoundException;
import com.thefisola.customerservice.model.AgentCustomerRequest;
import com.thefisola.customerservice.model.CustomerRequest;
import com.thefisola.customerservice.model.CustomerRequestConversation;
import com.thefisola.customerservice.repository.AgentCustomerRequestRepository;
import com.thefisola.customerservice.repository.CustomerRequestConversationRepository;
import com.thefisola.customerservice.repository.CustomerRequestRepository;
import com.thefisola.customerservice.service.CustomerRequestConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CustomerRequestConversationServiceImpl implements CustomerRequestConversationService {

    private final CustomerRequestRepository customerRequestRepository;
    private final AgentCustomerRequestRepository agentCustomerRequestRepository;
    private final CustomerRequestConversationRepository customerRequestConversationRepository;

    @Autowired
    public CustomerRequestConversationServiceImpl(CustomerRequestRepository customerRequestRepository,
                                                  AgentCustomerRequestRepository agentCustomerRequestRepository, CustomerRequestConversationRepository customerRequestConversationRepository) {
        this.customerRequestRepository = customerRequestRepository;
        this.agentCustomerRequestRepository = agentCustomerRequestRepository;

        this.customerRequestConversationRepository = customerRequestConversationRepository;
    }

    @Override
    public List<CustomerRequestConversation> getCustomerRequestConversations(String customerRequestId) {
        var customerRequest = customerRequestRepository.findById(customerRequestId).orElseThrow(NotFoundException::new);
        return customerRequestConversationRepository.findByCustomerRequestOrderByCreatedOn(customerRequest);
    }

    @Override
    @Transactional
    public CustomerRequestConversation sendMessage(SendChatMessageDto sendChatMessageDto) {
        var customerRequest = validateCustomerRequest(sendChatMessageDto);
        var customerRequestConversation = new CustomerRequestConversation().fromDto(sendChatMessageDto);
        customerRequestConversation.setCustomerRequest(customerRequest);
        return customerRequestConversationRepository.save(customerRequestConversation);
    }

    private CustomerRequest validateCustomerRequest(SendChatMessageDto sendChatMessageDto) {
        var customerRequest = customerRequestRepository.findById(sendChatMessageDto.getCustomerRequestId()).orElseThrow(NotFoundException::new);
        if (customerRequest.getStatus() != CustomerRequestStatus.ATTENDING_TO_REQUEST)
            throw new UnsupportedOperationException();
        var agentCustomerRequest = agentCustomerRequestRepository.findByCustomerRequest(customerRequest).orElseThrow(UnsupportedOperationException::new);
        validateMessageOwner(sendChatMessageDto.getMessageOwnerId(), sendChatMessageDto.getMessageOwner(), agentCustomerRequest);
        return customerRequest;
    }


    private void validateMessageOwner(String messageOwnerId, MessageOwner messageOwner, AgentCustomerRequest agentCustomerRequest) {
        switch (messageOwner) {
            case CUSTOMER:
                if (!agentCustomerRequest.getCustomerRequest().getUser().getId().equals(messageOwnerId))
                    throw new InvalidMessageOwnerException();
                break;
            case AGENT:
                if (!agentCustomerRequest.getAgent().getId().equals(messageOwnerId))
                    throw new InvalidMessageOwnerException();
                break;
            default:
                throw new UnsupportedOperationException();
        }

    }
}
