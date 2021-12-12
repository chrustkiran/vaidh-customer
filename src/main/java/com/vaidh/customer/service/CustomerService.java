package com.vaidh.customer.service;

import com.vaidh.customer.dto.CommonResults;
import com.vaidh.customer.dto.ItemAddedResponse;
import com.vaidh.customer.dto.request.ModifyUserRequest;
import com.vaidh.customer.dto.response.CommonMessageResponse;
import com.vaidh.customer.dto.response.HistoryResponse;
import com.vaidh.customer.exception.ModuleException;
import com.vaidh.customer.model.inventory.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface CustomerService {
    List<Product> getAllProducts();

    CommonMessageResponse addItemToCart(Long productId, Integer quantity) throws ModuleException;

    CommonMessageResponse addItemToCart(Map<Long, Integer> items) throws ModuleException;

    CommonMessageResponse addPrescriptionToCart(MultipartFile file) throws ModuleException;

    CommonMessageResponse placeOrder();

    List<CommonResults> getHistories();

    CommonMessageResponse modifyUserRequest(ModifyUserRequest modifyUserRequest) throws ModuleException;

    List<Product> getProductByName(String name);
}
