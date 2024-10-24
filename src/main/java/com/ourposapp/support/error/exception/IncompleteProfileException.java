package com.ourposapp.support.error.exception;

import com.ourposapp.support.error.ErrorCode;

public class IncompleteProfileException extends BusinessException {

    public IncompleteProfileException(ErrorCode errorCode) {
        super(errorCode);
    }
}
