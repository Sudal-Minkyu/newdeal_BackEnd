package com.broadwave.backend.evaluaton.dtos;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class EvaluationBridgeDataDto {
    private Long id; // 고유ID값
    private String evName; // 시설명
    private String evRoad1; // 도로종류
    private String evRoad2; // 노선명
    private String evRemark; //비고
    private String evAddr1; //시도
    private String evAddr2; //시군구
    private String evAddr3; //읍면동
    private String evAddr4; //리
    private Double evLength; // 총 길이
    private Double evWidth; // 총 폭
    private Double evWidthEff; // 유효 폭
    private Double evHeight; // 높이
    private Integer evSpanCnt; // 경간수
    private Double evMaxSpanLength; // 최대 경간장길이
    private String evSuperstructure; // 상부구조
    private String evSubstructure; // 하부구조
    private String evDesignLoading; // 설계하중
    private Integer evTraffic; // 교통량
    private String evOrg1; // 기관구분1
    private String evOrg2; // 기관구분2
    private String evOrg3; // 기관구분3
    private String evYearComplete; // 준공년도
}
