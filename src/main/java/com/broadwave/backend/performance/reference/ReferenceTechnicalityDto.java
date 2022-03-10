package com.broadwave.backend.performance.reference;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @author Minkyu
 * Date : 2021-10-29
 * Time :
 * Remark : 뉴딜 성능개선사업평가 관련 테이블(한대석박사) - 관리자전용 페이지 - 성능개선 사업평가 참조테이블 - 기술성
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
public class ReferenceTechnicalityDto {

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

    public String getPiTechSafetyAe() {
        return String.valueOf(piTechSafetyAe);
    }

    public Double getPiTechSafetyBd() {
        return piTechSafetyBd;
    }

    public Double getPiTechSafetyCc() {
        return piTechSafetyCc;
    }

    public Double getPiTechSafetyDb() {
        return piTechSafetyDb;
    }

    public Double getPiTechSafetyEa() {
        return piTechSafetyEa;
    }

    public Double getPiTechOldAMin() {
        return piTechOldAMin;
    }

    public Double getPiTechOldAScore() {
        return piTechOldAScore;
    }

    public Double getPiTechOldBMin() {
        return piTechOldBMin;
    }

    public Double getPiTechOldBMax() {
        return piTechOldBMax;
    }

    public Double getPiTechOldBScore() {
        return piTechOldBScore;
    }

    public Double getPiTechOldCMin() {
        return piTechOldCMin;
    }

    public Double getPiTechOldCMax() {
        return piTechOldCMax;
    }

    public Double getPiTechOldCScore() {
        return piTechOldCScore;
    }

    public Double getPiTechOldDMin() {
        return piTechOldDMin;
    }

    public Double getPiTechOldDMax() {
        return piTechOldDMax;
    }

    public Double getPiTechOldDScore() {
        return piTechOldDScore;
    }

    public Double getPiTechOldEMax() {
        return piTechOldEMax;
    }

    public Double getPiTechOldEScore() {
        return piTechOldEScore;
    }

    public Double getPiTechUsabilityAe() {
        return piTechUsabilityAe;
    }

    public Double getPiTechUsabilityBd() {
        return piTechUsabilityBd;
    }

    public Double getPiTechUsabilityCc() {
        return piTechUsabilityCc;
    }

    public Double getPiTechUsabilityDb() {
        return piTechUsabilityDb;
    }

    public Double getPiTechUsabilityEa() {
        return piTechUsabilityEa;
    }

    public Double getPiTechRetardationA0() {
        return piTechRetardationA0;
    }

    public Double getPiTechRetardationA1() {
        return piTechRetardationA1;
    }

    public Double getPiTechRetardationA2() {
        return piTechRetardationA2;
    }

    public Double getPiTechRetardationA3() {
        return piTechRetardationA3;
    }

    public Double getPiTechRetardationA4() {
        return piTechRetardationA4;
    }

    public Double getPiTechRetardationB0() {
        return piTechRetardationB0;
    }

    public Double getPiTechRetardationB1() {
        return piTechRetardationB1;
    }

    public Double getPiTechRetardationB2() {
        return piTechRetardationB2;
    }

    public Double getPiTechRetardationB3() {
        return piTechRetardationB3;
    }

    public Double getPiTechRetardationB4() {
        return piTechRetardationB4;
    }

    public Double getPiTechRetardationC0() {
        return piTechRetardationC0;
    }

    public Double getPiTechRetardationC1() {
        return piTechRetardationC1;
    }

    public Double getPiTechRetardationC2() {
        return piTechRetardationC2;
    }

    public Double getPiTechRetardationC3() {
        return piTechRetardationC3;
    }

    public Double getPiTechRetardationC4() {
        return piTechRetardationC4;
    }

    public Double getPiTechRetardationD0() {
        return piTechRetardationD0;
    }

    public Double getPiTechRetardationD1() {
        return piTechRetardationD1;
    }

    public Double getPiTechRetardationD2() {
        return piTechRetardationD2;
    }

    public Double getPiTechRetardationD3() {
        return piTechRetardationD3;
    }

    public Double getPiTechRetardationD4() {
        return piTechRetardationD4;
    }

    public Double getPiTechRetardationE0() {
        return piTechRetardationE0;
    }

    public Double getPiTechRetardationE1() {
        return piTechRetardationE1;
    }

    public Double getPiTechRetardationE2() {
        return piTechRetardationE2;
    }

    public Double getPiTechRetardationE3() {
        return piTechRetardationE3;
    }

    public Double getPiTechRetardationE4() {
        return piTechRetardationE4;
    }

    public Double getPiTechPerformanceAa() {
        return piTechPerformanceAa;
    }

    public Double getPiTechPerformanceAb() {
        return piTechPerformanceAb;
    }

    public Double getPiTechPerformanceAc() {
        return piTechPerformanceAc;
    }

    public Double getPiTechPerformanceBa() {
        return piTechPerformanceBa;
    }

    public Double getPiTechPerformanceBb() {
        return piTechPerformanceBb;
    }

    public Double getPiTechPerformanceBc() {
        return piTechPerformanceBc;
    }

    public Double getPiTechPerformanceCa() {
        return piTechPerformanceCa;
    }

    public Double getPiTechPerformanceCb() {
        return piTechPerformanceCb;
    }

    public Double getPiTechPerformanceCc() {
        return piTechPerformanceCc;
    }

    public Double getPiTechPerformanceDa() {
        return piTechPerformanceDa;
    }

    public Double getPiTechPerformanceDb() {
        return piTechPerformanceDb;
    }

    public Double getPiTechPerformanceDc() {
        return piTechPerformanceDc;
    }

    public Double getPiTechPerformanceEa() {
        return piTechPerformanceEa;
    }

    public Double getPiTechPerformanceEb() {
        return piTechPerformanceEb;
    }

    public Double getPiTechPerformanceEc() {
        return piTechPerformanceEc;
    }

    public Double getPiTechGoalAPlusMin() {
        return piTechGoalAPlusMin;
    }

    public Double getPiTechGoalAPlusMax() {
        return piTechGoalAPlusMax;
    }

    public Double getPiTechGoalAMinusMin() {
        return piTechGoalAMinusMin;
    }

    public Double getPiTechGoalAMinusMax() {
        return piTechGoalAMinusMax;
    }

    public Double getPiTechGoalBPlusMin() {
        return piTechGoalBPlusMin;
    }

    public Double getPiTechGoalBPlusMax() {
        return piTechGoalBPlusMax;
    }

    public Double getPiTechGoalBMinusMin() {
        return piTechGoalBMinusMin;
    }

    public Double getPiTechGoalBMinusMax() {
        return piTechGoalBMinusMax;
    }

    public Double getPiTechGoalCPlusMin() {
        return piTechGoalCPlusMin;
    }

    public Double getPiTechGoalCPlusMax() {
        return piTechGoalCPlusMax;
    }

    public Double getPiTechGoalCMinusMin() {
        return piTechGoalCMinusMin;
    }

    public Double getPiTechGoalCMinusMax() {
        return piTechGoalCMinusMax;
    }

    public Double getPiTechGoalDPlusMin() {
        return piTechGoalDPlusMin;
    }

    public Double getPiTechGoalDPlusMax() {
        return piTechGoalDPlusMax;
    }

    public Double getPiTechGoalDMinusMin() {
        return piTechGoalDMinusMin;
    }

    public Double getPiTechGoalDMinusMax() {
        return piTechGoalDMinusMax;
    }

    public Double getPiTechGoalEMax() {
        return piTechGoalEMax;
    }
}
