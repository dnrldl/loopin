package com.loopin.loopinbackend.user.repository;

import com.loopin.loopinbackend.user.entity.User;

public interface UserRepositoryCustom {
    User searchByEmail(String keyword);
}
