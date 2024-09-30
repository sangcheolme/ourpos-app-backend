package com.ourposapp.global.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // 인증 & 인가
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "A-001", "토큰이 만료되었습니다."),
    TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "A-002", "유효하지 않은 토큰입니다."),
    NOT_EXIST_AUTHORIZATION(HttpStatus.UNAUTHORIZED, "A-003", "Authorization 쿠키가 빈값입니다."),
    NOT_VALID_BEARER_GRANT_TYPE(HttpStatus.UNAUTHORIZED, "A-004", "인증 타입이 Bearer 타입이 아닙니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "A-005", "해당 refresh token은 존재하지 않습니다."),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "A-006", "해당 refresh token은 만료되었습니다."),
    NOT_ACCESS_TOKEN_TYPE(HttpStatus.UNAUTHORIZED, "A-007", "해당 token은 access token이 아닙니다."),
    FORBIDDEN_ADMIN(HttpStatus.FORBIDDEN, "A-008", "관리자 Role이 아닙니다."),

    // 회원
    INVALID_LOGIN_TYPE(HttpStatus.BAD_REQUEST, "C-001", "잘못된 로그인 타입입니다. (loginType: KAKAO, NAVER)"),
    USER_ALREADY_REGISTER(HttpStatus.BAD_REQUEST, "C-002", "이미 가입된 회원입니다."),
    USER_NOT_EXIST(HttpStatus.BAD_REQUEST, "C-003", "해당 회원을 찾을 수 없습니다."),
    PHONE_VERIFICATION_REQUIRED(HttpStatus.BAD_REQUEST, "C-004", "휴대폰 인증을 완료해 주세요."),
    PHONE_NUMBER_NOT_EXIST(HttpStatus.BAD_REQUEST, "C-005", "휴대폰 인증 요청을 먼저 해주세요."),
    INVALID_AUTH_NUMBER(HttpStatus.BAD_REQUEST, "C-006", "휴대폰 인증번호가 일치하지 않습니다."),

    // 회원 주소
    USER_ADDRESS_NOT_EXIST(HttpStatus.BAD_REQUEST, "CA-001", "해당 주소를 찾을 수 없습니다."),
    USER_ADDRESS_MAX_LIMIT_EXCEEDED(HttpStatus.BAD_REQUEST, "CA-002", "등록할 수 있는 최대 주소 개수를 초과했습니다."),
    USER_ADDRESS_ALREADY_DEFAULT(HttpStatus.BAD_REQUEST, "CA-003", "이미 기본주소로 설정되어 있습니다."),
    USER_ADDRESS_INVALID_DELETION(HttpStatus.BAD_REQUEST, "CA-004", "기본 주소는 삭제할 수 없습니다.");

    ErrorCode(HttpStatus httpStatus, String errorCode, String message) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
    }

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}
