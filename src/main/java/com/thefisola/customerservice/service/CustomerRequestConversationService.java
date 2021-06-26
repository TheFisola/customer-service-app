package com.thefisola.customerservice.service;

import com.thefisola.customerservice.dto.SendChatMessageDto;
import com.thefisola.customerservice.model.CustomerRequestConversation;

import java.util.List;

public interface CustomerRequestConversationService {

    List<CustomerRequestConversation> getCustomerRequestConversations(String customerRequestId);

    CustomerRequestConversation sendMessage(SendChatMessageDto chatMessageDto);
}
