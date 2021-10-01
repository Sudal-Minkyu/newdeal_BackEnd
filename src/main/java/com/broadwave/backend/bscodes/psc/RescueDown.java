package com.broadwave.backend.bscodes.psc;

/**
 * @author Minkyu
 * Date : 2019-10-15
 * Remark :
 */
public enum RescueDown {

    DOWN01("DOWN01", "교각"),
    DOWN02("DOWN02", "교대"),
    DOWN03("DOWN03", "교량받침");

    private String code;
    private String desc;

    RescueDown(String code, String desc) {
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
