package com.broadwave.backend.safety.calculation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Minkyu
 * Date : 2022-04-06
 * Time :
 * Remark : 뉴딜 계측 기반 안전성 추정 데이터 - 온도, 내하율 기록 저장 Dto
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalculationDto {

    private Long id;
    private String calYyyymmdd; // 계측일시
    private Double calTemperature; // 온도
    private Double calCapacity; // 공용 내하율

}
