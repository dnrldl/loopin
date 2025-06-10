package com.loopin.loopinbackend.domain.postlike.service.command;

import com.loopin.loopinbackend.domain.post.repository.PostRepository;
import com.loopin.loopinbackend.domain.postlike.entity.PostLike;
import com.loopin.loopinbackend.domain.postlike.exception.AlreadyLikedException;
import com.loopin.loopinbackend.domain.postlike.exception.PostLikeNotFoundException;
import com.loopin.loopinbackend.domain.postlike.repository.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PostLikeServiceImpl implements PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void like(Long postId, Long userId) {
        if (postLikeRepository.existsByPostIdAndUserId(postId, userId)) throw new AlreadyLikedException();
        String key = "post:" + postId + ":likes";

        postLikeRepository.save(new PostLike(postId, userId));
        postRepository.incrementLikeCount(postId);

        try {
            redisTemplate.opsForSet().add(key, userId);
        } catch (Exception e) {
            log.warn("Redis 업데이트 실패: postId={}, userId={}", postId, userId);
        }

    }

    @Override
    public void unlike(Long postId, Long userId) {
        if (!postLikeRepository.existsByPostIdAndUserId(postId, userId)) throw new PostLikeNotFoundException();
        String key = "post:" + postId + ":likes";

        postLikeRepository.deleteByPostIdAndUserId(postId, userId);
        postRepository.decrementLikeCount(postId);

        try {
            redisTemplate.opsForSet().remove(key, userId);
        } catch (Exception e) {
            log.warn("Redis 업데이트 실패: postId={}, userId={}", postId, userId);
        }
    }
}
