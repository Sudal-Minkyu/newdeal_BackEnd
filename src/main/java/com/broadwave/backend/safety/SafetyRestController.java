package com.broadwave.backend.safety;

import com.broadwave.backend.safety.calculation.CalculationSet;
import com.broadwave.backend.safety.service.CalculationService;
import com.broadwave.backend.safety.service.SafetyService;
import com.broadwave.backend.safety.safetyDtos.SafetyMapperDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * @author Minkyu
 * Date : 2022-04-05
 * Remark : NEWDEAL 계측기반 안전성 추정 데이터 제공 RestController
 */
@Slf4j
@RestController
@RequestMapping("/api/safety")
public class SafetyRestController {

    private final SafetyService safetyService;
    private final CalculationService calculationService;

    @Autowired
    public SafetyRestController(SafetyService safetyService, CalculationService calculationService) {
        this.safetyService = safetyService;
        this.calculationService = calculationService;
    }

    // NEWDEAL 계측 기반 안전성 추정 데이터 제공 저장
    @PostMapping("save")
    public ResponseEntity<Map<String,Object>> save(@ModelAttribute SafetyMapperDto lifeDetailTimeMapperDto, HttpServletRequest request) throws IOException {
        return safetyService.safetySave(lifeDetailTimeMapperDto, request);
    }

    // NEWDEAL 계측 기반 안전성 추정 데이터 - 교량 리스트 검색
    @GetMapping("bridgeDataList")
    public ResponseEntity<Map<String,Object>> bridgeDataList(@RequestParam("s_sfForm")String sfForm, @RequestParam("s_sfRank")String sfRank ,@RequestParam("s_sfName")String sfName, HttpServletRequest request) {
        return safetyService.bridgeDataList(sfForm, sfRank, sfName, request);
    }

    // NEWDEAL 계측 기반 교량 & 안전성 추정 데이터 삭제
    @PostMapping("bridgeDataDelete")
    public ResponseEntity<Map<String,Object>> bridgeDataDelete(@RequestParam("id")Long id, HttpServletRequest request) {
        return calculationService.bridgeDataDelete(id, request);
    }

    // NEWDEAL 계측 기반 안전성 추정 데이터 가져오기
    @GetMapping("calculationDate")
    public ResponseEntity<Map<String,Object>> calculationDate(@RequestParam("id")Long id, HttpServletRequest request) {
        return calculationService.calculationDate(id, request);
    }

    // NEWDEAL 계측 기반 안전성 추정 데이터 저장
    @PostMapping("calculationSave")
    public ResponseEntity<Map<String,Object>> calculationSave(@RequestBody CalculationSet calculationSet, HttpServletRequest request) {
        return calculationService.calculationSave(calculationSet, request);
    }

    // NEWDEAL 저장되어있는 교량 조회
    @GetMapping("bridgeList")
    public ResponseEntity<Map<String,Object>> bridgeList() {
        return calculationService.bridgeList();
    }

    // NEWDEAL 계측 기반 안전성 추정 아웃풋
    @GetMapping("safetyCalculationOutputInfo")
    public ResponseEntity<Map<String,Object>> safetyCalculationOutputInfo(@RequestParam("id")Long id, HttpServletRequest request) {
        return safetyService.safetyCalculationOutputInfo(id, request);
    }
    
}


