package com.ourposapp.user.application.user.dto;

import lombok.Getter;

@Getter
public class UserAddressUpdateDto {

    private Long userAddressId;
    private String address1;
    private String address2;
    private String zipcode;
    private String addressName;
    private String receiverName;
    private String phoneNumber;

}
