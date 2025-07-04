package com.loopin.loopinbackend.domain.post.service.query;

import com.loopin.loopinbackend.domain.post.dto.FlatCommentDto;
import com.loopin.loopinbackend.domain.post.dto.response.CommentResponse;
import com.loopin.loopinbackend.domain.post.dto.response.PostInfoResponse;
import com.loopin.loopinbackend.domain.post.qeury.PostSearchCond;
import com.loopin.loopinbackend.domain.post.repository.query.PostQueryRepository;
import com.loopin.loopinbackend.global.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class PostQueryServiceImpl implements PostQueryService {

    private final PostQueryRepository postQueryRepository;

    @Override
    @Transactional(readOnly = true)
    public PostInfoResponse getPostInfo(Long postId, Long userId) {
        return postQueryRepository.findPostById(postId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponse> getCommentTree(Long postId) {
        List<FlatCommentDto> result = postQueryRepository.findCommentTreeByPostId(postId);
        return buildCommentTree(result, postId);
    }

    @Override
    public PageResponse<PostInfoResponse> getPosts(PostSearchCond condition, Long userId) {
        int offset = condition.getPage() * condition.getSize();

        List<PostInfoResponse> content = postQueryRepository.findPosts(offset, condition);
        Long count = postQueryRepository.countPosts();

        return PageResponse.of(content, condition.getPage(), condition.getSize(), count, condition.getSortBy(), condition.getDirection());
    }

    private List<CommentResponse> buildCommentTree(List<FlatCommentDto> flatList, Long postId) {
        Map<Long, CommentResponse> idToNode = new LinkedHashMap<>();
        List<CommentResponse> roots = new ArrayList<>();

        for (FlatCommentDto dto : flatList) {
            CommentResponse node = CommentResponse.from(dto);
            idToNode.put(node.getId(), node);

            if (Objects.equals(node.getParentId(), postId)) {
                roots.add(node);
            } else {
                CommentResponse parent = idToNode.get(node.getParentId());
                if (parent != null) {
                    parent.getChildren().add(node);
                }
            }
        }
        return roots;
    }
}
