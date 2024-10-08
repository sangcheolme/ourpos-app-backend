package com.ourposapp.user.presentation.docs;

import java.io.IOException;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "authentication", description = "로그인 / 로그아웃 / 토큰재발급 API")
public interface OAuthKakaoControllerDocs {

    @Tag(name = "authentication")
    @Operation(summary = "카카오 소셜 로그인 API", description = "카카오 소셜 로그인 페이지 요청 API")
    @GetMapping("/authorization/kakao")
    void kakaoLogin(HttpServletResponse response) throws IOException;

    @Tag(name = "authentication")
    @Operation(summary = "카카오 소셜 로그인 API", description = "카카오 소셜 로그인 콜백 요청 구현 API")
    @GetMapping("/code/kakao")
    void kakaoLoginCallback(String code, HttpServletResponse response) throws IOException;
}
