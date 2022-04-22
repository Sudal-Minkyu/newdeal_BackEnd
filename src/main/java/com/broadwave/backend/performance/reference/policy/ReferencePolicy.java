package com.broadwave.backend.performance.reference.policy;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Minkyu
 * Date : 2021-11-02
 * Time :
 * Remark : 뉴딜 성능개선사업평가 관련 테이블(한대석박사) - 관리자전용 페이지 - 성능개선 사업평가 참조테이블 - 정책성
 */
@Entity
@Data
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="nd_pi_policy")
public class ReferencePolicy {

    @Id
    @Column(name="pi_policy_id")
    private String id; // 고유ID값(NOTNULL)



    @Column(name="pi_policy_validity_a")
    private Double piPolicyValidityA; // 사업추진 타당성 A등급 최소점수

    @Column(name="pi_policy_validity_b")
    private Double piPolicyValidityB; // 사업추진 타당성 B등급 최소점수

    @Column(name="pi_policy_validity_c")
    private Double piPolicyValidityC; // 사업추진 타당성 C등급 최소점수

    @Column(name="pi_policy_validity_d")
    private Double piPolicyValidityD; // 사업추진 타당성 D등급 최소점수


    @Column(name="pi_policy_respon_value_a")
    private Double piPolicyResponValueA; // 민원 및 사고 대응성 A등급 참조값

    @Column(name="pi_policy_respon_value_b")
    private Double piPolicyResponValueB; // 민원 및 사고 대응성 B등급 참조값

    @Column(name="pi_policy_respon_value_c")
    private Double piPolicyResponValueC; // 민원 및 사고 대응성 C등급 참조값

    @Column(name="pi_policy_respon_score_a")
    private Double piPolicyResponScoreA; // 사업추진 타당성 A등급 환산점수

    @Column(name="pi_policy_respon_score_b")
    private Double piPolicyResponScoreB; // 사업추진 타당성 B등급 환산점수

    @Column(name="pi_policy_respon_score_c")
    private Double piPolicyResponScoreC; // 사업추진 타당성 C등급 환산점수



    @Column(name="pi_policy_versatility_a_min")
    private Double piPolicyVersatilityAMin; // 사업효과 범용성 A등급 교통량 최소값

    @Column(name="pi_policy_versatility_a_score")
    private Double piPolicyVersatilityAScore; // 사업효과 범용성 A등급 교통량 환산점수

    @Column(name="pi_policy_versatility_b_min")
    private Double piPolicyVersatilityBMin; // 사업효과 범용성 B등급 교통량 최소값

    @Column(name="pi_policy_versatility_b_max")
    private Double piPolicyVersatilityBMax; // 사업효과 범용성 B등급 교통량 최대값

    @Column(name="pi_policy_versatility_b_score")
    private Double piPolicyVersatilityBScore; // 사업효과 범용성 B등급 교통량 환산점수

    @Column(name="pi_policy_versatility_c_min")
    private Double piPolicyVersatilityCMin; // 사업효과 범용성 C등급 교통량 최소값

    @Column(name="pi_policy_versatility_c_max")
    private Double piPolicyVersatilityCMax; // 사업효과 범용성 C등급 교통량 최대값

    @Column(name="pi_policy_versatility_c_score")
    private Double piPolicyVersatilityCScore; // 사업효과 범용성 C등급 교통량 환산점수

    @Column(name="pi_policy_versatility_d_min")
    private Double piPolicyVersatilityDMin; // 사업효과 범용성 D등급 교통량 최소값

    @Column(name="pi_policy_versatility_d_max")
    private Double piPolicyVersatilityDMax; // 사업효과 범용성 D등급 교통량 최대값

    @Column(name="pi_policy_versatility_d_score")
    private Double piPolicyVersatilityDScore; // 사업효과 범용성 D등급 교통량 환산점수

    @Column(name="pi_policy_versatility_e_max")
    private Double piPolicyVersatilityEMax; // 사업효과 범용성 E등급 교통량 최대값

    @Column(name="pi_policy_versatility_e_score")
    private Double piPolicyVersatilityEScore; // 사업효과 범용성EB등급 교통량 환산점수


    @Column(name="pi_policy_goal_a_plus_min")
    private Double piPolicyGoalAPlusMin; // 종합평가 등급 환산표 A+ 등급 최소점수

    @Column(name="pi_policy_goal_a_plus_max")
    private Double piPolicyGoalAPlusMax; // 종합평가 등급 환산표 A+ 등급 최대점수

    @Column(name="pi_policy_goal_a_minus_min")
    private Double piPolicyGoalAMinusMin; // 종합평가 등급 환산표 A- 등급 최소점수

    @Column(name="pi_policy_goal_a_minus_max")
    private Double piPolicyGoalAMinusMax; // 종합평가 등급 환산표 A- 등급 최대점수

    @Column(name="pi_policy_goal_b_plus_min")
    private Double piPolicyGoalBPlusMin; // 종합평가 등급 환산표 B+ 등급 최소점수

    @Column(name="pi_policy_goal_b_plus_max")
    private Double piPolicyGoalBPlusMax; // 종합평가 등급 환산표 B+ 등급 최대점수

    @Column(name="pi_policy_goal_b_minus_min")
    private Double piPolicyGoalBMinusMin; // 종합평가 등급 환산표 B- 등급 최소점수

    @Column(name="pi_policy_goal_b_minus_max")
    private Double piPolicyGoalBMinusMax; // 종합평가 등급 환산표 B- 등급 최대점수

    @Column(name="pi_policy_goal_c_plus_min")
    private Double piPolicyGoalCPlusMin; // 종합평가 등급 환산표 C+ 등급 최소점수

    @Column(name="pi_policy_goal_c_plus_max")
    private Double piPolicyGoalCPlusMax; // 종합평가 등급 환산표 C+ 등급 최대점수

    @Column(name="pi_policy_goal_c_minus_min")
    private Double piPolicyGoalCMinusMin; // 종합평가 등급 환산표 C- 등급 최소점수

    @Column(name="pi_policy_goal_c_minus_max")
    private Double piPolicyGoalCMinusMax; // 종합평가 등급 환산표 C- 등급 최대점수

    @Column(name="pi_policy_goal_d_plus_min")
    private Double piPolicyGoalDPlusMin; // 종합평가 등급 환산표 D+ 등급 최소점수

    @Column(name="pi_policy_goal_d_plus_max")
    private Double piPolicyGoalDPlusMax; // 종합평가 등급 환산표 D+ 등급 최대점수

    @Column(name="pi_policy_goal_d_minus_min")
    private Double piPolicyGoalDMinusMin; // 종합평가 등급 환산표 D- 등급 최소점수

    @Column(name="pi_policy_goal_d_minus_max")
    private Double piPolicyGoalDMinusMax; // 종합평가 등급 환산표 D- 등급 최대점수

    @Column(name="pi_policy_goal_e_max")
    private Double piPolicyGoalEMax; // 종합평가 등급 환산표 E 등급 최소점수



    @Column(name="insert_date")
    private LocalDateTime insertDateTime; // 저장날짜

    @Column(name="insert_id")
    private String insert_id; // 저장 사용자ID

}
