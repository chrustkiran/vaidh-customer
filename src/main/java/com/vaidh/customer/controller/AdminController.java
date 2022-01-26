package com.vaidh.customer.controller;

import com.vaidh.customer.dto.*;
import com.vaidh.customer.dto.request.*;
import com.vaidh.customer.dto.response.CommonMessageResponse;
import com.vaidh.customer.exception.ModuleException;
import com.vaidh.customer.model.enums.OrderStatus;
import com.vaidh.customer.service.AuthenticationService;
import com.vaidh.customer.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//shop's end point
@RestController
@RequestMapping("/owner")
public class AdminController {


    @Autowired
    InventoryService inventoryService;

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/add-product")
    public ResponseEntity<CommonResponse> addProduct(@RequestBody ProductDTO product) {
        if (product != null) {
            //validate product
            try {
                if (inventoryService.addProduct(product)) {
                    return ResponseEntity.ok(new CommonResponse(Arrays.asList(new CommonMessageResponse("Added"))));
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

    @PostMapping("/add-products")
    public ResponseEntity<CommonResponse> addProduct(@RequestBody List<ProductDTO> products) {
        if (products != null && !products.isEmpty()) {
            //validate product
            try {
                if (inventoryService.addProducts(products)) {
                    return ResponseEntity.ok(new CommonResponse(Arrays.asList(new CommonMessageResponse("Added"))));
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

    @GetMapping("/get-user-details")
    public ResponseEntity<CommonResponse> getUserDetails() {
        try {
            return ResponseEntity.ok(new CommonResponse(Arrays.asList(authenticationService.getUserDetails())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new
                    CommonResponse(true, new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage())));
        }
    }
    @GetMapping("/get-user-details-by-id")
    public ResponseEntity<CommonResponse> getUserDetails(String username) {
        try{
            return ResponseEntity.ok(new CommonResponse(Arrays.asList(authenticationService.getUserDetails(username))));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new
                    CommonResponse(true, new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage())));
        }
    }

    @PostMapping("/add-item-to-cart")
    public ResponseEntity<CommonResponse> addItemToCart(@RequestBody Long productId, @RequestBody int quantity, @RequestBody String freshCartId) {
        try {
            return ResponseEntity.ok(new CommonResponse(Arrays.asList(inventoryService.addItemToCart(productId, quantity,
                    freshCartId))));
        } catch (ModuleException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new CommonResponse(true, new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e)));
        }
    }

    @PostMapping("/add-items-to-cart")
    public ResponseEntity<CommonResponse> addItemsToCart(@RequestBody AddItemsRequest addItemsRequest) {
        try {
            return ResponseEntity.ok(new CommonResponse(Arrays.asList(inventoryService.addItemToCartAndPlaceOrder(addItemsRequest.getItems(), addItemsRequest.getReferenceId()))));
        } catch (ModuleException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new CommonResponse(true, new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e)));
        }
    }

    @PostMapping("/cancel-order")
    public ResponseEntity<CommonResponse> cancelOrder(@RequestBody CancelOrderRequest cancelOrderRequest) throws Exception {
        try  {
            return ResponseEntity.ok(new CommonResponse(Arrays.asList(inventoryService.cancelOrder(cancelOrderRequest.getReferenceId(), cancelOrderRequest.getNote()))));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new CommonResponse(true, new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e)));
        }
    }

    @PostMapping("/accept-order")
    public ResponseEntity<CommonResponse> acceptOrder(@RequestBody String referenceId) throws Exception {
        try  {
            return ResponseEntity.ok(new CommonResponse(Arrays.asList(inventoryService.acceptOrder(referenceId))));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new CommonResponse(true, new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e)));
        }
    }

    @PostMapping("/edit-product")
    public ResponseEntity<CommonResponse> editProducts(@RequestBody List<ProductDTO> products) {
        try  {
            return ResponseEntity.ok(new CommonResponse(Arrays.asList(inventoryService.editProducts(products))));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new CommonResponse(true, new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e)));
        }
    }

    @PostMapping("/change-order-status")
    public ResponseEntity<CommonResponse> changeOrderStatus(@RequestBody ChangeOrderStatusRequest changeOrderStatusRequest) {
        try  {
            return ResponseEntity.ok(new CommonResponse(Arrays.asList(inventoryService.changeOrderStatus(changeOrderStatusRequest))));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new CommonResponse(true, new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e)));
        }
    }
}
