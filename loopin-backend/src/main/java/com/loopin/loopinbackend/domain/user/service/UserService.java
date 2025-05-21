package com.loopin.loopinbackend.domain.user.service;

import com.loopin.loopinbackend.domain.user.dto.request.UserRegisterRequest;
import com.loopin.loopinbackend.domain.user.entity.User;

public interface UserService {
    String register(UserRegisterRequest request);

    User findById(Long userId);

    User findByEmail(String email);

}
