package com.thefisola.customerservice.controller;

import com.thefisola.customerservice.dto.AttendToCustomerRequestDto;
import com.thefisola.customerservice.dto.CustomerRequestDto;
import com.thefisola.customerservice.dto.query.CustomerRequestFilterOptions;
import com.thefisola.customerservice.dto.response.BaseResponse;
import com.thefisola.customerservice.model.CustomerRequest;
import com.thefisola.customerservice.service.CustomerRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public BaseResponse<Page<CustomerRequest>> getCustomerRequests(@Valid CustomerRequestFilterOptions filterOptions) {
        return new BaseResponse<>(customerRequestService.getCustomerRequests(filterOptions), HttpStatus.OK);
    }

    @PostMapping
    public BaseResponse<CustomerRequest> createCustomerRequests(@Valid @RequestBody CustomerRequestDto customerRequestDto) {
        return new BaseResponse<>(customerRequestService.createCustomerRequest(customerRequestDto), HttpStatus.CREATED);
    }

    @PutMapping("/attend")
    public BaseResponse<CustomerRequest> attendToCustomerRequest(@Valid @RequestBody AttendToCustomerRequestDto attendToCustomerRequestDto) {
        return new BaseResponse<>(customerRequestService.attendToCustomerRequest(attendToCustomerRequestDto), HttpStatus.OK);
    }

    @PutMapping("{id}/finalise")
    public BaseResponse<CustomerRequest> markCustomerRequestAsFinalised(@PathVariable String id) {
        return new BaseResponse<>(customerRequestService.markRequestAsFinalised(id), HttpStatus.OK, "Request Successfully Finalized");
    }

}
