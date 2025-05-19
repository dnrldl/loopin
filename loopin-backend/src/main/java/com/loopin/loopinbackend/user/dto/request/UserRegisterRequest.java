package com.loopin.loopinbackend.user.dto.request;

import com.loopin.loopinbackend.global.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;
@Schema(name = "UserRegisterRequest", description = "유저 회원가입 요청 DTO")
public record UserRegisterRequest(
        @Schema(description = "이메일", example = "loopin@loopin.com")
        @NotBlank String email,

        @Schema(description = "비밀번호", example = "loopin1234!")
        @NotBlank String password,

        @Schema(description = "닉네임", example = "looper")
        @NotBlank String nickname,

        @Schema(description = "이름", example = "루핀")
        @NotBlank String firstName,

        @Schema(description = "성", example = "김")
        @NotBlank String lastName,

        @Schema(description = "전화번호", example = "01012345678")
        @NotBlank String phoneNumber,

        @Schema(description = "성별", example = "MALE")
        @NotNull Gender gender,

        @Schema(description = "생년월일", example = "2000-01-01")
        @Past @NotNull LocalDate birth
) {
}
