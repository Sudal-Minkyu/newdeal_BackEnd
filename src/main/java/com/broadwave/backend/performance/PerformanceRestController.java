package com.broadwave.backend.performance;

import com.broadwave.backend.common.AjaxResponse;
import com.broadwave.backend.common.ResponseErrorCode;
import com.broadwave.backend.keygenerate.KeyGenerateService;
import com.broadwave.backend.performance.weight.Weight;
import com.broadwave.backend.performance.weight.WeightDto;
import com.broadwave.backend.performance.weight.WeightService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

//    private final ModelMapper modelMapper;

    private final PerformanceService performanceService;
    private final WeightService weightService;
    private final PerformanceFunction performanceFunction;
    private final KeyGenerateService keyGenerateService;

    @Autowired
    public PerformanceRestController(PerformanceService performanceService,
                                                    WeightService weightService,
                                                    PerformanceFunction performanceFunction,
                                                    KeyGenerateService keyGenerateService) {
//        this.modelMapper = modelMapper;
        this.performanceService = performanceService;
        this.weightService = weightService;
        this.performanceFunction = performanceFunction;
        this.keyGenerateService = keyGenerateService;
    }

    // NEWDEAL 성능개선사업평가 엑셀 업로드
    @PostMapping("/excelUpload")
    public ResponseEntity<Map<String,Object>> readExcel(@RequestParam("excelfile") MultipartFile excelfile, HttpServletRequest request) throws IOException {

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

        Sheet worksheet2 = workbook.getSheetAt(1); // 두번째 시트
        // 제공한 양식 엑셀파일이 맞는지 확인2 (두번째시트)
        try {
            Row rowCheck2 = worksheet2.getRow(3);
            Object cellDataCheck2 = rowCheck2.getCell(2);
            Integer rowSize = worksheet2.getPhysicalNumberOfRows();
            log.info("cellDataCheck2 : " + cellDataCheck2.toString());
            log.info("rowSize : " + rowSize);
            log.info("");
            if (!cellDataCheck2.toString().equals("항목")) {
                return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE012.getCode(), ResponseErrorCode.NDE012.getDesc(), null, null));
            }else if(!rowSize.equals(13)){
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

        ArrayList<Object> excelList = new ArrayList<>();
        List<Performance> ListPerformance = new ArrayList<>();
        Weight weight = new Weight();
        log.info("getPhysicalNumberOfRows : " + worksheet.getPhysicalNumberOfRows());
        log.info("");
        for (int i=3; i<5; i++){
            Performance performance = new Performance();
            for (int j = 3; j < worksheet.getPhysicalNumberOfRows()+1; j++) {
                try{
                    Row row = worksheet.getRow(j);
                    Cell cellData = row.getCell(i);
                    if(j==21 || j==9){
                        try{
                            String cost = cellData.toString();
                            if(cost.contains("E")){
                                log.info("문자가 E가 포함되어 있습니다.");
                                log.info("cost : "+cost);
                                BigDecimal costCheck = new BigDecimal(Double.parseDouble(cost));
                                log.info("숫자로 변환 : "+costCheck);
                                excelList.add(costCheck);
                            }else{
                                log.info("문자 E가 포함되어 있지 않습니다.");
                                log.info("cost : "+cost);
                                excelList.add(cellData);
                            }
                        }catch (Exception e){
                            log.info("e : "+e);
                            log.info("숫자가 아닌 문자열 입니다.");
                            return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE017.getCode(), ResponseErrorCode.NDE017.getDesc(),ResponseErrorCode.NDE018.getCode(), ResponseErrorCode.NDE018.getDesc()));
                        }
                    }else{
                        excelList.add(cellData);
                    }
                }catch (Exception e){
                    log.info("e : "+e);
                    return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE012.getCode(), ResponseErrorCode.NDE012.getDesc(), null, null));
                }
            }

            log.info("==============결과==============");
            log.info(i-2+"번째 루트 리스트 : "+excelList);
            log.info(i-2+"번째 루트 리스트길이 : "+excelList.size());


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
            performance.setPiInputCount(i-2);  //대안카운트(NULL)

            performance.setInsertDateTime(LocalDateTime.now()); // 작성날짜
            performance.setInsert_id(currentuserid); // 작성자

            excelList.clear();
            ListPerformance.add(performance);
        }
        log.info("ListPerformance : " + ListPerformance);

        for (Performance performance : ListPerformance) {
            performanceService.save(performance);
        }

        for(int i=4; i<worksheet2.getPhysicalNumberOfRows(); i++){
            Row row = worksheet2.getRow(i);
            Cell cellData = row.getCell(3);
            excelList.add(cellData);
            log.info(" row.getCell(5) : "+ row.getCell(5));
        }
        log.info("가중치 리스트 : "+excelList);

        weight.setPiAutoNum(autoNum);
        weight.setPiWeightSafe(Double.parseDouble(excelList.get(0).toString()));
        weight.setPiWeightOld(Double.parseDouble(excelList.get(1).toString()));
        weight.setPiWeightUrgency(Double.parseDouble(excelList.get(2).toString()));
        weight.setPiWeightGoal(Double.parseDouble(excelList.get(3).toString()));
        weight.setPiWeightSafeUtility(Double.parseDouble(excelList.get(4).toString()));
        weight.setPiWeightCostUtility(Double.parseDouble(excelList.get(5).toString()));
        weight.setPiWeightBusiness(Double.parseDouble(excelList.get(6).toString()));
        weight.setPiWeightComplaint(Double.parseDouble(excelList.get(7).toString()));
        weight.setPiWeightBusinessEffect(Double.parseDouble(excelList.get(8).toString()));
        weight.setInsert_id(currentuserid);
        weight.setInsertDateTime(LocalDateTime.now());
        weightService.save(weight);

        data.put("autoNum",autoNum);
        return ResponseEntity.ok(res.dataSendSuccess(data));
    }


    // NEWDEAL 성능개선사업평가 Output 호출
    @PostMapping("/output")
    public ResponseEntity<Map<String,Object>> output(@RequestParam("autoNum")String autoNum, HttpServletRequest request) throws IOException {

        log.info("Output 호출성공");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
        log.info("JWT_AccessToken : " + JWT_AccessToken);
        if (JWT_AccessToken==null) {
            return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE015.getCode(), ResponseErrorCode.NDE015.getDesc(), ResponseErrorCode.NDE016.getCode(), ResponseErrorCode.NDE016.getDesc()));
        }

        // 해당 아웃풋에 계산할 가중치 가져오기
        WeightDto weight = weightService.findByAutoNum(autoNum);

        // 해당 아웃풋에 가져올 대안 가져오기
        List<PerformanceDto> performance = performanceService.findByAutoNum(autoNum);

        log.info("일련번호 : " + autoNum);
        log.info("가중치 : " + weight);
        log.info("대안리스트 : " + performance);

        // 기술성 점수리스트, 등급리스트
        List<String> technicality_scroeList = new ArrayList<>();
        List<String> technicality_rankList = new ArrayList<>();

        Map<String,String> safetyLevel  = performanceFunction.safetyLevel(performance.get(0).getPiSafetyLevel());
        technicality_scroeList.add(safetyLevel.get("score"));
        technicality_rankList.add(safetyLevel.get("rank"));

        Map<String,String> publicYear  = performanceFunction.publicYear(performance.get(0).getPiPublicYear());
        technicality_scroeList.add(publicYear.get("score"));
        technicality_rankList.add(publicYear.get("rank"));

        Map<String,String> urgency  = performanceFunction.urgency(performance.get(0).getPiSafetyLevel(),performance.get(0).getPiMaintenanceDelay());
        technicality_scroeList.add(urgency.get("score"));
        technicality_rankList.add(urgency.get("rank"));

        Map<String,String> goal  = performanceFunction.goal(performance.get(0).getPiAfterSafetyRating(),performance.get(0).getPiGoalLevel());
        technicality_scroeList.add(goal.get("score"));
        technicality_rankList.add(goal.get("rank"));

        // 기술성 가중치 리스트
        List<Double> technicality_weigh = new ArrayList<>();
        technicality_weigh.add(weight.getPiWeightSafe());
        technicality_weigh.add(weight.getPiWeightOld());
        technicality_weigh.add(weight.getPiWeightUrgency());
        technicality_weigh.add(weight.getPiWeightGoal());

        // 기술성 종합점수
        Double technicality_allScore = performanceFunction.allScore(technicality_scroeList,technicality_weigh);
        // 기술성 종합랭크
        String technicality_allRank = performanceFunction.allRank(technicality_allScore);
        technicality_scroeList.add(String.valueOf(technicality_allScore));
        technicality_rankList.add(technicality_allRank);
        log.info("*******************************************************************************************************");
        log.info("노후화_기술성");
        log.info("성능개선 평가점수 리스트 : " + technicality_scroeList);
        log.info("성능개선 평가등급 리스트 : " + technicality_rankList);
        log.info("성능개선 종합점수 : " + technicality_allScore);
        log.info("성능개선 종합등급 : " + technicality_allRank);
        log.info("*******************************************************************************************************");











        data.put("technicalityScroeList",technicality_scroeList);
        data.put("technicalityRankList",technicality_rankList);
        data.put("technicalityAllScore",technicality_allScore);
        data.put("technicalityAllRank",technicality_allRank);

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }





}
