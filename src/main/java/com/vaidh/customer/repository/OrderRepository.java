package com.vaidh.customer.repository;

import com.vaidh.customer.model.inventory.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByFreshCartReferenceId(String freshCartReferenceId);
}
