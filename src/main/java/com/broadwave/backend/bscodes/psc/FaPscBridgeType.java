package com.broadwave.backend.bscodes.psc;

/**
 * @author Minkyu
 * Date : 2019-10-15
 * Remark :
 */
public enum FaPscBridgeType {

    FPBT01("FPBT01", "PSC I형교"),
    FPBT02("FPBT02", "PSC상자형교"),
    FPBT03("FPBT03", "RC T형교"),
    FPBT04("FPBT04", "RC슬래브교"),
    FPBT05("FPBT05", "강I형교"),
    FPBT06("FPBT06", "강상자형교"),
    FPBT07("FPBT07", "강판형교"),
    FPBT08("FPBT08", "라멘교"),
    FPBT09("FPBT09", "엑스트라도즈드교"),
    FPBT10("FPBT10", "트러스교"),
    FPBT11("FPBT11", "프리플렉스형교"),
    FPBT12("FPBT12", "기타");

    private final String code;
    private final String desc;

    FaPscBridgeType(String code, String desc) {
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
