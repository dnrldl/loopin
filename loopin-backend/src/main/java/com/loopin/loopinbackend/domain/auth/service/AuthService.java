package com.loopin.loopinbackend.domain.auth.service;

import com.loopin.loopinbackend.domain.auth.dto.request.UserLoginRequest;
import com.loopin.loopinbackend.domain.auth.dto.response.UserLoginResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
    UserLoginResponse login (UserLoginRequest request);

    void logout (HttpServletRequest request);
}
