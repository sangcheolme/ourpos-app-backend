package com.ourposapp.domain.user;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ourposapp.domain.common.Address;
import com.ourposapp.domain.user.entity.User;
import com.ourposapp.domain.user.entity.UserAddress;
import com.ourposapp.global.error.exception.InvalidAddressException;

class UserAddressTest {

    @DisplayName("회원은 주소를 추가할 수 있고, 첫 번째로 추가한 주소는 기본주소가 된다.")
    @Test
    void addUserAddress_default() {
        // given
        User user = createUser();
        UserAddress userAddress = createUserAddress();

        // when
        user.addUserAddress(userAddress);

        // then
        assertThat(user.getUserAddresses().get(0).getDefaultYn()).isTrue();
    }

    @DisplayName("회원은 주소를 추가할 수 있고, 이미 기본 주소가 있다면 기본 주소 여부는 false가 된다.")
    @Test
    void addUserAddress_non_default() {
        // given
        User user = createUser();
        UserAddress userAddress1 = createUserAddress();
        UserAddress userAddress2 = createUserAddress();
        UserAddress userAddress3 = createUserAddress();

        // when
        user.addUserAddress(userAddress1);
        user.addUserAddress(userAddress2);
        user.addUserAddress(userAddress3);

        // then
        assertThat(user.getUserAddresses().get(0).getDefaultYn()).isTrue();
        assertThat(user.getUserAddresses().get(1).getDefaultYn()).isFalse();
        assertThat(user.getUserAddresses().get(2).getDefaultYn()).isFalse();
    }

    @DisplayName("회원은 주소를 최대 3개까지 추가할 수 있고, 주소가 3개가 넘는 경우 예외가 터진다.")
    @Test
    void addUserAddress_ex() {
        // given
        User user = createUser();
        UserAddress userAddress1 = createUserAddress();
        UserAddress userAddress2 = createUserAddress();
        UserAddress userAddress3 = createUserAddress();
        UserAddress userAddress4 = createUserAddress();

        // when
        user.addUserAddress(userAddress1);
        user.addUserAddress(userAddress2);
        user.addUserAddress(userAddress3);

        // then
        assertThatThrownBy(() -> user.addUserAddress(userAddress4))
                .isInstanceOf(InvalidAddressException.class);
    }

    @DisplayName("나의 기본 주소 가져오기")
    @Test
    void getDefaultAddress() {
        // given
        User user = createUser();
        UserAddress userAddress1 = createUserAddress();
        UserAddress userAddress2 = createUserAddress();

        user.addUserAddress(userAddress1);
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
