package com.vaidh.customer.service;

import com.vaidh.customer.model.inventory.FreshCartItem;

import java.util.List;

public interface PaymentService {
    Double calculateTotalPaymentOfOrder(List<FreshCartItem> freshCartItems);
}
