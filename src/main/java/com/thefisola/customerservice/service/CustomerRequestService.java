package com.thefisola.customerservice.service;

import com.thefisola.customerservice.dto.AttendToCustomerRequestDto;
import com.thefisola.customerservice.dto.CustomerRequestDto;
import com.thefisola.customerservice.dto.query.CustomerRequestFilterOptions;
import com.thefisola.customerservice.model.CustomerRequest;
import org.springframework.data.domain.Page;

public interface CustomerRequestService {
    CustomerRequest getCustomerRequest(String customerRequestId);

    Page<CustomerRequest> getCustomerRequests(CustomerRequestFilterOptions filterOptions);

    CustomerRequest createCustomerRequest(CustomerRequestDto customerRequestDto);

    CustomerRequest attendToCustomerRequest(AttendToCustomerRequestDto attendToCustomerRequestDto);

    CustomerRequest markRequestAsFinalised(String customerRequestId);
}
