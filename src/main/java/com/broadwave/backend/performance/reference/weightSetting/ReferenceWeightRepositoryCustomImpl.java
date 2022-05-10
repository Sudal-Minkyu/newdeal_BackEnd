package com.broadwave.backend.performance.reference.weightSetting;

import com.broadwave.backend.performance.reference.weightSetting.weightSettingDtos.ReferenceWeightBaseDto;
import com.broadwave.backend.performance.reference.weightSetting.weightSettingDtos.ReferenceWeightOldDto;
import com.broadwave.backend.performance.reference.weightSetting.weightSettingDtos.ReferenceWeightUseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

/**
 * @author Minkyu
 * Date : 2022-04-25
 * Remark :
 */
@Repository
public class ReferenceWeightRepositoryCustomImpl extends QuerydslRepositorySupport implements ReferenceWeightRepositoryCustom {

    public ReferenceWeightRepositoryCustomImpl() {
        super(ReferenceWeight.class);
    }

    @Override
    public ReferenceWeightOldDto findByReferenceWeightOld(){

        QReferenceWeight referenceWeight = QReferenceWeight.referenceWeight;

        JPQLQuery<ReferenceWeightOldDto> query = from(referenceWeight)
                .select(Projections.constructor(ReferenceWeightOldDto.class,
                        referenceWeight.piOldSafetyStan,
                        referenceWeight.piOldSafetyMin,
                        referenceWeight.piOldSafetyMax,
                        referenceWeight.piOldOldStan,
                        referenceWeight.piOldOldMin,
                        referenceWeight.piOldOldMax,
                        referenceWeight.piOldUrgencyStan,
                        referenceWeight.piOldUrgencyMin,
                        referenceWeight.piOldUrgencyMax,
                        referenceWeight.piOldGoalStan,
                        referenceWeight.piOldGoalMin,
                        referenceWeight.piOldGoalMax,
                        referenceWeight.piOldSafeUtilityStan,
                        referenceWeight.piOldSafeUtilityMin,
                        referenceWeight.piOldSafeUtilityMax,
                        referenceWeight.piOldCostUtilityStan,
                        referenceWeight.piOldCostUtilityMin,
                        referenceWeight.piOldCostUtilityMax,
                        referenceWeight.piOldBusinessStan,
                        referenceWeight.piOldBusinessMin,
                        referenceWeight.piOldBusinessMax,
                        referenceWeight.piOldComplaintStan,
                        referenceWeight.piOldComplaintMin,
                        referenceWeight.piOldComplaintMax,
                        referenceWeight.piOldBusinessEffectStan,
                        referenceWeight.piOldBusinessEffectMin,
                        referenceWeight.piOldBusinessEffectMax
                ));

        return query.fetchOne();
    }

    @Override
    public ReferenceWeightUseDto findByReferenceWeightUse(){

        QReferenceWeight referenceWeight = QReferenceWeight.referenceWeight;

        JPQLQuery<ReferenceWeightUseDto> query = from(referenceWeight)
                .select(Projections.constructor(ReferenceWeightUseDto.class,
                        referenceWeight.piUseSafetyStan,
                        referenceWeight.piUseSafetyMin,
                        referenceWeight.piUseSafetyMax,
                        referenceWeight.piUseUsabilityStan,
                        referenceWeight.piUseUsabilityMin,
                        referenceWeight.piUseUsabilityMax,
                        referenceWeight.piUseOldStan,
                        referenceWeight.piUseOldMin,
                        referenceWeight.piUseOldMax,
                        referenceWeight.piUseBusinessScaleRankStan,
                        referenceWeight.piUseBusinessScaleRankMin,
                        referenceWeight.piUseBusinessScaleRankMax,
                        referenceWeight.piUseBusinessEffectRankStan,
                        referenceWeight.piUseBusinessEffectRankMin,
                        referenceWeight.piUseBusinessEffectRankMax,
                        referenceWeight.piUseBusinessStan,
                        referenceWeight.piUseBusinessMin,
                        referenceWeight.piUseBusinessMax,
                        referenceWeight.piUseComplaintStan,
                        referenceWeight.piUseComplaintMin,
                        referenceWeight.piUseComplaintMax,
                        referenceWeight.piUseBusinessEffectStan,
                        referenceWeight.piUseBusinessEffectMin,
                        referenceWeight.piUseBusinessEffectMax
                ));

        return query.fetchOne();
    }

    @Override
    public ReferenceWeightBaseDto findByReferenceWeightBase(){

        QReferenceWeight referenceWeight = QReferenceWeight.referenceWeight;

        JPQLQuery<ReferenceWeightBaseDto> query = from(referenceWeight)
                .select(Projections.constructor(ReferenceWeightBaseDto.class,
                        referenceWeight.piBaseSafetyStan,
                        referenceWeight.piBaseSafetyMin,
                        referenceWeight.piBaseSafetyMax,
                        referenceWeight.piBaseOldStan,
                        referenceWeight.piBaseOldMin,
                        referenceWeight.piBaseOldMax,
                        referenceWeight.piBaseBusinessScaleRankStan,
                        referenceWeight.piBaseBusinessScaleRankMin,
                        referenceWeight.piBaseBusinessScaleRankMax,
                        referenceWeight.piBaseBusinessEffectRankStan,
                        referenceWeight.piBaseBusinessEffectRankMin,
                        referenceWeight.piBaseBusinessEffectRankMax,
                        referenceWeight.piBaseBusinessStan,
                        referenceWeight.piBaseBusinessMin,
                        referenceWeight.piBaseBusinessMax,
                        referenceWeight.piBaseBusinessEffectStan,
                        referenceWeight.piBaseBusinessEffectMin,
                        referenceWeight.piBaseBusinessEffectMax
                    ));

        return query.fetchOne();
    }

}
