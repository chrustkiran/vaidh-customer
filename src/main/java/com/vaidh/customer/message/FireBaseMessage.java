package com.vaidh.customer.message;

import java.util.Date;

public abstract class FireBaseMessage {
    private Date createdTime;

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
