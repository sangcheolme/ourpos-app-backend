package com.ourposapp.infra.oauth.naver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NaverUserInfoResponse {

    @JsonProperty("response")
    private Response response;

    @Getter
    @Setter
    public static class Response {
        private String id;
        private String nickname;

        @JsonProperty("profile_image")
        private String profileImage;
    }
}
