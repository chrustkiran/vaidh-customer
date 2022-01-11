package com.vaidh.customer.message;

public class UserMessage {
    private String username;
    private String deliveryAddress;
    private String name;
    private String phoneNumber;
    private String email;

    public UserMessage(String username, String deliveryAddress, String name, String phoneNumber, String email) {
        this.username = username;
        this.deliveryAddress = deliveryAddress;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }
}
