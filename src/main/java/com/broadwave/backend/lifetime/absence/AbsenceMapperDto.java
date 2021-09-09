package com.broadwave.backend.lifetime.absence;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Minkyu
 * Date : 2021-09-09
 * Time :
 * Remark :
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@Slf4j
public class AbsenceMapperDto {

    private String ltAbsence; // 부재(NULL)
    private String ltAbsenceCode; // 부재코드(NULL)
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
