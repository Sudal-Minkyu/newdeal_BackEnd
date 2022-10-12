package com.broadwave.backend.saltpermeate.saltpermeateDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Minkyu
 * Date : 2022-06-13
 * Time :
 * Remark : 열화환경별 염화물 ListDto
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SaltPermeateListDto {

    private Long stId; // 고유ID값(NOTNULL)
    private String stBridge; // 교량명
    private String stLocation1; // 소재지 1
    private String stLocation2; // 소재지 2

    private String stCoordinateX; // 좌표 X
    private String stCoordinateY; // 좌표 Y
    private String stFreeze; // 동결융해
    private String stSnow; // 제설제
//    private String stSalt; // 비래염분


    public String getStFreeze() {
        int stFreezeVal = Integer.parseInt(stFreeze);
        if(stFreezeVal <= 0){
            stFreeze = "a-";
        }else if(stFreezeVal <= 2){
            stFreeze = "a+";
        }else if(stFreezeVal <= 28){
            stFreeze = "b-";
        }else if(stFreezeVal <= 49){
            stFreeze = "b+";
        }else if(stFreezeVal <= 75){
            stFreeze = "c-";
        }else if(stFreezeVal <= 76){
            stFreeze = "c+";
        }

        return stFreeze;
    }

    public String getStSnow() {
        int stSnowVal = Integer.parseInt(stSnow);
        if(stSnowVal <= 2){
            stSnow = "a-";
        }else if(stSnowVal <= 6){
            stSnow = "a+";
        }else if(stSnowVal <= 10){
            stSnow = "b-";
        }else if(stSnowVal <= 13){
            stSnow = "b+";
        }else if(stSnowVal <= 17){
            stSnow = "c-";
        }else if(stSnowVal <= 18){
            stSnow = "c+";
        }

        return stSnow;
    }

}
