package com.thefisola.customerservice.controller;

import com.thefisola.customerservice.dto.SendChatMessageDto;
import com.thefisola.customerservice.model.CustomerRequestConversation;
import com.thefisola.customerservice.service.CustomerRequestConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final CustomerRequestConversationService customerRequestConversationService;

    @Autowired
    public ChatController(CustomerRequestConversationService customerRequestConversationService) {
        this.customerRequestConversationService = customerRequestConversationService;
    }

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public CustomerRequestConversation sendChat(SendChatMessageDto message) throws Exception {
        System.out.println("Message Got Here");
        return customerRequestConversationService.sendMessage(message);
    }
}
