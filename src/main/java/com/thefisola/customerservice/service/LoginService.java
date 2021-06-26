package com.thefisola.customerservice.service;

import com.thefisola.customerservice.dto.LoginDto;
import com.thefisola.customerservice.dto.response.LoginResponse;

public interface LoginService {
    LoginResponse login(LoginDto loginDto);
}
