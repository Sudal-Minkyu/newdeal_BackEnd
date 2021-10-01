package com.broadwave.backend.bscodes;

/**
 * @author InSeok
 * Date : 2019-08-29
 * Remark : 일반정보 부재코드 타입
 */
public enum EdType {
    ED0100("ED0100", "바닥판","Y"),
    ED0101("ED0101", "바닥판","N"),
    ED0200("ED0200", "거더","Y"),
    ED0201("ED0201", "거더","N"),
    ED0300("ED0300", "교대","Y"),
    ED0301("ED0301", "교대","N"),
    ED0400("ED0400", "교각","Y"),
    ED0401("ED0401", "교각","N"),
    ED0500("ED0500", "긴장재","Y"),
    ED0501("ED0501", "긴장재","N")
    ;

    private String code;
    private String desc;
    private String groupStatus;


    EdType(String code, String desc,String groupStatus) {
        this.code = code;
        this.desc = desc;
        this.groupStatus = groupStatus;

    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public String getGroupStatus() {
        return groupStatus;
    }
}
