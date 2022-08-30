package com.broadwave.backend.lifetime.detail.crack;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Minkyu
 * Date : 2022-08-04
 * Time :
 * Remark : 생애주기 의사결전 지원서비스 관련 세부부분 테이블(권기현박사) - 균열깊이 엔티티
 */
@Entity
@Data
@EqualsAndHashCode(of = "ltCrackId")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="nd_lt_detail_crack")
public class Crack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="lt_crack_id")
    private Long ltCrackId; // 고유ID값(NOTNULL)

    @Column(name="lt_detail_auto_num")
    private String ltDetailAutoNum; // 고유코드


    @Column(name="lt_td_average")
    private Double ltTdAverage; // 설계피복두깨 평균값

    @Column(name="lt_td_standard")
    private Double ltTdStandard; // 설계피복두깨 표준편차

    @Column(name="lt_td_variance")
    private Double ltTdVariance; // 설계피복두깨 변동계수


    @Column(name="lt_l_average")
    private Double ltLAverage; // 발신수신자 사이의 거리 평균값

    @Column(name="lt_l_standard")
    private Double ltLStandard; // 발신수신자 사이의 거리 표준편차

    @Column(name="lt_l_variance")
    private Double ltLVariance; // 발신수신자 사이의 거리 변동계수


    @Column(name="lt_tc_average")
    private Double ltTcAverage; // 균열있는경우 전달시간 평균값

    @Column(name="lt_tc_standard")
    private Double ltTcStandard; // 균열있는경우 전달시간 표준편차

    @Column(name="lt_tc_variance")
    private Double ltTcVariance; // 균열있는경우 전달시간 변동계수


    @Column(name="lt_to_average")
    private Double ltToAverage; // 균열없는경우 전달시간 평균값

    @Column(name="lt_to_standard")
    private Double ltToStandard; // 균열없는경우 전달시간 표준편차

    @Column(name="lt_to_variance")
    private Double ltToVariance; // 균열없는경우 전달시간 변동계수


    @Column(name="lt_public_year")
    private Integer ltPublicYear; // 공용연수

    @Column(name="lt_simulation")
    private Integer ltSimulation; // 시뮬레이션 횟수

    @Column(name="lt_repair_length")
    private Double ltRepairLength; // 보수보강 총길이

    @Column(name="lt_target_value")
    private Double ltTargetValue; // 생애주기 목표값

    @Column(name="lt_recovery_percent")
    private Double ltRecoveryPercent; // 보수보강 회복율


    @Column(name="lt_recovery_one")
    private Double ltRecoveryOne; // 보수보강 회복율_1

    @Column(name="lt_recovery_two")
    private Double ltRecoveryTwo; // 보수보강 회복율_2

    @Column(name="lt_recovery_three")
    private Double ltRecoveryThree; // 보수보강 회복율_3

    @Column(name="lt_recovery_four")
    private Double ltRecoveryFour; // 보수보강 회복율_4

    @Column(name="lt_recovery_five")
    private Double ltRecoveryFive; // 보수보강 회복율_5

    @Column(name="lt_recovery_six")
    private Double ltRecoverySix; // 보수보강 회복율_6


    @Column(name="lt_cost_one")
    private Double ltCostOne; // 보수보강 비용_1

    @Column(name="lt_cost_two")
    private Double ltCostTwo; // 보수보강 비용_2

    @Column(name="lt_cost_three")
    private Double ltCostThree; // 보수보강 비용_3

    @Column(name="lt_cost_four")
    private Double ltCostFour; // 보수보강 비용_4

    @Column(name="lt_cost_five")
    private Double ltCostFive; // 보수보강 비용_5

    @Column(name="lt_cost_six")
    private Double ltCostSix; // 보수보강비용_6


    @Column(name="insert_date")
    private LocalDateTime insertDateTime;

    @Column(name="insert_id")
    private String insert_id;

    @Column(name="modify_date")
    private LocalDateTime modifyDateTime;

    @Column(name="modify_id")
    private String modify_id;

}
