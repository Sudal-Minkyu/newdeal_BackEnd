package com.broadwave.backend.uselog;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author InSeok
 * Date : 2019-03-29
 * Time : 10:24
 * Remark :
 */
@Repository
public class UserLogRepositoryCustomImpl extends QuerydslRepositorySupport implements UserLogRepositoryCustom {

    public UserLogRepositoryCustomImpl() {
        super(UserLog.class);
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long findBySearchCount() {
        QUserLog userLog  = QUserLog.userLog;
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
//        return queryFactory.selectFrom(userLog).where(userLog.useType.eq("search")).fetchCount();
        return queryFactory.selectFrom(userLog).fetchCount(); // 2022/07/12 임시로 open, search 모두 조회하도록 설정
    }

}
