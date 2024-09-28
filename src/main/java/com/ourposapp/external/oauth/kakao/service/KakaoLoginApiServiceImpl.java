package com.ourposapp.external.oauth.kakao.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ourposapp.external.oauth.kakao.client.KakaoUserInfoClient;
import com.ourposapp.external.oauth.kakao.dto.KakaoUserInfoResponseDto;
import com.ourposapp.external.oauth.model.OAuthAttributes;
import com.ourposapp.external.oauth.service.SocialLoginApiService;
import com.ourposapp.global.jwt.constant.GrantType;
import com.ourposapp.user.domain.user.constant.LoginType;

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
        KakaoUserInfoResponseDto kakaoUserInfoResponseDto = kakaoUserInfoClient.getKakaoUserInfo(
                CONTENT_TYPE,
                GrantType.BEARER.getType() + " " + accessToken,
                true
        );

        KakaoUserInfoResponseDto.KakaoAccount kakaoAccount = kakaoUserInfoResponseDto.getKakaoAccount();
        String username = KAKAO_PREFIX + kakaoUserInfoResponseDto.getId();

        return OAuthAttributes.builder()
                .username(username)
                .nickname(kakaoAccount.getProfile().getNickname())
                .profile(kakaoAccount.getProfile().getThumbnailImageUrl())
                .loginType(LoginType.KAKAO)
                .build();
    }
}
