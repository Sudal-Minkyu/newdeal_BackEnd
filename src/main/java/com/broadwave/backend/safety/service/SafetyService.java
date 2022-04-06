package com.broadwave.backend.safety.service;

import com.broadwave.backend.common.AjaxResponse;
import com.broadwave.backend.safety.Safety;
import com.broadwave.backend.safety.SafetyRepository;
import com.broadwave.backend.safety.safetyDtos.SafetyListDto;
import com.broadwave.backend.safety.safetyDtos.SafetyMapperDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Minkyu
 * Date : 2022-04-05
 * Time :
 * Remark : SafetyService
*/
@Slf4j
@Service
public class SafetyService {

    private final ModelMapper modelMapper;
    private final SafetyRepository safetyRepository;

    @Autowired
    public SafetyService(ModelMapper modelMapper, SafetyRepository safetyRepository){
        this.modelMapper = modelMapper;
        this.safetyRepository = safetyRepository;
    }

    // NEWDEAL 계측 기반 안전성 추정 데이터 제공 저장
    public ResponseEntity<Map<String, Object>> safetySave(SafetyMapperDto safetyMapperDto, HttpServletRequest request) {
        log.info("safetySave 호출성공");

        AjaxResponse res = new AjaxResponse();

        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
        String insert_id = request.getHeader("insert_id");

        log.info("JWT_AccessToken : "+JWT_AccessToken);
        log.info("insert_id : "+insert_id);

        Safety safety;
        Optional<Safety> optionalSafety = safetyRepository.findById(safetyMapperDto.getId());
        if(optionalSafety.isPresent()){
            log.info("수정입니다.");
            safety = modelMapper.map(optionalSafety.get(), Safety.class);
            safety.setSfName(safetyMapperDto.getSfName());
            safety.setSfForm(safetyMapperDto.getSfForm());
            safety.setSfRank(safetyMapperDto.getSfRank());
            safety.setSfLength(safetyMapperDto.getSfLength());
            safety.setSfWidth(safetyMapperDto.getSfWidth());
            safety.setSfNum(safetyMapperDto.getSfNum());
            safety.setSfCompletionYear(safetyMapperDto.getSfCompletionYear());
            safety.setSfFactor(safetyMapperDto.getSfFactor());
            safety.setModify_id(insert_id);
            safety.setModifyDateTime(LocalDateTime.now());
        }else{
            log.info("신규입니다.");
            safety = modelMapper.map(safetyMapperDto, Safety.class);
            safety.setInsert_id(insert_id);
            safety.setInsertDateTime(LocalDateTime.now());
        }
//        log.info("safety : "+safety);
        safetyRepository.save(safety);

        return ResponseEntity.ok(res.success());
    }

    // NEWDEAL 계측 기반 안전성 추정 데이터 - 교량 리스트 검색
    public ResponseEntity<Map<String, Object>> bridgeDataList(String sfForm, String sfRank, String sfName, HttpServletRequest request) {
        log.info("bridgeDataList 호출성공");

        log.info("sfForm : "+sfForm);
        log.info("sfRank : "+sfRank);
        log.info("sfName : "+sfName);

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
        String insert_id = request.getHeader("insert_id");

        log.info("JWT_AccessToken : "+JWT_AccessToken);
        log.info("insert_id : "+insert_id);

        List<SafetyListDto> safetyListDtoList = safetyRepository.findBySafetyList(sfForm, sfRank, sfName);
        data.put("gridListData",safetyListDtoList);

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

}
