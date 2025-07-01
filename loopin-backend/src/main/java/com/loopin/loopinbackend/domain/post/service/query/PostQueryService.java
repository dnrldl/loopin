package com.loopin.loopinbackend.domain.post.service.query;

import com.loopin.loopinbackend.domain.post.dto.response.CommentResponse;
import com.loopin.loopinbackend.domain.post.dto.response.PostInfoResponse;
import com.loopin.loopinbackend.domain.post.qeury.PostSearchCond;
import com.loopin.loopinbackend.global.response.PageResponse;

import java.util.List;

public interface PostQueryService {
    PostInfoResponse getPostInfo(Long postId, Long userId);

    PageResponse<PostInfoResponse> getPosts(PostSearchCond condition, Long userId);

    List<CommentResponse> getCommentTree(Long postId);
}
