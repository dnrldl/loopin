package com.loopin.loopinbackend.domain.postlike.service.command;

import com.loopin.loopinbackend.domain.postlike.entity.PostLike;
import com.loopin.loopinbackend.domain.postlike.exception.AlreadyLikedException;
import com.loopin.loopinbackend.domain.postlike.exception.NotLikedException;
import com.loopin.loopinbackend.domain.postlike.exception.PostLikeNotFoundException;
import com.loopin.loopinbackend.domain.postlike.repository.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikeServiceImpl implements PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void like(Long postId, Long userId) {
        if (postLikeRepository.existsByPostIdAndUserId(postId, userId)) throw new PostLikeNotFoundException();
        String key = "post:" + postId + ":likes";

        if (!redisTemplate.opsForSet().isMember(key, userId)) {
            redisTemplate.opsForSet().add(key, userId);
        } else {
            throw new AlreadyLikedException();
        }

        postLikeRepository.save(new PostLike(postId, userId));
    }

    @Override
    public void unlike(Long postId, Long userId) {
        if (postLikeRepository.existsByPostIdAndUserId(postId, userId)) throw new PostLikeNotFoundException();
        String key = "post:" + postId + ":likes";

        if (redisTemplate.opsForSet().isMember(key, userId)) {
            redisTemplate.opsForSet().remove(key, userId);
        } else {
            throw new NotLikedException();
        }

        postLikeRepository.deleteByPostIdAndUserId(postId, userId);
    }

    @Override
    public int countLike(Long postId) {
        if (postLikeRepository.existsByPostId(postId)) throw new PostLikeNotFoundException();
        String key = "post:" + postId + ":likes";

        return redisTemplate.opsForSet().size(key).intValue();
    }
}
