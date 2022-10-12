package com.broadwave.backend.evaluaton;

import com.broadwave.backend.common.AjaxResponse;
import com.broadwave.backend.evaluaton.dtos.EvaluationBridgeDataDto;
import com.broadwave.backend.safety.safetyDtos.SafetyListDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EvaluationServiceImpl implements EvaluationService{
    private final EvaluationBridgeDataRepository evaluationBridgeDataRepository;

    public EvaluationServiceImpl(EvaluationBridgeDataRepository evaluationBridgeDataRepository) {
        this.evaluationBridgeDataRepository = evaluationBridgeDataRepository;
    }

    @Override
    public ResponseEntity<Map<String, Object>> bridgeDataList(String evName, HttpServletRequest request) {

        log.info("노후도평가 bridgeDataList 호출성공");
        log.info("evName : "+evName);

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();


        List<EvaluationBridgeDataDto> evaluationBridgeDataDto= evaluationBridgeDataRepository.findByBridgeDataList(evName);
        data.put("gridListData",evaluationBridgeDataDto);

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }
}
