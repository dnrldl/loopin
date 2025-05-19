package com.loopin.loopinbackend.auth.service;


import com.loopin.loopinbackend.auth.dto.request.UserLoginRequest;
import com.loopin.loopinbackend.auth.dto.response.UserLoginResponse;
import com.loopin.loopinbackend.auth.token.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;


    public UserLoginResponse login(UserLoginRequest request) {
//        String email = request.email();
//        String password = request.password();
//        try {
//            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
//            Long userId = ((PrincipalDetails) auth.getPrincipal()).getUserId();
//
//            String accessToken = jwtProvider.generateAccessToken(auth);
//            String refreshToken = jwtProvider.generateRefreshToken(auth);
//
//            Long expiration = jwtProvider.extractExpiration(refreshToken); // 절대 시간 반환 e.g. ~월 ~일
//            long ttl = expiration - System.currentTimeMillis(); // 남은 시간 반환
//
//            // redis에 refresh 토큰 저장 key = refreshToken:<userId> : <refreshToken> TTL = <expiration>
//            redisTemplate.opsForValue().set("refreshToken:" + userId, refreshToken, ttl, TimeUnit.MILLISECONDS);
//
//            return new UserLoginResponse(accessToken, refreshToken);
//        } catch (Exception ex) {
//            throw new IllegalArgumentException("이메일 또는 비밀번호를 확인하세요", ex);
//        }

        return null;
    }
}
