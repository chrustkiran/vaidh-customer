package com.vaidh.customer.util;

import com.vaidh.customer.dto.ModifyProductStatus;
import com.vaidh.customer.dto.ProductDTO;
import com.vaidh.customer.dto.request.ModifyOrderRequest;
import com.vaidh.customer.model.enums.ModifiedType;
import com.vaidh.customer.model.enums.OrderStatus;
import com.vaidh.customer.model.enums.ProductCategory;
import com.vaidh.customer.model.enums.ProductUnit;
import com.vaidh.customer.model.inventory.ModifiedCartItem;
import com.vaidh.customer.model.inventory.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InventoryUtil {
    private static final String YOUR_ORDER_HAS_BEEN = "Your order has been";

    public static Product convertToProduct(ProductDTO productDTO) {
        if (productDTO != null) {
            Product product = new Product();
            product.setName(productDTO.getName().toLowerCase());
            product.setPrice(productDTO.getPrice());
            product.setImageUrl(productDTO.getImageURL());
            product.setProductCategory(productDTO.getProductCategory());
            product.setProductUnit(productDTO.getProductUnit());
            product.setCompanyName(productDTO.getCompanyName().toLowerCase());
            product.setDescription(productDTO.getDescription().toLowerCase());
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
                    return ProductCategory.LIQUID;
                case "TABLET":
                    return ProductCategory.TABLET;
            }
        }
        return ProductCategory.UNKNOWN;
    }

    public static List<ModifiedCartItem> getModifiedCardItems(ModifyOrderRequest modifyOrderRequest, Map<Long, Double> productPriceByProductId) {
        List<ModifiedCartItem> modifiedCartItems = new ArrayList<>();
        if (modifyOrderRequest != null && !modifyOrderRequest.getProducts().isEmpty()) {
            for (ModifyProductStatus product : modifyOrderRequest.getProducts()) {
                ModifiedCartItem modifiedCartItem = new ModifiedCartItem();

                modifiedCartItem.setCreatedTime(modifyOrderRequest.getModifiedTime());
                modifiedCartItem.setFreshCartReferenceId(modifyOrderRequest.getReferenceCartId());
                modifiedCartItem.setProduct(new Product(product.getProductId()));
                modifiedCartItem.setCurrentPrice(productPriceByProductId.get(product.getProductId()));
                modifiedCartItem.setModifiedType(getModifiedType(product.getStatus()));

                modifiedCartItems.add(modifiedCartItem);
            }
        }
        return modifiedCartItems;
    }

    private static ModifiedType getModifiedType(String status) {
        if (!status.isEmpty()) {
            switch (status.toUpperCase()){
                case "EXISTING":
                    return ModifiedType.EXISTING;
                case "ADDED":
                    return ModifiedType.ADDED;
                case "REMOVED":
                    return ModifiedType.REMOVED;
                default:
                    return null;
            }
        }
        return null;
    }

    public static String getMeaningFullOrderStatus(OrderStatus orderStatus) {
        if (orderStatus != null) {
            switch (orderStatus) {
                case CREATED: return new StringBuilder(YOUR_ORDER_HAS_BEEN).append(" created").toString();
                case ACCEPTED: return new StringBuilder(YOUR_ORDER_HAS_BEEN).append(" accepted").toString();
                case VERIFIED: return new StringBuilder(YOUR_ORDER_HAS_BEEN).append(" verified").toString();
                case CANCELED: return new StringBuilder("Sorry! ").append(YOUR_ORDER_HAS_BEEN).append(" created").toString();
                case COMPLETED: return new StringBuilder(YOUR_ORDER_HAS_BEEN).append(" completed").toString();
                case DELIVERY_FAILED: return new StringBuilder("Sorry! ").append("The delivery of your order is failed").toString();
                case DELIVERY_PENDING:return new StringBuilder("The delivery of your order is pending").toString();
                case DELIVERY_IN_PROGRESS: return new StringBuilder("The delivery of your order is in progress").toString();
            }
        }
        return "";
    }
}
