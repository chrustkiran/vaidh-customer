package com.vaidh.customer.repository;

import com.vaidh.customer.model.inventory.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
