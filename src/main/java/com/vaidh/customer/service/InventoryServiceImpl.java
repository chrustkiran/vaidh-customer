package com.vaidh.customer.service;

import com.vaidh.customer.dto.CommonResults;
import com.vaidh.customer.dto.ProductDTO;
import com.vaidh.customer.dto.request.ModifyOrderRequest;
import com.vaidh.customer.dto.request.ModifyProductRequest;
import com.vaidh.customer.dto.response.CommonMessageResponse;
import com.vaidh.customer.exception.ModuleException;
import com.vaidh.customer.model.enums.ProductStatus;
import com.vaidh.customer.model.inventory.Product;
import com.vaidh.customer.repository.ModifiedCartItemRepository;
import com.vaidh.customer.repository.ProductRepository;
import com.vaidh.customer.util.InventoryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.vaidh.customer.constants.ResponseMessage.SUCCESSFULLY_MODIFIED;
import static com.vaidh.customer.constants.ResponseMessage.SUCCESSFULLY_PLACED_ORDER;

@Service
public class InventoryServiceImpl implements InventoryService{
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ModifiedCartItemRepository modifiedCartItemRepository;

    @Override
    public boolean addProduct(ProductDTO productDTO) throws ModuleException {
        if (productDTO != null) {
            try {
                Product product = InventoryUtil.convertToProduct(productDTO);
                product.setProductStatus(ProductStatus.ACTIVE);
                productRepository.save(product);
                return true;
            } catch (Exception e) {
                throw new ModuleException("Product Adding Failed");
            }
        }
        return false;
    }

    @Override
    public boolean deActivateProduct(String productId) throws ModuleException {
        if (!productId.isEmpty()) {
            try {
                Product product = productRepository.findById(Long.parseLong(productId)).get();
                product.setProductStatus(ProductStatus.DE_ACTIVE);
                productRepository.save(product);
                return true;
            } catch (Exception e) {
                throw new ModuleException(e.getMessage());
            }
        }
        return false;
    }

    @Override
    public CommonMessageResponse modifyOrder(ModifyOrderRequest modifyOrderRequest) {
        modifiedCartItemRepository.saveAll(InventoryUtil.getModifiedCardItems(modifyOrderRequest));
        return new CommonMessageResponse(SUCCESSFULLY_PLACED_ORDER);
    }

    @Override
    public CommonMessageResponse modifyProduct(ModifyProductRequest modifyProductRequest) throws ModuleException {
        if (modifyProductRequest != null && modifyProductRequest.getProductId() != null) {
            Product savedProduct = productRepository.findById(modifyProductRequest.getProductId()).get();
            if (savedProduct != null) {
                if (!modifyProductRequest.getImageUrl().isEmpty()) {
                    savedProduct.setImageUrl(modifyProductRequest.getImageUrl());
                }
                if (!modifyProductRequest.getName().isEmpty()) {
                    savedProduct.setName(modifyProductRequest.getName());
                }
                if (modifyProductRequest.getPrice() != null) {
                    savedProduct.setPrice(modifyProductRequest.getPrice());
                }
                if (modifyProductRequest.getProductCategory() != null) {
                    savedProduct.setProductCategory(modifyProductRequest.getProductCategory());
                }
                if (modifyProductRequest.getProductStatus() != null) {
                    savedProduct.setProductStatus(modifyProductRequest.getProductStatus());
                }
                if (modifyProductRequest.getProductUnit() != null) {
                    savedProduct.setProductUnit(modifyProductRequest.getProductUnit());
                }

                productRepository.save(savedProduct);
                return new CommonMessageResponse(SUCCESSFULLY_MODIFIED);
            } else {
                throw new ModuleException("No Product with id : " + modifyProductRequest.getProductId());
            }
        } else {
            throw new ModuleException("Some fields are missing");
        }
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public boolean addProducts(List<ProductDTO> products) throws ModuleException {
        if (products != null & !products.isEmpty()) {
            try {
                List<Product> productEntities = products.stream().map(product -> {
                            Product proEntity = InventoryUtil.convertToProduct(product);
                            proEntity.setProductStatus(ProductStatus.ACTIVE);
                            return proEntity;
                        }
                ).collect(Collectors.toList());
                productRepository.saveAll(productEntities);
                return true;
            } catch (Exception ex) {
                throw new ModuleException("Product Adding Failed");
            }
        }
        return false;
    }
}
