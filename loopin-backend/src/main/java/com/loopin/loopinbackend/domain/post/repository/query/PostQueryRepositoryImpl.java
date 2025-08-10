package com.loopin.loopinbackend.domain.post.repository.query;

import com.loopin.loopinbackend.domain.comment.entity.QComment;
import com.loopin.loopinbackend.domain.post.dto.FlatCommentDto;
import com.loopin.loopinbackend.domain.post.dto.response.PostInfoResponse;
import com.loopin.loopinbackend.domain.post.dto.response.QPostInfoResponse;
import com.loopin.loopinbackend.domain.post.entity.QPost;
import com.loopin.loopinbackend.domain.post.exception.PostNotFoundException;
import com.loopin.loopinbackend.domain.post.qeury.PostSearchCond;
import com.loopin.loopinbackend.domain.user.entity.QUser;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostQueryRepositoryImpl implements PostQueryRepository {

    @PersistenceContext
    private final EntityManager em;

    private final JPAQueryFactory queryFactory;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public PostInfoResponse findPostById(Long postId) {
        QPost post = QPost.post;
        QUser user = QUser.user;
        QComment comment = QComment.comment;

        PostInfoResponse response = queryFactory
                .select(new QPostInfoResponse(
                        post.id,
                        post.content,
                        user.nickname,
                        post.depth,
                        JPAExpressions
                                .select(comment.count())
                                .from(comment)
                                .where(comment.parentId.eq(post.id)),
                        post.likeCount,
                        post.shareCount,
                        Expressions.constant(false), // isLiked: Redis 에서 따로 설정
                        post.createdAt,
                        post.updatedAt
                ))
                .from(post)
                .join(user).on(post.createdBy.eq(user.id))
                .where(post.id.eq(postId))
                .fetchOne();

        return Optional.ofNullable(response)
                .orElseThrow(PostNotFoundException::new);
    }

    @Override
    public List<PostInfoResponse> findPosts(int offset, PostSearchCond condition) {
        QPost post = QPost.post;
        QUser user = QUser.user;

        // 정렬 조건과 방향 기본값 설정 (최신 게시글)
        String sortBy = Optional.ofNullable(condition.getSortBy()).orElse("createdAt");
        String direction = Optional.ofNullable(condition.getDirection()).orElse("desc");

        // 정렬 방식 설정
        OrderSpecifier<?> order = switch (sortBy) {
            case "likeCount" -> "asc".equalsIgnoreCase(direction) ? post.likeCount.asc() : post.likeCount.desc();
            case "commentCount" -> "asc".equalsIgnoreCase(direction) ? post.commentCount.asc() : post.commentCount.desc();
            case "shareCount" -> "asc".equalsIgnoreCase(direction) ? post.shareCount.asc() : post.shareCount.desc();
            default -> "asc".equalsIgnoreCase(direction) ? post.createdAt.asc() : post.createdAt.desc();
        };

        return queryFactory
                .select(new QPostInfoResponse(
                        post.id,
                        post.content,
                        user.nickname,
                        post.depth,
                        post.commentCount,
                        post.likeCount,
                        post.shareCount,
                        Expressions.constant(false), // isLiked: Redis 처리 예정
                        post.createdAt,
                        post.updatedAt
                ))
                .from(post)
                .join(user).on(post.createdBy.eq(user.id))
                .offset(offset)
                .limit(condition.getSize())
                .orderBy(order)
                .fetch();
    }

    @Override
    public List<FlatCommentDto> findCommentTreeByPostId(Long postId) {
        return null;
    }

    public Long countPosts() {
        QPost post = QPost.post;

        Long result = queryFactory
                .select(post.count())
                .from(post)
                .fetchOne();

        return Optional.ofNullable(result).orElse(0L);
    }
}
