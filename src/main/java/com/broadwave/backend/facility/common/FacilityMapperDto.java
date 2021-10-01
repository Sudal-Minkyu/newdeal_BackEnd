package com.broadwave.backend.facility.common;

import com.broadwave.backend.bscodes.DonghaeType;
import com.broadwave.backend.bscodes.FlyingSaltType;
import com.broadwave.backend.bscodes.HeavyVehicleType;
import com.broadwave.backend.bscodes.SnowRemoverType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Minkyu
 * Date : 2019-10-14
 * Remark :
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FacilityMapperDto {
    private String faPscName;
    private String faPscCity;
    private String faPscAddress;
    private Double faPscContinuation;
    private Double faPscLongitude;  // 경도
    private Double faPscLatitude; // 위도
    private Double faPscStartpoint;
    private Double faPscEndpoint;
    private Integer faPscCompletion;
    private FlyingSaltType FaPscSalt;
    private SnowRemoverType faPscSnow;
    private DonghaeType faPscEastsea;
    private HeavyVehicleType faPscVehicle;
}
