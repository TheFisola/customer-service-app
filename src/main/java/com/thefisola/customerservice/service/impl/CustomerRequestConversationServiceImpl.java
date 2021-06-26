package com.thefisola.customerservice.service.impl;

import com.thefisola.customerservice.constant.MessageOwner;
import com.thefisola.customerservice.dto.SendChatMessageDto;
import com.thefisola.customerservice.exception.NotFoundException;
import com.thefisola.customerservice.model.CustomerRequest;
import com.thefisola.customerservice.model.CustomerRequestConversation;
import com.thefisola.customerservice.repository.AgentRepository;
import com.thefisola.customerservice.repository.CustomerRequestConversationRepository;
import com.thefisola.customerservice.repository.CustomerRequestRepository;
import com.thefisola.customerservice.repository.UserRepository;
import com.thefisola.customerservice.service.CustomerRequestConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
    @Transactional
    public CustomerRequestConversation sendMessage(SendChatMessageDto chatMessageDto) {
        CustomerRequestConversation customerRequestConversation = new CustomerRequestConversation().fromDto(chatMessageDto);
        validateMessageOwner(chatMessageDto.getMessageOwnerId(), chatMessageDto.getMessageOwner());
        CustomerRequest customerRequest = customerRequestRepository.findById(chatMessageDto.getCustomerRequestId()).orElseThrow(NotFoundException::new);
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
