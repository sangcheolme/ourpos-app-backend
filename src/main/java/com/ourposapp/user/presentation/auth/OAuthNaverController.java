package com.ourposapp.user.presentation.auth;

import java.io.IOException;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ourposapp.external.oauth.naver.client.NaverTokenClient;
import com.ourposapp.external.oauth.naver.dto.NaverTokenDto;
import com.ourposapp.global.util.CookieUtils;
import com.ourposapp.user.application.auth.AuthenticationService;
import com.ourposapp.user.application.auth.dto.AuthTokenDto;
import com.ourposapp.user.domain.user.constant.LoginType;
import com.ourposapp.user.presentation.docs.OAuthNaverControllerDocs;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@PropertySource("classpath:config.properties")
@RestController
@RequestMapping("/login/oauth2")
public class OAuthNaverController implements OAuthNaverControllerDocs {
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

    @GetMapping("/code/naver")
    public void naverLoginCallback(String code, String state, HttpServletResponse response) throws IOException {
        NaverTokenDto.Request naverTokenRequestDto = NaverTokenDto.Request.builder()
            .grant_type(AUTHORIZATION_CODE)
            .client_id(clientId)
            .client_secret(clientSecret)
            .redirect_uri(redirectUri)
            .code(code)
            .state(state)
            .build();

        NaverTokenDto.Response tokenResponse = naverTokenClient.requestNaverToken(naverTokenRequestDto);
        String accessToken = tokenResponse.getAccess_token();

        AuthTokenDto.Response authTokenDto = authenticationService.authenticate(accessToken, LoginType.NAVER);

        ResponseCookie accessCookie = CookieUtils.createAccessToken(authTokenDto.getAccessToken());
        ResponseCookie refreshCookie = CookieUtils.createRefreshToken(authTokenDto.getRefreshToken());
        response.addHeader("Set-Cookie", accessCookie.toString());
        response.addHeader("Set-Cookie", refreshCookie.toString());
        response.sendRedirect(baseUrl);
    }

}
