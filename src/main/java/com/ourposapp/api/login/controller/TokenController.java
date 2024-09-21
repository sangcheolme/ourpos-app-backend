package com.ourposapp.api.login.controller;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ourposapp.api.login.dto.AccessTokenResponseDto;
import com.ourposapp.api.login.service.TokenService;
import com.ourposapp.global.util.CookieUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/access-token/issue")
    public void createAccessToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String refreshToken = CookieUtils.getRefreshToken(request);
        AccessTokenResponseDto accessTokenResponseDto = tokenService.createAccessTokenByRefreshToken(refreshToken);
        ResponseCookie accessToken = CookieUtils.createAccessToken(accessTokenResponseDto.getAccessToken());

        response.addHeader("Set-Cookie", accessToken.toString());
    }

}
