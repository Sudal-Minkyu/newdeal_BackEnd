package com.broadwave.backend.performance.reference.weightSetting.weightSettingDtos;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @author Minkyu
 * Date : 2022-04-25
 * Time :
 * Remark : 뉴딜 성능개선사업평가 관련 테이블(한대석박사) - 노후도대응의 대한 가중치
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class ReferenceWeightOldDto {

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

}
