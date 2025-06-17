package com.loopin.loopinbackend.domain.post.repository;

import com.loopin.loopinbackend.domain.post.dto.FlatCommentDto;
import com.loopin.loopinbackend.domain.post.dto.response.PostInfoResponse;
import com.loopin.loopinbackend.domain.post.entity.QPost;
import com.loopin.loopinbackend.domain.post.exception.PostNotFoundException;
import com.loopin.loopinbackend.domain.postlike.entity.QPostLike;
import com.loopin.loopinbackend.domain.user.entity.QUser;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostQueryRepositoryImpl implements PostQueryRepository {

    @PersistenceContext
    private final EntityManager em;

    private final JPAQueryFactory queryFactory;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public PostInfoResponse findPostById(Long postId, Long userId) {
        QPost post = QPost.post;
        QUser user = QUser.user;

        PostInfoResponse response = queryFactory
                .select(Projections.constructor(PostInfoResponse.class,
                        post.id,
                        post.content,
                        user.nickname,
                        post.depth,
                        post.commentCount,
                        post.likeCount,
                        post.shareCount,
                        Expressions.constant(false), // isLiked: Redis에서 따로 설정
                        post.createdAt,
                        post.updatedAt
                ))
                .from(post)
                .join(user).on(post.authorId.eq(user.id))
                .where(post.id.eq(postId))
                .fetchOne();

        if (response == null) throw new PostNotFoundException();

        return response;
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
        QPost post = QPost.post;
        QUser user = QUser.user;
        QPost child = new QPost("child");         // 댓글 수 구하기용 alias
        QPostLike like = QPostLike.postLike;

        // 1. 게시글 + 메타 정보 조회 (QueryDSL)
        List<PostInfoResponse> posts = queryFactory
                .select(Projections.constructor(PostInfoResponse.class,
                        post.id,
                        post.content,
                        user.nickname,
                        Expressions.constant(0), // depth
                        JPAExpressions.select(child.count())
                                .from(child)
                                .where(child.parentId.eq(post.id)),
                        JPAExpressions.select(like.count())
                                .from(like)
                                .where(like.postId.eq(post.id)),
                        Expressions.constant(0), // shareCount (추후 구현 시 교체)
                        Expressions.constant(false), // isLiked, Redis에서 별도로 설정
                        post.createdAt,
                        post.updatedAt
                ))
                .from(post)
                .join(user).on(post.authorId.eq(user.id))
                .where(
                        post.parentId.isNull(),
                        lastId != null ? post.id.lt(lastId) : null
                )
                .orderBy(post.id.desc())
                .limit(size)
                .fetch();

        if (userId != null) {
            // 2. Redis를 이용해 isLiked 필드 설정
            for (PostInfoResponse response : posts) {
                String redisKey = "post:" + response.getId() + ":likes";
                boolean isLiked = Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(redisKey, userId));
                response.setIsLiked(isLiked);
            }
        }


        return posts;
    }
}
