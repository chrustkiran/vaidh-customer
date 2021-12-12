package com.vaidh.customer.repository;

import com.vaidh.customer.model.inventory.ModifiedCartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ModifiedCartItemRepository extends JpaRepository<ModifiedCartItem, Long> {

    @Query(value = "SELECT * FROM modified_cart_item where fresh_cart_reference_id = ?1", nativeQuery = true)
    List<ModifiedCartItem> findAllModifiedCartItemsByFreshCartId(String freshCartId);
}
