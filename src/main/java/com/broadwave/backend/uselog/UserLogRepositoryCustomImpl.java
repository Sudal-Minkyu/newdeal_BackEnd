package com.broadwave.backend.uselog;

import com.broadwave.backend.account.*;
import com.broadwave.backend.teams.QTeam;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Objects;

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
        return queryFactory.selectFrom(userLog).where(userLog.useType.eq("search")).fetchCount();
    }

}
