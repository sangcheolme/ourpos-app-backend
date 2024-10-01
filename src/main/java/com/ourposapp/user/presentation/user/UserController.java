package com.ourposapp.user.presentation.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.ourposapp.global.resolver.login.Login;
import com.ourposapp.global.resolver.login.UserInfoDto;
import com.ourposapp.global.response.Result;
import com.ourposapp.user.application.user.UserInfoService;
import com.ourposapp.user.application.user.dto.UserInfoResponseDto;
import com.ourposapp.user.presentation.docs.UserControllerDocs;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController implements UserControllerDocs {

    private final UserInfoService userInfoService;

    @GetMapping("/info")
    public Result<UserInfoResponseDto> getUserInfo(@Login UserInfoDto userInfoDto) {
        Long userId = userInfoDto.getUserId();
        UserInfoResponseDto userInfoResponse = userInfoService.getUserInfo(userId);

        return Result.of(userInfoResponse, "회원 정보 확인");
    }
}
