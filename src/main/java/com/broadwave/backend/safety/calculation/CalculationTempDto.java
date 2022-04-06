package com.broadwave.backend.safety.calculation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Minkyu
 * Date : 2022-04-06
 * Time :
 * Remark : 뉴딜 계측 기반 안전성 추정 데이터 - 온도 그래프용
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalculationTempDto {

    private String calYyyymmdd; // 계측일시
    private Double calTemperature; // 온도

    public StringBuffer getCalYyyymmdd() {
        if(calYyyymmdd != null && !calYyyymmdd.equals("")){
            StringBuffer getCalYyyymmdd = new StringBuffer(calYyyymmdd);
            getCalYyyymmdd.insert(4,'-');
            getCalYyyymmdd.insert(7,'-');
            return getCalYyyymmdd;
        }else{
            return null;
        }
    }

}
