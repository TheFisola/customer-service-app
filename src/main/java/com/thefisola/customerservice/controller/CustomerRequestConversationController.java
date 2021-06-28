package com.thefisola.customerservice.controller;

import com.thefisola.customerservice.dto.SendChatMessageDto;
import com.thefisola.customerservice.dto.response.BaseResponse;
import com.thefisola.customerservice.model.CustomerRequestConversation;
import com.thefisola.customerservice.service.CustomerRequestConversationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/customer-request-conversations")
public class CustomerRequestConversationController {

    private final CustomerRequestConversationService customerRequestConversationService;

    public CustomerRequestConversationController(CustomerRequestConversationService customerRequestConversationService) {
        this.customerRequestConversationService = customerRequestConversationService;
    }

    @GetMapping
    public BaseResponse<List<CustomerRequestConversation>> getCustomerRequestConversation(@RequestParam String customerRequestId) {
        return new BaseResponse<>(customerRequestConversationService.getCustomerRequestConversations(customerRequestId), HttpStatus.OK);
    }

    @PostMapping
    public BaseResponse<CustomerRequestConversation> sendMessage(@Valid @RequestBody SendChatMessageDto sendChatMessageDto) {
        return new BaseResponse<>(customerRequestConversationService.sendMessage(sendChatMessageDto), HttpStatus.CREATED);
    }
}
