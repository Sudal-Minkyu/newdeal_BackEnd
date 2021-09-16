package com.broadwave.backend.lifetime.absence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Minkyu
 * Date : 2021-09-09
 * Time :
 * Remark : 생애주기 의사결전 지원서비스 - 부재별 평균열화율 수치 조회용 AbsenceListDto
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AbsenceListDto {
    private Long id; // 고유ID값(NOTNULL)

    private String ltAbsence; // 부재
    private String ltAbsenceCode; // 부재코드(NULL)
    private String ltAbsenceName; // 부재명(NULL)

    private Double ltDeterioration; // 평균열화율(NOTNULL)
    private Double ltStandardDeviation; // 표준편차(NOTNULL)

}
