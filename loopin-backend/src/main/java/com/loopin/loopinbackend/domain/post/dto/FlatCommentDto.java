package com.loopin.loopinbackend.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FlatCommentDto {
    Long id;
    Long parentId;
    String authorNickname;
    String content;
    int depth;
    LocalDateTime createdAt;
}
