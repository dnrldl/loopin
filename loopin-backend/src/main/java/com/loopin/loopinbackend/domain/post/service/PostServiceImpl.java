package com.loopin.loopinbackend.domain.post.service;

import com.loopin.loopinbackend.domain.post.dto.request.PostCreateRequest;

import com.loopin.loopinbackend.domain.post.entity.Post;
import com.loopin.loopinbackend.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public void createPost(PostCreateRequest request) {
        int depth = 0;

        if (request.getParentId() != null) {
            Post parent = postRepository.findById(request.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("부모 글 없음"));
            depth = parent.getDepth() + 1;
        }

        postRepository.save(Post.builder()
                .content(request.getContent())
                .parentId(request.getParentId())
                .depth(depth)
                .build());
    }
}
