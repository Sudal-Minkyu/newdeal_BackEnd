package com.broadwave.backend.safety;

import com.broadwave.backend.safety.safetyDtos.SafetyInfoDto;
import com.broadwave.backend.safety.safetyDtos.SafetyInsertListDto;
import com.broadwave.backend.safety.safetyDtos.SafetyListDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Minkyu
 * Date : 2022-04-05
 * Remark :
 */
@Repository
public class SafetyRepositoryCustomImpl extends QuerydslRepositorySupport implements SafetyRepositoryCustom {

    public SafetyRepositoryCustomImpl() {
        super(Safety.class);
    }

    // 계측 기반 안전성 추정 데이터 - 교량 리스트 검색 Query
    @Override
    public List<SafetyListDto> findBySafetyList(String sfForm, String sfRank, String sfName) {

        QSafety safety = QSafety.safety;

        JPQLQuery<SafetyListDto> query = from(safety)
                .select(Projections.constructor(SafetyListDto.class,

                        safety.id,
                        safety.sfName,
                        safety.sfForm,
                        safety.sfRank,
                        safety.sfCompletionYear,
                        safety.sfFactor

                ));

        if(!sfForm.equals("00")) {
            query.where(safety.sfForm.eq(sfForm));
        }

        if(!sfRank.equals("00")) {
            query.where(safety.sfRank.eq(sfRank));
        }

        if(sfName != null && !sfName.isEmpty() ) {
            query.where(safety.sfName.likeIgnoreCase("%"+sfName+"%"));
        }

        return query.fetch();
    }

    @Override
    public SafetyInfoDto findBySafetyInfo(Long id) {

        QSafety safety = QSafety.safety;

        JPQLQuery<SafetyInfoDto> query = from(safety)
                .select(Projections.constructor(SafetyInfoDto.class,

                        safety.id,
                        safety.sfName,
                        safety.sfForm,
                        safety.sfRank,
                        safety.sfLength,
                        safety.sfWidth,
                        safety.sfNum,
                        safety.sfCompletionYear,
                        safety.sfFactor

                ));

        query.where(safety.id.eq(id));

        return query.fetchOne();
    }

    @Override
    public List<SafetyInsertListDto> findBySafetyInsertList() {

        QSafety safety = QSafety.safety;

        JPQLQuery<SafetyInsertListDto> query = from(safety)
                .select(Projections.constructor(SafetyInsertListDto.class,
                        safety.id,
                        safety.sfName
                ));
        return query.fetch();
    }

}
