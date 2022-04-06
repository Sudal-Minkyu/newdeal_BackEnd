package com.broadwave.backend.safety.calculation;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Minkyu
 * Date : 2022-04-06
 * Time :
 * Remark : 뉴딜 계측 기반 안전성 추정 데이터 - 온도, 내하율 기록 저장 Set
 */
@Data
public class CalculationSet {

    private Long id;

    // 추가 행 리스트
    private ArrayList<CalculationDto> add;

    // 수정 행 리스트
    private ArrayList<CalculationDto> update;

    // 삭제 행 리스트
    private List<Long> deleteList;

}
