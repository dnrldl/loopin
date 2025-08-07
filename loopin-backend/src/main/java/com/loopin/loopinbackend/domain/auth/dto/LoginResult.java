package com.loopin.loopinbackend.domain.auth.dto;

import com.loopin.loopinbackend.domain.auth.dto.response.UserLoginResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LoginResult {
    private final String refreshToken;
    private final UserLoginResponse userLoginResponse;
}