package com.ourposapp.user.application.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.ourposapp.user.application.user.dto.UserInfoResponseDto;
import com.ourposapp.user.domain.user.entity.User;

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
