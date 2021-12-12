package com.vaidh.customer.repository;

import com.vaidh.customer.model.inventory.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByFreshCartReferenceId(String freshCartReferenceId);

    @Query(value = "SELECT * FROM t_order WHERE order_placed_by = ?1 ", nativeQuery = true)
    List<Order> findOrdersByUser(String username);
}
