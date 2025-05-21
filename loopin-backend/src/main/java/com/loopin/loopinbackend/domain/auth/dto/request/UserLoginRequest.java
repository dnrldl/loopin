package com.loopin.loopinbackend.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UserLoginRequest", description = "유저 로그인 요청 DTO")
public record UserLoginRequest(
        @Schema(description = "이메일", example = "test@loopin.com")
        String email,

        @Schema(description = "비밀번호", example = "test!1")
        String password
) {
}
