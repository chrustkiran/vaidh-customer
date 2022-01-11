package com.vaidh.customer.dto.response;

import com.vaidh.customer.dto.CommonResults;

public class AddPrescriptionResponse implements CommonResults {
    private String referenceId;

    public AddPrescriptionResponse(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }
}
