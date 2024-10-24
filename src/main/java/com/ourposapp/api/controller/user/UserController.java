package com.ourposapp.api.controller.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ourposapp.api.controller.user.response.UserInfoResponse;
import com.ourposapp.api.service.user.UserInfoService;
import com.ourposapp.support.login.Login;
import com.ourposapp.support.login.UserInfo;
import com.ourposapp.support.response.Result;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "user", description = "회원 API")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserInfoService userInfoService;

    @Tag(name = "user")
    @Operation(summary = "회원 정보 조회 API", description = "회원 정보 조회 API")
    @ApiResponses({
            @ApiResponse(responseCode = "500", description = "서버 오류 발생"),
            @ApiResponse(responseCode = "C-003", description = "해당 회원을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "C-004", description = "휴대폰 인증을 완료해 주세요."),
    })
    @GetMapping("/info")
    public Result<UserInfoResponse> getUserInfo(@Login UserInfo userInfo) {
        Long userId = userInfo.getUserId();
        UserInfoResponse userInfoResponse = userInfoService.getUserInfo(userId);

        return Result.of(userInfoResponse, "회원 정보 확인");
    }

}
