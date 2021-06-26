package com.thefisola.customerservice.dto.response;

import com.thefisola.customerservice.constant.LoginRole;
import com.thefisola.customerservice.dto.LoginDto;
import lombok.Data;

@Data
public class LoginResponse extends LoginDto {
    private String id;
    private String name;

    public LoginResponse(LoginRole loginRole) {
        super.setRole(loginRole);
    }
}
