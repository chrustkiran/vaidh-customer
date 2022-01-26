package com.vaidh.customer.service;

import com.vaidh.customer.dto.PushNotificationDTO;

import java.util.concurrent.ExecutionException;

public interface FirebaseNotificationService {
    void sendMessage(PushNotificationDTO request) throws InterruptedException, ExecutionException;
}
