package com.ourposapp.external.oauth.naver.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ourposapp.user.domain.user.constant.LoginType;
import com.ourposapp.external.oauth.model.OAuthAttributes;
import com.ourposapp.external.oauth.naver.client.NaverUserInfoClient;
import com.ourposapp.external.oauth.naver.dto.NaverUserInfoResponseDto;
import com.ourposapp.external.oauth.service.SocialLoginApiService;
import com.ourposapp.global.jwt.constant.GrantType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class NaverLoginApiServiceImpl implements SocialLoginApiService {
    private static final String NAVER_PREFIX = "naver_";

    private final NaverUserInfoClient naverUserInfoClient;

    @Override
    public OAuthAttributes getUserInfo(String accessToken) {
        NaverUserInfoResponseDto naverUserInfo = naverUserInfoClient.getNaverUserInfo(
            GrantType.BEARER.getType() + " " + accessToken
        );

        NaverUserInfoResponseDto.Response naverAccount = naverUserInfo.getResponse();
        String username = NAVER_PREFIX + naverAccount.getId();

        return OAuthAttributes.builder()
            .username(username)
            .nickname(naverAccount.getNickname())
            .profile(naverAccount.getProfileImage())
            .loginType(LoginType.NAVER)
            .build();
    }
}
