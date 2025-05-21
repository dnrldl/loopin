package com.loopin.loopinbackend.domain.user.dto.response;

import com.loopin.loopinbackend.domain.user.enums.Role;
import com.loopin.loopinbackend.domain.user.enums.Status;
import com.loopin.loopinbackend.global.enums.Gender;
import com.loopin.loopinbackend.global.enums.Provider;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(name = "UserInfoResponse", description = "유저 정보 응답 DTO")
public record UserInfoResponse(

    @Schema(description = "유저 ID", example = "1")
    Long userId,

    @Schema(description = "이메일", example = "test@loopin.com")
    String email,

    @Schema(description = "이름", example = "루핀")
    String firstName,

    @Schema(description = "성", example = "김")
    String lastName,

    @Schema(description = "닉네임", example = "loopin")
    String nickname,

    @Schema(description = "전화번호", example = "010-1234-5678")
    String phoneNumber,

    @Schema(description = "성별", example = "MALE", enumAsRef = true)
    Gender gender,

    @Schema(description = "프로필 사진 URL", example = "https://cdn.example.com/avatar.jpg")
    String profileImageUrl,


    @Schema(description = "자기소개", example = "저는 루핀입니다.")
    String bio,

    @Schema(description = "권한", example = "USER", enumAsRef = true)
    Role role,

    @Schema(description = "상태", example = "ACTIVE", enumAsRef = true)
    Status status,

    @Schema(description = "이메일 인증 여부", example = "true")
    boolean emailVerified,

    @Schema(description = "소셜 로그인 사이트", example = "GOOGLE", enumAsRef = true)
    Provider provider,

    @Schema(description = "소셜 로그인 사이트 ID", example = "11703847562938475629")
    String providerId,

    @Schema(
            description = "마지막 로그인 시간",
            example     = "2025-05-19T14:30:00",
            type        = "string",
            format      = "date-time"
    )
    LocalDateTime lastLoginAt,

    @Schema(
            description = "생년월일",
            example     = "2000-01-01",
            type        = "string",
            format      = "date"
    )
    LocalDate birth

) {
}
