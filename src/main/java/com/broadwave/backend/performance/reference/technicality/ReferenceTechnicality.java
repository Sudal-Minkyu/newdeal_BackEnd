package com.broadwave.backend.performance.reference.technicality;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Minkyu
 * Date : 2021-10-29
 * Time :
 * Remark : 뉴딜 성능개선사업평가 관련 테이블(한대석박사) - 관리자전용 페이지 - 성능개선 사업평가 참조테이블 - 기술성
 */
@Entity
@Data
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="nd_pi_technicality")
public class ReferenceTechnicality {

    @Id
    @Column(name="pi_tech_id")
    private String id; // 고유ID값(NOTNULL)



    @Column(name="pi_tech_safety_ae")
    private Double piTechSafetyAe; // 안전성 평가등급 A, 안전등급 E 일때,

    @Column(name="pi_tech_safety_bd")
    private Double piTechSafetyBd; // 안전성 평가등급 B, 안전등급 D 일때,

    @Column(name="pi_tech_safety_cc")
    private Double piTechSafetyCc; // 안전성 평가등급 C, 안전등급 C 일때,

    @Column(name="pi_tech_safety_db")
    private Double piTechSafetyDb; // 안전성 평가등급 D, 안전등급 B 일때,

    @Column(name="pi_tech_safety_ea")
    private Double piTechSafetyEa; // 안전성 평가등급 E, 안전등급 A 일때,



    @Column(name="pi_tech_old_a_min")
    private Double piTechOldAMin; // 노후도 A등급 최소값

    @Column(name="pi_tech_old_a_score")
    private Double piTechOldAScore; // 노후도 A등급 환산점수

    @Column(name="pi_tech_old_b_min")
    private Double piTechOldBMin; // 노후도 B등급 최소값

    @Column(name="pi_tech_old_b_max")
    private Double piTechOldBMax; // 노후도 B등급 최대값

    @Column(name="pi_tech_old_b_score")
    private Double piTechOldBScore; // 노후도 B등급 환산점수

    @Column(name="pi_tech_old_c_min")
    private Double piTechOldCMin; // 노후도 C등급 최소값

    @Column(name="pi_tech_old_c_max")
    private Double piTechOldCMax; // 노후도 C등급 최대값

    @Column(name="pi_tech_old_c_score")
    private Double piTechOldCScore; // 노후도 C등급 환산점수

    @Column(name="pi_tech_old_d_min")
    private Double piTechOldDMin; // 노후도 D등급 최소값

    @Column(name="pi_tech_old_d_max")
    private Double piTechOldDMax; // 노후도 D등급 최대값

    @Column(name="pi_tech_old_d_score")
    private Double piTechOldDScore; // 노후도 D등급 환산점수

    @Column(name="pi_tech_old_e_max")
    private Double piTechOldEMax; // 노후도 E등급 최대값

    @Column(name="pi_tech_old_e_score")
    private Double piTechOldEScore; // 노후도 E등급 환산점수



    @Column(name="pi_tech_usability_ae")
    private Double piTechUsabilityAe; // 사용성 평가등급 A, 사용성등급 E 일때, 환산점수

    @Column(name="pi_tech_usability_bd")
    private Double piTechUsabilityBd; // 사용성 평가등급 B, 사용성등급 D 일때, 환산점수

    @Column(name="pi_tech_usability_cc")
    private Double piTechUsabilityCc; // 사용성 평가등급 C, 사용성등급 C 일때, 환산점수

    @Column(name="pi_tech_usability_db")
    private Double piTechUsabilityDb; // 사용성 평가등급 D, 사용성등급 B 일때, 환산점수

    @Column(name="pi_tech_usability_ea")
    private Double piTechUsabilityEa; // 사용성 평가등급 E, 사용성등급 A 일때, 환산점수



    @Column(name="pi_tech_retardation_a0")
    private Double piTechRetardationA0; // 지체도 안전등급 A, 지연기간 0 일때, 환산점수

    @Column(name="pi_tech_retardation_a1")
    private Double piTechRetardationA1; // 지체도 안전등급 A, 지연기간 1 일때, 환산점수

    @Column(name="pi_tech_retardation_a2")
    private Double piTechRetardationA2; // 지체도 안전등급 A, 지연기간 2 일때, 환산점수

    @Column(name="pi_tech_retardation_a3")
    private Double piTechRetardationA3; // 지체도 안전등급 A, 지연기간 3 일때, 환산점수

    @Column(name="pi_tech_retardation_a4")
    private Double piTechRetardationA4; // 지체도 안전등급 A, 지연기간 4 일때, 환산점수

    @Column(name="pi_tech_retardation_b0")
    private Double piTechRetardationB0; // 지체도 안전등급 B, 지연기간 0 일때, 환산점수

    @Column(name="pi_tech_retardation_b1")
    private Double piTechRetardationB1; // 지체도 안전등급 B, 지연기간 1 일때, 환산점수

    @Column(name="pi_tech_retardation_b2")
    private Double piTechRetardationB2; // 지체도 안전등급 B, 지연기간 2 일때, 환산점수

    @Column(name="pi_tech_retardation_b3")
    private Double piTechRetardationB3; // 지체도 안전등급 B, 지연기간 3 일때, 환산점수

    @Column(name="pi_tech_retardation_b4")
    private Double piTechRetardationB4; // 지체도 안전등급 B, 지연기간 4 일때, 환산점수

    @Column(name="pi_tech_retardation_c0")
    private Double piTechRetardationC0; // 지체도 안전등급 C, 지연기간 0 일때, 환산점수

    @Column(name="pi_tech_retardation_c1")
    private Double piTechRetardationC1; // 지체도 안전등급 C, 지연기간 1 일때, 환산점수

    @Column(name="pi_tech_retardation_c2")
    private Double piTechRetardationC2; // 지체도 안전등급 C, 지연기간 2 일때, 환산점수

    @Column(name="pi_tech_retardation_c3")
    private Double piTechRetardationC3; // 지체도 안전등급 C, 지연기간 3 일때, 환산점수

    @Column(name="pi_tech_retardation_c4")
    private Double piTechRetardationC4; // 지체도 안전등급 C, 지연기간 4 일때, 환산점수

    @Column(name="pi_tech_retardation_d0")
    private Double piTechRetardationD0; // 지체도 안전등급 D, 지연기간 0 일때, 환산점수

    @Column(name="pi_tech_retardation_d1")
    private Double piTechRetardationD1; // 지체도 안전등급 D, 지연기간 1 일때, 환산점수

    @Column(name="pi_tech_retardation_d2")
    private Double piTechRetardationD2; // 지체도 안전등급 D, 지연기간 2 일때, 환산점수

    @Column(name="pi_tech_retardation_d3")
    private Double piTechRetardationD3; // 지체도 안전등급 D, 지연기간 3 일때, 환산점수

    @Column(name="pi_tech_retardation_d4")
    private Double piTechRetardationD4; // 지체도 안전등급 D, 지연기간 4 일때, 환산점수

    @Column(name="pi_tech_retardation_e0")
    private Double piTechRetardationE0; // 지체도 안전등급 E, 지연기간 0 일때, 환산점수

    @Column(name="pi_tech_retardation_e1")
    private Double piTechRetardationE1; // 지체도 안전등급 E, 지연기간 1 일때, 환산점수

    @Column(name="pi_tech_retardation_e2")
    private Double piTechRetardationE2; // 지체도 안전등급 E, 지연기간 2 일때, 환산점수

    @Column(name="pi_tech_retardation_e3")
    private Double piTechRetardationE3; // 지체도 안전등급 E, 지연기간 3 일때, 환산점수

    @Column(name="pi_tech_retardation_e4")
    private Double piTechRetardationE4; // 지체도 안전등급 E, 지연기간 4 일때, 환산점수



    @Column(name="pi_tech_performance_aa")
    private Double piTechPerformanceAa; // 목표성능 달성도 안전등급 A, 목표등급 A 일때, 환산점수

    @Column(name="pi_tech_performance_ab")
    private Double piTechPerformanceAb; // 목표성능 달성도 안전등급 A, 목표등급 B 일때, 환산점수

    @Column(name="pi_tech_performance_ac")
    private Double piTechPerformanceAc; // 목표성능 달성도 안전등급 A, 목표등급 C 일때, 환산점수

    @Column(name="pi_tech_performance_ba")
    private Double piTechPerformanceBa; // 목표성능 달성도 안전등급 B, 목표등급 A 일때, 환산점수

    @Column(name="pi_tech_performance_bb")
    private Double piTechPerformanceBb; // 목표성능 달성도 안전등급 B, 목표등급 B 일때, 환산점수

    @Column(name="pi_tech_performance_bc")
    private Double piTechPerformanceBc; // 목표성능 달성도 안전등급 B, 목표등급 C 일때, 환산점수

    @Column(name="pi_tech_performance_ca")
    private Double piTechPerformanceCa; // 목표성능 달성도 안전등급 C, 목표등급 A 일때, 환산점수

    @Column(name="pi_tech_performance_cb")
    private Double piTechPerformanceCb; // 목표성능 달성도 안전등급 C, 목표등급 B 일때, 환산점수

    @Column(name="pi_tech_performance_cc")
    private Double piTechPerformanceCc; // 목표성능 달성도 안전등급 C, 목표등급 C 일때, 환산점수

    @Column(name="pi_tech_performance_da")
    private Double piTechPerformanceDa; // 목표성능 달성도 안전등급 D, 목표등급 A 일때, 환산점수

    @Column(name="pi_tech_performance_db")
    private Double piTechPerformanceDb; // 목표성능 달성도 안전등급 D, 목표등급 B 일때, 환산점수

    @Column(name="pi_tech_performance_dc")
    private Double piTechPerformanceDc; // 목표성능 달성도 안전등급 D, 목표등급 C 일때, 환산점수

    @Column(name="pi_tech_performance_ea")
    private Double piTechPerformanceEa; // 목표성능 달성도 안전등급 E, 목표등급 A 일때, 환산점수

    @Column(name="pi_tech_performance_eb")
    private Double piTechPerformanceEb; // 목표성능 달성도 안전등급 E, 목표등급 B 일때, 환산점수

    @Column(name="pi_tech_performance_ec")
    private Double piTechPerformanceEc; // 목표성능 달성도 안전등급 E, 목표등급 C 일때, 환산점수

    
    
    @Column(name="pi_tech_goal_a_plus_min")
    private Double piTechGoalAPlusMin; // 종합평가 등급 환산표 A+ 등급 최소점수

    @Column(name="pi_tech_goal_a_plus_max")
    private Double piTechGoalAPlusMax; // 종합평가 등급 환산표 A+ 등급 최대점수

    @Column(name="pi_tech_goal_a_minus_min")
    private Double piTechGoalAMinusMin; // 종합평가 등급 환산표 A- 등급 최소점수

    @Column(name="pi_tech_goal_a_minus_max")
    private Double piTechGoalAMinusMax; // 종합평가 등급 환산표 A- 등급 최대점수

    @Column(name="pi_tech_goal_b_plus_min")
    private Double piTechGoalBPlusMin; // 종합평가 등급 환산표 B+ 등급 최소점수

    @Column(name="pi_tech_goal_b_plus_max")
    private Double piTechGoalBPlusMax; // 종합평가 등급 환산표 B+ 등급 최대점수

    @Column(name="pi_tech_goal_b_minus_min")
    private Double piTechGoalBMinusMin; // 종합평가 등급 환산표 B- 등급 최소점수

    @Column(name="pi_tech_goal_b_minus_max")
    private Double piTechGoalBMinusMax; // 종합평가 등급 환산표 B- 등급 최대점수

    @Column(name="pi_tech_goal_c_plus_min")
    private Double piTechGoalCPlusMin; // 종합평가 등급 환산표 C+ 등급 최소점수

    @Column(name="pi_tech_goal_c_plus_max")
    private Double piTechGoalCPlusMax; // 종합평가 등급 환산표 C+ 등급 최대점수

    @Column(name="pi_tech_goal_c_minus_min")
    private Double piTechGoalCMinusMin; // 종합평가 등급 환산표 C- 등급 최소점수

    @Column(name="pi_tech_goal_c_minus_max")
    private Double piTechGoalCMinusMax; // 종합평가 등급 환산표 C- 등급 최대점수

    @Column(name="pi_tech_goal_d_plus_min")
    private Double piTechGoalDPlusMin; // 종합평가 등급 환산표 D+ 등급 최소점수

    @Column(name="pi_tech_goal_d_plus_max")
    private Double piTechGoalDPlusMax; // 종합평가 등급 환산표 D+ 등급 최대점수

    @Column(name="pi_tech_goal_d_minus_min")
    private Double piTechGoalDMinusMin; // 종합평가 등급 환산표 D- 등급 최소점수

    @Column(name="pi_tech_goal_d_minus_max")
    private Double piTechGoalDMinusMax; // 종합평가 등급 환산표 D- 등급 최대점수

    @Column(name="pi_tech_goal_e_max")
    private Double piTechGoalEMax; // 종합평가 등급 환산표 E 등급 최소점수



    @Column(name="insert_date")
    private LocalDateTime insertDateTime; // 저장날짜

    @Column(name="insert_id")
    private String insert_id; // 저장 사용자ID

}
