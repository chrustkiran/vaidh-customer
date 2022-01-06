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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new
                    CommonResponse(true, new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage())));
        }
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody JwtRegisterRequest authenticationRequest) {
        try {
            return ResponseEntity.ok(authenticationService.createUser(authenticationRequest));
        } catch (AlreadyUserExistException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("This user already exist");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new
                    CommonResponse(true, new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage())));
        }
    }

    @GetMapping("/forget-password")
    public ResponseEntity<CommonResponse> forgetPassword(@RequestParam String username) {
        if (!username.isEmpty()) {
            try {
                return ResponseEntity.ok(new CommonResponse(Arrays.asList(authenticationService.forgetPassword(username))));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new
                        CommonResponse(true, new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage())));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new
                CommonResponse(true, new ErrorResponse(HttpStatus.BAD_REQUEST, "Some fields are missing")));
    }

    @GetMapping("/confirm-forget-password-code")
    public ResponseEntity<CommonResponse> confirmForgetPasswordCode(@RequestParam String username, @RequestParam String code) {
        if (!username.isEmpty() && !code.isEmpty()) {
            try {
                return ResponseEntity.ok(new CommonResponse(Arrays.asList(authenticationService.confirmForgetPasswordCode(username, code))));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new
                        CommonResponse(true, new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage())));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new
                CommonResponse(true, new ErrorResponse(HttpStatus.BAD_REQUEST, "Some fields are missing")));
    }

    @PostMapping("/modify-password")
    public ResponseEntity<CommonResponse> modifyPassword(@RequestBody ModifyPasswordRequest modifyPasswordRequest,
                                                         @RequestParam(required = false) String confirmedCode, @RequestParam(required = false) String username) {
        if (modifyPasswordRequest != null) {
            if (confirmedCode != null && !confirmedCode.isEmpty() && username != null && !username.isEmpty()) {
                modifyPasswordRequest.setUsername(username);
                modifyPasswordRequest.setCode(confirmedCode);
            }
            if (modifyPasswordRequest.isNonEmpty()) {
                try {
                    return ResponseEntity.ok(new CommonResponse(Arrays.asList(authenticationService.modifyPassword(modifyPasswordRequest))));
                } catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new
                            CommonResponse(true, new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage())));
                }
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
}
