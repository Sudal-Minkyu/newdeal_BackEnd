package com.broadwave.backend.lifetime;

import com.broadwave.backend.common.AjaxResponse;
import com.broadwave.backend.common.ResponseErrorCode;
import com.broadwave.backend.keygenerate.KeyGenerateService;
import com.broadwave.backend.lifetime.absence.AbsenceDto;
import com.broadwave.backend.lifetime.absence.AbsenceService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Minkyu
 * Date : 2021-08-04
 * Remark : NEWDEAL 성능개선사업평가 컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api/lifealltime")
public class LifeAllTimeRestController {

    private final ModelMapper modelMapper;
    private final AbsenceService absenceService;
    private final LifeAllTimeService lifeAllTimeService;
    private final KeyGenerateService keyGenerateService;

    @Autowired
    public LifeAllTimeRestController(ModelMapper modelMapper, AbsenceService absenceService, LifeAllTimeService lifeAllTimeService,KeyGenerateService keyGenerateService) {
        this.modelMapper = modelMapper;
        this.absenceService = absenceService;
        this.lifeAllTimeService = lifeAllTimeService;
        this.keyGenerateService = keyGenerateService;
    }

    // NEWDEAL 생애주기 의사결정 지원 서비스 전체부분 저장
    @PostMapping("/save")
    public ResponseEntity<Map<String,Object>> save(@ModelAttribute LifeAllTimeMapperDto lifeAllTimeMapperDto,HttpServletRequest request) {

        log.info("save 호출성공");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
        String insert_id = request.getHeader("insert_id");

//        log.info("JWT_AccessToken : "+JWT_AccessToken);
//        log.info("insert_id : "+insert_id);

        LifeAllTime lifeAllTime = modelMapper.map(lifeAllTimeMapperDto, LifeAllTime.class);

//        log.info("일련번호 생성");
        Date now = new Date();
        SimpleDateFormat yyMM = new SimpleDateFormat("yyMM");
        String newAutoNum = keyGenerateService.keyGenerate("nd_lt_all_input", yyMM.format(now), insert_id);
        lifeAllTime.setLtBridgeCode(newAutoNum);
        lifeAllTime.setInsertDateTime(LocalDateTime.now());
        lifeAllTime.setInsert_id(insert_id);
//        log.info("lifeAllTime : "+lifeAllTime);

        LifeAllTime allTime = lifeAllTimeService.save(lifeAllTime);

        data.put("getId", allTime.getId());

        return ResponseEntity.ok(res.dataSendSuccess(data));

    }

    // NEWDEAL 생애주기 의사결정 지원 서비스 전체부분 아웃풋
    @PostMapping("/output")
    public ResponseEntity<Map<String,Object>> output(@RequestParam(value="id", defaultValue="")Long id, HttpServletRequest request) {

        log.info("output 호출성공");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
        log.info("JWT_AccessToken : "+JWT_AccessToken);

        LifeAllTimeDto lifeAllTimeDto = lifeAllTimeService.findById(id);

        if(lifeAllTimeDto.getLtAbsenceCode() != null){
            AbsenceDto absenceDto = absenceService.findByLtAbsenceCode(lifeAllTimeDto.getLtAbsenceCode());

            double ltDiscountRate = lifeAllTimeDto.getLtDiscountRate();
            double ltAllVolume = lifeAllTimeDto.getLtAllVolume();

            List<Double> rankList = new ArrayList<>();
            List<Double> rankList2 = new ArrayList<>();
            double ltDamageBRank = lifeAllTimeDto.getLtDamageBRank();
            double ltDamageCRank = lifeAllTimeDto.getLtDamageCRank();
            double ltDamageDRank = lifeAllTimeDto.getLtDamageDRank();
            double ltDamageERank = lifeAllTimeDto.getLtDamageERank();
            rankList.add(ltDamageBRank);
            rankList.add(ltDamageCRank);
            rankList.add(ltDamageDRank);
            rankList.add(ltDamageERank);
            rankList2.add(ltDamageCRank);
            rankList2.add(ltDamageDRank);
            rankList2.add(ltDamageERank);

            double ltRemunerationThree = absenceDto.getLtRemunerationThree();
            double ltRemunerationTwo = absenceDto.getLtRemunerationTwo();
            double ltRemunerationOne = absenceDto.getLtRemunerationOne();
            double ltRemunerationNum = absenceDto.getLtRemunerationNum();

            double ltStatusTwo = absenceDto.getLtStatusTwo();
            double ltStatusOne = absenceDto.getLtStatusOne();
            double ltStatusNum = absenceDto.getLtStatusNum();

            double rankB = ltDamageBRank-(ltStatusTwo*Math.pow(ltDamageBRank,2)+ltStatusOne*ltDamageBRank+ltStatusNum);
            double rankC = ltDamageCRank-(ltStatusTwo*Math.pow(ltDamageCRank,2)+ltStatusOne*ltDamageCRank+ltStatusNum);
            double rankD = ltDamageDRank-(ltStatusTwo*Math.pow(ltDamageDRank,2)+ltStatusOne*ltDamageDRank+ltStatusNum);
//            double rankE = ltDamageERank-(ltStatusTwo*Math.pow(ltDamageERank,2)+ltStatusOne*ltDamageERank+ltStatusNum);
            double rankE = 0.0; // 전면교체로 가정하여 교체 신성교량이 되므로, 보수보강 수행 후 E등급 손상지수는 0으로 고정함.

            List<Double> damageRankList = new ArrayList<>();
            damageRankList.add(rankB);
            damageRankList.add(rankC);
            damageRankList.add(rankD);
            damageRankList.add(rankE);
            List<Double> damageRankList2 = new ArrayList<>();
            damageRankList2.add(rankC);
            damageRankList2.add(rankD);
            damageRankList2.add(rankE);

            double costB = (ltRemunerationThree*Math.pow(ltDamageBRank,3)+ltRemunerationTwo*Math.pow(ltDamageBRank,2)+ltRemunerationOne*ltDamageBRank+ltRemunerationNum)*ltAllVolume;
            double costC =(ltRemunerationThree*Math.pow(ltDamageCRank,3)+ltRemunerationTwo*Math.pow(ltDamageCRank,2)+ltRemunerationOne*ltDamageCRank+ltRemunerationNum)*ltAllVolume;
            double costD = (ltRemunerationThree*Math.pow(ltDamageDRank,3)+ltRemunerationTwo*Math.pow(ltDamageDRank,2)+ltRemunerationOne*ltDamageDRank+ltRemunerationNum)*ltAllVolume;
            double costE = (ltRemunerationThree*Math.pow(ltDamageERank,3)+ltRemunerationTwo*Math.pow(ltDamageERank,2)+ltRemunerationOne*ltDamageERank+ltRemunerationNum)*ltAllVolume;

            List<Double> costRankList = new ArrayList<>();
            costRankList.add(costB);
            costRankList.add(costC);
            costRankList.add(costD);
            costRankList.add(costE);
            List<Double> costRankList2 = new ArrayList<>();
            costRankList2.add(costC);
            costRankList2.add(costD);
            costRankList2.add(costE);

            double discount = 1/(1+ltDiscountRate);

            double ltIncrease = 1+lifeAllTimeDto.getLtIncrease();

            double ltPeriodicRn = Math.pow(discount,lifeAllTimeDto.getLtPeriodicFrequency());
            double ltCloseRn = Math.pow(discount,lifeAllTimeDto.getLtCloseFrequency());
            double ltSafetyRn = Math.pow(discount,lifeAllTimeDto.getLtSafetyFrequency());

            // DB 데이터
            System.out.println();
            log.info("조회한 전체부분 데이터 : "+lifeAllTimeDto);
            log.info("부재별 수치 : "+absenceDto);
            System.out.println();

            // 단계마다 변하지 않은 값
            log.info("******************************************************************************************************************");
            log.info("보수보강 수행전 등급 손상지수 리스트 : "+rankList);
            log.info("보수보강 수행후 등급 손상지수 리스트 : "+damageRankList);
            log.info("보수보강 비용 리스트 : "+costRankList);
            log.info("R=1/(1+r) : "+ discount);
            log.info("열화증가율 : "+ ltIncrease);
            log.info("정기점검 R^n : "+ ltPeriodicRn);
            log.info("정밀점검 R^n : "+ ltCloseRn);
            log.info("정밀안전점검 R^n : "+ ltSafetyRn);
            log.info("******************************************************************************************************************");
            System.out.println();

            List<Double> periodicCountList = new ArrayList<>(); // 정기점검 횟수 리스트
            List<Double> closeCountList = new ArrayList<>(); // 정밀점검 횟수 리스트
            List<Double> safetyCountList = new ArrayList<>(); // 정밀안전진단 횟수 리스트
            List<Double> checkCostList = new ArrayList<>(); // 총 점검비용 리스트
            List<Double> managementCostList = new ArrayList<>(); // 총 유지관리비용 리스트

            double ltDeterioration; // 단계별 평균열화율
            double changeRankNum = 0; // 전 단계 수행전등급
            double damageRank = 0; // 보수보강 수행후등급
            double damageRankYear = 0; // 보수보강 수행시기(년)
            double discountRate = 0; // 할인율 적용 보수보강비용
            double repairYear; // 보수보강수행시기
            double costYearVal; // 원년

            List<Double> damageRankYearList = new ArrayList<>(); // 선제적 유지관리 보수보강수행시기 리스트
            List<Double> ltDeteriorationList = new ArrayList<>(); // 선제적 유지관리  적용된 기울기 리스트
            List<Double> pointViewEarlyList = new ArrayList<>(); // 선제적 유지관리 시점 초기값(b) 리스트
            List<Double> pointViewList = new ArrayList<>(); // 선제적 유지관리 시점 리스트

            double ltDeterioration2; // 단계별 평균열화율
            double changeRankNum2 = 0; // 전 단계 수행전등급
            double damageRank2 = 0; // 보수보강 수행후등급
            double damageRankYear2 = 0; // 보수보강 수행시기(년)
            double repairYear2; // 보수보강수행시기
            double discountRate2 = 0; // 할인율 적용 보수보강비용

            List<Double> damageRankYearList2 = new ArrayList<>(); // 현행 유지관리 보수보강수행시기 리스트
            List<Double> ltDeteriorationList2 = new ArrayList<>(); // 현행 유지관리 적용된 기울기 리스트
            List<Double> pointViewEarlyList2 = new ArrayList<>(); // 현행 유지관리 시점 초기값(b) 리스트
            List<Double> pointViewList2 = new ArrayList<>(); // 현행 유지관리 시점 리스트

            double pointView = 0; // 선제적 유지관리 시점
            double pointViewEarly = 0; // 선제적 유지관리 시점 초기값(b)

            double pointView2 = 0; // 현행 유지관리 시점
            double pointViewEarly2 = 0; // 현행 유지관리 시점 초기값(b)

            //person의 JSON정보를 담을 Array 선언
            List<HashMap<String,Double>> chartDataList = new ArrayList<>();
            HashMap<String,Double> chartData;


            for(int stage=1; stage<26; stage++){ // 25바퀴 고정

                List<Double> performYear = new ArrayList<>(); // 선행유지관리 보수보강수행시기(년) 리스트
                List<Double> discountRateList = new ArrayList<>(); // 선행유지관리 할인율적용 누적 보수보강비용 리스트
                List<Double> costYear = new ArrayList<>(); // 선행유지관리 원/년 리스트

                List<Double> performYear2 = new ArrayList<>(); // 선행유지관리 현행유지관리 보수보강수행시기(년) 리스트
                List<Double> discountRateList2 = new ArrayList<>(); // 선행유지관리 할인율적용 누적 보수보강비용 리스트
                List<Double> costYear2 = new ArrayList<>(); // 선행유지관리 원/년 리스트

                if(stage==1){
                    ltDeterioration = absenceDto.getLtDeterioration();
                    ltDeterioration2 = absenceDto.getLtDeterioration();
                }else{
                    if(changeRankNum == 1.0){
                        ltDeterioration = absenceDto.getLtDeterioration();
                    }else{
                        ltDeterioration = absenceDto.getLtDeterioration()*(Math.pow(ltIncrease,stage-1));
                    }
                    if(changeRankNum2 == 1.0){
                        ltDeterioration2 = absenceDto.getLtDeterioration();
                    }else{
                        ltDeterioration2 = absenceDto.getLtDeterioration()*(Math.pow(ltIncrease,stage-1));
                    }
                }

//                log.info(stage+"단계 선제적 유지관리 평균열화율  : "+ltDeterioration);
//                log.info(stage+"단계 현행 유지관리 평균열화율  : "+ltDeterioration2);
                // 선제적 유지관리
                for(int step=1; step<5; step++){ // 4바퀴 고정

                    // 1단계와 2~25단계까지 식이 다른 값들.
                    if(stage==1){
                        // 보수보강 수행시기(년)
                        repairYear = Math.pow((rankList.get(step - 1) / ltDeterioration), 0.5); // 보수보강 수행시기(년)
                        performYear.add(repairYear); // 보수보강 수행시기(년) 리스트로 저장

                        discountRateList.add(costRankList.get(step-1)*1/Math.pow((1+lifeAllTimeDto.getLtDiscountRate()),repairYear)); // 누적 보수보강비용(원) 리스트로 저장
                    }else{
                        // 보수보강 수행시기(년)
                        repairYear = damageRankYear+Math.sqrt((rankList.get(step-1)-damageRank)/ltDeterioration);
                        performYear.add(repairYear); // 보수보강 수행시기(년) 리스트로 저장

                        discountRateList.add(costRankList.get(step-1)*1/Math.pow((1+lifeAllTimeDto.getLtDiscountRate()),repairYear)+discountRate); // 누적 보수보강비용(원) 리스트로 저장
                    }

                    // 1~25단계까지 식이 같은 값들.

                    // 보수보강후 E등급 도달시기(년)
                    double arrival_ERank = Math.pow( (1-damageRankList.get(step-1)) / (ltDeterioration*ltIncrease),0.5)+repairYear;

                    // 총 점검비용 계산하기
                    // 1. 정기점검 횟수
                    double periodic = Math.floor(arrival_ERank/lifeAllTimeDto.getLtPeriodicFrequency());
                    // 2. 정밀점검 횟수
                    double close = Math.floor(arrival_ERank/lifeAllTimeDto.getLtCloseFrequency());
                    // 3. 정밀안전진단 횟수
                    double safety;
                    if(arrival_ERank<15){
                        safety = 1.0;
                    }else{
                        safety = Math.floor(1+((arrival_ERank-10)/lifeAllTimeDto.getLtSafetyFrequency()));
                    }
                    // 4. 총 점검비용
                    double check_cost = lifeAllTimeDto.getLtPeriodicCost()*ltPeriodicRn*(1-Math.pow(ltPeriodicRn,periodic))/(1-ltPeriodicRn) + lifeAllTimeDto.getLtCloseCost()*ltCloseRn*(1-Math.pow(ltCloseRn,close))/(1-ltCloseRn) + lifeAllTimeDto.getLtSafetyCost()*ltSafetyRn*(1-Math.pow(ltSafetyRn,safety+1))/(1-ltSafetyRn)- lifeAllTimeDto.getLtSafetyCost()*ltSafetyRn;

                    // 총 유지관리비용 = 누적 보수보강비용(discountRateList.get(j)) + 총 점검비용
                    double all_cost = discountRateList.get(step-1)+check_cost;
                    // 원/년 = 총 유지관리비용 / 보수보강후 E등급 도달시기(년)
                    double cost_year = all_cost/arrival_ERank;
                    // 원/년 리스트에 담기 : costYear
                    costYear.add(cost_year);

                }

                // 현행유지관리
                for(int step=1; step<4; step++){ // 3바퀴 고정

                    // 1단계와 2~25단계까지 식이 다른 값들.
                    if(stage==1){
                        repairYear2 = Math.pow((rankList2.get(step - 1) / ltDeterioration2), 0.5); // 보수보강 수행시기(년)
                        performYear2.add(repairYear2); // 보수보강 수행시기(년) 리스트로 저장

                        discountRateList2.add(costRankList2.get(step-1)*1/Math.pow((1+lifeAllTimeDto.getLtDiscountRate()),repairYear2)); // 누적 보수보강비용(원) 리스트로 저장
                    }else{

                        repairYear2 = damageRankYear2+Math.sqrt((rankList2.get(step-1)-damageRank2)/ltDeterioration2); // 보수보강 수행시기(년)
                        performYear2.add(repairYear2); // 보수보강 수행시기(년) 리스트로 저장

                        discountRateList2.add(costRankList2.get(step-1)*1/Math.pow((1+lifeAllTimeDto.getLtDiscountRate()),repairYear2)+discountRate2); // 누적 보수보강비용(원) 리스트로 저장
                    }

                    // 1~25단계까지 식이 같은 값들.

                    // 보수보강후 E등급 도달시기(년)
                    double arrival_ERank = Math.pow( (1-damageRankList2.get(step-1)) / (ltDeterioration2*ltIncrease),0.5)+repairYear2;

                    // 총 점검비용 계산하기
                    // 1. 정기점검 횟수
                    double periodic = Math.floor(arrival_ERank/lifeAllTimeDto.getLtPeriodicFrequency());

                    // 2. 정밀점검 횟수
                    double close = Math.floor(arrival_ERank/lifeAllTimeDto.getLtCloseFrequency());

                    // 3. 정밀안전진단 횟수
                    double safety;
                    if(arrival_ERank<15){
                        safety = 1.0;
                    }else{
                        safety = Math.floor(1+((arrival_ERank-10)/lifeAllTimeDto.getLtSafetyFrequency()));
                    }

                    // 4. 총 점검비용
                    double check_cost2 = lifeAllTimeDto.getLtPeriodicCost()*ltPeriodicRn*(1-Math.pow(ltPeriodicRn,periodic))/(1-ltPeriodicRn) + lifeAllTimeDto.getLtCloseCost()*ltCloseRn*(1-Math.pow(ltCloseRn,close))/(1-ltCloseRn) + lifeAllTimeDto.getLtSafetyCost()*ltSafetyRn*(1-Math.pow(ltSafetyRn,safety+1))/(1-ltSafetyRn)- lifeAllTimeDto.getLtSafetyCost()*ltSafetyRn;

                    // 총 유지관리비용 = 누적 보수보강비용(discountRateList.get(j)) + 총 점검비용
                    double all_cost2 = discountRateList2.get(step-1)+check_cost2;
                    // 원/년 = 총 유지관리비용 / 보수보강후 E등급 도달시기(년)
                    double cost_year2 = all_cost2/arrival_ERank;
                    // 원/년 리스트에 담기 : costYear
                    costYear2.add(cost_year2);

                }

                int thNum = 0;
                Double minVal = Collections.min(costYear);
                for (int x=0; x<4; x++) {
                    if (costYear.get(x).equals(minVal)) {
                        thNum = x;
                        break;
                    }
                }

                int thNum2 = 0;
                Double minVal2 = Collections.min(costYear2);
                for (int x=0; x<3; x++) {
                    if (costYear2.get(x).equals(minVal2)) {
                        thNum2 = x;
                        break;
                    }
                }

                if(stage!=1){
                    pointViewEarly = damageRank; // 시점 초기값(b) 입력
                    pointViewEarly2 = damageRank2; // 시점 초기값(b) 입력
                }

                // 선제적 유지관리 1~25단계까지 변하지 않는 값
                changeRankNum = rankList.get(thNum); // 전 단계 수행전등급
                damageRank = damageRankList.get(thNum); // 보수보강 수행후등급

                // 현행 유지관리 1~25단계까지 변하지 않는 값
                changeRankNum2 = rankList2.get(thNum2); // 전 단계 수행전등급
                damageRank2 = damageRankList2.get(thNum2); // 보수보강 수행후등급

                if(stage!=1){
                    pointView = damageRankYear; // 시점 입력
                    pointView2 = damageRankYear2; // 시점 입력
                }

                // 1~25단계까지 변하는 값
                damageRankYear = performYear.get(thNum); // 보수보강 수행시기(년)
                discountRate = discountRateList.get(thNum); // [할인율 적용] 누적 보수보강비용
                costYearVal = costYear.get(thNum); // 원년

                // 1~25단계까지 변하는 값
                damageRankYear2 = performYear2.get(thNum2); // 보수보강 수행시기(년)
                discountRate2 = discountRateList2.get(thNum2); // [할인율 적용] 누적 보수보강비용

                double periodicCount = Math.floor(damageRankYear/lifeAllTimeDto.getLtPeriodicFrequency()); // 정기점검 횟수
                double closeCount = Math.floor(damageRankYear/lifeAllTimeDto.getLtCloseFrequency()); // 정밀점검 횟수
                double safetyCount; // 정밀안전진단 횟수
                if(costYearVal<15){
                    safetyCount = 1.0;
                }else{
                    safetyCount = 1+Math.floor((damageRankYear-10)/lifeAllTimeDto.getLtSafetyFrequency());
                }

                double checkCostStep1 = lifeAllTimeDto.getLtPeriodicCost()*ltPeriodicRn*(1-Math.pow(ltPeriodicRn,periodicCount))/(1-ltPeriodicRn);
                double checkCostStep2 = lifeAllTimeDto.getLtCloseCost()*ltCloseRn*(1-Math.pow(ltCloseRn,closeCount))/(1-ltCloseRn);
                double checkCostStep3 = lifeAllTimeDto.getLtSafetyCost()*ltSafetyRn*(1-Math.pow(ltSafetyRn,safetyCount))/(1-ltSafetyRn);
                double checkCostStep4 = lifeAllTimeDto.getLtSafetyCost()*ltSafetyRn;
                double checkCost = checkCostStep1+checkCostStep2+checkCostStep3-checkCostStep4;
                double managementCost = discountRate+checkCost;

                periodicCountList.add(periodicCount);
                closeCountList.add(closeCount);
                safetyCountList.add(safetyCount);
                checkCostList.add(checkCost);
                managementCostList.add(managementCost);

                damageRankYearList.add(damageRankYear);
                ltDeteriorationList.add(ltDeterioration);
                pointViewEarlyList.add(pointViewEarly);
                pointViewList.add(pointView);

                damageRankYearList2.add(damageRankYear2);
                ltDeteriorationList2.add(ltDeterioration2);
                pointViewEarlyList2.add(pointViewEarly2);
                pointViewList2.add(pointView2);

            }



            // 1단계 D.I 구하는 식 -> 적용된기울기 *(공용연수^2)
            // 2단계부터 적용하기. D.I 구하는 식 -> ( 공용연수 - 해당단계의 시점값)^2 * 적용된기울기 + 시점 초기값
//            log.info("선제적 유지관리 보수보강수행시기 리스트 : "+damageRankYearList);
//            log.info("선제적 유지관리 적용된기울기 리스트 : "+ltDeteriorationList);
//            log.info("선제적 유지관리 시점 초기값 리스트 : "+pointViewEarlyList);
//            log.info("선제적 유지관리 시점 리스트 : "+pointViewList);
//            log.info("");
//            log.info("현행 유지관리 보수보강수행시기 리스트 : "+damageRankYearList2);
//            log.info("현행 유지관리 적용된기울기 리스트 : "+ltDeteriorationList2);
//            log.info("현행 유지관리 시점 초기값 리스트 : "+pointViewEarlyList2);
//            log.info("현행 유지관리 시점 리스트 : "+pointViewList2);

            // 선제적 유지관리 기울기
            double preemptive;
            // 무조치의 적용된 기울기
            double noAction;

            // 현행 유지관리 기울기
            double current;


            double noMaintananceltDeterioration = absenceDto.getLtDeterioration();

            // 선제적 유지관리
            int state = 0;
            int change = 0;
            double startDamageRankYear = Math.floor(damageRankYearList.get(change)*10)/10.0; // 보수보강수행시기
            double startLtDeterioration = ltDeteriorationList.get(change); // 적용된기울기
            double startPointViewEarly = pointViewEarlyList.get(change); // 시좀초기값
            double startPointViewList = pointViewList.get(change); // 시점

            // 현행 유지관리
            int state2 = 0;
            int change2 = 0;
            double startDamageRankYear2 = Math.floor(damageRankYearList2.get(change2)*10)/10.0; // 보수보강수행시기
            double startLtDeterioration2 = ltDeteriorationList2.get(change2); // 적용된기울기
            double startPointViewEarly2 = pointViewEarlyList2.get(change2); // 시좀초기값
            double startPointViewList2 = pointViewList2.get(change2); // 시점


            // 차트데이터 값 for문 알고리즘 천번돌아야됨.
            for(double year=0; year<1001; year++){
                // 공용연수 0.1년 단위
                double publicYear = year/10;

                // 그래프 선제적유지관리, 현행유지관리 데이터 계산
                if(year == 0){
                    preemptive = 1.0;
                    current = 1.0;
                }else {
                    // 선제적 유지관리 값
                    if(change==0){
                        if (publicYear != startDamageRankYear) {
                            preemptive = 1-startLtDeterioration * (Math.pow(publicYear, 2));
                        }else{
                            preemptive = 1-startLtDeterioration * (Math.pow(publicYear, 2));
                            change++;
                            startDamageRankYear = Math.floor(damageRankYearList.get(change)*10)/10.0; // 보수보강수행시기
                            startLtDeterioration = ltDeteriorationList.get(change);
                            startPointViewEarly = pointViewEarlyList.get(change);
                            startPointViewList = pointViewList.get(change);
                        }
                    }else{
                        if(state == 0){
                            double publicPoint = Math.pow(publicYear-startPointViewList,2);
                            double resultdi = publicPoint*startLtDeterioration+startPointViewEarly;
                            preemptive = 1-resultdi;
                            if (publicYear == startDamageRankYear) {
                                change++;
                                startDamageRankYear = Math.floor(damageRankYearList.get(change) * 10) / 10.0; // 보수보강수행시기
                                startLtDeterioration = ltDeteriorationList.get(change);
                                startPointViewEarly = pointViewEarlyList.get(change);
                                startPointViewList = pointViewList.get(change);
                            }

                            if(Math.floor(preemptive*100000)/100000.0 == 0.99999){
//                                log.info("");
//                                log.info("여기서부터 종료시점이다");
//                                log.info("");
                                state++;
                                preemptive = 0.0;
                            }
                        }else{
                            preemptive = 0.0;
                        }
                    }

                    // 현행 유지관리 값
                    if(change2==0){
                        if (publicYear != startDamageRankYear2) {
                            current = 1-startLtDeterioration2 * (Math.pow(publicYear, 2));
                        }else{
                            current = 1-startLtDeterioration2 * (Math.pow(publicYear, 2));
                            change2++;
                            startDamageRankYear2 = Math.floor(damageRankYearList2.get(change2)*10)/10.0; // 보수보강수행시기
                            startLtDeterioration2 = ltDeteriorationList2.get(change2);
                            startPointViewEarly2 = pointViewEarlyList2.get(change2);
                            startPointViewList2 = pointViewList2.get(change2);
                        }
                    }else{
                        if(state2 == 0){
                            double publicPoint = Math.pow(publicYear-startPointViewList2,2);
                            double resultdi = publicPoint*startLtDeterioration2+startPointViewEarly2;
                            current = 1-resultdi;
                            if (publicYear == startDamageRankYear2) {
                                change2++;
                                startDamageRankYear2 = Math.floor(damageRankYearList2.get(change2) * 10) / 10.0; // 보수보강수행시기
                                startLtDeterioration2 = ltDeteriorationList2.get(change2);
                                startPointViewEarly2 = pointViewEarlyList2.get(change2);
                                startPointViewList2 = pointViewList2.get(change2);
                            }

                            if(Math.floor(current*100000)/100000.0 == 0.99999){
//                                log.info("");
//                                log.info("여기서부터 종료시점이다");
//                                log.info("");
                                state2++;
                                current = 0.0;
                            }
                        }else{
                            current = 0.0;
                        }
                    }

                }

                // 그래프로 보낼 데이터 뽑기 여기서 시작
                chartData  = new HashMap<>();

                // 무조치시 값
                noAction = noMaintananceltDeterioration*Math.pow(publicYear,2);
                noAction = 1-noAction;
                if(noAction<0){
                    noAction = 0.0;
                }

                chartData.put("publicYear", publicYear);
                chartData.put("preemptive", Math.floor(preemptive*1000)/1000.0);
                chartData.put("noAction", Math.floor(noAction*1000)/1000.0);
                chartData.put("current", Math.floor(current*1000)/1000.0);

                chartData.put("aRank", Math.floor((1-0.1)*10)/10.0);
                chartData.put("bRank", Math.floor((1-ltDamageBRank)*10)/10.0);
                chartData.put("cRank", Math.floor((1-ltDamageCRank)*10)/10.0);
                chartData.put("dRank", Math.floor((1-ltDamageDRank)*10)/10.0);

                chartDataList.add(chartData);
            }

//            log.info("periodicCountList : "+periodicCountList);
//            log.info("closeCountList : "+closeCountList);
//            log.info("safetyCountList : "+safetyCountList);
//            log.info("checkCostList : "+checkCostList);
//            log.info("managementCostList : "+managementCostList);

            data.put("periodicCountList",periodicCountList);
            data.put("closeCountList",closeCountList);
            data.put("safetyCountList",safetyCountList);
            data.put("checkCostList",checkCostList);
            data.put("managementCostList",managementCostList);
            data.put("ltAbsenceName",absenceDto.getLtAbsenceName());

//            log.info("차트 테스트 : "+chartDataList);
//            log.info("차트 데이터 길이 : "+chartDataList.size());
            data.put("chartDataList",chartDataList);

        }else{
            return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE006.getCode(),ResponseErrorCode.NDE006.getDesc(),null,null));
        }

        data.put("lifeAllTimeDto",lifeAllTimeDto);

        return ResponseEntity.ok(res.dataSendSuccess(data));

    }













}
