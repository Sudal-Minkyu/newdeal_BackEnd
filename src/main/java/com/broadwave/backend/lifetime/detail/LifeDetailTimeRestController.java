package com.broadwave.backend.lifetime.detail;

import com.broadwave.backend.common.AjaxResponse;
import com.broadwave.backend.common.NormMath;
import com.broadwave.backend.common.ResponseErrorCode;
import com.broadwave.backend.lifetime.absence.AbsenceService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Minkyu
 * Date : 2021-09-13
 * Remark : NEWDEAL 성능개선사업평가 세부부분 컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api/lifedetailtime")
public class LifeDetailTimeRestController {

    private final ModelMapper modelMapper;
    private final AbsenceService absenceService;
    private final LifeDetailTimeService lifeDetailTimeService;

    @Autowired
    public LifeDetailTimeRestController(ModelMapper modelMapper, AbsenceService absenceService, LifeDetailTimeService lifeDetailTimeService) {
        this.modelMapper = modelMapper;
        this.absenceService = absenceService;
        this.lifeDetailTimeService = lifeDetailTimeService;
    }

    // NEWDEAL 생애주기 의사결정 지원 서비스 전체부분 저장
    @PostMapping("/save")
    public ResponseEntity<Map<String,Object>> save(@ModelAttribute LifeDetailTimeMapperDto lifeDetailTimeMapperDto, HttpServletRequest request) {

//        log.info("save 호출성공");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
        String insert_id = request.getHeader("insert_id");

        log.info("JWT_AccessToken : "+JWT_AccessToken);
        log.info("insert_id : "+insert_id);

        LifeDetailTime lifeDetailTime = modelMapper.map(lifeDetailTimeMapperDto, LifeDetailTime.class);

        lifeDetailTime.setInsertDateTime(LocalDateTime.now());
        lifeDetailTime.setInsert_id(insert_id);

        log.info("lifeDetailTime : "+lifeDetailTime);

        LifeDetailTime detailTime = lifeDetailTimeService.save(lifeDetailTime);

        data.put("getId", detailTime.getId());

        return ResponseEntity.ok(res.dataSendSuccess(data));

    }

    // NEWDEAL 생애주기 의사결정 지원 서비스 전체부분 아웃풋
    @PostMapping("/output")
    public ResponseEntity<Map<String,Object>> output(@RequestParam(value="id", defaultValue="")Long id, HttpServletRequest request) {

//        log.info("output 호출성공");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
        log.info("JWT_AccessToken : "+JWT_AccessToken);

        LifeDetailTimeDto lifeDetailTimeDto = lifeDetailTimeService.findById(id);

        if(lifeDetailTimeDto.getId() != null){

//            log.info("lifeDetailTimeDto : "+lifeDetailTimeDto);

            List<Double> ltRecoveryList = new ArrayList<>();
            ltRecoveryList.add(lifeDetailTimeDto.getLtRecoveryOne());
            ltRecoveryList.add(lifeDetailTimeDto.getLtRecoveryTwo());
            ltRecoveryList.add(lifeDetailTimeDto.getLtRecoveryThree());
            ltRecoveryList.add(lifeDetailTimeDto.getLtRecoveryFour());
            ltRecoveryList.add(lifeDetailTimeDto.getLtRecoveryFive());
            ltRecoveryList.add(lifeDetailTimeDto.getLtRecoverySix());

            List<Double> ltCostList = new ArrayList<>();
            ltCostList.add(lifeDetailTimeDto.getLtCostOne());
            ltCostList.add(lifeDetailTimeDto.getLtCostTwo());
            ltCostList.add(lifeDetailTimeDto.getLtCostThree());
            ltCostList.add(lifeDetailTimeDto.getLtCostFour());
            ltCostList.add(lifeDetailTimeDto.getLtCostFive());
            ltCostList.add(lifeDetailTimeDto.getLtCostSix());

            List<Double> meanList = new ArrayList<>();
            List<Double> sdtList = new ArrayList<>();

            double areAverage = 0.015; // 철근 단면적 감소율 평균값
            double areVariance = 0.1; // 철근 단면적 감소율 변동계수
            for(int i=0; i<21; i++){
                if(i==0){
                    meanList.add(1.0);
                    sdtList.add(0.1);
                }else{
                    double mean = meanList.get(i-1)-(meanList.get(i-1) * areAverage);
                    double sdt = mean * areVariance;
                    meanList.add(mean);
                    sdtList.add(sdt);
                }
            }
//            log.info("meanList : "+meanList);
//            log.info("sdtList : "+sdtList);

            // 사용자 입력 난수 -  평균값
            double fyAverage = lifeDetailTimeDto.getLtFyAverage(); // 철근 항복강도 평균값
            double AAverage = lifeDetailTimeDto.getLtSectionAverage(); // 철근 단면적 평균값
            double fcAverage = lifeDetailTimeDto.getLtFcAverage(); // 콘크리트 압축강도 평균값
            double mdeckAverage = lifeDetailTimeDto.getLtVehicleAverage(); // 차량하중 평균값

            // 사용자 입력 난수 -  표준편차
            double fyStandard = lifeDetailTimeDto.getLtFyStandard(); // 철근 항복강도 표준편차
            double AStandard = lifeDetailTimeDto.getLtSectionStandard(); // 철근 단면적 표준편차
            double fcStandard = lifeDetailTimeDto.getLtFcStandard(); // 콘크리트 압축강도 표준편차
            double mdeckStandard = lifeDetailTimeDto.getLtVehicleStandard(); // 차량하중 표준편차

            // 시스템 난수 -  평균값
            double ymfcAverage = 1.02; // 바닥판 모델링 불확실성 인자 평균값
            double asphAverage = 1.0; // 아스콘 중량 불확실성 인자 평균값
            double concAverage = 1.05; // 콘크리트 중량 불확실성 인자 평균값

            // 시스템 난수 -  표준편차
            double ymfcStandard = 0.06; // 바닥판 모델링 불확실성 인자 표준편차
            double asphStandard= 0.25; // 아스콘 중량 불확실성 인자 표준편차
            double concStandard = 0.11; // 콘크리트 중량 불확실성 인자 표준편차

            List<Double> pf_List = new ArrayList<>(); // 무조치시 PF 리스트
            List<Double> b_List = new ArrayList<>(); // 무조치시 B 리스트

            int simulation = 50001; // 시뮬레이션 횟수

            double masterCode1 = 0.563; // 마스터코드화 해야함. 이름은 결정안남//
            double masterCode2 = 244.8; // 마스터코드화 해야함. 이름은 결정안남//
            double masterCode3 = 0.137; // 마스터코드화 해야함. 이름은 결정안남//
            double masterCode4 = 0.471; // 마스터코드화 해야함. 이름은 결정안남//

            double rand;
            for(int area=0; area<21; area++){
                int pf_Count = 0; // PF<0 카운트
                double result;
                for(int i=1; i<simulation; i++) {

                    rand = Math.random();
                    double fyNum = NormMath.inv(rand, fyAverage, fyStandard); // fy 난수

                    rand = Math.random();
                    double ANum = NormMath.inv(rand, AAverage, AStandard); // A 난수

                    rand = Math.random();
                    double ymfcNum = NormMath.inv(rand, ymfcAverage, ymfcStandard); // ymfc 난수

                    rand = Math.random();
                    double fcNum = NormMath.inv(rand, fcAverage, fcStandard); // fc 난수

                    rand = Math.random();
                    double asphNum = NormMath.inv(rand, asphAverage, asphStandard); // asph 난수

                    rand = Math.random();
                    double concNum = NormMath.inv(rand, concAverage, concStandard); // conc 난수

                    rand = Math.random();
                    double mdeckNum = NormMath.inv(rand, mdeckAverage, mdeckStandard); // mdeck 난수

                    rand = Math.random();
                    double areRandom = NormMath.inv(rand, meanList.get(area), sdtList.get(area)); // Are 난수

//                    log.info(i + "번 fyNum 난수 : " + fyNum);
//                    log.info(i + "번 ANum 난수 : " + ANum);
//                    log.info(i + "번 ymfcNum 난수 : " + ymfcNum);
//                    log.info(i + "번 fcNum 난수 : " + fcNum);
//                    log.info(i + "번 asphNum 난수 : " + asphNum);
//                    log.info(i + "번 concNum 난수 : " + concNum);
//                    log.info(i + "번 mdeckNum 난수 : " + mdeckNum);

//                    log.info(i + "번 areRandom 난수 : " + areRandom);

                    // =(0.563*$D19*$C19*$E19)*J19 - ($D19*$D19*$C19*$C19*$E19/(244.8*$F19)) - 0.137*$G19-0.471*$H19-$I19  -> 엑셀 식
                    result = (masterCode1*ANum*fyNum*ymfcNum)*areRandom-(ANum*ANum*fyNum*fyNum*ymfcNum/(masterCode2*fcNum))-masterCode3*asphNum-masterCode4*concNum-mdeckNum;
//                    log.info(i+"번 result : "+result);

                    if(result<0){
                        pf_Count++;
                    }

                }

//                log.info(area+"번 pf_Count : "+pf_Count);
                double pf = (double)pf_Count/simulation;
                pf_List.add(pf);
                b_List.add(-NormMath.sinv(pf));
            }

//            System.out.println();
//            log.info("무조치시 PF pf_List : " + pf_List);
//            log.info("무조치시 B b_List : " + b_List);
//            log.info("사이즈 : " + b_List.size());

//            System.out.println();
            double pf_max = Collections.max(pf_List);
            double pf_min = -NormMath.sinv(pf_max);
//            log.info("손상확률 최대값(PF 최댓값) : " + pf_max);
//            log.info("신뢰성 지수 최소값 : " + pf_min);

            double b_max = Collections.max(b_List);

            List<Double> referenceTable_List = new ArrayList<>();
            for(int i=0; i<21; i++){
                switch (i%21){
                    case 0: case 5: case 10: case 15: case 20:
                        referenceTable_List.add(b_List.get(0)*lifeDetailTimeDto.getLtRecoveryPercent());
                        break;
                    case 1: case 6: case 11: case 16:
                        referenceTable_List.add(b_List.get(1)*lifeDetailTimeDto.getLtRecoveryPercent());
                        break;
                    case 2: case 7: case 12: case 17:
                        referenceTable_List.add(b_List.get(2)*lifeDetailTimeDto.getLtRecoveryPercent());
                        break;
                    case 3: case 8: case 13: case 18:
                        referenceTable_List.add(b_List.get(3)*lifeDetailTimeDto.getLtRecoveryPercent());
                        break;
                    case 4: case 9: case 14: case 19:
                        referenceTable_List.add(b_List.get(4)*lifeDetailTimeDto.getLtRecoveryPercent());
                        break;
                }
            }
//            log.info("referenceTable_List : " +referenceTable_List);

            List<Double> b_One_List = new ArrayList<>();
            List<Double> b_Two_List = new ArrayList<>();
            int maintenanceYear = 0;
            for(int i=1; i<22; i++){
                if(b_List.get(i-1)>=lifeDetailTimeDto.getLtTargetValue()){
                    b_One_List.add(b_List.get(i-1));
                }else{
                    b_One_List.add(0.0);
                }
            }

            for(int i=0; i<21; i++){
                if(b_One_List.get(i)==0.0){
                    maintenanceYear = i;
                    break;
                }
            }

            int count = 0;
            double ltRepairLength = lifeDetailTimeDto.getLtRepairLength(); // 보수보강 총 길이
            int repairNumber = 0; // 보수보강 개입 횟수
            int repairCost = 0; // 보수보강 총 비용
            for(int i=0; i<21; i++){
                if(i<maintenanceYear){
                    b_Two_List.add(b_List.get(i));
                }else{
                    b_Two_List.add(referenceTable_List.get(count));
                    count++;
                    if (count%5 == 0) {
                        repairNumber++;
                    }
                }
            }


            for(int i=0; i<ltRecoveryList.size(); i++){
                if(lifeDetailTimeDto.getLtRecoveryPercent().equals(ltRecoveryList.get(i))){
                    repairCost = Integer.parseInt(String.valueOf(Math.round(ltRepairLength * repairNumber * ltCostList.get(i))));
                    break;
                }
            }


//            System.out.println();
//            log.info("보수보강 총 길이 : " +ltRepairLength);
//            log.info("보수보강 개입 횟수: " + repairNumber);
//            log.info("보수보강 총 비용 : "+ repairCost);
//
//            System.out.println();
//            log.info("신뢰성 지수 최대값 : " +b_max);
//            log.info("신뢰성 지수 최소값 : " + lifeDetailTimeDto.getLtTargetValue());
//            log.info("보수보강 회복율 : " + lifeDetailTimeDto.getLtRecoveryPercent()*100);
//            log.info("유지보수 무조치 가능년수 : "+ maintenanceYear);

//            log.info("b_One_List : "+ b_One_List);
//            log.info("b_Two_List : "+ b_Two_List);
//            log.info("b_One_List 사이즈 : "+ b_One_List.size());
//            log.info("b_Two_List 사이즈 : "+ b_Two_List.size());

            data.put("lifeDetailTimeDto",lifeDetailTimeDto); // lifeDetailTimeDto 모든 값 던지기

            data.put("pfmax",Math.floor(pf_max*1000)/1000.0); // 무조치 시 손상확률 최대값(PF 최댓값)
            data.put("pfmin",Math.floor(pf_min*1000)/1000.0); // 무조치 시 신뢰성 지수 최소값
            data.put("pfList",pf_List); // 무조치 시 PF
            data.put("bList",b_List); // 무조치 시 B

            data.put("bmax",Math.floor(b_max*1000)/1000.0); // 유지보수 개입 시 신뢰성지수최대값
            data.put("bmin",lifeDetailTimeDto.getLtTargetValue()); // 유지보수 개입 시 신뢰성지수최소값
            data.put("ltRecoveryPercent",lifeDetailTimeDto.getLtRecoveryPercent()*100); // 유지보수 개입 시 보수보강 회복율
            data.put("maintenanceYear",maintenanceYear); // 유지보수 개입 시 유지보수 무조치 가능년수

            data.put("bOneList",b_One_List); // 유지보수 개입 시 B1
            data.put("bTwoList",b_Two_List); // 유지보수 개입 시 B2

            data.put("ltRepairLength",ltRepairLength);; // 보수보강 총 길이
            data.put("repairNumber",repairNumber); // 보수보강 개입 횟수
            data.put("repairCost",repairCost); // 보수보강 총 비용


            // 차트의 JSON정보를 담을 Array 선언
            List<HashMap<String,Object>> noactionChartDataList = new ArrayList<>(); // 무조치 시 차트데이터
            List<HashMap<String,Object>> actionChartDataList = new ArrayList<>(); // 유지보수 개입시 차트데이터
            HashMap<String,Object> noactionchartData;
            HashMap<String,Object> actionchartData;

            // 차트데이터 값 for문 알고리즘 20번돌아야됨.
            for(int publicYear=0; publicYear< b_Two_List.size(); publicYear++){

                // 그래프로 보낼 데이터 뽑기 여기서 시작
                noactionchartData  = new HashMap<>();
                actionchartData  = new HashMap<>();

                noactionchartData.put("publicYear", publicYear);
                noactionchartData.put("redline", lifeDetailTimeDto.getLtTargetValue());
                noactionchartData.put("noaction", Math.floor(b_List.get(publicYear)*1000)/1000.0);
                noactionChartDataList.add(noactionchartData);

                actionchartData.put("publicYear", publicYear);
                actionchartData.put("redline", lifeDetailTimeDto.getLtTargetValue());
                actionchartData.put("action", Math.floor(b_Two_List.get(publicYear)*1000)/1000.0);
                actionChartDataList.add(actionchartData);

            }

//            System.out.println();
//            log.info("무조치 차트 테스트 : "+noactionChartDataList);
//            log.info("무조치 차트 데이터 길이 : "+noactionChartDataList.size());
//            log.info("유지보수 차트 테스트 : "+actionChartDataList);
//            log.info("유지보수 차트 데이터 길이 : "+actionChartDataList.size());

            data.put("ltTargetValue", lifeDetailTimeDto.getLtTargetValue());
            data.put("noactionChartDataList",noactionChartDataList);
            data.put("actionChartDataList",actionChartDataList);

        }else{
            return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE006.getCode(),ResponseErrorCode.NDE006.getDesc(),null,null));
        }

//        data.put("lifeDetailTimeDto",lifeDetailTimeDto);

        return ResponseEntity.ok(res.dataSendSuccess(data));

    }

}


