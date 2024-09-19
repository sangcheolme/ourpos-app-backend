package com.ourposapp.api.login.dto;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ourposapp.global.jwt.dto.JwtTokenDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class AuthTokenDto {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {

        @Schema(description = "grant type", example = "Bearer", requiredMode = REQUIRED)
        private String grantType;

        @Schema(description = "access token", example = "aslkjfl2k3as24jfeji1jdfwef==", requiredMode = REQUIRED)
        private String accessToken;

        @Schema(description = "access token 만료 시간", example = "2024-09-16 03:20:21", requiredMode = REQUIRED)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private Date accessTokenExpireTime;

        @Schema(description = "refresh token", example = "sdfwerjfl2k3as24jfeji1jdfwef==", requiredMode = REQUIRED)
        private String refreshToken;

        @Schema(description = "refresh token 만료 시간", example = "2024-09-30 03:20:21", requiredMode = REQUIRED)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private Date refreshTokenExpireTime;

        public static Response of(JwtTokenDto jwtTokenDto) {
            return Response.builder()
                .grantType(jwtTokenDto.getGrantType())
                .accessToken(jwtTokenDto.getAccessToken())
                .refreshToken(jwtTokenDto.getRefreshToken())
                .accessTokenExpireTime(jwtTokenDto.getAccessTokenExpireTime())
                .refreshTokenExpireTime(jwtTokenDto.getRefreshTokenExpireTime())
                .build();
        }
    }
}
