package com.ourposapp.user.domain.user.entity;

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

import com.ourposapp.common.model.BaseTimeEntity;
import com.ourposapp.common.model.Phone;
import com.ourposapp.global.error.ErrorCode;
import com.ourposapp.global.error.exception.EntityNotFoundException;
import com.ourposapp.global.error.exception.InvalidAddressException;
import com.ourposapp.global.jwt.dto.JwtTokenDto;
import com.ourposapp.global.util.DateTimeUtils;
import com.ourposapp.user.application.user.dto.UserAddressUpdateDto;
import com.ourposapp.user.domain.user.constant.LoginType;
import com.ourposapp.user.domain.user.constant.Role;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@Entity
public class User extends BaseTimeEntity {
    public static final int MAX_ADDRESS_COUNT = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "user_nickname")
    private String nickname;

    @Column(name = "user_profile")
    private String profile;

    @Embedded
    @AttributeOverride(name = "phoneNumber", column = @Column(name = "user_phone", unique = true))
    private Phone phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_login_type")
    private LoginType loginType;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private Role role;

    private Boolean isPhoneVerified;

    @Column(length = 250)
    private String refreshToken;

    private LocalDateTime tokenExpirationTime;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserAddress> userAddresses = new ArrayList<>();

    @Builder
    public User(String username, String nickname, String profile, Phone phone, LoginType loginType, Role role) {
        this.username = username;
        this.nickname = nickname;
        this.profile = profile;
        this.phone = phone;
        this.loginType = loginType;
        this.role = role;
        this.isPhoneVerified = false;
    }

    public void updateRefreshToken(JwtTokenDto jwtTokenDto) {
        this.refreshToken = jwtTokenDto.getRefreshToken();
        this.tokenExpirationTime = DateTimeUtils.convertToLocalDateTime(jwtTokenDto.getRefreshTokenExpireTime());
    }

    public void addUserAddress(UserAddress userAddress) {
        if (hasReachedMaxAddresses()) {
            throw new InvalidAddressException(ErrorCode.USER_ADDRESS_MAX_LIMIT_EXCEEDED);
        }

        if (userAddresses.isEmpty()) {
            userAddress.setAsDefault();
        }

        userAddresses.add(userAddress);
        userAddress.addUser(this);
    }

    public void expireRefreshToken(LocalDateTime now) {
        this.tokenExpirationTime = now;
    }

    public void updateUserAddress(UserAddressUpdateDto userAddressUpdateDto) {
        UserAddress userAddress = getUserAddress(userAddressUpdateDto.getUserAddressId());
        userAddress.update(
                userAddressUpdateDto.getAddressName(),
                userAddress.getReceiverName(),
                userAddressUpdateDto.getPhoneNumber(),
                userAddressUpdateDto.getAddress1(),
                userAddressUpdateDto.getAddress2(),
                userAddressUpdateDto.getZipcode()
        );
    }

    public void changeDefaultUserAddress(Long newDefaultAddressId) {
        UserAddress newDefaultAddress = getUserAddress(newDefaultAddressId);
        if (newDefaultAddress.getDefaultYn()) {
            throw new InvalidAddressException(ErrorCode.USER_ADDRESS_ALREADY_DEFAULT);
        }
        UserAddress currentDefaultAddress = getDefaultAddress();

        currentDefaultAddress.unsetDefault();
        newDefaultAddress.setAsDefault();
    }

    public void updatePhone(Phone phone) {
        this.phone = phone;
        this.isPhoneVerified = true;
    }

    public void deleteUserAddress(Long userAddressId) {
        UserAddress userAddress = getUserAddress(userAddressId);
        if (userAddress.getDefaultYn()) {
            throw new InvalidAddressException(ErrorCode.USER_ADDRESS_INVALID_DELETION);
        }
        userAddress.delete();
    }

    public UserAddress getDefaultAddress() {
        return userAddresses.stream()
                .filter(UserAddress::getDefaultYn)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_ADDRESS_NOT_EXIST));
    }

    private UserAddress getUserAddress(Long userAddressId) {
        return userAddresses.stream()
                .filter(userAddress -> userAddress.getId().equals(userAddressId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_ADDRESS_NOT_EXIST));
    }

    private boolean hasReachedMaxAddresses() {
        return userAddresses.size() >= MAX_ADDRESS_COUNT;
    }
}
