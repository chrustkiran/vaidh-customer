package com.vaidh.customer.service;

import com.vaidh.customer.message.FireBaseMessage;

public interface FireBaseStorageService {
    void sendMessage(String key, FireBaseMessage message);
    void sendMessage(String key, String message);
}
