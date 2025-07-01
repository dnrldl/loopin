package com.loopin.loopinbackend.domain.post.repository.query;

import com.loopin.loopinbackend.domain.post.dto.FlatCommentDto;
import com.loopin.loopinbackend.domain.post.dto.response.PostInfoResponse;
import com.loopin.loopinbackend.domain.post.qeury.PostSearchCond;

import java.util.List;

public interface PostQueryRepository {
    PostInfoResponse findPostById(Long postId);

    List<PostInfoResponse> findPosts(int offset, PostSearchCond condition);

    Long countPosts();

    List<FlatCommentDto> findCommentTreeByPostId(Long postId);
}
