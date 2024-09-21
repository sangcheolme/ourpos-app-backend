package com.ourposapp.global.error.exception;

import com.ourposapp.global.error.ErrorCode;

public class InvalidAddressException extends BusinessException {

    public InvalidAddressException(ErrorCode errorCode) {
        super(errorCode);
    }
}
