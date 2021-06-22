package com.thefisola.customerservice.dto.query;

import com.thefisola.customerservice.constant.CustomerRequestStatus;
import com.thefisola.customerservice.constant.CustomerRequestType;
import lombok.Data;

@Data
public class CustomerRequestFilterOptions extends PageFilterOptions {
    private String message;
    private CustomerRequestStatus customerRequestStatus;
    private CustomerRequestType customerRequestType;
}
