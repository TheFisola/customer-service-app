package com.thefisola.customerservice.service;

import com.thefisola.customerservice.dto.query.CustomerRequestFilterOptions;
import com.thefisola.customerservice.model.CustomerRequest;
import org.springframework.data.domain.Page;

public interface CustomerRequestService {
    Page<CustomerRequest> getCustomerRequests(CustomerRequestFilterOptions filterOptions);
}
