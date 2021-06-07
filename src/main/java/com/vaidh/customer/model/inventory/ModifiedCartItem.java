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

    @ManyToMany
    @JoinColumn(name = "productId")
    private List<Product> product;

    @Enumerated(EnumType.STRING)
    private ModifiedType modifiedType;

    public Long getModifiedCartItemId() {
        return modifiedCartItemId;
    }

    public void setModifiedCartItemId(Long modifiedCartItemId) {
        this.modifiedCartItemId = modifiedCartItemId;
    }

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

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }

    public ModifiedType getModifiedType() {
        return modifiedType;
    }

    public void setModifiedType(ModifiedType modifiedType) {
        this.modifiedType = modifiedType;
    }
}
