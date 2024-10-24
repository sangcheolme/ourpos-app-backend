package com.ourposapp.support.error.exception;

import com.ourposapp.support.error.ErrorCode;

public class AuthPhoneException extends BusinessException {

    public AuthPhoneException(ErrorCode errorCode) {
        super(errorCode);
    }
}
