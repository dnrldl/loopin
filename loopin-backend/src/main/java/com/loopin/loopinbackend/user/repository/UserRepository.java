package com.loopin.loopinbackend.user.repository;

import com.loopin.loopinbackend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
