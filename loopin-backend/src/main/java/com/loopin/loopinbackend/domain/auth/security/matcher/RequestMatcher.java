package com.loopin.loopinbackend.domain.auth.security.matcher;

public class RequestMatcher {
    public static final String[] PERMIT_PATH = {
            "/api/**",      // 개발시에는 모두 열기
            "/swagger-ui/**",
            "/v3/api-docs/**"
    };
}
