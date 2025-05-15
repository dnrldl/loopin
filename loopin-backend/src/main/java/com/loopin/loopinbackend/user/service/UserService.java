package com.loopin.loopinbackend.user.service;

import com.loopin.loopinbackend.auth.dto.request.UserLoginRequest;
import com.loopin.loopinbackend.user.dto.request.UserRegisterRequest;
import com.loopin.loopinbackend.user.entity.User;

public interface UserService {
    String register(UserRegisterRequest request);

    User findById(Long userId);

    User findByEmail(String email);

}
