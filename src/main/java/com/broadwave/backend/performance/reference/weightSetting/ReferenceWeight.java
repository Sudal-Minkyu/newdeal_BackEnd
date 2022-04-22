package com.broadwave.backend.performance.reference.weightSetting;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @author Minkyu
 * Date : 2022-04-20
 * Time :
 * Remark : 뉴딜 성능개선사업평가 관련 테이블(한대석박사) - 관리자전용 페이지 - 성능개선 사업평가 참조테이블 - 가중치
 */
@Entity
@Data
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="nd_pi_weight_setting")
public class ReferenceWeight {

    @Id
    @Column(name="pi_weight_set")
    private String id; // 고유ID값(NOTNULL)


    @Column(name="pi_old_safety_stan")
    private Double piOldSafetyStan; // 노후화대응 안전성 표준 가중치

    @Column(name="pi_old_safety_min")
    private Double piOldSafetyMin; // 노후화대응 안전성 최소 가중치

    @Column(name="pi_old_safety_max")
    private Double piOldSafetyMax; // 노후화대응 안전성 최대 가중치

    @Column(name="pi_old_old_stan")
    private Double piOldOldStan; // 노후화대응 노후도 표준 가중치

    @Column(name="pi_old_old_min")
    private Double piOldOldMin; // 노후화대응 노후도 최소 가중치

    @Column(name="pi_old_old_max")
    private Double piOldOldMax; // 노후화대응 노후도 최대 가중치

    @Column(name="pi_old_urgency_stan")
    private Double piOldUrgencyStan; // 노후화대응 지체도 표준 가중치

    @Column(name="pi_old_urgency_min")
    private Double piOldUrgencyMin; // 노후화대응 지체도 최소 가중치

    @Column(name="pi_old_urgency_max")
    private Double piOldUrgencyMax; // 노후화대응 지체도 최대 가중치

    @Column(name="pi_old_goal_stan")
    private Double piOldGoalStan; // 노후화대응 목표달성도 표준 가중치

    @Column(name="pi_old_goal_min")
    private Double piOldGoalMin; // 노후화대응 목표달성도 최소 가중치

    @Column(name="pi_old_goal_max")
    private Double piOldGoalMax; // 노후화대응 목표달성도 최대 가중치


    @Column(name="pi_old_safe_utility_stan")
    private Double piOldSafeUtilityStan; // 노후화대응 안전효용 개선 표준 가중치

    @Column(name="pi_old_safe_utility_min")
    private Double piOldSafeUtilityMin; // 노후화대응 안전효용 개선 최소 가중치

    @Column(name="pi_old_safe_utility_max")
    private Double piOldSafeUtilityMax; // 노후화대응 안전효용 개선 최대 가중치

    @Column(name="pi_old_cost_utility_stan")
    private Double piOldCostUtilityStan; // 노후화대응 자산가치 개선 표준 가중치

    @Column(name="pi_old_cost_utility_min")
    private Double piOldCostUtilityMin; // 노후화대응 자산가치 개선 최소 가중치

    @Column(name="pi_old_cost_utility_max")
    private Double piOldCostUtilityMax; // 노후화대응 자산가치 개선 최대 가중치


    @Column(name="pi_old_business_stan")
    private Double piOldBusinessStan; // 노후화대응 사업추진 타당성 표준 가중치

    @Column(name="pi_old_business_min")
    private Double piOldBusinessMin; // 노후화대응 사업추진 타당성 최소 가중치

    @Column(name="pi_old_business_max")
    private Double piOldBusinessMax; // 노후화대응 사업추진 타당성 최대 가중치

    @Column(name="pi_old_complaint_stan")
    private Double piOldComplaintStan; // 노후화대응 민원 및 사고 대응성 표준 가중치

    @Column(name="pi_old_complaint_min")
    private Double piOldComplaintMin; // 노후화대응 민원 및 사고 대응성 최소 가중치

    @Column(name="pi_old_complaint_max")
    private Double piOldComplaintMax; // 노후화대응 민원 및 사고 대응성 최대 가중치

    @Column(name="pi_old_business_effect_stan")
    private Double piOldBusinessEffectStan; // 노후화대응 사업효과 범용성 표준 가중치

    @Column(name="pi_old_business_effect_min")
    private Double piOldBusinessEffectMin; // 노후화대응 사업효과 범용성 최소 가중치

    @Column(name="pi_old_business_effect_max")
    private Double piOldBusinessEffectMax; // 노후화대응 사업효과 범용성 최대 가중치


    @Column(name="pi_use_safety_stan")
    private Double piUseSafetyStan; // 사용성변화 안전성 표준 가중치

    @Column(name="pi_use_safety_min")
    private Double piUseSafetyMin; // 사용성변화 안전성 최소 가중치

    @Column(name="pi_use_safety_max")
    private Double piUseSafetyMax; // 사용성변화 안전성 최대 가중치

    @Column(name="pi_use_usability_stan")
    private Double piUseUsabilityStan; // 사용성변화 사용성 표준 가중치

    @Column(name="pi_use_usability_min")
    private Double piUseUsabilityMin; // 사용성변화 사용성 최소 가중치

    @Column(name="pi_use_usability_max")
    private Double piUseUsabilityMax; // 사용성변화 사용성 최대 가중치

    @Column(name="pi_use_old_stan")
    private Double piUseOldStan; // 사용성변화 노후도 표준 가중치

    @Column(name="pi_use_old_min")
    private Double piUseOldMin; // 사용성변화 노후도 최소 가중치

    @Column(name="pi_use_old_max")
    private Double piUseOldMax; // 사용성변화 노후도 최대 가중치


    @Column(name="pi_use_business_scale_rank_stan")
    private Double piUseBusinessScaleRankStan; // 사용성변화 사업규모 등급 표준 가중치

    @Column(name="pi_use_business_scale_rank_min")
    private Double piUseBusinessScaleRankMin; // 사용성변화 사업규모 등급 최소 가중치

    @Column(name="pi_use_business_scale_rank_max")
    private Double piUseBusinessScaleRankMax; // 사용성변화 사업규모 등급 최대 가중치

    @Column(name="pi_use_business_effect_rank_stan")
    private Double piUseBusinessEffectRankStan; // 사용성변화 사업효율 등급 표준 가중치

    @Column(name="pi_use_business_effect_rank_min")
    private Double piUseBusinessEffectRankMin; // 사용성변화 사업효율 등급 최소 가중치

    @Column(name="pi_use_business_effect_rank_max")
    private Double piUseBusinessEffectRankMax; // 사용성변화 사업효율 등급 최대 가중치


    @Column(name="pi_use_business_stan")
    private Double piUseBusinessStan; // 사용성변화 사업추진 타당성 표준 가중치

    @Column(name="pi_use_business_min")
    private Double piUseBusinessMin; // 사용성변화 사업추진 타당성 최소 가중치

    @Column(name="pi_use_business_max")
    private Double piUseBusinessMax; // 사용성변화 사업추진 타당성 최대 가중치

    @Column(name="pi_use_complaint_stan")
    private Double piUseComplaintStan; // 사용성변화 민원및사고 대응성 표준 가중치

    @Column(name="pi_use_complaint_min")
    private Double piUseComplaintMin; // 사용성변화 민원및사고 대응성 최소 가중치

    @Column(name="pi_use_complaint_max")
    private Double piUseComplaintMax; // 사용성변화 민원및사고 대응성 최대 가중치

    @Column(name="pi_use_business_effect_stan")
    private Double piUseBusinessEffectStan; // 사용성변화 사업효과 범용성 표준 가중치

    @Column(name="pi_use_business_effect_min")
    private Double piUseBusinessEffectMin; // 사용성변화 사업효과 범용성 최소 가중치

    @Column(name="pi_use_business_effect_max")
    private Double piUseBusinessEffectMax; // 사용성변화 사업효과 범용성 최대 가중치


    @Column(name="pi_base_safety_stan")
    private Double piBaseSafetyStan; // 기준변화 안전성 표준 가중치

    @Column(name="pi_base_safety_min")
    private Double piBaseSafetyMin; // 기준변화 안전성 최소 가중치

    @Column(name="pi_base_safety_max")
    private Double piBaseSafetyMax; // 기준변화 안전성 최대 가중치

    @Column(name="pi_base_old_stan")
    private Double piBaseOldStan; // 기준변화 노후도 표준 가중치

    @Column(name="pi_base_old_min")
    private Double piBaseOldMin; // 기준변화 노후도 최소 가중치

    @Column(name="pi_base_old_max")
    private Double piBaseOldMax; // 기준변화 노후도 최대 가중치


    @Column(name="pi_base_business_scale_rank_stan")
    private Double piBaseBusinessScaleRankStan; // 기준변화 사업규모 등급 표준 가중치

    @Column(name="pi_base_business_scale_rank_min")
    private Double piBaseBusinessScaleRankMin; // 기준변화 사업규모 등급 최소 가중치

    @Column(name="pi_base_business_scale_rank_max")
    private Double piBaseBusinessScaleRankMax; // 기준변화 사업규모 등급 최대 가중치

    @Column(name="pi_base_business_effect_rank_stan")
    private Double piBaseBusinessEffectRankStan; // 기준변화 사업효율 등급 표준 가중치

    @Column(name="pi_base_business_effect_rank_min")
    private Double piBaseBusinessEffectRankMin; // 기준변화 사업효율 등급 최소 가중치

    @Column(name="pi_base_business_effect_rank_max")
    private Double piBaseBusinessEffectRankMax; // 기준변화 사업효율 등급 최대 가중치


    @Column(name="pi_base_business_stan")
    private Double piBaseBusinessStan; // 기준변화 사업추진 타당성 표준 가중치

    @Column(name="pi_base_business_min")
    private Double piBaseBusinessMin; // 기준변화 사업추진 타당성 최소 가중치

    @Column(name="pi_base_business_max")
    private Double piBaseBusinessMax; // 기준변화 사업추진 타당성 최대 가중치

    @Column(name="pi_base_business_effect_stan")
    private Double piBaseBusinessEffectStan; // 기준변화 사업효과 범용성 표준 가중치

    @Column(name="pi_base_business_effect_min")
    private Double piBaseBusinessEffectMin; // 기준변화 사업효과 범용성 최소 가중치

    @Column(name="pi_base_business_effect_max")
    private Double piBaseBusinessEffectMax; // 기준변화 사업효과 범용성 최대 가중치


    @Column(name="modify_id")
    private String modify_id; // 수정한 사용자ID

    @Column(name="modify_date")
    private LocalDateTime modify_date; // 수정날짜

}
