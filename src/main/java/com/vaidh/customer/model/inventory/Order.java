package com.vaidh.customer.model.inventory;

import com.google.firebase.database.DataSnapshot;
import com.sun.istack.NotNull;
import com.vaidh.customer.model.enums.OrderStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;

    @Column(unique = true)
    @NotNull
    private String freshCartReferenceId;

    @NotNull
    private String prescribedImageUrl;

    private String failureNote;

    @OneToOne
    @JoinColumn(name="modified_cart_item_id")
    private ModifiedCartItem modifiedCartItemId;

    private String orderPlacedBy;

    private Date orderCreatedTime;

    private Date orderModifiedTime;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "deliveryId")
    private Delivery delivery;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "paymentId")
    private Payment payment;

    public Order() {}
    public Order(String freshCartReferenceId, String orderPlacedBy, Date orderCreatedTime, String prescribedImageUrl) {
        this.freshCartReferenceId = freshCartReferenceId;
        this.orderPlacedBy = orderPlacedBy;
        this.orderCreatedTime = orderCreatedTime;
        this.prescribedImageUrl = prescribedImageUrl;
        this.orderStatus = OrderStatus.CREATED;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getFreshCartReferenceId() {
        return freshCartReferenceId;
    }

    public void setFreshCartReferenceId(String freshCartReferenceId) {
        this.freshCartReferenceId = freshCartReferenceId;
    }

    public ModifiedCartItem getModifiedCartItemId() {
        return modifiedCartItemId;
    }

    public void setModifiedCartItemId(ModifiedCartItem modifiedCartItemId) {
        this.modifiedCartItemId = modifiedCartItemId;
    }

    public String getOrderPlacedBy() {
        return orderPlacedBy;
    }

    public void setOrderPlacedBy(String orderPlacedBy) {
        this.orderPlacedBy = orderPlacedBy;
    }

    public Date getOrderCreatedTime() {
        return orderCreatedTime;
    }

    public void setOrderCreatedTime(Date orderCreatedTime) {
        this.orderCreatedTime = orderCreatedTime;
    }

    public Date getOrderModifiedTime() {
        return orderModifiedTime;
    }

    public void setOrderModifiedTime(Date orderModifiedTime) {
        this.orderModifiedTime = orderModifiedTime;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public String getPrescribedImageUrl() {
        return prescribedImageUrl;
    }

    public void setPrescribedImageUrl(String prescribedImageUrl) {
        this.prescribedImageUrl = prescribedImageUrl;
    }

    public String getFailureNote() {
        return failureNote;
    }

    public void setFailureNote(String failureNote) {
        this.failureNote = failureNote;
    }
}
