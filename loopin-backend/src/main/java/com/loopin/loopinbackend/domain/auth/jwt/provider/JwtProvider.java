package com.loopin.loopinbackend.domain.auth.jwt.provider;

import com.loopin.loopinbackend.domain.auth.exception.ExpiredCustomJwtException;
import com.loopin.loopinbackend.domain.auth.exception.InvalidJwtException;
import com.loopin.loopinbackend.domain.auth.model.CustomUserDetails;
import com.loopin.loopinbackend.domain.user.enums.Role;
import com.loopin.loopinbackend.global.error.BaseException;
import com.loopin.loopinbackend.global.error.ErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {
    @Value("${jwt.key}")
    private String SECRET_KEY;
    private final long ACCESS_TOKEN_VALIDITY = 1000 * 60 * 60 * 24; // 24시간
    private final long REFRESH_TOKEN_VALIDITY = 1000 * 60 * 60 * 24 * 7; // 일주일

    public String generateAccessToken(Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        String username = userDetails.getUsername();
        Long userId = userDetails.getUserId();
        Role role = userDetails.user().getRole();

        return generateToken(username, userId, role, ACCESS_TOKEN_VALIDITY);
    }

    public String generateRefreshToken(Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        String username = userDetails.getUsername();
        Long userId = userDetails.getUserId();
        Role role = userDetails.user().getRole();

        return generateToken(username, userId, role, REFRESH_TOKEN_VALIDITY);
    }

    private String generateToken(String username, Long userId, Role role, long validity) {
        Claims claims = Jwts.claims();
        claims.put("userId", userId);
        claims.put("role", role.name());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + validity))
                .signWith(getSignKey())
                .compact();
    }

    private Claims extractClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException ex) {      // 만료된 토큰은 추출하려고 하면 값을 반환하지 않음
            return ex.getClaims();
        }
    }

    public String extractUsername(String token) { return extractClaims(token).getSubject(); }
    public long extractExpiration(String token) { return extractClaims(token).getExpiration().getTime(); }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new ExpiredCustomJwtException();
        } catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            throw new InvalidJwtException();
        } catch (Exception e) {
            throw new BaseException(ErrorCode.JWT_VALIDATION_ERROR);
        }
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}

