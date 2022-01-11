package com.vaidh.customer.model.inventory;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "fresh_cart_item")
public class FreshCartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long freshCartItemId;

    private String freshCartReferenceId;

    private Long productId;

    private String prescribedImage;

    private Integer quantity;

    private Double price;

    public FreshCartItem() {}

    public FreshCartItem(String freshCartReferenceId, Long productId, Integer quantity, Double price) {
        this.freshCartReferenceId = freshCartReferenceId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public FreshCartItem(String freshCartReferenceId, String prescribedImage) {
        this.freshCartReferenceId = freshCartReferenceId;
        this.prescribedImage = prescribedImage;
    }

    public Long getFreshCartItemId() {
        return freshCartItemId;
    }

    public void setFreshCartItemId(Long freshCartItemId) {
        this.freshCartItemId = freshCartItemId;
    }

    public String getFreshCartReferenceId() {
        return freshCartReferenceId;
    }

    public void setFresh_cart_reference_id(String freshCartReferenceId) {
        this.freshCartReferenceId = freshCartReferenceId;
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

    public Double getPrice() {
        return price;
    }

}
