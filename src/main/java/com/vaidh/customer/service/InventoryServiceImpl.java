package com.vaidh.customer.service;

import com.vaidh.customer.dto.ItemAddedResponse;
import com.vaidh.customer.dto.ProductDTO;
import com.vaidh.customer.dto.ResponseMessage;
import com.vaidh.customer.exception.ModuleException;
import com.vaidh.customer.model.inventory.Product;
import com.vaidh.customer.repository.ProductRepository;
import com.vaidh.customer.util.InventoryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryServiceImpl implements InventoryService{
    @Autowired
    ProductRepository productRepository;

    @Override
    public ResponseMessage addProduct(ProductDTO productDTO) throws ModuleException {
        if (productDTO != null) {
            try {
                Product product = InventoryUtil.convertToProduct(productDTO);
                productRepository.save(product);
                return new ItemAddedResponse("Success");
            } catch (Exception e) {
                throw new ModuleException("Product Adding Failed");
            }
        }
        return null;
    }
}
