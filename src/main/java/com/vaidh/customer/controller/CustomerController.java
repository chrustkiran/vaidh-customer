package com.vaidh.customer.controller;

import com.vaidh.customer.dto.JwtRegisterRequest;
import com.vaidh.customer.service.AuthenticationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private AuthenticationServiceImpl authenticationService;

    @PostMapping(value = "/get-all-products")
    public ResponseEntity<?> getAllProducts(@RequestBody JwtRegisterRequest authenticationRequest) throws Exception {
        return ResponseEntity.ok("WORKING");
    }
}
