package com.broadwave.backend.performance.reference.weightSetting.weightSettingDtos;

import lombok.*;

/**
 * @author Minkyu
 * Date : 2022-04-20
 * Time :
 * Remark : 뉴딜 성능개선사업평가 관련 테이블(한대석박사) - 관리자전용 페이지 - 성능개선 사업평가 참조테이블 - 가중치 Dto
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class ReferenceWeightMapperDto {


    private Double piOldSafetyStan; // 노후화대응 안전성 표준 가중치
    private Double piOldSafetyMin; // 노후화대응 안전성 최소 가중치
    private Double piOldSafetyMax; // 노후화대응 안전성 최대 가중치
    private Double piOldOldStan; // 노후화대응 노후도 표준 가중치
    private Double piOldOldMin; // 노후화대응 노후도 최소 가중치
    private Double piOldOldMax; // 노후화대응 노후도 최대 가중치
    private Double piOldUrgencyStan; // 노후화대응 지체도 표준 가중치
    private Double piOldUrgencyMin; // 노후화대응 지체도 최소 가중치
    private Double piOldUrgencyMax; // 노후화대응 지체도 최대 가중치
    private Double piOldGoalStan; // 노후화대응 목표달성도 표준 가중치
    private Double piOldGoalMin; // 노후화대응 목표달성도 최소 가중치
    private Double piOldGoalMax; // 노후화대응 목표달성도 최대 가중치
    private Double piOldSafeUtilityStan; // 노후화대응 안전효용 개선 표준 가중치
    private Double piOldSafeUtilityMin; // 노후화대응 안전효용 개선 최소 가중치
    private Double piOldSafeUtilityMax; // 노후화대응 안전효용 개선 최대 가중치
    private Double piOldCostUtilityStan; // 노후화대응 자산가치 개선 표준 가중치
    private Double piOldCostUtilityMin; // 노후화대응 자산가치 개선 최소 가중치
    private Double piOldCostUtilityMax; // 노후화대응 자산가치 개선 최대 가중치
    private Double piOldBusinessStan; // 노후화대응 사업추진 타당성 표준 가중치
    private Double piOldBusinessMin; // 노후화대응 사업추진 타당성 최소 가중치
    private Double piOldBusinessMax; // 노후화대응 사업추진 타당성 최대 가중치
    private Double piOldComplaintStan; // 노후화대응 민원 및 사고 대응성 표준 가중치
    private Double piOldComplaintMin; // 노후화대응 민원 및 사고 대응성 최소 가중치
    private Double piOldComplaintMax; // 노후화대응 민원 및 사고 대응성 최대 가중치
    private Double piOldBusinessEffectStan; // 노후화대응 사업효과 범용성 표준 가중치
    private Double piOldBusinessEffectMin; // 노후화대응 사업효과 범용성 최소 가중치
    private Double piOldBusinessEffectMax; // 노후화대응 사업효과 범용성 최대 가중치


    private Double piUseSafetyStan; // 사용성변화 안전성 표준 가중치
    private Double piUseSafetyMin; // 사용성변화 안전성 최소 가중치
    private Double piUseSafetyMax; // 사용성변화 안전성 최대 가중치
    private Double piUseUsabilityStan; // 사용성변화 사용성 표준 가중치
    private Double piUseUsabilityMin; // 사용성변화 사용성 최소 가중치
    private Double piUseUsabilityMax; // 사용성변화 사용성 최대 가중치
    private Double piUseOldStan; // 사용성변화 노후도 표준 가중치
    private Double piUseOldMin; // 사용성변화 노후도 최소 가중치
    private Double piUseOldMax; // 사용성변화 노후도 최대 가중치
    private Double piUseBusinessScaleRankStan; // 사용성변화 사업규모 등급 표준 가중치
    private Double piUseBusinessScaleRankMin; // 사용성변화 사업규모 등급 최소 가중치
    private Double piUseBusinessScaleRankMax; // 사용성변화 사업규모 등급 최대 가중치
    private Double piUseBusinessEffectRankStan; // 사용성변화 사업효율 등급 표준 가중치
    private Double piUseBusinessEffectRankMin; // 사용성변화 사업효율 등급 최소 가중치
    private Double piUseBusinessEffectRankMax; // 사용성변화 사업효율 등급 최대 가중치
    private Double piUseBusinessStan; // 사용성변화 사업추진 타당성 표준 가중치
    private Double piUseBusinessMin; // 사용성변화 사업추진 타당성 최소 가중치
    private Double piUseBusinessMax; // 사용성변화 사업추진 타당성 최대 가중치
    private Double piUseComplaintStan; // 사용성변화 민원및사고 대응성 표준 가중치
    private Double piUseComplaintMin; // 사용성변화 민원및사고 대응성 최소 가중치
    private Double piUseComplaintMax; // 사용성변화 민원및사고 대응성 최대 가중치
    private Double piUseBusinessEffectStan; // 사용성변화 사업효과 범용성 표준 가중치
    private Double piUseBusinessEffectMin; // 사용성변화 사업효과 범용성 최소 가중치
    private Double piUseBusinessEffectMax; // 사용성변화 사업효과 범용성 최대 가중치


    private Double piBaseSafetyStan; // 기준변화 안전성 표준 가중치
    private Double piBaseSafetyMin; // 기준변화 안전성 최소 가중치
    private Double piBaseSafetyMax; // 기준변화 안전성 최대 가중치
    private Double piBaseOldStan; // 기준변화 노후도 표준 가중치
    private Double piBaseOldMin; // 기준변화 노후도 최소 가중치
    private Double piBaseOldMax; // 기준변화 노후도 최대 가중치
    private Double piBaseBusinessScaleRankStan; // 기준변화 사업규모 등급 표준 가중치
    private Double piBaseBusinessScaleRankMin; // 기준변화 사업규모 등급 최소 가중치
    private Double piBaseBusinessScaleRankMax; // 기준변화 사업규모 등급 최대 가중치
    private Double piBaseBusinessEffectRankStan; // 기준변화 사업효율 등급 표준 가중치
    private Double piBaseBusinessEffectRankMin; // 기준변화 사업효율 등급 최소 가중치
    private Double piBaseBusinessEffectRankMax; // 기준변화 사업효율 등급 최대 가중치
    private Double piBaseBusinessStan; // 기준변화 사업추진 타당성 표준 가중치
    private Double piBaseBusinessMin; // 기준변화 사업추진 타당성 최소 가중치
    private Double piBaseBusinessMax; // 기준변화 사업추진 타당성 최대 가중치
    private Double piBaseBusinessEffectStan; // 기준변화 사업효과 범용성 표준 가중치
    private Double piBaseBusinessEffectMin; // 기준변화 사업효과 범용성 최소 가중치
    private Double piBaseBusinessEffectMax; // 기준변화 사업효과 범용성 최대 가중치


}
