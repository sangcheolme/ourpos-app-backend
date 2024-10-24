package com.ourposapp.api.controller.user.request;

import com.ourposapp.domain.common.Address;
import com.ourposapp.domain.common.Phone;
import com.ourposapp.domain.user.User;
import com.ourposapp.domain.user.UserAddress;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserAddressRequest {

    private Long userId;
    private String address1;
    private String address2;
    private String zipcode;
    private String addressName;
    private String receiverName;
    private String phoneNumber;

    public UserAddress toEntity(User user) {
        return UserAddress.builder()
                .user(user)
                .address(Address.of(address1, address2, zipcode))
                .phone(Phone.of(phoneNumber))
                .name(addressName)
                .receiverName(receiverName)
                .build();
    }
}
