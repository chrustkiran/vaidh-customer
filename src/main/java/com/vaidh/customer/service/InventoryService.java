package com.vaidh.customer.service;

import com.vaidh.customer.dto.ProductDTO;
import com.vaidh.customer.dto.ResponseMessage;
import com.vaidh.customer.exception.ModuleException;

public interface InventoryService {
    ResponseMessage addProduct(ProductDTO product) throws ModuleException;
}
