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
public class PerformanceFunction {

    private final Map<String, String> funRankScore;

    public PerformanceFunction(Map<String, String> funRankScore) {
        this.funRankScore = funRankScore;
    }

//****************************노후화_기술성 함수********************************

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

    // 기술성 종합 환산점수
    public Double allScore(List<String> scroeList, List<Double> weight){
        log.info("기술성 종합 환산점수 함수호출");
        double allScore;
        try{
            Double s = Double.parseDouble(scroeList.get(0))*weight.get(0);
            Double p = Double.parseDouble(scroeList.get(1))*weight.get(1);
            Double u = Double.parseDouble(scroeList.get(2))*weight.get(2);
            Double g = Double.parseDouble(scroeList.get(3))*weight.get(3);
            allScore = s+p+u+g;
            return allScore;
        }catch (Exception e){
            log.info("기술성 종합 환산점수 예외발생 : "+e);
            return null;
        }
    }

    // 종합평가 등급 환산랭크
    public String allRank(Double allScore) {
        log.info("종합평가 등급 환산랭크 함수호출");
        funRankScore.clear();
        String allRank;
        log.info("allScore : " + allScore);
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

//*****************************************************************************

//++++++++++++++++노후화_경제성 함수+++++++++++++++++++






//+++++++++++++++++++++++++++++++++++++++++++++




//================노후화_정책성 함수===================

//=============================================








}
