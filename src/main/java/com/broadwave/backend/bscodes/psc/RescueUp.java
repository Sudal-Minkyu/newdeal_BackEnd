package com.broadwave.backend.bscodes.psc;

/**
 * @author Minkyu
 * Date : 2019-10-15
 * Remark :
 */
public enum RescueUp {

    UP01("UP01", "거더"),
    UP02("UP02", "가로보/세로보"),
    UP03("UP03", "긴장재"),
    UP04("UP04", "신축이음"),
    UP05("UP05", "바닥판");

    private String code;
    private String desc;

    RescueUp(String code, String desc) {
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