package com.broadwave.backend.performance.reference;

import com.broadwave.backend.common.AjaxResponse;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Minkyu
 * Date : 2021-11-02
 * Remark : NEWDEAL 성능개선사업평가 참조테이블 컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api/performance/reference")
public class ReferenceRestController {

    private final ModelMapper modelMapper;
    private final ReferenceService referenceService;

    @Autowired
    public ReferenceRestController(ModelMapper modelMapper, ReferenceService referenceService) {
        this.modelMapper = modelMapper;
        this.referenceService = referenceService;
    }

    // NEWDEAL 성능개선사업평가 참조테이블 설정 - 기술성 셋팅 함수
    @PostMapping("/techSave")
    public ResponseEntity<Map<String,Object>> techSave(@ModelAttribute ReferenceTechnicalityMapperDto referenceTechnicalityMapperDto, HttpServletRequest request) {

//        log.info("기술성 셋팅 함수 호출");
        AjaxResponse res = new AjaxResponse();

        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
        String currentuserid = request.getHeader("insert_id");
        log.info("JWT_AccessToken : " + JWT_AccessToken);

        ReferenceTechnicality referenceTechnicality = modelMapper.map(referenceTechnicalityMapperDto, ReferenceTechnicality.class);

        referenceTechnicality.setId("tech");
        referenceTechnicality.setInsert_id(currentuserid);
        referenceTechnicality.setInsertDateTime(LocalDateTime.now());

//        log.info("referenceTechnicality : " + referenceTechnicality);

        referenceService.techSave(referenceTechnicality);

        return ResponseEntity.ok(res.success());

    }

    // NEWDEAL 성능개선사업평가 참조테이블 설정 - 기술성 데이터호출
    @GetMapping("/techData")
    public ResponseEntity<Map<String,Object>> techData(HttpServletRequest request) {

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
        log.info("JWT_AccessToken : " + JWT_AccessToken);

        ReferenceTechnicality technicality = referenceService.techData("tech");
//        log.info("technicality : " + technicality);
        data.put("technicality",technicality);

        return ResponseEntity.ok(res.dataSendSuccess(data));

    }

    // NEWDEAL 성능개선사업평가 참조테이블 설정 - 경제성 셋팅 함수
    @PostMapping("/ecoSave")
    public ResponseEntity<Map<String,Object>> ecoSave(@ModelAttribute ReferenceEconomyMapperDto referenceEconomyMapperDto, HttpServletRequest request) {

//        log.info("기술성 셋팅 함수 호출");
        AjaxResponse res = new AjaxResponse();

        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
        String currentuserid = request.getHeader("insert_id");
        log.info("JWT_AccessToken : " + JWT_AccessToken);

        ReferenceEconomy referenceEconomy = modelMapper.map(referenceEconomyMapperDto, ReferenceEconomy.class);

        referenceEconomy.setId("eco");
        referenceEconomy.setInsert_id(currentuserid);
        referenceEconomy.setInsertDateTime(LocalDateTime.now());

//        log.info("referenceEconomy : " + referenceEconomy);

        referenceService.ecoSave(referenceEconomy);

        return ResponseEntity.ok(res.success());

    }

    // NEWDEAL 성능개선사업평가 참조테이블 설정 - 경제성 데이터호출
    @GetMapping("/ecoData")
    public ResponseEntity<Map<String,Object>> ecoData(HttpServletRequest request) {

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
        log.info("JWT_AccessToken : " + JWT_AccessToken);

        ReferenceEconomy economy = referenceService.ecoData("eco");
//        log.info("economy : " + economy);
        data.put("economy",economy);

        return ResponseEntity.ok(res.dataSendSuccess(data));

    }

    // NEWDEAL 성능개선사업평가 참조테이블 설정 - 정책성 셋팅 함수
    @PostMapping("/policySave")
    public ResponseEntity<Map<String,Object>> policySave(@ModelAttribute ReferencePolicyMapperDto referencePolicyMapperDto, HttpServletRequest request) {

//        log.info("기술성 셋팅 함수 호출");
        AjaxResponse res = new AjaxResponse();

        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
        String currentuserid = request.getHeader("insert_id");
        log.info("JWT_AccessToken : " + JWT_AccessToken);

        ReferencePolicy referencePolicy = modelMapper.map(referencePolicyMapperDto, ReferencePolicy.class);

        referencePolicy.setId("policy");
        referencePolicy.setInsert_id(currentuserid);
        referencePolicy.setInsertDateTime(LocalDateTime.now());

//        log.info("referencePolicy : " + referencePolicy);

        referenceService.policySave(referencePolicy);

        return ResponseEntity.ok(res.success());

    }

    // NEWDEAL 성능개선사업평가 참조테이블 설정 - 정책성 데이터호출
    @GetMapping("/policyData")
    public ResponseEntity<Map<String,Object>> policyData(HttpServletRequest request) {

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
        log.info("JWT_AccessToken : " + JWT_AccessToken);

        ReferencePolicy policy = referenceService.policyData("policy");
//        log.info("policy : " + policy);
        data.put("policy",policy);

        return ResponseEntity.ok(res.dataSendSuccess(data));

    }


}
