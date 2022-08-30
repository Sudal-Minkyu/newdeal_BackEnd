package com.broadwave.backend.lifetime.detail.chloride;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Minkyu
 * Date : 2022-08-04
 * Time :
 * Remark : 생애주기 의사결전 지원서비스 관련 세부부분 테이블(권기현박사) - 열화물침투량 엔티티
 */
@Entity
@Data
@EqualsAndHashCode(of = "ltChlorideId")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="nd_lt_detail_chloride")
public class Chloride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="lt_chloride_id")
    private Long ltChlorideId; // 고유ID값(NOTNULL)

    @Column(name="lt_detail_auto_num")
    private String ltDetailAutoNum; // 고유코드


    @Column(name="lt_co_average")
    private Double ltCoAverage; // 표면 염화물함유량 평균값

    @Column(name="lt_co_standard")
    private Double ltCoStandard; // 표면 염화물함유량 표준편차

    @Column(name="lt_co_variance")
    private Double ltCoVariance; // 표면 염화물함유량 변동계수


    @Column(name="lt_clim_average")
    private Double ltClimAverage; // 임계 염화물함유량 평균값

    @Column(name="lt_clim_standard")
    private Double ltClimStandard; // 임계 염화물함유량 표준편차

    @Column(name="lt_clim_variance")
    private Double ltClimVariance; // 임계 염화물함유량 변동계수


    @Column(name="lt_d_average")
    private Double ltDAverage; // 염소이온 확산계수 평균값

    @Column(name="lt_d_standard")
    private Double ltDStandard; // 염소이온 확산계수 표준편차

    @Column(name="lt_d_variance")
    private Double ltDVariance; // 염소이온 확산계수 변동계수


    @Column(name="lt_x_average")
    private Double ltXAverage; // 피복두께 평균값

    @Column(name="lt_x_standard")
    private Double ltXStandard; // 피복두께 표준편차

    @Column(name="lt_x_variance")
    private Double ltXVariance; // 피복두께 변동계수


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
