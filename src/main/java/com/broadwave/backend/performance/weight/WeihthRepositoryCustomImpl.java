package com.broadwave.backend.performance.weight;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

/**
 * @author Minkyu
 * Date : 2021-07-14
 * Remark :
 */
@Repository
public class WeihthRepositoryCustomImpl extends QuerydslRepositorySupport implements WeigthRepositoryCustom{

    public WeihthRepositoryCustomImpl() {
        super(Weight.class);
    }

    @Override
    public WeightDto findByAutoNum(String autoNum) {
        QWeight weight = QWeight.weight;

        JPQLQuery<WeightDto> query = from(weight)
                .select(Projections.constructor(WeightDto.class,
                        weight.piWeightSafe,
                        weight.piWeightOld,
                        weight.piWeightUrgency,
                        weight.piWeightGoal,
                        weight.piWeightSafeUtility,
                        weight.piWeightCostUtility,
                        weight.piWeightBusiness,
                        weight.piWeightComplaint,
                        weight.piWeightBusinessEffect
                ));

        if (autoNum != null && !autoNum.isEmpty()){
            query.where(weight.piAutoNum.eq(autoNum));
        }

        return query.fetchOne();
    }

}
