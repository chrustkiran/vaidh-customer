package com.vaidh.customer.message;

import java.util.Map;

public class PlacedOrderMessage extends FireBaseMessage {
    private Map<String, Integer> itemWiseQuantity;
    private PaymentMessage payment;


    public PlacedOrderMessage(Map<String, Integer> itemWiseQuantity, PaymentMessage payment) {
        this.itemWiseQuantity = itemWiseQuantity;
        this.payment = payment;
    }

    public Map<String, Integer> getItemWiseQuantity() {
        return itemWiseQuantity;
    }

    public PaymentMessage getPayment() {
        return payment;
    }
}
