package com.vaidh.customer.service;

import com.vaidh.customer.dto.CommonResults;
import com.vaidh.customer.dto.ProductDTO;
import com.vaidh.customer.dto.request.ModifyOrderRequest;
import com.vaidh.customer.dto.request.ModifyProductRequest;
import com.vaidh.customer.dto.response.CommonMessageResponse;
import com.vaidh.customer.exception.ModuleException;

import java.util.List;

public interface InventoryService {
    boolean addProduct(ProductDTO product) throws ModuleException;
    boolean deActivateProduct(String productId) throws ModuleException;
    CommonMessageResponse modifyOrder(ModifyOrderRequest modifyOrderRequest);
    CommonMessageResponse modifyProduct(ModifyProductRequest modifyProductRequest) throws ModuleException;
}
