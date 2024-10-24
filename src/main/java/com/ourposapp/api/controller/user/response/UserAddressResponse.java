package com.ourposapp.api.controller.user.response;

import com.ourposapp.domain.user.UserAddress;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserAddressResponse {

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

    public static UserAddressResponse of(UserAddress userAddress) {
        return UserAddressResponse.builder()
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
