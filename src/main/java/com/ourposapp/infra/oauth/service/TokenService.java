package com.ourposapp.infra.oauth.service;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ourposapp.api.service.user.UserService;
import com.ourposapp.domain.user.User;
import com.ourposapp.support.jwt.constant.GrantType;
import com.ourposapp.support.jwt.service.TokenManager;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class TokenService {

    private final UserService userService;
    private final TokenManager tokenManager;

    public AccessTokenResponse createAccessTokenByRefreshToken(String refreshToken) {
        User user = userService.findUserByRefreshToken(refreshToken);

        Date accessTokenExpireTime = tokenManager.createAccessTokenExpireTime();
        String accessToken = tokenManager.createAccessToken(user.getId(), user.getRole(), user.getIsPhoneVerified(), accessTokenExpireTime);
        return AccessTokenResponse.builder()
                .grantType(GrantType.BEARER.getType())
                .accessToken(accessToken)
                .accessTokenExpireTime(accessTokenExpireTime)
                .build();
    }
}
