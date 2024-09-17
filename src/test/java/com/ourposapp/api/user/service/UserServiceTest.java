package com.ourposapp.api.user.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import jakarta.persistence.EntityManager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.ourposapp.domain.common.Address;
import com.ourposapp.domain.user.entity.User;
import com.ourposapp.domain.user.entity.UserAddress;
import com.ourposapp.domain.user.repository.UserRepository;
import com.ourposapp.domain.user.service.UserService;
import com.ourposapp.global.error.ErrorCode;
import com.ourposapp.global.error.exception.EntityNotFoundException;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager em;

    @DisplayName("회원은 기본 주소를 변경할 수 있다.")
    @Test
    void changeDefaultUserAddress() {
        // given
        UserAddress userAddress1 = createUserAddress();
        UserAddress userAddress2 = createUserAddress();
        UserAddress userAddress3 = createUserAddress();

        User user = createUser("testId");
        user.addUserAddress(userAddress1);
        user.addUserAddress(userAddress2);
        user.addUserAddress(userAddress3);

        userRepository.save(user);
        clearPersistenceContext();
        
        // when
        // 기본주소 변경: 1 -> 2
        userService.changeDefaultUserAddress(user.getId(), userAddress2.getId());
        clearPersistenceContext();

        List<UserAddress> userAddresses = userRepository.findUserAddresses(user.getId());
        UserAddress findUserAddress1 = findUserAddress(userAddresses, userAddress1.getId());
        UserAddress findUserAddress2 = findUserAddress(userAddresses, userAddress2.getId());
        UserAddress findUserAddress3 = findUserAddress(userAddresses, userAddress3.getId());

        // then
        assertThat(findUserAddress1.getDefaultYn()).isFalse();
        assertThat(findUserAddress2.getDefaultYn()).isTrue();
        assertThat(findUserAddress3.getDefaultYn()).isFalse();
    }
    
    @DisplayName("회원은 본인의 주소가 아닌 주소를 기본 주소로 변경하려 하면 예외가 발생한다.")
    @Test
    void changDefaultUserAddress_ex_notMyAddress() {
        // given
        UserAddress userAddress1 = createUserAddress();
        UserAddress userAddress2 = createUserAddress();
        User user1 = createUser("testId1");
        user1.addUserAddress(userAddress1);
        user1.addUserAddress(userAddress2);

        UserAddress userAddress4 = createUserAddress();
        User user2 = createUser("testId2");
        user2.addUserAddress(userAddress4);

        // when
        userRepository.save(user1);
        userRepository.save(user2);

        // then
        assertThatThrownBy(
            () -> userService.changeDefaultUserAddress(user1.getId(), userAddress4.getId()))
            .isInstanceOf(EntityNotFoundException.class)
            .hasMessage(ErrorCode.USER_ADDRESS_NOT_EXIST.getMessage());
    }
    
    @DisplayName("이미 기본 주소인 주소를 기본 주소로 변경하려고 하면 예외가 발생한다.")
    @Test
    void changDefaultUserAddress_ex_alreadyDefault() {
        // given
        UserAddress userAddress1 = createUserAddress();
        UserAddress userAddress2 = createUserAddress();

        User user = createUser("testId1");
        user.addUserAddress(userAddress1);
        user.addUserAddress(userAddress2);

        // when
        userRepository.save(user);

        // then
        assertThatThrownBy(
            () -> userService.changeDefaultUserAddress(user.getId(), userAddress1.getId()))
            .isInstanceOf(IllegalArgumentException.class);
    }

    private UserAddress findUserAddress(List<UserAddress> userAddresses, Long userId) {
        return userAddresses.stream()
            .filter(userAddress -> userAddress.getId().equals(userId))
            .findFirst()
            .orElseThrow();
    }

    private UserAddress createUserAddress() {
        return UserAddress.builder()
            .address(Address.of("서울시 중구 1번길 10", "현대아파트 101호", "11111"))
            .build();
    }

    private User createUser(String nickname) {
        return User.builder()
            .nickname(nickname)
            .build();
    }

    private void clearPersistenceContext() {
        em.flush();
        em.clear();
    }

}