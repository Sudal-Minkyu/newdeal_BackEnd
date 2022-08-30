package com.broadwave.backend.costprediction.costpredicionDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Minkyu
 * Date : 2022-08-03
 * Time :
 * Remark : 뉴딜 유지관리 기술 선정 및 비용예측 ListDto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CostPredictionListDto {

    private Long id;
    private String cpNum; // 교량번호
    private String cpName; // 교량명
    private String cpType; // 도로종류
    private String cpManager; // 관리기관
    private String cpTopForm; // 상부형식
    private String cpKind; // 종별구분

}
