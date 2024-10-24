package com.ourposapp.infra.oauth.naver.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.ourposapp.infra.oauth.naver.dto.NaverUserInfoResponse;

@FeignClient(url = "https://openapi.naver.com", name = "naverUserInfoClient")
public interface NaverUserInfoClient {

    @GetMapping(value = "/v1/nid/me", consumes = "application/json")
    NaverUserInfoResponse getNaverUserInfo(@RequestHeader("Authorization") String accessToken);
}
