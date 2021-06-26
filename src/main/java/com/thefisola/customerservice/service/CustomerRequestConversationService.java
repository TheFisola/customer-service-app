package com.thefisola.customerservice.service;

import com.thefisola.customerservice.dto.SendChatMessageDto;
import com.thefisola.customerservice.model.CustomerRequestConversation;

public interface CustomerRequestConversationService {
    CustomerRequestConversation sendMessage(SendChatMessageDto chatMessageDto);
}
