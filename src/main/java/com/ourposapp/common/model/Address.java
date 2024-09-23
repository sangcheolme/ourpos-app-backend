package com.ourposapp.common.model;

import jakarta.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Address {

    private String address1;
    private String address2;
    private String zipcode;

    private Address(String address1, String address2, String zipcode) {
        this.address1 = address1;
        this.address2 = address2;
        this.zipcode = zipcode;
    }

    public static Address of(String address1, String address2, String zipcode) {
        return new Address(address1, address2, zipcode);
    }

}
