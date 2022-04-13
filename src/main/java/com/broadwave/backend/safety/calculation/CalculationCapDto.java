package com.broadwave.backend.safety.calculation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Minkyu
 * Date : 2022-04-06
 * Time :
 * Remark : 뉴딜 계측 기반 안전성 추정 데이터 - 공용내하율 그래프용
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalculationCapDto {

    private String calYyyymmdd; // 계측일시
    private Double calCapacity; // 공용 내하율

}
