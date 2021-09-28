package com.broadwave.backend.lifetime.detail;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Minkyu
 * Date : 2021-09-14
 * Time :
 * Remark : 생애주기 의사결전 지원서비스 관련 세부부분 테이블(권기현박사)
 */
@Entity
@Data
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="nd_lt_detail_input")
public class LifeDetailTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="lt_id")
    private Long id; // 고유ID값(NOTNULL)

    @Column(name="lt_fy_average")
    private Double ltFyAverage; // 철근 항복강도 평균값(NOTNULL)

    @Column(name="lt_fy_standard")
    private Double ltFyStandard; // 철근 항복강도 표준편차(NOTNULL)

    @Column(name="lt_fy_variance")
    private Double ltFyVariance; // 철근 항복강도 변동계수(NOTNULL)


    @Column(name="lt_fc_average")
    private Double ltFcAverage; // 콘크리트 압축강도 평균값(NOTNULL)

    @Column(name="lt_fc_standard")
    private Double ltFcStandard; // 콘크리트 압축강도 표준편차(NOTNULL)

    @Column(name="lt_fc_variance")
    private Double ltFcVariance; // 콘크리트 압축강도 변동계수(NOTNULL)


    @Column(name="lt_section_average")
    private Double ltSectionAverage; // 철근 단면적 평균값(NOTNULL)

    @Column(name="lt_section_standard")
    private Double ltSectionStandard; // 철근 단면적 표준편차(NOTNULL)

    @Column(name="lt_section_variance")
    private Double ltSectionVariance; // 철근 단면적 변동계수(NOTNULL)


    @Column(name="lt_vehicle_average")
    private Double ltVehicleAverage; // 차량하중 평균값(NOTNULL)

    @Column(name="lt_vehicle_standard")
    private Double ltVehicleStandard; // 차량하중 표준편차(NOTNULL)

    @Column(name="lt_vehicle_variance")
    private Double ltVehicleVariance; // 차량하중 변동계수(NOTNULL)


    @Column(name="lt_target_value")
    private Double ltTargetValue; // 생애주기 목표값(NOTNULL)

    @Column(name="lt_recovery_percent")
    private Double ltRecoveryPercent; // 보수보강 회복율(NOTNULL)


    @Column(name="lt_recovery_one")
    private Double ltRecoveryOne; // 보수보강 회복율_1(NOTNULL)

    @Column(name="lt_recovery_two")
    private Double ltRecoveryTwo; // 보수보강 회복율_2(NOTNULL)

    @Column(name="lt_recovery_three")
    private Double ltRecoveryThree; // 보수보강 회복율_3(NOTNULL)

    @Column(name="lt_recovery_four")
    private Double ltRecoveryFour; // 보수보강 회복율_4(NOTNULL)

    @Column(name="lt_recovery_five")
    private Double ltRecoveryFive; // 보수보강 회복율_5(NOTNULL)

    @Column(name="lt_recovery_six")
    private Double ltRecoverySix; // 보수보강 회복율_6(NOTNULL)


    @Column(name="lt_cost_one")
    private Double ltCostOne; // 보수보강 비용_1(NOTNULL)

    @Column(name="lt_cost_two")
    private Double ltCostTwo; // 보수보강 비용_2(NOTNULL)

    @Column(name="lt_cost_three")
    private Double ltCostThree; // 보수보강 비용_3(NOTNULL)

    @Column(name="lt_cost_four")
    private Double ltCostFour; // 보수보강 비용_4(NOTNULL)

    @Column(name="lt_cost_five")
    private Double ltCostFive; // 보수보강 비용_5(NOTNULL)

    @Column(name="lt_cost_six")
    private Double ltCostSix; // 보수보강비용_6(NOTNULL)

    @Column(name="insert_date")
    private LocalDateTime insertDateTime;

    @Column(name="insert_id")
    private String insert_id;

    @Column(name="modify_date")
    private LocalDateTime modifyDateTime;

    @Column(name="modify_id")
    private String modify_id;

}
