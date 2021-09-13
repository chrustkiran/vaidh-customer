package com.vaidh.customer.repository;

import com.vaidh.customer.model.inventory.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "SELECT * FROM product WHERE productStatus = 'ACTIVE'", nativeQuery = true)
    List<Product> findAllActiveProducts();
}
