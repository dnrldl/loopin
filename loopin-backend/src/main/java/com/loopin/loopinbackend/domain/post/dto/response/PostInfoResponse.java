package com.loopin.loopinbackend.domain.post.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@Schema(name = "PostInfoResponse", description = "게시글 정보 응답 DTO")
public class PostInfoResponse {
    @Schema(description = "게시글 ID", example = "1")
    private Long id;

    @Schema(description = "게시글 내용", example = "게시글 내용")
    private String content;

    @Schema(description = "작성자 닉네임", example = "loopin")
    private String authorNickname;

    @Schema(description = "계층 깊이", example = "0은 원글, 1이상은 댓글")
    private int depth;

    @Schema(description = "댓글 수", example = "99")
    private int commentCount;
    @Schema(description = "좋아요 수", example = "99")
    private int likeCount;
    @Schema(description = "공유 수", example = "99")
    private int shareCount;

    @Schema(description = "로그인한 유저의 좋아요 여부", example = "true")
    private boolean isLiked;

    @Schema(description = "게시글 생성일", example = "2025-01-01T09:00:00", type = "string", format = "date-time")
    protected LocalDateTime createdAt;
    @Schema(description = "게시글 변경일", example = "2025-01-01T09:00:00", type = "string", format = "date-time")
    protected LocalDateTime updatedAt;
}
