package com.thefisola.customerservice.service.impl;

import com.thefisola.customerservice.dto.LoginDto;
import com.thefisola.customerservice.dto.response.LoginResponse;
import com.thefisola.customerservice.exception.InvalidLoginException;
import com.thefisola.customerservice.repository.AgentRepository;
import com.thefisola.customerservice.repository.UserRepository;
import com.thefisola.customerservice.service.LoginService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;
    private final AgentRepository agentRepository;

    @Autowired
    public LoginServiceImpl(UserRepository userRepository, AgentRepository agentRepository) {
        this.userRepository = userRepository;
        this.agentRepository = agentRepository;
    }

    @Override
    public LoginResponse login(LoginDto loginDto) {
        var loginResponse = new LoginResponse(loginDto.getRole());
        switch (loginDto.getRole()) {
            case USER:
                var user = userRepository.findByEmail(loginDto.getEmail()).orElseThrow(InvalidLoginException::new);
                BeanUtils.copyProperties(user, loginResponse);
                break;
            case AGENT:
                var agent = agentRepository.findByEmail(loginDto.getEmail()).orElseThrow(InvalidLoginException::new);
                BeanUtils.copyProperties(agent, loginResponse);
                break;
            default:
                throw new UnsupportedOperationException();
        }
        return loginResponse;
    }
}
