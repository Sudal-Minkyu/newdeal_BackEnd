package com.broadwave.backend.bscodes.psc;

/**
 * @author Minkyu
 * Date : 2019-10-15
 * Remark :
 */
public enum RescueDownSub {

    DOWNSUB1001("DOWNSUB1001", "균열", RescueDown.DOWN01),
    DOWNSUB1002("DOWNSUB1002", "박리/박락", RescueDown.DOWN01),
    DOWNSUB1003("DOWNSUB1003", "누수 및 백태", RescueDown.DOWN01),
    DOWNSUB1004("DOWNSUB1004", "콘크리트 염화물침투량", RescueDown.DOWN01),
    DOWNSUB1005("DOWNSUB1005", "콘크리트 탄산화깊이", RescueDown.DOWN01),
    DOWNSUB1006("DOWNSUB1006", "피복(표면부) 콘크리트 품질", RescueDown.DOWN01),
    DOWNSUB1007("DOWNSUB1007", "철근부식", RescueDown.DOWN01),
    DOWNSUB1008("DOWNSUB1008", "기울음", RescueDown.DOWN01),
    DOWNSUB1009("DOWNSUB1009", "침하", RescueDown.DOWN01),
    DOWNSUB1010("DOWNSUB1010", "세굴", RescueDown.DOWN01),
    DOWNSUB1011("DOWNSUB1011", "수중부기초 손상", RescueDown.DOWN01),

    DOWNSUB2001("DOWNSUB2001", "균열", RescueDown.DOWN02),
    DOWNSUB2002("DOWNSUB2002", "박리/박락", RescueDown.DOWN02),
    DOWNSUB2003("DOWNSUB2003", "누수 및 백태", RescueDown.DOWN02),
    DOWNSUB2004("DOWNSUB2004", "콘크리트 염화물침투량", RescueDown.DOWN02),
    DOWNSUB2005("DOWNSUB2005", "콘크리트 탄산화깊이", RescueDown.DOWN02),
    DOWNSUB2006("DOWNSUB2006", "피복(표면부) 콘크리트 품질", RescueDown.DOWN02),
    DOWNSUB2007("DOWNSUB2007", "철근부식", RescueDown.DOWN02),
    DOWNSUB2008("DOWNSUB2008", "기울음", RescueDown.DOWN02),
    DOWNSUB2009("DOWNSUB2009", "침하", RescueDown.DOWN02),
    DOWNSUB2010("DOWNSUB2010", "세굴", RescueDown.DOWN02),

    DOWNSUB3001("DOWNSUB3001", "-", RescueDown.DOWN03);

    private String code;
    private String desc;
    private RescueDown rescueDown;

    RescueDownSub(String code, String desc, RescueDown rescueDown) {
        this.code = code;
        this.desc = desc;
        this.rescueDown = rescueDown;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public String getRescueDown() {
        return rescueDown.getCode();
    }
}
