package com.thefisola.customerservice.repository;

import com.thefisola.customerservice.model.CustomerRequestConversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRequestConversationRepository extends JpaRepository<CustomerRequestConversation, String> {

}
