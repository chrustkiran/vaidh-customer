package com.vaidh.customer.controller;

import com.vaidh.customer.dto.JwtRegisterRequest;
import com.vaidh.customer.model.inventory.Product;
import com.vaidh.customer.service.AuthenticationServiceImpl;
import com.vaidh.customer.service.CustomerService;
import com.vaidh.customer.service.FireBaseStorageService;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Customer's endpoint
@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private FireBaseStorageService fireBaseStorageService;

    @GetMapping("/test")
    public String test() {
        fireBaseStorageService.saveTestDate();
        return "working";
    }

    @GetMapping(value = "/get-all-products")
    public ResponseEntity<List<Product>> getAllProducts() throws Exception {
        try  {
            return ResponseEntity.ok(customerService.getAllProducts());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
