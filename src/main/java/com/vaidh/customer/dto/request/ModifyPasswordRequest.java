package com.vaidh.customer.dto.request;

public class ModifyPasswordRequest {
    private String username;
    private String currentPassword;
    private String newPassword;
    private String phoneNumber;
    private String code;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isNonEmpty() {
        return newPassword != null && !newPassword.isEmpty() &&
                ((currentPassword != null && !currentPassword.isEmpty()) || (code != null && !code.isEmpty()))
                && (username != null && !username.isEmpty() || phoneNumber != null && !phoneNumber.isEmpty());
    }


}
