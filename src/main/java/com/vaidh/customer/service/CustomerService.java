package com.vaidh.customer.service;

import com.vaidh.customer.dto.CommonResults;
import com.vaidh.customer.dto.ItemAddedResponse;
import com.vaidh.customer.dto.request.ModifyUserRequest;
import com.vaidh.customer.dto.response.CommonMessageResponse;
import com.vaidh.customer.exception.ModuleException;
import com.vaidh.customer.model.inventory.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CustomerService {
    List<Product> getAllProducts();

    ItemAddedResponse addItemToCart(Long productId, Integer quantity) throws ModuleException;

    ItemAddedResponse addPrescriptionToCart(MultipartFile file) throws ModuleException;

    CommonMessageResponse placeOrder();

    List<CommonResults> getHistories();

    CommonMessageResponse modifyUserReqyest(ModifyUserRequest modifyUserRequest) throws ModuleException;
}
