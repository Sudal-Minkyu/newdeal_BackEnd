package com.broadwave.backend.costprediction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Minkyu
 * Date : 2022-08-03
 * Remark : NEWDEAL 유지관리 기술 선정 및 비용예측 RestController
 */
@Slf4j
@RestController
@RequestMapping("/api/costprediction")
public class CostPredictionRestController {

    private final CostPredictionService costPredictionService;

    @Autowired
    public CostPredictionRestController(CostPredictionService costPredictionService) {
        this.costPredictionService = costPredictionService;
    }

    // NEWDEAL 유지관리 기술 선정 및 비용예측 리스트
    @GetMapping("list")
    public ResponseEntity<Map<String,Object>> list(@RequestParam("cpName")String cpName) {
        return costPredictionService.list(cpName);
    }


}


