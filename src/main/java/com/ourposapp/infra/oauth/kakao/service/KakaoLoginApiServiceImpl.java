package com.ourposapp.infra.oauth.kakao.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ourposapp.domain.user.LoginType;
import com.ourposapp.infra.oauth.kakao.client.KakaoUserInfoClient;
import com.ourposapp.infra.oauth.kakao.dto.KakaoUserInfoResponse;
import com.ourposapp.infra.oauth.model.OAuthAttributes;
import com.ourposapp.infra.oauth.service.SocialLoginApiService;
import com.ourposapp.support.jwt.constant.GrantType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class KakaoLoginApiServiceImpl implements SocialLoginApiService {
    private static final String KAKAO_PREFIX = "kakao_";
    private static final String CONTENT_TYPE = "application/x-www-form-urlencoded;charset=utf-8";

    private final KakaoUserInfoClient kakaoUserInfoClient;

    @Override
    public OAuthAttributes getUserInfo(String accessToken) {
        KakaoUserInfoResponse kakaoUserInfoResponse = kakaoUserInfoClient.getKakaoUserInfo(
                CONTENT_TYPE,
                GrantType.BEARER.getType() + " " + accessToken,
                true
        );

        KakaoUserInfoResponse.KakaoAccount kakaoAccount = kakaoUserInfoResponse.getKakaoAccount();
        String username = KAKAO_PREFIX + kakaoUserInfoResponse.getId();

        return OAuthAttributes.builder()
                .username(username)
                .nickname(kakaoAccount.getProfile().getNickname())
                .profile(kakaoAccount.getProfile().getThumbnailImageUrl())
                .loginType(LoginType.KAKAO)
                .build();
    }
}
