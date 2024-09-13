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

    private CustomerAddress createCustomerAddress() {
        return CustomerAddress.builder()
            .base("서울시")
            .detail("중구 121")
            .build();
    }

    private Customer createCustomer() {
        return Customer.builder()
            .username("testId")
            .nickname("test")
            .build();
    }
}