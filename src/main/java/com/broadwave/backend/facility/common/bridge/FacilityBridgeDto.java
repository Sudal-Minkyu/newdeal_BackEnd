package com.broadwave.backend.facility.common.bridge;

import lombok.*;

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
public class FacilityBridgeDto {
    private Long id;
    private Long faPscBridgeNumber;
    private String faPscBridgeType;
    private Double faPscBridgeWidth;
    private Double faPscBridgeMaxspanlength;
    private String faPscBridgeBridgeStep;
    private Double faPscBridgeHeight;
    private Double faPscBridgeSeaheight;
    private Double faPscBridgeSlabThickness;
    private Double faPscBridgeSlabWidth;
    private String faPscBridgePierType;
    private Long faPscBridgePierNumber;
    private Long faPscBridgePierSupportNumber;
    private Long faPscBridgeExpansionjointNumber;

    public Long getId() {
        return id;
    }

    public Long getFaPscBridgeNumber() {
        return faPscBridgeNumber;
    }

    public String getFaPscBridgeType() {
        return faPscBridgeType;
    }

    public Double getFaPscBridgeWidth() {
        return faPscBridgeWidth;
    }

    public Double getFaPscBridgeMaxspanlength() {
        return faPscBridgeMaxspanlength;
    }

    public String getFaPscBridgeBridgeStep() {
        return faPscBridgeBridgeStep;
    }

    public Double getFaPscBridgeHeight() {
        return faPscBridgeHeight;
    }

    public Double getFaPscBridgeSeaheight() {
        return faPscBridgeSeaheight;
    }

    public Double getFaPscBridgeSlabThickness() {
        return faPscBridgeSlabThickness;
    }

    public Double getFaPscBridgeSlabWidth() {
        return faPscBridgeSlabWidth;
    }

    public String getFaPscBridgePierType() {
        return faPscBridgePierType;
    }

    public Long getFaPscBridgePierNumber() {
        return faPscBridgePierNumber;
    }

    public Long getFaPscBridgePierSupportNumber() {
        return faPscBridgePierSupportNumber;
    }

    public Long getFaPscBridgeExpansionjointNumber() {
        return faPscBridgeExpansionjointNumber;
    }
}
