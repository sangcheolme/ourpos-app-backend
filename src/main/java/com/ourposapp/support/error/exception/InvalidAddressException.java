package com.ourposapp.support.error.exception;

import com.ourposapp.support.error.ErrorCode;

public class InvalidAddressException extends BusinessException {

    public InvalidAddressException(ErrorCode errorCode) {
        super(errorCode);
    }
}
