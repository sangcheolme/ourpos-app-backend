package com.ourposapp.api.logout.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ourposapp.domain.user.entity.User;
import com.ourposapp.domain.user.service.UserService;
import com.ourposapp.global.error.ErrorCode;
import com.ourposapp.global.error.exception.AuthenticationException;
import com.ourposapp.global.jwt.constant.TokenType;
import com.ourposapp.global.jwt.service.TokenManager;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class LogoutService {

    private final UserService userService;
    private final TokenManager tokenManager;

    public void logout(String accessToken) {
        // 1. 토큰 검증
        tokenManager.validateToken(accessToken);

        // 2. 토큰 타입 확인
        Claims tokenClaims = tokenManager.getTokenClaims(accessToken);
        String tokenType = tokenClaims.getSubject();
        if (!TokenType.isAccessToken(tokenType)) {
            throw new AuthenticationException(ErrorCode.NOT_ACCESS_TOKEN_TYPE);
        }

        // 3. 토큰 만료 처리
        Long userId = Long.valueOf((Integer)tokenClaims.get("userId"));
        User user = userService.findUserById(userId);
        user.expireRefreshToken(LocalDateTime.now());
    }
}
