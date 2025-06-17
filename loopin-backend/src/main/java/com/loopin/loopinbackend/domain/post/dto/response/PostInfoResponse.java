package com.loopin.loopinbackend.domain.post.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Builder
//@AllArgsConstructor
@Setter
@Schema(name = "PostInfoResponse", description = "게시글 정보 응답 DTO")
public class PostInfoResponse {
    @Schema(description = "게시글 ID", example = "1")
    private Long id;

    @Schema(description = "게시글 내용", example = "게시글 내용")
    private String content;

    @Schema(description = "작성자 닉네임", example = "loopin")
    private String authorNickname;

    @Schema(description = "계층 깊이", example = "0은 원글, 1이상은 댓글")
    private Integer depth;

    @Schema(description = "댓글 수", example = "99")
    private Long commentCount;
    @Schema(description = "좋아요 수", example = "99")
    private Long likeCount;
    @Schema(description = "공유 수", example = "99")
    private Integer shareCount;

    @Schema(description = "로그인한 유저의 좋아요 여부", example = "true")
    private Boolean isLiked;

    @Schema(description = "게시글 생성일", example = "2025-01-01T09:00:00", type = "string", format = "date-time")
    protected LocalDateTime createdAt;
    @Schema(description = "게시글 변경일", example = "2025-01-01T09:00:00", type = "string", format = "date-time")
    protected LocalDateTime updatedAt;
    public PostInfoResponse(Long id, String content, String authorNickname,
                            Integer depth, Long commentCount, Long likeCount,
                            Integer shareCount, Boolean isLiked,
                            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.content = content;
        this.authorNickname = authorNickname;
        this.depth = depth;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
        this.shareCount = shareCount;
        this.isLiked = isLiked;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


}
