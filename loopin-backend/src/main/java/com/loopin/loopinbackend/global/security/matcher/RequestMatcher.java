package com.loopin.loopinbackend.global.security.matcher;

public class RequestMatcher {
    public static final String[] PERMIT_PATH = {
            "/api/public/**",
            "/api/auth/login",
            "/swagger-ui/**",
            "/v3/api-docs/**"
    };
}
