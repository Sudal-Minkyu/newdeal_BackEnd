package com.broadwave.backend.performance;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    private final Map<String, String> funRankScore;

    public PerformanceFunctionService(Map<String, String> funRankScore) {
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
                funRankScore.put("score", "100");
                funRankScore.put("rank", "E");
                break;
            case "B":
                funRankScore.put("score", "80");
                funRankScore.put("rank", "D");
                break;
            case "C":
                funRankScore.put("score", "50");
                funRankScore.put("rank", "C");
                break;
            case "D":
                funRankScore.put("score", "20");
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
        if (31 <= piPublicYear) {
            funRankScore.put("score", "100");
            funRankScore.put("rank", "A");
        } else if (26 <= piPublicYear) {
            funRankScore.put("score", "80");
            funRankScore.put("rank", "B");
        } else if (21 <= piPublicYear) {
            funRankScore.put("score", "50");
            funRankScore.put("rank", "C");
        } else if (11 <= piPublicYear) {
            funRankScore.put("score", "20");
            funRankScore.put("rank", "D");
        } else {
            funRankScore.put("score", "0");
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
    public Map<String, String> technicality_allScoreRank(List<String> scroeList, Double piWeightSafe, Double piWeightOld, Double piWeightUrgency, Double piWeightGoal){
        log.info("기술성 종합 환산점수 함수호출");
        funRankScore.clear();
        double allScore;
        String allRank;
        try{
            Double a = Double.parseDouble(scroeList.get(0))*piWeightSafe;
            Double b = Double.parseDouble(scroeList.get(1))*piWeightOld;
            Double c = Double.parseDouble(scroeList.get(2))*piWeightUrgency;
            Double d = Double.parseDouble(scroeList.get(3))*piWeightGoal;
            allScore = a+b+c+d;

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
    public Map<String, String> assetValue(String piSafetyLevel) {
        log.info("안정성 환산점수 함수호출");
        funRankScore.clear();
        log.info("piSafetyLevel : " + piSafetyLevel);
        switch (piSafetyLevel) {
            case "A":
                funRankScore.put("score", "100");
                funRankScore.put("rank", "E");
                break;
            case "B":
                funRankScore.put("score", "80");
                funRankScore.put("rank", "D");
                break;
            case "C":
                funRankScore.put("score", "50");
                funRankScore.put("rank", "C");
                break;
            case "D":
                funRankScore.put("score", "20");
                funRankScore.put("rank", "B");
                break;
            default:
                funRankScore.put("score", "0");
                funRankScore.put("rank", "A");
                break;
        }
        return funRankScore;
    }

    // 안전효용 개선 효율성 환산점수
    public Map<String, String> safetyUtility(Double piPublicYear) {
        log.info("노후도 환산점수 함수호출");
        funRankScore.clear();
        log.info("piPublicYear : " + piPublicYear);
        if (31 <= piPublicYear) {
            funRankScore.put("score", "100");
            funRankScore.put("rank", "A");
        } else if (26 <= piPublicYear) {
            funRankScore.put("score", "80");
            funRankScore.put("rank", "B");
        } else if (21 <= piPublicYear) {
            funRankScore.put("score", "50");
            funRankScore.put("rank", "C");
        } else if (11 <= piPublicYear) {
            funRankScore.put("score", "20");
            funRankScore.put("rank", "D");
        } else {
            funRankScore.put("score", "0");
            funRankScore.put("rank", "E");
        }
        return funRankScore;
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
        } else if (30001 <= piAADT) {
            funRankScore.put("score", "80");
            funRankScore.put("rank", "B");
        } else if (20001 <= piAADT) {
            funRankScore.put("score", "60");
            funRankScore.put("rank", "C");
        } else if (10001 <= piAADT) {
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
