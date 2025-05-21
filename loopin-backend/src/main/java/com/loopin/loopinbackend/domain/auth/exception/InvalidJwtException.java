package com.loopin.loopinbackend.domain.auth.exception;

import com.loopin.loopinbackend.global.error.BaseException;
import com.loopin.loopinbackend.global.error.ErrorCode;

public class InvalidJwtException extends BaseException {
    public InvalidJwtException() { super(ErrorCode.INVALID_JWT); }
}
