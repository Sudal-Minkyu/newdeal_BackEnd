package com.broadwave.backend.lifetime.detail.carbonation;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

/**
 * @author Minkyu
 * Date : 2022-05-24
 * Remark :
 */
@Repository
public class CabonationRepositoryCustomImpl extends QuerydslRepositorySupport implements CabonationRepositoryCustom {

    public CabonationRepositoryCustomImpl() {
        super(Cabonation.class);
    }

    @Override
    public CabonationInfoDto findByLtAutoNum(String autoNum) {

        QCabonation cabonation = QCabonation.cabonation;

        JPQLQuery<CabonationInfoDto> query = from(cabonation)
                .where(cabonation.ltDetailAutoNum.eq(autoNum))
                .select(Projections.constructor(CabonationInfoDto.class,

                        cabonation.ltTdAverage,
                        cabonation.ltTdStandard,
                        cabonation.ltTdVariance,

                        cabonation.ltYAverage,
                        cabonation.ltYStandard,
                        cabonation.ltYVariance,

                        cabonation.ltAAverage,
                        cabonation.ltAStandard,
                        cabonation.ltAVariance,

                        cabonation.ltCAverage,
                        cabonation.ltCStandard,
                        cabonation.ltCVariance,

                        cabonation.ltSimulation,
                        cabonation.ltRepairLength,
                        cabonation.ltTargetValue,
                        cabonation.ltRecoveryPercent,

                        cabonation.ltRecoveryOne,
                        cabonation.ltRecoveryTwo,
                        cabonation.ltRecoveryThree,
                        cabonation.ltRecoveryFour,
                        cabonation.ltRecoveryFive,
                        cabonation.ltRecoverySix,

                        cabonation.ltCostOne,
                        cabonation.ltCostTwo,
                        cabonation.ltCostThree,
                        cabonation.ltCostFour,
                        cabonation.ltCostFive,
                        cabonation.ltCostSix
                ));

        return query.fetchOne();
    }

}
