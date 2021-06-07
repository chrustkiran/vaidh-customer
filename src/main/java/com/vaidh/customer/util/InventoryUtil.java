package com.vaidh.customer.util;

import com.vaidh.customer.dto.ProductDTO;
import com.vaidh.customer.model.enums.ProductCategory;
import com.vaidh.customer.model.enums.ProductUnit;
import com.vaidh.customer.model.inventory.Product;

public class InventoryUtil {
    public static Product convertToProduct(ProductDTO productDTO) {
        if (productDTO != null) {
            Product product = new Product();
            product.setName(productDTO.getName());
            product.setPrice(productDTO.getPrice());
            product.setImageUrl(productDTO.getImageURL());
            product.setProductCategory(getCategory(productDTO.getProductCategory()));
            product.setProductUnit(getProductUnit(productDTO.getProductUnit()));

            return product;
        }
        return null;
    }

    private static ProductUnit getProductUnit(String productUnit) {
        if (productUnit != null && !productUnit.isEmpty()) {
            productUnit = productUnit.toUpperCase();
            switch (productUnit) {
                case "MG":
                    return ProductUnit.MG;
                case "ML":
                    return ProductUnit.ML;
            }
        }
        return ProductUnit.UNKNOWN;
    }

    private static ProductCategory getCategory(String productCategory) {
        if (productCategory != null && !productCategory.isEmpty()) {
            productCategory = productCategory.toUpperCase();
            switch (productCategory) {
                case "SYRUP":
                    return ProductCategory.SYRUP;
                case "TABLET":
                    return ProductCategory.TABLET;
            }
        }
        return ProductCategory.UNKNOWN;
    }
}
