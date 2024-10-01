package com.ourposapp.user.presentation.auth;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ourposapp.global.util.AuthorizationCookieUtils;
import com.ourposapp.user.application.auth.TokenService;
import com.ourposapp.user.application.auth.dto.AccessTokenResponseDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/access-token/issue")
    public void createAccessToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String refreshToken = AuthorizationCookieUtils.getRefreshToken(request);
        AccessTokenResponseDto accessTokenResponseDto = tokenService.createAccessTokenByRefreshToken(refreshToken);
        ResponseCookie accessToken = AuthorizationCookieUtils.createAccessToken(accessTokenResponseDto.getAccessToken());

        response.addHeader("Set-Cookie", accessToken.toString());
    }

}
