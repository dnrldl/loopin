package com.loopin.loopinbackend.domain.post.repository;

import com.loopin.loopinbackend.domain.post.dto.response.PostInfoResponse;
import com.loopin.loopinbackend.domain.post.exception.PostNotFoundException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostQueryRepositoryImpl implements PostQueryRepository {

    private final EntityManager em;

    @Override
    public PostInfoResponse findPostById(Long postId) {
        return em.createQuery("""
                            SELECT new com.loopin.loopinbackend.domain.post.dto.response.PostInfoResponse(
                                p.id,
                                p.content,
                                u.nickname,
                                p.depth,
                                p.createdAt,
                                p.updatedAt
                            )
                            FROM Post p
                            JOIN User u ON p.authorId = u.id
                            WHERE p.id = :postId
                        """, PostInfoResponse.class)
                .setParameter("postId", postId)
                .getResultList().stream().findFirst()
                .orElseThrow(PostNotFoundException::new);
    }
}
