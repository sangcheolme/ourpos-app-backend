package com.ourposapp.infra.oauth.kakao.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.ourposapp.infra.oauth.kakao.dto.KakaoUserInfoResponse;

@FeignClient(url = "https://kapi.kakao.com", name = "kakaoUserInfoClient")
public interface KakaoUserInfoClient {

    @GetMapping(value = "/v2/user/me", consumes = "application/json")
    KakaoUserInfoResponse getKakaoUserInfo(@RequestHeader("Content-Type") String contentType,
                                           @RequestHeader("Authorization") String accessToken,
                                           @RequestParam("secure_resource") Boolean secure);
}
