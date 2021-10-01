package com.broadwave.backend.bscodes;

/**
 * @author Minkyu
 * Date : 2020-09-23
 * Remark : 센서종류
 */
public enum SensorType {
    SST001("SST001", "가속도센서"),
    SST002("SST002", "변위센서"),
    SST003("SST003", "기울기센서"),
    SST004("SST004", "온습도센서");

    private final String code;
    private final String desc;

    SensorType(String code, String desc) {
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