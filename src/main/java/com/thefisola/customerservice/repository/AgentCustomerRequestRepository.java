package com.thefisola.customerservice.repository;

import com.thefisola.customerservice.model.AgentCustomerRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentCustomerRequestRepository extends JpaRepository<AgentCustomerRequest, String> {
}
