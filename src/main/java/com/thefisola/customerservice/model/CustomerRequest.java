package com.thefisola.customerservice.model;

import com.thefisola.customerservice.constant.CustomerRequestStatus;
import com.thefisola.customerservice.constant.CustomerRequestType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer_requests")
public class CustomerRequest extends BaseModel {

    private String message;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Enumerated(EnumType.STRING)
    private CustomerRequestType type;

    @Enumerated(EnumType.STRING)
    private CustomerRequestStatus status;
}
