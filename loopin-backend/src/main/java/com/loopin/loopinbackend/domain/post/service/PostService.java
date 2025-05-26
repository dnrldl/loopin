package com.loopin.loopinbackend.domain.post.service;

import com.loopin.loopinbackend.domain.post.dto.request.PostCreateRequest;

public interface PostService {
    void createPost(PostCreateRequest post);
}
