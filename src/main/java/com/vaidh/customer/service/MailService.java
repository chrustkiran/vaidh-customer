package com.vaidh.customer.service;

public interface MailService {
    void sendMail(String message, String toAddress, String subject);
}
