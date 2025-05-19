package com.loopin.loopinbackend.global.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "공통 API 응답 래퍼")
public record ApiResponse<T>(

        @Schema(description = "요청 성공 여부", example = "true")
        boolean success,

        @Schema(description = "응답 데이터", implementation = Object.class)
        T data,

        @Schema(description = "오류 메시지 (실패 시)", example = "잘못된 요청입니다.")
        String message
) {
    public ApiResponse(boolean success, T data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data, null);
    }

    public static <T> ApiResponse<T> fail(String message) {
        return new ApiResponse<>(false, null, message);
    }
}
