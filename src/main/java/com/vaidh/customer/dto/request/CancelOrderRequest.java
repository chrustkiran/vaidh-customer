package com.vaidh.customer.dto.request;

public class CancelOrderRequest {
    private String referenceId;
    private String note;

    public String getReferenceId() {
        return referenceId;
    }

    public String getNote() {
        return note;
    }
}
