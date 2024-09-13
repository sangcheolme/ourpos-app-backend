package com.ourposapp.domain.customer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ourposapp.domain.customer.entity.Customer;
import com.ourposapp.domain.customer.entity.CustomerAddress;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("select c from Customer c left join fetch c.customerAddresses ca where c.id = :customerId")
    Optional<Customer> findCustomerWithAddress(@Param("customerId") Long customerId);

    @Query("select ca from CustomerAddress ca join fetch ca.customer c where c.id = :customerId")
    List<CustomerAddress> findCustomerAddresses(@Param("customerId") Long customerId);
}
