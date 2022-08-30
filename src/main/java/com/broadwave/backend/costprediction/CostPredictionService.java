package com.broadwave.backend.costprediction;

import com.broadwave.backend.common.AjaxResponse;
import com.broadwave.backend.costprediction.costpredicionDtos.CostPredictionListDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CostPredictionService {

    private final CostPredictionRepository costPredictionRepository;

    @Autowired
    public CostPredictionService(CostPredictionRepository costPredictionRepository) {
        this.costPredictionRepository = costPredictionRepository;
    }

    // NEWDEAL 유지관리 기술 선정 및 비용예측 리스트
    public ResponseEntity<Map<String, Object>> list(String cpName) {
        log.info("list 호출");

        log.info("cpName : "+cpName);

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        List<CostPredictionListDto> costPredictionListDtos = costPredictionRepository.findByCostPredicionList(cpName);

        data.put("gridListData", costPredictionListDtos);
        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

}
