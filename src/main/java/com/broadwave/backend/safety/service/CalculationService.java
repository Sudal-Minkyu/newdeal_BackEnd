package com.broadwave.backend.safety.service;

import com.broadwave.backend.common.AjaxResponse;
import com.broadwave.backend.safety.SafetyRepository;
import com.broadwave.backend.safety.calculation.CalculationListDto;
import com.broadwave.backend.safety.calculation.CalculationRepository;
import com.broadwave.backend.safety.safetyDtos.SafetyInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Minkyu
 * Date : 2022-04-05
 * Time :
 * Remark : CalculationService
*/
@Slf4j
@Service
public class CalculationService {

    private final ModelMapper modelMapper;
    private final CalculationRepository calculationRepository;
    private final SafetyRepository safetyRepository;

    @Autowired
    public CalculationService(ModelMapper modelMapper, CalculationRepository calculationRepository, SafetyRepository safetyRepository){
        this.modelMapper = modelMapper;
        this.calculationRepository = calculationRepository;
        this.safetyRepository = safetyRepository;
    }

    public ResponseEntity<Map<String, Object>> calculationDate(Long id, HttpServletRequest request) {
        log.info("calculationDate 호출성공");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
        String insert_id = request.getHeader("insert_id");

        log.info("JWT_AccessToken : "+JWT_AccessToken);
        log.info("insert_id : "+insert_id);

        SafetyInfoDto safetyInfoDto = safetyRepository.findBySafetyInfo(id);
        data.put("safetyInfo",safetyInfoDto);

        List<CalculationListDto> calculationListDtoList = calculationRepository.findByCalculationList(id);
        data.put("gridListData",calculationListDtoList);

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

}
