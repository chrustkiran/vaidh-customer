package com.vaidh.customer.dto.response;

import com.vaidh.customer.dto.CommonResults;

public class ConfirmCodeResponse implements CommonResults {
    private String secretCode;

    public ConfirmCodeResponse(String secretCode) {
        this.secretCode = secretCode;
    }

    public String getSecretCode() {
        return secretCode;
    }

    public void setSecretCode(String secretCode) {
        this.secretCode = secretCode;
    }
}
