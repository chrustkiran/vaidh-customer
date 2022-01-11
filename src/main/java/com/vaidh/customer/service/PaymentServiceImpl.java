package com.vaidh.customer.service;

import com.vaidh.customer.model.inventory.FreshCartItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Override
    public Double calculateTotalPaymentOfOrder(List<FreshCartItem> freshCartItems) {
        Double tot = 0.0;
        if (freshCartItems != null && !freshCartItems.isEmpty()) {
            for (FreshCartItem freshCartItem : freshCartItems) {
                tot += freshCartItem.getPrice() * freshCartItem.getQuantity();
            }
        }
        return tot;
    }
}
