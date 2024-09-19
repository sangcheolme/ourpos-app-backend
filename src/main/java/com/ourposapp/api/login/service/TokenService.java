package com.ourposapp.api.login.service;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ourposapp.api.login.dto.AccessTokenResponseDto;
import com.ourposapp.domain.user.entity.User;
import com.ourposapp.domain.user.service.UserService;
import com.ourposapp.global.jwt.constant.GrantType;
import com.ourposapp.global.jwt.service.TokenManager;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class TokenService {

    private final UserService userService;
    private final TokenManager tokenManager;

    public AccessTokenResponseDto createAccessTokenByRefreshToken(String refreshToken) {
        User user = userService.findUserByRefreshToken(refreshToken);

        Date accessTokenExpireTime = tokenManager.createAccessTokenExpireTime();
        String accessToken = tokenManager.createAccessToken(user.getId(), user.getRole(), user.getIsPhoneVerified(), accessTokenExpireTime);
        return AccessTokenResponseDto.builder()
            .grantType(GrantType.BEARER.getType())
            .accessToken(accessToken)
            .accessTokenExpireTime(accessTokenExpireTime)
            .build();
    }
}
