package com.loopin.loopinbackend.global.error;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    // user
    DUPLICATE_EMAIL("U001", "이미 사용 중인 이메일입니다.", HttpStatus.BAD_REQUEST),
    DUPLICATE_NICKNAME("U002", "이미 사용 중인 닉네임입니다.", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND("U003", "존재하지 않는 사용자입니다.", HttpStatus.NOT_FOUND),

    // auth
    INVALID_PASSWORD("A001", "비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED);

    private final String code;
    private final String message;
    private final HttpStatus status;

    ErrorCode(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
    public HttpStatus getStatus() { return status; }
}
