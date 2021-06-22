package com.thefisola.customerservice.service.impl;

import com.thefisola.customerservice.dto.query.CustomerRequestFilterOptions;
import com.thefisola.customerservice.model.CustomerRequest;
import com.thefisola.customerservice.repository.CustomerRequestRepository;
import com.thefisola.customerservice.service.CustomerRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CustomerRequestServiceImpl implements CustomerRequestService {

    private final CustomerRequestRepository customerRequestRepository;

    @Autowired
    public CustomerRequestServiceImpl(CustomerRequestRepository customerRequestRepository) {
        this.customerRequestRepository = customerRequestRepository;
    }

    @Override
    public Page<CustomerRequest> getCustomerRequests(CustomerRequestFilterOptions filterOptions) {
        Pageable pageable = PageRequest.of(filterOptions.getPageNumber() > 0 ?
                filterOptions.getPageNumber() - 1 : filterOptions.getPageNumber(), filterOptions.getPageSize());
        return customerRequestRepository.filterByMessageAndTypeAndStatus(filterOptions.getMessage(),
                filterOptions.getCustomerRequestStatus(),
                filterOptions.getCustomerRequestType(),
                pageable);
    }
}
