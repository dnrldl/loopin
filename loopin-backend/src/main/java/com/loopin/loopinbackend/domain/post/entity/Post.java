package com.loopin.loopinbackend.domain.post.entity;

import com.loopin.loopinbackend.global.entity.BaseAuditingEntity;
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
public class Post extends BaseAuditingEntity {

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer depth = 0;

    @Column(nullable = false)
    private Long viewCount = 0L;
    @Column(nullable = false)
    private Long likeCount = 0L;
    @Column(nullable = false)
    private Long shareCount = 0L;
    @Column(nullable = false)
    private Long commentCount = 0L;
}
