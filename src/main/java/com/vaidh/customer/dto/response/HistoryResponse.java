package com.vaidh.customer.dto.response;

import com.vaidh.customer.dto.CommonResults;
import com.vaidh.customer.dto.ProductHistoryDTO;
import com.vaidh.customer.model.enums.OrderStatus;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class HistoryResponse implements Serializable, CommonResults {
    private final long serialVersionUID = 1L;

    private Date createdTime;
    private List<ProductHistoryDTO> products;
    private OrderStatus orderStatus;
    private Double totalPrice;
    private Double discount;
    private Double netAmount;
    private String prescriptionUrl;
    private String failureNote;

    public HistoryResponse(Date createdTime, List<ProductHistoryDTO> products, OrderStatus orderStatus, Double totalPrice,
                           Double discount, Double netAmount, String prescriptionUrl, String failureNote) {
        this.createdTime = createdTime;
        this.products = products;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.discount = discount;
        this.netAmount = netAmount;
        this.prescriptionUrl = prescriptionUrl;
        this.failureNote = failureNote;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public List<ProductHistoryDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductHistoryDTO> products) {
        this.products = products;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public double getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(Double netAmount) {
        this.netAmount = netAmount;
    }

    public String getPrescriptionUrl() {
        return prescriptionUrl;
    }

    public void setPrescriptionUrl(String prescriptionUrl) {
        this.prescriptionUrl = prescriptionUrl;
    }

    public String getFailureNote() {
        return failureNote;
    }

    public void setFailureNote(String failureNote) {
        this.failureNote = failureNote;
    }
}
