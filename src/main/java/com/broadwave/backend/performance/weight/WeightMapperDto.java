package com.broadwave.backend.performance.weight;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Minkyu
 * Date : 2021-07-16
 * Time :
 * Remark : 뉴딜 성능개선사업평가 가중치 MapperDto
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class WeightMapperDto {

    private String piAutoNum; // 대안 일려번호(NOTNULL)

    private String weightCategory;
    private String piInputSkip;

    private Double piWeightTechnicality; // 유형_기술성(NOTNULL)
    private Double piWeightEconomy; // 유형_경제성(NOTNULL)
    private Double piWeightPolicy; // 유형_정책성(NOTNULL)

    private Double piWeightSafe; // 안정성 가중치(NOTNULL)
    private Double piWeightUsability; // 사용성 가중치(NOTNULL)
    private Double piWeightOld; // 노후도 가중치(NULL)
    private Double piWeightUrgency; // 시급성 가중치(NOTNULL)
    private Double piWeightGoal; // 목표달성도 가중치(NOTNULL)

    private Double piWeightSafeUtility; // 안전효용 개선 가중치(NOTNULL)
    private Double piWeightCostUtility; // 자산가치 개선 가중치(NULL)

    private Double piWeightBusiness; // 사업추진 타당성 가중치(NOTNULL)
    private Double piWeightComplaint; // 민원 및 사고 대응성 가중치(NOTNULL)
    private Double piWeightBusinessEffect; // 사업효과 범용성 가중치(NOTNULL)

    private Double piWeightCriticalScore; // 사업추진 임계점수(NOTNULL)

}
