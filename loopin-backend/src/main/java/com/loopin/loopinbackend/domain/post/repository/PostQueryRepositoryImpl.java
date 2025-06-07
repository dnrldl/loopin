package com.loopin.loopinbackend.domain.post.repository;

import com.loopin.loopinbackend.domain.post.dto.FlatCommentDto;
import com.loopin.loopinbackend.domain.post.dto.response.PostInfoResponse;
import com.loopin.loopinbackend.domain.post.exception.PostNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostQueryRepositoryImpl implements PostQueryRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public PostInfoResponse findPostById(Long postId) {
        String sql = """
                            SELECT new com.loopin.loopinbackend.domain.post.dto.response.PostInfoResponse(
                                p.id,
                                p.content,
                                u.nickname,
                                p.depth,
                                p.commentCount,
                                p.likeCount,
                                p.shareCount,
                                false,
                                p.createdAt,
                                p.updatedAt
                            )
                            FROM Post p
                            JOIN User u ON p.authorId = u.id
                            WHERE p.id = :postId
                        """;

        return em.createQuery(sql, PostInfoResponse.class)
                .setParameter("postId", postId)
                .getResultList().stream().findFirst()
                .orElseThrow(PostNotFoundException::new);
    }

    @Override
    public List<FlatCommentDto> findCommentTreeByPostId(Long postId) {
        String sql = """
                WITH RECURSIVE comment_tree AS (
                    -- 1단계: 이 게시글(postId)의 자식 댓글부터 시작
                    SELECT p.id, p.parent_id, u.nickname, p.content, p.depth, p.created_at, p.updated_at
                    FROM posts p
                    JOIN users u ON p.author_id = u.id
                    WHERE p.parent_id = CAST(:postId AS BIGINT)
                    
                    UNION ALL
                    
                    -- 2단계 이후: 이전 단계에서 조회된 댓글의 자식들을 계속 탐색
                    SELECT c.id, c.parent_id, u.nickname, c.content, c.depth, c.created_at, c.updated_at
                    FROM posts c
                    JOIN users u ON c.author_id = u.id
                    JOIN comment_tree ct ON c.parent_id = ct.id
                )
                SELECT * FROM comment_tree
                ORDER BY depth, created_at;
                """;

        List<Object[]> resultList = em.createNativeQuery(sql)
                .setParameter("postId", postId)
                .getResultList();

        return resultList.stream()
                .map(row -> new FlatCommentDto(
                        ((Number) row[0]).longValue(),              // id
                        row[1] == null ? null : ((Number) row[1]).longValue(), // parent_id (nullable)
                        (String) row[2],              // author_id
                        (String) row[3],                            // content0
                        ((Number) row[4]).intValue(),               // depth
                        ((Timestamp) row[5]).toLocalDateTime(),      // created_at
                        ((Timestamp) row[6]).toLocalDateTime()      // updated_at
                ))
                .toList();
    }

    @Override
    public List<PostInfoResponse> findPosts(Long lastId, int size, Long userId) {
        String sql = """
        SELECT
            p.id,
            p.content,
            u.nickname AS author_nickname,
            0 AS depth,
            (SELECT COUNT(*) FROM posts c WHERE c.parent_id = p.id) AS comment_count,
            (SELECT COUNT(*) FROM post_likes pl WHERE pl.post_id = p.id) AS like_count,
            0 AS share_count,
            CASE
                WHEN CAST(:userId AS BIGINT) IS NULL THEN FALSE
                ELSE EXISTS (
                    SELECT 1 FROM post_likes pl WHERE pl.post_id = p.id AND pl.user_id = CAST(:userId AS BIGINT)
                )
            END AS isLiked,
            p.created_at,
            p.updated_at
        FROM posts p
        JOIN users u ON p.author_id = u.id
        WHERE (CAST(:lastId AS BIGINT) IS NULL OR p.id < CAST(:lastId AS BIGINT))
        AND p.parent_id IS NULL
        ORDER BY p.id DESC
        LIMIT CAST(:size AS BIGINT)
        """;

        List<Object[]> resultList = em.createNativeQuery(sql)
                .setParameter("lastId", lastId)
                .setParameter("size", size)
                .setParameter("userId", userId)
                .getResultList();

        return resultList.stream()
                .map(row -> {
                    Long id = ((Number) row[0]).longValue();
                    String content = (String) row[1];
                    String nickname = (String) row[2];
                    int depth = ((Number) row[3]).intValue();
                    int commentCount = ((Number) row[4]).intValue();
                    int likeCount = ((Number) row[5]).intValue();
                    int shareCount = ((Number) row[6]).intValue();
                    boolean isLikedByMe = row[7] != null && ((Boolean) row[7]);
                    LocalDateTime createdAt = row[8] != null ? ((Timestamp) row[8]).toLocalDateTime() : null;
                    LocalDateTime updatedAt = row[9] != null ? ((Timestamp) row[9]).toLocalDateTime() : null;

                    return new PostInfoResponse(
                            id, content, nickname, depth,
                            commentCount, likeCount, shareCount,
                            isLikedByMe, createdAt, updatedAt
                    );
                })
                .toList();

    }
}
