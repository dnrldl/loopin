package com.loopin.loopinbackend.domain.comment.entity;

import com.loopin.loopinbackend.global.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment extends BaseTimeEntity {

    @Column(nullable = false)
    private Long authorId;

    @Column(nullable = false)
    private Long parentId;

    private String content;

    private int depth = 0;

    private int likeCount = 0;
    private int shareCount = 0;
    private int commentCount = 0;

    private boolean isDeleted = false;
}
