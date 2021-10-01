package com.broadwave.backend.env.tunnel;

import com.broadwave.backend.common.CommonUtils;
import com.broadwave.backend.excel.DtoExcel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

/**
 * @author InSeok
 * Date : 2019-06-21
 * Time : 14:47
 * Remark :
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TunnelDto implements DtoExcel {

    private Long id;
    private String roadType;       // 도로종류
    private String routeName;     // 노선명
    private String facilityName;  // 시설명
    private String address1;  // 시도
    private String address2;  // 시군구
    private String address3;  // 읍면동
    private String address4;  // 리
    private Double length;  // 연장
    private Double width;  // 총폭
    private Double effectiveWidth;  // 유효폭
    private Double height;  // 높이
    private Double gongsu;  // 공수
    private Double traffic;  // 교통량
    private String completionYear;  // 준공년도
    private Double longitude;  // 경도
    private Double latitude;  // 위도
    private String totalValue;  // 종합등급value
    private String totalRank;  // 종합등급
    private String dongRank;  // 동해환경
    private String snowRank;  // 제설제
    private String saltRank;  // 염해환경
    private String trafficRank;  // 교통량
    @Override
    public List<String> toArray() {
        return Arrays.asList("tunnel",Long.toString(this.id), CommonUtils.GetNulltoString(this.roadType), CommonUtils.GetNulltoString(this.routeName)
                , CommonUtils.GetNulltoString(this.facilityName), CommonUtils.GetNulltoString(this.address1)
                , CommonUtils.GetNulltoString(this.address2), CommonUtils.GetNulltoString(this.address3), CommonUtils.GetNulltoString(this.address4)
                , CommonUtils.GetNulltoString(Double.toString(CommonUtils.GetNulltoDouble(this.length))), CommonUtils.GetNulltoString(Double.toString(CommonUtils.GetNulltoDouble(this.width))), CommonUtils.GetNulltoString(Double.toString(CommonUtils.GetNulltoDouble(this.effectiveWidth))), CommonUtils.GetNulltoString(Double.toString(CommonUtils.GetNulltoDouble(this.height)))
                , CommonUtils.GetNulltoString(Double.toString(CommonUtils.GetNulltoDouble(this.gongsu)))
                , CommonUtils.GetNulltoString(Double.toString(CommonUtils.GetNulltoDouble(this.traffic)))
                , CommonUtils.GetNulltoString(this.completionYear), CommonUtils.GetNulltoString(Double.toString(CommonUtils.GetNulltoDouble(this.longitude))), CommonUtils.GetNulltoString(Double.toString(CommonUtils.GetNulltoDouble(this.latitude)))
                , CommonUtils.GetNulltoString(this.totalValue), CommonUtils.GetNulltoString(this.totalRank), CommonUtils.GetNulltoString(this.dongRank)
                , CommonUtils.GetNulltoString(this.snowRank), CommonUtils.GetNulltoString(this.saltRank), CommonUtils.GetNulltoString(this.trafficRank)


        );

    }
}
