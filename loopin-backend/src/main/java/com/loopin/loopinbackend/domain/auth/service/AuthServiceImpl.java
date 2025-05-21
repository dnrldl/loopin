package com.loopin.loopinbackend.domain.auth.service;


import com.loopin.loopinbackend.domain.auth.dto.request.UserLoginRequest;
import com.loopin.loopinbackend.domain.auth.dto.response.UserLoginResponse;
import com.loopin.loopinbackend.domain.auth.exception.InvalidLoginValueException;
import com.loopin.loopinbackend.domain.auth.jwt.provider.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RedisTemplate<String, Object> redisTemplate;

    public UserLoginResponse login(UserLoginRequest request) {
        String email = request.email();
        String password = request.password();
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

            String accessToken = jwtProvider.generateAccessToken(auth);
            String refreshToken = jwtProvider.generateRefreshToken(auth);

            // redis 설정
            String redisKey = "refresh:" + email;
            long now = System.currentTimeMillis();
            Long expiration = jwtProvider.extractExpiration(refreshToken);

            redisTemplate.opsForValue().set(redisKey, refreshToken, Duration.ofMillis(expiration - now));

            return new UserLoginResponse(accessToken, refreshToken);
        } catch (Exception ex) {
            throw new InvalidLoginValueException();
        }
    }
}
