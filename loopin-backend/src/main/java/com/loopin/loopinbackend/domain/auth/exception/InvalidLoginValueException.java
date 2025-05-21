package com.loopin.loopinbackend.domain.auth.exception;

import com.loopin.loopinbackend.global.error.BaseException;
import com.loopin.loopinbackend.global.error.ErrorCode;

public class InvalidLoginValueException extends BaseException {
    public InvalidLoginValueException() { super(ErrorCode.INVALID_EMAIL_OR_PASSWORD); }
}
