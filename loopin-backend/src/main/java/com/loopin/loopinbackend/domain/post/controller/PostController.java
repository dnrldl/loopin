package com.loopin.loopinbackend.domain.post.controller;

import com.loopin.loopinbackend.domain.auth.jwt.provider.JwtProvider;
import com.loopin.loopinbackend.domain.auth.model.CustomUserDetails;
import com.loopin.loopinbackend.domain.auth.security.util.SecurityUtils;
import com.loopin.loopinbackend.domain.post.dto.request.PostCreateRequest;
import com.loopin.loopinbackend.domain.post.dto.request.PostUpdateRequest;
import com.loopin.loopinbackend.domain.comment.dto.response.CommentResponse;
import com.loopin.loopinbackend.domain.post.dto.response.PostInfoResponse;
import com.loopin.loopinbackend.domain.post.qeury.PostSearchCond;
import com.loopin.loopinbackend.domain.post.service.command.PostService;
import com.loopin.loopinbackend.domain.post.service.query.PostQueryService;
import com.loopin.loopinbackend.global.response.ApiErrorResponse;
import com.loopin.loopinbackend.global.response.ApiSuccessResponse;
import com.loopin.loopinbackend.global.response.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Post", description = "게시글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final PostQueryService postQueryService;
    private final JwtProvider jwtProvider;

    // public
    @Operation(summary = "게시글 단건 조회",
            description = "게시글 ID를 지용해서 게시글 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "조회 실패", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
    })
    @GetMapping("/{postId}")
    public ResponseEntity<ApiSuccessResponse<PostInfoResponse>> getPost(@PathVariable Long postId,
                                                                        @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long userId = null;
        if (userDetails != null) userId = userDetails.getUserId();
        PostInfoResponse postInfo = postQueryService.getPostInfo(postId, userId);

        return ResponseEntity.ok(ApiSuccessResponse.success(postInfo));
    }

    @Operation(summary = "게시글 복수건 조회",
            description = "게시글 ID를 지용해서 게시글 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "조회 실패", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
    })
    @GetMapping
    public ResponseEntity<ApiSuccessResponse<PageResponse<PostInfoResponse>>> getPosts(
            PostSearchCond condition,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long userId = null;
        if (userDetails != null) userId = userDetails.getUserId();
        PageResponse<PostInfoResponse> responses = postQueryService.getPosts(condition, userId);

        return ResponseEntity.ok(ApiSuccessResponse.success(responses));
    }

    // private
    @Operation(summary = "게시글 생성",
            description = "로그인한 사용자의 ID, 게시글 내용으로 게시글을 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "생성 성공"),
            @ApiResponse(responseCode = "400", description = "입력값 검증 실패", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<ApiSuccessResponse<Long>> createPost(@RequestBody PostCreateRequest request) {
        Long currentUserId = SecurityUtils.getCurrentUser().getId();

        System.out.println("currentUserId = " + currentUserId);
        System.out.println("request = " + request.getContent());

        Long postId = postService.createPost(request, currentUserId);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiSuccessResponse.success(postId));
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
