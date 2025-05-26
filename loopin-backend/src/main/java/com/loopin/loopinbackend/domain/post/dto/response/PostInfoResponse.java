package com.loopin.loopinbackend.domain.post.dto.response;


import com.loopin.loopinbackend.domain.post.entity.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@Schema(name = "PostInfoResponse", description = "게시글 정보 응답 DTO")
public class PostInfoResponse {
    private Long id;
    private String content;
    private int depth;
    private List<PostInfoResponse> children = new ArrayList<>();

    public static PostInfoResponse from(Post post) {
        return new PostInfoResponse(post.getId(), post.getContent(), post.getDepth(), new ArrayList<>());
    }
}
