package com.thefisola.customerservice.repository;

import com.thefisola.customerservice.constant.CustomerRequestStatus;
import com.thefisola.customerservice.constant.CustomerRequestType;
import com.thefisola.customerservice.model.CustomerRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRequestRepository extends JpaRepository<CustomerRequest, String> {

    @Query("SELECT cr from CustomerRequest cr WHERE \n" +
            "(:message IS NULL OR cr.message LIKE '%:message%')\n" +
            "AND (:status IS NULL OR cr.status = :status)\n" +
            "AND (:type IS NULL OR cr.type =:type) \n" +
            "ORDER BY cr.createdOn DESC")
    Page<CustomerRequest> filterByMessageAndTypeAndStatus(@Param("message") String message, @Param("status") CustomerRequestStatus status, @Param("type") CustomerRequestType type, Pageable page);

}
