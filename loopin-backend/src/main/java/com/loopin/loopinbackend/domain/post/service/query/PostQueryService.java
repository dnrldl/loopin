package com.loopin.loopinbackend.domain.post.service.query;

import com.loopin.loopinbackend.domain.post.dto.response.PostInfoResponse;

public interface PostQueryService {
    PostInfoResponse getPostInfo(Long postId);
}
