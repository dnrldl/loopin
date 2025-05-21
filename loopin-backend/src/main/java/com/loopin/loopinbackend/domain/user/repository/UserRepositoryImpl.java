package com.loopin.loopinbackend.domain.user.repository;

import com.loopin.loopinbackend.user.entity.QUser;
import com.loopin.loopinbackend.domain.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public User searchByEmail(String keyword) {
        QUser user = QUser.user;

        return queryFactory
                .selectFrom(user)
                .where(user.email.containsIgnoreCase(keyword))
                .fetchOne();
    }
}
