package com.loopin.loopinbackend.domain.postlike.service.command;

import com.loopin.loopinbackend.domain.postlike.entity.PostLike;
import com.loopin.loopinbackend.domain.postlike.exception.PostLikeNotFoundException;
import com.loopin.loopinbackend.domain.postlike.repository.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikeServiceImpl implements PostLikeService {

    private final PostLikeRepository postLikeRepository;

    @Override
    public void like(Long postId, Long userId) {
        if (postLikeRepository.existsByPostIdAndUserId(postId, userId)) throw new PostLikeNotFoundException();

        postLikeRepository.save(new PostLike(postId, userId));
    }

    @Override
    public void unlike(Long postId, Long userId) {
        postLikeRepository.deleteByPostIdAndUserId(postId, userId);
    }
}
