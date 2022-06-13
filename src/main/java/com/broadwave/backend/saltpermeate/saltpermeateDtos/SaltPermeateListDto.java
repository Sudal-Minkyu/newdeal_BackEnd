package com.broadwave.backend.saltpermeate.saltpermeateDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Minkyu
 * Date : 2022-06-13
 * Time :
 * Remark : 열화환경별 염화물 ListDto
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class SaltPermeateListDto {

    private Long stId; // 고유ID값(NOTNULL)
    private String stBridge; // 교량명
    private String stLocation1; // 소재지 1
    private String stLocation2; // 소재지 2

    private String stCoordinateX; // 좌표 X
    private String stCoordinateY; // 좌표 Y
    private String stFreeze; // 동결융해
    private String stSnow; // 제설제
    private String stSalt; // 비래염분

}
