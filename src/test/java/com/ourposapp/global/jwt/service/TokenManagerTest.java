package com.ourposapp.global.jwt.service;

import static org.assertj.core.api.Assertions.*;

import java.time.Duration;
import java.time.Instant;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ourposapp.global.error.exception.AuthenticationException;
import com.ourposapp.global.jwt.dto.JwtTokenDto;
import com.ourposapp.user.domain.user.constant.Role;

class TokenManagerTest {

    @DisplayName("토큰이 만료되면 예외가 발생한다.")
    @Test
    void expiredToken() throws InterruptedException {
        // given
        TokenManager tokenManager = new TokenManager(
                "1",
                "1",
                getTokenSecret()
        );
        JwtTokenDto jwtTokenDto = tokenManager.createJwtTokenDto(1L, Role.ROLE_USER, true);

        // when
        Thread.sleep(2);

        // then
        assertThatThrownBy(() -> tokenManager.validateToken(jwtTokenDto.getAccessToken()))
                .isInstanceOf(AuthenticationException.class)
                .hasMessage("토큰이 만료되었습니다.");
    }

    @DisplayName("잘못된 토큰인 경우 예외가 발생한다.")
    @Test
    void invalidToken() {
        // given
        TokenManager tokenManager = new TokenManager
                (
                        "1",
                        "1",
                        getTokenSecret()
                );

        // when
        String invalidToken = "123123";

        // then
        assertThatThrownBy(() -> tokenManager.validateToken(invalidToken))
                .isInstanceOf(AuthenticationException.class)
                .hasMessage("유효하지 않은 토큰입니다.");
    }

    @DisplayName("AccessToken과 RefreshToken의 만료 시간을 각각 밀리초 단위로 검증할 수 있다.")
    @Test
    void setTokenExpireTime() {
        // given
        long accessTokenExpireMinutes = 15; // AccessToken 만료 시간 (분)
        long refreshTokenExpireDays = 14;    // RefreshToken 만료 시간 (일)

        long accessTokenExpireMillis = Duration.ofMinutes(accessTokenExpireMinutes).toMillis(); // 15분 -> 밀리초
        long refreshTokenExpireMillis = Duration.ofDays(refreshTokenExpireDays).toMillis();     // 7일 -> 밀리초

        TokenManager tokenManager = new TokenManager(
                String.valueOf(accessTokenExpireMillis),
                String.valueOf(refreshTokenExpireMillis),
                getTokenSecret()
        );

        // when
        JwtTokenDto jwtTokenDto = tokenManager.createJwtTokenDto(1L, Role.ROLE_USER, true);

        // then
        Instant now = Instant.now();
        long toleranceMillis = Duration.ofSeconds(5).toMillis();  // 허용 오차 5초 (5000 밀리초)

        Instant accessTokenExpireTime = jwtTokenDto.getAccessTokenExpireTime().toInstant();
        long accessTokenActualLifetime = Duration.between(now, accessTokenExpireTime).toMillis();
        assertThat(accessTokenActualLifetime).isCloseTo(accessTokenExpireMillis, within(toleranceMillis));

        Instant refreshTokenExpireTime = jwtTokenDto.getRefreshTokenExpireTime().toInstant();
        long refreshTokenActualLifetime = Duration.between(now, refreshTokenExpireTime).toMillis();
        assertThat(refreshTokenActualLifetime).isCloseTo(refreshTokenExpireMillis, within(toleranceMillis));
    }

    private static String getTokenSecret() {
        return "sdfasdfwafsdfcvj3kler3jflksjdsadf13xcgr==";
    }
}
