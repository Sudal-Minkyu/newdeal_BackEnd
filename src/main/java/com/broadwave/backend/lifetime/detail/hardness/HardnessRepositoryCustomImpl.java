package com.broadwave.backend.lifetime.detail.hardness;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

/**
 * @author Minkyu
 * Date : 2022-07-14
 * Remark :
 */
@Repository
public class HardnessRepositoryCustomImpl extends QuerydslRepositorySupport implements HardnessRepositoryCustom {

    public HardnessRepositoryCustomImpl() {
        super(Hardness.class);
    }

    @Override
    public HardnessInfoDto findByLtAutoNum(String autoNum) {

        QHardness hardness = QHardness.hardness;

        JPQLQuery<HardnessInfoDto> query = from(hardness)
                .where(hardness.ltDetailAutoNum.eq(autoNum))
                .select(Projections.constructor(HardnessInfoDto.class,

                        hardness.ltFdAverage,
                        hardness.ltFdStandard,
                        hardness.ltFdVariance,

                        hardness.ltYserviceAverage,
                        hardness.ltYserviceStandard,
                        hardness.ltYserviceVariance,
                        
                        hardness.ltYAverage,
                        hardness.ltYStandard,
                        hardness.ltYVariance,

                        hardness.ltSAverage,
                        hardness.ltSStandard,
                        hardness.ltSVariance,

                        hardness.ltSimulation,
                        hardness.ltRepairLength,
                        hardness.ltTargetValue,
                        hardness.ltRecoveryPercent,

                        hardness.ltRecoveryOne,
                        hardness.ltRecoveryTwo,
                        hardness.ltRecoveryThree,
                        hardness.ltRecoveryFour,
                        hardness.ltRecoveryFive,
                        hardness.ltRecoverySix,

                        hardness.ltCostOne,
                        hardness.ltCostTwo,
                        hardness.ltCostThree,
                        hardness.ltCostFour,
                        hardness.ltCostFive,
                        hardness.ltCostSix
                ));

        return query.fetchOne();
    }

}
