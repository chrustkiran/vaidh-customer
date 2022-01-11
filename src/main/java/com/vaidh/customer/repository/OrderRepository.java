package com.vaidh.customer.repository;

import com.vaidh.customer.model.inventory.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByFreshCartReferenceId(String freshCartReferenceId);

    @Query(value = "SELECT * FROM t_order WHERE order_placed_by = ?1 ", nativeQuery = true)
    List<Order> findOrdersByUser(String username);

    @Query(value = "SELECT * FROM t_order WHERE order_placed_by = ?1 AND order_status = ?2 ORDER BY order_created_time DESC LIMIT 1"
            , nativeQuery = true)
    Optional<Order> findOrdersByUserAndStatus(String username, String status);
}
