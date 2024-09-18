package com.ourposapp.global.jwt.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.ourposapp.domain.user.constant.Role;
import com.ourposapp.global.error.ErrorCode;
import com.ourposapp.global.error.exception.AuthenticationException;
import com.ourposapp.global.jwt.constant.GrantType;
import com.ourposapp.global.jwt.constant.TokenType;
import com.ourposapp.global.jwt.dto.JwtTokenDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class TokenManager {

    private final String accessTokenExpirationTime;
    private final String refreshTokenExpirationTime;
    private final String tokenSecret;

    public JwtTokenDto createJwtTokenDto(Long userId, Role role, Boolean isPhoneVerified) {
        Date accessTokenExpireTime = createAccessTokenExpireTime();
        String accessToken = createAccessToken(userId, role, isPhoneVerified, accessTokenExpireTime);

        Date refreshTokenExpireTime = createRefreshTokenExpireTime();
        String refreshToken = createRefreshToken(userId, role, isPhoneVerified, refreshTokenExpireTime);

        return JwtTokenDto.builder()
            .grantType(GrantType.BEARER.getType())
            .accessToken(accessToken)
            .accessTokenExpireTime(accessTokenExpireTime)
            .refreshToken(refreshToken)
            .refreshTokenExpireTime(refreshTokenExpireTime)
            .build();
    }

    public Date createAccessTokenExpireTime() {
        return new Date(System.currentTimeMillis() + Long.parseLong(accessTokenExpirationTime));
    }

    public Date createRefreshTokenExpireTime() {
        return new Date(System.currentTimeMillis() + Long.parseLong(refreshTokenExpirationTime));
    }

    public String createAccessToken(Long userId, Role role, Boolean isPhoneVerified, Date expirationTime) {
        return Jwts.builder()
            .subject(TokenType.ACCESS.name())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(expirationTime)
            .claim("userId", userId)
            .claim("role", role)
            .claim("isPhoneVerified", isPhoneVerified)
            .signWith(getSecretKey())
            .header().type("JWT").and()
            .compact();
    }

    public String createRefreshToken(Long userId, Role role, Boolean isPhoneVerified, Date expirationTime) {
        return Jwts.builder()
            .subject(TokenType.REFRESH.name())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(expirationTime)
            .claim("userId", userId)
            .claim("role", role)
            .claim("isPhoneVerified", isPhoneVerified)
            .signWith(getSecretKey())
            .header().type("JWT").and()
            .compact();
    }

    public void validateToken(String token) {
        try {
            Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token);
        } catch (ExpiredJwtException e) {
            log.info("token 만료", e);
            throw new AuthenticationException(ErrorCode.TOKEN_EXPIRED);
        } catch (Exception e) {
            log.info("유효하지 않은 token", e);
            throw new AuthenticationException(ErrorCode.TOKEN_INVALID);
        }
    }

    public Claims getTokenClaims(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        } catch (Exception e) {
            log.info("유효하지 않은 token", e);
            throw new AuthenticationException(ErrorCode.TOKEN_INVALID);
        }
        return claims;
    }

    private SecretKey getSecretKey() {
        return new SecretKeySpec(tokenSecret.getBytes(StandardCharsets.UTF_8),
            Jwts.SIG.HS256.key().build().getAlgorithm());
    }
}
