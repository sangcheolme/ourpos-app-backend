package com.ourposapp.api.controller.user;

import java.io.IOException;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ourposapp.domain.user.LoginType;
import com.ourposapp.infra.oauth.naver.client.NaverTokenClient;
import com.ourposapp.infra.oauth.naver.dto.NaverTokenDto;
import com.ourposapp.infra.oauth.service.AuthTokenDto;
import com.ourposapp.infra.oauth.service.AuthenticationService;
import com.ourposapp.support.util.AuthorizationCookieUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "authentication", description = "로그인 / 로그아웃 / 토큰재발급 API")
@RequiredArgsConstructor
@PropertySource("classpath:config.properties")
@RestController
@RequestMapping("/login/oauth2")
public class OAuthNaverController {
    private static final String AUTHORIZATION_CODE = "authorization_code";
    private static final String RESPONSE_TYPE = "code";

    @Value("${naver.client.id}")
    private String clientId;

    @Value("${naver.client.secret}")
    private String clientSecret;

    @Value("${naver.client.redirect-uri}")
    private String redirectUri;

    @Value("${client.base-url}")
    private String baseUrl;

    private final NaverTokenClient naverTokenClient;
    private final AuthenticationService authenticationService;

    @Tag(name = "authentication")
    @Operation(summary = "네이버 소셜 로그인 API", description = "네이버 소셜 로그인 페이지 요청 API")
    @GetMapping("/authorization/naver")
    public void naverLogin(HttpServletResponse response) throws IOException {
        String kakaoAuthUrl = UriComponentsBuilder.fromHttpUrl("https://nid.naver.com/oauth2.0/authorize")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("response_type", RESPONSE_TYPE)
                .queryParam("state", "test")
                .build().toUriString();

        response.sendRedirect(kakaoAuthUrl);
    }

    @Tag(name = "authentication")
    @Operation(summary = "네이버 소셜 로그인 API", description = "네이버 소셜 로그인 콜백 요청 구현 API")
    @GetMapping("/code/naver")
    public void naverLoginCallback(String code, String state, HttpServletResponse response) throws IOException {
        NaverTokenDto.Request naverTokenRequest = NaverTokenDto.Request.builder()
                .grant_type(AUTHORIZATION_CODE)
                .client_id(clientId)
                .client_secret(clientSecret)
                .redirect_uri(redirectUri)
                .code(code)
                .state(state)
                .build();

        NaverTokenDto.Response tokenResponse = naverTokenClient.requestNaverToken(naverTokenRequest);
        String accessToken = tokenResponse.getAccess_token();

        AuthTokenDto.Response authTokenDto = authenticationService.authenticate(accessToken, LoginType.NAVER);

        ResponseCookie accessCookie = AuthorizationCookieUtils.createAccessToken(authTokenDto.getAccessToken());
        ResponseCookie refreshCookie = AuthorizationCookieUtils.createRefreshToken(authTokenDto.getRefreshToken());
        response.addHeader("Set-Cookie", accessCookie.toString());
        response.addHeader("Set-Cookie", refreshCookie.toString());
        response.sendRedirect(baseUrl);
    }

}
