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
    private String username;
    private String code;

    @Enumerated(EnumType.STRING)
    private ForgetPasswordCodeStatus status;

    private String confirmedCode;

    public ForgetPassword() {}

    public ForgetPassword(String username, String code) {
        this.username = username;
        this.code = code;
        this.status = ForgetPasswordCodeStatus.NEW;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
