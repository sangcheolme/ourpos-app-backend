package com.ourposapp.user.presentation.docs;

import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.ourposapp.global.resolver.login.Login;
import com.ourposapp.global.resolver.login.UserInfoDto;
import com.ourposapp.global.response.Result;
import com.ourposapp.user.application.user.dto.UserInfoResponseDto;

@Tag(name = "user", description = "회원 API")
public interface UserControllerDocs {

    @Tag(name = "user")
    @Operation(summary = "회원 정보 조회 API", description = "회원 정보 조회 API")
    @ApiResponses({
            @ApiResponse(responseCode = "500", description = "서버 오류 발생"),
            @ApiResponse(responseCode = "C-003", description = "해당 회원을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "C-004", description = "휴대폰 인증을 완료해 주세요."),
    })
    @GetMapping("/info")
    Result<UserInfoResponseDto> getUserInfo(@Login UserInfoDto userInfoDto);
}
