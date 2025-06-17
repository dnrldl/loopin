package com.loopin.loopinbackend.domain.post.service.query;

import com.loopin.loopinbackend.domain.post.dto.FlatCommentDto;
import com.loopin.loopinbackend.domain.post.dto.response.CommentResponse;
import com.loopin.loopinbackend.domain.post.dto.response.PostInfoResponse;
import com.loopin.loopinbackend.domain.post.repository.PostQueryRepository;
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
        return postQueryRepository.findPostById(postId, userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponse> getCommentTree(Long postId) {
        List<FlatCommentDto> result = postQueryRepository.findCommentTreeByPostId(postId);
        return buildCommentTree(result, postId);
    }

    @Override
    public List<PostInfoResponse> getPosts(Long lastId, int size, Long userId) {
        return postQueryRepository.findPosts(lastId, size, userId);
    }

    private List<CommentResponse> buildCommentTree(List<FlatCommentDto> flatList, Long postId) {
        Map<Long, CommentResponse> idToNode = new LinkedHashMap<>();
        List<CommentResponse> roots = new ArrayList<>();

        for (FlatCommentDto dto : flatList) {
            CommentResponse node = CommentResponse.from(dto);
            idToNode.put(node.getId(), node);

            if (Objects.equals(node.getParentId(), postId)) { // postId를 루트 parentId로 간주
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
