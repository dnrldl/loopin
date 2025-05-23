package com.loopin.loopinbackend.domain.user.validator;

import com.loopin.loopinbackend.domain.user.dto.request.UserRegisterRequest;
import com.loopin.loopinbackend.domain.user.entity.User;
import com.loopin.loopinbackend.domain.user.exception.*;
import com.loopin.loopinbackend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRegisterValidator {
    private final UserRepository userRepository;

    public void validate(UserRegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) throw new DuplicateEmailException();
        if (userRepository.existsByNickname(request.getNickname())) throw new DuplicateNicknameException();
        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) throw new DuplicatePhoneNumberException();
    }
}
