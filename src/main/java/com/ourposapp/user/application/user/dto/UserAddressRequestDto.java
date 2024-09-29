package com.ourposapp.user.application.user.dto;

import com.ourposapp.common.model.Address;
import com.ourposapp.common.model.Phone;
import com.ourposapp.user.domain.user.entity.User;
import com.ourposapp.user.domain.user.entity.UserAddress;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserAddressRequestDto {

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
