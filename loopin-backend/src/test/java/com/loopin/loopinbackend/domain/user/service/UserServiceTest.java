package com.loopin.loopinbackend.domain.user.service;

import com.loopin.loopinbackend.domain.user.dto.request.UserRegisterRequest;
import com.loopin.loopinbackend.domain.user.entity.User;
import com.loopin.loopinbackend.domain.user.enums.Gender;
import com.loopin.loopinbackend.domain.user.exception.DuplicateEmailException;
import com.loopin.loopinbackend.domain.user.exception.DuplicateNicknameException;
import com.loopin.loopinbackend.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

// JUnit5에서 Mockito 를 쓸 수 있게 해줌
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock // 가짜 객체를 만듬 (DB나 외부 의존 없이 동작)
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks // Mock 으로 만든 가짜 객체를 주입 (테스트 대상 클래스)
    private UserServiceImpl userService;

    private final UserRegisterRequest req = new UserRegisterRequest(
            "test@loopin.com",
            "test1234!",
            "loopin",
            "루핀",
            "김",
            "01012345678",
            Gender.MALE,
            LocalDate.of(2000, 1, 1)
    );

    @Test
    @DisplayName("정상적으로 회원가입이 되면 이메일을 반환")
    void 회원가입_정상_테스트() {
        // given
        given(userRepository.existsByEmail(req.getEmail())).willReturn(false);
        given(userRepository.existsByNickname(req.getNickname())).willReturn(false);
        given(passwordEncoder.encode(req.getPassword())).willReturn("encodedPwd");
        given(userRepository.save(any(User.class))).willReturn(User.builder().email(req.getEmail()).build());

        // when
        String result = userService.register(req);

        // then
        assertThat(result).isEqualTo(req.getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void 회원가입_이메일_중복_예외() {
        // given
        given(userRepository.existsByEmail(req.getEmail())).willReturn(true);

        // when & then
        assertThatThrownBy(() -> userService.register(req))
                .isInstanceOf(DuplicateEmailException.class);
        verify(userRepository, never()).save(any());
    }

    @Test
    void 회원가입_닉네임_중복_예외() {
        // given
        given(userRepository.existsByEmail(req.getEmail())).willReturn(false);
        given(userRepository.existsByNickname(req.getNickname())).willReturn(true);

        // when & then
        assertThatThrownBy(() -> userService.register(req))
                .isInstanceOf(DuplicateNicknameException.class);
        verify(userRepository, never()).save(any());
    }
}