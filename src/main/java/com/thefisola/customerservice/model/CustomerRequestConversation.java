package com.thefisola.customerservice.model;

import com.thefisola.customerservice.constant.MessageOwner;
import com.thefisola.customerservice.dto.SendChatMessageDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer_request_conversations")
public class CustomerRequestConversation extends BaseModel {
    private String message;
    private MessageOwner messageOwner;
    @ManyToOne
    @JoinColumn(name = "customer_request_id", referencedColumnName = "id")
    private CustomerRequest customerRequest;

    public CustomerRequestConversation fromDto(SendChatMessageDto chatMessageDto) {
        BeanUtils.copyProperties(chatMessageDto, this);
        return this;
    }
}
