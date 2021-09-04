package com.vaidh.customer.dto.response;

import com.vaidh.customer.dto.CommonResults;

public class ConfirmCodeResponse implements CommonResults {
    private String secredCode;

    public ConfirmCodeResponse(String secredCode) {
        this.secredCode = secredCode;
    }

    public String getSecredCode() {
        return secredCode;
    }

    public void setSecredCode(String secredCode) {
        this.secredCode = secredCode;
    }
}
