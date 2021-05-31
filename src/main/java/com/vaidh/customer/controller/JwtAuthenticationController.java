package com.vaidh.customer.controller;

import com.vaidh.customer.dto.JwtLoginRequest;
import com.vaidh.customer.dto.JwtRegisterRequest;
import com.vaidh.customer.exception.AlreadyUserExistException;
import com.vaidh.customer.service.AuthenticationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

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


}
