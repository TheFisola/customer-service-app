package com.thefisola.customerservice.dto;

import com.thefisola.customerservice.constant.CustomerRequestType;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CustomerRequestDto {
    @NotBlank(message = "Please provide a message")
    private String message;
    @NotBlank(message = "Please provide a user Id")
    private String userId;

    private CustomerRequestType type;
}
