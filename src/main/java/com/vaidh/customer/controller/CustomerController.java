package com.vaidh.customer.controller;

import com.vaidh.customer.dto.CommonResponse;
import com.vaidh.customer.dto.ErrorResponse;
import com.vaidh.customer.dto.request.ModifyUserRequest;
import com.vaidh.customer.exception.ModuleException;
import com.vaidh.customer.service.CustomerService;
import com.vaidh.customer.service.FireBaseStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

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
        return "working";
    }


   /* @GetMapping(value = "/place-order")
    public ResponseEntity<CommonResponse> placeOrder() throws Exception {
        try  {
            return ResponseEntity.ok(new CommonResponse(Arrays.asList(customerService.placeOrder())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new CommonResponse(true, new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e)));
        }
    }*/

    @GetMapping(value = "/view-history")
    public ResponseEntity<CommonResponse> viewHistory() throws ModuleException {
        try {
            return ResponseEntity.ok(new CommonResponse(customerService.getHistories()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new CommonResponse(true, new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e)));
        }
    }

    @PostMapping(value = "/modify-user")
    public ResponseEntity<CommonResponse> modifyUser(@RequestBody ModifyUserRequest modifyUserRequest) throws ModuleException {
        try {
            if (modifyUserRequest != null) {
                return ResponseEntity.ok(new CommonResponse(Arrays.asList(customerService.modifyUserRequest(modifyUserRequest))));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new CommonResponse(true, new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e)));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new CommonResponse(true, new ErrorResponse(HttpStatus.BAD_REQUEST, new ModuleException("Bad Strings"))));
    }

 /*   @PostMapping(value = "/add-item-to-cart")
    public ResponseEntity<CommonResponse> addItemToCart(@RequestBody Long productId, @RequestBody int quantity) {
        try {
            return ResponseEntity.ok(new CommonResponse(Arrays.asList(customerService.addItemToCart(productId, quantity))));
        } catch (ModuleException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new CommonResponse(true, new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e)));
        }
    }

    @PostMapping(value = "/add-items-to-cart")
    public ResponseEntity<CommonResponse> addItemsToCart(@RequestBody Map<Long, Integer> items) {
        try {
            return ResponseEntity.ok(new CommonResponse(Arrays.asList(customerService.addItemToCart(items))));
        } catch (ModuleException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new CommonResponse(true, new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e)));
        }
    }*/


    @PostMapping(value = "/add-prescription-to-cart")
    public ResponseEntity<CommonResponse> addPrescriptionToCart(@RequestParam("file") MultipartFile file) {
        try {
            return ResponseEntity.ok(new CommonResponse(Arrays.asList(customerService.addPrescriptionToCart(file))));
        } catch (ModuleException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new CommonResponse(true, new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e)));
        }
    }

    @GetMapping(value = "/get-last-added-order")
    public ResponseEntity<CommonResponse> getLastAddedOrder() {
        try {
            return ResponseEntity.ok(new CommonResponse(Arrays.asList(customerService.getLastAddedOrder())));
        } catch (ModuleException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new CommonResponse(true, new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e)));
        }
    }
}
