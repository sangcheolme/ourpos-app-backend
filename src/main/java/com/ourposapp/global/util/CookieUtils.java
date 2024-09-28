package com.ourposapp.global.util;

import java.util.Arrays;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseCookie;

import com.ourposapp.global.error.ErrorCode;
import com.ourposapp.global.error.exception.AuthenticationException;

public class CookieUtils {

    public static final String ACCESS_TOKEN_COOKIE_NAME = "access_token";
    public static final String REFRESH_TOKEN_COOKIE_NANE = "refresh_token";

    public static ResponseCookie createAccessToken(String accessToken) {
        return ResponseCookie.from(ACCESS_TOKEN_COOKIE_NAME, accessToken)
                .httpOnly(true)
                .path("/")
                .build();
    }

    public static ResponseCookie createRefreshToken(String refreshToken) {
        return ResponseCookie.from(REFRESH_TOKEN_COOKIE_NANE, refreshToken)
                .httpOnly(true)
                .path("/")
                .build();
    }

    public static String getAccessToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            throw new AuthenticationException(ErrorCode.NOT_EXIST_AUTHORIZATION);
        }

        return Arrays.stream(cookies)
                .filter(cookie -> ACCESS_TOKEN_COOKIE_NAME.equals(cookie.getName()))
                .findFirst()
                .orElseThrow(() -> new AuthenticationException(ErrorCode.NOT_EXIST_AUTHORIZATION)).getValue();
    }

    public static String getRefreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            throw new AuthenticationException(ErrorCode.NOT_EXIST_AUTHORIZATION);
        }

        return Arrays.stream(cookies)
                .filter(cookie -> REFRESH_TOKEN_COOKIE_NANE.equals(cookie.getName()))
                .findFirst()
                .orElseThrow(() -> new AuthenticationException(ErrorCode.NOT_EXIST_AUTHORIZATION)).getValue();
    }
}
