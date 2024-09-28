package com.ourposapp.global.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.ourposapp.global.error.ErrorCode;
import com.ourposapp.global.error.exception.AuthenticationException;
import com.ourposapp.global.jwt.service.TokenManager;
import com.ourposapp.global.util.CookieUtils;
import com.ourposapp.user.domain.user.constant.Role;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AdminAuthorizationInterceptor implements HandlerInterceptor {

    private final TokenManager tokenManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
            Exception {
        String accessToken = CookieUtils.getAccessToken(request);
        Claims tokenClaims = tokenManager.getTokenClaims(accessToken);
        String role = (String) tokenClaims.get("role");
        if (!Role.ROLE_ADMIN.equals(Role.from(role))) {
            throw new AuthenticationException(ErrorCode.FORBIDDEN_ADMIN);
        }
        return true;
    }
}
