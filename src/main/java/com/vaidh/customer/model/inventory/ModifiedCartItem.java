package com.vaidh.customer.model.inventory;

import com.vaidh.customer.model.enums.ModifiedType;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "modified_cart_item")
public class ModifiedCartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long modifiedCartItemId;

    private String freshCartReferenceId;

    private Date createdTime;

    @OneToOne
    @JoinColumn(name = "productId")
    private Product product;

    @Enumerated(EnumType.STRING)
    private ModifiedType modifiedType;

    private double currentPrice;

    public String getFreshCartReferenceId() {
        return freshCartReferenceId;
    }

    public void setFreshCartReferenceId(String freshCartReferenceId) {
        this.freshCartReferenceId = freshCartReferenceId;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ModifiedType getModifiedType() {
        return modifiedType;
    }

    public void setModifiedType(ModifiedType modifiedType) {
        this.modifiedType = modifiedType;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }
}
