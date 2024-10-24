package com.ourposapp.support.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

import com.ourposapp.support.error.ErrorCode;
import com.ourposapp.support.error.exception.AuthenticationException;
import com.ourposapp.support.jwt.constant.TokenType;
import com.ourposapp.support.jwt.service.TokenManager;
import com.ourposapp.support.util.AuthorizationCookieUtils;

@RequiredArgsConstructor
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final TokenManager tokenManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken = AuthorizationCookieUtils.getAccessToken(request);
        Claims tokenClaims = tokenManager.getTokenClaims(accessToken);
        String tokenType = tokenClaims.getSubject();
        if (!TokenType.isAccessToken(tokenType)) {
            throw new AuthenticationException(ErrorCode.NOT_ACCESS_TOKEN_TYPE);
        }
        return true;
    }
}
