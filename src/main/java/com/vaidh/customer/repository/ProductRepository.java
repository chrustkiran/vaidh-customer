package com.vaidh.customer.repository;

import com.vaidh.customer.model.inventory.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "SELECT * FROM product WHERE product_status = 'ACTIVE'", nativeQuery = true)
    List<Product> findAllActiveProducts();

    @Query(value = "SELECT * FROM product WHERE name LIKE %:name%", nativeQuery = true)
    List<Product> findProductsByName(@Param("name") String name);
}
