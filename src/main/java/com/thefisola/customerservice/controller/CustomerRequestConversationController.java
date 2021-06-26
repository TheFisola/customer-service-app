package com.thefisola.customerservice.controller;

import com.thefisola.customerservice.dto.SendChatMessageDto;
import com.thefisola.customerservice.dto.response.BaseResponse;
import com.thefisola.customerservice.model.CustomerRequestConversation;
import com.thefisola.customerservice.service.CustomerRequestConversationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/customer-request-conversations")
public class CustomerRequestConversationController {

    private final CustomerRequestConversationService customerRequestConversationService;

    public CustomerRequestConversationController(CustomerRequestConversationService customerRequestConversationService) {
        this.customerRequestConversationService = customerRequestConversationService;
    }

    @PostMapping
    public BaseResponse<CustomerRequestConversation> sendMessage(@Valid @RequestBody SendChatMessageDto sendChatMessageDto) {
        return new BaseResponse<>(customerRequestConversationService.sendMessage(sendChatMessageDto), HttpStatus.CREATED);
    }
}
