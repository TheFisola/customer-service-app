package com.thefisola.customerservice.controller;

import com.thefisola.customerservice.dto.SendChatMessageDto;
import com.thefisola.customerservice.service.CustomerRequestConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final CustomerRequestConversationService customerRequestConversationService;

    @Autowired
    public ChatController(SimpMessagingTemplate messagingTemplate, CustomerRequestConversationService customerRequestConversationService) {
        this.messagingTemplate = messagingTemplate;
        this.customerRequestConversationService = customerRequestConversationService;
    }

    @MessageMapping("/chat/{id}")
    public void sendChat(@DestinationVariable String id, SendChatMessageDto message) throws Exception {
        var customerRequestConversation = customerRequestConversationService.sendMessage(message);
        messagingTemplate.convertAndSend("/topic/messages/" + id, customerRequestConversation);
    }
}
