package com.broadwave.backend.bscodes;

/**
 * @author Minkyu
 * Date : 2019-10-14
 * Remark :
 */
public enum BridgeType {
    BT001("BT001", "PSC-Beam"),
    BT002("BT002", "PSC-Box"),
    BT003("BT003", "슬래프교"),
    BT004("BT004", "테스트");

    private final String code;
    private final String desc;

    BridgeType(String code, String desc) {
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
