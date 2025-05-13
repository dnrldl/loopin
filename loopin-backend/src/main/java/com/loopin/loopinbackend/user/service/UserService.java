package com.loopin.loopinbackend.user.service;

import com.loopin.loopinbackend.user.dto.request.UserLoginRequest;
import com.loopin.loopinbackend.user.dto.request.UserRegisterRequest;
import com.loopin.loopinbackend.user.entity.User;

public interface UserService {
    void register(UserRegisterRequest request);

    User login(UserLoginRequest request);

    User findById(Long userId);

    boolean isEmailDuplicated(String email);
}
