package com.vaidh.customer.service;

import com.vaidh.customer.dto.CommonResults;
import com.vaidh.customer.dto.request.JwtLoginRequest;
import com.vaidh.customer.dto.request.JwtRegisterRequest;
import com.vaidh.customer.dto.JwtResponse;
import com.vaidh.customer.dto.request.ModifyPasswordRequest;
import com.vaidh.customer.dto.response.CommonMessageResponse;
import com.vaidh.customer.dto.response.ConfirmCodeResponse;
import com.vaidh.customer.exception.AlreadyUserExistException;
import com.vaidh.customer.exception.ModuleException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AuthenticationService {
    JwtResponse createAuthenticationToken(JwtLoginRequest jwtLoginRequest) throws Exception;
    JwtResponse createUser(JwtRegisterRequest authenticationRequest) throws AlreadyUserExistException, ModuleException, Exception;
    String getCurrentUserName();
    CommonMessageResponse forgetPassword(String emailAddress);
    ConfirmCodeResponse confirmForgetPasswordCode(String emailAddress, String code) throws ModuleException;
    CommonMessageResponse modifyPassword(ModifyPasswordRequest modifyPasswordRequest) throws ModuleException;
}
