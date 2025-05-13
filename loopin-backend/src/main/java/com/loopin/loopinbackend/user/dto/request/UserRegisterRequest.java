package com.loopin.loopinbackend.user.dto.request;

import com.loopin.loopinbackend.global.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record UserRegisterRequest(
        @NotBlank String email,
        @NotBlank String password,
        @NotBlank String nickname,
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank String phoneNumber,
        @NotNull Gender gender,
        @Past @NotNull LocalDate birth
) {
}
