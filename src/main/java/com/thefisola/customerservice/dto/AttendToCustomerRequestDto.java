package com.thefisola.customerservice.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AttendToCustomerRequestDto implements Serializable {
    private String agentId;
    private String customerRequestId;
}
