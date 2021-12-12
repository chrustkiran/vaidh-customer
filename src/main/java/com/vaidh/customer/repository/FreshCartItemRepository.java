package com.vaidh.customer.repository;

import com.vaidh.customer.model.inventory.FreshCartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FreshCartItemRepository extends JpaRepository<FreshCartItem, Long> {
    @Query(value = "DELETE FROM fresh_cart_item WHERE product_id = ?1 ", nativeQuery = true)
    boolean deleteAddedItemFromCart(Long productId);

    List<FreshCartItem> findByFreshCartReferenceId(String freshCartReferenceId);
}
