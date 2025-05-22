package com.loopin.loopinbackend.domain.user.service;

import com.loopin.loopinbackend.domain.user.dto.request.UserRegisterRequest;
import com.loopin.loopinbackend.domain.user.dto.response.UserInfoResponse;
import com.loopin.loopinbackend.domain.user.entity.User;
import com.loopin.loopinbackend.domain.user.enums.Provider;
import com.loopin.loopinbackend.domain.user.enums.Role;
import com.loopin.loopinbackend.domain.user.enums.Status;
import com.loopin.loopinbackend.domain.user.repository.UserRepository;
import com.loopin.loopinbackend.domain.user.validator.UserRegisterValidator;
import com.loopin.loopinbackend.domain.auth.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRegisterValidator userRegisterValidator;

    @Override
    public String register(UserRegisterRequest request) {
        userRegisterValidator.validate(request);

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .nickname(request.getNickname())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .gender(request.getGender())
                .birth(request.getBirth())
                .role(Role.USER)
                .status(Status.ACTIVE)
                .provider(Provider.LOCAL)
                .emailVerified(false)
                .build();

        User savedUser = userRepository.save(user);
        return savedUser.getEmail();
    }

    @Override
    @Transactional(readOnly = true)
    public UserInfoResponse getMyInfo() {
        User currentUser = SecurityUtils.getCurrentUser();
        return UserInfoResponse.of(currentUser);
    }
}
