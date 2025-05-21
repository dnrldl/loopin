package com.loopin.loopinbackend.domain.user.repository;

import com.loopin.loopinbackend.domain.user.entity.User;

public interface UserRepositoryCustom {
    User searchByEmail(String keyword);
}
