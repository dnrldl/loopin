package com.loopin.loopinbackend.domain.auth.security.matcher;

public class RequestMatcher {
    public static final String[] PERMIT_PATH = {
            // Auth
            "/api/auth/**",

            // User
            "/api/users/register",

            // Post (조회만 허용)
            "/api/posts",
            "/api/posts/*", // /{postId}

            // Comment (조회만 허용)
            "/api/posts/*/comments",

            // Swagger
            "/swagger-ui/**",
            "/v3/api-docs/**"
    };
}
