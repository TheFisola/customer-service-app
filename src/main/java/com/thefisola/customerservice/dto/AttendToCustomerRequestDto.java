package com.thefisola.customerservice.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class AttendToCustomerRequestDto implements Serializable {
    @NotBlank(message = "Please provide an agent Id")
    private String agentId;
    @NotBlank(message = "Please provide an customer request Id")
    private String customerRequestId;
}
