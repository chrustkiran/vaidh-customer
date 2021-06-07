package com.vaidh.customer.repository;

import com.vaidh.customer.model.inventory.FreshCartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FreshCartItemRepository extends JpaRepository<FreshCartItem, Long> {
    @Query(value = "DELETE FROM fresh_cart_item WHERE productId = ?1 ", nativeQuery = true)
    public boolean deleteAddedItemFromCart(Long productId);
}
