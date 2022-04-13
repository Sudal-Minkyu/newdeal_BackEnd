package com.broadwave.backend.safety.calculation;

import lombok.*;

/**
 * @author Minkyu
 * Date : 2022-04-05
 * Time :
 * Remark : 온도, 내하율 기록 테이블 ListDto
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class CalculationListDto {

    private Long id;
    private String calYyyymmdd; // 계측일시
    private Double calTemperature; // 온도
    private Double calCapacity; // 공용 내하율

}
