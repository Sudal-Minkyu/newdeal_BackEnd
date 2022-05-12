package com.broadwave.backend.account;

import com.broadwave.backend.account.AccountDtos.AccountDtoWithTeam;
import com.broadwave.backend.account.AccountDtos.AccountBaseDto;
import com.broadwave.backend.account.AccountDtos.AccountRole;
import com.broadwave.backend.teams.QTeam;
import com.broadwave.backend.teams.Team;
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
import java.util.List;
import java.util.Objects;

/**
 * @author InSeok
 * Date : 2019-03-29
 * Time : 10:24
 * Remark :
 */
@Repository
public class AccountRepositoryCustomImpl extends QuerydslRepositorySupport implements AccountRepositoryCustom {

    public AccountRepositoryCustomImpl() {
        super(Account.class);
    }

    @Override
    public Page<AccountDtoWithTeam> findAllBySearchStrings(String userid, String username, String teamname, Pageable pageable) {
        QAccount account  = QAccount.account;
        QTeam team = QTeam.team;

        JPQLQuery<AccountDtoWithTeam> query = from(account)
                .innerJoin(account.team,team)
                .select(Projections.constructor(AccountDtoWithTeam.class,
                        account.userid,
                        account.username,
                        account.email,
                        account.role,
                        team.teamcode,
                        team.teamname
                ));

        if (userid != null && !userid.isEmpty()){
            query.where(account.userid.containsIgnoreCase(userid));
        }
        if (username != null && !username.isEmpty()){
            query.where(account.username.containsIgnoreCase(username));
        }
        if (teamname != null && !teamname.isEmpty()){
            query.where(team.teamname.containsIgnoreCase(teamname));
        }

        final List<AccountDtoWithTeam> accounts = Objects.requireNonNull(getQuerydsl()).applyPagination(pageable, query).fetch();
        return new PageImpl<>(accounts, pageable, query.fetchCount());
    }

    @Override
    public AccountBaseDto findByAcountBase(String userid) {
        QAccount account  = QAccount.account;
        QTeam team = QTeam.team;

        JPQLQuery<AccountBaseDto> query = from(account)
                .innerJoin(team).on(team.eq(account.team))
                .select(Projections.constructor(AccountBaseDto.class,
                        account.username,
                        account.role,
                        team.teamcode,
                        team.teamname
                ));

        query.where(account.userid.eq(userid));
        return query.fetchOne();
    }

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public Long findByAccountCount() {
        QAccount account  = QAccount.account;

        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        return queryFactory.selectFrom(account).where(account.team.teamcode.eq("T00003")).fetchCount();
    }


}
