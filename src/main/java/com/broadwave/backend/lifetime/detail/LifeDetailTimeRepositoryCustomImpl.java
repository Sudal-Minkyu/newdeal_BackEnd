package com.broadwave.backend.lifetime.detail;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

/**
 * @author Minkyu
 * Date : 2021-09-15
 * Remark :
 */
@Repository
public class LifeDetailTimeRepositoryCustomImpl extends QuerydslRepositorySupport implements LifeDetailTimeRepositoryCustom {

    public LifeDetailTimeRepositoryCustomImpl() {
        super(LifeDetailTime.class);
    }

    @Override
    public LifeDetailTimeDto findById(Long id) {

        QLifeDetailTime lifeDetailTime = QLifeDetailTime.lifeDetailTime;

        JPQLQuery<LifeDetailTimeDto> query = from(lifeDetailTime)
                .select(Projections.constructor(LifeDetailTimeDto.class,
                        lifeDetailTime.id,

                        lifeDetailTime.ltFyAverage,
                        lifeDetailTime.ltFyStandard,
                        lifeDetailTime.ltFyVariance,

                        lifeDetailTime.ltFcAverage,
                        lifeDetailTime.ltFcStandard,
                        lifeDetailTime.ltFcVariance,

                        lifeDetailTime.ltSectionAverage,
                        lifeDetailTime.ltSectionStandard,
                        lifeDetailTime.ltSectionVariance,

                        lifeDetailTime.ltVehicleAverage,
                        lifeDetailTime.ltVehicleStandard,
                        lifeDetailTime.ltVehicleVariance,

                        lifeDetailTime.ltTargetValue,
                        lifeDetailTime.ltRecoveryPercent,

                        lifeDetailTime.ltRecoveryOne,
                        lifeDetailTime.ltRecoveryTwo,
                        lifeDetailTime.ltRecoveryThree,
                        lifeDetailTime.ltRecoveryFour,
                        lifeDetailTime.ltRecoveryFive,
                        lifeDetailTime.ltRecoverySix,

                        lifeDetailTime.ltCostOne,
                        lifeDetailTime.ltCostTwo,
                        lifeDetailTime.ltCostThree,
                        lifeDetailTime.ltCostFour,
                        lifeDetailTime.ltCostFive,
                        lifeDetailTime.ltCostSix
                ));

        query.where(lifeDetailTime.id.eq(id));

        return query.fetchOne();
    }

}
