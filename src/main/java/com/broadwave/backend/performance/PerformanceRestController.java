package com.broadwave.backend.performance;

import com.broadwave.backend.Aws.AWSS3Service;
import com.broadwave.backend.account.AccountDtos.AccountBaseDto;
import com.broadwave.backend.account.AccountService;
import com.broadwave.backend.common.AjaxResponse;
import com.broadwave.backend.common.ResponseErrorCode;
import com.broadwave.backend.keygenerate.KeyGenerateService;
import com.broadwave.backend.performance.performanceDtos.*;
import com.broadwave.backend.performance.reference.ReferenceService;
import com.broadwave.backend.performance.reference.economy.ReferenceEconomy;
import com.broadwave.backend.performance.reference.policy.ReferencePolicy;
import com.broadwave.backend.performance.reference.technicality.ReferenceTechnicality;
import com.broadwave.backend.performance.reference.weightSetting.ReferenceWeight;
import com.broadwave.backend.performance.reference.weightSetting.weightSettingDtos.ReferenceWeightBaseDto;
import com.broadwave.backend.performance.reference.weightSetting.weightSettingDtos.ReferenceWeightOldDto;
import com.broadwave.backend.performance.reference.weightSetting.weightSettingDtos.ReferenceWeightUseDto;
import com.broadwave.backend.performance.uploadFile.Uploadfile;
import com.broadwave.backend.performance.weight.Weight;
import com.broadwave.backend.performance.weight.WeightDto;
import com.broadwave.backend.performance.weight.WeightMapperDto;
import com.broadwave.backend.performance.weight.WeightService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author Minkyu
 * Date : 2021-07-07
 * Remark : NEWDEAL 성능개선사업평가 컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api/performance")
public class PerformanceRestController {

    @Value("${newdeal.aws.s3.bucket.url}")
    private String AWSBUCKETURL;

    private final ModelMapper modelMapper;
    private final PerformanceService performanceService;
    private final AccountService accountService;
    private final WeightService weightService;
    private final PerformanceFunctionService performanceFunctionService;
    private final KeyGenerateService keyGenerateService;
    private final ReferenceService referenceService;

    private final AWSS3Service awss3Service;

    @Autowired
    public PerformanceRestController(ModelMapper modelMapper, PerformanceService performanceService, AccountService accountService,
                                     WeightService weightService, PerformanceFunctionService performanceFunctionService, KeyGenerateService keyGenerateService, ReferenceService referenceService, AWSS3Service awss3Service) {
        this.modelMapper = modelMapper;
        this.performanceService = performanceService;
        this.accountService = accountService;
        this.weightService = weightService;
        this.performanceFunctionService = performanceFunctionService;
        this.keyGenerateService = keyGenerateService;
        this.referenceService = referenceService;
        this.awss3Service = awss3Service;
    }

    // NEWDEAL 성능개선사업평가 Input 중간저장게시물이 있는지 조회하기
    @PostMapping("/middleCheck")
    public ResponseEntity<Map<String,Object>> middleCheck(HttpServletRequest request) {

        log.info("middleCheck 호출성공");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
        String insert_id = request.getHeader("insert_id");
        log.info("JWT_AccessToken : "+JWT_AccessToken);
        log.info("insert_id : "+insert_id);

        PerformanceCheckDto performance = performanceService.findByInsertId(insert_id);
        log.info("middleCheck performance : "+performance);

        String nowDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));

        AccountBaseDto accountBaseDto = accountService.findByAcountBase(insert_id);
        data.put("accountData",accountBaseDto);
        data.put("nowDate",nowDate);

        if(performance==null){
            data.put("middleSave",0);
        }else{
            data.put("middleSave",1);
            data.put("piAutoNum",performance.getPiAutoNum());
            data.put("piBusiness",performance.getPiBusiness());
            data.put("piInputSkip",performance.getPiInputSkip());
        }
        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    // NEWDEAL 성능개선사업평가 Input 중간저장게시물의 첫번째 데이터 불러오기
    @PostMapping("/middleData")
    public ResponseEntity<Map<String,Object>> middleData(@RequestParam("autoNum")String autoNum,HttpServletRequest request) {

        log.info("middleData 호출성공");
        log.info("autoNum : "+autoNum);
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
        String insert_id = request.getHeader("insert_id");
        log.info("JWT_AccessToken : "+JWT_AccessToken);
        log.info("insert_id : "+insert_id);

        PerformanceMiddleDataDto performance = performanceService.findByInsertIAndAutoNum(insert_id,autoNum);
//        log.info("middleData performance : "+performance);

        if(performance!=null){
            data.put("performanceData",performance);
        }else{
            return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE019.getCode(), ResponseErrorCode.NDE019.getDesc(), ResponseErrorCode.NDE020.getCode(), ResponseErrorCode.NDE020.getDesc()));
        }

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    // NEWDEAL 성능개선사업평가 Input 중간저장게시물의 두번째 데이터 불러오기
    @PostMapping("/middleDataBusiness")
    public ResponseEntity<Map<String,Object>> middleDataBusiness(@RequestParam("autoNum")String autoNum,HttpServletRequest request) {

        log.info("middleDataBusiness 호출성공");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
        String insert_id = request.getHeader("insert_id");
        log.info("JWT_AccessToken : "+JWT_AccessToken);
        log.info("insert_id : "+insert_id);

        List<PerformanceMiddleBusinessDataDto> performance = performanceService.findByInsertIAndAutoNum2(insert_id,autoNum);
//        log.info("middleData performance : "+performance);

        data.put("performance",performance);
        data.put("size",performance.size());

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

//    // NEWDEAL 성능개선사업평가 유형 가져오기
//    @PostMapping("/weightBusiness")
//    public ResponseEntity<Map<String,Object>> weightBusiness(@RequestParam("autoNum")String autoNum,HttpServletRequest request) {
//
//        log.info("weightBusiness 호출성공");
//
//        AjaxResponse res = new AjaxResponse();
//        HashMap<String, Object> data = new HashMap<>();
//
//        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
//        String insert_id = request.getHeader("insert_id");
//        log.info("JWT_AccessToken : "+JWT_AccessToken);
//        log.info("insert_id : "+insert_id);
//
//        PerformancePiBusinessDto performance = performanceService.findByInsertIAndAutoNumAndCount(insert_id,autoNum);
//        log.info("성능개선 유형 : "+performance.getPiBusiness());
//
//        data.put("facilityType",performance.getPiFacilityType());
//        data.put("weightBusiness",performance.getPiBusiness());
//
//        return ResponseEntity.ok(res.dataSendSuccess(data));
//    }

    // NEWDEAL 성능개선사업평가 Input 아니오를 누르면 중간저장된 게시물을 삭제 할 함수
    @PostMapping("/middleDataDel")
    public ResponseEntity<Map<String,Object>> middleDataDel(@RequestParam("autoNum")String autoNum,HttpServletRequest request) {

        log.info("middleDataDel 호출성공");

        AjaxResponse res = new AjaxResponse();

        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
        String insert_id = request.getHeader("insert_id");
        log.info("JWT_AccessToken : "+JWT_AccessToken);
        log.info("insert_id : "+insert_id);
        log.info("삭제 할 일련번호 : "+autoNum);

        List<Performance> optionalPerformance = performanceService.findByPiAutoNumAndInsert_idDel(autoNum,insert_id,0);
        log.info("삭제 optionalPerformance : "+optionalPerformance);
        for(int i=0; i<optionalPerformance.size(); i++){
            performanceService.delete(optionalPerformance.get(i));
        }

        return ResponseEntity.ok(res.success());
    }

    // NEWDEAL 성능개선사업평가 Performance1 중간저장 세이브
    @PostMapping("/middleSaveUpdate/{autoNum}")
    public ResponseEntity<Map<String,Object>> middleSaveUpdate(@ModelAttribute PerformanceMiddleSaveDto performanceMiddleSaveDto, @PathVariable String autoNum, HttpServletRequest request) {

        log.info("NEWDEAL 성능개선사업평가 Performance1 중간저장 세이브");

        log.info("middleSaveUpdate 호출성공");
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
        String currentuserid = request.getHeader("insert_id");
        log.info("JWT_AccessToken : " + JWT_AccessToken);

        Performance performance = modelMapper.map(performanceMiddleSaveDto, Performance.class);
        log.info("autoNum : " + autoNum);

        String piFacilityType = performance.getPiFacilityType();
        if(piFacilityType==null){
            return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE021.getCode(), ResponseErrorCode.NDE021.getDesc(),null,null));
        }else if(piFacilityType.equals("보도육교") || piFacilityType.equals("지하차도") || piFacilityType.equals("절토사면")|| piFacilityType.equals("옹벽")){
            performance.setPiUsabilityAndGoalLevel("기타");
        }

        if(autoNum.equals("null")){
            log.info("일련번호 생성");
            Date now = new Date();
            SimpleDateFormat yyMM = new SimpleDateFormat("yyMM");
            String newAutoNum = keyGenerateService.keyGenerate("nd_pi_input", yyMM.format(now), currentuserid);
            performance.setPiAutoNum(newAutoNum);
            performance.setPiInputMiddleSave(0);
            performance.setPiInputCount(1);
            performance.setInsert_id(currentuserid);
            performance.setInsertDateTime(LocalDateTime.now());

            data.put("autoNum",newAutoNum);
            data.put("businessNum",performance.getPiBusiness());
        }else{
            Optional<Performance> optionalPerformance = performanceService.findByPiAutoNumAndInsert_id(autoNum,currentuserid);
            log.info("optionalPerformance : "+optionalPerformance);
            if(optionalPerformance.isPresent()){
                performance.setId(optionalPerformance.get().getId());
                performance.setPiAutoNum(autoNum);

                //여기서부터 비지니스 중간저장 및 수정
                performance.setPiBusinessType(optionalPerformance.get().getPiBusinessType());
                performance.setPiTargetAbsence(optionalPerformance.get().getPiTargetAbsence());
                performance.setPiBusinessClassification(optionalPerformance.get().getPiBusinessClassification());
                performance.setPiBusinessExpenses(optionalPerformance.get().getPiBusinessExpenses());
                performance.setPiBeforeSafetyRating(optionalPerformance.get().getPiBeforeSafetyRating());
                performance.setPiAfterSafetyRating(optionalPerformance.get().getPiAfterSafetyRating());
                performance.setPiBusinessValidity(optionalPerformance.get().getPiBusinessValidity());
//                performance.setPiBusinessObligatory(optionalPerformance.get().getPiBusinessObligatory());
//                performance.setPiBusinessMandatory(optionalPerformance.get().getPiBusinessMandatory());
//                performance.setPiBusinessPlanned(optionalPerformance.get().getPiBusinessPlanned());
                performance.setPiWhether(optionalPerformance.get().getPiWhether());

                performance.setPiInputCount(optionalPerformance.get().getPiInputCount());
                performance.setPiInputGreat(optionalPerformance.get().getPiInputGreat());
                performance.setPiInputMiddleSave(optionalPerformance.get().getPiInputMiddleSave());

                performance.setInsert_id(optionalPerformance.get().getInsert_id());
                performance.setInsertDateTime(optionalPerformance.get().getInsertDateTime());
                performance.setModify_id(currentuserid);
                performance.setModifyDateTime(LocalDateTime.now());

                data.put("autoNum", autoNum);
                data.put("businessNum",performance.getPiBusiness());
            }else {
                return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE019.getCode(), ResponseErrorCode.NDE019.getDesc(),ResponseErrorCode.NDE020.getCode(), ResponseErrorCode.NDE020.getDesc()));
            }
        }

        log.info("중간저장 performance : " + performance);

        //중간저장하기
        performanceService.save(performance);
        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    // NEWDEAL 성능개선사업평가 Performance2 중간저장 세이브
    @PostMapping("/middleSaveUpdateBusiness/{autoNum}")
    public ResponseEntity<Map<String,Object>> middleSaveUpdateBusiness(@ModelAttribute PerformanceMiddleSaveBusinessDto performanceMiddleSaveBusinessDto, @PathVariable String autoNum, HttpServletRequest request) {

        log.info("NEWDEAL 성능개선사업평가 Performance2 중간저장 세이브");
        log.info("middleSaveUpdateBusiness 호출성공");
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
        String insert_id = request.getHeader("insert_id");
        log.info("JWT_AccessToken : " + JWT_AccessToken);
        log.info("insert_id : " + insert_id);

        Performance optionalPerformance = performanceService.findByBusiness(autoNum,insert_id);

        if(optionalPerformance == null ){
            log.info("존재하지않음.");
        }else {
            log.info("현재 일려번호 AutoNum : " + optionalPerformance.getPiAutoNum());
            log.info("현재 사업구분 PiBusiness : " + optionalPerformance.getPiBusiness());
            System.out.println();

            List<PerformanceMiddleBusinessDataDto> listPerformance = performanceService.findByInsertIAndAutoNum2(insert_id,autoNum);
            log.info("List Performance : "+listPerformance);
            log.info("List Performance.size() : "+listPerformance.size());
            System.out.println();

            log.info("optionalPerformance : " + optionalPerformance);
            log.info("performanceMiddleSaveBusinessDto : " + performanceMiddleSaveBusinessDto);
            System.out.println();

            log.info("for문 도는 수 : " + performanceMiddleSaveBusinessDto.getBusinessCount());
            if(performanceMiddleSaveBusinessDto.getBusinessCount()==2) {
                log.info("*  대안이 2개일 때, 신규등록 or 업데이트 *");
                // 대안이 2개일 때, 신규등록 or 업데이트
                for (int i = 0; i < performanceMiddleSaveBusinessDto.getBusinessCount(); i++) {

                    Performance performance = modelMapper.map(optionalPerformance, Performance.class);

                    performance.setPiTargetAbsence(performanceMiddleSaveBusinessDto.getPiTargetAbsence().get(i));
                    performance.setPiBusinessClassification(performanceMiddleSaveBusinessDto.getPiBusinessClassification().get(i));
                    performance.setPiBusinessExpenses(performanceMiddleSaveBusinessDto.getPiBusinessExpenses().get(i));

                    performance.setPiBeforeSafetyRating(performanceMiddleSaveBusinessDto.getPiBeforeSafetyRating().get(i));
                    performance.setPiAfterSafetyRating(performanceMiddleSaveBusinessDto.getPiAfterSafetyRating().get(i));

                    performance.setPiWhether(performanceMiddleSaveBusinessDto.getPiWhether());

                    performance.setPiInputGreat(0);

                    performance.setPiInputCount(i+1);

                    if (i == 0 ) {
                        performance.setId(listPerformance.get(i).getId());
                        performance.setPiBusinessType(performanceMiddleSaveBusinessDto.getPiBusinessType1());
                        performance.setPiBusinessValidity(performanceMiddleSaveBusinessDto.getPiBusinessValidity1());
//                        performance.setPiBusinessObligatory(performanceMiddleSaveBusinessDto.getPiBusinessObligatory1());
//                        performance.setPiBusinessMandatory(performanceMiddleSaveBusinessDto.getPiBusinessMandatory1());
//                        performance.setPiBusinessPlanned(performanceMiddleSaveBusinessDto.getPiBusinessPlanned1());

                        // 더미데이터삭제
                        if(3<=listPerformance.size()){
                            Optional<Performance> garbageDataPerformance = performanceService.findById(listPerformance.get(2).getId());
                            garbageDataPerformance.ifPresent(performanceService::delete);
                        }
                        if(4<=listPerformance.size()){
                            Optional<Performance> garbageDataPerformance = performanceService.findById(listPerformance.get(3).getId());
                            garbageDataPerformance.ifPresent(performanceService::delete);
                        }
                    } else if(i == 1){
                        if(2<=listPerformance.size()){
                            //수정일때,
                            performance.setId(listPerformance.get(i).getId());
                        }else{
                            //신규일때,
                            performance.setId(null);
                        }
                        performance.setPiBusinessType(performanceMiddleSaveBusinessDto.getPiBusinessType2());
                        performance.setPiBusinessValidity(performanceMiddleSaveBusinessDto.getPiBusinessValidity2());
                    }

                    log.info("신규 등록 "+(i+1)+"번째 대안 : " + performance);
                    System.out.println();

                    // 중간저장하기2
                    performanceService.save(performance);
                }
            }else if(performanceMiddleSaveBusinessDto.getBusinessCount()==3){
                log.info("*  대안이 3개일 때, 신규등록 or 업데이트 *");
                // 대안이 3개일 때, 신규등록 or 업데이트
                for (int i = 0; i < performanceMiddleSaveBusinessDto.getBusinessCount(); i++) {
                    try {
                        Performance performance = modelMapper.map(optionalPerformance, Performance.class);

                        performance.setPiTargetAbsence(performanceMiddleSaveBusinessDto.getPiTargetAbsence().get(i));
                        performance.setPiBusinessClassification(performanceMiddleSaveBusinessDto.getPiBusinessClassification().get(i));
                        performance.setPiBusinessExpenses(performanceMiddleSaveBusinessDto.getPiBusinessExpenses().get(i));

                        performance.setPiBeforeSafetyRating(performanceMiddleSaveBusinessDto.getPiBeforeSafetyRating().get(i));
                        performance.setPiAfterSafetyRating(performanceMiddleSaveBusinessDto.getPiAfterSafetyRating().get(i));

                        performance.setPiWhether(performanceMiddleSaveBusinessDto.getPiWhether());

                        performance.setPiInputGreat(0);

                        performance.setPiInputCount(i+1);

                        if (i == 0) {
                            performance.setId(listPerformance.get(i).getId());
                            performance.setPiBusinessType(performanceMiddleSaveBusinessDto.getPiBusinessType1());
                            performance.setPiBusinessValidity(performanceMiddleSaveBusinessDto.getPiBusinessValidity1());

                            // 더미데이터삭제
                            if(4<=listPerformance.size()){
                                Optional<Performance> garbageDataPerformance = performanceService.findById(listPerformance.get(3).getId());
                                garbageDataPerformance.ifPresent(performanceService::delete);
                            }
                        } else if(i == 1){
                            if(2<=listPerformance.size()){
                                performance.setId(listPerformance.get(i).getId());
                            }else{
                                performance.setId(null);
                            }
                            performance.setPiBusinessType(performanceMiddleSaveBusinessDto.getPiBusinessType2());
                            performance.setPiBusinessValidity(performanceMiddleSaveBusinessDto.getPiBusinessValidity2());

                        } else if(i == 2){
                            if(3<=listPerformance.size()){
                                performance.setId(listPerformance.get(i).getId());
                            }else{
                                performance.setId(null);
                            }
                            performance.setPiBusinessType(performanceMiddleSaveBusinessDto.getPiBusinessType3());
                            performance.setPiBusinessValidity(performanceMiddleSaveBusinessDto.getPiBusinessValidity3());
                        }

                        log.info("신규 등록 "+(i+1)+"번째 대안 : " + performance);
                        System.out.println();

                        // 중간저장하기2
                        performanceService.save(performance);
                    }catch (Exception e){
                        log.info("예외발생 : "+e);
                        data.put("again", "again");
                    }
                }
            }else {
                log.info("*  대안이 4개일 때, 신규등록 or 업데이트 *");
                // 대안이 4개일 때, 신규등록 or 업데이트
                for (int i = 0; i < performanceMiddleSaveBusinessDto.getBusinessCount(); i++) {

                    try {
                        Performance performance = modelMapper.map(optionalPerformance, Performance.class);

                        performance.setPiTargetAbsence(performanceMiddleSaveBusinessDto.getPiTargetAbsence().get(i));
                        performance.setPiBusinessClassification(performanceMiddleSaveBusinessDto.getPiBusinessClassification().get(i));
                        performance.setPiBusinessExpenses(performanceMiddleSaveBusinessDto.getPiBusinessExpenses().get(i));

                        performance.setPiBeforeSafetyRating(performanceMiddleSaveBusinessDto.getPiBeforeSafetyRating().get(i));
                        performance.setPiAfterSafetyRating(performanceMiddleSaveBusinessDto.getPiAfterSafetyRating().get(i));

                        performance.setPiWhether(performanceMiddleSaveBusinessDto.getPiWhether());

                        performance.setPiInputGreat(0);

                        performance.setPiInputCount(i+1);

                        if (i == 0) {
                            performance.setId(listPerformance.get(i).getId());
                            performance.setPiBusinessType(performanceMiddleSaveBusinessDto.getPiBusinessType1());
                            performance.setPiBusinessValidity(performanceMiddleSaveBusinessDto.getPiBusinessValidity1());
//                            performance.setPiBusinessObligatory(performanceMiddleSaveBusinessDto.getPiBusinessObligatory1());
//                            performance.setPiBusinessMandatory(performanceMiddleSaveBusinessDto.getPiBusinessMandatory1());
//                            performance.setPiBusinessPlanned(performanceMiddleSaveBusinessDto.getPiBusinessPlanned1());
                        } else if(i == 1){
                            if(2<=listPerformance.size()){
                                performance.setId(listPerformance.get(i).getId());
                            }else{
                                performance.setId(null);
                            }
                            performance.setPiBusinessType(performanceMiddleSaveBusinessDto.getPiBusinessType2());
                            performance.setPiBusinessValidity(performanceMiddleSaveBusinessDto.getPiBusinessValidity2());
                        } else if(i == 2){
                            if(3<=listPerformance.size()){
                                performance.setId(listPerformance.get(i).getId());
                            }else{
                                performance.setId(null);
                            }
                            performance.setPiBusinessType(performanceMiddleSaveBusinessDto.getPiBusinessType3());
                            performance.setPiBusinessValidity(performanceMiddleSaveBusinessDto.getPiBusinessValidity3());
                        } else if(i == 3){
                            if(4<=listPerformance.size()){
                                performance.setId(listPerformance.get(i).getId());
                            }else{
                                performance.setId(null);
                            }
                            performance.setPiBusinessType(performanceMiddleSaveBusinessDto.getPiBusinessType4());
                            performance.setPiBusinessValidity(performanceMiddleSaveBusinessDto.getPiBusinessValidity4());
                        }

                        log.info("신규 등록 "+(i+1)+"번째 대안 : " + performance);
                        System.out.println();

                        // 중간저장하기2
                        performanceService.save(performance);
                    }catch (Exception e){
                        log.info("예외발생 : "+e);
                        data.put("again", "again");
                    }

                }
            }

        }

        data.put("autoNum", autoNum);

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    // NEWDEAL 성능개선사업평가 Performance3 마지막번째 세이브
    @PostMapping("/weightSave/{autoNum}")
    public ResponseEntity<Map<String,Object>> weightSave(@ModelAttribute WeightMapperDto weightMapperDto,@PathVariable String autoNum, HttpServletRequest request) {

        log.info("weightSave 호출성공");
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
        String insert_id = request.getHeader("insert_id");
        log.info("JWT_AccessToken : " + JWT_AccessToken);
        log.info("insert_id : " + insert_id);

        log.info("일련번호 : "+autoNum);

        List<Performance> performanceList = performanceService.findByPiAutoNumAndInsert_idDel(autoNum,insert_id,0);
        log.info("가중치 저장하고, 업데이트할 데이터 : "+performanceList);
        System.out.println();

        for(int i=0; i<performanceList.size(); i++){
            Performance performance = modelMapper.map(performanceList.get(i), Performance.class);
            performance.setPiInputMiddleSave(1);
            log.info(i+"번째 performance : "+performance);
            System.out.println();
            performanceService.save(performance);
        }

        // 가중치 셋팅
        log.info("weightMapperDto : " + weightMapperDto);
        Optional<Weight> optionalWeight = weightService.findByAutoNumAndInsertId(autoNum, insert_id);
        Weight weight;
        if(optionalWeight.isPresent()){

            optionalWeight.get().setPiWeightTechnicality(weightMapperDto.getPiWeightTechnicality());
            optionalWeight.get().setPiWeightEconomy(weightMapperDto.getPiWeightEconomy());
            optionalWeight.get().setPiWeightPolicy(weightMapperDto.getPiWeightPolicy());

            optionalWeight.get().setPiWeightSafe(weightMapperDto.getPiWeightSafe());
            optionalWeight.get().setPiWeightUsability(weightMapperDto.getPiWeightUsability());
            optionalWeight.get().setPiWeightOld(weightMapperDto.getPiWeightOld());
            optionalWeight.get().setPiWeightUrgency(weightMapperDto.getPiWeightUrgency());
            optionalWeight.get().setPiWeightGoal(weightMapperDto.getPiWeightGoal());

            optionalWeight.get().setPiWeightSafeUtility(weightMapperDto.getPiWeightSafeUtility());
            optionalWeight.get().setPiWeightCostUtility(weightMapperDto.getPiWeightCostUtility());

            optionalWeight.get().setPiWeightBusiness(weightMapperDto.getPiWeightBusiness());
            optionalWeight.get().setPiWeightComplaint(weightMapperDto.getPiWeightComplaint());
            optionalWeight.get().setPiWeightBusinessEffect(weightMapperDto.getPiWeightBusinessEffect());

            optionalWeight.get().setPiWeightCriticalScore(weightMapperDto.getPiWeightCriticalScore());

            optionalWeight.get().setModify_id(insert_id);
            optionalWeight.get().setModifyDateTime(LocalDateTime.now());
            log.info("가중치 : "+optionalWeight.get());
            weightService.save(optionalWeight.get());
        }else{
            weight = modelMapper.map(weightMapperDto, Weight.class);
            weight.setPiAutoNum(autoNum);
            weight.setInsert_id(insert_id);
            weight.setInsertDateTime(LocalDateTime.now());
            log.info("가중치 : "+weight);
            weightService.save(weight);
        }

        data.put("autoNum", autoNum);

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    // NEWDEAL 성능개선사업평가 엑셀 업로드
    @Transactional
    @PostMapping("/excelUpload")
    public ResponseEntity<Map<String,Object>> readExcel(@ModelAttribute WeightMapperDto weightMapperDto, @RequestParam("excelfile") MultipartFile excelfile, HttpServletRequest request) throws IOException {

        log.info("excelUpload 호출성공");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
        String currentuserid = request.getHeader("insert_id");
        log.info("JWT_AccessToken : " + JWT_AccessToken);
        log.info("currentuserid : " + currentuserid);

        if (JWT_AccessToken==null || currentuserid==null) {
            return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE015.getCode(), ResponseErrorCode.NDE015.getDesc(), ResponseErrorCode.NDE016.getCode(), ResponseErrorCode.NDE016.getDesc()));
        }

        String extension = FilenameUtils.getExtension(excelfile.getOriginalFilename());
        log.info("확장자 : " + extension);

        // 확장자가 엑셀이 맞는지 확인
        Workbook workbook;
        assert extension != null;
        if (extension.equals("xlsx")) {
            workbook = new XSSFWorkbook(excelfile.getInputStream());  // -> .xlsx
        } else {
            workbook = new HSSFWorkbook(excelfile.getInputStream());  // -> .xls
        }

        Sheet worksheet = workbook.getSheetAt(0); // 첫번째 시트
//        Sheet worksheet = workbook.getSheetAt(1); // 두번째 시트

        // 제공한 양식 엑셀파일이 맞는지 확인 (첫번째시트)
        String businessData = weightMapperDto.getWeightCategory();
        String piBusiness;
        log.info("유형 : "+businessData);
        try {
            Row rowCheck;
            Cell cellDataCheck;
//            log.info("cellDataCheck : " + cellDataCheck.toString());

            rowCheck = worksheet.getRow(3);
            cellDataCheck = rowCheck.getCell(3);
            CellType ct = cellDataCheck.getCellType();
            if (ct == CellType.BLANK) {
                return ResponseEntity.ok(res.fail("문자", "필수사항 대안1의 교량을 입력해주세요.", "문자", "혹은 양식파일을 다시 받아주세요."));
            }
            rowCheck = worksheet.getRow(3);
            cellDataCheck = rowCheck.getCell(4);
            ct = cellDataCheck.getCellType();
            if (ct == CellType.BLANK) {
                return ResponseEntity.ok(res.fail("문자", "필수사항 대안2의 교량을 입력해주세요.", "문자", "혹은 양식파일을 다시 받아주세요."));
            }
            rowCheck = worksheet.getRow(2);
            cellDataCheck = rowCheck.getCell(2);
            if (!cellDataCheck.toString().equals("입력정보")) {
//                log.info("2열,2행 값이 양식에 맞지않음.");
                return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE012.getCode(), ResponseErrorCode.NDE012.getDesc(), null, null));
            }

            Row rowBusiness = worksheet.getRow(1);
            Object rowBusinessCheck = rowBusiness.getCell(1);
            piBusiness = rowBusinessCheck.toString().substring(0,2);
        } catch (NullPointerException e) {
//            log.info("1열,1행 값이 양식에 맞지않음.");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE012.getCode(), ResponseErrorCode.NDE012.getDesc(), null, null));
        }

        log.info("piBusiness : "+piBusiness);

        // 일련번호 카운트 생성
        String autoNum;
        if(weightMapperDto.getPiAutoNum() == null || weightMapperDto.getPiAutoNum().equals("")){
            log.info("일련번호 생성");
            Date now = new Date();
            SimpleDateFormat yyMM = new SimpleDateFormat("yyMM");
            autoNum = keyGenerateService.keyGenerate("nd_pi_input", yyMM.format(now), currentuserid);
        }else{
            autoNum = weightMapperDto.getPiAutoNum();
            // 현재 일련번호의 글 삭제하기
            List<Performance> performanceList = performanceService.findByPiAutoNumAndInsert_idDel(autoNum, currentuserid, 1);
            if(performanceList.size() != 0){
                // 기존 파일이 존재하면 파일삭제
                if(performanceList.get(0).getPiFileYn().equals("Y")){
                    Optional<Uploadfile> optionalUploadfile = performanceService.findByUploadFile(autoNum, currentuserid);
                    Optional<Weight> optionalWeight = weightService.findByAutoNumAndInsertId(autoNum, currentuserid);
                    if(optionalUploadfile.isPresent()){
                        if(optionalUploadfile.get().getPiFilePath() != null && optionalUploadfile.get().getPiFileName() != null){
                            String path = "/performance-upload-excelfiles/"+optionalUploadfile.get().getPiFileYyyymmdd();
                            awss3Service.deleteObject(path, optionalUploadfile.get().getPiFileName());
                            performanceService.deleteUploadFile(optionalUploadfile.get());
                            performanceService.findByPerformanceListDelete(performanceList);
                            if(optionalWeight.isPresent()){
                                weightService.delete(optionalWeight.get());
                            }else{
                                log.info("가중치가 존재하지 않음");
                            }
                        }
                    }else{
                        log.info("파일이 존재하지 않음");
                    }
                }
            }
        }

        // 가중치 셋팅
        Weight weight = modelMapper.map(weightMapperDto, Weight.class);
        log.info("가중치 : "+weight);

        SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
        String fileRealName;

        if(piBusiness.equals("노후") && piBusiness.equals(businessData.substring(0,2))){
            log.info("노후화 유형 엑셀파일입니다.");

            fileRealName = "노후화대응_유형_양식_"+date+".xlsx";

            ArrayList<Object> excelList = new ArrayList<>();
            List<Performance> ListPerformance = new ArrayList<>();
            log.info("getPhysicalNumberOfRows : " + worksheet.getPhysicalNumberOfRows());
            log.info("");

            for (int i = 3; i < 7; i++) {
                Performance performance = new Performance();
                for (int j = 3; j < worksheet.getPhysicalNumberOfRows(); j++) {
                    Row row = worksheet.getRow(j);
                    Cell cellData = row.getCell(i);
                    CellType ct = cellData.getCellType();
//                    log.info( j +" 셀타입 : "+ct);
                    if (ct == CellType.BLANK) {
                        if (j == 4 || j == 6 || j == 7 || j == 9 || j == 10 || j == 11 || j == 12 || j == 15 || j == 16 || j == 19 || j == 20 || j == 21 || j == 22 || j == 23 || j == 24) {
                            return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE029.getCode(), ResponseErrorCode.NDE029.getDesc(), ResponseErrorCode.NDE030.getCode(), i+"열의 "+j+"번째 행"+ResponseErrorCode.NDE030.getDesc()));
                        }else{
                            if(j == 3){
                                log.info("대안 종료");
                                excelList.add("");
                                break;
                            }else{
//                                log.info("이곳은 널 입니다.");
                                excelList.add("");
                            }
                        }
                    } else if (ct == CellType.NUMERIC || ct == CellType.STRING) {
                        if (j == 6 || j == 7 || j == 9 || j == 15 || j == 19 || j == 24) {
                            try {
                                String cost = cellData.toString();
                                if (cost.contains("E")) {
//                                    log.info("문자가 E가 포함되어 있습니다.");
//                                    log.info("cost : " + cost);
                                    BigDecimal costCheck = new BigDecimal(Double.parseDouble(cost));
//                                    log.info("숫자로 변환 : " + costCheck);
                                    excelList.add(costCheck);
                                } else {
//                                    log.info("문자 E가 포함되어 있지 않습니다.");
//                                    log.info("cost : " + cost);
                                    excelList.add(cellData);
                                }
                            } catch (Exception e) {
//                                log.info("e : " + e);
//                                log.info("숫자가 아닌 문자열 입니다.");
                                return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE017.getCode(), ResponseErrorCode.NDE017.getDesc(), ResponseErrorCode.NDE018.getCode(), ResponseErrorCode.NDE018.getDesc()));
                            }
                        } else {
                            excelList.add(cellData);
                        }
                    } else {
                        return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE012.getCode(), ResponseErrorCode.NDE012.getDesc(), null, null));
                    }
                }

                log.info("==============결과==============");
                log.info(i-2+"번째 루트 리스트 : "+excelList);
                log.info(i-2+"번째 루트 리스트길이 : "+excelList.size());
                log.info("");
                if (excelList.size() == 25) {
                    log.info("노후화 대응 : 엑셀업로드 성능개선사업 내용을 저장합니다.");
                    performance.setPiInputSkip(weightMapperDto.getPiInputSkip());  // 스킵여부 0:적합, 1:부적합, 2: 스킵

                    performance.setPiFacilityType(excelList.get(0).toString());  // 시설유형(NOTNULL)
                    performance.setPiFacilityName(excelList.get(1).toString());  // 시설유형
                    if (excelList.get(2) == null) {
                        performance.setPiKind(null);
                    } else {
                        performance.setPiKind(excelList.get(2).toString()); // 종별구분(NULL)
                    }

                    performance.setPiCompletionYear(Double.parseDouble(excelList.get(3).toString())); // 준공연도(NOTNULL)
                    performance.setPiPublicYear(Double.parseDouble(excelList.get(4).toString())); // 공용연수(NOTNULL)
                    if (excelList.get(5) == null) {
                        performance.setPiType(null);
                    } else {
                        performance.setPiType(excelList.get(5).toString()); // 형식구분(NULL)
                    }

                    performance.setPiErectionCost(Long.parseLong(excelList.get(6).toString())); // 취득원가(NOTNULL)
                    performance.setPiSafetyLevel(excelList.get(7).toString()); // 안전등급(NOTNULL)
                    performance.setPiUsabilityAndGoalLevel(excelList.get(8).toString()); // 목표 안전등급(NOTNULL)

                    if (excelList.get(9).equals("1년 미만")) {
                        performance.setPiMaintenanceDelay(0.0); // 유지보수기간(NOTNULL)
                    }else if(excelList.get(9).equals("1년")){
                        performance.setPiMaintenanceDelay(1.0); // 유지보수기간(NOTNULL)
                    }else if(excelList.get(9).equals("2년")){
                        performance.setPiMaintenanceDelay(2.0); // 유지보수기간(NOTNULL)
                    }else if(excelList.get(9).equals("3년")){
                        performance.setPiMaintenanceDelay(3.0); // 유지보수기간(NOTNULL)
                    }else{
                        performance.setPiMaintenanceDelay(4.0); // 유지보수기간(NOTNULL)
                    }

                    if (excelList.get(10) == null) {
                        performance.setPiManagement(null);
                    } else {
                        performance.setPiManagement(excelList.get(10).toString()); // 관리주체(NULL)
                    }
                    if (excelList.get(11) == null) {
                        performance.setPiAgency(null);
                    } else {
                        performance.setPiAgency(excelList.get(11).toString()); // 관리감독기관(NULL)
                    }

                    performance.setPiAADT(Double.parseDouble(excelList.get(12).toString())); // 연평균일교통량(NOTNULL)

                    performance.setPiBusiness(weightMapperDto.getWeightCategory()); // 사업구분(NOTNULL)

                    performance.setPiBusinessType(excelList.get(13).toString()); // 사업유형(NOTNULL)

                    if (excelList.get(14) == null) {
                        performance.setPiTargetAbsence(null);
                    } else {
                        performance.setPiTargetAbsence(excelList.get(14).toString()); // 대상부재(NULL)
                    }
                    if (excelList.get(15) == null) {
                        performance.setPiBusinessClassification(null);
                    } else {
                        performance.setPiBusinessClassification(excelList.get(15).toString()); // 사업분류(NOTNULL)
                    }

                    performance.setPiBusinessExpenses(Long.parseLong(excelList.get(16).toString())); // 사업비용(NOTNULL)
                    performance.setPiBeforeSafetyRating(excelList.get(17).toString()); // 사업전 부재 안전등급(NOTNULL)
                    performance.setPiAfterSafetyRating(excelList.get(18).toString()); // 사업후 부재 안전등급(NOTNULL)

                    if (excelList.get(19).toString().startsWith("해당")) {
                        performance.setPiBusinessValidity("0"); // 사업추진 타당성(NOTNULL) - 해당사유 외
                    }else if(excelList.get(19).toString().startsWith("법에")){
                        performance.setPiBusinessValidity("1"); // 사업추진 타당성(NOTNULL) - 법에 따른 의무사업
                    }else if(excelList.get(19).toString().startsWith("법정")){
                        performance.setPiBusinessValidity("2"); // 사업추진 타당성(NOTNULL) - 법정계획/설계기준에 따른 의무사업
                    }else{
                        performance.setPiBusinessValidity("3"); // 사업추진 타당성(NOTNULL) - 자체계획/의결에 따른 사업
                    }

                    performance.setPiWhether(Double.parseDouble(excelList.get(20).toString().substring(0,1))); // 최근 1년간 민원 및 사고발생 건수(NOTNULL)


                    performance.setPiRaterBaseYear(Double.parseDouble(excelList.get(21).toString())); // 평가 기준년도(NOTNULL)
                    if (excelList.get(22) == null) {
                        performance.setPiRater(null);
                    } else {
                        performance.setPiRater(excelList.get(22).toString()); // 평가자이름(NULL)
                    }
                    if (excelList.get(23) == null) {
                        performance.setPiRaterBelong(null);
                    } else {
                        performance.setPiRaterBelong(excelList.get(23).toString()); // 평자가 소속(NULL)
                    }
                    if (excelList.get(24) == null) {
                        performance.setPiRaterPhone(null);
                    } else {
                        performance.setPiRaterPhone(excelList.get(24).toString()); // 평가자 연락처(NULL)
                    }

                    performance.setPiAutoNum(autoNum); // 대안 일려번호(NOTNULL)
                    performance.setPiInputCount(i - 2);  //대안카운트(NULL)
                    performance.setPiInputGreat(0);  //우수대안인지 0 or 1(NULL)
                    performance.setPiInputMiddleSave(1);  //작성완료된 글인지 0 or 1(NULL)
                    performance.setPiFileYn("Y"); // 파일업로드 여부
                    performance.setInsertDateTime(LocalDateTime.now()); // 작성날짜
                    performance.setInsert_id(currentuserid); // 작성자

                    ListPerformance.add(performance);
                }

                excelList.clear();

            }
            log.info("노후화 ListPerformance : " + ListPerformance);

            for (Performance performance : ListPerformance) {
                performanceService.save(performance);
            }
        }
        else if(piBusiness.equals("기준") && piBusiness.equals(businessData.substring(0,2))){
            log.info("기준변화 유형 엑셀파일입니다.");

            fileRealName = "기준변화_유형_양식_"+date+".xlsx";

            ArrayList<Object> excelList = new ArrayList<>();
            List<Performance> ListPerformance = new ArrayList<>();
            log.info("getPhysicalNumberOfRows : " + worksheet.getPhysicalNumberOfRows());
            log.info("");
            for (int i=3; i<7; i++){
                Performance performance = new Performance();
                for (int j = 3; j < worksheet.getPhysicalNumberOfRows(); j++) {
                    Row row = worksheet.getRow(j);
                    Cell cellData = row.getCell(i);
                    CellType ct = cellData.getCellType();
                    if (ct == CellType.BLANK) {
                        if(j==4 || j==5 || j==7 || j==8 || j==10 || j==13 || j==14 || j==17 || j==18 || j==19){
                            return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE029.getCode(), ResponseErrorCode.NDE029.getDesc(), ResponseErrorCode.NDE030.getCode(), i+"열의 "+j+"번째 행"+ResponseErrorCode.NDE030.getDesc()));
                        }else{
                            if(j == 3){
                                log.info("대안 종료");
                                excelList.add(null);
                                break;
                            }else{
//                                log.info("이곳은 널 입니다.");
                                excelList.add(null);
                            }
                        }
                    } else if(ct == CellType.NUMERIC || ct == CellType.STRING) {
                        if (j == 6 || j == 7 || j == 12 || j == 16 || j == 18) {
                            try {
                                String cost = cellData.toString();
                                if (cost.contains("E")) {
                                    BigDecimal costCheck = new BigDecimal(Double.parseDouble(cost));
                                    excelList.add(costCheck);
                                } else {
                                    excelList.add(cellData);
                                }
                            } catch (Exception e) {
                                return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE017.getCode(), ResponseErrorCode.NDE017.getDesc(), ResponseErrorCode.NDE018.getCode(), ResponseErrorCode.NDE018.getDesc()));
                            }
                        }else{
                            excelList.add(cellData);
                        }
                    }else{
                        return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE012.getCode(), ResponseErrorCode.NDE012.getDesc(), null, null));
                    }
                }

                log.info("==============결과==============");
                log.info(i-2+"번째 루트 리스트 : "+excelList);
                log.info(i-2+"번째 루트 리스트길이 : "+excelList.size());
                log.info("");
                if(excelList.size()==19) {
                    log.info("기준변화의 대한 엑셀파일 성능개선사업 내용을 저장합니다.");
                    performance.setPiInputSkip(weightMapperDto.getPiInputSkip());  // 스킵여부 0:적합, 1:부적합, 2: 스킵

                    performance.setPiFacilityType(excelList.get(0).toString());  // 시설유형(NOTNULL)
                    performance.setPiFacilityName(excelList.get(1).toString());  // 시설유형(NULL)
                    if(excelList.get(2)==null){
                        performance.setPiKind(null);
                    }else{
                        performance.setPiKind(excelList.get(2).toString()); // 종별구분(NOTNULL)
                    }
                    performance.setPiCompletionYear(Double.parseDouble(excelList.get(3).toString())); // 준공연도(NOTNULL)
                    performance.setPiPublicYear(Double.parseDouble(excelList.get(4).toString())); // 공용연수(NOTNULL)
                    if(excelList.get(5)==null){
                        performance.setPiType(null);
                    }else{
                        performance.setPiType(excelList.get(5).toString());  // 형식구분(NULL)
                    }
                    performance.setPiErectionCost(Long.parseLong("0")); // 취득원가(NOTNULL)
                    performance.setPiSafetyLevel(excelList.get(6).toString()); // 안전등급(NOTNULL)
                    performance.setPiUsabilityAndGoalLevel("해당안됨"); // 사용성등급(NOTNULL) , 목표안전등급
                    performance.setPiMaintenanceDelay(Double.parseDouble("9")); // 유지보수기간(NOTNULL)

                    if(excelList.get(7)==null){
                        performance.setPiManagement(null);
                    }else{
                        performance.setPiManagement(excelList.get(7).toString()); // 관리주체(NULL)
                    }

                    if(excelList.get(8)==null){
                        performance.setPiAgency(null);
                    }else{
                        performance.setPiAgency(excelList.get(8).toString()); // 관리감독기관(NULL)
                    }

                    performance.setPiAADT(Double.parseDouble(excelList.get(9).toString())); // 연평균일교통량(NOTNULL)

                    performance.setPiBusiness(weightMapperDto.getWeightCategory()); // 사업구분(NOTNULL)

                    performance.setPiBusinessType(excelList.get(10).toString()); // 사업유형(NOTNULL)
                    if(excelList.get(11)==null){
                        performance.setPiTargetAbsence(null);
                    }else{
                        performance.setPiTargetAbsence(excelList.get(11).toString()); // 대상부재(NULL)
                    }
                    if(excelList.get(12)==null){
                        performance.setPiBusinessClassification(null);
                    }else{
                        performance.setPiBusinessClassification(excelList.get(12).toString()); // 사업분류(NOTNULL)
                    }
                    performance.setPiBusinessExpenses(Long.parseLong(excelList.get(13).toString())); // 사업비용(NOTNULL)

                    performance.setPiBeforeSafetyRating("A"); // 사업전 부재 안전등급(NOTNULL)
                    performance.setPiAfterSafetyRating("A"); // 사업후 부재 안전등급(NOTNULL)

                    if (excelList.get(14).toString().startsWith("해당")) {
                        performance.setPiBusinessValidity("0"); // 사업추진 타당성(NOTNULL) - 해당사유 외
                    }else if(excelList.get(14).toString().startsWith("법에")){
                        performance.setPiBusinessValidity("1"); // 사업추진 타당성(NOTNULL) - 법에 따른 의무사업
                    }else if(excelList.get(14).toString().startsWith("법정")){
                        performance.setPiBusinessValidity("2"); // 사업추진 타당성(NOTNULL) - 법정계획/설계기준에 따른 의무사업
                    }else{
                        performance.setPiBusinessValidity("3"); // 사업추진 타당성(NOTNULL) - 자체계획/의결에 따른 사업
                    }

                    performance.setPiWhether(Double.parseDouble("99999")); // 최근 1년간 민원 및 사고발생 건수(NOTNULL)

                    performance.setPiRaterBaseYear(Double.parseDouble(excelList.get(15).toString())); // 평가 기준년도(NOTNULL)
                    if(excelList.get(16)==null){
                        performance.setPiRater(null);
                    }else{
                        performance.setPiRater(excelList.get(16).toString()); // 평가자이름(NULL)
                    }
                    if(excelList.get(17)==null){
                        performance.setPiRaterBelong(null);
                    }else{
                        performance.setPiRaterBelong(excelList.get(17).toString()); // 평자가 소속(NULL)
                    }
                    if(excelList.get(18)==null){
                        performance.setPiRaterPhone(null);
                    }else{
                        performance.setPiRaterPhone(excelList.get(18).toString()); // 평가자 연락처(NULL)
                    }

                    performance.setPiAutoNum(autoNum); // 대안 일려번호(NOTNULL)
                    performance.setPiInputCount(i - 2);  //대안카운트(NULL)
                    performance.setPiInputGreat(0);  //우수대안인지 0 or 1(NULL)
                    performance.setPiInputMiddleSave(1);  //작성완료된 글인지 0 or 1(NULL)
                    performance.setPiFileYn("Y"); // 파일업로드 여부
                    performance.setInsertDateTime(LocalDateTime.now()); // 작성날짜
                    performance.setInsert_id(currentuserid); // 작성자

                    ListPerformance.add(performance);
                }

                excelList.clear();
            }
            log.info("ListPerformance : " + ListPerformance);

            for (Performance performance : ListPerformance) {
                performanceService.save(performance);
            }

        }
        else if(piBusiness.equals("사용") && piBusiness.equals(businessData.substring(0,2))){
            log.info("사용성변화 유형 엑셀파일입니다.");

            fileRealName = "사용성변화_유형_양식_"+date+".xlsx";

            ArrayList<Object> excelList = new ArrayList<>();
            List<Performance> ListPerformance = new ArrayList<>();
            log.info("getPhysicalNumberOfRows : " + worksheet.getPhysicalNumberOfRows());
            log.info("");
            for (int i=3; i<7; i++){
                Performance performance = new Performance();
                for (int j = 3; j < worksheet.getPhysicalNumberOfRows(); j++) {
                    Row row = worksheet.getRow(j);
                    Cell cellData = row.getCell(i);
                    CellType ct = cellData.getCellType();
//                    log.info( j +" 셀타입 : "+ct);
                    if (ct == CellType.BLANK) {
                        if(j==4 || j==6 || j==7 || j==9 || j==10 || j==13 || j==14 ||  j==17 || j==18 || j==19 || j==20){
                            return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE029.getCode(), ResponseErrorCode.NDE029.getDesc(), ResponseErrorCode.NDE030.getCode(), i+"열의 "+j+"번째 행"+ResponseErrorCode.NDE030.getDesc()));
                        }else{
                            if(j == 3){
                                log.info("대안 종료");
                                excelList.add(null);
                                break;
                            }else{
//                                log.info("이곳은 널 입니다.");
                                excelList.add(null);
                            }
                        }
                    } else if(ct == CellType.NUMERIC || ct == CellType.STRING) {
                        if (j == 6 || j == 7 || j == 13 || j == 17 || j == 20) {
                            try {
                                String cost = cellData.toString();
                                if (cost.contains("E")) {
                                    BigDecimal costCheck = new BigDecimal(Double.parseDouble(cost));
                                    excelList.add(costCheck);
                                } else {
                                    excelList.add(cellData);
                                }
                            } catch (Exception e) {
                                return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE017.getCode(), ResponseErrorCode.NDE017.getDesc(), ResponseErrorCode.NDE018.getCode(), ResponseErrorCode.NDE018.getDesc()));
                            }
                        }else{
                            excelList.add(cellData);
                        }
                    }else{
                        return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE012.getCode(), ResponseErrorCode.NDE012.getDesc(), null, null));
                    }
                }
                log.info("==============결과==============");
                log.info(i-2+"번째 루트 리스트 : "+excelList);
                log.info(i-2+"번째 루트 리스트길이 : "+excelList.size());
                log.info("");
                if(excelList.size()==21) {
                    log.info("사용성변화의 대한 엑셀파일 성능개선사업 내용을 저장합니다.");
                    performance.setPiInputSkip(weightMapperDto.getPiInputSkip());  // 스킵여부 0:적합, 1:부적합, 2: 스킵

                    performance.setPiFacilityType(excelList.get(0).toString());  // 시설유형(NOTNULL)
                    performance.setPiFacilityName(excelList.get(1).toString());  // 시설유형(NULL)
                    if(excelList.get(2)==null){
                        performance.setPiKind(null);
                    }else{
                        performance.setPiKind(excelList.get(2).toString()); // 종별구분(NOTNULL)
                    }
                    performance.setPiCompletionYear(Double.parseDouble(excelList.get(3).toString())); // 준공연도(NOTNULL)
                    performance.setPiPublicYear(Double.parseDouble(excelList.get(4).toString())); // 공용연수(NOTNULL)
                    if(excelList.get(5)==null){
                        performance.setPiType(null);
                    }else{
                        performance.setPiType(excelList.get(5).toString()); // 형식구분(NULL)
                    }
                    performance.setPiErectionCost(Long.parseLong("0")); // 취득원가(NOTNULL)
                    performance.setPiSafetyLevel(excelList.get(6).toString()); // 안전등급(NOTNULL)
                    performance.setPiUsabilityAndGoalLevel(excelList.get(7).toString()); // 사용성등급(NOTNULL)
                    performance.setPiMaintenanceDelay(Double.parseDouble("9")); // 유지보수기간(NOTNULL)

                    if(excelList.get(8)==null){
                        performance.setPiManagement(null);
                    }else{
                        performance.setPiManagement(excelList.get(8).toString()); // 관리주체(NULL)
                    }
                    if(excelList.get(9)==null){
                        performance.setPiAgency(null);
                    }else{
                        performance.setPiAgency(excelList.get(9).toString()); // 관리감독기관(NULL)
                    }
                    performance.setPiAADT(Double.parseDouble(excelList.get(10).toString())); // 연평균일교통량(NOTNULL)
                    performance.setPiBusiness(weightMapperDto.getWeightCategory()); // 사업구분(NOTNULL)
                    performance.setPiBusinessType(excelList.get(11).toString()); // 사업유형(NOTNULL)
                    if(excelList.get(12)==null){
                        performance.setPiTargetAbsence(null);
                    }else{
                        performance.setPiTargetAbsence(excelList.get(12).toString()); // 대상부재(NULL)
                    }
                    if(excelList.get(13)==null){
                        performance.setPiBusinessClassification(null);
                    }else{
                        performance.setPiBusinessClassification(excelList.get(13).toString()); // 사업분류(NOTNULL)
                    }
                    performance.setPiBusinessExpenses(Long.parseLong(excelList.get(14).toString())); // 사업비용(NOTNULL)

                    performance.setPiBeforeSafetyRating("A"); // 사업전 부재 안전등급(NOTNULL)
                    performance.setPiAfterSafetyRating("A"); // 사업후 부재 안전등급(NOTNULL)

                    if (excelList.get(15).toString().startsWith("해당")) {
                        performance.setPiBusinessValidity("0"); // 사업추진 타당성(NOTNULL) - 해당사유 외
                    }else if(excelList.get(15).toString().startsWith("법에")){
                        performance.setPiBusinessValidity("1"); // 사업추진 타당성(NOTNULL) - 법에 따른 의무사업
                    }else if(excelList.get(15).toString().startsWith("법정")){
                        performance.setPiBusinessValidity("2"); // 사업추진 타당성(NOTNULL) - 법정계획/설계기준에 따른 의무사업
                    }else{
                        performance.setPiBusinessValidity("3"); // 사업추진 타당성(NOTNULL) - 자체계획/의결에 따른 사업
                    }

                    performance.setPiWhether(Double.parseDouble(excelList.get(16).toString().substring(0,1))); // 최근 1년간 민원 및 사고발생 건수(NOTNULL)

                    performance.setPiRaterBaseYear(Double.parseDouble(excelList.get(17).toString())); // 평가 기준년도(NOTNULL)
                    if(excelList.get(18)==null){
                        performance.setPiRater(null);
                    }else{
                        performance.setPiRater(excelList.get(18).toString()); // 평가자이름(NULL)
                    }
                    if(excelList.get(19)==null){
                        performance.setPiRaterBelong(null);
                    }else{
                        performance.setPiRaterBelong(excelList.get(19).toString()); // 평자가 소속(NULL)
                    }
                    if(excelList.get(20)==null){
                        performance.setPiRaterPhone(null);
                    }else{
                        performance.setPiRaterPhone(excelList.get(20).toString()); // 평가자 연락처(NULL)
                    }
                    performance.setPiAutoNum(autoNum); // 대안 일려번호(NOTNULL)
                    performance.setPiInputCount(i - 2);  //대안카운트(NULL)
                    performance.setPiInputGreat(0);  //우수대안인지 0 or 1(NULL)
                    performance.setPiInputMiddleSave(1);  //작성완료된 글인지 0 or 1(NULL)
                    performance.setPiFileYn("Y"); // 파일업로드 여부
                    performance.setInsertDateTime(LocalDateTime.now()); // 작성날짜
                    performance.setInsert_id(currentuserid); // 작성자

                    ListPerformance.add(performance);
                }
                excelList.clear();
            }
            log.info("ListPerformance : " + ListPerformance);

            for (Performance performance : ListPerformance) {
                performanceService.save(performance);
            }


        }
        else{
            return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE027.getCode(), ResponseErrorCode.NDE027.getDesc(), ResponseErrorCode.NDE028.getCode(), ResponseErrorCode.NDE028.getDesc()));
        }

        weight.setPiAutoNum(autoNum);
        weight.setInsert_id(currentuserid);
        weight.setInsertDateTime(LocalDateTime.now());
        weightService.save(weight);

        // 저장완료후 S3에 파일업로드 : 업데이트 무조건 삭제후 다시 등록
        Uploadfile uploadfile = new Uploadfile();
        uploadfile.setPiAutoNum(autoNum);
        uploadfile.setPiFileRealname(fileRealName);
        String ext = extension.substring(extension.lastIndexOf(".") + 1);
        String fileName = UUID.randomUUID().toString().replace("-", "")+"."+ext;
        uploadfile.setPiFileName(fileName);
        uploadfile.setPiFileYyyymmdd(date.format(new Date()));
        String filePath = "/performance-upload-excelfiles/" + date.format(new Date());
        log.info("filePath : "+AWSBUCKETURL+filePath+"/");
        log.info("excelfile : "+excelfile);
        log.info("fileName : "+fileName);
        log.info("fileRealName : "+fileRealName);
        uploadfile.setPiFilePath(AWSBUCKETURL+filePath+"/");
        uploadfile.setInsert_id(currentuserid);
        uploadfile.setInsertDateTime(LocalDateTime.now());
        performanceService.saveUploadFile(uploadfile);
        awss3Service.nomalFileUpload(excelfile, fileName, filePath);

        data.put("autoNum",autoNum);

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    // NEWDEAL 성능개선사업평가 Output 호출
    @PostMapping("/output")
    public ResponseEntity<Map<String,Object>> output(@RequestParam("autoNum")String autoNum, HttpServletRequest request) {

        log.info("Output 호출성공");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
        String insert_id = request.getHeader("insert_id");
        log.info("JWT_AccessToken : "+JWT_AccessToken);
        log.info("insert_id : "+insert_id);

        if (JWT_AccessToken==null) {
            return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE015.getCode(), ResponseErrorCode.NDE015.getDesc(), ResponseErrorCode.NDE016.getCode(), ResponseErrorCode.NDE016.getDesc()));
        }

        // 해당 아웃풋에 계산할 가중치 가져오기
        WeightDto weight = weightService.findByAutoNum(autoNum);

        // 해당 아웃풋에 가져올 대안 가져오기
        List<PerformanceDto> performance = performanceService.findByAutoNum(autoNum);

        log.info("=========================");
        log.info("일련번호 : " + autoNum);
        log.info("가중치 : " + weight);
        log.info("대안리스트 : " + performance);
        log.info("대안사이즈 : " + performance.size());
        log.info("타입 : " + performance.get(0).getPiFacilityType());
        log.info("파일업로드 여부 : " + performance.get(0).getPiFileYn());
        log.info("=========================");

        ReferenceTechnicality technicality = referenceService.techData("tech");
        ReferenceEconomy economy = referenceService.ecoData("eco");
        ReferencePolicy policy = referenceService.policyData("policy");
        ReferenceWeight weightSetting = referenceService.findByWeightSettingData("weight");

        log.info("");
        log.info("기술성 technicality : " + technicality);
        log.info("경제성 economy : " + economy);
        log.info("정책성 policy : " + policy);
        log.info("셋팅된 가중치 referenceWeight : " + weightSetting); // 여기서부터 -> 유형의 따라 불부합,부합, Yes,No 리스트데이터 send

        // 기술성 점수리스트, 등급리스트
        List<String> technicality_scroeList;
        List<String> technicality_rankList;

        // 경제성 점수리스트, 등급리스트
        List<String> economy_scroeList;
        List<String> economy_rankList;

        // 정책성 점수리스트, 등급리스트
        List<String> policy_scroeList;
        List<String> policy_rankList;

        Map<Integer,List<String>> technicality_scroeMap = new HashMap<>();
        Map<Integer,List<String>> technicality_rankMap = new HashMap<>();
        Map<Integer,List<String>> economy_scroeMap = new HashMap<>();
        Map<Integer,List<String>> economy_rankMap = new HashMap<>();
        Map<Integer,List<String>> policy_scroeMap = new HashMap<>();
        Map<Integer,List<String>> policy_rankMap = new HashMap<>();

        String piBusiness = performance.get(0).getPiBusiness();
        String type = performance.get(0).getPiFacilityType();

        List<List<String>> weightResultList = performanceFunctionService.weightResultList(type, piBusiness, weight, weightSetting); // 가중치 변경여부, 최소-최대 범위내 지정 리스트 호출
        data.put("weightResultList",weightResultList);

        String piBusinessType;
        String safeValue = null;
        String publicYearValue = null;

        for(int i=0; i<performance.size(); i++){
//        for(int i=0; i<1; i++){
            technicality_scroeList = new ArrayList<>();
            technicality_rankList = new ArrayList<>();

            economy_scroeList = new ArrayList<>();
            economy_rankList = new ArrayList<>();

            policy_scroeList = new ArrayList<>();
            policy_rankList = new ArrayList<>();

            piBusinessType = performance.get(i).getPiBusinessType();

            // 노후화 대응, 기준변화, 사용성변화 - 안정성 환산점수 10/29 완료
            Map<String,String> safetyLevel;
//            log.info("");
//            log.info("@@@@@ 시작 @@@@@");
//            log.info("");
            if(piBusinessType.startsWith("유지")) {
//                log.info("유지보수임");
                safetyLevel  = performanceFunctionService.safetyLevel(performance.get(i).getPiSafetyLevel(),technicality,safeValue);
                technicality_scroeList.add(safetyLevel.get("score"));
                technicality_rankList.add(safetyLevel.get("rank"));
            }else{
//                log.info("성능개선임");
                safetyLevel  = performanceFunctionService.safetyLevel(performance.get(i).getPiSafetyLevel(),technicality,null);
                safeValue = safetyLevel.get("score");
                technicality_scroeList.add(safetyLevel.get("score"));
                technicality_rankList.add(safetyLevel.get("rank"));
            }

            // 노후화 대응, 기준변화, 사용성변화 - 기술성 - 노후도 환산점수 11/04 완료
            Map<String,String> publicYear;
            if(piBusinessType.startsWith("유지")) {
                publicYear  = performanceFunctionService.publicYear(performance.get(i).getPiPublicYear(),technicality,publicYearValue);
                technicality_scroeList.add(publicYear.get("score"));
                technicality_rankList.add(publicYear.get("rank"));
            }else{
                publicYear  = performanceFunctionService.publicYear(performance.get(i).getPiPublicYear(),technicality,null);
                publicYearValue = publicYear.get("score");
                technicality_scroeList.add(publicYear.get("score"));
                technicality_rankList.add(publicYear.get("rank"));
            }

//            publicYear  = performanceFunctionService.publicYear(performance.get(i).getPiPublicYear(),technicality);
//            technicality_scroeList.add(publicYear.get("score"));
//            technicality_rankList.add(publicYear.get("rank"));

            if(piBusiness.equals("노후화대응")){

                // 노후화 대응 - 기술성 - 지체도 환산점수 11/04 완료
                Map<String,String> urgency  = performanceFunctionService.urgency(performance.get(i).getPiSafetyLevel(), performance.get(i).getPiMaintenanceDelay(), technicality);
                technicality_scroeList.add(urgency.get("score"));
                technicality_rankList.add(urgency.get("rank"));

                // 노후화 대응 - 기술성 - 목표성능 달성도 환산점수 11/04 완료
                Map<String,String> goal  = performanceFunctionService.goal(performance.get(i).getPiAfterSafetyRating(),performance.get(i).getPiUsabilityAndGoalLevel(), technicality);
                technicality_scroeList.add(goal.get("score"));
                technicality_rankList.add(goal.get("rank"));

            }else if(piBusiness.equals("사용성변화")){

                // 사용성변화 - 기술성 - 사용성 환산점수 11/04 완료
                if(type.equals("교량") || type.equals("터널")){
                    Map<String,String> usabilityLevel  = performanceFunctionService.usabilityLevel(performance.get(i).getPiUsabilityAndGoalLevel(), technicality);
                    technicality_scroeList.add(usabilityLevel.get("score"));
                    technicality_rankList.add(usabilityLevel.get("rank"));
                }else{
                    technicality_scroeList.add("0");
                    technicality_rankList.add("E");
                }

            }

            // 노후화 대응, 기준변화, 사용성변화 - 기술성 종합 환산점수,환산랭크 11/04 완료
            Map<String, String> technicalityAllScoreRank = performanceFunctionService.technicality_allScoreRank(type,technicality_scroeList,
                    weight.getPiWeightSafe(),weight.getPiWeightUsability(), weight.getPiWeightOld(), weight.getPiWeightUrgency(), weight.getPiWeightGoal(), piBusiness, technicality);
            technicality_scroeList.add(technicalityAllScoreRank.get("score"));
            technicality_rankList.add(technicalityAllScoreRank.get("rank"));

            if(piBusiness.equals("노후화대응")){

                // 노후화대응 - 경제성 - 자산가치 개선 효율성 - 22/04/13 - 검토 : 문제없음
                Map<String, String> assetValue = performanceFunctionService.assetValue(economy, performance.get(i).getPiFacilityType(), performance.get(i).getPiErectionCost(), performance.get(i).getPiBusinessExpenses() ,performance.get(i).getPiCompletionYear(), performance.get(i).getPiRaterBaseYear(), performance.get(i).getPiBeforeSafetyRating(), performance.get(i).getPiAfterSafetyRating());
                if(assetValue==null){
                    return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE022.getCode(), ResponseErrorCode.NDE022.getDesc(), ResponseErrorCode.NDE023.getCode(), ResponseErrorCode.NDE023.getDesc()));
                }
                economy_scroeList.add(assetValue.get("score"));
                economy_rankList.add(assetValue.get("rank"));

                // 노후화대응 - 경제성 - 안전효용 개선 효율성 - 22/04/13 - 검토 : 문제없음
                Map<String, String> safetyUtility = performanceFunctionService.safetyUtility(economy, performance.get(i).getPiAfterSafetyRating(),performance.get(i).getPiBeforeSafetyRating(),performance.get(i).getPiAADT(),performance.get(i).getPiBusinessExpenses());
                economy_scroeList.add(safetyUtility.get("score"));
                economy_rankList.add(safetyUtility.get("rank"));

            }else{

                Double cost = 5000000000.0; // 단위비용
                // 기준변화, 사용성변화 - 경제성 - 사업규모 등급
                Map<String, String> businessScale = performanceFunctionService.businessScale(economy, performance.get(i).getPiBusinessExpenses(), cost);
                economy_scroeList.add(businessScale.get("score"));
                economy_rankList.add(businessScale.get("rank"));

                // 기준변화, 사용성변화 - 경제성 - 사업효율 등급
                Map<String, String> businessEfficiency = performanceFunctionService.businessEfficiency(economy, performance.get(i).getPiFacilityType(), performance.get(i).getPiBusinessExpenses(), performance.get(i).getPiAADT());
                economy_scroeList.add(businessEfficiency.get("score"));
                economy_rankList.add(businessEfficiency.get("rank"));

            }


            // 노후화대응, 기준변화, 사용성변화 - 경제성 종합점수 및 등급 - 22/04/13 - 검토 : 문제없음
            Map<String, String> economyAllScoreRank = performanceFunctionService.economy_allScoreRank(economy, economy_scroeList,weight.getPiWeightSafeUtility(), weight.getPiWeightCostUtility());
            economy_scroeList.add(economyAllScoreRank.get("score"));
            economy_rankList.add(economyAllScoreRank.get("rank"));




            // 정책성 - 사업추진 타당성
            Map<String, String> businessFeasibility = performanceFunctionService.BusinessFeasibility(policy, performance.get(i).getPiBusinessValidity());
            policy_scroeList.add(businessFeasibility.get("score"));
            policy_rankList.add(businessFeasibility.get("rank"));

            // 정책성 - 사업효과 범용성
            Map<String, String> businessEffect = performanceFunctionService.businessEffect(policy, performance.get(i).getPiAADT());
            policy_scroeList.add(businessEffect.get("score"));
            policy_rankList.add(businessEffect.get("rank"));

            if(piBusiness.equals("노후화대응") || piBusiness.equals("사용성변화")){
                // 정책성 - 민원 및 사고 대응성
                Map<String, String> complaintResponsiveness = performanceFunctionService.ComplaintResponsiveness(policy, performance.get(i).getPiWhether());
                policy_scroeList.add(complaintResponsiveness.get("score"));
                policy_rankList.add(complaintResponsiveness.get("rank"));
            }

            // 정책성 - 종합점수 및 등급
            Map<String, String> policyAllScoreRank = performanceFunctionService.policy_allScoreRank(policy, piBusiness, policy_scroeList, weight.getPiWeightBusiness(), weight.getPiWeightComplaint(), weight.getPiWeightBusinessEffect());
            policy_scroeList.add(policyAllScoreRank.get("score"));
            policy_rankList.add(policyAllScoreRank.get("rank"));

//            log.info("기술성 technicality_scroeList : " + technicality_scroeList);
//            log.info("기술성 technicality_rankList : " + technicality_rankList);
//
//            log.info("경제성 economy_scroeList : " + economy_scroeList);
//            log.info("경제성 economy_rankList : " + economy_rankList);
//
//            log.info("정책성 policy_scroeList : " + policy_scroeList);
//            log.info("정책성 policy_rankList : " + policy_rankList);

            technicality_scroeMap.put(i,technicality_scroeList);
            technicality_rankMap.put(i,technicality_rankList);

            economy_scroeMap.put(i,economy_scroeList);
            economy_rankMap.put(i,economy_rankList);

            policy_scroeMap.put(i,policy_scroeList);
            policy_rankMap.put(i,policy_rankList);
        }

//        log.info("");
//        log.info("기술성 환산점수 리스트 : " + technicality_scroeMap);
//        log.info("기술성 환산등급 리스트 : " + technicality_rankMap);
//
//        log.info("경제성 환산점수 리스트 : " + economy_scroeMap);
//        log.info("경제성 환산등급 리스트 : " + economy_rankMap);
//
//        log.info("정책성 환산점수 리스트 : " + policy_scroeMap);
//        log.info("정책성 환산등급 리스트 : " + policy_rankMap);
//        log.info("");

        data.put("piBusiness",piBusiness);
        data.put("typeName",performance.get(0).getPiFacilityType());
        data.put("piFileYn",performance.get(0).getPiFileYn());

        // 가중치, 대안리스트, 대안갯수
        data.put("weightList",weight);
        data.put("performanceList",performance);
        data.put("performanceSize",performance.size());

        // 노후화_기술성
        data.put("technicalityScore",technicality_scroeMap);
        data.put("technicalityRank",technicality_rankMap);

        // 노후화_경제
        data.put("economyScore",economy_scroeMap);
        data.put("economyRank",economy_rankMap);

        // 노후화_정책성
        data.put("policyScore",policy_scroeMap);
        data.put("policyRank",policy_rankMap);



        // 종합평가표 점수리스트, 등급리스트
        List<String> scoreList;
        List<Double> all_scroeList;
        List<Double> great_scroeList = new ArrayList<>();
        List<String> all_rankList;
        List<String> all_businessList;
        List<String> all_greate = new ArrayList<>();
        Map<Integer,List<Double>> all_scroeMap = new HashMap<>();
        Map<Integer,List<String>> all_rankMap = new HashMap<>();
        Map<Integer,List<String>> all_businessMap = new HashMap<>();
        List<Long> piBusinessExpensesList = new ArrayList<>();
        List<Integer> maxCostVal = new ArrayList<>();;
        int size;
        for(int i=0; i<performance.size(); i++) {
//        for(int i=0; i<1; i++){
            scoreList = new ArrayList<>();
            all_scroeList = new ArrayList<>();
            all_rankList = new ArrayList<>();
            all_businessList = new ArrayList<>();

            // 사업비용 리스트
            piBusinessExpensesList.add(performance.get(i).getPiBusinessExpenses());

            size = technicality_scroeMap.get(i).size();
            scoreList.add(technicality_scroeMap.get(i).get(size-1));
            size = economy_scroeMap.get(i).size();
            scoreList.add(economy_scroeMap.get(i).get(size-1));
            size = policy_scroeMap.get(i).size();
            scoreList.add(policy_scroeMap.get(i).get(size-1));
//            log.info(i+1+"번째 scoreList : "+scoreList);

            // 유형별/지표별 가중치는 바뀔수있음.
            Map<String, Object> all_ScoreRank = performanceFunctionService.all_ScoreRank(technicality, scoreList, weight.getPiWeightTechnicality(),weight.getPiWeightEconomy(),weight.getPiWeightPolicy(), weight.getPiWeightCriticalScore());
            all_scroeList.add(Double.parseDouble(String.valueOf(all_ScoreRank.get("score"))));
            great_scroeList.add(Double.parseDouble(String.valueOf(all_ScoreRank.get("score"))));
            all_rankList.add(String.valueOf(all_ScoreRank.get("rank")));
            all_businessList.add(String.valueOf(all_ScoreRank.get("business")));

            all_scroeMap.put(i,all_scroeList);
            all_rankMap.put(i,all_rankList);
            all_businessMap.put(i,all_businessList);

//            log.info("great_scroeList : "+great_scroeList);
            if(i+1==performance.size()){
                Double maxVal = Collections.max(great_scroeList);
                Long minCost = Collections.min(piBusinessExpensesList);

                int x= 0;
                for (int j=0; j<great_scroeList.size(); j++) {
                    if (great_scroeList.get(j).equals(maxVal)) {
                        maxCostVal.add(j+1);
                        x++;
                    } else {
                        maxCostVal.add(0);
                    }
                }

                for(int z=0; z<maxCostVal.size(); z++){
                    if(!maxCostVal.get(z).equals(0)){
                        Optional<Performance> optionalPerformance = performanceService.findByPiAutoNumAndInsert_idAndPiInputCount(autoNum,maxCostVal.get(z));
                        if(optionalPerformance.isPresent()) {
                            if (2 <= x) {
                                if (piBusinessExpensesList.get(z).equals(minCost)) {
//                                    log.info("우수대안 업데이트하기 카운트 : "+maxCostVal.get(z));
                                    optionalPerformance.get().setPiInputGreat(1);
                                    optionalPerformance.get().setModify_id(insert_id);
                                    optionalPerformance.get().setModifyDateTime(LocalDateTime.now());
                                    performanceService.save(optionalPerformance.get());

                                    all_greate.add("우수 대안");
                                }else{
                                    optionalPerformance.get().setPiInputGreat(0);
                                    optionalPerformance.get().setModify_id(insert_id);
                                    optionalPerformance.get().setModifyDateTime(LocalDateTime.now());
                                    performanceService.save(optionalPerformance.get());

                                    all_greate.add("-");
                                }
                            }else{
//                                log.info("우수대안 업데이트하기 카운트 : "+maxCostVal.get(z));
                                optionalPerformance.get().setPiInputGreat(1);
                                optionalPerformance.get().setModify_id(insert_id);
                                optionalPerformance.get().setModifyDateTime(LocalDateTime.now());
                                performanceService.save(optionalPerformance.get());

                                all_greate.add("우수 대안");
                            }
                        }
                    }else{
                        Optional<Performance> optionalPerformance = performanceService.findByPiAutoNumAndInsert_idAndPiInputCount(autoNum,z+1);
                        if(optionalPerformance.isPresent()){
                            optionalPerformance.get().setPiInputGreat(0);
                            optionalPerformance.get().setModify_id(insert_id);
                            optionalPerformance.get().setModifyDateTime(LocalDateTime.now());
                            performanceService.save(optionalPerformance.get());

                            all_greate.add("-");
                        }
                    }
                }

            }
        }

//        log.info("종합평가표 환산점수 리스트 : " + all_scroeMap);
//        log.info("종합평가표 환산등급 리스트 : " + all_rankMap);
//        log.info("종합평가표 사업성 : " + all_businessMap);
//        log.info("종합평가표 우수대안 : " + all_greate);

        data.put("allScroeMap",all_scroeMap);
        data.put("allRankMap",all_rankMap);
        data.put("allBusinessMap",all_businessMap);
        data.put("allGreate",all_greate);

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    // NEWDEAL 성능개선사업평가 조회페이지 (우수대안의 대한 리스트만 출력함)
    @GetMapping("/list")
    public ResponseEntity<Map<String,Object>> list(@RequestParam("piFacilityType")String piFacilityType, @RequestParam("piKind")String piKind,@RequestParam("piFacilityName")String piFacilityName, Pageable pageable,HttpServletRequest request) {

        log.info("performanceList 호출성공");

        AjaxResponse res = new AjaxResponse();

        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
        String insert_id = request.getHeader("insert_id");
        log.info("JWT_AccessToken : "+JWT_AccessToken);

        // 검색조건
        log.info("insert_id : "+insert_id);
//        log.info("piFacilityType : "+piFacilityType);
//        log.info("piKind : "+piKind);
//        log.info("piFacilityName : "+piFacilityName);
        Page<PerformanceListDto> performanceListDtoPage = performanceService.findByPerformanceList(piFacilityType, piKind,piFacilityName, insert_id, pageable);
        return ResponseEntity.ok(res.ResponseEntityPage(performanceListDtoPage));
    }

    // NEWDEAL 성능개선사업평가 Input 아니오를 누르면 중간저장된 게시물을 삭제 할 함수
    @PostMapping("/del")
    public ResponseEntity<Map<String,Object>> del(@RequestParam("autoNum")String autoNum,HttpServletRequest request) {

        log.info("del 호출성공");

        AjaxResponse res = new AjaxResponse();

        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
        String insert_id = request.getHeader("insert_id");
        log.info("JWT_AccessToken : "+JWT_AccessToken);
        log.info("insert_id : "+insert_id);
        log.info("삭제 할 일련번호 : "+autoNum);

        List<Performance> optionalPerformance = performanceService.findByPiAutoNumAndInsert_idDel(autoNum,insert_id,1);
        log.info("삭제 optionalPerformance : "+optionalPerformance);
        for (Performance performance : optionalPerformance) {
            performanceService.delete(performance);
        }

        Optional<Weight> optionalWeight = weightService.findByAutoNumAndInsertId(autoNum,insert_id);
        log.info("삭제 optionalWeight : "+optionalWeight);
        optionalWeight.ifPresent(weightService::delete);

        return ResponseEntity.ok(res.success());
    }

    // NEWDEAL 성능개선사업평가 가중치 가져오기(웹방식용)
    @PostMapping("/weightGet")
    public ResponseEntity<Map<String,Object>> weightGet(@RequestParam("autoNum")String autoNum, @RequestParam("businessNum")String businessNum,
                                                        @RequestParam("uploadYn")String uploadYn, HttpServletRequest request) {

        log.info("weightGet 호출성공");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
        String insert_id = request.getHeader("insert_id");
        log.info("JWT_AccessToken : "+JWT_AccessToken);
        log.info("insert_id : "+insert_id);

        log.info("autoNum : "+autoNum);
        log.info("businessNum : "+businessNum);

        WeightDto weightDto = weightService.findByAutoNum(autoNum);
        if(weightDto != null){
            data.put("weightDto", weightDto);
        }else{
            data.put("weightDto", null);
        }

        if(uploadYn.equals("N")) {
            if (businessNum.equals("노후화대응")) {
                log.info("노후화대응 가중치");
                ReferenceWeightOldDto referenceWeightOldDto = referenceService.findByReferenceWeightOld();
                data.put("weightSettingDto", referenceWeightOldDto);
            }else if (businessNum.equals("기준변화")){
                log.info("기준변화 가중치");
                ReferenceWeightBaseDto referenceWeightBaseDto = referenceService.findByReferenceWeightBase();
                data.put("weightSettingDto", referenceWeightBaseDto);
            }else{
                log.info("사용성변화 가중치");
                ReferenceWeightUseDto referenceWeightUseDto = referenceService.findByReferenceWeightUse();
                data.put("weightSettingDto", referenceWeightUseDto);
            }
        }

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    // NEWDEAL 성능개선사업평가 가중치 가져오기(업로드용)
    @PostMapping("/weightUploadGet")
    public ResponseEntity<Map<String,Object>> weightUploadGet(@RequestParam("businessNum")String businessNum, HttpServletRequest request) {

        log.info("weightUploadGet 호출성공");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
        String insert_id = request.getHeader("insert_id");
        log.info("JWT_AccessToken : "+JWT_AccessToken);
        log.info("insert_id : "+insert_id);

        if (businessNum.equals("노후화대응")) {
            log.info("노후화대응 가중치");
            ReferenceWeightOldDto referenceWeightOldDto = referenceService.findByReferenceWeightOld();
            data.put("weightSettingOldDto", referenceWeightOldDto);
        }else if (businessNum.equals("기준변화")){
            log.info("기준변화 가중치");
            ReferenceWeightBaseDto referenceWeightBaseDto = referenceService.findByReferenceWeightBase();
            data.put("weightSettingBaseDto", referenceWeightBaseDto);
        }else{
            log.info("사용성변화 가중치");
            ReferenceWeightUseDto referenceWeightUseDto = referenceService.findByReferenceWeightUse();
            data.put("weightSettingUseDto", referenceWeightUseDto);
        }

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

}
