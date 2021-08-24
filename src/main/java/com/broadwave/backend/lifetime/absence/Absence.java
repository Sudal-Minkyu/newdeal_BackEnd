package com.broadwave.backend.lifetime.absence;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Minkyu
 * Date : 2021-08-04
 * Time :
 * Remark : 생애주기 의사결전 지원서비스 - 부재별 평균열화율 수치 테이블
 */
@Entity
@Data
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="nd_lt_absence")
public class Absence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id; // 고유ID값(NOTNULL)


    @Column(name="lt_absence_code")
    private String ltAbsenceCode; // 부재코드(NULL)

    @Column(name="lt_absence_name")
    private String ltAbsenceName; // 부재명(NULL)


    @Column(name="lt_deterioration")
    private Double ltDeterioration; // 평균열화율(NOTNULL)

    @Column(name="lt_standard_deviation")
    private Double ltStandardDeviation; // 표준편차(NOTNULL)


    @Column(name="lt_remuneration_three")
    private Double ltRemunerationThree; // 보수보강 비용모델 3차항 계수(NOTNULL)

    @Column(name="lt_remuneration_two")
    private Double ltRemunerationTwo; // 보수보강 비용모델 2차항 계수(NOTNULL)

    @Column(name="lt_remuneration_one")
    private Double ltRemunerationOne; // 보수보강 비용모델 1차항 계수(NOTNULL)

    @Column(name="lt_remuneration_num")
    private Double ltRemunerationNum; // 보수보강 비용모델 상수(NOTNULL)


    @Column(name="lt_status_two")
    private Double ltStatusTwo; // 상태향상 모델모델 2차항 계수(NOTNULL)

    @Column(name="lt_status_one")
    private Double ltStatusOne; // 상태향상 모델모델 1차항 계수(NOTNULL)

    @Column(name="lt_status_num")
    private Double ltStatusNum; // 상태향상 모델모델 상수(NOTNULL)


    @Column(name="insert_date")
    private LocalDateTime insertDateTime;

}
