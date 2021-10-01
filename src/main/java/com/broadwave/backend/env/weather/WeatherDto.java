package com.broadwave.backend.env.weather;

import com.broadwave.backend.common.CommonUtils;
import com.broadwave.backend.excel.DtoExcel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

/**
 * @author Minkyu
 * Date : 2020-11-05
 * Time :
 * Remark :환경정보내 기상관측장비 리스트 Dto
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherDto implements DtoExcel {

    private Long id;
    private String ewName; // 지점명
    private Double ewLatitude; // 위도
    private Double ewLongitude; // 경도
    private String ewNumber; // 지점번호
    private Double ewAltitude; // 해발고도
    private String ewAddress; // 주소

    @Override
    public List<String> toArray() {
        return Arrays.asList("point",Long.toString(this.id), CommonUtils.GetNulltoString(this.ewName),
                CommonUtils.GetNulltoString(Double.toString(CommonUtils.GetNulltoDouble(this.ewLatitude))),CommonUtils.GetNulltoString(Double.toString(CommonUtils.GetNulltoDouble(this.ewLongitude))),
                CommonUtils.GetNulltoString(this.ewNumber),CommonUtils.GetNulltoString(Double.toString(CommonUtils.GetNulltoDouble(this.ewAltitude))),
                CommonUtils.GetNulltoString(this.ewAddress)
        );

    }
}
