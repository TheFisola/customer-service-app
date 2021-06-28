package com.thefisola.customerservice.controller;

import com.thefisola.customerservice.dto.LoginDto;
import com.thefisola.customerservice.dto.response.BaseResponse;
import com.thefisola.customerservice.dto.response.LoginResponse;
import com.thefisola.customerservice.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * MOCK LOGIN WITHOUT AUTHENTICATION
 */
@CrossOrigin
@RestController
@RequestMapping("/api/login")
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public BaseResponse<LoginResponse> login(@Valid @RequestBody LoginDto loginDto) {
        return new BaseResponse<>(loginService.login(loginDto), HttpStatus.OK);
    }
}
