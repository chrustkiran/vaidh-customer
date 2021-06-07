package com.vaidh.customer.dto;

public abstract class ResponseCommonMessage implements ResponseMessage {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
