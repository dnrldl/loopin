package com.loopin.loopinbackend.domain.post.service.query;

import com.loopin.loopinbackend.domain.post.dto.FlatCommentDto;
import com.loopin.loopinbackend.domain.post.dto.response.PostInfoResponse;
import com.loopin.loopinbackend.domain.post.repository.PostQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
