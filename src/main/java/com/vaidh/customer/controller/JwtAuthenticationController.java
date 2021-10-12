package com.vaidh.customer.controller;

import com.vaidh.customer.dto.CommonResponse;
import com.vaidh.customer.dto.ErrorResponse;
import com.vaidh.customer.dto.request.JwtLoginRequest;
import com.vaidh.customer.dto.request.JwtRegisterRequest;
import com.vaidh.customer.dto.request.ModifyPasswordRequest;
import com.vaidh.customer.exception.AlreadyUserExistException;
import com.vaidh.customer.service.AuthenticationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationServiceImpl authenticationService;

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody JwtLoginRequest authenticationRequest)  {
        try {
            return ResponseEntity.ok(authenticationService.createAuthenticationToken(authenticationRequest));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invalid credintials");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("UNKNOWN");
        }
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody JwtRegisterRequest authenticationRequest) {
        try {
            return ResponseEntity.ok(authenticationService.createUser(authenticationRequest));
        } catch (AlreadyUserExistException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("This user already exist");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("UNKNOWN");
        }
    }

    @GetMapping("/forget-password")
    public ResponseEntity<CommonResponse> forgetPassword(@RequestParam String emailAddress) {
        if (!emailAddress.isEmpty()) {
            try {
                return ResponseEntity.ok(new CommonResponse(Arrays.asList(authenticationService.forgetPassword(emailAddress))));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new
                        CommonResponse(true, new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage())));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new
                CommonResponse(true, new ErrorResponse(HttpStatus.BAD_REQUEST, "Some fields are missing")));
    }

    @GetMapping("/confirm-forget-password-code")
    public ResponseEntity<CommonResponse> confirmForgetPasswordCode(@RequestParam String emailAddress, @RequestParam String code) {
        if (!emailAddress.isEmpty() && !code.isEmpty()) {
            try {
                return ResponseEntity.ok(new CommonResponse(Arrays.asList(authenticationService.confirmForgetPasswordCode(emailAddress, code))));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new
                        CommonResponse(true, new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage())));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new
                CommonResponse(true, new ErrorResponse(HttpStatus.BAD_REQUEST, "Some fields are missing")));
    }

    @PostMapping("/modify-password")
    public ResponseEntity<CommonResponse> modifyPassword(@RequestBody ModifyPasswordRequest modifyPasswordRequest) {
        if (modifyPasswordRequest != null && modifyPasswordRequest.isNonEmpty()) {
            try {
                return ResponseEntity.ok(new CommonResponse(Arrays.asList(authenticationService.modifyPassword(modifyPasswordRequest))));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new
                        CommonResponse(true, new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage())));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new
                CommonResponse(true, new ErrorResponse(HttpStatus.BAD_REQUEST, "Some fields are missing")));
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<String> refreshToken() {
        try {
            return ResponseEntity.ok(this.authenticationService.refreshToken());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ERROR");
        }
    }

    @GetMapping("/get-user-details")
    public ResponseEntity<CommonResponse> getUserDetails() {
        try {
            return ResponseEntity.ok(new CommonResponse(Arrays.asList(authenticationService.getUserDetails())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new
                    CommonResponse(true, new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage())));
        }
    }
}
