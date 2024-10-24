package com.ourposapp.support.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.ourposapp.domain.user.Role;
import com.ourposapp.support.error.ErrorCode;
import com.ourposapp.support.error.exception.AuthenticationException;
import com.ourposapp.support.jwt.service.TokenManager;
import com.ourposapp.support.util.AuthorizationCookieUtils;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AdminAuthorizationInterceptor implements HandlerInterceptor {

    private final TokenManager tokenManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken = AuthorizationCookieUtils.getAccessToken(request);
        Claims tokenClaims = tokenManager.getTokenClaims(accessToken);
        String role = (String) tokenClaims.get("role");
        if (!Role.ROLE_ADMIN.equals(Role.from(role))) {
            throw new AuthenticationException(ErrorCode.FORBIDDEN_ADMIN);
        }
        return true;
    }
}
