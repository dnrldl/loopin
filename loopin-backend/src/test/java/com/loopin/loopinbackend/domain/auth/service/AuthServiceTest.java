package com.loopin.loopinbackend.domain.auth.service;

import com.loopin.loopinbackend.domain.auth.dto.request.UserLoginRequest;
import com.loopin.loopinbackend.domain.auth.dto.response.UserLoginResponse;
import com.loopin.loopinbackend.domain.auth.exception.InvalidLoginValueException;
import com.loopin.loopinbackend.domain.auth.jwt.provider.JwtProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtProvider jwtProvider;
    @Mock
    private RedisTemplate<String, Object> redisTemplate;
    @Mock
    private ValueOperations<String, Object> valueOperations;

    @InjectMocks
    private AuthServiceImpl authService;

    private final UserLoginRequest loginRequest = new UserLoginRequest(
            "test@loopin.com", "password123!");

    @Test
    @DisplayName("로그인 성공 시 AccessToken, RefreshToken 이 반환된다")
    void 로그인_정상_테스트() {
        // given
        Authentication authentication = mock(Authentication.class);

        given(authenticationManager.authenticate(any())).willReturn(authentication);
        given(jwtProvider.generateAccessToken(authentication)).willReturn("access-token");
        given(jwtProvider.generateRefreshToken(authentication)).willReturn("refresh-token");
        given(redisTemplate.opsForValue()).willReturn(valueOperations);

        // when
        UserLoginResponse response = authService.login(loginRequest);

        // then
        assertThat(response.accessToken()).isEqualTo("access-token");
        assertThat(response.refreshToken()).isEqualTo("refresh-token");

        verify(redisTemplate.opsForValue()).set("refresh:test@loopin.com", "refresh-token");
    }

    @Test
    @DisplayName("이메일/비밀번호가 올바르지 않으면 예외가 발생한다")
    void 로그인_실패_예외_테스트() {
        // given
        given(authenticationManager.authenticate(any()))
                .willThrow(new BadCredentialsException("잘못된 자격 증명"));

        // when & then
        assertThatThrownBy(() -> authService.login(loginRequest))
                .isInstanceOf(InvalidLoginValueException.class);
    }
}