package com.ourposapp.api.controller.user;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.ourposapp.support.util.AuthorizationCookieUtils;
import com.ourposapp.infra.oauth.service.TokenService;
import com.ourposapp.infra.oauth.service.AccessTokenResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/access-token/issue")
    public void createAccessToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String refreshToken = AuthorizationCookieUtils.getRefreshToken(request);
        AccessTokenResponse accessTokenResponse = tokenService.createAccessTokenByRefreshToken(refreshToken);
        ResponseCookie accessToken = AuthorizationCookieUtils.createAccessToken(accessTokenResponse.getAccessToken());

        response.addHeader("Set-Cookie", accessToken.toString());
    }

}
