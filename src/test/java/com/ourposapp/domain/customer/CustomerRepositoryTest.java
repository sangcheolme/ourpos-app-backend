package com.ourposapp.domain.customer;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.ourposapp.domain.customer.entity.Customer;
import com.ourposapp.domain.customer.entity.CustomerAddress;
import com.ourposapp.domain.customer.entity.Phone;
import com.ourposapp.domain.customer.repository.CustomerRepository;

@Transactional
@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @DisplayName("고객 주소 조회")
    @Test
    void findCustomerAddresses() {
        // given
        CustomerAddress customerAddress1 = createCustomerAddress();
        CustomerAddress customerAddress2 = createCustomerAddress();
        CustomerAddress customerAddress3 = createCustomerAddress();

        Customer customer = createCustomer();
        customer.addCustomerAddress(customerAddress1);
        customer.addCustomerAddress(customerAddress2);
        customer.addCustomerAddress(customerAddress3);

        customerRepository.save(customer);

        // when
        List<CustomerAddress> customerAddresses = customerRepository.findCustomerAddresses(customer.getId());

        // then
        assertThat(customerAddresses).hasSize(3)
            .extracting("defaultYn")
            .containsExactly(true, false, false);
    }

    @DisplayName("핸드폰 번호를 통해 회원의 존재 여부를 조회할 수 있다.")
    @Test
    void findByPhone() {
        // given
        Phone phone = new Phone("01012341234");
        Customer customer = Customer.builder()
            .phone(phone)
            .build();
        customerRepository.save(customer);

        // when
        boolean isExists = customerRepository.existsByPhone(new Phone("01012341234"));

        // then
        assertThat(isExists).isTrue();
    }

    @DisplayName("username을 통해 회원의 존재 여부를 조회할 수 있다.")
    @Test
    void existsByUsername() {
        // given
        String username = "hello";
        Customer customer = Customer.builder()
            .username(username)
            .build();
        customerRepository.save(customer);

        // when
        Customer findCustomer = customerRepository.findByUsername("hello").get();

        // then
        assertThat(findCustomer).isEqualTo(customer);
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