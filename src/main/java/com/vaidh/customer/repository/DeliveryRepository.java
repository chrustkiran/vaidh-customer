package com.vaidh.customer.repository;

import com.vaidh.customer.model.inventory.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
