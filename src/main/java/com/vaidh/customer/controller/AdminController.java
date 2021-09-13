package com.vaidh.customer.controller;

import com.vaidh.customer.dto.*;
import com.vaidh.customer.dto.request.ModifyOrderRequest;
import com.vaidh.customer.dto.request.ModifyProductRequest;
import com.vaidh.customer.exception.ModuleException;
import com.vaidh.customer.service.InventoryService;
import com.vaidh.customer.service.InventoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;

//shop's end point
@RestController
@RequestMapping("/owner")
public class AdminController {


    @Autowired
    InventoryService inventoryService;

    @PostMapping("/add-product")
    public ResponseEntity<CommonResponse> addProduct(@RequestBody ProductDTO product) {
        if (product != null) {
            //validate product
            try {
                if (inventoryService.addProduct(product)) {
                    return ResponseEntity.ok(new CommonResponse());
                }
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CommonResponse(true,
                        new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Adding failed")));
            } catch (ModuleException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CommonResponse(true,
                        new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e)));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CommonResponse(true, new ErrorResponse(HttpStatus.BAD_REQUEST, "Bad Request")));
    }


    @GetMapping("/de-activate-product")
    public ResponseEntity<CommonResponse>  deActivateProduct(@RequestParam String productId) {
        if (productId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new
                    CommonResponse(true, new ErrorResponse(HttpStatus.BAD_REQUEST, "Empty product id")));
        } else {
            try {
                inventoryService.deActivateProduct(productId);
                return ResponseEntity.ok(new CommonResponse());
            } catch (ModuleException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new
                        CommonResponse(true, new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage())));
            }
        }
    }

    @PostMapping("/modify-order")
    public ResponseEntity<CommonResponse> modifyOrder(@RequestBody ModifyOrderRequest modifyOrderRequest) {
        if (modifyOrderRequest != null && !modifyOrderRequest.getReferenceCartId().isEmpty()) {
            try {
                return ResponseEntity.ok(new CommonResponse(Arrays.asList(inventoryService.modifyOrder(modifyOrderRequest))));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new
                        CommonResponse(true, new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage())));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new
                CommonResponse(true, new ErrorResponse(HttpStatus.BAD_REQUEST, "Some fields are missing")));
    }

    @PostMapping("/modify-product")
    public ResponseEntity<CommonResponse> modifyProduct(@RequestBody ModifyProductRequest modifyProductRequest) {
        if (modifyProductRequest != null && modifyProductRequest.getProductId() != null) {
            try {
                return ResponseEntity.ok(new CommonResponse(Arrays.asList(inventoryService.modifyProduct(modifyProductRequest))));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new
                        CommonResponse(true, new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage())));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new
                CommonResponse(true, new ErrorResponse(HttpStatus.BAD_REQUEST, "Some fields are missing")));
    }

    @GetMapping(value = "/get-all-products")
    public ResponseEntity<CommonResponse> getAllProducts() {
        try  {
            return ResponseEntity.ok(new CommonResponse(new ArrayList<>(inventoryService.getAllProducts())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new
                    CommonResponse(true, new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage())));
        }
    }
}
