package com.loopin.loopinbackend.domain.auth.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopin.loopinbackend.domain.auth.exception.BlacklistTokenException;
import com.loopin.loopinbackend.domain.auth.exception.ExpiredCustomJwtException;
import com.loopin.loopinbackend.domain.auth.exception.InvalidJwtException;
import com.loopin.loopinbackend.domain.auth.jwt.provider.JwtProvider;
import com.loopin.loopinbackend.domain.auth.model.CustomUserDetails;
import com.loopin.loopinbackend.domain.auth.security.util.SecurityUtils;
import com.loopin.loopinbackend.domain.auth.service.CustomUserDetailsService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService userDetailsService;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        /**
         * 1. 만료된 access token 으로 요청
         * 2. jwt filter 에서 예외 발생
         * 3. client 에서 401 응답
         * 4. client 에서 access token 을 제외하고 cookie 로 refresh token 으로 새로운 access token 요청
         * 5. server 는 refresh 로 access token 반환
         * 6. client 는 응답된 access token 으로 원래의 요청 다시 요청
         */

        String token = SecurityUtils.resolveToken(request);

        if (token != null) {
            try {
                if (redisTemplate.hasKey("blacklist:" + token)) throw new BlacklistTokenException();

                if (jwtProvider.validateToken(token)) {
                    String email = jwtProvider.extractUsername(token);
                    CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(email);

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                log.error("JwtAuthenticationFilter 오류", e);
            }
        }

        filterChain.doFilter(request, response);
    }
}
