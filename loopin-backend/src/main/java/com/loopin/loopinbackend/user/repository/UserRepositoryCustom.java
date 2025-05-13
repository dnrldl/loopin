package com.loopin.loopinbackend.user.repository;

import com.loopin.loopinbackend.user.entity.User;

import java.util.List;

public interface UserRepositoryCustom {
    List<User> searchByNickname(String keyword);
}
