package com.loopin.loopinbackend.user.service;

import com.loopin.loopinbackend.global.enums.Provider;
import com.loopin.loopinbackend.user.dto.request.UserLoginRequest;
import com.loopin.loopinbackend.user.dto.request.UserRegisterRequest;
import com.loopin.loopinbackend.user.entity.User;
import com.loopin.loopinbackend.user.enums.Role;
import com.loopin.loopinbackend.user.enums.Status;
import com.loopin.loopinbackend.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String register(UserRegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        User user = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .nickname(request.nickname())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .phoneNumber(request.phoneNumber())
                .gender(request.gender())
                .birth(request.birth())
                .role(Role.USER)
                .status(Status.ACTIVE)
                .provider(Provider.LOCAL)
                .emailVerified(false)
                .build();

        User savedUser = userRepository.save(user);
        return savedUser.getEmail();
    }

    @Override
    public User login(UserLoginRequest request) {
        return null;
    }

    @Override
    public User findById(Long userId) {
        return null;
    }

    @Override
    public User findByEmail(String email) {
        User user = userRepository.searchByEmail(email);
        System.out.println("Objects.toString(user) = " + Objects.toString(user));
        return null;
    }

    @Override
    public boolean isEmailDuplicated(String email) {
        return false;
    }
}
