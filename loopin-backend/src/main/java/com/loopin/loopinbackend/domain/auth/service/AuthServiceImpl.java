package com.loopin.loopinbackend.domain.auth.service;


import com.loopin.loopinbackend.domain.auth.dto.request.UserLoginRequest;
import com.loopin.loopinbackend.domain.auth.dto.response.UserLoginResponse;
import com.loopin.loopinbackend.domain.auth.exception.InvalidLoginException;
import com.loopin.loopinbackend.domain.auth.jwt.provider.JwtProvider;
import com.loopin.loopinbackend.domain.auth.model.CustomUserDetails;
import com.loopin.loopinbackend.domain.auth.security.util.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RedisTemplate<String, Object> redisTemplate;

    public UserLoginResponse login(UserLoginRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            String userId = ((CustomUserDetails) auth.getPrincipal()).getUserId().toString();

            String accessToken = jwtProvider.generateAccessToken(auth);
            String refreshToken = jwtProvider.generateRefreshToken(auth);

            String redisKey = "refresh:" + userId;
            long ttl = jwtProvider.extractExpiration(refreshToken) - System.currentTimeMillis();

            redisTemplate.opsForValue().set(redisKey, refreshToken, Duration.ofMillis(ttl));

            return new UserLoginResponse(accessToken, refreshToken);
        } catch (AuthenticationException ex) {
            System.out.println(ex.getMessage());
            throw new InvalidLoginException();
        }
    }

    @Override
    public void logout(HttpServletRequest request) {
        String token = SecurityUtils.resolveToken(request);
        String userId = jwtProvider.extractUserId(token).toString();
        long ttl = jwtProvider.extractExpiration(token) - System.currentTimeMillis();

        // 엑세스 토큰 블랙리스트 등록
        String blackKey = "blacklist:" + token;
        redisTemplate.opsForValue().set(blackKey, "logout", Duration.ofMillis(ttl));
        // 리프레시 토큰 레디스에서 삭제
        redisTemplate.delete("refresh:" + userId);
    }
}
