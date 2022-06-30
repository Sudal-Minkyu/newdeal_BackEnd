package com.broadwave.backend.lifetime.detail.cabonationThreePlate;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

/**
 * @author Minkyu
 * Date : 2022-06-29
 * Remark :
 */
@Repository
public class CabonationThreePlateRepositoryCustomImpl extends QuerydslRepositorySupport implements CabonationThreePlateRepositoryCustom {

    public CabonationThreePlateRepositoryCustomImpl() {
        super(CabonationThreePlate.class);
    }

    @Override
    public CabonationThreePlateInfoDto findByLtAutoNum(String autoNum) {

        QCabonationThreePlate cabonationThreePlate = QCabonationThreePlate.cabonationThreePlate;

        JPQLQuery<CabonationThreePlateInfoDto> query = from(cabonationThreePlate)
                .where(cabonationThreePlate.ltDetailAutoNum.eq(autoNum))
                .select(Projections.constructor(CabonationThreePlateInfoDto.class,

                        cabonationThreePlate.ltTdAveragePlate1,
                        cabonationThreePlate.ltTdStandardPlate1,
                        cabonationThreePlate.ltAAveragePlate1,
                        cabonationThreePlate.ltAStandardPlate1,
                        cabonationThreePlate.ltCAveragePlate1,
                        cabonationThreePlate.ltCStandardPlate1,

                        cabonationThreePlate.ltTdAveragePlate2,
                        cabonationThreePlate.ltTdStandardPlate2,
                        cabonationThreePlate.ltAAveragePlate2,
                        cabonationThreePlate.ltAStandardPlate2,
                        cabonationThreePlate.ltCAveragePlate2,
                        cabonationThreePlate.ltCStandardPlate2,

                        cabonationThreePlate.ltTdAveragePlate3,
                        cabonationThreePlate.ltTdStandardPlate3,
                        cabonationThreePlate.ltAAveragePlate3,
                        cabonationThreePlate.ltAStandardPlate3,
                        cabonationThreePlate.ltCAveragePlate3,
                        cabonationThreePlate.ltCStandardPlate3,

                        cabonationThreePlate.ltYAverage,
                        cabonationThreePlate.ltYStandard,
                        cabonationThreePlate.ltPublicYear,
                        cabonationThreePlate.ltSimulation,
                        cabonationThreePlate.ltRepairLength,
                        cabonationThreePlate.ltTargetValue,
                        cabonationThreePlate.ltRecoveryPercent,

                        cabonationThreePlate.ltRecoveryOne,
                        cabonationThreePlate.ltRecoveryTwo,
                        cabonationThreePlate.ltRecoveryThree,
                        cabonationThreePlate.ltRecoveryFour,
                        cabonationThreePlate.ltRecoveryFive,
                        cabonationThreePlate.ltRecoverySix,

                        cabonationThreePlate.ltCostOne,
                        cabonationThreePlate.ltCostTwo,
                        cabonationThreePlate.ltCostThree,
                        cabonationThreePlate.ltCostFour,
                        cabonationThreePlate.ltCostFive,
                        cabonationThreePlate.ltCostSix
                ));

        return query.fetchOne();
    }

}
