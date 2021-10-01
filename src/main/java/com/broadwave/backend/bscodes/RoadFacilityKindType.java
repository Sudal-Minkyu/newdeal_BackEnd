package com.broadwave.backend.bscodes;

/**
 * @author Minkyu
 * Date : 2020-06-11
 * Remark : 도로부속시설 시설물제원 종류
 */
public enum RoadFacilityKindType {
    RFK01("RFK01", "가드펜스"),
    RFK02("RFK02", "가드케이블"),
    RFK03("RFK03", "낙성방호 울타리"),
    RFK04("RFK04", "콘크리트 보형"),
    RFK05("RFK05", "광폭형"),
    RFK06("RFK06", "기타");

    private final String code;
    private final String desc;

    RoadFacilityKindType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
