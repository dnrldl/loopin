package com.loopin.loopinbackend.domain.postlike.entity;

import com.loopin.loopinbackend.global.entity.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"post_id", "user_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostLike extends BaseTimeEntity {
    private Long postId;
    private Long userId;
}
