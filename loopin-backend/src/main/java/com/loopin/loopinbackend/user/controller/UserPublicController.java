package com.loopin.loopinbackend.user.controller;

import com.loopin.loopinbackend.global.response.ApiErrorResponse;
import com.loopin.loopinbackend.global.response.ApiResponse;
import com.loopin.loopinbackend.user.dto.request.UserRegisterRequest;
import com.loopin.loopinbackend.user.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User (Public)", description = "인증이 필요 없는 유저 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public/users")
public class UserPublicController {
    private final UserServiceImpl userService;

    @Operation(summary = "회원가입",
            description = "사용자가 이메일, 비밀번호 등 정보를 입력하여 회원가입합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "입력값 검증 실패", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "중복된 입력값", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "회원가입 요청 DTO", required = true)
            @Valid @RequestBody UserRegisterRequest request
    ) {
        String response = userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                            .body(ApiResponse.success(response));
    }
}
