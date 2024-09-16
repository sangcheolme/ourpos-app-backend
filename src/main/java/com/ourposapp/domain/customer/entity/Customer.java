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
import com.ourposapp.global.error.ErrorCode;
import com.ourposapp.global.error.exception.EntityNotFoundException;
import com.ourposapp.global.jwt.dto.JwtTokenDto;
import com.ourposapp.global.util.DateTimeUtils;

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

    @Column(name = "customer_profile")
    private String profile;

    @Embedded
    @AttributeOverride(name = "phoneNumber", column = @Column(name = "customer_phone", unique = true))
    private Phone phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "customer_login_type")
    private LoginType loginType;

    @Enumerated(EnumType.STRING)
    @Column(name = "customer_role")
    private Role role;

    private Boolean isPhoneVerified;

    @Column(length = 250)
    private String refreshToken;

    private LocalDateTime tokenExpirationTime;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<CustomerAddress> customerAddresses = new ArrayList<>();

    @Builder
    public Customer(String username, String nickname, String profile, Phone phone, LoginType loginType, Role role) {
        this.username = username;
        this.nickname = nickname;
        this.profile = profile;
        this.phone = phone;
        this.loginType = loginType;
        this.role = role;
        this.isPhoneVerified = false;
    }

    public CustomerAddress getDefaultAddress() {
        return customerAddresses.stream()
            .filter(CustomerAddress::getDefaultYn)
            .findFirst()
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.CUSTOMER_ADDRESS_NOT_EXIST));
    }

    public CustomerAddress getCustomerAddress(Long customerAddressId) {
        return customerAddresses.stream()
            .filter(customerAddress -> customerAddress.getId().equals(customerAddressId))
            .findFirst()
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.CUSTOMER_ADDRESS_NOT_EXIST));
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

    public void updateRefreshToken(JwtTokenDto jwtTokenDto) {
        this.refreshToken = jwtTokenDto.getRefreshToken();
        this.tokenExpirationTime = DateTimeUtils.convertToLocalDateTime(jwtTokenDto.getRefreshTokenExpireTime());
    }

    public void expireRefreshToken(LocalDateTime now) {
        this.tokenExpirationTime = now;
    }

    public void updatePhone(Phone phone) {
        this.phone = phone;
        this.isPhoneVerified = true;
    }
}
