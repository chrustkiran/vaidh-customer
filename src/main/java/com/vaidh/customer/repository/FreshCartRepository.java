package com.vaidh.customer.repository;

import com.vaidh.customer.model.inventory.FreshCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FreshCartRepository extends JpaRepository<FreshCart, Long> {
    Optional<FreshCart> findByUsername(String username);
}
