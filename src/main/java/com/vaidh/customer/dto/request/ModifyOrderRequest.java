package com.vaidh.customer.dto.request;

import com.vaidh.customer.dto.ModifyProductStatus;

import java.util.Date;
import java.util.List;

public class ModifyOrderRequest {
    private String referenceCartId;
    List<ModifyProductStatus> products;
    Date modifiedTime;

    public String getReferenceCartId() {
        return referenceCartId;
    }

    public void setReferenceCartId(String referenceCartId) {
        this.referenceCartId = referenceCartId;
    }

    public List<ModifyProductStatus> getProducts() {
        return products;
    }

    public void setProducts(List<ModifyProductStatus> products) {
        this.products = products;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }
}
