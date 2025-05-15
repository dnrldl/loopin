package com.loopin.loopinbackend.global.response;

import com.loopin.loopinbackend.global.error.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "API 에러 응답 래퍼")
public record ApiErrorResponse(
        @Schema(description = "성공 여부", example = "true") boolean success,
        @Schema(description = "응답 코드", example = "404") String code,
        @Schema(description = "에러 메시지", example = "true") String message,
        int status
) {
    public ApiErrorResponse(ErrorCode errorCode) {
        this(false, errorCode.getCode(), errorCode.getMessage(), errorCode.getStatus().value());
    }

    public static ApiErrorResponse from(ErrorCode errorCode) {
        return new ApiErrorResponse(errorCode);
    }
}
