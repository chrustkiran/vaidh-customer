package com.vaidh.customer.dto.request;

public class ModifyPasswordRequest {
    private String emailAddress;
    private String secretCode;
    private String newPassword;

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getSecretCode() {
        return secretCode;
    }

    public void setSecretCode(String secretCode) {
        this.secretCode = secretCode;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public boolean isNonEmpty() {
        return !newPassword.isEmpty() && !secretCode.isEmpty() && !emailAddress.isEmpty();
    }
}
