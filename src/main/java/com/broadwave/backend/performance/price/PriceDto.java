package com.broadwave.backend.performance.price;

import lombok.*;

/**
 * @author Minkyu
 * Date : 2021-07-22
 * Time :
 * Remark : 뉴딜 관리자 전용 성능개선사업평가 년도별 환율 PriceDto
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceDto {
    private Long id;
    private Double piYear; // 년도
    private Double piPrice; // 환율
}
