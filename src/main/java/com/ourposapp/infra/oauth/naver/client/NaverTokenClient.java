package com.ourposapp.infra.oauth.naver.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.PostMapping;

import com.ourposapp.infra.oauth.naver.dto.NaverTokenDto;

@FeignClient(url = "https://nid.naver.com", name = "naverTokenClient")
public interface NaverTokenClient {

    @PostMapping(value = "/oauth2.0/token", consumes = "application/json")
    NaverTokenDto.Response requestNaverToken(
            @SpringQueryMap NaverTokenDto.Request request
    );
}
