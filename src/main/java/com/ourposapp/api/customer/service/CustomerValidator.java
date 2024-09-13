package com.ourposapp.api.customer.service;

import org.springframework.stereotype.Component;

import com.ourposapp.domain.customer.entity.CustomerAddress;

@Component
public class CustomerValidator {

    public void checkIfAlreadyDefaultAddress(CustomerAddress customerAddress) {
        if (customerAddress.getDefaultYn()) {
            throw new IllegalArgumentException("이미 기본주소로 설정되어 있습니다.");
        }
    }
}
