package com.loopin.loopinbackend.domain.post.service.query;

import com.loopin.loopinbackend.domain.post.dto.FlatCommentDto;
import com.loopin.loopinbackend.domain.post.dto.response.CommentResponse;
import com.loopin.loopinbackend.domain.post.dto.response.PostInfoResponse;
import com.loopin.loopinbackend.domain.post.repository.PostQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PostQueryServiceImpl implements PostQueryService {

    private final PostQueryRepository postQueryRepository;

    @Override
    public PostInfoResponse getPostInfo(Long postId) {
        return postQueryRepository.findPostById(postId);
    }

    @Override
    public List<CommentResponse> getCommentTree(Long postId) {
        List<FlatCommentDto> result = postQueryRepository.findCommentTreeByPostId(postId);
        return buildCommentTree(result, postId);
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
