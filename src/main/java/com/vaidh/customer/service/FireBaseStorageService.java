package com.vaidh.customer.service;

import com.vaidh.customer.message.FireBaseMessage;

public interface FireBaseStorageService {
    void saveTestDate(String key, FireBaseMessage message);
}
