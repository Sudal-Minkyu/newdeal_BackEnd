package com.broadwave.backend.earthquake.EarthQuakeDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;

/**
 * @author Minkyu
 * Date : 2022-06-10
 * Time :
 * Remark : 내진성능 추정서비스 MapperDto
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EarthQuakeMapperDto {

    private String eqBridge; // 교량명
    private String eqLocation; // 대상지역
    private String eqRank; // 내진등급
    private String eqLength; // 주 경간장
    private String eqConfiguration; // 경간 구성
    private String eqPillar; // 교각기둥 구성
    private String eqDivision; // 주형 구분
    private String eqGirder; // 거더형식

    private MultipartFile excelFile; // 엑셀업로드 파일

}
