package com.broadwave.backend.lifetime;

import com.broadwave.backend.common.AjaxResponse;
import com.broadwave.backend.common.ResponseErrorCode;
import com.broadwave.backend.lifetime.absence.AbsenceDto;
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

    @Autowired
    public LifeAllTimeRestController(ModelMapper modelMapper, AbsenceService absenceService, LifeAllTimeService lifeAllTimeService) {
        this.modelMapper = modelMapper;
        this.absenceService = absenceService;
        this.lifeAllTimeService = lifeAllTimeService;
    }

    // NEWDEAL 생애주기 의사결정 지원 서비스 전체부분 저장
    @PostMapping("/save")
    public ResponseEntity<Map<String,Object>> save(@ModelAttribute LifeAllTimeMapperDto lifeAllTimeMapperDto,HttpServletRequest request) {

        log.info("save 호출성공");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
        String insert_id = request.getHeader("insert_id");
        log.info("JWT_AccessToken : "+JWT_AccessToken);
        log.info("insert_id : "+insert_id);

        LifeAllTime lifeAllTime = modelMapper.map(lifeAllTimeMapperDto, LifeAllTime.class);
        lifeAllTime.setInsertDateTime(LocalDateTime.now());
        lifeAllTime.setInsert_id(insert_id);
        log.info("lifeAllTime : "+lifeAllTime);

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
            double ltDamageBRank = lifeAllTimeDto.getLtDamageBRank();
            double ltDamageCRank = lifeAllTimeDto.getLtDamageCRank();
            double ltDamageDRank = lifeAllTimeDto.getLtDamageDRank();
            double ltDamageERank = lifeAllTimeDto.getLtDamageERank();
            rankList.add(ltDamageBRank);
            rankList.add(ltDamageCRank);
            rankList.add(ltDamageDRank);
            rankList.add(ltDamageERank);

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
            double rankE = ltDamageERank-(ltStatusTwo*Math.pow(ltDamageERank,2)+ltStatusOne*ltDamageERank+ltStatusNum);

            List<Double> damageRankList = new ArrayList<>();
            damageRankList.add(rankB);
            damageRankList.add(rankC);
            damageRankList.add(rankD);
            damageRankList.add(rankE);
            for(int i=0; i<damageRankList.size(); i++){
                if(damageRankList.get(i)<0){
                    damageRankList.remove(damageRankList.get(i));
                    damageRankList.add(0.0);
                }
            }

            double costB = (ltRemunerationThree*Math.pow(ltDamageBRank,3)+ltRemunerationTwo*Math.pow(ltDamageBRank,2)+ltRemunerationOne*ltDamageBRank+ltRemunerationNum)*ltAllVolume;
            double costC =(ltRemunerationThree*Math.pow(ltDamageCRank,3)+ltRemunerationTwo*Math.pow(ltDamageCRank,2)+ltRemunerationOne*ltDamageCRank+ltRemunerationNum)*ltAllVolume;
            double costD = (ltRemunerationThree*Math.pow(ltDamageDRank,3)+ltRemunerationTwo*Math.pow(ltDamageDRank,2)+ltRemunerationOne*ltDamageDRank+ltRemunerationNum)*ltAllVolume;
            double costE = (ltRemunerationThree*Math.pow(ltDamageERank,3)+ltRemunerationTwo*Math.pow(ltDamageERank,2)+ltRemunerationOne*ltDamageERank+ltRemunerationNum)*ltAllVolume;

            List<Double> costRankList = new ArrayList<>();
            costRankList.add(costB);
            costRankList.add(costC);
            costRankList.add(costD);
            costRankList.add(costE);

            double discount = 1/(1+ltDiscountRate);

            double ltIncrease = 1+lifeAllTimeDto.getLtIncrease();

            double ltPeriodicRn = Math.pow(discount,lifeAllTimeDto.getLtPeriodicFrequency());
            double ltCloseRn = Math.pow(discount,lifeAllTimeDto.getLtCloseFrequency());
            double ltSafetyRn = Math.pow(discount,lifeAllTimeDto.getLtSafetyFrequency());

            // DB 데이터
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



            //person의 JSON정보를 담을 Array 선언
            List<HashMap<String,Double>> chartDataList = new ArrayList<>();
            HashMap<String,Double> chartData;
            for(int stage=1; stage<26; stage++){ // 25바퀴 고정

                List<Double> performYear = new ArrayList<>(); // 보수보강수행시기(년) 리스트
                List<Double> discountRateList = new ArrayList<>(); // 할인율적용 누적 보수보강비용 리스트
                List<Double> costYear = new ArrayList<>(); // 원/년 리스트
                if(stage==1){
                    ltDeterioration = absenceDto.getLtDeterioration();
                }else{
                    if(changeRankNum == 1.0){
                        ltDeterioration = absenceDto.getLtDeterioration();
                    }else{
                        ltDeterioration = absenceDto.getLtDeterioration()*(Math.pow(ltIncrease,stage-1));
                    }
                }

                log.info(stage+"단계 평균열화율  : "+ltDeterioration);

                for(int step=1; step<5; step++){ // 5바퀴 고정

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

//                    log.info(i+"단계 arrival_ERank : "+arrival_ERank);
//                    log.info(i+"단계 periodic : "+periodic);
//                    log.info(i+"단계 close : "+close);
//                    log.info(i+"단계 safety : "+safety);
//                    log.info(i+"단계 check_cost : "+check_cost);
//                    log.info(i+"단계 all_cost : "+all_cost);
//                    log.info(i+"단계 cost_year : "+cost_year);
//                    log.info("@@@@@@@@@@@@@@@@@");
                }

                int thNum = 0;
                Double minVal = Collections.min(costYear);
                for (int x=0; x<4; x++) {
                    if (costYear.get(x).equals(minVal)) {
                        thNum = x;
                        break;
                    }
                }
                log.info(stage+"단계 보수보강수행시기(년)  : "+performYear);
                log.info(stage+"단계 누적 보수보강비용(원) : "+discountRateList);
                log.info(stage+"단계 원/년 : "+costYear);
                System.out.println();

                // 1~25단계까지 변하지 않는 값
                changeRankNum = rankList.get(thNum); // 전 단계 수행전등급
                damageRank = damageRankList.get(thNum); // 보수보강 수행후등급

                // 1~25단계까지 변하는 값
                damageRankYear = performYear.get(thNum); // 보수보강 수행시기(년)
                discountRate = discountRateList.get(thNum); // [할인율 적용] 누적 보수보강비용
                costYearVal = costYear.get(thNum); // 원년

                log.info("선택된 값의 보수보강 수행전등급 changeRankNum : "+changeRankNum);
                log.info("선택된 값의 보수보강 수행후등급 damageRank : "+damageRank);
                log.info("선택된 값의 보수보강수행시기(년) damageRankYear : "+damageRankYear);
                log.info("선택된 값의 누적 보수보강비용(원) discountRate : "+discountRate);
                log.info("");

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

                log.info("periodicCount : "+periodicCount);
                log.info("closeCount : "+closeCount);
                log.info("safetyCount : "+safetyCount);
                log.info("checkCost : "+checkCost);
                log.info("managementCost : "+managementCost);

                periodicCountList.add(periodicCount);
                closeCountList.add(closeCount);
                safetyCountList.add(safetyCount);
                checkCostList.add(checkCost);
                managementCostList.add(managementCost);

                // 그래프로 보낼 데이터 뽑기 여기서 시작
                // 공용연수(x축)은 0년~100년까지 하되 0년,0.5년,1년,1.5년~99.5년,100년으로 한다. 너무 많을시 줄이기.
                chartData  = new HashMap<>();

                chartData.put("category", 10.0);
                chartData.put("maintenance",0.98);
                chartData.put("noAction", 0.89);
                chartDataList.add(chartData);

            }

            log.info("periodicCountList : "+periodicCountList);
            log.info("closeCountList : "+closeCountList);
            log.info("safetyCountList : "+safetyCountList);
            log.info("checkCostList : "+checkCostList);
            log.info("managementCostList : "+managementCostList);

            data.put("periodicCountList",periodicCountList);
            data.put("closeCountList",closeCountList);
            data.put("safetyCountList",safetyCountList);
            data.put("checkCostList",checkCostList);
            data.put("managementCostList",managementCostList);
            data.put("ltAbsenceName",absenceDto.getLtAbsenceName());

            log.info("차트 테스트 : "+chartDataList);
            log.info("차트 데이터 길이 : "+chartDataList.size());
            data.put("chartDataList",chartDataList);

        }else{
            return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE006.getCode(),ResponseErrorCode.NDE006.getDesc(),null,null));
        }

        data.put("lifeAllTimeDto",lifeAllTimeDto);

        return ResponseEntity.ok(res.dataSendSuccess(data));

    }













}
