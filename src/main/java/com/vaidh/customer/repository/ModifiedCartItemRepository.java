package com.vaidh.customer.repository;

import com.vaidh.customer.model.inventory.ModifiedCartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModifiedCartItemRepository extends JpaRepository<ModifiedCartItem, Long> {
}
