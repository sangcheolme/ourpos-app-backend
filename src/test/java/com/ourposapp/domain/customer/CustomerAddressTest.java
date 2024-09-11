package com.ourposapp.domain.customer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomerAddressTest {

    @DisplayName("고객은 주소를 추가할 수 있고, 첫 번째로 추가한 주소는 기본주소가 된다.")
    @Test
    void addCustomerAddress_default() {
        // given
        Customer customer = createCustomer();
        CustomerAddress customerAddress = createCustomerAddress();

        // when
        customer.addCustomerAddress(customerAddress);

        // then
        assertThat(customer.getCustomerAddresses().get(0).getDefaultYn()).isTrue();
    }

    @DisplayName("고객은 주소를 추가할 수 있고, 이미 기본 주소가 있다면 기본 주소 여부는 false가 된다.")
    @Test
    void addCustomerAddress_non_default() {
        // given
        Customer customer = createCustomer();
        CustomerAddress customerAddress1 = createCustomerAddress();
        CustomerAddress customerAddress2 = createCustomerAddress();
        CustomerAddress customerAddress3 = createCustomerAddress();

        // when
        customer.addCustomerAddress(customerAddress1);
        customer.addCustomerAddress(customerAddress2);
        customer.addCustomerAddress(customerAddress3);

        // then
        assertThat(customer.getCustomerAddresses().get(0).getDefaultYn()).isTrue();
        assertThat(customer.getCustomerAddresses().get(1).getDefaultYn()).isFalse();
        assertThat(customer.getCustomerAddresses().get(2).getDefaultYn()).isFalse();
    }

    @DisplayName("고객은 주소를 최대 3개까지 추가할 수 있고, 주소가 3개가 넘는 경우 예외가 터진다.")
    @Test
    void addCustomerAddress_ex() {
        // given
        Customer customer = createCustomer();
        CustomerAddress customerAddress1 = createCustomerAddress();
        CustomerAddress customerAddress2 = createCustomerAddress();
        CustomerAddress customerAddress3 = createCustomerAddress();
        CustomerAddress customerAddress4 = createCustomerAddress();

        // when
        customer.addCustomerAddress(customerAddress1);
        customer.addCustomerAddress(customerAddress2);
        customer.addCustomerAddress(customerAddress3);

        // then
        assertThatThrownBy(() -> customer.addCustomerAddress(customerAddress4))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("나의 기본 주소 가져오기")
    @Test
    void getDefaultAddress() {
        // given
        Customer customer = createCustomer();
        CustomerAddress customerAddress1 = createCustomerAddress();
        CustomerAddress customerAddress2 = createCustomerAddress();

        customer.addCustomerAddress(customerAddress1);
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
                .username("testId")
                .name("test")
                .build();
    }

}