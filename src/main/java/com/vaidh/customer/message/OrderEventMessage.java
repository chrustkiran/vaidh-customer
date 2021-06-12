package com.vaidh.customer.message;

import org.springframework.context.ApplicationEvent;

public class OrderEventMessage extends ApplicationEvent {
    private String message;

    public OrderEventMessage(Object source, String message) {
        super(source);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}