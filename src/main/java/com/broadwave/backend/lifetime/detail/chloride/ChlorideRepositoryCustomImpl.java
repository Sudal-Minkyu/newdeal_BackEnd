package com.broadwave.backend.lifetime.detail.chloride;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

/**
 * @author Minkyu
 * Date : 2022-08-04
 * Remark :
 */
@Repository
public class ChlorideRepositoryCustomImpl extends QuerydslRepositorySupport implements ChlorideRepositoryCustom {

    public ChlorideRepositoryCustomImpl() {
        super(Chloride.class);
    }

    @Override
    public ChlorideInfoDto findByLtAutoNum(String autoNum) {

        QChloride chloride = QChloride.chloride;

        JPQLQuery<ChlorideInfoDto> query = from(chloride)
                .where(chloride.ltDetailAutoNum.eq(autoNum))
                .select(Projections.constructor(ChlorideInfoDto.class,

                        chloride.ltCoAverage,
                        chloride.ltCoStandard,
                        chloride.ltCoVariance,

                        chloride.ltClimAverage,
                        chloride.ltClimStandard,
                        chloride.ltClimVariance,
                        
                        chloride.ltDAverage,
                        chloride.ltDStandard,
                        chloride.ltDVariance,

                        chloride.ltXAverage,
                        chloride.ltXStandard,
                        chloride.ltXVariance,

                        chloride.ltPublicYear,
                        chloride.ltSimulation,
                        chloride.ltRepairLength,
                        chloride.ltTargetValue,
                        chloride.ltRecoveryPercent,

                        chloride.ltRecoveryOne,
                        chloride.ltRecoveryTwo,
                        chloride.ltRecoveryThree,
                        chloride.ltRecoveryFour,
                        chloride.ltRecoveryFive,
                        chloride.ltRecoverySix,

                        chloride.ltCostOne,
                        chloride.ltCostTwo,
                        chloride.ltCostThree,
                        chloride.ltCostFour,
                        chloride.ltCostFive,
                        chloride.ltCostSix
                ));

        return query.fetchOne();
    }

}
