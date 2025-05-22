package com.loopin.loopinbackend.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@Schema(name = "UserLoginRequest", description = "유저 로그인 요청 DTO")
public class UserLoginRequest {
        @Schema(description = "이메일", example = "loopin@loopin.com")
        @NotBlank(message = "이메일은 필수 입력값입니다.")
        @Email(message = "유효한 이메일 형식이어야 합니다.")
        @Pattern(
                regexp = "^[\\w-.]+@[\\w-]+\\.[A-Za-z]{2,}$",
                message = "이메일은 example@domain.com 형식이어야 합니다."
        )
        String email;

        @Schema(description = "비밀번호", example = "loopin1234!")
        @NotBlank(message = "비밀번호는 필수 입력값입니다.")
        @Size(min = 8, max = 20, message = "비밀번호는 8~20자 사이여야 합니다.")
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*]).+$",
                message = "비밀번호는 대문자·소문자·숫자·특수문자를 모두 포함해야 합니다."
        )
        String password;
}
