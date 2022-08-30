package com.broadwave.backend.lifetime.all;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Minkyu
 * Date : 2021-08-04
 * Time :
 * Remark : 생애주기 의사결전 지원서비스 관련 전체부분 테이블(이진혁박사)
 */
@Entity
@Data
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="nd_lt_all_input")
public class LifeAllTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="lt_id")
    private Long id; // 고유ID값(NOTNULL)

    @Column(name="lt_bridge_code")
    private String ltBridgeCode; // 교량코드(NULL)

    @Column(name="lt_bridge_name")
    private String ltBridgeName; // 교량명(NULL)

    @Column(name="lt_absence")
    private String ltAbsence; // 교량형식(NOTNULL)

    @Column(name="lt_span_num")
    private String ltSpanNum; // 경간수(NOTNULL)

    @Column(name="lt_absence_code")
    private String ltAbsenceCode; // 부재코드(NOTNULL)

    @Column(name="lt_all_tea_road")
    private Double ltAllTeaRoad; // 차로수(NOTNULL)

    @Column(name="lt_all_kind")
    private String ltAllKind; // 종별구분(NOTNULL)

    @Column(name="lt_all_length")
    private Double ltAllLength; // 연장(NOTNULL)

    @Column(name="lt_all_area")
    private Double ltAllArea; // 폭(NOTNULL)

    @Column(name="lt_all_completion_date")
    private String ltAllCompletionDate; // 준공일자(NOTNULL)

    @Column(name="lt_all_input_date")
    private String ltAllInputDate; // 평가기준 일자(NOTNULL)

    @Column(name="lt_all_stage")
    private Integer ltAllStage; // 평가단계 횟수, 기본값 : 25

    @Column(name="lt_all_rank")
    private String ltAllRank; // 현재 상태등급(NOTNULL)

    @Column(name="lt_damage_b_rank")
    private Double ltDamageBRank; // B등급 손상지수(NOTNULL)

    @Column(name="lt_damage_c_rank")
    private Double ltDamageCRank; // C등급 손상지수(NOTNULL)

    @Column(name="lt_damage_d_rank")
    private Double ltDamageDRank; // D등급 손상지수(NOTNULL)

    @Column(name="lt_damage_e_rank")
    private Double ltDamageERank; // E등급 손상지수(NOTNULL)


    @Column(name="lt_discount_rate")
    private Double ltDiscountRate; // 할인율(NOTNULL)

    @Column(name="lt_increase")
    private Double ltIncrease; // 열화증가율(NOTNULL)


    @Column(name="lt_periodic_year")
    private Double ltPeriodicYear; // 정기점검 년수(NOTNULL)

    @Column(name="lt_periodic_num")
    private Double ltPeriodicNum; // 정기점검 횟수(NOTNULL)

    @Column(name="lt_periodic_cost")
    private Double ltPeriodicCost; // 정기점검 바용(NOTNULL)


    @Column(name="lt_close_year")
    private Double ltCloseYear; // 정밀점검 년수(NOTNULL)

    @Column(name="lt_close_num")
    private Double ltCloseNum; // 정밀점검 횟수(NOTNULL)

    @Column(name="lt_close_cost")
    private Double ltCloseCost; // 정밀점검 비용(NOTNULL)


    @Column(name="lt_safety_year")
    private Double ltSafetyYear; // 정밀안전점검 년수(NOTNULL)

    @Column(name="lt_safety_num")
    private Double ltSafetyNum; // 정밀안전점검 횟수(NOTNULL)

    @Column(name="lt_safety_cost")
    private Double ltSafetyCost; // 정밀안전점검 비용(NOTNULL)


    @Column(name="insert_date")
    private LocalDateTime insertDateTime;

    @Column(name="insert_id")
    private String insert_id;

    @Column(name="modify_date")
    private LocalDateTime modifyDateTime;

    @Column(name="modify_id")
    private String modify_id;

}
