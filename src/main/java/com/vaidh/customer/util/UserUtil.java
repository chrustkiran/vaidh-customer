package com.vaidh.customer.util;

import com.vaidh.customer.dto.JwtRegisterRequest;
import com.vaidh.customer.model.customer.UserEntity;

public class UserUtil {
    public static UserEntity convertToUserEntity(JwtRegisterRequest request, String encryptedPassword) {
        if (request != null) {
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(request.getUsername());
            userEntity.setName(request.getName());
            userEntity.setEmailAddress(request.getEmailAddress());
            userEntity.setPhoneNumber(request.getPhoneNumber());
            userEntity.setAddress(request.getAddress());
            userEntity.setPassword(encryptedPassword);

            return userEntity;
        }
        return null;
    }
}
