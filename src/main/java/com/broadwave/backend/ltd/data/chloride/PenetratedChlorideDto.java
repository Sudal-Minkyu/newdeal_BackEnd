package com.broadwave.backend.ltd.data.chloride;

import com.broadwave.backend.bscodes.SeriesCode;
import com.broadwave.backend.excel.DtoExcel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

/**
 * @author InSeok
 * Date : 2019-05-10
 * Time : 11:25
 * Remark :
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PenetratedChlorideDto implements DtoExcel {
    private SeriesCode seriesCode;
    private String environment;  //내륙환경/해안환경
    private String element1;
    private String element2;
    private Integer period;         // 주기(day)
    private String periodName;      // 주기(설명)
    private Double saltRate;      // 초기염분함유량
    private Double value;        //  염분함율량(%) -- value 1, 2, 3, 4,5,6 의 합-> 평균계산시 나누기 6를 추가할것
    private Double value1;        // 염분함율량(%) - 0-0.15cm
    private Double value2;        // 염분함율량(%) - 0.15-1.5cm
    private Double value3;        // 염분함율량(%) - 1.5-3cm
    private Double value4;        // 염분함율량(%) - 3-4.5cm
    private Double value5;        // 염분함율량(%) - 4.5-6cm
    private Double value6;        // 염분함율량(%) - 10cm

    @Override
    public List<String> toArray() {
        return Arrays.asList(this.environment, this.element1,this.element2, this.periodName, Double.toString(this.saltRate)
                ,Double.toString(this.value1),Double.toString(this.value2),Double.toString(this.value3),Double.toString(this.value4),Double.toString(this.value5),Double.toString(this.value6)

        );
    }
}
