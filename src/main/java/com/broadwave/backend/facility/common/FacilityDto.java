package com.broadwave.backend.facility.common;

import com.broadwave.backend.bscodes.DonghaeType;
import com.broadwave.backend.bscodes.FlyingSaltType;
import com.broadwave.backend.bscodes.HeavyVehicleType;
import com.broadwave.backend.bscodes.SnowRemoverType;
import com.broadwave.backend.common.CommonUtils;
import com.broadwave.backend.excel.DtoExcel;
import lombok.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author Minkyu
 * Date : 2019-10-15
 * Remark :
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
public class FacilityDto implements DtoExcel {
    private Long id;
    private String faPscName;
    private String faPscCity;
    private Double faPscContinuation;
    private String faPscAddress;
    private Double faPscStartpoint;
    private Double faPscEndpoint;
    private Integer faPscCompletion;

    private Double faPscLongitude;  // 경도
    private Double faPscLatitude; // 위도

    private DonghaeType faPscEastsea;  // 동해환경
    private SnowRemoverType faPscSnow;  //제설제
    private FlyingSaltType faPscSalt;  // 비래염분
    private HeavyVehicleType faPscVehicle; // 중차량환경

    @Override
    public List<String> toArray() {
        return Arrays.asList("bridge",Long.toString(this.id), CommonUtils.GetNulltoString(this.faPscName), CommonUtils.GetNulltoString(this.faPscCity),CommonUtils.GetNulltoString(this.faPscAddress),
                CommonUtils.GetNulltoString(Double.toString(CommonUtils.GetNulltoDouble(this.faPscContinuation))),CommonUtils.GetNulltoString(Double.toString(CommonUtils.GetNulltoDouble(this.faPscLatitude))),CommonUtils.GetNulltoString(Double.toString(CommonUtils.GetNulltoDouble(this.faPscLongitude))),
                CommonUtils.GetNulltoString(Integer.toString(this.faPscCompletion)), CommonUtils.GetNulltoString(faPscEastsea.getDesc()),CommonUtils.GetNulltoString(faPscSnow.getDesc()),CommonUtils.GetNulltoString(faPscSalt.getDesc()),CommonUtils.GetNulltoString(faPscVehicle.getDesc())
        );
    }

    public Double getFaPscLongitude() {
        return faPscLongitude;
    }

    public Double getFaPscLatitude() {
        return faPscLatitude;
    }

    public Double getFaPscStartpoint() {
        return faPscStartpoint;
    }

    public Double getFaPscEndpoint() {
        return faPscEndpoint;
    }

    public Long getId() {
        return id;
    }

    public String getFaPscName() {
        return faPscName;
    }

    public String getFaPscCity() {
        return faPscCity;
    }

    public Double getFaPscContinuation() {
        return faPscContinuation;
    }

    public String getFaPscAddress() {
        return faPscAddress;
    }

    public Integer getFaPscCompletion() {
        return faPscCompletion;
    }

}
