package com.vaidh.customer.model.inventory;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "fresh_cart_item")
public class FreshCartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long freshCartItemId;

    private String fresh_cart_reference_id;

    private Long productId;

    private String prescribedImage;

    private Integer quantity;

    public FreshCartItem(String fresh_cart_reference_id, Long productId, Integer quantity) {
        this.fresh_cart_reference_id = fresh_cart_reference_id;
        this.productId = productId;
        this.quantity = quantity;
    }

    public FreshCartItem(String fresh_cart_reference_id, String prescribedImage) {
        this.fresh_cart_reference_id = fresh_cart_reference_id;
        this.prescribedImage = prescribedImage;
    }

    public Long getFreshCartItemId() {
        return freshCartItemId;
    }

    public void setFreshCartItemId(Long freshCartItemId) {
        this.freshCartItemId = freshCartItemId;
    }

    public String getFresh_cart_reference_id() {
        return fresh_cart_reference_id;
    }

    public void setFresh_cart_reference_id(String fresh_cart_reference_id) {
        this.fresh_cart_reference_id = fresh_cart_reference_id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProduct(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        if (quantity == null) quantity = 1;
        this.quantity = quantity;
    }

    public String getPrescribedImage() {
        return prescribedImage;
    }

    public void setPrescribedImage(String prescribedImage) {
        this.prescribedImage = prescribedImage;
    }
}
