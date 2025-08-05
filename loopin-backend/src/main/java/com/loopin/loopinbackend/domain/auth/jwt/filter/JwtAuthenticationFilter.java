package com.loopin.loopinbackend.domain.auth.jwt.filter;

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

        String uri = request.getRequestURI();

        if (uri.equals("/api/auth/reissue")) {
            filterChain.doFilter(request, response);
            return;
        }

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
            } catch (ExpiredCustomJwtException e) {
                log.info("AccessToken 만료됨 (허용): {}", e.getMessage());
            } catch (Exception e) {
                log.error("JwtAuthenticationFilter 오류", e);
            }
        }

        filterChain.doFilter(request, response);
    }

}
