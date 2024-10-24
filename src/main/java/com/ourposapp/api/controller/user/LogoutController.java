package com.ourposapp.api.controller.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ourposapp.infra.oauth.service.AuthenticationService;
import com.ourposapp.support.response.Result;
import com.ourposapp.support.util.AuthorizationCookieUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "authentication", description = "로그인 / 로그아웃 / 토큰재발급 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class LogoutController {

    private final AuthenticationService authenticationService;

    @Tag(name = "authentication")
    @Operation(summary = "로그아웃 API", description = "로그아웃시 refresh token 만료 처리")
    @PostMapping("/logout")
    public Result<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        String accessToken = AuthorizationCookieUtils.getAccessToken(request);
        authenticationService.logout(accessToken);

        ResponseCookie logoutAccessCookie = AuthorizationCookieUtils.createLogoutAccessToken();
        ResponseCookie logoutRefreshToken = AuthorizationCookieUtils.createLogoutRefreshToken();
        response.addHeader("Set-Cookie", logoutAccessCookie.toString());
        response.addHeader("Set-Cookie", logoutRefreshToken.toString());

        return Result.of("회원이 로그아웃 되었습니다.");
    }
}
