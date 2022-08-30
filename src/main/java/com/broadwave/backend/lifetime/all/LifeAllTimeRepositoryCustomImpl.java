package com.broadwave.backend.lifetime.all;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

/**
 * @author Minkyu
 * Date : 2021-08-06
 * Remark :
 */
@Repository
public class LifeAllTimeRepositoryCustomImpl extends QuerydslRepositorySupport implements LifeAllTimeRepositoryCustom {

    public LifeAllTimeRepositoryCustomImpl() {
        super(LifeAllTime.class);
    }

    @Override
    public LifeAllTimeDto findById(Long id) {

        QLifeAllTime lifeAllTime = QLifeAllTime.lifeAllTime;

        JPQLQuery<LifeAllTimeDto> query = from(lifeAllTime)
                .select(Projections.constructor(LifeAllTimeDto.class,
                        lifeAllTime.ltBridgeCode,
                        lifeAllTime.ltBridgeName,
                        lifeAllTime.ltAbsence,

                        lifeAllTime.ltSpanNum,
                        lifeAllTime.ltAbsenceCode,

                        lifeAllTime.ltAllTeaRoad,
                        lifeAllTime.ltAllKind,
                        lifeAllTime.ltAllLength,
                        lifeAllTime.ltAllArea,
                        lifeAllTime.ltAllCompletionDate,
                        lifeAllTime.ltAllInputDate,
                        lifeAllTime.ltAllStage,

                        lifeAllTime.ltAllRank,

                        lifeAllTime.ltDamageBRank,
                        lifeAllTime.ltDamageCRank,
                        lifeAllTime.ltDamageDRank,
                        lifeAllTime.ltDamageERank,

                        lifeAllTime.ltDiscountRate,
                        lifeAllTime.ltIncrease,

                        lifeAllTime.ltPeriodicYear,
                        lifeAllTime.ltPeriodicNum,
                        lifeAllTime.ltPeriodicCost,

                        lifeAllTime.ltCloseYear,
                        lifeAllTime.ltCloseNum,
                        lifeAllTime.ltCloseCost,

                        lifeAllTime.ltCloseYear,
                        lifeAllTime.ltCloseNum,
                        lifeAllTime.ltSafetyCost
                ));

        query.where(lifeAllTime.id.eq(id));

        return query.fetchOne();
    }

}
