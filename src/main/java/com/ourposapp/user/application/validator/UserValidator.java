package com.ourposapp.user.application.validator;

import org.springframework.stereotype.Component;

import com.ourposapp.user.domain.user.entity.UserAddress;

@Component
public class UserValidator {

    public void checkIfAlreadyDefaultAddress(UserAddress userAddress) {
        if (userAddress.getDefaultYn()) {
            throw new IllegalArgumentException("이미 기본주소로 설정되어 있습니다.");
        }
    }
}
