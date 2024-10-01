package com.ourposapp.user.presentation.docs;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "authentication", description = "로그인 / 로그아웃 / 토큰재발급 API")
public interface LogoutControllerDocs {

    @Tag(name = "authentication")
    @Operation(summary = "로그아웃 API", description = "로그아웃시 refresh token 만료 처리")
    @PostMapping("/logout")
    ResponseEntity<Void> logout(HttpServletRequest request);
}
