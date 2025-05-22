package com.loopin.loopinbackend.global.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(name = "ApiResponse", description = "공통 성공 응답 래퍼")
public record ApiResponse<T>(
        @Schema(description = "요청 성공 여부", example = "true")
        boolean success,

        @Schema(description = "응답 데이터")
        T data,

        @Schema(description = "API 응답 시간", example = "2025-05-21T13:50:00")
        LocalDateTime timestamp
) {
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data, LocalDateTime.now());
    }

    public static <T> ApiResponse<T> fail(String message) { return new ApiResponse<>(false, null, LocalDateTime.now()); }
}
