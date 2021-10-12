package com.vaidh.customer.dto.response;

import com.vaidh.customer.dto.CommonResults;

import java.util.HashMap;
import java.util.List;

public class UserDetailsResponse implements CommonResults {
    private String userName;
    private String name;
    private String phoneNumber;
    private HashMap<String, List<String>> info = new HashMap<>();

    public UserDetailsResponse(String userName, String name, String phoneNumber) {
        this.userName = userName;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public HashMap<String, List<String>> getInfo() {
        return info;
    }

    public void setInfo(String key,List<String> values) {
        if (this.info != null) {
            this.info = new HashMap<>();
            this.info.put(key, values);
        } else {
            this.info.put(key, values);
        }
    }
}
