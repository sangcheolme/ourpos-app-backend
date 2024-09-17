package com.ourposapp.domain.user.service;

import java.util.Optional;

import com.ourposapp.domain.user.entity.User;

public interface UserService {

    void register(User user);

    void updatePhoneNumber(Long userId, String phoneNumber);

    void changeDefaultUserAddress(Long userId, Long userAddressId);

    User findUserById(Long userId);

    Optional<User> findUserByUsername(String username);

    User findUserByRefreshToken(String refreshToken);
}
