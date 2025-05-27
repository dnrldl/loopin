package com.loopin.loopinbackend.domain.post.controller;


import com.loopin.loopinbackend.domain.auth.security.util.SecurityUtils;
import com.loopin.loopinbackend.domain.post.dto.request.PostCreateRequest;
import com.loopin.loopinbackend.domain.post.dto.request.PostUpdateRequest;
import com.loopin.loopinbackend.domain.post.service.command.PostService;
import com.loopin.loopinbackend.global.response.ApiErrorResponse;
import com.loopin.loopinbackend.global.response.ApiSuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Post (Private)", description = "인증이 필요한 게시글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/private/posts")
public class PostPrivateController {
    private final PostService postService;

    @Operation(summary = "게시글 생성",
            description = "로그인한 사용자의 ID, 게시글 내용으로 게시글을 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "생성 성공"),
            @ApiResponse(responseCode = "400", description = "입력값 검증 실패", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<ApiSuccessResponse<Void>> createPost(@RequestBody PostCreateRequest request) {
        Long currentUserId = SecurityUtils.getCurrentUser().getId();

        postService.createPost(request, currentUserId);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiSuccessResponse.success(null));
    }

    @Operation(summary = "게시글 변경",
            description = "게시글 ID, 로그인한 사용자의 ID, 변경할 내용으로 게시글을 변경합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "변경 성공"),
            @ApiResponse(responseCode = "400", description = "입력값 검증 실패", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PutMapping("/{postId}")
    public ResponseEntity<ApiSuccessResponse<Void>> updatePost(@RequestBody PostUpdateRequest request, @PathVariable Long postId) {
        Long currentUserId = SecurityUtils.getCurrentUser().getId();
        postService.updatePost(request, postId, currentUserId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(ApiSuccessResponse.success(null));
    }

    @Operation(summary = "게시글 삭제",
            description = "게시글 ID, 로그인한 사용자의 ID로 게시글을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "삭제 성공"),
            @ApiResponse(responseCode = "400", description = "입력값 검증 실패", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiSuccessResponse<Void>> deletePost(@PathVariable Long postId) {
        Long currentUserId = SecurityUtils.getCurrentUser().getId();
        postService.deletePost(postId, currentUserId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(ApiSuccessResponse.success(null));
    }
}
