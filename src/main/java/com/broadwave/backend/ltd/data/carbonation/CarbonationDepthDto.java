package com.broadwave.backend.ltd.data.carbonation;

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
 * Time : 11:14
 * Remark : 탄산화깊이 정보를 가져오기위한 Dto
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarbonationDepthDto implements DtoExcel {
    private SeriesCode seriesCode;
    private String environment;  //내륙환경/해안환경
    private String element1;
    private String element2;
    private Integer period;         // 주기(day)
    private String periodName;      // 주기(설명)
    private Double saltRate;      // 초기염분함유량
    private Double value;        // 압축강도 MPA

    @Override
    public List<String> toArray() {
        return Arrays.asList(this.environment, this.element1,this.element2, this.periodName, Double.toString(this.saltRate),Double.toString(this.value));
    }
}
