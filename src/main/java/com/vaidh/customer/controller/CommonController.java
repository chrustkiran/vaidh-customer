package com.vaidh.customer.controller;

import com.vaidh.customer.dto.CommonResponse;
import com.vaidh.customer.dto.ErrorResponse;
import com.vaidh.customer.service.CustomerService;
import com.vaidh.customer.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/common")
public class CommonController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private MailService mailService;

    @GetMapping(value = "/get-all-products")
    public ResponseEntity<CommonResponse> getAllProducts() {
        try  {
            return ResponseEntity.ok(new CommonResponse(new ArrayList<>(customerService.getAllProducts())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new
                    CommonResponse(true, new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage())));
        }
    }

    @GetMapping(value = "/search-products")
    public ResponseEntity<CommonResponse> getProductByName(@RequestParam String name) {
        try  {
            return ResponseEntity.ok(new CommonResponse(new ArrayList<>(customerService.getProductByName(name))));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new
                    CommonResponse(true, new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage())));
        }
    }

    @GetMapping(value = "/send-mail")
    public String sendMail() {
        mailService.sendMail("hi", "christkiran.15@cse.mrt.ac.lk", "sample");
        return "success";
    }
}
