package com.broadwave.backend.performance;

import com.broadwave.backend.common.AjaxResponse;
import com.broadwave.backend.common.ResponseErrorCode;
import com.broadwave.backend.keygenerate.KeyGenerateService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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

    private final ModelMapper modelMapper;
    private final PerformanceService performanceService;
    private final WeightService weightService;
    private final PerformanceFunctionService performanceFunctionService;
    private final KeyGenerateService keyGenerateService;

    @Autowired
    public PerformanceRestController(ModelMapper modelMapper,
                                     PerformanceService performanceService,
                                     WeightService weightService,
                                     PerformanceFunctionService performanceFunctionService,
                                     KeyGenerateService keyGenerateService) {
        this.modelMapper = modelMapper;
        this.performanceService = performanceService;
        this.weightService = weightService;
        this.performanceFunctionService = performanceFunctionService;
        this.keyGenerateService = keyGenerateService;
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
        log.info("performance : "+performance);

        if(performance==null){
            data.put("middleSave",0);
        }else{
            data.put("middleSave",1);
            data.put("piAutoNum",performance.getPiAutoNum());
        }
        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    // NEWDEAL 성능개선사업평가 Input 중간저장게시물의 데이터 불러오기
    @PostMapping("/middleData")
    public ResponseEntity<Map<String,Object>> middleData(@RequestParam("autoNum")String autoNum,HttpServletRequest request) {

        log.info("middleData 호출성공");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
        String insert_id = request.getHeader("insert_id");
        log.info("JWT_AccessToken : "+JWT_AccessToken);
        log.info("insert_id : "+insert_id);

        PerformanceMiddleDataDto performance = performanceService.findByInsertIAndAutoNum(insert_id,autoNum);
        log.info("performance : "+performance);

        if(performance!=null){
            data.put("performanceData",performance);
        }else{
            return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE019.getCode(), ResponseErrorCode.NDE019.getDesc(), ResponseErrorCode.NDE020.getCode(), ResponseErrorCode.NDE020.getDesc()));
        }
        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    // NEWDEAL 성능개선사업평가 Input 아니오를 누르면 중간저장된 게시물을 삭제 할 함수
    @PostMapping("/middleDataDel")
    public ResponseEntity<Map<String,Object>> middleDataDel(@RequestParam("autoNum")String autoNum,HttpServletRequest request) {

        log.info("middleDataDel 호출성공");

        AjaxResponse res = new AjaxResponse();

        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
        String insert_id = request.getHeader("insert_id");
        log.info("JWT_AccessToken : "+JWT_AccessToken);
        log.info("insert_id : "+insert_id);

        Optional<Performance> optionalPerformance = performanceService.findByPiAutoNumAndInsert_id(autoNum,insert_id);
        log.info("optionalPerformance : "+optionalPerformance);

        if (optionalPerformance.isEmpty()){
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E003.getCode(), ResponseErrorCode.E003.getDesc(),null,null));
        }

        performanceService.delete(optionalPerformance.get());

        return ResponseEntity.ok(res.success());
    }

    // NEWDEAL 성능개선사업평가 Performance1 중간저장 세이브
    @PostMapping("/middleSaveUpdate/{autoNum}")
    public ResponseEntity<Map<String,Object>> middleSaveUpdate(@ModelAttribute PerformanceMiddleSaveDto performanceMiddleSaveDto,@PathVariable String autoNum, HttpServletRequest request) {

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
        }

        if(autoNum.equals("null")){
            log.info("일련번호 생성");
            Date now = new Date();
            SimpleDateFormat yyMM = new SimpleDateFormat("yyMM");
            String newAutoNum = keyGenerateService.keyGenerate("nd_pi_input", yyMM.format(now), currentuserid);

            performance.setPiAutoNum(newAutoNum);
            performance.setPiInputMiddleSave(0);
            performance.setInsert_id(currentuserid);
            performance.setInsertDateTime(LocalDateTime.now());
            data.put("autoNum",newAutoNum);
        }else{
            Optional<Performance> optionalPerformance = performanceService.findByPiAutoNumAndInsert_id(autoNum,currentuserid);
            if(optionalPerformance.isPresent()){
                performance.setId(optionalPerformance.get().getId());
                performance.setPiAutoNum(autoNum);
                performance.setPiInputMiddleSave(0);
                performance.setInsert_id(currentuserid);
                performance.setInsertDateTime(LocalDateTime.now());
                data.put("autoNum", autoNum);
            }else {
                return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE019.getCode(), ResponseErrorCode.NDE019.getDesc(),ResponseErrorCode.NDE020.getCode(), ResponseErrorCode.NDE020.getDesc()));
            }
        }

        log.info("중간저장 performance : " + performance);

        //중간저장하기
        performanceService.save(performance);
        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    // NEWDEAL 성능개선사업평가 엑셀 업로드
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
        // 제공한 양식 엑셀파일이 맞는지 확인 (첫번째시트)
        try {
            Row rowCheck = worksheet.getRow(2);
            Object cellDataCheck = rowCheck.getCell(2);
            Integer rowSize = worksheet.getPhysicalNumberOfRows();
            log.info("cellDataCheck : " + cellDataCheck.toString());
            log.info("");
            if (!cellDataCheck.toString().equals("입력정보")) {
                return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE012.getCode(), ResponseErrorCode.NDE012.getDesc(), null, null));
            }else if(!rowSize.equals(31)){
                return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE012.getCode(), ResponseErrorCode.NDE012.getDesc(), null, null));
            }
        } catch (NullPointerException e) {
            return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE012.getCode(), ResponseErrorCode.NDE012.getDesc(), null, null));
        }

        // 일련번호 카운트 생성
        log.info("일련번호 생성");
        Date now = new Date();
        SimpleDateFormat yyMM = new SimpleDateFormat("yyMM");
        String autoNum = keyGenerateService.keyGenerate("nd_pi_input", yyMM.format(now), currentuserid);

        // 가중치 셋팅
        Weight weight = modelMapper.map(weightMapperDto, Weight.class);
        log.info("가중치 : "+weight);

        ArrayList<Object> excelList = new ArrayList<>();
        List<Performance> ListPerformance = new ArrayList<>();
        log.info("getPhysicalNumberOfRows : " + worksheet.getPhysicalNumberOfRows());
        log.info("");
        for (int i=3; i<7; i++){
            Performance performance = new Performance();
            for (int j = 3; j < worksheet.getPhysicalNumberOfRows()+1; j++) {
                Row row = worksheet.getRow(j);
                Cell cellData = row.getCell(i);
                CellType ct = cellData.getCellType();
                log.info("셀타입 : "+ct);
                if (ct == CellType.BLANK) {
                    log.info("이곳은 널 입니다.");
                    break;
                } else if(ct == CellType.NUMERIC || ct == CellType.STRING) {
                    try {
                        if (j == 21 || j == 9) {
                            try {
                                String cost = cellData.toString();
                                if (cost.contains("E")) {
                                    log.info("문자가 E가 포함되어 있습니다.");
                                    log.info("cost : " + cost);
                                    BigDecimal costCheck = new BigDecimal(Double.parseDouble(cost));
                                    log.info("숫자로 변환 : " + costCheck);
                                    excelList.add(costCheck);
                                } else {
                                    log.info("문자 E가 포함되어 있지 않습니다.");
                                    log.info("cost : " + cost);
                                    excelList.add(cellData);
                                }
                            } catch (Exception e) {
                                log.info("e : " + e);
                                log.info("숫자가 아닌 문자열 입니다.");
                                return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE017.getCode(), ResponseErrorCode.NDE017.getDesc(), ResponseErrorCode.NDE018.getCode(), ResponseErrorCode.NDE018.getDesc()));
                            }
                        } else {
                            excelList.add(cellData);
                        }
                    } catch (Exception e) {
                        log.info("e : " + e);
                        return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE012.getCode(), ResponseErrorCode.NDE012.getDesc(), null, null));
                    }
                }else{
                    return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE012.getCode(), ResponseErrorCode.NDE012.getDesc(), null, null));
                }
            }

            log.info("==============결과==============");
            log.info(i-2+"번째 루트 리스트 : "+excelList);
            log.info(i-2+"번째 루트 리스트길이 : "+excelList.size());
            log.info("");
            if(excelList.size()==29) {
                log.info("엑셀의 성능개선사업 내용을 저장합니다.");

                performance.setPiFacilityType(excelList.get(0).toString());  // 시설유형(NOTNULL)
                performance.setPiFacilityName(excelList.get(1).toString());  // 시설유형(NULL)
                performance.setPiKind(excelList.get(2).toString()); // 종별구분(NOTNULL)
                performance.setPiCompletionYear(Double.parseDouble(excelList.get(3).toString())); // 준공연도(NOTNULL)
                performance.setPiPublicYear(Double.parseDouble(excelList.get(4).toString())); // 공용연수(NOTNULL)
                performance.setPiType(excelList.get(5).toString()); // 형식구분(NULL)
                performance.setPiErectionCost(Long.parseLong(excelList.get(6).toString())); // 취득원가(NOTNULL)
                performance.setPiSafetyLevel(excelList.get(7).toString()); // 안전등급(NOTNULL)
                performance.setPiGoalLevel(excelList.get(8).toString()); // 목표등급(NOTNULL)
                performance.setPiMaintenanceDelay(Double.parseDouble(excelList.get(9).toString())); // 유지보수기간(NOTNULL)
                performance.setPiManagement(excelList.get(10).toString()); // 관리주체(NULL)
                performance.setPiAgency(excelList.get(11).toString()); // 관리감독기관(NULL)

                performance.setPiAADT(Double.parseDouble(excelList.get(12).toString())); // 연평균일교통량(NOTNULL)

                performance.setPiBusiness(excelList.get(13).toString()); // 사업구분(NOTNULL)
                performance.setPiBusinessType(excelList.get(14).toString()); // 사업유형(NOTNULL)
                performance.setPiTargetAbsence(excelList.get(15).toString()); // 대상부재(NULL)
                performance.setPiBusinessClassification(excelList.get(16).toString()); // 사업분류(NOTNULL)
                performance.setPiBusinessInformation(excelList.get(17).toString()); // 사업내용(NULL)
                performance.setPiBusinessExpenses(Long.parseLong(excelList.get(18).toString())); // 사업비용(NOTNULL)
                performance.setPiBeforeSafetyRating(excelList.get(19).toString()); // 사업전 부재 안전등급(NOTNULL)
                performance.setPiAfterSafetyRating(excelList.get(20).toString()); // 사업후 부재 안전등급(NOTNULL)

                performance.setPiBusinessObligatory(Double.parseDouble(excelList.get(21).toString()));// 법에 따른 의무사업(NOTNULL)
                performance.setPiBusinessMandatory(Double.parseDouble(excelList.get(22).toString())); // 법정계획에 따른 의무사업(NOTNULL)
                performance.setPiBusinessPlanned(Double.parseDouble(excelList.get(23).toString())); // 자체계획/의결에 따른 사업(NOTNULL)
                performance.setPiWhether(Double.parseDouble(excelList.get(24).toString())); // 최근 1년간 민원 및 사고발생 건수(NOTNULL)

                performance.setPiRaterBaseYear(Double.parseDouble(excelList.get(25).toString())); // 평가 기준년도(NOTNULL)
                performance.setPiRater(excelList.get(26).toString()); // 평가자이름(NULL)
                performance.setPiRaterBelong(excelList.get(27).toString()); // 평자가 소속(NULL)
                performance.setPiRaterPhone(excelList.get(28).toString()); // 평가자 연락처(NULL)

                performance.setPiAutoNum(autoNum); // 대안 일려번호(NOTNULL)
                performance.setPiInputCount(i - 2);  //대안카운트(NULL)
                performance.setPiInputGreat(0);  //우수대안인지 0 or 1(NULL)
                performance.setPiInputMiddleSave(1);  //작성완료된 글인지 0 or 1(NULL)

                performance.setInsertDateTime(LocalDateTime.now()); // 작성날짜
                performance.setInsert_id(currentuserid); // 작성자

                excelList.clear();
                ListPerformance.add(performance);
            }
        }
        log.info("ListPerformance : " + ListPerformance);

        for (Performance performance : ListPerformance) {
            performanceService.save(performance);
        }


        weight.setPiAutoNum(autoNum);
        weight.setInsert_id(currentuserid);
        weight.setInsertDateTime(LocalDateTime.now());
        weightService.save(weight);

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
//        log.info("JWT_AccessToken : "+JWT_AccessToken);
//        log.info("insert_id : "+insert_id);

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
        log.info("=========================");

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

        for(int i=0; i<performance.size(); i++){

            technicality_scroeList = new ArrayList<>();
            technicality_rankList = new ArrayList<>();

            economy_scroeList = new ArrayList<>();
            economy_rankList = new ArrayList<>();

            policy_scroeList = new ArrayList<>();
            policy_rankList = new ArrayList<>();

            // 기술성 - 안전성
            Map<String,String> safetyLevel  = performanceFunctionService.safetyLevel(performance.get(i).getPiSafetyLevel());
            technicality_scroeList.add(safetyLevel.get("score"));
            technicality_rankList.add(safetyLevel.get("rank"));
            // 기술성 - 노후도
            Map<String,String> publicYear  = performanceFunctionService.publicYear(performance.get(i).getPiPublicYear());
            technicality_scroeList.add(publicYear.get("score"));
            technicality_rankList.add(publicYear.get("rank"));
            // 기술성 - 시급성
            Map<String,String> urgency  = performanceFunctionService.urgency(performance.get(i).getPiSafetyLevel(),performance.get(i).getPiMaintenanceDelay());
            technicality_scroeList.add(urgency.get("score"));
            technicality_rankList.add(urgency.get("rank"));
            // 기술성 - 목표달성도
            Map<String,String> goal  = performanceFunctionService.goal(performance.get(i).getPiAfterSafetyRating(),performance.get(i).getPiGoalLevel());
            technicality_scroeList.add(goal.get("score"));
            technicality_rankList.add(goal.get("rank"));
            // 기술성 - 종합점수 및 등급
            Map<String, String> technicalityAllScoreRank = performanceFunctionService.technicality_allScoreRank(technicality_scroeList, weight.getPiWeightSafe(), weight.getPiWeightOld(), weight.getPiWeightUrgency(), weight.getPiWeightGoal());
            technicality_scroeList.add(technicalityAllScoreRank.get("score"));
            technicality_rankList.add(technicalityAllScoreRank.get("rank"));

            // 경제성 - 자산가치 개선 효율성
            Map<String, String> assetValue = performanceFunctionService.assetValue(performance.get(i).getPiFacilityType(), performance.get(i).getPiErectionCost(), performance.get(i).getPiBusinessExpenses() ,performance.get(i).getPiCompletionYear(), performance.get(i).getPiRaterBaseYear(), performance.get(i).getPiBeforeSafetyRating(), performance.get(i).getPiAfterSafetyRating());
            if(assetValue==null){
                return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE022.getCode(), ResponseErrorCode.NDE022.getDesc(), ResponseErrorCode.NDE023.getCode(), ResponseErrorCode.NDE023.getDesc()));
            }
            economy_scroeList.add(assetValue.get("score"));
            economy_rankList.add(assetValue.get("rank"));
            // 경제성 - 안전효용 개선 효율성
            Map<String, String> safetyUtility = performanceFunctionService.safetyUtility(performance.get(i).getPiAfterSafetyRating(),performance.get(i).getPiBeforeSafetyRating(),performance.get(i).getPiAADT(),performance.get(i).getPiBusinessExpenses());
            economy_scroeList.add(safetyUtility.get("score"));
            economy_rankList.add(safetyUtility.get("rank"));
            // 경제성 - 종합점수 및 등급
            Map<String, String> economyAllScoreRank = performanceFunctionService.economy_allScoreRank(economy_scroeList,weight.getPiWeightSafeUtility(), weight.getPiWeightCostUtility());
            economy_scroeList.add(economyAllScoreRank.get("score"));
            economy_rankList.add(economyAllScoreRank.get("rank"));

            // 정책성 - 사업추진 타당성
            Map<String, String> businessFeasibility = performanceFunctionService.BusinessFeasibility(performance.get(i).getPiBusinessObligatory(), performance.get(i).getPiBusinessMandatory(), performance.get(i).getPiBusinessPlanned());
            policy_scroeList.add(businessFeasibility.get("score"));
            policy_rankList.add(businessFeasibility.get("rank"));
            // 정책성 - 민원 및 사고 대응성
            Map<String, String> complaintResponsiveness = performanceFunctionService.ComplaintResponsiveness(performance.get(i).getPiWhether());
            policy_scroeList.add(complaintResponsiveness.get("score"));
            policy_rankList.add(complaintResponsiveness.get("rank"));
            // 정책성 - 사업효과 범용성
            Map<String, String> businessEffect = performanceFunctionService.businessEffect(performance.get(i).getPiAADT());
            policy_scroeList.add(businessEffect.get("score"));
            policy_rankList.add(businessEffect.get("rank"));
            // 정책성 - 종합점수 및 등급
            Map<String, String> policyAllScoreRank = performanceFunctionService.policy_allScoreRank(policy_scroeList, weight.getPiWeightBusiness(), weight.getPiWeightComplaint(), weight.getPiWeightBusinessEffect());
            policy_scroeList.add(policyAllScoreRank.get("score"));
            policy_rankList.add(policyAllScoreRank.get("rank"));

//            log.info("technicality_scroeList : " + technicality_scroeList);
//            log.info("technicality_rankList : " + technicality_rankList);
//
//            log.info("economy_scroeList : " + economy_scroeList);
//            log.info("economy_rankList : " + economy_rankList);
//
//            log.info("policy_scroeList : " + policy_scroeList);
//            log.info("policy_rankList : " + policy_rankList);

            technicality_scroeMap.put(i,technicality_scroeList);
            technicality_rankMap.put(i,technicality_rankList);

            economy_scroeMap.put(i,economy_scroeList);
            economy_rankMap.put(i,economy_rankList);

            policy_scroeMap.put(i,policy_scroeList);
            policy_rankMap.put(i,policy_rankList);

        }

//        log.info("기술성 환산점수 리스트 : " + technicality_scroeMap);
//        log.info("기술성 환산등급 리스트 : " + technicality_rankMap);
//
//        log.info("경제성 환산점수 리스트 : " + economy_scroeMap);
//        log.info("경제성 환산등급 리스트 : " + economy_rankMap);
//
//        log.info("정책성 환산점수 리스트 : " + policy_scroeMap);
//        log.info("정책성 환산등급 리스트 : " + policy_rankMap);

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
        int size;
        for(int i=0; i<performance.size(); i++) {
            scoreList = new ArrayList<>();
            all_scroeList = new ArrayList<>();
            all_rankList = new ArrayList<>();
            all_businessList = new ArrayList<>();

            size = technicality_scroeMap.get(i).size();
            scoreList.add(technicality_scroeMap.get(i).get(size-1));
            size = economy_scroeMap.get(i).size();
            scoreList.add(economy_scroeMap.get(i).get(size-1));
            size = policy_scroeMap.get(i).size();
            scoreList.add(policy_scroeMap.get(i).get(size-1));
            log.info(i+1+"번째 scoreList : "+scoreList);

            // 유형별/지표별 가중치는 바뀔수있음.
            Map<String, Object> all_ScoreRank = performanceFunctionService.all_ScoreRank(scoreList, 0.8, 0.1, 0.1, weight.getPiWeightCriticalScore());
            all_scroeList.add(Double.parseDouble(String.valueOf(all_ScoreRank.get("score"))));
            great_scroeList.add(Double.parseDouble(String.valueOf(all_ScoreRank.get("score"))));
            all_rankList.add(String.valueOf(all_ScoreRank.get("rank")));
            all_businessList.add(String.valueOf(all_ScoreRank.get("business")));

            all_scroeMap.put(i,all_scroeList);
            all_rankMap.put(i,all_rankList);
            all_businessMap.put(i,all_businessList);

            if(i+1==performance.size()){
                Double maxVal = Collections.max(great_scroeList);
                for (int j=0; j<great_scroeList.size(); j++) {
                    if (great_scroeList.get(j).equals(maxVal)) {
                        Optional<Performance> optionalPerformance = performanceService.findByPiAutoNumAndInsert_idAndPiInputCount(autoNum,insert_id,j);
                        if(optionalPerformance.isEmpty()){
                            return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE024.getCode(), ResponseErrorCode.NDE024.getDesc(), null, null));
                        }else{
                            log.info("우수대안 업데이트하기 카운트 : "+j);
                            Performance updatePerformance = new Performance();
                            updatePerformance.setId(optionalPerformance.get().getId());
                            updatePerformance.setPiAutoNum(optionalPerformance.get().getPiAutoNum());
                            updatePerformance.setPiFacilityType(optionalPerformance.get().getPiFacilityType());
                            updatePerformance.setPiFacilityName(optionalPerformance.get().getPiFacilityName());
                            updatePerformance.setPiKind(optionalPerformance.get().getPiKind());
                            updatePerformance.setPiCompletionYear(optionalPerformance.get().getPiCompletionYear());
                            updatePerformance.setPiPublicYear(optionalPerformance.get().getPiPublicYear());
                            updatePerformance.setPiType(optionalPerformance.get().getPiType());
                            updatePerformance.setPiErectionCost(optionalPerformance.get().getPiErectionCost());
                            updatePerformance.setPiSafetyLevel(optionalPerformance.get().getPiSafetyLevel());
                            updatePerformance.setPiGoalLevel(optionalPerformance.get().getPiGoalLevel());
                            updatePerformance.setPiMaintenanceDelay(optionalPerformance.get().getPiMaintenanceDelay());
                            updatePerformance.setPiManagement(optionalPerformance.get().getPiManagement());
                            updatePerformance.setPiAgency(optionalPerformance.get().getPiAgency());
                            updatePerformance.setPiAADT(optionalPerformance.get().getPiAADT());
                            updatePerformance.setPiBusiness(optionalPerformance.get().getPiBusiness());
                            updatePerformance.setPiBusinessType(optionalPerformance.get().getPiBusinessType());
                            updatePerformance.setPiTargetAbsence(optionalPerformance.get().getPiTargetAbsence());
                            updatePerformance.setPiBusinessClassification(optionalPerformance.get().getPiBusinessClassification());
                            updatePerformance.setPiBusinessInformation(optionalPerformance.get().getPiBusinessInformation());
                            updatePerformance.setPiBusinessExpenses(optionalPerformance.get().getPiBusinessExpenses());
                            updatePerformance.setPiBeforeSafetyRating(optionalPerformance.get().getPiBeforeSafetyRating());
                            updatePerformance.setPiAfterSafetyRating(optionalPerformance.get().getPiAfterSafetyRating());
                            updatePerformance.setPiBusinessObligatory(optionalPerformance.get().getPiBusinessObligatory());
                            updatePerformance.setPiBusinessMandatory(optionalPerformance.get().getPiBusinessMandatory());
                            updatePerformance.setPiBusinessPlanned(optionalPerformance.get().getPiBusinessPlanned());
                            updatePerformance.setPiWhether(optionalPerformance.get().getPiWhether());
                            updatePerformance.setPiRaterBaseYear(optionalPerformance.get().getPiRaterBaseYear());
                            updatePerformance.setPiRater(optionalPerformance.get().getPiRater());
                            updatePerformance.setPiRaterBelong(optionalPerformance.get().getPiRaterBelong());
                            updatePerformance.setPiRaterPhone(optionalPerformance.get().getPiRaterPhone());
                            updatePerformance.setPiInputCount(optionalPerformance.get().getPiInputCount());
                            updatePerformance.setPiInputGreat(1);
                            updatePerformance.setPiInputMiddleSave(optionalPerformance.get().getPiInputMiddleSave());
                            updatePerformance.setInsertDateTime(optionalPerformance.get().getInsertDateTime());
                            updatePerformance.setInsert_id(optionalPerformance.get().getInsert_id());
                            updatePerformance.setModifyDateTime(LocalDateTime.now());
                            updatePerformance.setModify_id(insert_id);
                            performanceService.save(updatePerformance);
                        }
                        all_greate.add("우수 대안");
                    } else {
                        all_greate.add("-");
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





}
