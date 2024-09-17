package com.ourposapp.api.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ourposapp.api.user.dto.UserInfoResponseDto;
import com.ourposapp.api.user.service.UserInfoService;
import com.ourposapp.global.resolver.login.UserInfoDto;
import com.ourposapp.global.resolver.login.Login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController implements UserControllerDocs {

    private final UserInfoService userInfoService;

    @GetMapping("/info")
    public ResponseEntity<UserInfoResponseDto> getUserInfo(@Login UserInfoDto userInfoDto) {
        Long userId = userInfoDto.getUserId();
        return ResponseEntity.ok(userInfoService.getUserInfo(userId));
    }

}
