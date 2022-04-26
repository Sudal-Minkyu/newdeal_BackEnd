package com.broadwave.backend.performance.reference.weightSetting.weightSettingDtos;

import lombok.*;

/**
 * @author Minkyu
 * Date : 2022-04-25
 * Time :
 * Remark : 뉴딜 성능개선사업평가 관련 테이블(한대석박사) - 기준변화의 대한 가중치
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class ReferenceWeightBaseDto {

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
