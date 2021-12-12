package com.vaidh.customer.dto.response;

import com.vaidh.customer.dto.CommonResults;
import com.vaidh.customer.model.enums.OrderStatus;
import com.vaidh.customer.model.inventory.Product;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class HistoryResponse implements Serializable, CommonResults {
    private final long serialVersionUID = 1L;

    private Date createdTime;
    private Date modifiedTime;
    private List<Product> products;
    private OrderStatus orderStatus;
    private double totalPrice;
    private double discount;
    private double netAmount;

    public HistoryResponse(Date createdTime, Date modifiedTime, List<Product> products, OrderStatus orderStatus, double totalPrice, double discount, double netAmount) {
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
        this.products = products;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.discount = discount;
        this.netAmount = netAmount;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
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

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(double netAmount) {
        this.netAmount = netAmount;
    }
}
