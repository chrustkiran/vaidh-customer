package com.vaidh.customer.repository;

import com.vaidh.customer.model.inventory.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
