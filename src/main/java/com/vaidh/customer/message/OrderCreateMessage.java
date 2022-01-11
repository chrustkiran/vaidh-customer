package com.vaidh.customer.message;

import com.vaidh.customer.model.enums.OrderStatus;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class OrderCreateMessage extends FireBaseMessage {
    private String image;
    private UserMessage user;
    private OrderStatus status;

    public OrderCreateMessage(String image, UserMessage user, OrderStatus status) {
        this.image = image;
        this.user = user;
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public UserMessage getUser() {
        return user;
    }

    public OrderStatus getStatus() {
        return status;
    }
}
