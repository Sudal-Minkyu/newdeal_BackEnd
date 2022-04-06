package com.broadwave.backend.safety;

import com.broadwave.backend.safety.safetyDtos.SafetyInfoDto;
import com.broadwave.backend.safety.safetyDtos.SafetyListDto;

import java.util.List;

/**
 * @author Minkyu
 * Date : 2022-04-05
 * Remark :
 */
public interface SafetyRepositoryCustom {

    List<SafetyListDto> findBySafetyList(String sfForm, String sfRank, String sfName); // 계측 기반 안전성 추정 데이터 - 교량 리스트 검색

    SafetyInfoDto findBySafetyInfo(Long id);
}
