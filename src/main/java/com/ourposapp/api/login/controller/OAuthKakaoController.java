package com.ourposapp.api.login.controller;

import java.io.IOException;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ourposapp.api.login.dto.AuthTokenDto;
import com.ourposapp.api.login.service.AuthenticationService;
import com.ourposapp.domain.user.constant.LoginType;
import com.ourposapp.external.oauth.kakao.client.KakaoTokenClient;
import com.ourposapp.external.oauth.kakao.dto.KakaoTokenDto;
import com.ourposapp.global.util.CookieUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@PropertySource("classpath:config.properties")
@RestController
@RequestMapping("/login/oauth2")
public class OAuthKakaoController implements OAuthKakaoControllerDocs {
    private static final String AUTHORIZATION_CODE = "authorization_code";
    private static final String CONTENT_TYPE = "application/x-www-form-urlencoded;charset=utf-8";
    private static final String RESPONSE_TYPE = "code";

    @Value("${kakao.client.id}")
    private String clientId;

    @Value("${kakao.client.secret}")
    private String clientSecret;

    @Value("${kakao.client.redirect-uri}")
    private String redirectUri;

    private final KakaoTokenClient kakaoTokenClient;
    private final AuthenticationService authenticationService;

    @GetMapping("/authorization/kakao")
    public void kakaoLogin(HttpServletResponse response) throws IOException {
        String kakaoAuthUrl = UriComponentsBuilder.fromHttpUrl("https://kauth.kakao.com/oauth/authorize")
            .queryParam("client_id", clientId)
            .queryParam("redirect_uri", redirectUri)
            .queryParam("response_type", RESPONSE_TYPE)
            .build().toUriString();

        response.sendRedirect(kakaoAuthUrl);
    }

    @GetMapping("/code/kakao")
    public void kakaoLoginCallback(String code, HttpServletResponse response) throws IOException {
        KakaoTokenDto.Request kakaoTokenRequestDto = KakaoTokenDto.Request.builder()
            .grant_type(AUTHORIZATION_CODE)
            .client_id(clientId)
            .client_secret(clientSecret)
            .redirect_uri(redirectUri)
            .code(code)
            .build();

        KakaoTokenDto.Response tokenResponse = kakaoTokenClient.requestKakaoToken(CONTENT_TYPE, kakaoTokenRequestDto);
        String accessToken = tokenResponse.getAccess_token();

        AuthTokenDto.Response authTokenDto = authenticationService.authenticate(accessToken, LoginType.KAKAO);
        Cookie cookie = CookieUtils.create("refresh_token", authTokenDto.getRefreshToken(), 60 * 60 * 60);

        response.addCookie(cookie);
        response.sendRedirect("http://localhost:3000");
    }
}

