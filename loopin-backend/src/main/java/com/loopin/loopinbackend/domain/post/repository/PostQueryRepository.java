package com.loopin.loopinbackend.domain.post.repository;

import com.loopin.loopinbackend.domain.post.dto.FlatCommentDto;
import com.loopin.loopinbackend.domain.post.dto.response.PostInfoResponse;

import java.util.List;

public interface PostQueryRepository {
    PostInfoResponse findPostById(Long postId);

    List<FlatCommentDto> findCommentTreeByPostId(Long postId);
}
