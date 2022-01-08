package com.vaidh.customer.model.inventory;

import com.sun.istack.NotNull;
import com.vaidh.customer.model.enums.FreshCartStatus;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;

@Entity
@Table(name = "fresh_cart")
public class FreshCart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long freshCartId;

    @Column(unique = true)
    private String username;

    @Enumerated(EnumType.STRING)
    private FreshCartStatus status;

    @NotNull
    @Column(unique = true)
    //this will be auto renewed whenever user changes to active
    //username_currentmillisecond
    private String cartReferenceId;

    public FreshCart() {}

    public FreshCart(Long freshCartId, FreshCartStatus status, String cartReferenceId) {
        this.freshCartId = freshCartId;
        this.status = status;
        this.cartReferenceId = cartReferenceId;
    }

    public FreshCart(String username, FreshCartStatus status, String cartReferenceId) {
        this.username = username;
        this.status = status;
        this.cartReferenceId = cartReferenceId;
    }

    public Long getFreshCartId() {
        return freshCartId;
    }

    public void setFreshCartId(Long freshCartId) {
        this.freshCartId = freshCartId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public FreshCartStatus getStatus() {
        return status;
    }

    public void setStatus(FreshCartStatus status) {
        this.status = status;
    }

    public String getCartReferenceId() {
        return cartReferenceId;
    }

    public void setCartReferenceId(String cartReferenceId) {
        this.cartReferenceId = cartReferenceId;
    }
}
