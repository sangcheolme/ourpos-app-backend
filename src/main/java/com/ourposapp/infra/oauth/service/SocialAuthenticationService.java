package com.ourposapp.infra.oauth.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ourposapp.api.service.user.UserService;
import com.ourposapp.domain.user.LoginType;
import com.ourposapp.domain.user.Role;
import com.ourposapp.domain.user.User;
import com.ourposapp.infra.oauth.model.OAuthAttributes;
import com.ourposapp.support.error.ErrorCode;
import com.ourposapp.support.error.exception.AuthenticationException;
import com.ourposapp.support.jwt.constant.TokenType;
import com.ourposapp.support.jwt.dto.JwtTokenDto;
import com.ourposapp.support.jwt.service.TokenManager;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class SocialAuthenticationService implements AuthenticationService {

    private final UserService userService;
    private final TokenManager tokenManager;

    @Override
    public AuthTokenDto.Response authenticate(String accessToken, LoginType loginType) {
        SocialLoginApiService socialLoginApiService = SocialLoginApiServiceFactory.getSocialLoginApiService(loginType);

        OAuthAttributes userInfo = socialLoginApiService.getUserInfo(accessToken);
        log.info("userInfo = {}", userInfo);

        JwtTokenDto jwtTokenDto;
        User oauthUser;
        Optional<User> optionalUser = userService.findUserByUsername(userInfo.getUsername());
        if (optionalUser.isEmpty()) { // 신규 회원
            oauthUser = userInfo.toUserEntity(Role.ROLE_USER);
            userService.register(oauthUser);
        } else { // 기존 회원
            oauthUser = optionalUser.get();
        }

        // 토큰 생성
        jwtTokenDto = tokenManager.createJwtTokenDto(oauthUser.getId(), oauthUser.getRole(),
                oauthUser.getIsPhoneVerified());
        oauthUser.updateRefreshToken(jwtTokenDto);

        return AuthTokenDto.Response.of(jwtTokenDto);
    }

    @Override
    public void logout(String accessToken) {
        tokenManager.validateToken(accessToken);

        Claims tokenClaims = tokenManager.getTokenClaims(accessToken);
        String tokenType = tokenClaims.getSubject();
        if (!TokenType.isAccessToken(tokenType)) {
            throw new AuthenticationException(ErrorCode.NOT_ACCESS_TOKEN_TYPE);
        }

        Long userId = Long.valueOf((Integer) tokenClaims.get("userId"));
        User user = userService.findUserById(userId);
        user.expireRefreshToken(LocalDateTime.now());
    }
}
