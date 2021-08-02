package com.broadwave.backend.performance;

import com.broadwave.backend.performance.price.PriceDto;
import com.broadwave.backend.performance.price.PriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Minkyu
 * Date : 2021-07-14
 * Time :
 * Remark : 노후화_기술성시트 , 노후화_정책성시트, 노후화_경제성시트, 노후화_종합평가표에 관련된 함수모음
*/
@Slf4j
@Service
public class PerformanceFunctionService {

    private PriceService priceService;
    private final Map<String, String> funRankScore;


    @Autowired
    public PerformanceFunctionService(PriceService priceService, Map<String, String> funRankScore) {
        this.priceService = priceService;
        this.funRankScore = funRankScore;

    }

//**************************** 노후화_기술성 함수 ********************************

    // 안정성 환산점수
    public Map<String, String> safetyLevel(String piSafetyLevel) {
        log.info("안정성 환산점수 함수호출");
        funRankScore.clear();
        log.info("piSafetyLevel : " + piSafetyLevel);
        switch (piSafetyLevel) {
            case "A":
                funRankScore.put("score", "10");
                funRankScore.put("rank", "E");
                break;
            case "B":
                funRankScore.put("score", "50");
                funRankScore.put("rank", "D");
                break;
            case "C":
                funRankScore.put("score", "70");
                funRankScore.put("rank", "C");
                break;
            case "D":
                funRankScore.put("score", "90");
                funRankScore.put("rank", "B");
                break;
            default:
                funRankScore.put("score", "100");
                funRankScore.put("rank", "A");
                break;
        }
        return funRankScore;
    }

    // 사용성 환산점수
    public Map<String, String> usabilityLevel(String piUsabilityLevel) {
        log.info("사용성 환산점수 함수호출");
        funRankScore.clear();
        log.info("piUsabilityLevel : " + piUsabilityLevel);
        switch (piUsabilityLevel) {
            case "A":
                funRankScore.put("score", "20");
                funRankScore.put("rank", "A");
                break;
            case "B":
                funRankScore.put("score", "50");
                funRankScore.put("rank", "C");
                break;
            case "C":
                funRankScore.put("score", "80");
                funRankScore.put("rank", "B");
                break;
            default:
                funRankScore.put("score", "0");
                funRankScore.put("rank", "A");
                break;
        }
        return funRankScore;
    }

    // 노후도 환산점수
    public Map<String, String> publicYear(Double piPublicYear) {
        log.info("노후도 환산점수 함수호출");
        funRankScore.clear();
        log.info("piPublicYear : " + piPublicYear);
        if (50 <= piPublicYear) {
            funRankScore.put("score", "100");
            funRankScore.put("rank", "A");
        } else if (30 <= piPublicYear) {
            funRankScore.put("score", "90");
            funRankScore.put("rank", "B");
        } else if (20 <= piPublicYear) {
            funRankScore.put("score", "70");
            funRankScore.put("rank", "C");
        } else if (10 <= piPublicYear) {
            funRankScore.put("score", "50");
            funRankScore.put("rank", "D");
        } else {
            funRankScore.put("score", "10");
            funRankScore.put("rank", "E");
        }
        return funRankScore;
    }

    // 시급성 환산점수
    public Map<String, String> urgency(String piSafetyLevel, Double piMaintenanceDelay) {
        log.info("시급성 환산점수 함수호출");
        funRankScore.clear();
        int maintenanceDelay = Integer.parseInt(String.valueOf(Math.round(piMaintenanceDelay))); // 형변환
        String rank = piSafetyLevel + maintenanceDelay;
        log.info("rank : " + rank);
        switch (rank) {
            case "A0":
            case "B0":
            case "A1":
            case "A2":
                funRankScore.put("score", "0");
                funRankScore.put("rank", rank);
                break;
            case "B1":
            case "A3":
                funRankScore.put("score", "10");
                funRankScore.put("rank", rank);
                break;
            case "B2":
            case "A4":
                funRankScore.put("score", "20");
                funRankScore.put("rank", rank);
                break;
            case "C0":
            case "B3":
                funRankScore.put("score", "30");
                funRankScore.put("rank", rank);
                break;
            case "B4":
                funRankScore.put("score", "40");
                funRankScore.put("rank", rank);
                break;
            case "C1":
                funRankScore.put("score", "50");
                funRankScore.put("rank", rank);
                break;
            case "C2":
                funRankScore.put("score", "60");
                funRankScore.put("rank", rank);
                break;
            case "C3":
                funRankScore.put("score", "70");
                funRankScore.put("rank", rank);
                break;
            case "D0":
            case "C4":
                funRankScore.put("score", "80");
                funRankScore.put("rank", rank);
                break;
            case "D1":
                funRankScore.put("score", "90");
                funRankScore.put("rank", rank);
                break;
            default:
                funRankScore.put("score", "100");
                funRankScore.put("rank", rank);
                break;
        }
        return funRankScore;
    }

    // 목표성능 달성도 환산점수
    public Map<String, String> goal(String afterSafetyRating, String piGoalLevel) {
        log.info("목표성늘 달성도 환산점수 함수호출");
        funRankScore.clear();
        String rank = afterSafetyRating + piGoalLevel;
        log.info("rank : " + rank);
        switch (rank) {
            case "CA":
            case "DA":
            case "EA":
            case "DB":
            case "EB":
            case "DC":
            case "EC":
                funRankScore.put("score", "0");
                funRankScore.put("rank", rank);
                break;
            case "CB":
                funRankScore.put("score", "20");
                funRankScore.put("rank", rank);
                break;
            case "BA":
                funRankScore.put("score", "30");
                funRankScore.put("rank", rank);
                break;
            case "BB":
            case "CC":
                funRankScore.put("score", "70");
                funRankScore.put("rank", rank);
                break;
            case "BC":
                funRankScore.put("score", "80");
                funRankScore.put("rank", rank);
                break;
            default:
                funRankScore.put("score", "100");
                funRankScore.put("rank", rank);
                break;
        }
        return funRankScore;
    }

    // 기술성 종합 환산점수,환산랭크
    public Map<String, String> technicality_allScoreRank(String type, List<String> scroeList, Double piWeightSafe,Double piWeightUsability, Double piWeightOld, Double piWeightUrgency, Double piWeightGoal){
        log.info("기술성 종합 환산점수 함수호출");
        funRankScore.clear();
        double allScore;
        String allRank;
        try{
            double a = Double.parseDouble(scroeList.get(0))*piWeightSafe;
            double b;
            double c;
            double d;
            double e = 0.0;
            if(type.equals("교량") || type.equals("터널")){
                e = Double.parseDouble(scroeList.get(1))*piWeightUsability;
                b = Double.parseDouble(scroeList.get(2))*piWeightOld;
                c = Double.parseDouble(scroeList.get(3))*piWeightUrgency;
                d = Double.parseDouble(scroeList.get(4))*piWeightGoal;
            }else{
                b = Double.parseDouble(scroeList.get(1))*piWeightOld;
                c = Double.parseDouble(scroeList.get(2))*piWeightUrgency;
                d = Double.parseDouble(scroeList.get(3))*piWeightGoal;
            }
            allScore = a+b+c+d+e;

            allRank = allScoreRankReturn(allScore);

            funRankScore.put("score",String.valueOf(allScore));
            funRankScore.put("rank", allRank);
            return funRankScore;
        }catch (Exception e){
            log.info("기술성 종합 환산점수 예외발생 : "+e);
            return null;
        }
    }

//*****************************************************************************

//++++++++++++++++ 노후화_경제성 함수 +++++++++++++++++++

    // 자산가치 개선 효율성 환산점수
    public Map<String, String> assetValue(String piFacilityType, Long piErectionCost, Long piBusinessExpenses, Double piCompletionYear, Double piRaterBaseYear,
                                          String piBeforeSafetyRating, String piAfterSafetyRating ) {
        log.info("자산가치 개선 효율성 환산점수 함수호출");
        funRankScore.clear();

        log.info("준공연도 : " + piCompletionYear);
        double completionExchangeRate; // 준공연도 물가배수 환산율
        double nowExchangeRate; // 현재 물가배수 환산율
        double priceIndex; // 조정 물가지수
        double costRepurchase; // 재조 달원가
        try{
            PriceDto completionPriceDto = priceService.findByPiYearCustom((double) Math.round(piCompletionYear));
            PriceDto nowPriceDto = priceService.findByPiYearCustom((double) Math.round(piRaterBaseYear)-1);
//            log.info("completionPriceDto : " + completionPriceDto);
//            log.info("nowPriceDto : " + nowPriceDto);
            completionExchangeRate = completionPriceDto.getPiPrice(); // 준공연도 물가배수 환산율
            nowExchangeRate = nowPriceDto.getPiPrice(); // 현재 물가배수 환산율
        }catch (NullPointerException e){
            log.info("기준년도의 환산율이 존재하지 않습니다. 관리자에게 문의바람.");
            return null;
        }
        priceIndex = Math.round(nowExchangeRate/completionExchangeRate*100)/100.0; // 조정 물가지수
        log.info("조정 물가지수 : "+priceIndex);
        costRepurchase = piErectionCost*priceIndex; // 재조 달원가
        log.info("재조 달원가 : "+costRepurchase);

        log.info("*****************************************************************************************************************");

        log.info("부재 : " + piFacilityType);
        log.info("사업전 안전등급 : " + piBeforeSafetyRating);
        log.info("사업후 안전등급 : " + piAfterSafetyRating);

        double usefulLife; // 내용연수 쓰이는데가 없음.
        if (piFacilityType.equals("교량")) {
            usefulLife = 20.0;
        }else if(piFacilityType.equals("보도육교")){
            usefulLife = 20.0;
        }else if(piFacilityType.equals("터널")){
            usefulLife = 20.0;
        }else if(piFacilityType.equals("지하터널")){
            usefulLife = 20.0;
        }else if(piFacilityType.equals("옹벽")){
            usefulLife = 20.0;
        }else if(piFacilityType.equals("절토사면")){
            usefulLife = 20.0;
        }else{
            usefulLife = 20.0;
        }

        double beforeLifeRate; // 사업전 잔존수명률
        double afterLifeRate; // 사업후 잔존수명률
        switch (piBeforeSafetyRating) {
            case "A":
                beforeLifeRate = 95.0;
                break;
            case "B":
                beforeLifeRate = 75.0;
                break;
            case "C":
                beforeLifeRate = 50.0;
                break;
            case "D":
                beforeLifeRate = 30.0;
                break;
            default:
                beforeLifeRate = 5.0;
                break;
        }
        switch (piAfterSafetyRating) {
            case "A":
                afterLifeRate = 95.0;
                break;
            case "B":
                afterLifeRate = 75.0;
                break;
            case "C":
                afterLifeRate = 50.0;
                break;
            case "D":
                afterLifeRate = 30.0;
                break;
            default:
                afterLifeRate = 5.0;
                break;
        }

//        double beforePublic; // 사업전 잔종공용연수 쓰이는데가 없음.
//        double afterPublic; // 사업후 잔존공용연수 쓰이는데가 없음.

//        log.info("사업전 잔존수명률 : " + beforeLifeRate);
//        log.info("사업후 잔존수명률 : " + afterLifeRate);
        double beforeReplacementCost  = costRepurchase*beforeLifeRate/100.0; // 사업전 상각후 대체원가
        double afterReplacementCost  = costRepurchase*afterLifeRate/100.0; // 사업후 상각후 대체원가
//        log.info("사업전 상각후 대체원가 : " + beforeReplacementCost);
//        log.info("사업후 상각후 대체원가 : " + afterReplacementCost);

        double assetValue = afterReplacementCost-beforeReplacementCost; // 자산가치 증가액
//        log.info("자산가치 증가액 : " + assetValue);
//        log.info("유지보수 사업비 : " + piBusinessExpenses);
        double Improving = Math.round(assetValue/piBusinessExpenses*100)/100.0; // 자산가치 개선 효율성
//        log.info("자산가치 개선 효율성 : " + Improving);

        if(5<=Improving){
            funRankScore.put("score", "100");
            funRankScore.put("rank", "A");
        }else if(4<=Improving){
            funRankScore.put("score", "80");
            funRankScore.put("rank", "B");
        }else if(3<=Improving){
            funRankScore.put("score", "70");
            funRankScore.put("rank", "C");
        }else if(2<=Improving){
            funRankScore.put("score", "50");
            funRankScore.put("rank", "D");
        }else{
            funRankScore.put("score", "30");
            funRankScore.put("rank", "E");
        }

        return funRankScore;
    }

    // 안전효용 개선 효율성 환산점수
    public Map<String, String> safetyUtility(String piAfterSafetyRating, String piBeforeSafetyRating, Double piAADT, Long piBusinessExpenses) {
        log.info("안전효용 개선 효율성 환산점수 함수호출");
        funRankScore.clear();
        log.info("piAfterSafetyRating : " + piAfterSafetyRating);
        log.info("piPublicYepiBeforeSafetyRatingar : " + piBeforeSafetyRating);
        log.info("piAADT : " + piAADT);

        String safeRank = piAfterSafetyRating+piBeforeSafetyRating; // 안전효용성 등급
        double safeScore; // 안전효용성 평가점수
        double aadtScore; // 교통량 가중 평가점수
        double safeIndices; // 안전효용 개선 효율성 지수

        switch (safeRank) {
            case "AE":
                safeScore = 100.0;
                break;
            case "AD":
                safeScore = 90.0;
                break;
            case "AC":
            case "BE":
                safeScore = 80.0;
                break;
            case "BD":
                safeScore = 70.0;
                break;
            case "BC":
            case "CE":
                safeScore = 50.0;
                break;
            case "CD":
                safeScore = 30.0;
                break;
            case "AB":
                safeScore = 20.0;
                break;
            case "BB":
            case "DE":
                safeScore = 10.0;
                break;
            case "AA":
                safeScore = 5.0;
                break;
            default:
                safeScore = 0.0;
                break;
        }

        if(40001 <= piAADT){
            aadtScore = 10.0;
        }else if(20000 <= piAADT){
            aadtScore = 8.0;
        }else if(10000 <= piAADT){
            aadtScore = 4.0;
        }else if(5000 <= piAADT){
            aadtScore = 2.0;
        }else{
            aadtScore = 1.0;
        }

        log.info("safeScore : "+safeScore);
        log.info("aadtScore : "+aadtScore);
        log.info("piBusinessExpenses : " + piBusinessExpenses);

        safeIndices = safeScore*aadtScore/(piBusinessExpenses/100000000.0);
        log.info("safeIndices : "+safeIndices);

        if (2 <= safeIndices) {
            funRankScore.put("score", "100");
            funRankScore.put("rank", "A");
        } else if (1.5 <= safeIndices) {
            funRankScore.put("score", "80");
            funRankScore.put("rank", "B");
        } else if (1 <= safeIndices) {
            funRankScore.put("score", "70");
            funRankScore.put("rank", "C");
        } else if (0.5 <= safeIndices) {
            funRankScore.put("score", "50");
            funRankScore.put("rank", "D");
        } else {
            funRankScore.put("score", "30");
            funRankScore.put("rank", "E");
        }
        return funRankScore;
    }

    // 경제성 종합 환산점수,환산랭크
    public Map<String, String> economy_allScoreRank(List<String> economy_scroeList, Double piWeightSafeUtility, Double piWeightCostUtility) {
        log.info("경제성 종합 환산점수,환산랭크 함수호출");
        funRankScore.clear();

        double allScore;
        String allRank;
        try{
            Double a = Double.parseDouble(economy_scroeList.get(0))*piWeightSafeUtility;
            Double b = Double.parseDouble(economy_scroeList.get(1))*piWeightCostUtility;
            allScore = a+b;

            allRank = allScoreRankReturn(allScore);

            funRankScore.put("score",String.valueOf(allScore));
            funRankScore.put("rank", allRank);
            return funRankScore;
        }catch (Exception e){
            log.info("경제성 종합 환산점수 예외발생 : "+e);
            return null;
        }
    }

//+++++++++++++++++++++++++++++++++++++++++++++

//================ 노후화_정책성 함수 ===================

    // 사업추진 타당성 환산점수
    public Map<String, String> BusinessFeasibility(Double piBusinessObligatory,Double piBusinessMandatory,Double piBusinessPlanned) {
        log.info("사업추진 타당성 환산점수 함수호출");
        funRankScore.clear();
        log.info("piBusinessObligatory : " + piBusinessObligatory);
        log.info("piBusinessMandatory : " + piBusinessMandatory);
        log.info("piBusinessPlanned : " + piBusinessPlanned);

        if(piBusinessObligatory==1){
            funRankScore.put("score", "100");
            funRankScore.put("rank", "A");
        }else if(piBusinessMandatory==1){
            funRankScore.put("score", "80");
            funRankScore.put("rank", "B");
        }else if(piBusinessPlanned==1){
            funRankScore.put("score", "30");
            funRankScore.put("rank", "C");
        }else{
            funRankScore.put("score", "0");
            funRankScore.put("rank", "D");
        }
        return funRankScore;
    }

    // 민원 및 사고 대응성 환산점수
    public Map<String, String> ComplaintResponsiveness(Double piWhether) {
        log.info("민원 및 사고 대응성 환산점수 함수호출");
        funRankScore.clear();
        log.info("piWhether : " + piWhether);

        if(3 <= piWhether){
            funRankScore.put("score", "100");
            funRankScore.put("rank", "A");
        }else if(1 <= piWhether){
            funRankScore.put("score", "50");
            funRankScore.put("rank", "B");
        }else{
            funRankScore.put("score", "30");
            funRankScore.put("rank", "C");
        }
        return funRankScore;
    }

    // 사업효과 범용성 환산점수
    public Map<String, String> businessEffect(Double piAADT) {
        log.info("사업효과 범용성 환산점수 함수호출");
        funRankScore.clear();
        log.info("piAADT : " + piAADT);
        if (40001 <= piAADT) {
            funRankScore.put("score", "100");
            funRankScore.put("rank", "A");
        } else if (20000 <= piAADT) {
            funRankScore.put("score", "80");
            funRankScore.put("rank", "B");
        } else if (10000 <= piAADT) {
            funRankScore.put("score", "60");
            funRankScore.put("rank", "C");
        } else if (5000 <= piAADT) {
            funRankScore.put("score", "50");
            funRankScore.put("rank", "D");
        } else {
            funRankScore.put("score", "30");
            funRankScore.put("rank", "E");
        }
        return funRankScore;
    }

    // 정책성 종합 환산점수,환산랭크
    public Map<String, String> policy_allScoreRank(List<String> policy_scroeList, Double piWeightBusiness, Double piWeightComplaint, Double piWeightBusinessEffect) {
        log.info("정책성 종합 환산점수,환산랭크 함수호출");
        funRankScore.clear();

        double allScore;
        String allRank;
        try{
            Double a = Double.parseDouble(policy_scroeList.get(0))*piWeightBusiness;
            Double b = Double.parseDouble(policy_scroeList.get(1))*piWeightComplaint;
            Double c = Double.parseDouble(policy_scroeList.get(2))*piWeightBusinessEffect;
            allScore = a+b+c;

            allRank = allScoreRankReturn(allScore);

            funRankScore.put("score",String.valueOf(allScore));
            funRankScore.put("rank", allRank);
            return funRankScore;
        }catch (Exception e){
            log.info("정책성 종합 환산점수 예외발생 : "+e);
            return null;
        }
    }

//=============================================

//================ 종합평가표 함수 ===================

    // 종합평가표 함수
    public Map<String, Object> all_ScoreRank(List<String> all_scroeList, Double technicality, Double economy, Double policy, Double piWeightCriticalScore) {
        log.info("종합평가표 함수호출");

        Map<String, Object> allRankScore = new HashMap<>();

        log.info("all_scroeList : " + all_scroeList);
        log.info("piWeightCriticalScore : " + piWeightCriticalScore);

        Double allScore;
        String allRank;

        try{

            Double a = Double.parseDouble(all_scroeList.get(0))*technicality;
            Double b = Double.parseDouble(all_scroeList.get(1))*economy;
            Double c = Double.parseDouble(all_scroeList.get(2))*policy;
            allScore = a+b+c;

            allRank = allScoreRankReturn(allScore);

            allRankScore.put("score",Math.round(allScore*1000)/1000.0);
            allRankScore.put("rank", allRank);

            if(piWeightCriticalScore<=allScore){
                allRankScore.put("business","사업성 충족");
            }else{
                allRankScore.put("business","기준점수 미달");
            }

            return allRankScore;
        }catch (Exception e){
            log.info("종합평가표 환산점수 예외발생 : "+e);
            return null;
        }
    }

//=============================================

//########################### 그외 #############################

    // 종합 스코어점수의 랭크반환
    public String allScoreRankReturn(Double allScore){
        String allRank;
        if (90 <= allScore) {
            allRank = "A+";
        } else if (80 <= allScore) {
            allRank = "A0";
        } else if (70 <= allScore) {
            allRank = "B+";
        } else if (60 <= allScore) {
            allRank = "B0";
        }else if (55 <= allScore) {
            allRank = "C+";
        } else if (50 <= allScore) {
            allRank = "C0";
        } else if (45 <= allScore) {
            allRank = "D+";
        } else if (40 <= allScore) {
            allRank = "D0";
        } else {
            allRank = "E";
        }
        return allRank;
    }

//############################################################

}
