package com.loopin.loopinbackend.domain.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UserLoginResponse", description = "유저 로그인 응답 DTO")
public record UserLoginResponse(
        @Schema(description = "액세스 토큰", example = "ca23e05ead1f753fae9d4791f508925812394c8f9e9da261627a89c5a3a4d0d8")
        String accessToken,

        @Schema(description = "리프레시 토큰", example = "8d7671f2eb0e094c9329dd5769602627955b5013d6940e0ac0d6e9cd8683cb25")
        String refreshToken
        ) {
}
