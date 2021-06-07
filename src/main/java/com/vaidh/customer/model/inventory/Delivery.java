package com.vaidh.customer.model.inventory;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "delivery")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long deliveryId;

    @NotNull
    private String deliveryAddress;

    @NotNull
    private String deliveredBy;

    @NotNull
    private Date deliveryStartedTime;

    private Date deliveryEndedTime;

    public Long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getDeliveredBy() {
        return deliveredBy;
    }

    public void setDeliveredBy(String deliveredBy) {
        this.deliveredBy = deliveredBy;
    }

    public Date getDeliveryStartedTime() {
        return deliveryStartedTime;
    }

    public void setDeliveryStartedTime(Date deliveryStartedTime) {
        this.deliveryStartedTime = deliveryStartedTime;
    }

    public Date getDeliveryEndedTime() {
        return deliveryEndedTime;
    }

    public void setDeliveryEndedTime(Date deliveryEndedTime) {
        this.deliveryEndedTime = deliveryEndedTime;
    }
}
