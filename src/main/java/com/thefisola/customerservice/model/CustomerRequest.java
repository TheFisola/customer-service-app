package com.thefisola.customerservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thefisola.customerservice.constant.CommonConstants;
import com.thefisola.customerservice.constant.CustomerRequestStatus;
import com.thefisola.customerservice.constant.CustomerRequestType;
import com.thefisola.customerservice.dto.CustomerRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.Date;

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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CommonConstants.DATETIME_FORMAT, timezone = CommonConstants.TIME_ZONE)
    private Date finalisedOn;

    public CustomerRequest fromDto(CustomerRequestDto customerRequestDto) {
        BeanUtils.copyProperties(customerRequestDto, this);
        this.status = CustomerRequestStatus.AWAITING_RESPONSE;
        return this;
    }
}
