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
            if(excelList.size()!=0) {
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
        log.info("대안사이즈 : " + performance.size());

        // 기술성 점수리스트, 등급리스트
        List<String> technicality_scroeList = new ArrayList<>();
        List<String> technicality_rankList = new ArrayList<>();












        Map<String,String> safetyLevel  = performanceFunctionService.safetyLevel(performance.get(0).getPiSafetyLevel());
        technicality_scroeList.add(safetyLevel.get("score"));
        technicality_rankList.add(safetyLevel.get("rank"));

        Map<String,String> publicYear  = performanceFunctionService.publicYear(performance.get(0).getPiPublicYear());
        technicality_scroeList.add(publicYear.get("score"));
        technicality_rankList.add(publicYear.get("rank"));

        Map<String,String> urgency  = performanceFunctionService.urgency(performance.get(0).getPiSafetyLevel(),performance.get(0).getPiMaintenanceDelay());
        technicality_scroeList.add(urgency.get("score"));
        technicality_rankList.add(urgency.get("rank"));

        Map<String,String> goal  = performanceFunctionService.goal(performance.get(0).getPiAfterSafetyRating(),performance.get(0).getPiGoalLevel());
        technicality_scroeList.add(goal.get("score"));
        technicality_rankList.add(goal.get("rank"));

        // 기술성 가중치 리스트
        List<Double> technicality_weigh = new ArrayList<>();
        technicality_weigh.add(weight.getPiWeightSafe());
        technicality_weigh.add(weight.getPiWeightOld());
        technicality_weigh.add(weight.getPiWeightUrgency());
        technicality_weigh.add(weight.getPiWeightGoal());

        // 기술성 종합점수
        Double technicality_allScore = performanceFunctionService.allScore(technicality_scroeList,technicality_weigh);
        // 기술성 종합랭크
        String technicality_allRank = performanceFunctionService.allRank(technicality_allScore);
        technicality_scroeList.add(String.valueOf(technicality_allScore));
        technicality_rankList.add(technicality_allRank);
        log.info("*******************************************************************************************************");
        log.info("노후화_기술성");
        log.info("성능개선 평가점수 리스트 : " + technicality_scroeList);
        log.info("성능개선 평가등급 리스트 : " + technicality_rankList);
        log.info("성능개선 종합점수 : " + technicality_allScore);
        log.info("성능개선 종합등급 : " + technicality_allRank);
        log.info("*******************************************************************************************************");




        data.put("performanceList",performance);
        data.put("performanceSize",performance.size());





        data.put("technicalityScroeList",technicality_scroeList);
        data.put("technicalityRankList",technicality_rankList);
        data.put("technicalityAllScore",technicality_allScore);
        data.put("technicalityAllRank",technicality_allRank);

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }





}
