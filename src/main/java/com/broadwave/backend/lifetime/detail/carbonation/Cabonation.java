package com.broadwave.backend.lifetime.detail.carbonation;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Minkyu
 * Date : 2022-05-23
 * Time :
 * Remark : 생애주기 의사결전 지원서비스 관련 세부부분 테이블(권기현박사) - 탄산화깊이 엔티티
 */
@Entity
@Data
@EqualsAndHashCode(of = "ltCarbonationId")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="nd_lt_detail_cabonation")
public class Cabonation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="lt_carbonation_id")
    private Long ltCarbonationId; // 고유ID값(NOTNULL)

    @Column(name="lt_detail_auto_num")
    private String ltDetailAutoNum; // 고유코드

    @Column(name="lt_td_average")
    private Double ltTdAverage; // 실측피복두께 평균값

    @Column(name="lt_td_standard")
    private Double ltTdStandard; // 실측피복두께 표준편차

    @Column(name="lt_td_variance")
    private Double ltTdVariance; // 실측피복두께 변동계수


    @Column(name="lt_y_average")
    private Double ltYAverage; // 공용연수 평균값

    @Column(name="lt_y_standard")
    private Double ltYStandard; // 공용연수 표준편차

    @Column(name="lt_y_variance")
    private Double ltYVariance; // 공용연수 변동계수


    @Column(name="lt_a_average")
    private Double ltAAverage; // 탄산화속도계수 평균값

    @Column(name="lt_a_standard")
    private Double ltAStandard; // 탄산화속도계수 표준편차

    @Column(name="lt_a_variance")
    private Double ltAVariance; // 탄산화속도계수 변동계수


    @Column(name="lt_c_average")
    private Double ltCAverage; // 탄산화깊이 평균값

    @Column(name="lt_c_standard")
    private Double ltCStandard; // 탄산화깊이 표준편차

    @Column(name="lt_c_variance")
    private Double ltCVariance; // 탄산화깊이 변동계수


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
