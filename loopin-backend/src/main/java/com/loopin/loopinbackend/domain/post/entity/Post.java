package com.loopin.loopinbackend.domain.post.entity;

import com.loopin.loopinbackend.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post extends BaseEntity {
    private String content;

    private Long parentId;

    private int depth;
}
