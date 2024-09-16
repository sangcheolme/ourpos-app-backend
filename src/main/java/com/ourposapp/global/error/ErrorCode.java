package com.ourposapp.global.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // 인증 & 인가
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "A-001", "토큰이 만료되었습니다."),
    TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "A-002", "유효하지 않은 토큰입니다."),
    NOT_EXIST_AUTHORIZATION(HttpStatus.UNAUTHORIZED, "A-003", "Authorization header가 빈값입니다."),
    NOT_VALID_BEARER_GRANT_TYPE(HttpStatus.UNAUTHORIZED, "A-004", "인증 타입이 Bearer 타입이 아닙니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "A-005", "해당 refresh token은 존재하지 않습니다."),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "A-006", "해당 refresh token은 만료되었습니다."),
    NOT_ACCESS_TOKEN_TYPE(HttpStatus.UNAUTHORIZED, "A-007", "해당 token은 access token이 아닙니다."),
    FORBIDDEN_ADMIN(HttpStatus.FORBIDDEN, "A-008", "관리자 Role이 아닙니다."),

    // 회원
    INVALID_LOGIN_TYPE(HttpStatus.BAD_REQUEST, "C-001", "잘못된 로그인 타입입니다. (loginType: KAKAO, NAVER)"),
    CUSTOMER_ALREADY_REGISTER(HttpStatus.BAD_REQUEST, "C-002", "이미 가입된 회원입니다."),
    CUSTOMER_NOT_EXIST(HttpStatus.BAD_REQUEST, "C-003", "해당 회원을 찾을 수 없습니다."),
    PHONE_VERIFICATION_REQUIRED(HttpStatus.BAD_REQUEST, "C-004", "휴대폰 인증을 완료해 주세요."),

    // 회원 주소
    CUSTOMER_ADDRESS_NOT_EXIST(HttpStatus.BAD_REQUEST, "CA-001", "해당 주소를 찾을 수 없습니다.")
    ;

    ErrorCode(HttpStatus httpStatus, String errorCode, String message) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
    }

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}
