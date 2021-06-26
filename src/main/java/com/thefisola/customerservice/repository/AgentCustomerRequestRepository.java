package com.thefisola.customerservice.repository;

import com.thefisola.customerservice.model.AgentCustomerRequest;
import com.thefisola.customerservice.model.CustomerRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AgentCustomerRequestRepository extends JpaRepository<AgentCustomerRequest, String> {
    Optional<AgentCustomerRequest> findByCustomerRequest(CustomerRequest customerRequest);
}
