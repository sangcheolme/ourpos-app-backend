package com.ourposapp.infra.oauth.naver.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ourposapp.domain.user.LoginType;
import com.ourposapp.infra.oauth.model.OAuthAttributes;
import com.ourposapp.infra.oauth.naver.client.NaverUserInfoClient;
import com.ourposapp.infra.oauth.naver.dto.NaverUserInfoResponse;
import com.ourposapp.infra.oauth.service.SocialLoginApiService;
import com.ourposapp.support.jwt.constant.GrantType;

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
        NaverUserInfoResponse naverUserInfo = naverUserInfoClient.getNaverUserInfo(
                GrantType.BEARER.getType() + " " + accessToken
        );

        NaverUserInfoResponse.Response naverAccount = naverUserInfo.getResponse();
        String username = NAVER_PREFIX + naverAccount.getId();

        return OAuthAttributes.builder()
                .username(username)
                .nickname(naverAccount.getNickname())
                .profile(naverAccount.getProfileImage())
                .loginType(LoginType.NAVER)
                .build();
    }
}
