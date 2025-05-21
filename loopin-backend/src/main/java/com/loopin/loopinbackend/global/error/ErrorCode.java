package com.loopin.loopinbackend.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // user
    DUPLICATE_EMAIL("U001", "이미 사용 중인 이메일입니다.", HttpStatus.BAD_REQUEST),
    DUPLICATE_NICKNAME("U002", "이미 사용 중인 닉네임입니다.", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND("U003", "존재하지 않는 사용자입니다.", HttpStatus.NOT_FOUND),
    INVALID_INPUT_VALUE("U004", "회원가입 입력 형식이 맞지 않습니다.", HttpStatus.BAD_REQUEST),

    // auth
    INVALID_EMAIL_OR_PASSWORD("A001", "이메일 또는 비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED("A002", "인증되지 않은 요청입니다.", HttpStatus.UNAUTHORIZED),
    ACCESS_DENIED("A003", "접근 권한이 없습니다.", HttpStatus.FORBIDDEN),

    // jwt
    INVALID_JWT("J001", "잘못된 JWT입니다.", HttpStatus.UNAUTHORIZED),
    EXPIRED_JWT("J002", "토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED),
    JWT_VALIDATION_ERROR("J003", "JWT 검증 중 예기치 못한 오류가 발생했습니다.", HttpStatus.UNAUTHORIZED);


    private final String code;
    private final String message;
    private final HttpStatus status;

    ErrorCode(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

}
