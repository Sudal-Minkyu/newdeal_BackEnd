package com.broadwave.backend.evaluaton;


import com.broadwave.backend.evaluaton.dtos.EvaluationBridgeDataDto;

import java.util.List;

public interface EvaluationBridgeDataRepositoryCustom {
    List<EvaluationBridgeDataDto> findByBridgeDataList(String evName);
}
