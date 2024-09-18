package com.ourposapp.global.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.ourposapp.global.error.ErrorCode;
import com.ourposapp.global.error.exception.IncompleteProfileException;
import com.ourposapp.global.jwt.service.TokenManager;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CheckProfileCompleteInterceptor implements HandlerInterceptor {

    private final TokenManager tokenManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorizationHeader = request.getHeader("Authorization");
        String accessToken = authorizationHeader.split(" ")[1];

        Claims tokenClaims = tokenManager.getTokenClaims(accessToken);
        Boolean isPhoneVerified = (Boolean)tokenClaims.get("isPhoneVerified");

        if (!isPhoneVerified) {
            throw new IncompleteProfileException(ErrorCode.PHONE_VERIFICATION_REQUIRED);
        }

        return true;
    }
}
