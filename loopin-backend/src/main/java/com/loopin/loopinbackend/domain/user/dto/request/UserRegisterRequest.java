package com.loopin.loopinbackend.domain.user.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.loopin.loopinbackend.global.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
@Schema(name = "UserRegisterRequest", description = "유저 회원가입 요청 DTO")
public record UserRegisterRequest(
        @Schema(description = "이메일", example = "loopin@loopin.com")
        @NotBlank(message = "이메일은 필수 입력값입니다.")
        @Email(message = "유효한 이메일 형식이어야 합니다.")
        @Pattern(
                regexp = "^[\\w-.]+@[\\w-]+\\.[A-Za-z]{2,}$",
                message = "이메일은 example@domain.com 형식이어야 합니다."
        )
        String email,

        @Schema(description = "비밀번호", example = "loopin1234!")
        @NotBlank(message = "비밀번호는 필수 입력값입니다.")
        @Size(min = 8, max = 20, message = "비밀번호는 8~20자 사이여야 합니다.")
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*]).+$",
                message = "비밀번호는 대문자·소문자·숫자·특수문자를 모두 포함해야 합니다."
        )
        String password,

        @Schema(description = "닉네임", example = "looper")
        @NotBlank(message = "닉네임은 필수 입력값입니다.")
        @Size(min = 2, max = 20, message = "닉네임은 2~20자여야 합니다.")
        String nickname,

        @Schema(description = "이름", example = "루핀")
        @NotBlank(message = "이름은 필수 입력값입니다.")
        @Size(max = 10, message = "이름은 최대 10자입니다.")
        String firstName,

        @Schema(description = "성", example = "김")
        @NotBlank(message = "성은 필수 입력값입니다.")
        @Size(max = 10, message = "성은 최대 10자입니다.")
        String lastName,

        @Schema(description = "전화번호", example = "01012345678")
        @NotBlank(message = "전화번호는 필수 입력값입니다.")
        @Pattern(
                regexp = "^\\d{10,11}$",
                message = "전화번호는 10~11자리 숫자여야 합니다."
        )
        String phoneNumber,

        @Schema(description = "성별", allowableValues = {"MALE","FEMALE","OTHER"}, example = "MALE")
        @NotNull(message = "성별은 필수 입력값입니다.")
        Gender gender,

        @Schema(description = "생년월일", example = "2000-01-01")
        @NotNull(message = "생년월일은 필수 입력값입니다.")
        @Past(message = "과거의 날짜이어야 합니다.")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate birth
) {
}
