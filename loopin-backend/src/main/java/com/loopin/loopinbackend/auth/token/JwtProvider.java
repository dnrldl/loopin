package com.loopin.loopinbackend.auth.token;

import com.loopin.loopinbackend.auth.model.CustomUserDetails;
import com.loopin.loopinbackend.user.enums.Role;
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
//        return generateToken(userDetails.getUsername(), userDetails.getUserId(), userDetails.getRole(), ACCESS_TOKEN_VALIDITY);
        return null;
    }

    public String generateRefreshToken(Authentication auth) {
        return null;
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

    public Claims extractClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException ex) {
            return ex.getClaims();
        }
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean validateToken(String token) {
        return false;
    }
}

