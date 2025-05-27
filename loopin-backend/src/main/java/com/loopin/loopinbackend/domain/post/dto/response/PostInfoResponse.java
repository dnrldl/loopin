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
    private Long id;
    private String content;
    private String authorNickname;
    private int depth;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
}
