package com.ourposapp.user.application.user;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import jakarta.persistence.EntityManager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.ourposapp.common.model.Address;
import com.ourposapp.global.error.ErrorCode;
import com.ourposapp.global.error.exception.EntityNotFoundException;
import com.ourposapp.global.error.exception.InvalidAddressException;
import com.ourposapp.user.application.user.dto.UserAddressRequestDto;
import com.ourposapp.user.application.user.dto.UserAddressResponseDto;
import com.ourposapp.user.domain.user.entity.User;
import com.ourposapp.user.domain.user.entity.UserAddress;
import com.ourposapp.user.domain.user.repository.UserRepository;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class UserAddressServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private UserAddressService userAddressService;

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
        userAddressService.changeDefaultUserAddress(user.getId(), userAddress2.getId());
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
                () -> userAddressService.changeDefaultUserAddress(user1.getId(), userAddress4.getId()))
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
        assertThatThrownBy(() -> userAddressService.changeDefaultUserAddress(user.getId(), userAddress1.getId()))
                .isInstanceOf(InvalidAddressException.class)
                .hasMessage("이미 기본주소로 설정되어 있습니다.");
    }

    @DisplayName("회원은 회원의 주소를 저장할 수 있다.")
    @Test
    void addUserAddress() {
        // given
        User user = createUser("user");
        userRepository.save(user);

        UserAddressRequestDto userAddressRequestDto1 = createUserAddressRequestDto(user);
        UserAddressRequestDto userAddressRequestDto2 = createUserAddressRequestDto(user);

        // when
        userAddressService.addUserAddress(user.getId(), userAddressRequestDto1);
        userAddressService.addUserAddress(user.getId(), userAddressRequestDto2);
        List<UserAddressResponseDto> userAddresses = userAddressService.findUserAddressesByUserId(user.getId());

        // then
        assertThat(userAddresses).hasSize(2)
                .extracting("defaultYn")
                .containsExactly(true, false);
    }

    @DisplayName("회원은 회원의 주소를 삭제할 수 있다.")
    @Test
    void deleteUserAddress() {
        // given
        User user = createUser("user");
        userRepository.save(user);

        UserAddressRequestDto userAddressRequestDto1 = createUserAddressRequestDto(user);
        UserAddressRequestDto userAddressRequestDto2 = createUserAddressRequestDto(user);
        userAddressService.addUserAddress(user.getId(), userAddressRequestDto1);
        userAddressService.addUserAddress(user.getId(), userAddressRequestDto2);

        // when
        Long userAddressId = userAddressService.findUserAddressesByUserId(user.getId()).get(1).getUserAddressId();
        userAddressService.deleteUserAddress(user.getId(), userAddressId);

        // then
        assertThat(userAddressService.findUserAddressesByUserId(user.getId())).hasSize(2)
                .extracting("deleteYn")
                .containsExactly(false, true);
    }

    @DisplayName("회원은 회원의 기본 주소를 삭제할 수 없다.")
    @Test
    void deleteDeleteUserAddressException() {
        // given
        User user = createUser("user");
        userRepository.save(user);

        UserAddressRequestDto userAddressRequestDto1 = createUserAddressRequestDto(user);
        userAddressService.addUserAddress(user.getId(), userAddressRequestDto1);

        // when
        UserAddressResponseDto defaultUserAddress = userAddressService.findDefaultUserAddress(user.getId());

        // then
        assertThatThrownBy(() -> userAddressService.deleteUserAddress(user.getId(), defaultUserAddress.getUserAddressId()))
                .isInstanceOf(InvalidAddressException.class)
                .hasMessage("기본 주소는 삭제할 수 없습니다.");
    }


    private UserAddressRequestDto createUserAddressRequestDto(User user) {
        return UserAddressRequestDto.builder()
                .userId(user.getId())
                .phoneNumber("01000000000")
                .address1("address1")
                .address2("address2")
                .zipcode("12345")
                .addressName("addressName")
                .receiverName("receiverName")
                .build();
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
