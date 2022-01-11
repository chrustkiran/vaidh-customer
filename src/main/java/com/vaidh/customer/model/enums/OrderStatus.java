package com.vaidh.customer.model.enums;

public enum OrderStatus {
    //fresh order
    CREATED,
    ACCEPTED,
    //verified by pharmacy
    VERIFIED,
    //until deliverer collects
    DELIVERY_PENDING,
    //delivery boy collected, delivery in progress
    DELIVERY_IN_PROGRESS,
    //delivery completed successfully
    COMPLETED,
    //delivery failed
    DELIVERY_FAILED,
    //order canceled / invalid order
    CANCELED
}
