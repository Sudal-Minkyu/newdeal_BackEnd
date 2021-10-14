package com.broadwave.backend.ltd.data.rebar;

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
 * Date : 2019-05-08
 * Time : 17:05
 * Remark :
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RebarCorrosionDto implements DtoExcel {
    private SeriesCode seriesCode;
    private String environment;  //내륙환경/해안환경
    private String element1;
    private String element2;
    private Integer period;         // 주기(day)
    private String periodName;      // 주기(설명)
    private Double saltRate;      // 초기염분함유량
    private Double value;         // 철근부식량(g) -- value 1, 2, 3, 4, 의 합-> 평균계산시 나누기 4를 추가할것
    private Double value1;        // 철근부식량(g) - 1cm
    private Double value2;        // 철근부식량(g) - 2cm
    private Double value3;        // 철근부식량(g) - 3cm
    private Double value4;        // 철근부식량(g) - 4cm

    @Override
    public List<String> toArray() {
        return Arrays.asList(this.environment, this.element1,this.element2, this.periodName, Double.toString(this.saltRate)
                ,Double.toString(this.value1),Double.toString(this.value2),Double.toString(this.value3),Double.toString(this.value4)

        );
    }
}
