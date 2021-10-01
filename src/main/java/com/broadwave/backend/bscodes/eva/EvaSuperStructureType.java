package com.broadwave.backend.bscodes.eva;

/**
 * @author InSeok
 * Date : 2019-09-06
 * Remark : 알고리즘평가입력 -> 상부구조타입
 */
public enum EvaSuperStructureType {
    ST001("ST001", "PSC I 형교"),
    ST002("ST002", "PSC 상자형교"),
    ST003("ST003", "PSC 슬래브교"),
    ST004("ST004", "PSC 중공슬래브교"),
    ST005("ST005", "RC T형교"),
    ST006("ST006", "RC 상자형교"),
    ST007("ST007", "RC 슬래브교"),
    ST008("ST008", "RC 중공슬래브교"),
    ST009("ST009", "강 I형교"),
    ST010("ST010", "강 상자형교"),
    ST011("ST011", "강 판형교"),
    ST012("ST012", "라멘교")
    ;

    private String code;
    private String desc;


    EvaSuperStructureType(String code, String desc) {
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
