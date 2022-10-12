package com.broadwave.backend.evaluaton;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/evaluation")
public class EvaluationRestController {
    private final EvaluationService evaluationService;

    public EvaluationRestController(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    @GetMapping("bridgeDataList")
    public ResponseEntity<Map<String,Object>> bridgeDataList(@RequestParam("s_evName")String evName, HttpServletRequest request) {
        return evaluationService.bridgeDataList(evName, request);
    }

}
