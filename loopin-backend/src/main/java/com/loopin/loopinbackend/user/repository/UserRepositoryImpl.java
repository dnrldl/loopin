package com.loopin.loopinbackend.user.repository;

import com.loopin.loopinbackend.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {
    private final JPAQueryFactory queryFactory;



    @Override
    public List<User> searchByNickname(String keyword) {
//        return queryFactory
//                .selectFrom(user)
//                .where(user.nickname.containsIgnoreCase(keyword))
//                .fetch();
        return null;
    }
}
