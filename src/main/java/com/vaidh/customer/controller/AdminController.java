package com.vaidh.customer.controller;

import com.vaidh.customer.dto.ItemAddedResponse;
import com.vaidh.customer.dto.ProductDTO;
import com.vaidh.customer.dto.ResponseMessage;
import com.vaidh.customer.exception.ModuleException;
import com.vaidh.customer.service.InventoryService;
import com.vaidh.customer.service.InventoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//shop's end point
@RestController
@RequestMapping("/owner")
public class AdminController {


    @Autowired
    InventoryService inventoryService;

    @PostMapping("/add-product")
    public ResponseEntity<ResponseMessage> addProduct(@RequestBody ProductDTO product) {
        if (product != null) {
            //validate product
            try {
                return ResponseEntity.ok(inventoryService.addProduct(product));
            } catch (ModuleException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ItemAddedResponse("Failed adding"));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ItemAddedResponse("Wrong request"));
    }
}
