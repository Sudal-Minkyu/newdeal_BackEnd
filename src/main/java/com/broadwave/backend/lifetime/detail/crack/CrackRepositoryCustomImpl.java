package com.broadwave.backend.lifetime.detail.crack;

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
public class CrackRepositoryCustomImpl extends QuerydslRepositorySupport implements CrackRepositoryCustom {

    public CrackRepositoryCustomImpl() {
        super(Crack.class);
    }

    @Override
    public CrackInfoDto findByLtAutoNum(String autoNum) {

        QCrack crack = QCrack.crack;

        JPQLQuery<CrackInfoDto> query = from(crack)
                .where(crack.ltDetailAutoNum.eq(autoNum))
                .select(Projections.constructor(CrackInfoDto.class,

                        crack.ltTdAverage,
                        crack.ltTdStandard,
                        crack.ltTdVariance,

                        crack.ltLAverage,
                        crack.ltLStandard,
                        crack.ltLVariance,
                        
                        crack.ltTcAverage,
                        crack.ltTcStandard,
                        crack.ltTcVariance,

                        crack.ltToAverage,
                        crack.ltToStandard,
                        crack.ltToVariance,

                        crack.ltPublicYear,
                        crack.ltSimulation,
                        crack.ltRepairLength,
                        crack.ltTargetValue,
                        crack.ltRecoveryPercent,

                        crack.ltRecoveryOne,
                        crack.ltRecoveryTwo,
                        crack.ltRecoveryThree,
                        crack.ltRecoveryFour,
                        crack.ltRecoveryFive,
                        crack.ltRecoverySix,

                        crack.ltCostOne,
                        crack.ltCostTwo,
                        crack.ltCostThree,
                        crack.ltCostFour,
                        crack.ltCostFive,
                        crack.ltCostSix
                ));

        return query.fetchOne();
    }

}
