package com.vaidh.customer.util;

import com.vaidh.customer.dto.request.JwtRegisterRequest;
import com.vaidh.customer.dto.response.UserDetailsResponse;
import com.vaidh.customer.message.UserMessage;
import com.vaidh.customer.model.customer.UserEntity;
import com.vaidh.customer.model.enums.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    public static UserDetailsResponse convertToUserDetails(UserEntity userEntity) {
        if (userEntity != null) {
            UserDetailsResponse user = new UserDetailsResponse(userEntity.getUsername(), userEntity.getName(), userEntity.getPhoneNumber());
            user.setEmail(userEntity.getEmailAddress());
            user.setAddress(userEntity.getAddress());
            user.setInfo("ProductUnit", convertObjectToString(ProductUnit.values()));
            user.setInfo("ProductStatus", convertObjectToString(ProductStatus.values()));
            user.setInfo("ProductCategory", convertObjectToString(ProductCategory.values()));
            user.setInfo("OrderStatus", convertObjectToString(OrderStatus.values()));
            user.setInfo("ModifiedType", convertObjectToString(ModifiedType.values()));
            user.setInfo("FreshCartStatus", convertObjectToString(FreshCartStatus.values()));
            user.setInfo("ForgetPasswordStatus", convertObjectToString(ForgetPasswordCodeStatus.values()));
            return user;
        }
        return null;
    }

    private static List<String> convertObjectToString(Object[] objs) {
        if (objs != null) {
            return Arrays.stream(objs).map(s -> s.toString()).collect(Collectors.toList());
        }
        return null;
    }

    public static boolean validatePassword(String password) {
        return password != null  && !password.isEmpty() && password.length() >= 8;
    }

    public static UserMessage getUserMessage(UserEntity userEntity) {
        if (userEntity != null) {
            return new UserMessage(userEntity.getUsername(), userEntity.getAddress(), userEntity.getName(),
                    userEntity.getPhoneNumber(), userEntity.getEmailAddress());
        }
        return null;
    }
}
