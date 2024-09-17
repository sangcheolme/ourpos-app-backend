package com.ourposapp.api.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ourposapp.api.user.dto.UserInfoResponseDto;
import com.ourposapp.domain.user.entity.User;
import com.ourposapp.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserInfoService {

    private final UserService userService;

    public UserInfoResponseDto getUserInfo(Long userId) {
        User user = userService.findUserById(userId);
        return UserInfoResponseDto.of(user);
    }
}
