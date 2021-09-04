package com.vaidh.customer.message;

import java.util.Date;

public abstract class FireBaseMessage {
    private Date createdTime;
    private String username;

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
