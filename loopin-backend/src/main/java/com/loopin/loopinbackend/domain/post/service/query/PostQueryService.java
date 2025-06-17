package com.loopin.loopinbackend.domain.post.service.query;

import com.loopin.loopinbackend.domain.post.dto.response.CommentResponse;
import com.loopin.loopinbackend.domain.post.dto.response.PostInfoResponse;

import java.util.List;

public interface PostQueryService {
    PostInfoResponse getPostInfo(Long postId, Long userId);

    List<CommentResponse> getCommentTree(Long postId);

    List<PostInfoResponse> getPosts(Long lastId, int size, Long userId);
}
