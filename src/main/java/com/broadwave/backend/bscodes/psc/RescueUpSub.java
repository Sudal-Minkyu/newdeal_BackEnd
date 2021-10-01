package com.broadwave.backend.bscodes.psc;

/**
 * @author Minkyu
 * Date : 2019-10-15
 * Remark :
 */
public enum RescueUpSub {

    UPSUB1001("UPSUB1001", "균열", RescueUp.UP01),
    UPSUB1002("UPSUB1002", "박리/박락", RescueUp.UP01),
    UPSUB1003("UPSUB1003", "누수 및 백태", RescueUp.UP01),
    UPSUB1004("UPSUB1004", "콘크리트 염화물침투량", RescueUp.UP01),
    UPSUB1005("UPSUB1005", "콘크리트 탄산화깊이", RescueUp.UP01),
    UPSUB1006("UPSUB1006", "피복(표면부) 콘크리트 품질", RescueUp.UP01),
    UPSUB1007("UPSUB1007", "철근부식", RescueUp.UP01),

    UPSUB2001("UPSUB2001", "균열", RescueUp.UP02),
    UPSUB2002("UPSUB2002", "박리/박락", RescueUp.UP02),
    UPSUB2003("UPSUB2003", "누수 및 백태", RescueUp.UP02),
    UPSUB2004("UPSUB2004", "콘크리트 염화물침투량", RescueUp.UP02),
    UPSUB2005("UPSUB2005", "콘크리트 탄산화깊이", RescueUp.UP02),
    UPSUB2006("UPSUB2006", "피복(표면부) 콘크리트 품질", RescueUp.UP02),
    UPSUB2007("UPSUB2007", "철근부식", RescueUp.UP02),

    UPSUB3001("UPSUB3001", "보수이력", RescueUp.UP03),
    UPSUB3002("UPSUB3002", "PC 텐던 부식", RescueUp.UP03),
    UPSUB3003("UPSUB3003", "정착부 균열", RescueUp.UP03),
    UPSUB3006("UPSUB3006", "정착부 손상비율", RescueUp.UP03),
    UPSUB3004("UPSUB3004", "보호관 파손", RescueUp.UP03),
    UPSUB3005("UPSUB3005", "에어벤트 파손", RescueUp.UP03),
    UPSUB3007("UPSUB3007", "그라우트 충진 결함", RescueUp.UP03),

    UPSUB4001("UPSUB4001", "보수이력", RescueUp.UP04),
    UPSUB4002("UPSUB4002", "유간", RescueUp.UP04),
    UPSUB4003("UPSUB4003", "누수 및 백태", RescueUp.UP04),
    UPSUB4004("UPSUB4004", "후타재 손상", RescueUp.UP04),
    UPSUB4005("UPSUB4005", "기타 손상", RescueUp.UP04),

    UPSUB5001("UPSUB5001", "균열", RescueUp.UP05),
    UPSUB5002("UPSUB5002", "박리/박락", RescueUp.UP05),
    UPSUB5003("UPSUB5003", "누수 및 백태", RescueUp.UP05),
    UPSUB5004("UPSUB5004", "콘크리트 염화물침투량", RescueUp.UP05),
    UPSUB5005("UPSUB5005", "콘크리트 탄산화깊이", RescueUp.UP05),
    UPSUB5006("UPSUB5006", "피복(표면부) 콘크리트 품질", RescueUp.UP05),
    UPSUB5007("UPSUB5007", "철근부식", RescueUp.UP05),
    UPSUB5008("UPSUB5008", "초음파 탐상", RescueUp.UP05);

    private String code;
    private String desc;
    private RescueUp rescueUp;

    RescueUpSub(String code, String desc, RescueUp rescueUp) {
        this.code = code;
        this.desc = desc;
        this.rescueUp = rescueUp;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public String getRescueUp() {
        return rescueUp.getCode();
    }
}
