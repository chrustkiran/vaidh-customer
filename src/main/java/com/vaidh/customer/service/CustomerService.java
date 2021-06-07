package com.vaidh.customer.service;

import com.vaidh.customer.dto.ItemAddedResponse;
import com.vaidh.customer.model.inventory.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CustomerService {
    List<Product> getAllProducts();

    ItemAddedResponse addItemToCart(Long productId, Integer quantity);

    ItemAddedResponse addPrescriptionToCart(MultipartFile file);
}
