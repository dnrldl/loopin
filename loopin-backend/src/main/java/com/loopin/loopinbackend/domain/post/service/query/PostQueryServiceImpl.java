package com.loopin.loopinbackend.domain.post.service.query;

import com.loopin.loopinbackend.domain.post.dto.FlatCommentDto;
import com.loopin.loopinbackend.domain.post.dto.response.CommentResponse;
import com.loopin.loopinbackend.domain.post.dto.response.PostInfoResponse;
import com.loopin.loopinbackend.domain.post.repository.PostQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PostQueryServiceImpl implements PostQueryService {

    private final PostQueryRepository postQueryRepository;

    @Override
    public PostInfoResponse getPostInfo(Long postId) {
        return postQueryRepository.findPostById(postId);
    }

    @Override
    public List<FlatCommentDto> getFlatCommentTree(Long postId) {
        return postQueryRepository.findCommentTreeByPostId(postId);
    }

    private List<CommentResponse> buildCommentTree(List<FlatCommentDto> flatList) {
        Map<Long, CommentResponse> idToNode = new LinkedHashMap<>();
        List<CommentResponse> roots = new ArrayList<>();

        for (FlatCommentDto dto : flatList) {
            CommentResponse node = CommentResponse.from(dto);
            idToNode.put(node.getId(), node);

            if (node.getParentId() == null) {
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
