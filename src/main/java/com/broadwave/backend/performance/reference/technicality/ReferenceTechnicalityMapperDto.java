package com.broadwave.backend.performance.reference.technicality;

import lombok.*;

/**
 * @author Minkyu
 * Date : 2021-11-01
 * Time :
 * Remark : 뉴딜 성능개선사업평가 관련 테이블(한대석박사) - 관리자전용 페이지 - 성능개선 사업평가 참조테이블 - 기술성 Dto
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class ReferenceTechnicalityMapperDto {

    private Double piTechSafetyAe; // 안전성 평가등급 A, 안전등급 E 일때,
    private Double piTechSafetyBd; // 안전성 평가등급 B, 안전등급 D 일때,
    private Double piTechSafetyCc; // 안전성 평가등급 C, 안전등급 C 일때,
    private Double piTechSafetyDb; // 안전성 평가등급 D, 안전등급 B 일때,
    private Double piTechSafetyEa; // 안전성 평가등급 E, 안전등급 A 일때,

    private Double piTechOldAMin; // 노후도 A등급 최소값
    private Double piTechOldAScore; // 노후도 A등급 환산점수
    private Double piTechOldBMin; // 노후도 B등급 최소값
    private Double piTechOldBMax; // 노후도 B등급 최대값
    private Double piTechOldBScore; // 노후도 B등급 환산점수
    private Double piTechOldCMin; // 노후도 C등급 최소값
    private Double piTechOldCMax; // 노후도 C등급 최대값
    private Double piTechOldCScore; // 노후도 C등급 환산점수
    private Double piTechOldDMin; // 노후도 D등급 최소값
    private Double piTechOldDMax; // 노후도 D등급 최대값
    private Double piTechOldDScore; // 노후도 D등급 환산점수
    private Double piTechOldEMax; // 노후도 E등급 최대값
    private Double piTechOldEScore; // 노후도 E등급 환산점수

    private Double piTechUsabilityAe; // 사용성 평가등급 A, 사용성등급 E 일때, 환산점수
    private Double piTechUsabilityBd; // 사용성 평가등급 B, 사용성등급 D 일때, 환산점수
    private Double piTechUsabilityCc; // 사용성 평가등급 C, 사용성등급 C 일때, 환산점수
    private Double piTechUsabilityDb; // 사용성 평가등급 D, 사용성등급 B 일때, 환산점수
    private Double piTechUsabilityEa; // 사용성 평가등급 E, 사용성등급 A 일때, 환산점수

    private Double piTechRetardationA0; // 지체도 안전등급 A, 지연기간 0 일때, 환산점수
    private Double piTechRetardationA1; // 지체도 안전등급 A, 지연기간 1 일때, 환산점수
    private Double piTechRetardationA2; // 지체도 안전등급 A, 지연기간 2 일때, 환산점수
    private Double piTechRetardationA3; // 지체도 안전등급 A, 지연기간 3 일때, 환산점수
    private Double piTechRetardationA4; // 지체도 안전등급 A, 지연기간 4 일때, 환산점수
    private Double piTechRetardationB0; // 지체도 안전등급 B, 지연기간 0 일때, 환산점수
    private Double piTechRetardationB1; // 지체도 안전등급 B, 지연기간 1 일때, 환산점수
    private Double piTechRetardationB2; // 지체도 안전등급 B, 지연기간 2 일때, 환산점수
    private Double piTechRetardationB3; // 지체도 안전등급 B, 지연기간 3 일때, 환산점수
    private Double piTechRetardationB4; // 지체도 안전등급 B, 지연기간 4 일때, 환산점수
    private Double piTechRetardationC0; // 지체도 안전등급 C, 지연기간 0 일때, 환산점수
    private Double piTechRetardationC1; // 지체도 안전등급 C, 지연기간 1 일때, 환산점수
    private Double piTechRetardationC2; // 지체도 안전등급 C, 지연기간 2 일때, 환산점수
    private Double piTechRetardationC3; // 지체도 안전등급 C, 지연기간 3 일때, 환산점수
    private Double piTechRetardationC4; // 지체도 안전등급 C, 지연기간 4 일때, 환산점수
    private Double piTechRetardationD0; // 지체도 안전등급 D, 지연기간 0 일때, 환산점수
    private Double piTechRetardationD1; // 지체도 안전등급 D, 지연기간 1 일때, 환산점수
    private Double piTechRetardationD2; // 지체도 안전등급 D, 지연기간 2 일때, 환산점수
    private Double piTechRetardationD3; // 지체도 안전등급 D, 지연기간 3 일때, 환산점수
    private Double piTechRetardationD4; // 지체도 안전등급 D, 지연기간 4 일때, 환산점수
    private Double piTechRetardationE0; // 지체도 안전등급 E, 지연기간 0 일때, 환산점수
    private Double piTechRetardationE1; // 지체도 안전등급 E, 지연기간 1 일때, 환산점수
    private Double piTechRetardationE2; // 지체도 안전등급 E, 지연기간 2 일때, 환산점수
    private Double piTechRetardationE3; // 지체도 안전등급 E, 지연기간 3 일때, 환산점수
    private Double piTechRetardationE4; // 지체도 안전등급 E, 지연기간 4 일때, 환산점수

    private Double piTechPerformanceAa; // 목표성능 달성도 안전등급 A, 목표등급 A 일때, 환산점수
    private Double piTechPerformanceAb; // 목표성능 달성도 안전등급 A, 목표등급 B 일때, 환산점수
    private Double piTechPerformanceAc; // 목표성능 달성도 안전등급 A, 목표등급 C 일때, 환산점수
    private Double piTechPerformanceBa; // 목표성능 달성도 안전등급 B, 목표등급 A 일때, 환산점수
    private Double piTechPerformanceBb; // 목표성능 달성도 안전등급 B, 목표등급 B 일때, 환산점수
    private Double piTechPerformanceBc; // 목표성능 달성도 안전등급 B, 목표등급 C 일때, 환산점수
    private Double piTechPerformanceCa; // 목표성능 달성도 안전등급 C, 목표등급 A 일때, 환산점수
    private Double piTechPerformanceCb; // 목표성능 달성도 안전등급 C, 목표등급 B 일때, 환산점수
    private Double piTechPerformanceCc; // 목표성능 달성도 안전등급 C, 목표등급 C 일때, 환산점수
    private Double piTechPerformanceDa; // 목표성능 달성도 안전등급 D, 목표등급 A 일때, 환산점수
    private Double piTechPerformanceDb; // 목표성능 달성도 안전등급 D, 목표등급 B 일때, 환산점수
    private Double piTechPerformanceDc; // 목표성능 달성도 안전등급 D, 목표등급 C 일때, 환산점수
    private Double piTechPerformanceEa; // 목표성능 달성도 안전등급 E, 목표등급 A 일때, 환산점수
    private Double piTechPerformanceEb; // 목표성능 달성도 안전등급 E, 목표등급 B 일때, 환산점수
    private Double piTechPerformanceEc; // 목표성능 달성도 안전등급 E, 목표등급 C 일때, 환산점수

    private Double piTechGoalAPlusMin; // 종합평가 등급 환산표 A+ 등급 최소점수
    private Double piTechGoalAPlusMax; // 종합평가 등급 환산표 A+ 등급 최대점수
    private Double piTechGoalAMinusMin; // 종합평가 등급 환산표 A- 등급 최소점수
    private Double piTechGoalAMinusMax; // 종합평가 등급 환산표 A- 등급 최대점수
    private Double piTechGoalBPlusMin; // 종합평가 등급 환산표 B+ 등급 최소점수
    private Double piTechGoalBPlusMax; // 종합평가 등급 환산표 B+ 등급 최대점수
    private Double piTechGoalBMinusMin; // 종합평가 등급 환산표 B- 등급 최소점수
    private Double piTechGoalBMinusMax; // 종합평가 등급 환산표 B- 등급 최대점수
    private Double piTechGoalCPlusMin; // 종합평가 등급 환산표 C+ 등급 최소점수
    private Double piTechGoalCPlusMax; // 종합평가 등급 환산표 C+ 등급 최대점수
    private Double piTechGoalCMinusMin; // 종합평가 등급 환산표 C- 등급 최소점수
    private Double piTechGoalCMinusMax; // 종합평가 등급 환산표 C- 등급 최대점수
    private Double piTechGoalDPlusMin; // 종합평가 등급 환산표 D+ 등급 최소점수
    private Double piTechGoalDPlusMax; // 종합평가 등급 환산표 D+ 등급 최대점수
    private Double piTechGoalDMinusMin; // 종합평가 등급 환산표 D- 등급 최소점수
    private Double piTechGoalDMinusMax; // 종합평가 등급 환산표 D- 등급 최대점수
    private Double piTechGoalEMax; // 종합평가 등급 환산표 E 등급 최대점수

}
