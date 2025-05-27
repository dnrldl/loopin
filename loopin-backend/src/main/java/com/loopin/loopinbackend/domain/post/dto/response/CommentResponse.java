package com.loopin.loopinbackend.domain.post.dto.response;

import com.loopin.loopinbackend.domain.post.dto.FlatCommentDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@Schema(name = "CommentResponse", description = "댓글 응답 DTO")
public class CommentResponse {
    @Schema(description = "댓글 ID", example = "1")
    private Long id;

    @Schema(description = "부모 게시글(댓글) ID", example = "1")
    private Long parentId;

    @Schema(description = "댓글 내용", example = "게시글 내용")
    private String content;

    @Schema(description = "작성자 닉네임", example = "loopin")
    private String authorNickname;

    @Schema(description = "계층 깊이", example = "0은 원글, 1이상은 댓글")
    private int depth;

    @Schema(description = "댓글 생성일", example = "2025-01-01T09:00:00", type = "string", format = "date-time")
    protected LocalDateTime createdAt;
    @Schema(description = "댓글 변경일", example = "2025-01-01T09:00:00", type = "string", format = "date-time")
    protected LocalDateTime updatedAt;

    @Schema(description = "대댓글 목록 (depth+1)")
    private List<CommentResponse> replies;
}