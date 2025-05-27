package com.loopin.loopinbackend.domain.post.service.query;

import com.loopin.loopinbackend.domain.post.dto.response.PostInfoResponse;
import com.loopin.loopinbackend.domain.post.entity.Post;
import com.loopin.loopinbackend.domain.post.exception.PostNotFoundException;
import com.loopin.loopinbackend.domain.post.repository.PostQueryRepository;
import com.loopin.loopinbackend.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostQueryServiceImpl implements PostQueryService {

    private final PostRepository postRepository;
    private final PostQueryRepository postQueryRepository;

    @Override
    public PostInfoResponse getPostInfo(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        return postQueryRepository.findPostById(postId);
    }
}
