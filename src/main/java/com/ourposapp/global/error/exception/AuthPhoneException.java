package com.ourposapp.global.error.exception;

import com.ourposapp.global.error.ErrorCode;

public class AuthPhoneException extends BusinessException {

    public AuthPhoneException(ErrorCode errorCode) {
        super(errorCode);
    }
}
