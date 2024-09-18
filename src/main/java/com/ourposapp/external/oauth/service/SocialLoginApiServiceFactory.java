package com.ourposapp.external.oauth.service;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.ourposapp.domain.user.constant.LoginType;

@Component
public class SocialLoginApiServiceFactory {

    private static Map<String, SocialLoginApiService> socialLoginApiServices;

    public SocialLoginApiServiceFactory(Map<String, SocialLoginApiService> socialLoginApiServices) {
        SocialLoginApiServiceFactory.socialLoginApiServices = socialLoginApiServices;
    }

    public static SocialLoginApiService getSocialLoginApiService(LoginType loginType) {
        String socialLoginApiBeanName = switch (loginType) {
            case KAKAO -> "kakaoLoginApiServiceImpl";
            case NAVER -> "naverLoginApiServiceImpl";
        };

        return socialLoginApiServices.get(socialLoginApiBeanName);
    }
}
