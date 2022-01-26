package com.vaidh.customer.service;

import com.vaidh.customer.dto.ProductDTO;
import com.vaidh.customer.dto.request.ChangeOrderStatusRequest;
import com.vaidh.customer.dto.request.ModifyOrderRequest;
import com.vaidh.customer.dto.request.ModifyProductRequest;
import com.vaidh.customer.dto.response.CommonMessageResponse;
import com.vaidh.customer.exception.ModuleException;
import com.vaidh.customer.model.inventory.Product;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface InventoryService {
    boolean addProduct(ProductDTO product) throws ModuleException;
    boolean deActivateProduct(String productId) throws ModuleException;
    CommonMessageResponse modifyOrder(ModifyOrderRequest modifyOrderRequest);
    CommonMessageResponse modifyProduct(ModifyProductRequest modifyProductRequest) throws ModuleException;
    List<Product> getAllProducts();
    boolean addProducts(List<ProductDTO> products) throws ModuleException;
    CommonMessageResponse addItemToCart(Long productId, Integer quantity, String freshCartId) throws ModuleException;
    CommonMessageResponse addItemToCartAndPlaceOrder(Map<Long, Integer> items, String freshCartId) throws ModuleException;
    CommonMessageResponse cancelOrder(String freshCartId, String note) throws ModuleException, ExecutionException, InterruptedException;
    CommonMessageResponse acceptOrder(String referenceId) throws ModuleException, ExecutionException, InterruptedException;
    CommonMessageResponse editProducts(List<ProductDTO> products) throws Exception;
    CommonMessageResponse changeOrderStatus(ChangeOrderStatusRequest orderStatus) throws ExecutionException, InterruptedException;
}
