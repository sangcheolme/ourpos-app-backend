package com.ourposapp.api.service.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ourposapp.domain.user.User;
import com.ourposapp.api.controller.user.response.UserInfoResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserInfoService {

    private final UserService userService;

    public UserInfoResponse getUserInfo(Long userId) {
        User user = userService.findUserById(userId);
        return UserInfoResponse.of(user);
    }
}
