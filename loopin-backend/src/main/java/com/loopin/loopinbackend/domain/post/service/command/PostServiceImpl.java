package com.loopin.loopinbackend.domain.post.service.command;

import com.loopin.loopinbackend.domain.post.dto.request.PostCreateRequest;
import com.loopin.loopinbackend.domain.post.dto.request.PostUpdateRequest;
import com.loopin.loopinbackend.domain.post.entity.Post;
import com.loopin.loopinbackend.domain.post.exception.PostNotFoundException;
import com.loopin.loopinbackend.domain.post.exception.PostNotOwnerException;
import com.loopin.loopinbackend.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public Long createPost(PostCreateRequest request, Long userId) {
        int depth = 0;

        // 원글이 아닌 댓글이라면
        if (request.getParentId() != null) {
            Post parent = postRepository.findById(request.getParentId())
                    .orElseThrow(PostNotFoundException::new);
            depth = parent.getDepth() + 1;
        }

        return postRepository.save(Post.builder()
                .authorId(userId)
                .content(request.getContent())
                .parentId(request.getParentId())
                .depth(depth)
                .build()).getId();
    }

    @Override
    public void updatePost(PostUpdateRequest request, Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
        if (!post.getAuthorId().equals(userId)) throw new PostNotOwnerException();

        post.setContent(request.getContent());
        postRepository.save(post);
    }

    @Override
    public void deletePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
        if (!post.getAuthorId().equals(userId)) throw new PostNotOwnerException();

        postRepository.delete(post);
    }
}
