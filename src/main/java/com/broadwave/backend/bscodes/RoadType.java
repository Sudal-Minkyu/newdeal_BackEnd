package com.broadwave.backend.bscodes;

/**
 * @author Minkyu
 * Date : 2020-05-21
 * Remark : 도로부속시설 시설물종
 */
public enum RoadType {
    ROAD01("ROAD01", "PC-콘크리트 중앙분리대"),
    ROAD02("ROAD02", "L형 측구");

    private final String code;
    private final String desc;

    RoadType(String code, String desc) {
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
