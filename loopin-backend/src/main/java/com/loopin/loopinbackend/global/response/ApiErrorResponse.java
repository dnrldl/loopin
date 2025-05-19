package com.loopin.loopinbackend.global.response;

import com.loopin.loopinbackend.global.error.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ApiErrorResponse", description = "API 에러 응답 래퍼")
public record ApiErrorResponse(
        @Schema(description = "요청 성공 여부", example = "false")
        boolean success,

        @Schema(description = "에러 코드", example = "U001")
        String code,

        @Schema(description = "에러 메시지", example = "이미 사용 중인 이메일입니다.")
        String message,

        @Schema(description = "HTTP 상태 코드", example = "400")
        int status
) {
    public ApiErrorResponse(ErrorCode errorCode) {
        this(false,
                errorCode.getCode(),
                errorCode.getMessage(),
                errorCode.getStatus().value());
    }

    public static ApiErrorResponse from(ErrorCode errorCode) {
        return new ApiErrorResponse(errorCode);
    }
}
