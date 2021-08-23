package com.broadwave.backend.lifetime.absence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Minkyu
 * Date : 2021-08-04
 * Time :
 * Remark : 생애주기 의사결전 지원서비스 - 부재별 평균열화율 수치 AbsenceDto
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AbsenceDto {

    private String ltAbsenceName; // 부재명(NULL)

    private Double ltDeterioration; // 평균열화율(NOTNULL)
    private Double ltStandardDeviation; // 표준편차(NOTNULL)

    private Double ltRemunerationThree; // 보수보강 비용모델 3차항 계수(NOTNULL)
    private Double ltRemunerationTwo; // 보수보강 비용모델 2차항 계수(NOTNULL)
    private Double ltRemunerationOne; // 보수보강 비용모델 1차항 계수(NOTNULL)
    private Double ltRemunerationNum; // 보수보강 비용모델 상수(NOTNULL)

    private Double ltStatusTwo; // 상태향상 모델모델 2차항 계수(NOTNULL)
    private Double ltStatusOne; // 상태향상 모델모델 1차항 계수(NOTNULL)
    private Double ltStatusNum; // 상태향상 모델모델 상수(NOTNULL)

}
