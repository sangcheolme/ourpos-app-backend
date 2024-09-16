package com.ourposapp.global.error.exception;

import com.ourposapp.global.error.ErrorCode;

public class IncompleteProfileException extends BusinessException {

    public IncompleteProfileException(ErrorCode errorCode) {
        super(errorCode);
    }
}
