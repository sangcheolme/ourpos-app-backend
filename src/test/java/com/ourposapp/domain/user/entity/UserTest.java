package com.ourposapp.domain.user.entity;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ourposapp.domain.common.Address;

class UserTest {

    @DisplayName("회원은 나의 기본 주소를 가져올 수 있다.")
    @Test
    void getDefaultAddress() {
        // given
        User user = createUser();
        UserAddress userAddress1 = createUserAddress();
        UserAddress userAddress2 = createUserAddress();

        user.addUserAddress(userAddress1); // 기본 주소
        user.addUserAddress(userAddress2);

        // when
        UserAddress defaultAddress = user.getDefaultAddress();

        // then
        assertThat(defaultAddress).isEqualTo(userAddress1);
    }

    private UserAddress createUserAddress() {
        return UserAddress.builder()
            .address(Address.of("서울시 중구 1번길 10", "현대아파트 101호", "11111"))
            .build();
    }

    private User createUser() {
        return User.builder()
            .nickname("test")
            .build();
    }

}