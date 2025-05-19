package com.loopin.loopinbackend.auth.service;


import com.loopin.loopinbackend.auth.dto.request.UserLoginRequest;
import com.loopin.loopinbackend.auth.dto.response.UserLoginResponse;
import com.loopin.loopinbackend.auth.exception.InvalidLoginValueException;
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
        String email = request.email();
        String password = request.password();
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

            String accessToken = jwtProvider.generateAccessToken(auth);
            String refreshToken = jwtProvider.generateRefreshToken(auth);

            // redis 설정

            return new UserLoginResponse(accessToken, refreshToken);
        } catch (Exception ex) {
            throw new InvalidLoginValueException();
        }
    }
}
