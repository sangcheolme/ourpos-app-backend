package com.ourposapp.user.application.user.dto;

import lombok.Builder;
import lombok.Getter;

import com.ourposapp.user.domain.user.entity.UserAddress;

@Getter
@Builder
public class UserAddressResponseDto {

    private Long userAddressId;
    private Long userId;
    private String address1;
    private String address2;
    private String zipcode;
    private String addressName;
    private String receiverName;
    private String phoneNumber;
    private Boolean defaultYn;
    private Boolean deleteYn;

    public static UserAddressResponseDto of(UserAddress userAddress) {
        return UserAddressResponseDto.builder()
                .userAddressId(userAddress.getId())
                .userId(userAddress.getUser().getId())
                .address1(userAddress.getAddress().getAddress1())
                .address2(userAddress.getAddress().getAddress2())
                .zipcode(userAddress.getAddress().getZipcode())
                .addressName(userAddress.getName())
                .receiverName(userAddress.getReceiverName())
                .phoneNumber(userAddress.getPhone().getPhoneNumber())
                .defaultYn(userAddress.getDefaultYn())
                .deleteYn(userAddress.getDeleteYn())
                .build();
    }
}
