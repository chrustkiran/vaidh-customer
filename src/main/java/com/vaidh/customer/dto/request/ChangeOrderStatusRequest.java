package com.vaidh.customer.dto.request;

import com.vaidh.customer.model.enums.OrderStatus;

public class ChangeOrderStatusRequest {
    private OrderStatus orderStatus;
    private String referenceId;

    public ChangeOrderStatusRequest(OrderStatus orderStatus, String referenceId) {
        this.orderStatus = orderStatus;
        this.referenceId = referenceId;
    }

    public ChangeOrderStatusRequest() { }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }
}
