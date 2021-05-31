package com.vaidh.customer.service;

import com.vaidh.customer.dto.JwtLoginRequest;
import com.vaidh.customer.dto.JwtRegisterRequest;
import com.vaidh.customer.dto.JwtResponse;
import com.vaidh.customer.exception.AlreadyUserExistException;
import com.vaidh.customer.exception.ModuleException;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
    JwtResponse createAuthenticationToken(JwtLoginRequest jwtLoginRequest) throws Exception;
    JwtResponse createUser(JwtRegisterRequest authenticationRequest) throws AlreadyUserExistException, ModuleException, Exception;
}
