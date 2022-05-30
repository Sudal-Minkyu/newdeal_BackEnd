package com.broadwave.backend.safety.service;

import com.broadwave.backend.Aws.AWSS3Service;
import com.broadwave.backend.common.AjaxResponse;
import com.broadwave.backend.common.ResponseErrorCode;
import com.broadwave.backend.safety.Safety;
import com.broadwave.backend.safety.SafetyRepository;
import com.broadwave.backend.safety.calculation.*;
import com.broadwave.backend.safety.safetyDtos.SafetyInfoDto;
import com.broadwave.backend.safety.safetyDtos.SafetyInsertListDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;

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
    private final AWSS3Service awss3Service;

    @Autowired
    public CalculationService(ModelMapper modelMapper, CalculationRepository calculationRepository, SafetyRepository safetyRepository,
                              AWSS3Service awss3Service){
        this.modelMapper = modelMapper;
        this.calculationRepository = calculationRepository;
        this.safetyRepository = safetyRepository;
        this.awss3Service = awss3Service;
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

    // NEWDEAL 계측 기반 안전성 추정 데이터 저장
    public ResponseEntity<Map<String, Object>> calculationSave(CalculationSet calculationSet, HttpServletRequest request) {
        log.info("calculationSave 호출성공");

        AjaxResponse res = new AjaxResponse();

        String login_id = request.getHeader("insert_id");
        log.info("현재 접속한 아이디 : "+login_id);

        ArrayList<CalculationDto> addList = calculationSet.getAdd(); // 추가 리스트 얻기
        ArrayList<CalculationDto> updateList = calculationSet.getUpdate(); // 수정 리스트 얻기

        log.info("추가 리스트 : "+addList);
        log.info("수정 리스트 : "+updateList);
        log.info("삭제 리스트 : "+calculationSet.getDeleteList());

        Optional<Safety> optionalSafety = safetyRepository.findById(calculationSet.getId());
        if(optionalSafety.isEmpty()){
            return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE006.getCode(), "교량 "+ResponseErrorCode.NDE006.getDesc(), null, null));
        }else{
            List<Calculation> calculationList = new ArrayList<>();

            // 안전성 추정 데이터 저장 시작
            if(addList.size()!=0){
                for (CalculationDto calculationDto : addList) {
                    log.info("안전성 추정 데이터 신규생성");
                    Calculation calculation = new Calculation();
                    calculation.setSfId(optionalSafety.get());
                    calculation.setCalYyyymmdd(calculationDto.getCalYyyymmdd());
                    calculation.setCalTemperature(calculationDto.getCalTemperature());
                    calculation.setCalCapacity(calculationDto.getCalCapacity());
                    calculation.setInsert_id(login_id);
                    calculation.setInsertDateTime(LocalDateTime.now());

                    calculationList.add(calculation);
                }

                log.info("저장 calculationList : " +calculationList);
                if(calculationList.size() != 0){
                    calculationRepository.saveAll(calculationList);
                    calculationList.clear();
                }
            }

            // 안전성 추정 데이터 수정 시작
            if(updateList.size()!=0){
                for (CalculationDto calculationDto : updateList) {
                    log.info("안전성 추정 데이터 수정");
                    Optional<Calculation> optionalCalculation = calculationRepository.findById(calculationDto.getId());
                    if(optionalCalculation.isPresent()){
                        Calculation calculation = modelMapper.map(optionalCalculation.get(), Calculation.class);
                        calculation.setCalYyyymmdd(calculationDto.getCalYyyymmdd());
                        calculation.setCalTemperature(calculationDto.getCalTemperature());
                        calculation.setCalCapacity(calculationDto.getCalCapacity());
                        calculation.setModify_id(login_id);
                        calculation.setModifyDateTime(LocalDateTime.now());
                        calculationList.add(calculation);
                    }else{
                        return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE006.getCode(), "수정 할 "+ResponseErrorCode.NDE006.getDesc(), null, null));
                    }
                }

                log.info("수정 calculationList : " +calculationList);
                if(calculationList.size() != 0){
                    calculationRepository.saveAll(calculationList);
                    calculationList.clear();
                }
            }

            // 안전성 추정 데이터 삭제 시작
            if(calculationSet.getDeleteList() != null && calculationSet.getDeleteList().size() != 0){
                calculationRepository.calculationDelete(calculationSet.getDeleteList());
            }

        }

        return ResponseEntity.ok(res.success());
    }

    public ResponseEntity<Map<String, Object>> bridgeList() {
        log.info("bridgeList 호출성공");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        List<SafetyInsertListDto> safetyInsertListDtos = safetyRepository.findBySafetyInsertList();
        data.put("safetyList",safetyInsertListDtos);

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    // NEWDEAL 계측 기반 교량 & 안전성 추정 데이터 삭제
    public ResponseEntity<Map<String, Object>> bridgeDataDelete(Long id, HttpServletRequest request) {
        log.info("bridgeDataDelete 호출성공");

        AjaxResponse res = new AjaxResponse();

        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
        String insert_id = request.getHeader("insert_id");

        log.info("JWT_AccessToken : "+JWT_AccessToken);
        log.info("insert_id : "+insert_id);

        Optional<Safety> optionalSafety = safetyRepository.findById(id);
        List<Calculation> calculationList = calculationRepository.findBySfId(id);
        if(optionalSafety.isPresent()){
            if(optionalSafety.get().getSfFilePath() != null && optionalSafety.get().getSfFileName() != null){
                awss3Service.deleteObject(optionalSafety.get().getSfFilePath(), optionalSafety.get().getSfFileName());
            }
            safetyRepository.delete(optionalSafety.get());
            calculationRepository.deleteAll(calculationList);
        }else{
            return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE006.getCode(), "교량 "+ResponseErrorCode.NDE006.getDesc(), ResponseErrorCode.NDE031.getCode(), ResponseErrorCode.NDE031.getDesc()));
        }

        return ResponseEntity.ok(res.success());
    }

}
