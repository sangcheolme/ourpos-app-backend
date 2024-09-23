package com.ourposapp.domain.user;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.ourposapp.common.model.Address;
import com.ourposapp.common.model.Phone;
import com.ourposapp.user.domain.user.entity.User;
import com.ourposapp.user.domain.user.entity.UserAddress;
import com.ourposapp.user.domain.user.repository.UserRepository;

@Transactional
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @DisplayName("회원 주소 조회")
    @Test
    void findUserAddresses() {
        // given
        UserAddress userAddress1 = createUserAddress();
        UserAddress userAddress2 = createUserAddress();
        UserAddress userAddress3 = createUserAddress();

        User user = createUser();
        user.addUserAddress(userAddress1);
        user.addUserAddress(userAddress2);
        user.addUserAddress(userAddress3);

        userRepository.save(user);

        // when
        List<UserAddress> userAddresses = userRepository.findUserAddresses(user.getId());

        // then
        assertThat(userAddresses).hasSize(3)
            .extracting("defaultYn")
            .containsExactly(true, false, false);
    }

    @DisplayName("핸드폰 번호를 통해 회원의 존재 여부를 조회할 수 있다.")
    @Test
    void findByPhone() {
        // given
        Phone phone = Phone.of("01012341234");
        User user = User.builder()
            .phone(phone)
            .build();
        userRepository.save(user);

        // when
        boolean isExists = userRepository.existsByPhone(Phone.of("01012341234"));

        // then
        assertThat(isExists).isTrue();
    }

    @DisplayName("username을 통해 회원의 존재 여부를 조회할 수 있다.")
    @Test
    void existsByUsername() {
        // given
        String username = "hello";
        User user = User.builder()
            .username(username)
            .build();
        userRepository.save(user);

        // when
        User findUser = userRepository.findByUsername("hello").get();

        // then
        assertThat(findUser).isEqualTo(user);
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
