package com.ourposapp.domain.customer;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import com.ourposapp.domain.generic.BaseTimeEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "customer")
@Entity
public class Customer extends BaseTimeEntity {
    public static final int MAX_ADDRESS_COUNT = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    @Column(name = "customer_username", unique = true)
    private String username;

    @Column(name = "customer_name")
    private String name;

    @Column(name = "customer_phone")
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "customer_role")
    private Role role;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<CustomerAddress> customerAddresses = new ArrayList<>();

    @Builder
    public Customer(String username, String name, String phone) {
        this.username = username;
        this.name = name;
        this.phone = phone;
        this.role = Role.ROLE_USER;
    }

    public void addCustomerAddress(CustomerAddress customerAddress) {
        if (hasReachedMaxAddresses()) {
            throw new IllegalArgumentException("고객 주소는 최대 " + MAX_ADDRESS_COUNT + "개까지 저장 가능합니다.");
        }

        if (customerAddresses.isEmpty()) {
            customerAddress.setAsDefault();
        }

        customerAddresses.add(customerAddress);
        customerAddress.addCustomer(this);
    }

    private boolean hasReachedMaxAddresses() {
        return customerAddresses.size() >= MAX_ADDRESS_COUNT;
    }
}
