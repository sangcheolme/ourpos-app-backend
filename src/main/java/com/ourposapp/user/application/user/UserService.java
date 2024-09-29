package com.ourposapp.user.application.user;

import java.util.Optional;

import com.ourposapp.user.domain.user.entity.User;

public interface UserService {

    void register(User user);

    void updatePhoneNumber(Long userId, String phoneNumber);

    User findUserById(Long userId);

    Optional<User> findUserByUsername(String username);

    User findUserByRefreshToken(String refreshToken);
}
