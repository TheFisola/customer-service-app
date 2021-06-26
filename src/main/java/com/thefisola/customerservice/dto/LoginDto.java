package com.thefisola.customerservice.dto;

import com.thefisola.customerservice.constant.LoginRole;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class LoginDto implements Serializable {
    @NotBlank(message = "Please provide an email")
    private String email;
    private LoginRole role;
}
