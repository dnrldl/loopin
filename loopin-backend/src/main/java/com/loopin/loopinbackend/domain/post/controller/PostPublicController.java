package com.loopin.loopinbackend.domain.post.controller;

import com.loopin.loopinbackend.domain.post.dto.FlatCommentDto;
import com.loopin.loopinbackend.domain.post.dto.response.PostInfoResponse;
import com.loopin.loopinbackend.domain.post.service.query.PostQueryService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Post (Public)", description = "인증이 필요하지 않은 게시글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public/posts")
public class PostPublicController {
    private final PostService postService;
    private final PostQueryService postQueryService;

    @Operation(summary = "게시글 단건 조회",
            description = "게시글 ID를 지용해서 게시글 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "조회 실패", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
    })
    @GetMapping("/{postId}")
    public ResponseEntity<ApiSuccessResponse<PostInfoResponse>> getPostInfo(@PathVariable Long postId) {
        PostInfoResponse postInfo = postQueryService.getPostInfo(postId);

        return ResponseEntity.ok(ApiSuccessResponse.success(postInfo));
    }

    @Operation(summary = "게시글 댓글 트리 조회",
            description = "게시글 ID를 지용해서 게시글 댓글트리를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "조회 실패", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
    })
    @GetMapping("/{postId}/comments")
    public ResponseEntity<ApiSuccessResponse<List<FlatCommentDto>>> getFlatComments(@PathVariable Long postId) {
        List<FlatCommentDto> response = postQueryService.getFlatCommentTree(postId);

        return ResponseEntity.ok(ApiSuccessResponse.success(response));
    }
}
