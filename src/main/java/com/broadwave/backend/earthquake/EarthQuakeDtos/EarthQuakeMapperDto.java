package com.broadwave.backend.earthquake.EarthQuakeDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private Long id; // 교량ID
    private String eqBridge; // 교량명
    private String eqLocation; // 대상지역
    private String eqRank; // 내진등급

    private String eqLength; // 주 경간장
    private String eqConfiguration; // 경간 구성
    private String eqPillar; // 교각기둥 구성
    private String eqDivision; // 주형 구분
    private String eqGirder; // 거더형식

    private String eqBridgeClassification; // 교량분류

    public String getEqBridgeClassification() {
        return "멍청한";
    }

    public String setEqBridgeClassification() {
        String eqBridgeClassificationStr;
        if(eqLength.equals("150m이상")){
            eqBridgeClassificationStr = "HWB1";
        }else{
            if(eqConfiguration.equals("단일경간")){
                eqBridgeClassificationStr = "HWB3";
            }else{
                if(eqPillar.equals("다중기둥") && eqGirder.equals("콘크리트")){
                    eqBridgeClassificationStr = "HWB5";
                }else if(eqPillar.equals("단일기둥") && eqDivision.equals("연속교") && eqGirder.equals("콘크리트")){
                    eqBridgeClassificationStr = "HWB8";
                }else if(eqDivision.equals("연속교") && eqGirder.equals("콘크리트")){
                    eqBridgeClassificationStr = "HWB10";
                }else if(eqPillar.equals("다중기둥") && eqGirder.equals("강재")){
                    eqBridgeClassificationStr = "HWB12";
                }else if(eqDivision.equals("연속교") && eqGirder.equals("강재")){
                    eqBridgeClassificationStr = "HWB15";
                }else if(eqPillar.equals("다중기둥") && eqGirder.equals("PSC")){
                    eqBridgeClassificationStr = "HWB17";
                }else if(eqPillar.equals("단일기둥") && eqGirder.equals("PSC")){
                    eqBridgeClassificationStr = "HWB20";
                }else{
                    eqBridgeClassificationStr = "HWB28";
                }
            }
        }

        return eqBridgeClassificationStr;
    }

    //    private MultipartFile excelFile; // 엑셀업로드 파일

}
