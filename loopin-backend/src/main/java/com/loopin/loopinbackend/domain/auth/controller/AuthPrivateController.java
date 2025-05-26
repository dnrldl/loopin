package com.loopin.loopinbackend.domain.auth.controller;

import com.loopin.loopinbackend.domain.auth.service.AuthServiceImpl;
import com.loopin.loopinbackend.global.response.ApiErrorResponse;
import com.loopin.loopinbackend.global.response.ApiSuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth (Private)", description = "인증이 필요한 인증 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/private/auth")
public class AuthPrivateController {
    private final AuthServiceImpl authService;

    @Operation(summary = "로그아웃",
            description = "사용자의 액세스 토큰으로 로그아웃합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "로그아웃 성공"),
            @ApiResponse(responseCode = "400", description = "로그아웃 실패", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
    })
    @PostMapping("/logout")
    public ResponseEntity<ApiSuccessResponse<Void>> logout(HttpServletRequest request, HttpServletResponse response) {
        authService.logout(request);

        ResponseCookie deleteCookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("Strict")
                .maxAge(0) // 쿠키 삭제
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, deleteCookie.toString());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
