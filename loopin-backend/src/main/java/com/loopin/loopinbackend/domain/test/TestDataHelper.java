package com.loopin.loopinbackend.domain.test;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestDataHelper {
//    private final PostJpaRepository postJpaRepository;
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    public User createTestUser(String nickname) {
//        User user = User.builder()
//                .email(nickname + "@example.com")
//                .password(passwordEncoder.encode("test1234!"))
//                .nickname(nickname)
//                .firstName(nickname)
//                .lastName(nickname)
//                .phoneNumber("01000000000")
//                .gender(Gender.MALE)
//                .birth(LocalDate.of(2000, 1, 1))
//                .role(Role.USER)
//                .status(Status.ACTIVE)
//                .provider(Provider.LOCAL)
//                .emailVerified(false)
//                .build();
//
//        return userRepository.save(user);
//    }
//
//    public void createBulkPosts(User user, int count) {
//        for (int i = 1; i <= count; i++) {
//            createTestPost(user, "Test post " + i);
//        }
//    }
//
//
//    public void createTestPost(User user, String content) {
//        Post post = Post.builder()
//                .content(content)
//                .depth(0)
//                .shareCount(0L)
//                .likeCount(0L)
//                .commentCount(0L)
//                .build();
//        postJpaRepository.save(post);
//    }
//
//    public void createTestPostWithNewUser(String nickname, String content) {
//        if (userRepository.count() != 0 && postJpaRepository.count() != 0) return;
//        User user = createTestUser(nickname);
//        createBulkPosts(user, 30);
//    }
}
