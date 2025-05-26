package com.loopin.loopinbackend.domain.post.repository;

import com.loopin.loopinbackend.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
