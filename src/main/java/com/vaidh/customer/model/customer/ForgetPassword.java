package com.vaidh.customer.model.customer;

import com.vaidh.customer.model.enums.ForgetPasswordCodeStatus;

import javax.persistence.*;

@Entity
@Table(name = "forget_password")
public class ForgetPassword {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String emailAddress;
    private String code;

    @Enumerated(EnumType.STRING)
    private ForgetPasswordCodeStatus status;

    private String confirmedCode;

    public ForgetPassword(String emailAddress, String code) {
        this.emailAddress = emailAddress;
        this.code = code;
        this.status = ForgetPasswordCodeStatus.NEW;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ForgetPasswordCodeStatus getStatus() {
        return status;
    }

    public void setStatus(ForgetPasswordCodeStatus status) {
        this.status = status;
    }

    public String getConfirmedCode() {
        return confirmedCode;
    }

    public void setConfirmedCode(String confirmedCode) {
        this.confirmedCode = confirmedCode;
    }
}
