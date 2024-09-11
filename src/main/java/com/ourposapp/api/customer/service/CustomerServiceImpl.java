package com.ourposapp.api.customer.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ourposapp.domain.customer.Customer;
import com.ourposapp.domain.customer.CustomerAddress;
import com.ourposapp.domain.customer.CustomerRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerValidator customerValidator;

    @Transactional
    @Override
    public void changeDefaultCustomerAddress(Long customerId, Long newDefaultAddressId) {
        Customer customer = customerRepository.findCustomerWithAddress(customerId).orElseThrow(
            () -> new IllegalArgumentException("회원을 찾을 수 없습니다. " + customerId)
        );

        CustomerAddress newDefaultAddress = customer.getCustomerAddress(newDefaultAddressId);
        CustomerAddress currentDefaultAddress = customer.getDefaultAddress();

        customerValidator.checkIfAlreadyDefaultAddress(newDefaultAddress);

        currentDefaultAddress.unsetDefault();
        newDefaultAddress.setAsDefault();
    }
}
