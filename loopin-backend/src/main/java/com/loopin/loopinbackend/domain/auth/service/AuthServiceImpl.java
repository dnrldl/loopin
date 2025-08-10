package com.loopin.loopinbackend.domain.auth.service;


import com.loopin.loopinbackend.domain.auth.dto.LoginResult;
import com.loopin.loopinbackend.domain.auth.dto.request.UserLoginRequest;
import com.loopin.loopinbackend.domain.auth.dto.response.UserLoginResponse;
import com.loopin.loopinbackend.domain.auth.exception.BlacklistTokenException;
import com.loopin.loopinbackend.domain.auth.exception.InvalidJwtException;
import com.loopin.loopinbackend.domain.auth.exception.InvalidLoginException;
import com.loopin.loopinbackend.domain.auth.jwt.provider.JwtProvider;
import com.loopin.loopinbackend.domain.auth.model.CustomUserDetails;
import com.loopin.loopinbackend.domain.auth.security.util.SecurityUtils;
import com.loopin.loopinbackend.domain.user.entity.User;
import com.loopin.loopinbackend.domain.user.repository.UserJpaRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserDetailsService userDetailsService;
    private final UserJpaRepository userJpaRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RedisTemplate<String, Object> redisTemplate;

    public LoginResult login(UserLoginRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        try {
            // login 진행
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            CustomUserDetails principal = (CustomUserDetails) auth.getPrincipal();
            String userId = principal.getUserId().toString();

            String accessToken = jwtProvider.generateAccessToken(auth);
            String refreshToken = jwtProvider.generateRefreshToken(auth);

            String redisKey = "refresh:" + userId;
            long ttl = jwtProvider.extractExpiration(refreshToken) - System.currentTimeMillis();

            redisTemplate.opsForValue().set(redisKey, refreshToken, Duration.ofMillis(ttl));

            User loggedInUser = principal.user();

            return new LoginResult(refreshToken, UserLoginResponse.of(loggedInUser, accessToken));
        } catch (AuthenticationException ex) {
            System.out.println(ex.getMessage());
            throw new InvalidLoginException();
        }
    }

    @Override
    public void logout(HttpServletRequest request) {
        String accessToken = SecurityUtils.resolveToken(request);
        String userId = jwtProvider.extractUserId(accessToken).toString();
        long ttl = jwtProvider.extractExpiration(accessToken) - System.currentTimeMillis();

        // 토큰이 유효하면 엑세스 토큰 블랙리스트 등록
        if (ttl > 0) {
            String blackKey = "blacklist:" + accessToken;
            redisTemplate.opsForValue().set(blackKey, "logout", Duration.ofMillis(ttl));
        }

        // 리프레시 토큰 레디스에서 삭제
        redisTemplate.delete("refresh:" + userId);
    }

    @Override
    public Map<String, String> refresh(String refreshToken) {
        if (redisTemplate.hasKey("BL:RT:" + refreshToken)) throw new BlacklistTokenException();
        if (!jwtProvider.validateToken(refreshToken)) throw new InvalidJwtException();

        String userId = jwtProvider.extractUserId(refreshToken).toString();
        String username = jwtProvider.extractUsername(refreshToken);
        String storedKey = redisTemplate.opsForValue().get("refresh:" + userId).toString();

        if (storedKey == null || !storedKey.equals(refreshToken)) throw new InvalidJwtException();

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        String newAccessToken = jwtProvider.generateAccessToken(auth);
        String newRefreshToken = jwtProvider.generateRefreshToken(auth);
        long blTtl = jwtProvider.extractExpiration(refreshToken) - System.currentTimeMillis();
        long ttl = jwtProvider.extractExpiration(newRefreshToken) - System.currentTimeMillis();

        // 사용한 리프레시 토큰 블랙리스트 등록
        redisTemplate.opsForValue().set("BL:RT:" + refreshToken, "used", Duration.ofMillis(blTtl));
        // 새로운 리프레시 토큰 등록
        redisTemplate.opsForValue().set("refresh:" + userId, newRefreshToken, Duration.ofMillis(ttl));

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", newAccessToken);
        tokens.put("refreshToken", newRefreshToken);

        return tokens;
    }
}
