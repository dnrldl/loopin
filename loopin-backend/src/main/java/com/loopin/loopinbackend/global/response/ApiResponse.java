package com.loopin.loopinbackend.global.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ApiResponse", description = "공통 성공 응답 래퍼")
public record ApiResponse<T>(
        @Schema(description = "요청 성공 여부", example = "true")
        boolean success,

        @Schema(description = "응답 데이터")
        T data
) {
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data);
    }
}
