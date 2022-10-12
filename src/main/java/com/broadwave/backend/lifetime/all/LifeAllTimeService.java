package com.broadwave.backend.lifetime.all;

import com.broadwave.backend.common.AjaxResponse;
import com.broadwave.backend.common.ResponseErrorCode;
import com.broadwave.backend.keygenerate.KeyGenerateService;
import com.broadwave.backend.lifetime.absence.AbsenceDto;
import com.broadwave.backend.lifetime.absence.AbsenceService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author Minkyu
 * Date : 2021-08-06
 * Time :
 * Remark : NewDeal LifeAllTime Service
*/
@Slf4j
@Service
public class LifeAllTimeService {

    private final ModelMapper modelMapper;
    private final AbsenceService absenceService;
    private final KeyGenerateService keyGenerateService;
    private final LiftAllTimeRepository liftAllTimeRepository;
    private final LifeAllTimeRepositoryCustom lifeAllTimeRepositoryCustom;

    @Autowired
    public LifeAllTimeService(ModelMapper modelMapper, AbsenceService absenceService, KeyGenerateService keyGenerateService,
                              LiftAllTimeRepository liftAllTimeRepository, LifeAllTimeRepositoryCustom lifeAllTimeRepositoryCustom) {
        this.modelMapper = modelMapper;
        this.absenceService = absenceService;
        this.keyGenerateService = keyGenerateService;
        this.liftAllTimeRepository = liftAllTimeRepository;
        this.lifeAllTimeRepositoryCustom = lifeAllTimeRepositoryCustom;
    }

    public ResponseEntity<Map<String,Object>> save(LifeAllTimeMapperDto lifeAllTimeMapperDto, HttpServletRequest request){

        log.info("save 호출성공");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
        String insert_id = request.getHeader("insert_id");

//        log.info("JWT_AccessToken : "+JWT_AccessToken);
//        log.info("insert_id : "+insert_id);

        LifeAllTime lifeAllTime = modelMapper.map(lifeAllTimeMapperDto, LifeAllTime.class);
        if(lifeAllTimeMapperDto.getLtId() != 0L){

            Optional<LifeAllTime> optionalLifeAllTime = liftAllTimeRepository.findById(lifeAllTimeMapperDto.getLtId());
            if(optionalLifeAllTime.isPresent()){
                lifeAllTime.setId(optionalLifeAllTime.get().getId());
                lifeAllTime.setLtBridgeCode(optionalLifeAllTime.get().getLtBridgeCode());
                lifeAllTime.setInsert_id(optionalLifeAllTime.get().getInsert_id());
                lifeAllTime.setInsertDateTime(optionalLifeAllTime.get().getInsertDateTime());
                lifeAllTime.setModify_id(insert_id);
                lifeAllTime.setModifyDateTime(LocalDateTime.now());
                liftAllTimeRepository.save(lifeAllTime);
                data.put("getId", optionalLifeAllTime.get().getId());
            }
        }else{
//            log.info("일련번호 생성");
            Date now = new Date();
            SimpleDateFormat yyMM = new SimpleDateFormat("yyMM");
            String newAutoNum = keyGenerateService.keyGenerate("nd_lt_all_input", yyMM.format(now), insert_id);
            lifeAllTime.setLtBridgeCode(newAutoNum);
            lifeAllTime.setInsertDateTime(LocalDateTime.now());
            lifeAllTime.setInsert_id(insert_id);
            log.info("lifeAllTime : "+lifeAllTime);

            LifeAllTime allTime = liftAllTimeRepository.save(lifeAllTime);

            data.put("getId", allTime.getId());
        }

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    public ResponseEntity<Map<String, Object>> output(Long id, HttpServletRequest request) {

        log.info("output 호출성공");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
        log.info("JWT_AccessToken : "+JWT_AccessToken);

        LifeAllTimeDto lifeAllTimeDto = lifeAllTimeRepositoryCustom.findById(id);

        if(lifeAllTimeDto.getLtAbsenceCode() != null){
            AbsenceDto absenceDto = absenceService.findByLtAbsenceCode(lifeAllTimeDto.getLtAbsenceCode());

            double ltDiscountRate = lifeAllTimeDto.getLtDiscountRate();
            double volume = lifeAllTimeDto.getLtAllLength() * lifeAllTimeDto.getLtAllArea(); // 전체물량 = volume

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
            double rankE = ltDamageERank-(ltStatusTwo*Math.pow(ltDamageERank,2)+ltStatusOne*ltDamageERank+ltStatusNum);
//            double rankE = 0.0; // 전면교체로 가정하여 교체 신성교량이 되므로, 보수보강 수행 후 E등급 손상지수는 0으로 고정함.

            List<Double> damageScoreList = new ArrayList<>();
            List<String> damageRankListAfter = new ArrayList<>();
            damageScoreList.add(rankB);
            damageScoreList.add(rankC);
            damageScoreList.add(rankD);
            damageScoreList.add(rankE);
            for(int i=0; i<damageScoreList.size(); i++){
                double score = damageScoreList.get(i);
                if(score < 0.13){
                    damageRankListAfter.add("A");
                }else if(score < 0.26){
                    damageRankListAfter.add("B");
                }else if(score < 0.49){
                    damageRankListAfter.add("C");
                }else if(score < 0.79){
                    damageRankListAfter.add("D");
                }else if(score < 1){
                    damageRankListAfter.add("E");
                }
            }

            List<Double> damageScoreList2 = new ArrayList<>();
            damageScoreList2.add(rankC);
            damageScoreList2.add(rankD);
            damageScoreList2.add(rankE);

            List<String> costRank = new ArrayList<>(); // 보수보강 수행전등급 랭크(알파벳)
            String costRankValue;
            double costB = (ltRemunerationThree*Math.pow(ltDamageBRank,3)+ltRemunerationTwo*Math.pow(ltDamageBRank,2)+ltRemunerationOne*ltDamageBRank+ltRemunerationNum)*volume;
            double costC =(ltRemunerationThree*Math.pow(ltDamageCRank,3)+ltRemunerationTwo*Math.pow(ltDamageCRank,2)+ltRemunerationOne*ltDamageCRank+ltRemunerationNum)*volume;
            double costD = (ltRemunerationThree*Math.pow(ltDamageDRank,3)+ltRemunerationTwo*Math.pow(ltDamageDRank,2)+ltRemunerationOne*ltDamageDRank+ltRemunerationNum)*volume;
            double costE = (ltRemunerationThree*Math.pow(ltDamageERank,3)+ltRemunerationTwo*Math.pow(ltDamageERank,2)+ltRemunerationOne*ltDamageERank+ltRemunerationNum)*volume;

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

            double ltPeriodicRn = 0.0;
            double ltPeriodicFrequency = Math.floor(lifeAllTimeDto.getLtPeriodicYear()/lifeAllTimeDto.getLtPeriodicNum()*10)/10;
            if(lifeAllTimeDto.getLtPeriodicNum() != 0){
                ltPeriodicRn = Math.pow(discount,ltPeriodicFrequency);
            }
            double ltCloseRn = 0.0;
            double ltCloseFrequency = Math.floor(lifeAllTimeDto.getLtCloseYear()/lifeAllTimeDto.getLtCloseNum()*10)/10;
            if(lifeAllTimeDto.getLtCloseNum() != 0){
                ltCloseRn = Math.pow(discount,ltCloseFrequency);
            }
            double ltSafetyRn = 0.0;
            double ltSafetyFrequency = Math.floor(lifeAllTimeDto.getLtSafetyYear()/lifeAllTimeDto.getLtSafetyNum()*10)/10;
            if(lifeAllTimeDto.getLtSafetyNum() != 0){
                ltSafetyRn = Math.pow(discount,ltSafetyFrequency);
            }

            // DB 데이터
            System.out.println();
            log.info("조회한 전체부분 데이터 : "+lifeAllTimeDto);
            log.info("부재별 수치 : "+absenceDto);
            System.out.println();

            // 단계마다 변하지 않은 값
            log.info("******************************************************************************************************************");
            log.info("보수보강 수행전 등급 손상지수 리스트 : "+rankList);
            log.info("보수보강 수행후 등급 손상지수 리스트 : "+damageScoreList);
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
            List<Double> managementCostList = new ArrayList<>(); // 선제적 총 유지관리비용 리스트
            List<Double> managementCostList2 = new ArrayList<>(); // 현행 총 유지관리비용 리스트

            List<Double> costDownEffect = new ArrayList<>(); // 비용 절감효과 리스트

            double ltDeterioration; // 단계별 평균열화율
            double changeRankNum = 0; // 전 단계 수행전등급
            double damageRank = 0; // 보수보강 수행후등급
            double damageRankYear = 0; // 보수보강 수행시기(년)
            double discountRate = 0; // 할인율 적용 보수보강비용
            double repairYear; // 보수보강수행시기
            double costYearVal; // 선제적 원년
            double costYearVal2; // 현행 원년

            double completionYear = Double.parseDouble(lifeAllTimeDto.getLtAllCompletionDate().substring(0,4)); // 준공 년도
            String completionMonth = lifeAllTimeDto.getLtAllCompletionDate().substring(4,7); // 준공 월

            List<Double> damageRankYearList = new ArrayList<>(); // 선제적 유지관리 보수보강수행시기 리스트
            List<Double> ltDeteriorationList = new ArrayList<>(); // 선제적 유지관리  적용된 기울기 리스트
            List<Double> pointViewEarlyList = new ArrayList<>(); // 선제적 유지관리 시점 초기값(b) 리스트
            List<Double> pointViewList = new ArrayList<>(); // 선제적 유지관리 시점 리스트
            List<String> performCompletion = new ArrayList<>(); // 실제 보수보강 수행시기(년) 리스트(준공년도 + 보수보강수행시기년도)

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


            //차트의 JSON정보를 담을 Array 선언
            List<HashMap<String,Object>> chartDataList = new ArrayList<>();
            HashMap<String,Object> chartData;

            List<Double> discountAccumulateList = new ArrayList<>(); // 선행유지관리 할인율적용 자바스크립트 누적 보수보강비용 리스트

            for(int stage=1; stage<lifeAllTimeDto.getLtAllStage()+1; stage++){ // 25바퀴 고정 -> 6/24수정 ltAllStage 입력값의 따라 다름, 범위 : 1~25단계

                List<Double> discountRateList = new ArrayList<>(); // 선행유지관리 할인율적용 누적 보수보강비용 리스트
                List<Double> performYear = new ArrayList<>(); // 선행유지관리 보수보강수행시기(년) 리스트
                List<Double> costYear = new ArrayList<>(); // 선행유지관리 원/년 리스트

                List<Double> discountRateList2 = new ArrayList<>(); // 현행유지관리 할인율적용 누적 보수보강비용 리스트
                List<Double> performYear2 = new ArrayList<>(); // 현행유지관리 현행유지관리 보수보강수행시기(년) 리스트
                List<Double> costYear2 = new ArrayList<>(); // 현행유지관리 원/년 리스트

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
                    double arrival_ERank = Math.pow( (1-damageScoreList.get(step-1)) / (ltDeterioration*ltIncrease),0.5)+repairYear;

                    // 총 점검비용 계산하기
                    // 1. 정기점검 횟수
                    double periodic;
                    double periodicValue = 0.0;
                    if(lifeAllTimeDto.getLtPeriodicNum() != 0 && ltPeriodicRn == 0.0) {
                        periodic = Math.floor(arrival_ERank/(lifeAllTimeDto.getLtPeriodicYear()/lifeAllTimeDto.getLtPeriodicNum()));
                        periodicValue = lifeAllTimeDto.getLtPeriodicCost()*ltPeriodicRn*(1-Math.pow(ltPeriodicRn,periodic))/(1-ltPeriodicRn);
                    }
                    // 2. 정밀점검 횟수
                    double close;
                    double closeValue = 0.0;
                    if(lifeAllTimeDto.getLtCloseNum() != 0 && ltCloseRn == 0.0) {
                        close = Math.floor(arrival_ERank/(lifeAllTimeDto.getLtCloseYear()/lifeAllTimeDto.getLtCloseNum()));
                        closeValue = lifeAllTimeDto.getLtCloseCost()*ltCloseRn*(1-Math.pow(ltCloseRn,close))/(1-ltCloseRn);
                    }
                    // 3. 정밀안전진단 횟수
                    double safety;
                    double safetyValue = 0.0;
                    if(lifeAllTimeDto.getLtSafetyNum() != 0 && ltSafetyRn == 0.0) {
                        if(arrival_ERank<15){
                            safety = 1.0;
                        }else{
                            safety = Math.floor(1+(arrival_ERank-10)/(lifeAllTimeDto.getLtSafetyYear()/lifeAllTimeDto.getLtSafetyNum()));
                        }
                        safetyValue = lifeAllTimeDto.getLtSafetyCost()*ltSafetyRn*(1-Math.pow(ltSafetyRn,safety+1))/(1-ltSafetyRn)- lifeAllTimeDto.getLtSafetyCost()*ltSafetyRn;
                    }

                    // 4. 총 점검비용
                    double check_cost = periodicValue + closeValue + safetyValue;

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
                    double arrival_ERank = Math.pow( (1-damageScoreList2.get(step-1)) / (ltDeterioration2*ltIncrease),0.5)+repairYear2;

                    // 총 점검비용 계산하기
                    // 1. 정기점검 횟수
                    double periodic;
                    double periodicValue = 0.0;
                    if(lifeAllTimeDto.getLtPeriodicNum() != 0 && ltPeriodicRn == 0.0) {
                        periodic = Math.floor(arrival_ERank/(lifeAllTimeDto.getLtPeriodicYear()/lifeAllTimeDto.getLtPeriodicNum()));
                        periodicValue = lifeAllTimeDto.getLtPeriodicCost()*ltPeriodicRn*(1-Math.pow(ltPeriodicRn,periodic))/(1-ltPeriodicRn);
                    }

                    // 2. 정밀점검 횟수
                    double close;
                    double closeValue = 0.0;
                    if(lifeAllTimeDto.getLtPeriodicNum() != 0 && ltPeriodicRn == 0.0) {
                        close = Math.floor(arrival_ERank/(lifeAllTimeDto.getLtCloseYear()/lifeAllTimeDto.getLtCloseNum()));
                        closeValue =  lifeAllTimeDto.getLtCloseCost()*ltCloseRn*(1-Math.pow(ltCloseRn,close))/(1-ltCloseRn);
                    }

                    // 3. 정밀안전진단 횟수
                    double safety;
                    double safetyValue = 0.0;
                    if(lifeAllTimeDto.getLtSafetyNum() != 0 && ltSafetyRn == 0.0) {
                        if(arrival_ERank<15){
                            safety = 1.0;
                        }else{
                            safety = Math.floor(1+((arrival_ERank-10)/(lifeAllTimeDto.getLtSafetyYear()/lifeAllTimeDto.getLtSafetyNum())));
                        }
                        safetyValue = lifeAllTimeDto.getLtSafetyCost()*ltSafetyRn*(1-Math.pow(ltSafetyRn,safety+1))/(1-ltSafetyRn)- lifeAllTimeDto.getLtSafetyCost()*ltSafetyRn;
                    }

                    // 4. 총 점검비용
                    double check_cost2 = periodicValue + closeValue + safetyValue;

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
                damageRank = damageScoreList.get(thNum); // 보수보강 수행후등급

                if(thNum==0){
                    costRankValue = "B";
                }else if(thNum==1){
                    costRankValue = "C";
                }else if(thNum==2){
                    costRankValue = "D";
                }else{
                    costRankValue = "E";
                }
                costRank.add(costRankValue);

                // 현행 유지관리 1~25단계까지 변하지 않는 값
                changeRankNum2 = rankList2.get(thNum2); // 전 단계 수행전등급
                damageRank2 = damageScoreList2.get(thNum2); // 보수보강 수행후등급

                if(stage!=1){
                    pointView = damageRankYear; // 시점 입력
                    pointView2 = damageRankYear2; // 시점 입력
                }

                // 1~25단계까지 변하는 값
                damageRankYear = performYear.get(thNum); // 선제적 보수보강 수행시기(년)
                discountRate = discountRateList.get(thNum); // 선제적 [할인율 적용] 누적 보수보강비용
                discountAccumulateList.add(discountRate);// [할인율 적용] 누적 보수보강비용 리스트

                costYearVal = costYear.get(thNum); // 선제적 원/년

                // 1~25단계까지 변하는 값
                damageRankYear2 = performYear2.get(thNum2); // 현행 보수보강 수행시기(년)
                discountRate2 = discountRateList2.get(thNum2); // 현행 [할인율 적용] 누적 보수보강비용

                costYearVal2 = costYear2.get(thNum2);;  // 현행 원/년

                double periodicCount = Math.floor(damageRankYear/ltPeriodicFrequency); // 선제적 정기점검 횟수
                double closeCount = Math.floor(damageRankYear/ltCloseFrequency); // 선제적 정밀점검 횟수
                double safetyCount; // 선제적 정밀안전진단 횟수
                if(costYearVal<15){
                    safetyCount = 1.0;
                }else{
                    safetyCount = 1+Math.floor((damageRankYear-10)/ltSafetyFrequency);
                }

                double periodicCount2 = Math.floor(damageRankYear/ltPeriodicFrequency); // 현행 정기점검 횟수
                double closeCount2 = Math.floor(damageRankYear/ltCloseFrequency); // 현행 정밀점검 횟수
                double safetyCount2; // 현행 정밀안전진단 횟수
                if(costYearVal2<15){
                    safetyCount2 = 1.0;
                }else{
                    safetyCount2 = 1+Math.floor((damageRankYear-10)/ltSafetyFrequency);
                }


                double checkCostStep1_1 = 0.0;
                double checkCostStep1_2 = 0.0;
                if(ltPeriodicRn != 0.0){
                    checkCostStep1_1 = lifeAllTimeDto.getLtPeriodicCost()*ltPeriodicRn*(1-Math.pow(ltPeriodicRn,periodicCount))/(1-ltPeriodicRn); // 선제적 정기점검횟 수
                    checkCostStep1_2 = lifeAllTimeDto.getLtPeriodicCost()*ltPeriodicRn*(1-Math.pow(ltPeriodicRn,periodicCount2))/(1-ltPeriodicRn); // 현행 정기점검횟 수
                }
                double checkCostStep2_1 = 0.0;
                double checkCostStep2_2 = 0.0;
                if(ltCloseRn != 0.0){
                    checkCostStep2_1 = lifeAllTimeDto.getLtCloseCost()*ltCloseRn*(1-Math.pow(ltCloseRn,closeCount))/(1-ltCloseRn); // 선제적 정밀점검 횟수
                    checkCostStep2_2 = lifeAllTimeDto.getLtCloseCost()*ltCloseRn*(1-Math.pow(ltCloseRn,closeCount2))/(1-ltCloseRn); // 현행 정밀점검 횟수
                }

                double checkCostStep3_1 = 0.0;
                double checkCostStep4_1 = 0.0;
                double checkCostStep3_2 = 0.0;
                double checkCostStep4_2 = 0.0;
                if(ltSafetyRn != 0.0){
                    checkCostStep3_1 = lifeAllTimeDto.getLtSafetyCost()*ltSafetyRn*(1-Math.pow(ltSafetyRn,safetyCount))/(1-ltSafetyRn); // 선제적 정밀안전진단 횟수
                    checkCostStep4_1 = lifeAllTimeDto.getLtSafetyCost()*ltSafetyRn;
                    checkCostStep3_2 = lifeAllTimeDto.getLtSafetyCost()*ltSafetyRn*(1-Math.pow(ltSafetyRn,safetyCount2))/(1-ltSafetyRn); // 현행 정밀안전진단 횟수
                    checkCostStep4_2 = lifeAllTimeDto.getLtSafetyCost()*ltSafetyRn;
                }

                double checkCost = checkCostStep1_1+checkCostStep2_1+checkCostStep3_1-checkCostStep4_1; // 선제적 총 점검비용
                double managementCost = discountRate+checkCost; // 선제적 총 유지관리비용(원)

                double checkCost2 = checkCostStep1_2+checkCostStep2_2+checkCostStep3_2-checkCostStep4_2; // 현행 총 점검비용
                double managementCost2 = discountRate2+checkCost2; // 현행 총 유지관리비용(원)

                periodicCountList.add(periodicCount);
                closeCountList.add(closeCount);
                safetyCountList.add(safetyCount);
                checkCostList.add(checkCost);
                managementCostList.add(managementCost);
                managementCostList2.add(managementCost2);

                damageRankYearList.add(damageRankYear);
                ltDeteriorationList.add(ltDeterioration);
                pointViewEarlyList.add(pointViewEarly);
                pointViewList.add(pointView);

                double costDownEffectPercent = ((managementCost2-managementCost)/managementCost2)*100; // 비용절감효과
                costDownEffect.add(costDownEffectPercent); // 비용절감효과 리스트

                int completionYearPlusDamageRankYear = (int)Math.round(completionYear+damageRankYear);
                String change = String.valueOf(completionYearPlusDamageRankYear);
                performCompletion.add(change+completionMonth);  // 실제 보수보강 수행시기(년) 리스트로 저장

                damageRankYearList2.add(damageRankYear2);
                ltDeteriorationList2.add(ltDeterioration2);
                pointViewEarlyList2.add(pointViewEarly2);
                pointViewList2.add(pointView2);

            }
//            log.info("선제 총 유지관리비용 : "+managementCostList);

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

            // 상태지수가 0으로 됬을때 최초 무조치시 공용연수
            double noActionResult = 0;

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

            String ltAllRank = lifeAllTimeDto.getLtAllRank();
            double aRank = Math.floor((1-0.1)*10)/10.0;
//            double bRank = Math.floor((1-ltDamageBRank)*10)/10.0;
//            double cRank = Math.floor((1-ltDamageCRank)*10)/10.0;
//            double dRank = Math.floor((1-ltDamageDRank)*10)/10.0;
            double bRank = 1-ltDamageBRank;
            double cRank = 1-ltDamageCRank;
            double dRank = 1-ltDamageDRank;

            double graphRank;
            if(ltAllRank.equals("A")){
                graphRank = Math.round(aRank*10)/10.0;
            }else if(ltAllRank.equals("B")){
                graphRank = Math.round(bRank*10)/10.0;
            }else if(ltAllRank.equals("C")){
                graphRank = Math.round(cRank*10)/10.0;
            }else{
                graphRank = Math.round(dRank*10)/10.0;
            }

            int chartRankCheck = 0;
            log.info("graphRank : "+graphRank);
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
                    if (change != lifeAllTimeDto.getLtAllStage()-1) {
                        if (change == 0) {
                            if (publicYear != startDamageRankYear) {
                                preemptive = 1 - startLtDeterioration * (Math.pow(publicYear, 2));
                            } else {
                                preemptive = 1 - startLtDeterioration * (Math.pow(publicYear, 2));
                                change++;
                                startDamageRankYear = Math.floor(damageRankYearList.get(change) * 10) / 10.0; // 보수보강수행시기
                                startLtDeterioration = ltDeteriorationList.get(change);
                                startPointViewEarly = pointViewEarlyList.get(change);
                                startPointViewList = pointViewList.get(change);
                            }
                        } else {
                            if (state == 0) {
                                double publicPoint = Math.pow(publicYear - startPointViewList, 2);
                                double resultdi = publicPoint * startLtDeterioration + startPointViewEarly;
                                preemptive = 1 - resultdi;
                                if (publicYear == startDamageRankYear) {
                                    change++;
                                    startDamageRankYear = Math.floor(damageRankYearList.get(change) * 10) / 10.0; // 보수보강수행시기
                                    startLtDeterioration = ltDeteriorationList.get(change);
                                    startPointViewEarly = pointViewEarlyList.get(change);
                                    startPointViewList = pointViewList.get(change);
                                }

                                if (Math.floor(preemptive * 100000) / 100000.0 == 0.99999) {
//                                log.info("");
//                                log.info("여기서부터 종료시점이다");
//                                log.info("");
                                    state++;
                                    preemptive = 0.0;
                                }
                            } else {
                                preemptive = 0.0;
                            }
                        }
                    } else {
                        preemptive = 0.0;
                    }

                    if(preemptive > 1){
                        preemptive = 0.0;
                    }

                    // 현행 유지관리 값
                    if(change2 != lifeAllTimeDto.getLtAllStage()-1){
                        if (change2 == 0) {
                            if (publicYear != startDamageRankYear2) {
                                current = 1 - startLtDeterioration2 * (Math.pow(publicYear, 2));
                            } else {
                                current = 1 - startLtDeterioration2 * (Math.pow(publicYear, 2));
                                change2++;
                                startDamageRankYear2 = Math.floor(damageRankYearList2.get(change2) * 10) / 10.0; // 보수보강수행시기
                                startLtDeterioration2 = ltDeteriorationList2.get(change2);
                                startPointViewEarly2 = pointViewEarlyList2.get(change2);
                                startPointViewList2 = pointViewList2.get(change2);
                            }
                        } else {
                            if (state2 == 0) {
                                double publicPoint = Math.pow(publicYear - startPointViewList2, 2);
                                double resultdi = publicPoint * startLtDeterioration2 + startPointViewEarly2;
                                current = 1 - resultdi;
                                if (publicYear == startDamageRankYear2) {
                                    change2++;
                                    startDamageRankYear2 = Math.floor(damageRankYearList2.get(change2) * 10) / 10.0; // 보수보강수행시기
                                    startLtDeterioration2 = ltDeteriorationList2.get(change2);
                                    startPointViewEarly2 = pointViewEarlyList2.get(change2);
                                    startPointViewList2 = pointViewList2.get(change2);
                                }

                                if (Math.floor(current * 100000) / 100000.0 == 0.99999) {
//                                    log.info("");
//                                    log.info("여기서부터 종료시점이다");
//                                    log.info("");
                                    state2++;
                                    current = 0.0;
                                }
                            } else {
                                current = 0.0;
                            }
                        }
                    }else{
                        current = 0.0;
                    }

                }

                // 그래프로 보낼 데이터 뽑기 여기서 시작
                chartData  = new HashMap<>();

                // 무조치시 값
                noAction = noMaintananceltDeterioration*Math.pow(publicYear,2);
                noAction = 1-noAction;
                if(noAction<0){
                    if(noActionResult == 0){
                        noActionResult = publicYear;
                    }
                    noAction = 0.0;
                }

                if(year == 340){
                    log.info("값1 : "+noAction);
                    log.info("값2 : "+Math.round(noAction*10)/10.0);
                    log.info("값3 : "+Math.round(noAction*100)/100.0);
                    log.info("값4 : "+Math.round(noAction*1000)/1000.0);
                }

                // 현재 상태등급 표시 그리기
                if( Math.round(noAction*100)/100.0 == graphRank && Math.round(noAction*10)/10.0 == graphRank && chartRankCheck == 0) {
                    chartRankCheck++;
                    chartData.put("graphRank", graphRank);
                    chartData.put("bulletColor", "am4core.color('#ee6868')");
                }

                chartData.put("publicYear", publicYear);
                chartData.put("preemptive", Math.floor(preemptive*1000)/1000.0);
                chartData.put("noAction", Math.floor(noAction*1000)/1000.0);
                chartData.put("current", Math.floor(current*1000)/1000.0);

                chartData.put("aRank", aRank);
                chartData.put("bRank", bRank);
                chartData.put("cRank", cRank);
                chartData.put("dRank", dRank);

//                chartData.put("test1", 1.5);
//                chartData.put("test2", 50);
//                chartData.put("test3", 80);

//                if(year==1000){
//                    chartData.put("bulletDisabled",false);
//                }
                chartDataList.add(chartData);
            }

            data.put("aRankValue", Math.floor((1-0.1)*10)/10.0);
            data.put("bRankValue", Math.floor((1-ltDamageBRank)*10)/10.0);
            data.put("cRankValue", Math.floor((1-ltDamageCRank)*10)/10.0);
            data.put("dRankValue", Math.floor((1-ltDamageDRank)*10)/10.0);
//            data.put("test1Value", 1.5);
//            data.put("test2Value", 50);
//            data.put("test3Value", 80);

//            log.info("periodicCountList : "+periodicCountList);
//            log.info("closeCountList : "+closeCountList);
//            log.info("safetyCountList : "+safetyCountList);
//            log.info("checkCostList : "+checkCostList);
//            log.info("managementCostList : "+managementCostList);
//            log.info("costRank : "+costRank);
            data.put("damageScoreList",damageScoreList);
            data.put("damageRankListAfter",damageRankListAfter);
            data.put("costRankList",costRankList);
            data.put("costRank",costRank);
//            data.put("discount",discount);
//            data.put("ltPeriodicRn",ltPeriodicRn);
//            data.put("ltCloseRn",ltCloseRn);
//            data.put("ltSafetyRn",ltSafetyRn);

            data.put("periodicCountList",periodicCountList);
            data.put("closeCountList",closeCountList);
            data.put("safetyCountList",safetyCountList);
            data.put("checkCostList",checkCostList);
            data.put("managementCostList",managementCostList);
            data.put("managementCostList2",managementCostList2);
            data.put("costDownEffect",costDownEffect);

            data.put("ltAbsenceName",absenceDto.getLtAbsenceName());

            data.put("performCompletion",performCompletion);
            data.put("discountAccumulateList",discountAccumulateList);

//            log.info("차트 테스트 : "+chartDataList);
//            log.info("차트 데이터 길이 : "+chartDataList.size());
            data.put("chartDataList",chartDataList);

//            log.info("damageRankYearList 선제적 : "+damageRankYearList);
//            log.info("damageRankYearList2 현행 : "+damageRankYearList2);

            data.put("damageRankYearList",damageRankYearList);
            data.put("damageRankYearList2",damageRankYearList2);

            data.put("ltPeriodicFrequency",ltPeriodicFrequency);
            data.put("ltCloseFrequency",ltCloseFrequency);
            data.put("ltSafetyFrequency",ltSafetyFrequency);

            int count = 0;
            int count2 = 0;
            for(int i=0; i<damageRankYearList.size(); i++){
                if(damageRankYearList.get(i) < 100.0){
                    count++;
                }else{
                    break;
                }
            }
            for(int i=0; i<damageRankYearList2.size(); i++){
                if(damageRankYearList2.get(i) < 100.0){
                    count2++;
                }else{
                    break;
                }
            }
            data.put("resultTableCnt", Math.max(count, count2));
            data.put("result2",noActionResult); // 무조치시 사용수명 년수
            data.put("result3",count);
            data.put("result4",managementCostList.get(count-1));
            data.put("result6",managementCostList2.get(count-1)-managementCostList.get(count-1));
        }else{
            return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE006.getCode(),ResponseErrorCode.NDE006.getDesc(),null,null));
        }

        data.put("lifeAllTimeDto",lifeAllTimeDto);

        // 금일 날짜 호출
        String nowDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
//        log.info("금일날짜 : "+nowDate);
        data.put("nowYear",nowDate.substring(0,4));
        data.put("nowMonth",nowDate.substring(4,6));
        data.put("nowDate",nowDate.substring(6,8));

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    // NEWDEAL 생애주기 의사결정 지원 서비스 수정시 데이터 호출하기
    public ResponseEntity<Map<String, Object>> info(Long ltId) {
        log.info("info 호출성공");

        log.info("ltId : "+ltId);

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        LifeAllTimeDto lifeAllTimeDto = lifeAllTimeRepositoryCustom.findById(ltId);
        if(lifeAllTimeDto == null){
            return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE006.getCode(),ResponseErrorCode.NDE006.getDesc(),null,null));
        }else{
            data.put("lifeAllTimeDto",lifeAllTimeDto);
        }

        return ResponseEntity.ok(res.dataSendSuccess(data));

    }
}
