package com.ourposapp.api.controller.user.request;

import lombok.Getter;

@Getter
public class UserAddressUpdateRequest {

    private Long userAddressId;
    private String address1;
    private String address2;
    private String zipcode;
    private String addressName;
    private String receiverName;
    private String phoneNumber;

}
