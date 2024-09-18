package com.ourposapp.global.util;

import org.springframework.util.StringUtils;

import com.ourposapp.global.error.ErrorCode;
import com.ourposapp.global.error.exception.AuthenticationException;
import com.ourposapp.global.jwt.constant.GrantType;

public class AuthorizationHeaderUtils {

    public static void validateAuthorization(String authorizationHeader) {
        if (!StringUtils.hasText(authorizationHeader)) {
            throw new AuthenticationException(ErrorCode.NOT_EXIST_AUTHORIZATION);
        }

        String[] authorizations = authorizationHeader.split(" ");
        if (authorizations.length < 2 || (!GrantType.BEARER.getType().equals(authorizations[0]))) {
            throw new AuthenticationException(ErrorCode.NOT_VALID_BEARER_GRANT_TYPE);
        }
    }
}
