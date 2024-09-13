package com.ourposapp.domain.customer.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import com.ourposapp.domain.common.BaseTimeEntity;
import com.ourposapp.domain.customer.constant.LoginType;
import com.ourposapp.domain.customer.constant.Role;

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

    @Column(name = "customer_nickname")
    private String nickname;

    @Embedded
    @AttributeOverride(name = "phoneNumber", column = @Column(name = "customer_phone"))
    private Phone phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "customer_login_type")
    private LoginType loginType;

    @Enumerated(EnumType.STRING)
    @Column(name = "customer_role")
    private Role role;

    @Column(length = 250)
    private String refreshToken;

    private LocalDateTime tokenExpirationTime;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<CustomerAddress> customerAddresses = new ArrayList<>();

    @Builder
    public Customer(String username, String nickname, Phone phone, LoginType loginType, Role role) {
        this.username = username;
        this.nickname = nickname;
        this.phone = phone;
        this.loginType = loginType;
        this.role = role;
    }

    public CustomerAddress getDefaultAddress() {
        return customerAddresses.stream()
            .filter(CustomerAddress::getDefaultYn)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("등록되어 있는 기본 주소가 없습니다. 기본 주소를 먼저 등록해주세요"));
    }

    public CustomerAddress getCustomerAddress(Long customerAddressId) {
        return customerAddresses.stream()
            .filter(customerAddress -> customerAddress.getId().equals(customerAddressId))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("회원의 주소가 아닙니다. " + customerAddressId));
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
