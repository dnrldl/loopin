package com.loopin.loopinbackend.global.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
//@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;  // 엔티티 처음 생성시

    @LastModifiedDate
    private LocalDateTime updatedAt;  // 엔티티 처음 생성 and 변경시
}
