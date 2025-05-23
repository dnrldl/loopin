package com.loopin.loopinbackend.domain.user.service;

import com.loopin.loopinbackend.domain.user.dto.request.UserRegisterRequest;
import com.loopin.loopinbackend.domain.user.dto.response.UserInfoResponse;
import com.loopin.loopinbackend.domain.user.entity.User;

public interface UserService {
    String register(UserRegisterRequest request);

    void updatePassword(String password);

    UserInfoResponse getMyInfo();
}
