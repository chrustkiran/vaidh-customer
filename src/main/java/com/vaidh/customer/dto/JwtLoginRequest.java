package com.vaidh.customer.dto;

import java.io.Serializable;

public class JwtLoginRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;
    private String phoneNumber;
    private String password;

    //need default constructor for JSON Parsing
    public JwtLoginRequest()
    {

    }

    public JwtLoginRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}