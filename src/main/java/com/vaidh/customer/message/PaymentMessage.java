package com.vaidh.customer.message;

public class PaymentMessage {
    private Double totalAmount;
    private Double offerAmount;
    private Double netAmount;

    public PaymentMessage() {}
    public PaymentMessage(Double totalAmount, Double offerAmount, Double netAmount) {
        this.totalAmount = totalAmount;
        this.offerAmount = offerAmount;
        this.netAmount = netAmount;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public Double getOfferAmount() {
        return offerAmount;
    }

    public Double getNetAmount() {
        return netAmount;
    }
}
