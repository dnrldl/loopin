package com.loopin.loopinbackend.domain.auth.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopin.loopinbackend.global.error.ErrorCode;
import com.loopin.loopinbackend.global.response.ApiErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ApiErrorResponse errorResponse = ApiErrorResponse.from(ErrorCode.ACCESS_DENIED);

        response.setStatus(ErrorCode.ACCESS_DENIED.getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(mapper.writeValueAsString(errorResponse));
    }
}
