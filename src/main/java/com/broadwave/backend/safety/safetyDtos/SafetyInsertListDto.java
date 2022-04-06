package com.broadwave.backend.safety.safetyDtos;

import lombok.*;

/**
 * @author Minkyu
 * Date : 2022-04-05
 * Time :
 * Remark : 뉴딜 계측 기반 안전성 추정 데이터 아웃풋화면 저장된 교량리스트
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class SafetyInsertListDto {

    private Long id; // 고유ID값
    private String sfName; // 교량명

}
