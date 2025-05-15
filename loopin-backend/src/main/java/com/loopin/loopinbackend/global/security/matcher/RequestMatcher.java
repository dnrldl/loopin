package com.loopin.loopinbackend.global.security.matcher;

public class RequestMatcher {
    public static final String[] PERMIT_PATH = {
            "**",
            "/swagger-ui/**",
            "/v3/api-docs/**"
    };
}
