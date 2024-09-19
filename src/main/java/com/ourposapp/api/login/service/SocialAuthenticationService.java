package com.ourposapp.api.login.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ourposapp.api.login.dto.AuthTokenDto;
import com.ourposapp.domain.user.constant.LoginType;
import com.ourposapp.domain.user.constant.Role;
import com.ourposapp.domain.user.entity.User;
import com.ourposapp.domain.user.service.UserService;
import com.ourposapp.external.oauth.model.OAuthAttributes;
import com.ourposapp.external.oauth.service.SocialLoginApiService;
import com.ourposapp.external.oauth.service.SocialLoginApiServiceFactory;
import com.ourposapp.global.jwt.dto.JwtTokenDto;
import com.ourposapp.global.jwt.service.TokenManager;

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
        jwtTokenDto = tokenManager.createJwtTokenDto(oauthUser.getId(), oauthUser.getRole(), oauthUser.getIsPhoneVerified());
        oauthUser.updateRefreshToken(jwtTokenDto);

        return AuthTokenDto.Response.of(jwtTokenDto);
    }
}
