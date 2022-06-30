package com.broadwave.backend.lifetime.detail.cabonationThreePlate;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Minkyu
 * Date : 2022-06-29
 * Time :
 * Remark : 생애주기 의사결전 지원서비스 관련 세부부분 테이블(권기현박사) - 탄산화깊이 바닥판 3개 엔티티
 */
@Entity
@Data
@EqualsAndHashCode(of = "ltCarbonationThreePlateId")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="nd_lt_detail_carbonation_three_plate")
public class CabonationThreePlate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="lt_carbonation_three_plate_id")
    private Long ltCarbonationThreePlateId; // 고유ID값(NOTNULL)

    @Column(name="lt_detail_auto_num")
    private String ltDetailAutoNum; // 고유코드


    @Column(name="lt_td_average_plate_1")
    private Double ltTdAveragePlate1; // 바닥판1의 실측피복두께 평균값

    @Column(name="lt_td_standard_plate_1")
    private Double ltTdStandardPlate1; // 바닥판1의 실측피복두께 표준편차

    @Column(name="lt_a_average_plate_1")
    private Double ltAAveragePlate1; // 바닥판1의 탄산화속도계수 평균값

    @Column(name="lt_a_standard_plate_1")
    private Double ltAStandardPlate1; // 바닥판1의 탄산화속도계수 표준편차

    @Column(name="lt_c_average_plate_1")
    private Double ltCAveragePlate1; // 바닥판1의 탄산화깊이 평균값

    @Column(name="lt_c_standard_plate_1")
    private Double ltCStandardPlate1; // 바닥판1의 탄산화깊이 표준편차


    @Column(name="lt_td_average_plate_2")
    private Double ltTdAveragePlate2; // 바닥판2의 실측피복두께 평균값

    @Column(name="lt_td_standard_plate_2")
    private Double ltTdStandardPlate2; // 바닥판2의 실측피복두께 표준편차

    @Column(name="lt_a_average_plate_2")
    private Double ltAAveragePlate2; // 바닥판2의 탄산화속도계수 평균값

    @Column(name="lt_a_standard_plate_2")
    private Double ltAStandardPlate2; // 바닥판2의 탄산화속도계수 표준편차

    @Column(name="lt_c_average_plate_2")
    private Double ltCAveragePlate2; // 바닥판2의 탄산화깊이 평균값

    @Column(name="lt_c_standard_plate_2")
    private Double ltCStandardPlate2; // 바닥판2의 탄산화깊이 표준편차


    @Column(name="lt_td_average_plate_3")
    private Double ltTdAveragePlate3; // 바닥판3의 실측피복두께 평균값

    @Column(name="lt_td_standard_plate_3")
    private Double ltTdStandardPlate3; // 바닥판3의 실측피복두께 표준편차

    @Column(name="lt_a_average_plate_3")
    private Double ltAAveragePlate3; // 바닥판3의 탄산화속도계수 평균값

    @Column(name="lt_a_standard_plate_3")
    private Double ltAStandardPlate3; // 바닥판3의 탄산화속도계수 표준편차

    @Column(name="lt_c_average_plate_3")
    private Double ltCAveragePlate3; // 바닥판3의 탄산화깊이 평균값

    @Column(name="lt_c_standard_plate_3")
    private Double ltCStandardPlate3; // 바닥판3의 탄산화깊이 표준편차

    @Column(name="lt_y_average")
    private Double ltYAverage; // 공용연수 조정계수 평균값

    @Column(name="lt_y_standard")
    private Double ltYStandard; // 공용연수 조정계수 표준편차

    @Column(name="lt_public_year")
    private Integer ltPublicYear; // 입력 공용연수

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
