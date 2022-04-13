package com.broadwave.backend.performance.performanceDtos;

import lombok.*;

/**
 * @author Minkyu
 * Date : 2021-07-19
 * Time :
 * Remark : 뉴딜 성능개선사업평가 관련 PerformanceCheckDto
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
public class PerformanceCheckDto {
    private String piAutoNum; // 대안 일려번호(NOTNULL)
    private String piBusiness; // 사업구분(NOTNULL)
    private Integer piInputMiddleSave; //작성완료된 글인지 0 or 1(NULL)

    public String getPiAutoNum() {
        return piAutoNum;
    }

    public String getPiBusiness() {
        return piBusiness;
    }

    public Integer getPiInputMiddleSave() {
        return piInputMiddleSave;
    }
}
