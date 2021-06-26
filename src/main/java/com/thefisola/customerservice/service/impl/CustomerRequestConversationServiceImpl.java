package com.thefisola.customerservice.service.impl;

import com.thefisola.customerservice.constant.MessageOwner;
import com.thefisola.customerservice.dto.SendChatMessageDto;
import com.thefisola.customerservice.exception.NotFoundException;
import com.thefisola.customerservice.model.CustomerRequestConversation;
import com.thefisola.customerservice.repository.AgentRepository;
import com.thefisola.customerservice.repository.CustomerRequestConversationRepository;
import com.thefisola.customerservice.repository.CustomerRequestRepository;
import com.thefisola.customerservice.repository.UserRepository;
import com.thefisola.customerservice.service.CustomerRequestConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CustomerRequestConversationServiceImpl implements CustomerRequestConversationService {

    private final CustomerRequestRepository customerRequestRepository;
    private final UserRepository userRepository;
    private final AgentRepository agentRepository;
    private final CustomerRequestConversationRepository customerRequestConversationRepository;

    @Autowired
    public CustomerRequestConversationServiceImpl(CustomerRequestRepository customerRequestRepository,
                                                  UserRepository userRepository,
                                                  AgentRepository agentRepository,
                                                  CustomerRequestConversationRepository customerRequestConversationRepository) {
        this.customerRequestRepository = customerRequestRepository;
        this.userRepository = userRepository;
        this.agentRepository = agentRepository;

        this.customerRequestConversationRepository = customerRequestConversationRepository;
    }

    @Override
    public List<CustomerRequestConversation> getCustomerRequestConversations(String customerRequestId) {
        var customerRequest = customerRequestRepository.findById(customerRequestId).orElseThrow(NotFoundException::new);
        return customerRequestConversationRepository.findByByCustomerRequest(customerRequest);
    }

    @Override
    @Transactional
    public CustomerRequestConversation sendMessage(SendChatMessageDto chatMessageDto) {
        var customerRequestConversation = new CustomerRequestConversation().fromDto(chatMessageDto);
        validateMessageOwner(chatMessageDto.getMessageOwnerId(), chatMessageDto.getMessageOwner());
        var customerRequest = customerRequestRepository.findById(chatMessageDto.getCustomerRequestId()).orElseThrow(NotFoundException::new);
        customerRequestConversation.setCustomerRequest(customerRequest);
        return customerRequestConversationRepository.save(customerRequestConversation);
    }

    private void validateMessageOwner(String messageOwnerId, MessageOwner messageOwner) {
        switch (messageOwner) {
            case CUSTOMER:
                userRepository.findById(messageOwnerId).orElseThrow(NotFoundException::new);
                break;
            case AGENT:
                agentRepository.findById(messageOwnerId).orElseThrow(NotFoundException::new);
                break;
            default:
                throw new UnsupportedOperationException();
        }

    }
}
