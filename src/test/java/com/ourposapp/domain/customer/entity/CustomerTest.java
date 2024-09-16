package com.ourposapp.domain.customer.entity;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CustomerTest {

    @DisplayName("회원은 나의 기본 주소를 가져올 수 있다.")
    @Test
    void getDefaultAddress() {
        // given
        Customer customer = createCustomer();
        CustomerAddress customerAddress1 = createCustomerAddress();
        CustomerAddress customerAddress2 = createCustomerAddress();

        customer.addCustomerAddress(customerAddress1); // 기본 주소
        customer.addCustomerAddress(customerAddress2);

        // when
        CustomerAddress defaultAddress = customer.getDefaultAddress();

        // then
        assertThat(defaultAddress).isEqualTo(customerAddress1);
    }

    private CustomerAddress createCustomerAddress() {
        return CustomerAddress.builder()
            .base("서울시")
            .detail("중구 121")
            .build();
    }

    private Customer createCustomer() {
        return Customer.builder()
            .nickname("test")
            .build();
    }

}