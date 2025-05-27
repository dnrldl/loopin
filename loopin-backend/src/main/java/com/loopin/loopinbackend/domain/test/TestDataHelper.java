package com.loopin.loopinbackend.domain.test;

import com.loopin.loopinbackend.domain.post.entity.Post;
import com.loopin.loopinbackend.domain.post.repository.PostRepository;
import com.loopin.loopinbackend.domain.user.entity.User;
import com.loopin.loopinbackend.domain.user.enums.Gender;
import com.loopin.loopinbackend.domain.user.enums.Provider;
import com.loopin.loopinbackend.domain.user.enums.Role;
import com.loopin.loopinbackend.domain.user.enums.Status;
import com.loopin.loopinbackend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class TestDataHelper {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public User createTestUser(String nickname) {
        User user = User.builder()
                .email(nickname + "@example.com")
                .password("test1234!")
                .nickname(nickname)
                .firstName(nickname)
                .lastName(nickname)
                .phoneNumber("01000000000")
                .gender(Gender.MALE)
                .birth(LocalDate.of(2000, 1, 1))
                .role(Role.USER)
                .status(Status.ACTIVE)
                .provider(Provider.LOCAL)
                .emailVerified(false)
                .build();

        return userRepository.save(user);
    }

    public void createTestPost(User user, String content) {
        Post post = Post.builder()
                .authorId(user.getId())
                .content(content)
                .depth(0)
                .build();
        postRepository.save(post);
    }

    public void createTestPostWithNewUser(String nickname, String content) {
        if (userRepository.count() != 0 && postRepository.count() != 0) return;
        User user = createTestUser(nickname);
        createTestPost(user, content);
    }
}
