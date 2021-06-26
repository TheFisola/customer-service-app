package com.thefisola.customerservice.dto;

import com.thefisola.customerservice.constant.MessageOwner;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SendChatMessageDto {
    @NotBlank(message = "Please provide a customer request Id")
    private String customerRequestId;
    @NotBlank(message = "Please provide a message")
    private String message;
    @NotBlank(message = "Please provide a message owner")
    private MessageOwner messageOwner;
    @NotBlank(message = "Please provide a message owner Id")
    private String messageOwnerId;
}
