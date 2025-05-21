package com.loopin.loopinbackend.domain.auth.security.entrypoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopin.loopinbackend.global.error.ErrorCode;
import com.loopin.loopinbackend.global.response.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        ApiErrorResponse errorResponse = ApiErrorResponse.from(ErrorCode.UNAUTHORIZED);

        response.setStatus(ErrorCode.UNAUTHORIZED.getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(mapper.writeValueAsString(errorResponse));
    }
}