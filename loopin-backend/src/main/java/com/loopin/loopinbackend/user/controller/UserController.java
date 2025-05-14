package com.loopin.loopinbackend.user.controller;

import com.loopin.loopinbackend.global.response.ApiResponse;
import com.loopin.loopinbackend.user.dto.request.UserRegisterRequest;
import com.loopin.loopinbackend.user.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "회원 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserServiceImpl userService;

    @GetMapping
    public ResponseEntity<ApiResponse<?>> test() {
        userService.findByEmail("asd");
        return ResponseEntity.ok(ApiResponse.success("asd"));
    }

    @Operation(summary = "회원가입", description = "사용자가 이메일, 비밀번호 등 정보를 입력하여 회원가입합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "회원가입 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping
    public ResponseEntity<ApiResponse<String>> register(@RequestBody UserRegisterRequest request) {
        String response = userService.register(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
