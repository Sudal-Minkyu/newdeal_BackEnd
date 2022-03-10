package com.broadwave.backend.performance.reference;

import lombok.*;

/**
 * @author Minkyu
 * Date : 2021-11-02
 * Time :
 * Remark : 뉴딜 성능개선사업평가 관련 테이블(한대석박사) - 관리자전용 페이지 - 성능개선 사업평가 참조테이블 - 정책성 Dto
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class ReferencePolicyMapperDto {

    private Double piPolicyValidityA; // 사업추진 타당성 A등급 최소점수
    private Double piPolicyValidityB; // 사업추진 타당성 B등급 최소점수
    private Double piPolicyValidityC; // 사업추진 타당성 C등급 최소점수
    private Double piPolicyValidityD; // 사업추진 타당성 D등급 최소점수

    private Double piPolicyResponValueA; // 민원 및 사고 대응성 A등급 참조값
    private Double piPolicyResponValueB; // 민원 및 사고 대응성 B등급 참조값
    private Double piPolicyResponValueC; // 민원 및 사고 대응성 C등급 참조값
    private Double piPolicyResponScoreA; // 사업추진 타당성 A등급 환산점수
    private Double piPolicyResponScoreB; // 사업추진 타당성 B등급 환산점수
    private Double piPolicyResponScoreC; // 사업추진 타당성 C등급 환산점수

    private Double piPolicyVersatilityAMin; // 사업효과 범용성 A등급 교통량 최소값
    private Double piPolicyVersatilityAScore; // 사업효과 범용성 A등급 교통량 환산점수
    private Double piPolicyVersatilityBMin; // 사업효과 범용성 B등급 교통량 최소값
    private Double piPolicyVersatilityBMax; // 사업효과 범용성 B등급 교통량 최대값
    private Double piPolicyVersatilityBScore; // 사업효과 범용성 B등급 교통량 환산점수
    private Double piPolicyVersatilityCMin; // 사업효과 범용성 C등급 교통량 최소값
    private Double piPolicyVersatilityCMax; // 사업효과 범용성 C등급 교통량 최대값
    private Double piPolicyVersatilityCScore; // 사업효과 범용성 C등급 교통량 환산점수
    private Double piPolicyVersatilityDMin; // 사업효과 범용성 D등급 교통량 최소값
    private Double piPolicyVersatilityDMax; // 사업효과 범용성 D등급 교통량 최대값
    private Double piPolicyVersatilityDScore; // 사업효과 범용성 D등급 교통량 환산점수
    private Double piPolicyVersatilityEMax; // 사업효과 범용성 E등급 교통량 최대값
    private Double piPolicyVersatilityEScore; // 사업효과 범용성EB등급 교통량 환산점수

    private Double piPolicyGoalAPlusMin; // 종합평가 등급 환산표 A+ 등급 최소점수
    private Double piPolicyGoalAPlusMax; // 종합평가 등급 환산표 A+ 등급 최대점수
    private Double piPolicyGoalAMinusMin; // 종합평가 등급 환산표 A- 등급 최소점수
    private Double piPolicyGoalAMinusMax; // 종합평가 등급 환산표 A- 등급 최대점수
    private Double piPolicyGoalBPlusMin; // 종합평가 등급 환산표 B+ 등급 최소점수
    private Double piPolicyGoalBPlusMax; // 종합평가 등급 환산표 B+ 등급 최대점수
    private Double piPolicyGoalBMinusMin; // 종합평가 등급 환산표 B- 등급 최소점수
    private Double piPolicyGoalBMinusMax; // 종합평가 등급 환산표 B- 등급 최대점수
    private Double piPolicyGoalCPlusMin; // 종합평가 등급 환산표 C+ 등급 최소점수
    private Double piPolicyGoalCPlusMax; // 종합평가 등급 환산표 C+ 등급 최대점수
    private Double piPolicyGoalCMinusMin; // 종합평가 등급 환산표 C- 등급 최소점수
    private Double piPolicyGoalCMinusMax; // 종합평가 등급 환산표 C- 등급 최대점수
    private Double piPolicyGoalDPlusMin; // 종합평가 등급 환산표 D+ 등급 최소점수
    private Double piPolicyGoalDPlusMax; // 종합평가 등급 환산표 D+ 등급 최대점수
    private Double piPolicyGoalDMinusMin; // 종합평가 등급 환산표 D- 등급 최소점수
    private Double piPolicyGoalDMinusMax; // 종합평가 등급 환산표 D- 등급 최대점수
    private Double piPolicyGoalEMax; // 종합평가 등급 환산표 E 등급 최소점수

}
