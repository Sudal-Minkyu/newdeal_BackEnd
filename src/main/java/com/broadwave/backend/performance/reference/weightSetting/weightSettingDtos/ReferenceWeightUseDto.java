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
 * Remark : 뉴딜 성능개선사업평가 관련 테이블(한대석박사) - 사용성변화의 대한 가중치
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class ReferenceWeightUseDto {

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

}
