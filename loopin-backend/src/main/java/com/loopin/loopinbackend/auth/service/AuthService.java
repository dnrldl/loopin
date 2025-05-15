package com.loopin.loopinbackend.auth.service;

import com.loopin.loopinbackend.auth.dto.request.UserLoginRequest;
import com.loopin.loopinbackend.auth.dto.response.UserLoginResponse;

public interface AuthService {
    UserLoginResponse login (UserLoginRequest request);
}
