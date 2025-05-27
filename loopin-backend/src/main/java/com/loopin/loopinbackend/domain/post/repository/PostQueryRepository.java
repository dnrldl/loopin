package com.loopin.loopinbackend.domain.post.repository;

import com.loopin.loopinbackend.domain.post.dto.response.PostInfoResponse;

public interface PostQueryRepository {
    PostInfoResponse findPostById(Long postId);
}
