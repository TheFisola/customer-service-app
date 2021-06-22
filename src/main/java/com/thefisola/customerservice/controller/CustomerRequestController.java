package com.thefisola.customerservice.controller;

import com.thefisola.customerservice.dto.query.CustomerRequestFilterOptions;
import com.thefisola.customerservice.model.CustomerRequest;
import com.thefisola.customerservice.service.CustomerRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/customer-requests")
public class CustomerRequestController {

    private final CustomerRequestService customerRequestService;

    @Autowired
    public CustomerRequestController(CustomerRequestService customerRequestService) {
        this.customerRequestService = customerRequestService;
    }

    @GetMapping
    public ResponseEntity<Page<CustomerRequest>> getCustomerRequests(@Valid CustomerRequestFilterOptions filterOptions) {
        return new ResponseEntity<>(customerRequestService.getCustomerRequests(filterOptions), HttpStatus.OK);
    }
}
