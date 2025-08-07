package com.loopin.loopinbackend.domain.auth.service;

import com.loopin.loopinbackend.domain.auth.dto.LoginResult;
import com.loopin.loopinbackend.domain.auth.dto.request.UserLoginRequest;
import com.loopin.loopinbackend.domain.auth.dto.response.UserLoginResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public interface AuthService {
    LoginResult login (UserLoginRequest request);

    void logout (HttpServletRequest request);

    Map<String, String> reissue (String refreshToken);
}
