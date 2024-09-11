package com.ourposapp.api.customer.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import jakarta.persistence.EntityManager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.ourposapp.domain.customer.Customer;
import com.ourposapp.domain.customer.CustomerAddress;
import com.ourposapp.domain.customer.CustomerRepository;

@Transactional
@SpringBootTest
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EntityManager em;

    @DisplayName("회원은 기본 주소를 변경할 수 있다.")
    @Test
    void changeDefaultCustomerAddress() {
        // given
        CustomerAddress customerAddress1 = createCustomerAddress();
        CustomerAddress customerAddress2 = createCustomerAddress();
        CustomerAddress customerAddress3 = createCustomerAddress();

        Customer customer = createCustomer("testId");
        customer.addCustomerAddress(customerAddress1);
        customer.addCustomerAddress(customerAddress2);
        customer.addCustomerAddress(customerAddress3);

        customerRepository.save(customer);
        clearPersistenceContext();
        
        // when
        // 기본주소 변경: 1 -> 2
        customerService.changeDefaultCustomerAddress(customer.getId(), customerAddress2.getId());
        clearPersistenceContext();

        List<CustomerAddress> customerAddresses = customerRepository.findCustomerAddresses(customer.getId());
        CustomerAddress findCustomerAddress1 = findCustomerAddress(customerAddresses, customerAddress1.getId());
        CustomerAddress findCustomerAddress2 = findCustomerAddress(customerAddresses, customerAddress2.getId());
        CustomerAddress findCustomerAddress3 = findCustomerAddress(customerAddresses, customerAddress3.getId());

        // then
        assertThat(findCustomerAddress1.getDefaultYn()).isFalse();
        assertThat(findCustomerAddress2.getDefaultYn()).isTrue();
        assertThat(findCustomerAddress3.getDefaultYn()).isFalse();
    }
    
    @DisplayName("회원은 본인의 주소가 아닌 주소를 기본 주소로 변경하려 하면 예외가 발생한다.")
    @Test
    void changDefaultCustomerAddress_ex_notMyAddress() {
        // given
        CustomerAddress customerAddress1 = createCustomerAddress();
        CustomerAddress customerAddress2 = createCustomerAddress();
        Customer customer1 = createCustomer("testId1");
        customer1.addCustomerAddress(customerAddress1);
        customer1.addCustomerAddress(customerAddress2);

        CustomerAddress customerAddress4 = createCustomerAddress();
        Customer customer2 = createCustomer("testId2");
        customer2.addCustomerAddress(customerAddress4);

        // when
        customerRepository.save(customer1);
        customerRepository.save(customer2);

        // then
        assertThatThrownBy(
            () -> customerService.changeDefaultCustomerAddress(customer1.getId(), customerAddress4.getId()))
            .isInstanceOf(IllegalArgumentException.class);
    }
    
    @DisplayName("이미 기본 주소인 주소를 기본 주소로 변경하려고 하면 예외가 발생한다.")
    @Test
    void changDefaultCustomerAddress_ex_alreadyDefault() {
        // given
        CustomerAddress customerAddress1 = createCustomerAddress();
        CustomerAddress customerAddress2 = createCustomerAddress();

        Customer customer = createCustomer("testId1");
        customer.addCustomerAddress(customerAddress1);
        customer.addCustomerAddress(customerAddress2);

        // when
        customerRepository.save(customer);

        // then
        assertThatThrownBy(
            () -> customerService.changeDefaultCustomerAddress(customer.getId(), customerAddress1.getId()))
            .isInstanceOf(IllegalArgumentException.class);
    }

    private CustomerAddress findCustomerAddress(List<CustomerAddress> customerAddresses, Long customerId) {
        return customerAddresses.stream()
            .filter(customerAddress -> customerAddress.getId().equals(customerId))
            .findFirst()
            .orElseThrow();
    }

    private CustomerAddress createCustomerAddress() {
        return CustomerAddress.builder()
            .base("서울시")
            .detail("중구 121")
            .build();
    }

    private Customer createCustomer(String username) {
        return Customer.builder()
            .username(username)
            .name("test")
            .build();
    }

    private void clearPersistenceContext() {
        em.flush();
        em.clear();
    }

}