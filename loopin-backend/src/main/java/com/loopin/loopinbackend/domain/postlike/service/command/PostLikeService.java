package com.loopin.loopinbackend.domain.postlike.service.command;

public interface PostLikeService {
    void like(Long postId, Long userId);
    void unlike(Long postId, Long userId);
}
