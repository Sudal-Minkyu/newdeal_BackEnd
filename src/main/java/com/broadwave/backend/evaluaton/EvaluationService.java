package com.broadwave.backend.evaluaton;

import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface EvaluationService {
    ResponseEntity<Map<String, Object>> bridgeDataList(String evName, HttpServletRequest request);
}
