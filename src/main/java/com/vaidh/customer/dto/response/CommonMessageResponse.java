package com.vaidh.customer.dto.response;

import com.vaidh.customer.dto.CommonResults;

public class CommonMessageResponse implements CommonResults {
    private String message;

    public CommonMessageResponse(String message) {
        this.setMessage(message);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
