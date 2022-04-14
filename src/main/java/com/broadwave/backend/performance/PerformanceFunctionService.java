package com.broadwave.backend.performance;

import com.broadwave.backend.performance.price.PriceDto;
import com.broadwave.backend.performance.price.PriceService;
import com.broadwave.backend.performance.reference.ReferenceEconomy;
import com.broadwave.backend.performance.reference.ReferencePolicy;
import com.broadwave.backend.performance.reference.ReferenceTechnicality;
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

//**************************** 기술성 함수 ********************************

    // 노후화 대응, 기준변화, 사용성변화 - 안정성 환산점수 21/10/29 완료
    public Map<String, String> safetyLevel(String piSafetyLevel, ReferenceTechnicality technicality , String safeValue) {
        log.info("안정성 환산점수 함수호출");
        funRankScore.clear();
        log.info("piSafetyLevel : " + piSafetyLevel);
        log.info("safeValue : " + safeValue);
        if(safeValue == null){
            switch (piSafetyLevel) {
                case "A":
                    funRankScore.put("score", Double.toString(technicality.getPiTechSafetyEa()));
                    funRankScore.put("rank", "E");
                    break;
                case "B":
                    funRankScore.put("score", Double.toString(technicality.getPiTechSafetyDb()));
                    funRankScore.put("rank", "D");
                    break;
                case "C":
                    funRankScore.put("score", Double.toString(technicality.getPiTechSafetyCc()));
                    funRankScore.put("rank", "C");
                    break;
                case "D":
                    funRankScore.put("score", Double.toString(technicality.getPiTechSafetyBd()));
                    funRankScore.put("rank", "B");
                    break;
                default:
                    funRankScore.put("score", Double.toString(technicality.getPiTechSafetyAe()));
                    funRankScore.put("rank", "A");
                    break;
            }
        }else{
            double score = 100-Double.parseDouble(safeValue);
            if (technicality.getPiTechSafetyAe() <= score) {
                funRankScore.put("rank", "A");
            } else if (technicality.getPiTechSafetyBd() <= score) {
                funRankScore.put("rank", "B");
            } else if (technicality.getPiTechSafetyCc() <= score) {
                funRankScore.put("rank", "C");
            } else if (technicality.getPiTechSafetyDb() <= score) {
                funRankScore.put("rank", "D");
            } else {
                funRankScore.put("rank", "E");
            }
            funRankScore.put("score", String.valueOf(score));
        }
        return funRankScore;
    }

    // 노후화 대응, 기준변화, 사용성변화 - 노후도 환산점수 21/11/04 완료
    public Map<String, String> publicYear(Double piPublicYear,ReferenceTechnicality technicality, String publicYearValue) {
        log.info("노후도 환산점수 함수호출");
        funRankScore.clear();
        log.info("piPublicYear : " + piPublicYear);
        if(publicYearValue == null) {
            if (technicality.getPiTechOldAMin() <= piPublicYear) {
                funRankScore.put("score", String.valueOf(technicality.getPiTechOldAScore()));
                funRankScore.put("rank", "A");
            } else if (technicality.getPiTechOldBMin() <= piPublicYear) {
                funRankScore.put("score", String.valueOf(technicality.getPiTechOldBScore()));
                funRankScore.put("rank", "B");
            } else if (technicality.getPiTechOldCMin() <= piPublicYear) {
                funRankScore.put("score", String.valueOf(technicality.getPiTechOldCScore()));
                funRankScore.put("rank", "C");
            } else if (technicality.getPiTechOldDMin() <= piPublicYear) {
                funRankScore.put("score", String.valueOf(technicality.getPiTechOldDScore()));
                funRankScore.put("rank", "D");
            } else {
                funRankScore.put("score", String.valueOf(technicality.getPiTechOldEScore()));
                funRankScore.put("rank", "E");
            }
        }else{
            double score = 100-Double.parseDouble(publicYearValue);
            if (technicality.getPiTechOldAMin() <= score) {
                funRankScore.put("rank", "A");
            } else if (technicality.getPiTechOldBMin() <= score) {
                funRankScore.put("rank", "B");
            } else if (technicality.getPiTechOldCMin() <= score) {
                funRankScore.put("rank", "C");
            } else if (technicality.getPiTechOldDMin() <= score) {
                funRankScore.put("rank", "D");
            } else {
                funRankScore.put("rank", "E");
            }
            funRankScore.put("score", String.valueOf(score));
        }
        return funRankScore;
    }

    // 사용성변화 - 사용성 환산점수 11/04 완료
    public Map<String, String> usabilityLevel(String piUsabilityLevel,ReferenceTechnicality technicality) {
        log.info("사용성 환산점수 함수호출");
        funRankScore.clear();
        log.info("piUsabilityLevel : " + piUsabilityLevel);
        switch (piUsabilityLevel) {
            case "A":
                funRankScore.put("score", String.valueOf(technicality.getPiTechUsabilityAe()));
                funRankScore.put("rank", "E");
                break;
            case "B":
                funRankScore.put("score", String.valueOf(technicality.getPiTechUsabilityBd()));
                funRankScore.put("rank", "D");
                break;
            case "C":
                funRankScore.put("score", String.valueOf(technicality.getPiTechUsabilityCc()));
                funRankScore.put("rank", "C");
                break;
            case "D":
                funRankScore.put("score", String.valueOf(technicality.getPiTechUsabilityDb()));
                funRankScore.put("rank", "B");
                break;
            default:
                funRankScore.put("score", String.valueOf(technicality.getPiTechUsabilityEa()));
                funRankScore.put("rank", "A");
                break;
        }
        return funRankScore;
    }

    // 노후화 대응 - 지체도 환산점수 11/04 완료
    public Map<String, String> urgency(String piSafetyLevel, Double piMaintenanceDelay,ReferenceTechnicality technicality) {
        log.info("지체도 환산점수 함수호출");
        funRankScore.clear();
        int maintenanceDelay = Integer.parseInt(String.valueOf(Math.round(piMaintenanceDelay))); // 형변환
        log.info("maintenanceDelay : "+maintenanceDelay);

        String rank = piSafetyLevel + maintenanceDelay;
        log.info("rank : " + rank);
        switch (rank) {
            case "A0":
                funRankScore.put("score", String.valueOf(technicality.getPiTechRetardationA0()));
                funRankScore.put("rank", rank);
                break;
            case "A1":
                funRankScore.put("score", String.valueOf(technicality.getPiTechRetardationA1()));
                funRankScore.put("rank", rank);
                break;
            case "A2":
                funRankScore.put("score", String.valueOf(technicality.getPiTechRetardationA2()));
                funRankScore.put("rank", rank);
                break;
            case "A3":
                funRankScore.put("score", String.valueOf(technicality.getPiTechRetardationA3()));
                funRankScore.put("rank", rank);
                break;
            case "A4":
                funRankScore.put("score", String.valueOf(technicality.getPiTechRetardationA4()));
                funRankScore.put("rank", rank);
                break;
            case "B0":
                funRankScore.put("score", String.valueOf(technicality.getPiTechRetardationB0()));
                funRankScore.put("rank", rank);
                break;
            case "B1":
                funRankScore.put("score", String.valueOf(technicality.getPiTechRetardationB1()));
                funRankScore.put("rank", rank);
                break;
            case "B2":
                funRankScore.put("score", String.valueOf(technicality.getPiTechRetardationB2()));
                funRankScore.put("rank", rank);
            case "B3":
                funRankScore.put("score", String.valueOf(technicality.getPiTechRetardationB3()));
                funRankScore.put("rank", rank);
                break;
            case "B4":
                funRankScore.put("score", String.valueOf(technicality.getPiTechRetardationB4()));
                funRankScore.put("rank", rank);
                break;
            case "C0":
                funRankScore.put("score", String.valueOf(technicality.getPiTechRetardationC0()));
                funRankScore.put("rank", rank);
                break;
            case "C1":
                funRankScore.put("score", String.valueOf(technicality.getPiTechRetardationC1()));
                funRankScore.put("rank", rank);
                break;
            case "C2":
                funRankScore.put("score", String.valueOf(technicality.getPiTechRetardationC2()));
                funRankScore.put("rank", rank);
                break;
            case "C3":
                funRankScore.put("score", String.valueOf(technicality.getPiTechRetardationC3()));
                funRankScore.put("rank", rank);
                break;
            case "C4":
                funRankScore.put("score", String.valueOf(technicality.getPiTechRetardationC4()));
                funRankScore.put("rank", rank);
                break;
            case "D0":
                funRankScore.put("score", String.valueOf(technicality.getPiTechRetardationD0()));
                funRankScore.put("rank", rank);
                break;
            case "D1":
                funRankScore.put("score", String.valueOf(technicality.getPiTechRetardationD1()));
                funRankScore.put("rank", rank);
                break;
            case "D2":
                funRankScore.put("score", String.valueOf(technicality.getPiTechRetardationD2()));
                funRankScore.put("rank", rank);
            case "D3":
                funRankScore.put("score", String.valueOf(technicality.getPiTechRetardationD3()));
                funRankScore.put("rank", rank);
                break;
            case "D4":
                funRankScore.put("score", String.valueOf(technicality.getPiTechRetardationD4()));
                funRankScore.put("rank", rank);
                break;
            case "E0":
                funRankScore.put("score", String.valueOf(technicality.getPiTechRetardationE0()));
                funRankScore.put("rank", rank);
                break;
            case "E1":
                funRankScore.put("score", String.valueOf(technicality.getPiTechRetardationE1()));
                funRankScore.put("rank", rank);
                break;
            case "E2":
                funRankScore.put("score", String.valueOf(technicality.getPiTechRetardationE2()));
                funRankScore.put("rank", rank);
                break;
            case "E3":
                funRankScore.put("score", String.valueOf(technicality.getPiTechRetardationE3()));
                funRankScore.put("rank", rank);
                break;
            default:
                funRankScore.put("score", String.valueOf(technicality.getPiTechRetardationE4()));
                funRankScore.put("rank", rank);
                break;
        }
        log.info("funRankScore : "+funRankScore);

        return funRankScore;
    }

    // 노후화 대응 - 목표성능 달성도 환산점수 11/04 완료
    public Map<String, String> goal(String afterSafetyRating, String piGoalLevel, ReferenceTechnicality technicality) {
        log.info("목표성늘 달성도 환산점수 함수호출");
        funRankScore.clear();
        String rank = afterSafetyRating + piGoalLevel;
        log.info("rank : " + rank);
        switch (rank) {
            case "AA":
                funRankScore.put("score", String.valueOf(technicality.getPiTechPerformanceAa()));
                funRankScore.put("rank", rank);
                break;
            case "AB":
                funRankScore.put("score", String.valueOf(technicality.getPiTechPerformanceAb()));
                funRankScore.put("rank", rank);
                break;
            case "AC":
                funRankScore.put("score", String.valueOf(technicality.getPiTechPerformanceAc()));
                funRankScore.put("rank", rank);
                break;
            case "BA":
                funRankScore.put("score", String.valueOf(technicality.getPiTechPerformanceBa()));
                funRankScore.put("rank", rank);
                break;
            case "BB":
                funRankScore.put("score", String.valueOf(technicality.getPiTechPerformanceBb()));
                funRankScore.put("rank", rank);
                break;
            case "BC":
                funRankScore.put("score", String.valueOf(technicality.getPiTechPerformanceBc()));
                funRankScore.put("rank", rank);
                break;
            case "CA":
                funRankScore.put("score", String.valueOf(technicality.getPiTechPerformanceCa()));
                funRankScore.put("rank", rank);
                break;
            case "CB":
                funRankScore.put("score", String.valueOf(technicality.getPiTechPerformanceCb()));
                funRankScore.put("rank", rank);
                break;
            case "CC":
                funRankScore.put("score", String.valueOf(technicality.getPiTechPerformanceCc()));
                funRankScore.put("rank", rank);
                break;
            case "DA":
                funRankScore.put("score", String.valueOf(technicality.getPiTechPerformanceDa()));
                funRankScore.put("rank", rank);
                break;
            case "DB":
                funRankScore.put("score", String.valueOf(technicality.getPiTechPerformanceDb()));
                funRankScore.put("rank", rank);
                break;
            case "DC":
                funRankScore.put("score", String.valueOf(technicality.getPiTechPerformanceDc()));
                funRankScore.put("rank", rank);
            case "EA":
                funRankScore.put("score", String.valueOf(technicality.getPiTechPerformanceEa()));
                funRankScore.put("rank", rank);
                break;
            case "EB":
                funRankScore.put("score", String.valueOf(technicality.getPiTechPerformanceEb()));
                funRankScore.put("rank", rank);
                break;
            default:
                funRankScore.put("score", String.valueOf(technicality.getPiTechPerformanceEc()));
                funRankScore.put("rank", rank);
                break;
        }
        return funRankScore;
    }

    // 노후화 대응, 기준변화, 사용성변화 - 기술성 종합 환산점수,환산랭크 11/04 완료
    public Map<String, String> technicality_allScoreRank(String type, List<String> scroeList, Double piWeightSafe,Double piWeightUsability, Double piWeightOld, Double piWeightUrgency, Double piWeightGoal, String piBusiness, ReferenceTechnicality technicality){
        log.info("기술성 종합 환산점수 함수호출");
        funRankScore.clear();
        log.info("");
        log.info("type : " + type);
//        log.info("piWeightSafe 안정성가중치 : " + piWeightSafe);
//        log.info("piWeightOld 노후도 가중치 : " + piWeightOld);
//        log.info("piWeightUrgency 지체도 가중치 : " + piWeightUrgency);
//        log.info("piWeightGoal 목표달성도 가중치 : " + piWeightGoal);
//
//        log.info("piWeightUsability 사용성 가중치 -> 사용성변화에만 사용 : " + piWeightUsability);

        log.info("piBusiness : " + piBusiness);

        double allScore = 0;
        String allRank = null;
        log.info("scroeList : "+scroeList);

        try{

            if(piBusiness.equals("노후화대응")){

                double safeScore = Double.parseDouble(scroeList.get(0)) * piWeightSafe;
                double oldScore = Double.parseDouble(scroeList.get(1)) * piWeightOld;
                double urgencyScore = Double.parseDouble(scroeList.get(2)) * piWeightUrgency;
                double goalScore = Double.parseDouble(scroeList.get(3)) * piWeightGoal;
                allScore = safeScore+oldScore+urgencyScore+goalScore;

            }else if(piBusiness.equals("기준변화")){

            }else{

            }

            if (technicality.getPiTechGoalAPlusMin() < allScore && technicality.getPiTechGoalAPlusMax() >= allScore) {
                allRank = "A+";
            } else if (technicality.getPiTechGoalAMinusMin() < allScore && technicality.getPiTechGoalAMinusMax() >= allScore) {
                allRank = "A0";
            } else if (technicality.getPiTechGoalBPlusMin() < allScore && technicality.getPiTechGoalBPlusMax() >= allScore) {
                allRank = "B+";
            } else if (technicality.getPiTechGoalBMinusMin() < allScore && technicality.getPiTechGoalBMinusMax() >= allScore) {
                allRank = "B0";
            } else if (technicality.getPiTechGoalCPlusMin() < allScore && technicality.getPiTechGoalCPlusMax() >= allScore) {
                allRank = "C+";
            } else if (technicality.getPiTechGoalCMinusMin() < allScore && technicality.getPiTechGoalCMinusMax() >= allScore) {
                allRank = "C0";
            } else if (technicality.getPiTechGoalDPlusMin() < allScore && technicality.getPiTechGoalDPlusMax() >= allScore) {
                allRank = "D+";
            } else if (technicality.getPiTechGoalDMinusMin() < allScore && technicality.getPiTechGoalDMinusMax() >= allScore) {
                allRank = "D0";
            } else {
                allRank = "E";
            }

            funRankScore.put("score", String.valueOf(Math.round(allScore*10)/10.0));
            funRankScore.put("rank", allRank);

            return funRankScore;

        }catch (Exception e){
            log.info("기술성 종합 환산점수 예외발생 : "+e);
            return null;
        }
    }

//*****************************************************************************

//++++++++++++++++ 경제성 함수 +++++++++++++++++++

    // 자산가치 개선 효율성 환산점수
    public Map<String, String> assetValue(ReferenceEconomy economy, String piFacilityType, Long piErectionCost, Long piBusinessExpenses, Double piCompletionYear, Double piRaterBaseYear,
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
            completionExchangeRate = completionPriceDto.getPiPrice(); // 준공연도 물가배수 환산율
            if(nowPriceDto == null){
                nowPriceDto = priceService.findByPiYearCustom(null);
                nowExchangeRate = nowPriceDto.getPiPrice(); // 현재 물가배수 환산율
            }else{
                nowExchangeRate = nowPriceDto.getPiPrice(); // 현재 물가배수 환산율
            }

            log.info("completionExchangeRate : " + completionExchangeRate);
            log.info("nowExchangeRate : " + nowExchangeRate);

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
            usefulLife = economy.getPiEcoFacilityA();
        }else if(piFacilityType.equals("보도육교")){
            usefulLife = economy.getPiEcoFacilityB();
        }else if(piFacilityType.equals("터널")){
            usefulLife = economy.getPiEcoFacilityC();
        }else if(piFacilityType.equals("지하터널")){
            usefulLife = economy.getPiEcoFacilityD();
        }else if(piFacilityType.equals("옹벽")){
            usefulLife = economy.getPiEcoFacilityE();
        }else if(piFacilityType.equals("절토사면")){
            usefulLife = economy.getPiEcoFacilityF();
        }else{
            usefulLife = economy.getPiEcoFacilityG();
        }

        double beforeLifeRate; // 사업전 잔존수명률
        double afterLifeRate; // 사업후 잔존수명률
        switch (piBeforeSafetyRating) {
            case "A":
                beforeLifeRate = economy.getPiEcoLifeA();
                break;
            case "B":
                beforeLifeRate = economy.getPiEcoLifeB();
                break;
            case "C":
                beforeLifeRate = economy.getPiEcoLifeC();
                break;
            case "D":
                beforeLifeRate = economy.getPiEcoLifeD();
                break;
            default:
                beforeLifeRate = economy.getPiEcoLifeE();
                break;
        }
        switch (piAfterSafetyRating) {
            case "A":
                afterLifeRate = economy.getPiEcoLifeA();
                break;
            case "B":
                afterLifeRate = economy.getPiEcoLifeB();
                break;
            case "C":
                afterLifeRate = economy.getPiEcoLifeC();
                break;
            case "D":
                afterLifeRate = economy.getPiEcoLifeD();
                break;
            default:
                afterLifeRate = economy.getPiEcoLifeE();
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

        if(economy.getPiEcoAssetAMin()<=Improving){
            funRankScore.put("score", String.valueOf(economy.getPiEcoAssetAScore()));
            funRankScore.put("rank", "A");
        }else if(economy.getPiEcoAssetBMin() <= Improving && Improving< economy.getPiEcoAssetBMax()){
            funRankScore.put("score",  String.valueOf(economy.getPiEcoAssetBScore()));
            funRankScore.put("rank", "B");
        }else if(economy.getPiEcoAssetCMin() <= Improving && Improving< economy.getPiEcoAssetCMax()){
            funRankScore.put("score",  String.valueOf(economy.getPiEcoAssetCScore()));
            funRankScore.put("rank", "C");
        }else if(economy.getPiEcoAssetDMin() <= Improving && Improving< economy.getPiEcoAssetDMax()){
            funRankScore.put("score",  String.valueOf(economy.getPiEcoAssetDScore()));
            funRankScore.put("rank", "D");
        }else{
            funRankScore.put("score",  String.valueOf(economy.getPiEcoAssetEScore()));
            funRankScore.put("rank", "E");
        }

        return funRankScore;
    }

    // 안전효용 개선 효율성 환산점수
    public Map<String, String> safetyUtility(ReferenceEconomy economy, String piAfterSafetyRating, String piBeforeSafetyRating, Double piAADT, Long piBusinessExpenses) {
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
            case "AA":
                safeScore = economy.getPiEcoUtilityAa();
                break;
            case "AB":
                safeScore = economy.getPiEcoUtilityBa();
                break;
            case "AC":
                safeScore = economy.getPiEcoUtilityCa();
                break;
            case "AD":
                safeScore = economy.getPiEcoUtilityDa();
                break;
            case "AE":
                safeScore = economy.getPiEcoUtilityEa();
                break;
            case "BB":
                safeScore = economy.getPiEcoUtilityBb();
                break;
            case "BC":
                safeScore = economy.getPiEcoUtilityCb();
                break;
            case "BD":
                safeScore = economy.getPiEcoUtilityDb();
                break;
            case "BE":
                safeScore = economy.getPiEcoUtilityEb();
                break;
            case "CC":
                safeScore = economy.getPiEcoUtilityCc();
                break;
            case "CD":
                safeScore = economy.getPiEcoUtilityDc();
                break;
            case "CE":
                safeScore = economy.getPiEcoUtilityEc();
                break;
            case "DD":
                safeScore = economy.getPiEcoUtilityDd();
                break;
            case "DE":
                safeScore = economy.getPiEcoUtilityEd();
                break;
            default:
                safeScore = economy.getPiEcoUtilityEe();
                break;
        }


        if(economy.getPiEcoTrafficAMin() <= piAADT){
            aadtScore = economy.getPiEcoTrafficAScore();
        }else if(economy.getPiEcoTrafficBMin() <= piAADT && piAADT< economy.getPiEcoTrafficBMax()){
            aadtScore = economy.getPiEcoTrafficBScore();
        }else if(economy.getPiEcoTrafficCMin() <= piAADT && piAADT< economy.getPiEcoTrafficCMax()){
            aadtScore = economy.getPiEcoTrafficCScore();
        }else if(economy.getPiEcoTrafficDMin() <= piAADT && piAADT< economy.getPiEcoTrafficDMax()){
            aadtScore = economy.getPiEcoTrafficDScore();
        }else{
            aadtScore = economy.getPiEcoTrafficEScore();
        }

        log.info("safeScore : "+safeScore);
        log.info("aadtScore : "+aadtScore);
        log.info("piBusinessExpenses : " + piBusinessExpenses);

        safeIndices = safeScore*aadtScore/(piBusinessExpenses/100000000.0);
        log.info("safeIndices : "+safeIndices);

        if(economy.getPiEcoImproAMin() <= safeIndices){
            funRankScore.put("score",  String.valueOf(economy.getPiEcoImproAScore()));
            funRankScore.put("rank", "A");
        }else if(economy.getPiEcoImproBMin() <= safeIndices && safeIndices< economy.getPiEcoImproBMax()){
            funRankScore.put("score",  String.valueOf(economy.getPiEcoImproBScore()));
            funRankScore.put("rank", "B");
        }else if(economy.getPiEcoImproCMin() <= safeIndices && safeIndices< economy.getPiEcoImproCMax()){
            funRankScore.put("score",  String.valueOf(economy.getPiEcoImproCScore()));
            funRankScore.put("rank", "C");
        }else if(economy.getPiEcoImproDMin() <= safeIndices && safeIndices< economy.getPiEcoImproDMax()){
            funRankScore.put("score",  String.valueOf(economy.getPiEcoImproDScore()));
            funRankScore.put("rank", "D");
        }else {
            funRankScore.put("score",  String.valueOf(economy.getPiEcoImproEScore()));
            funRankScore.put("rank", "E");
        }
        return funRankScore;
    }

    // 기준변화, 사용성변화 - 경제성 - 사업규모 등급 22/04/14 완료
    public Map<String, String> businessScale(ReferenceEconomy economy, Long piBusinessExpenses, Double cost) {
        log.info("사업규모 등급 함수호출");
        funRankScore.clear();

        log.info("piBusinessExpenses : "+piBusinessExpenses);
        log.info("cost : "+cost);

        // 사업비용 단위
        double businessCostUnit = piBusinessExpenses/cost;
        log.info("사업비용 단위 : "+businessCostUnit);

        if(businessCostUnit <= economy.getPiEcoScaleBaseA()){
            funRankScore.put("score", String.valueOf(economy.getPiEcoScaleScoreA()));
            funRankScore.put("rank", "A");
        }else if(businessCostUnit <= economy.getPiEcoScaleBaseB()){
            funRankScore.put("score",  String.valueOf(economy.getPiEcoScaleScoreB()));
            funRankScore.put("rank", "B");
        }else if(businessCostUnit <= economy.getPiEcoScaleBaseC()){
            funRankScore.put("score",  String.valueOf(economy.getPiEcoScaleScoreC()));
            funRankScore.put("rank", "C");
        }else if(businessCostUnit <= economy.getPiEcoScaleBaseD()){
            funRankScore.put("score",  String.valueOf(economy.getPiEcoScaleScoreD()));
            funRankScore.put("rank", "D");
        }else{
            funRankScore.put("score",  String.valueOf(economy.getPiEcoScaleScoreE()));
            funRankScore.put("rank", "E");
        }

        return funRankScore;
    }

    // 기준변화, 사용성변화 - 경제성 - 사업효율 등급 22/04/14 완료
    public Map<String, String> businessEfficiency(ReferenceEconomy economy, String piFacilityType, Long piBusinessExpenses, Double piAADT) {
        log.info("사업효율 등급 함수호출");
        funRankScore.clear();

        log.info("piBusinessExpenses : "+piBusinessExpenses);
        log.info("piAADT : "+piAADT);

        double usefulLife;
        if (piFacilityType.equals("교량")) {
            usefulLife = economy.getPiEcoFacilityA();
        }else if(piFacilityType.equals("보도육교")){
            usefulLife = economy.getPiEcoFacilityB();
        }else if(piFacilityType.equals("터널")){
            usefulLife = economy.getPiEcoFacilityC();
        }else if(piFacilityType.equals("지하터널")){
            usefulLife = economy.getPiEcoFacilityD();
        }else if(piFacilityType.equals("옹벽")){
            usefulLife = economy.getPiEcoFacilityE();
        }else if(piFacilityType.equals("절토사면")){
            usefulLife = economy.getPiEcoFacilityF();
        }else{
            usefulLife = economy.getPiEcoFacilityG();
        }

        // 사업효율 단위
        double businessEfficiencyUnit = piBusinessExpenses/(piAADT*usefulLife*365);
        log.info("사업효율 단위 : "+businessEfficiencyUnit);

        if(businessEfficiencyUnit <= economy.getPiEcoEfficiencyBaseA()){
            funRankScore.put("score", String.valueOf(economy.getPiEcoEfficiencyScoreA()));
            funRankScore.put("rank", "A");
        }else if(businessEfficiencyUnit <= economy.getPiEcoEfficiencyBaseB()){
            funRankScore.put("score", String.valueOf(economy.getPiEcoEfficiencyScoreB()));
            funRankScore.put("rank", "B");
        }else if(businessEfficiencyUnit <= economy.getPiEcoEfficiencyBaseC()){
            funRankScore.put("score", String.valueOf(economy.getPiEcoEfficiencyScoreC()));
            funRankScore.put("rank", "C");
        }else if(businessEfficiencyUnit <= economy.getPiEcoEfficiencyBaseD()){
            funRankScore.put("score", String.valueOf(economy.getPiEcoEfficiencyScoreD()));
            funRankScore.put("rank", "D");
        }else{
            funRankScore.put("score", String.valueOf(economy.getPiEcoEfficiencyScoreE()));
            funRankScore.put("rank", "E");
        }

        return funRankScore;
    }

    // 경제성 종합 환산점수,환산랭크
    public Map<String, String> economy_allScoreRank(ReferenceEconomy economy, List<String> economy_scroeList, Double piWeightSafeUtility, Double piWeightCostUtility) {
        log.info("경제성 종합 환산점수,환산랭크 함수호출");
        funRankScore.clear();

        log.info("piWeightSafeUtility : "+piWeightSafeUtility);
        log.info("piWeightCostUtility : "+piWeightCostUtility);

        double allScore;
        String allRank;
        try{
            Double a = Double.parseDouble(economy_scroeList.get(0))*piWeightSafeUtility;
            Double b = Double.parseDouble(economy_scroeList.get(1))*piWeightCostUtility;
            allScore = a+b;

            if (economy.getPiEcoGoalAPlusMin() <= allScore) {
                allRank = "A+";
            } else if (economy.getPiEcoGoalAMinusMin() <= allScore) {
                allRank = "A0";
            } else if (economy.getPiEcoGoalBPlusMin() <= allScore) {
                allRank = "B+";
            } else if (economy.getPiEcoGoalBMinusMin() <= allScore) {
                allRank = "B0";
            } else if (economy.getPiEcoGoalCPlusMin() <= allScore) {
                allRank = "C+";
            } else if (economy.getPiEcoGoalCMinusMin() <= allScore) {
                allRank = "C0";
            } else if (economy.getPiEcoGoalDPlusMin() <= allScore) {
                allRank = "D+";
            } else if (economy.getPiEcoGoalDMinusMin() <= allScore) {
                allRank = "D0";
            } else {
                allRank = "E";
            }

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
    public Map<String, String> BusinessFeasibility(ReferencePolicy policy, Double piBusinessObligatory, Double piBusinessMandatory, Double piBusinessPlanned) {
        log.info("사업추진 타당성 환산점수 함수호출");
        funRankScore.clear();
        log.info("piBusinessObligatory : " + piBusinessObligatory);
        log.info("piBusinessMandatory : " + piBusinessMandatory);
        log.info("piBusinessPlanned : " + piBusinessPlanned);

        if(piBusinessObligatory==1){
            funRankScore.put("score",String.valueOf(policy.getPiPolicyValidityA()));
            funRankScore.put("rank", "A");
        }else if(piBusinessMandatory==1){
            funRankScore.put("score",String.valueOf(policy.getPiPolicyValidityB()));
            funRankScore.put("rank", "B");
        }else if(piBusinessPlanned==1){
            funRankScore.put("score",String.valueOf(policy.getPiPolicyValidityC()));
            funRankScore.put("rank", "C");
        }else{
            funRankScore.put("score",String.valueOf(policy.getPiPolicyValidityD()));
            funRankScore.put("rank", "D");
        }
        return funRankScore;
    }

    // 사업효과 범용성 환산점수
    public Map<String, String> businessEffect(ReferencePolicy policy, Double piAADT) {
        log.info("사업효과 범용성 환산점수 함수호출");
        funRankScore.clear();
        log.info("piAADT : " + piAADT);
        if (policy.getPiPolicyVersatilityAMin() <= piAADT) {
            funRankScore.put("score",String.valueOf(policy.getPiPolicyVersatilityAScore()));
            funRankScore.put("rank", "A");
        } else if(policy.getPiPolicyVersatilityBMin() <= piAADT && piAADT< policy.getPiPolicyVersatilityBMax()){
            funRankScore.put("score",String.valueOf(policy.getPiPolicyVersatilityBScore()));
            funRankScore.put("rank", "B");
        } else if(policy.getPiPolicyVersatilityCMin() <= piAADT && piAADT< policy.getPiPolicyVersatilityCMax()){
            funRankScore.put("score",String.valueOf(policy.getPiPolicyVersatilityCScore()));
            funRankScore.put("rank", "C");
        } else if(policy.getPiPolicyVersatilityDMin() <= piAADT && piAADT< policy.getPiPolicyVersatilityDMax()){
            funRankScore.put("score",String.valueOf(policy.getPiPolicyVersatilityDScore()));
            funRankScore.put("rank", "D");
        } else {
            funRankScore.put("score",String.valueOf(policy.getPiPolicyVersatilityEScore()));
            funRankScore.put("rank", "E");
        }

        return funRankScore;
    }

    // 민원 및 사고 대응성 환산점수
    public Map<String, String> ComplaintResponsiveness(ReferencePolicy policy, Double piWhether) {
        log.info("민원 및 사고 대응성 환산점수 함수호출");
        funRankScore.clear();
        log.info("piWhether : " + piWhether);

        if(policy.getPiPolicyResponValueA() <= piWhether){
            funRankScore.put("score",String.valueOf(policy.getPiPolicyResponScoreA()));
            funRankScore.put("rank", "A");
        }else if(policy.getPiPolicyResponValueB() <= piWhether){
            funRankScore.put("score",String.valueOf(policy.getPiPolicyResponScoreB()));
            funRankScore.put("rank", "B");
        }else{
            funRankScore.put("score",String.valueOf(policy.getPiPolicyResponScoreC()));
            funRankScore.put("rank", "C");
        }
        return funRankScore;
    }

    // 정책성 종합 환산점수,환산랭크
    public Map<String, String> policy_allScoreRank(ReferencePolicy policy, String piBusiness,List<String> policy_scroeList, Double piWeightBusiness, Double piWeightComplaint, Double piWeightBusinessEffect) {
        log.info("정책성 종합 환산점수,환산랭크 함수호출");
        funRankScore.clear();

        log.info("사업추진 타당성 : " + piWeightBusiness);
        log.info("사업효과 범용성 : " + piWeightBusinessEffect);
        log.info("민원 및 사고 대응성 : " + piWeightComplaint);

        double allScore;
        String allRank;
        try{
            double a;
            double b;
            double c;

            if(piBusiness.equals("기준변화")){
                a = Double.parseDouble(policy_scroeList.get(0))*piWeightBusiness;
                b = Double.parseDouble(policy_scroeList.get(1))*piWeightBusinessEffect;
                allScore = a+b;
            }else{
                a = Double.parseDouble(policy_scroeList.get(0))*piWeightBusiness;
                b = Double.parseDouble(policy_scroeList.get(1))*piWeightBusinessEffect;
                c = Double.parseDouble(policy_scroeList.get(2))*piWeightComplaint;
                allScore = a+b+c;
            }

            if (policy.getPiPolicyGoalAPlusMin() <= allScore) {
                allRank = "A+";
            } else if (policy.getPiPolicyGoalAMinusMin() <= allScore) {
                allRank = "A0";
            } else if (policy.getPiPolicyGoalBPlusMin() <= allScore) {
                allRank = "B+";
            } else if (policy.getPiPolicyGoalBMinusMin() <= allScore) {
                allRank = "B0";
            } else if (policy.getPiPolicyGoalCPlusMin() <= allScore) {
                allRank = "C+";
            } else if (policy.getPiPolicyGoalCMinusMin() <= allScore) {
                allRank = "C0";
            } else if (policy.getPiPolicyGoalDPlusMin() <= allScore) {
                allRank = "D+";
            } else if (policy.getPiPolicyGoalDMinusMin() <= allScore) {
                allRank = "D0";
            } else {
                allRank = "E";
            }

            funRankScore.put("score",String.valueOf(Math.round(allScore*10)/10.0));
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
