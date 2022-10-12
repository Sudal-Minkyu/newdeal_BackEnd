package com.broadwave.backend.lifetime.detail.main;

import com.broadwave.backend.common.AjaxResponse;
import com.broadwave.backend.common.NormMath;
import com.broadwave.backend.lifetime.detail.cabonationThreePlate.CabonationThreePlateInfoDto;
import com.broadwave.backend.lifetime.detail.cabonationThreePlate.CabonationThreePlateRepository;
import com.broadwave.backend.lifetime.detail.carbonation.CabonationInfoDto;
import com.broadwave.backend.lifetime.detail.carbonation.CabonationRepository;
import com.broadwave.backend.lifetime.detail.chloride.ChlorideInfoDto;
import com.broadwave.backend.lifetime.detail.chloride.ChlorideRepository;
import com.broadwave.backend.lifetime.detail.crack.CrackInfoDto;
import com.broadwave.backend.lifetime.detail.crack.CrackRepository;
import com.broadwave.backend.lifetime.detail.hardness.HardnessInfoDto;
import com.broadwave.backend.lifetime.detail.hardness.HardnessRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author Minkyu
 * Date : 2022-05-23
 * Time :
 * Remark : NewDeal LifeDetail Service
*/
@Slf4j
@Service
public class LifeDetailService {

    private final LiftDetailRepository liftDetailRepository;

    private final HardnessRepository hardnessRepository;
    private final CabonationRepository cabonationRepository;
    private final CabonationThreePlateRepository cabonationThreePlateRepository;
    private final CrackRepository crackRepository;
    private final ChlorideRepository chlorideRepository;

    @Autowired
    public LifeDetailService(LiftDetailRepository liftDetailRepository,
                             HardnessRepository hardnessRepository, CabonationRepository cabonationRepository,
                             CabonationThreePlateRepository cabonationThreePlateRepository, CrackRepository crackRepository, ChlorideRepository chlorideRepository) {
        this.liftDetailRepository = liftDetailRepository;
        this.hardnessRepository = hardnessRepository;
        this.cabonationRepository = cabonationRepository;
        this.cabonationThreePlateRepository = cabonationThreePlateRepository;
        this.crackRepository = crackRepository;
        this.chlorideRepository = chlorideRepository;
    }

    public ResponseEntity<Map<String, Object>> output(String autoNum, HttpServletRequest request) {

        log.info("output 호출성공");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
        log.info("JWT_AccessToken : "+JWT_AccessToken);

        log.info("autoNum : "+autoNum);

        Optional<LifeDetail> optionalLifeDetail = liftDetailRepository.findByLtDetailAutoNum(autoNum);
        if(optionalLifeDetail.isPresent()){
            log.info("optionalLifeDetail.get().getLtDetailAutonum() : "+optionalLifeDetail.get().getLtDetailAutoNum());

            if(optionalLifeDetail.get().getLtDetailType().equals("1") || optionalLifeDetail.get().getLtDetailType().equals("2") ||
                    optionalLifeDetail.get().getLtDetailType().equals("3") || optionalLifeDetail.get().getLtDetailType().equals("4")){

                HashMap<String, Object> returnData;
                if(optionalLifeDetail.get().getLtDetailType().equals("1")) {
                    // 반발경도
                    log.info("반발경도 아웃풋시작");

                    returnData = durabilityService("1", autoNum);

                    log.info("returnData : "+returnData);

                    data.put("chartName","hardness"); // 타입
                }
//                else (optionalLifeDetail.get().getLtDetailType().equals("2") {
                else if(optionalLifeDetail.get().getLtDetailType().equals("2")) {
                    // 탄산화깊이
                    log.info("탄산화깊이 아웃풋시작");

                    returnData = durabilityService("2", autoNum);

                    data.put("chartName","carbonation"); // 타입
                }

                else if(optionalLifeDetail.get().getLtDetailType().equals("3")) {
                    // 균열깊이
                    log.info("균열깊이 아웃풋시작");

                    returnData = durabilityService("3", autoNum);

                    data.put("chartName","crack"); // 타입
                }

                else {
                    // 염화물침투량
                    log.info("염화물침투량 아웃풋시작");

                    returnData = durabilityService("4", autoNum);

                    data.put("chartName","chloride"); // 타입
                }

                data.put("publicYear",returnData.get("publicYear")); // 공용연수
                data.put("ltRecoveryList",returnData.get("ltRecoveryList")); // 회복율
                data.put("ltCostList",returnData.get("ltCostList")); // 비용

                data.put("ltRepairLength",returnData.get("ltRepairLength")); // 보수보강 총 길이
                data.put("repairNum",returnData.get("repairNum")); // 보수보강 개입 횟수
                data.put("repairCost",returnData.get("repairCost")); // 보수보강 총 비용(만원)r®

                data.put("pfList",returnData.get("pfList")); // 무조치 시 PF
                data.put("bList",returnData.get("bList")); // 무조치 시 B

                data.put("bOneList",returnData.get("bOneList")); // 유지보수 개입 시 B1
                data.put("bTwoList",returnData.get("bTwoList")); // 유지보수 개입 시 B2

                data.put("pf_max",returnData.get("pf_max")); // 손상확률 최대값(PF 최댓값)
                data.put("pf_min",returnData.get("pf_min")); // 손상확률 최소값(PF 최소값)
                data.put("bmax",returnData.get("bmax")); // 유지보수 개입 시 신뢰성지수최대값

                data.put("ltRecoveryPercent",returnData.get("ltRecoveryPercent")); // 유지보수 개입 시 보수보강 회복율
                data.put("maxYear",returnData.get("maxYear")); // 유지보수 무조치 가능한 최대년수

                data.put("ltTargetValue",returnData.get("ltTargetValue")); // 생애주기 목표값(성능유지 기준값)
                data.put("noactionChartDataList",returnData.get("noactionChartDataList"));
                data.put("actionChartDataList",returnData.get("actionChartDataList"));
                data.put("repairYn",returnData.get("repairYn"));

            }

            else if(optionalLifeDetail.get().getLtDetailType().equals("5")) {
                // 탄산화깊이 바닥판3개
                log.info("탄산화깊이 바닥판3개 아웃풋시작");

                HashMap<String, Object> returnData = durabilityPlateService("5", autoNum);

                // 보수보강 불필요
                if(returnData.get("repairYn").equals("N")){
                    data.put("noactionChartDataList",returnData.get("noactionChartDataList"));
                    data.put("b_List_before_plate1",returnData.get("b_List_before_plate1"));
                    data.put("b_List_before_plate2",returnData.get("b_List_before_plate2"));
                    data.put("b_List_before_plate3",returnData.get("b_List_before_plate3"));
                    data.put("publicYear",returnData.get("publicYear"));
                    data.put("bmax",returnData.get("bmax"));
                    data.put("pf_min",returnData.get("pf_min"));
                    data.put("ltTargetValue",returnData.get("ltTargetValue"));
                    data.put("repairYn","N");
                }
                // 보수보강 필요
                else{
                    data.put("repairYn","Y");

                    data.put("publicYear",returnData.get("publicYear")); // 공용연수
                    data.put("ltRecoveryList",returnData.get("ltRecoveryList")); // 회복율
                    data.put("ltCostList",returnData.get("ltCostList")); // 비용

                    data.put("ltRepairLength",returnData.get("ltRepairLength")); // 보수보강 총 길이
                    data.put("repairNum",returnData.get("repairNum")); // 보수보강 개입 횟수
                    data.put("repairCost",returnData.get("repairCost")); // 보수보강 총 비용(만원)r®

                    data.put("pfList",returnData.get("pfList")); // 무조치 시 PF
                    data.put("bList",returnData.get("bList")); // 무조치 시 B

                    data.put("bOneList",returnData.get("bOneList")); // 유지보수 개입 시 B1
                    data.put("bTwoList",returnData.get("bTwoList")); // 유지보수 개입 시 B2

                    data.put("pf_max",returnData.get("pf_max")); // 손상확률 최대값(PF 최댓값)
                    data.put("pf_min",returnData.get("pf_min")); // 손상확률 최소값(PF 최소값)
                    data.put("bmax",returnData.get("bmax")); // 유지보수 개입 시 신뢰성지수최대값
                    data.put("bmax2",returnData.get("bmax2")); // 유지보수 개입 시 신뢰성지수최대값

                    data.put("ltRecoveryPercent",returnData.get("ltRecoveryPercent")); // 유지보수 개입 시 보수보강 회복율
                    data.put("maxYear",returnData.get("maxYear")); // 유지보수 무조치 가능한 최대년수

                    data.put("ltTargetValue",returnData.get("ltTargetValue")); // 생애주기 목표값(성능유지 기준값)

                    data.put("noactionChartDataList",returnData.get("noactionChartDataList"));
                    data.put("actionChartDataList",returnData.get("actionChartDataList"));

                }

                data.put("chartName","carbonationThreePlate"); // 타입

            }

//            else if(optionalLifeDetail.get().getLtDetailType().equals("6")) {
//                // 탄산화깊이(바닥판4개)
//                log.info("탄산화깊이(바닥판4개) 아웃풋시작");
//            }

            else{
                return ResponseEntity.ok(res.fail("문자", "존재하지 않은 타입번호 입니다. ", "문자", "타입번호 : "+optionalLifeDetail.get().getLtDetailType()));
            }
        } else{
            return ResponseEntity.ok(res.fail("문자", "존재하지 않은 고유코드 입니다. ", "문자", "고유코드 : "+autoNum));
        }

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    // 내구성4종(반발경도,탄산화깊이, 균열깊이, 열화물침투량) 아웃풋 계산 알고리즘
    public HashMap<String,Object> durabilityService(String number, String autoNum){

        HashMap<String, Object> returnData = new HashMap<>();

        // 변수 선언자리
        List<Double> ltRecoveryList = new ArrayList<>();
        List<Double> ltCostList = new ArrayList<>();

        List<Integer> pf_0List = new ArrayList<>(); // 무조치시 PF<0 리스트
        List<Double> pf_List = new ArrayList<>(); // 무조치시 PF 리스트
        List<Double> b_List = new ArrayList<>(); // 무조치시 B 리스트

        double pf_max ; // 손상확률 최대값(PF 최댓값)
        double pf_min; //손상확률 최소값
        double b_max; // 신뢰성 지수 최대값

        List<Double> b1_List = new ArrayList<>(); // 유지보수 개입 시 B1 리스트
        List<Double> b2_List = new ArrayList<>(); // 유지보수 개입 시 B2 리스트

        List<Integer> step1_list = new ArrayList<>(); // 우측의 step1_list
        List<Double> step2_list = new ArrayList<>(); // 우측의 step2_list
        int step1_value = 0; // 우측의 step1 값
        int maxYear = 0; // 유지보수 무조치 가능한 최대년수

        List<Double> referenceTable_List = new ArrayList<>(); // 우측 레퍼런스테이블

        List<Integer> adjustYear_list = new ArrayList<>(); // 하단의 공용연수 리스트

        int repairNum = 0; // 보수보강 개입 횟수
        double repairCost = 0; // 보수보강 총 비용(만원)

        int ltSimulation; // 시뮬레이션 횟수
        Double ltRepairLength = 0.0; // 보수보강 총길이
        Double ltTargetValue = 0.0; // 생애주기 목표값
        Double ltRecoveryPercent = 0.0; // 보수보강 회복율

//        int simulation; // 시뮬레이션 횟수

        // adjustYear_list 계산
        int b1_num = 1; // B1,B2 리스트의 두번째를 제어
        int b2_num = 0; // B2 첫번째값 제어

        double years = 0; // 공용연수 초기화용
        int publicYear = 0;

        int checkNum = 0;  // 우측의 step1_list 도는 횟수

        if(number.equals("1")){
            HardnessInfoDto hardnessInfoDto = hardnessRepository.findByLtAutoNum(autoNum);

            ltRecoveryList.add(hardnessInfoDto.getLtRecoveryOne());
            ltRecoveryList.add(hardnessInfoDto.getLtRecoveryTwo());
            ltRecoveryList.add(hardnessInfoDto.getLtRecoveryThree());
            ltRecoveryList.add(hardnessInfoDto.getLtRecoveryFour());
            ltRecoveryList.add(hardnessInfoDto.getLtRecoveryFive());
            ltRecoveryList.add(hardnessInfoDto.getLtRecoverySix());
            ltCostList.add(hardnessInfoDto.getLtCostOne());
            ltCostList.add(hardnessInfoDto.getLtCostTwo());
            ltCostList.add(hardnessInfoDto.getLtCostThree());
            ltCostList.add(hardnessInfoDto.getLtCostFour());
            ltCostList.add(hardnessInfoDto.getLtCostFive());
            ltCostList.add(hardnessInfoDto.getLtCostSix());

            ltSimulation = hardnessInfoDto.getLtSimulation()+1; // 시뮬레이션 횟수
            ltRepairLength = hardnessInfoDto.getLtRepairLength(); // 보수보강 총길이
            ltTargetValue = hardnessInfoDto.getLtTargetValue(); // 생애주기 목표값
            ltRecoveryPercent = hardnessInfoDto.getLtRecoveryPercent(); // 보수보강 회복율

            years = hardnessInfoDto.getLtYserviceAverage(); // 공용연수 평균값

            checkNum = 21;

            // 사용자 입력 난수 -  평균값
            double fdAverage = hardnessInfoDto.getLtFdAverage(); // 설계압축강도 평균값
            double yAverage = hardnessInfoDto.getLtYAverage(); // 노후재령계수 평균값
            double sAverage = hardnessInfoDto.getLtSAverage(); //  하면 반발경도 평균값

            log.info("fdAverage : "+fdAverage);
            log.info("yAverage : "+yAverage);
            log.info("sAverage : "+sAverage);

            // 사용자 입력 난수 -  표준편차
            double fdStandard = hardnessInfoDto.getLtFdStandard(); // 설계압축강도 표준편차
            double yStandard = hardnessInfoDto.getLtYStandard(); // 노후재령계수 표준편차
            double sStandard = hardnessInfoDto.getLtSStandard(); //  하면 반발경도 표준편차

            log.info("fdStandard : "+fdStandard);
            log.info("yStandard : "+yStandard);
            log.info("sStandard : "+sStandard);

            double cov = 0.05;
            double[] damageRateMean = {0.3, 0.306, 0.3121, 0.3184, 0.3247, 0.3312, 0.3378, 0.3446, 0.3515, 0.3585,
                    0.3657, 0.373, 0.3805, 0.3881,0.3958, 0.4038, 0.4118, 0.4201, 0.4285, 0.437, 0.4458};
            log.info("damageRateMean : "+ Arrays.toString(damageRateMean));
            log.info("damageRateMean : "+damageRateMean.length);
            // 압축강도 연간 표
            List<Double> damageRateStd = new ArrayList<>();
            for(int i=0; i<21; i++){
                damageRateStd.add(Math.floor(damageRateMean[i]*cov*10000)/10000);
            }
            log.info("damageRateStd : "+damageRateStd);
            log.info("damageRateStd : "+damageRateStd.size());

            publicYear = (int) years;

            // 성능평가
            double rand;
            for(int area=0; area<years+1; area++){
                int pf_Count = 0; // PF<0 카운트
                double result;
                for(int i=0; i<ltSimulation; i++) {
//                    log.info("");
                    rand = Math.random();
                    double fdNum = NormMath.inv(rand, fdAverage, fdStandard); // fd 난수(설계압축강도)
//                    log.info("fdNum : "+fdNum);
                    rand = Math.random();
                    double yNum = NormMath.inv(rand, yAverage, yStandard); // Y 난수(노후재령계수)
//                    log.info("yNum : "+yNum);
                    rand = Math.random();
                    double sNum = NormMath.inv(rand, sAverage, sStandard); // S 난수(하면 반발경도)
//                    log.info("sNum : "+sNum);
                    rand = Math.random();
                    double rateNum = NormMath.inv(rand, damageRateMean[area], damageRateStd.get(area)); // S 난수(하면 반발경도)
//                    log.info("rateNum : "+rateNum);

                    // =(((13*$F21-184)+(7.3*$F21+100))*$E21*0.098/2)/$C21-$L21  -> 엑셀 식
                    result = (((13*sNum-184)+(7.3*sNum+100))*yNum*0.098/2)/fdNum-rateNum;
//                    log.info("result : "+result);
//                    log.info("");
                    if(result<0){
                        pf_Count++;
                    }
                }

//                log.info(area+"번 pf_Count : "+pf_Count);
                double pf = (double)pf_Count/ltSimulation;
                // Pf 리스트
                pf_List.add(pf);
                pf_0List.add(pf_Count);
            }

        }
        else if(number.equals("2")) {
            log.info("탄산화깊이");

            CabonationInfoDto cabonationInfoDto = cabonationRepository.findByLtAutoNum(autoNum);
            ltRecoveryList.add(cabonationInfoDto.getLtRecoveryOne());
            ltRecoveryList.add(cabonationInfoDto.getLtRecoveryTwo());
            ltRecoveryList.add(cabonationInfoDto.getLtRecoveryThree());
            ltRecoveryList.add(cabonationInfoDto.getLtRecoveryFour());
            ltRecoveryList.add(cabonationInfoDto.getLtRecoveryFive());
            ltRecoveryList.add(cabonationInfoDto.getLtRecoverySix());
            ltCostList.add(cabonationInfoDto.getLtCostOne());
            ltCostList.add(cabonationInfoDto.getLtCostTwo());
            ltCostList.add(cabonationInfoDto.getLtCostThree());
            ltCostList.add(cabonationInfoDto.getLtCostFour());
            ltCostList.add(cabonationInfoDto.getLtCostFive());
            ltCostList.add(cabonationInfoDto.getLtCostSix());

            ltSimulation = cabonationInfoDto.getLtSimulation()+1; // 시뮬레이션 횟수
            ltRepairLength = cabonationInfoDto.getLtRepairLength(); // 보수보강 총길이
            ltTargetValue = cabonationInfoDto.getLtTargetValue(); // 생애주기 목표값
            ltRecoveryPercent = cabonationInfoDto.getLtRecoveryPercent(); // 보수보강 회복율

            // 사용자 입력 난수 -  평균값
            double tdAverage = cabonationInfoDto.getLtTdAverage(); // 실측피복두께 평균값
            double aAverage = cabonationInfoDto.getLtAAverage(); // 탄산화속도계수 평균값
            // double cAverage = cabonationInfoDto.getLtCAverage(); // 탄산화깊이 평균값 - 어디서쓰는지 모름 확인필요

            // 사용자 입력 난수 -  표준편차
            double tdStandard = cabonationInfoDto.getLtTdStandard(); // 실측피복두께 표준편차
            double yVariance = cabonationInfoDto.getLtYVariance()/100; // 공용연수 변동계수
            double aStandard = cabonationInfoDto.getLtAStandard(); // 탄산화속도계수 표준편차
            // double cStandard = cabonationInfoDto.getLtCStandard(); // 탄산화깊이 표준편차 - 어디서쓰는지 모름 확인필요

            years = cabonationInfoDto.getLtYAverage(); // 공용연수 평균값

            publicYear = (int) years;

            checkNum = 22;

            // 성능평가
            double rand;
            for(int area=publicYear; area<publicYear+21; area++){
                int pf_Count = 0; // PF<0 카운트
                double result;
                for(int i=0; i<ltSimulation; i++) {
                    rand = Math.random();
                    double tdNum = NormMath.inv(rand, tdAverage, tdStandard); // td 난수

                    rand = Math.random();
                    double yNum = NormMath.inv(rand, 1, yVariance); // Y 난수

                    rand = Math.random();
                    double aNum = NormMath.inv(rand, aAverage, aStandard); // A 난수

                    // =$C21- $E21*SQRT(X$17*$D21)  -> 엑셀 식
                    result = tdNum-aNum*Math.sqrt(area*yNum);

                    if(result<0){
                        pf_Count++;
                    }
                }

//                log.info(area+"번 pf_Count : "+pf_Count);
                double pf = (double)pf_Count/ltSimulation;
                // Pf 리스트
                pf_List.add(pf);
                pf_0List.add(pf_Count);
            }

        }
        else if(number.equals("3")) {
            CrackInfoDto crackInfoDto = crackRepository.findByLtAutoNum(autoNum);
            ltRecoveryList.add(crackInfoDto.getLtRecoveryOne());
            ltRecoveryList.add(crackInfoDto.getLtRecoveryTwo());
            ltRecoveryList.add(crackInfoDto.getLtRecoveryThree());
            ltRecoveryList.add(crackInfoDto.getLtRecoveryFour());
            ltRecoveryList.add(crackInfoDto.getLtRecoveryFive());
            ltRecoveryList.add(crackInfoDto.getLtRecoverySix());
            ltCostList.add(crackInfoDto.getLtCostOne());
            ltCostList.add(crackInfoDto.getLtCostTwo());
            ltCostList.add(crackInfoDto.getLtCostThree());
            ltCostList.add(crackInfoDto.getLtCostFour());
            ltCostList.add(crackInfoDto.getLtCostFive());
            ltCostList.add(crackInfoDto.getLtCostSix());

            ltSimulation = crackInfoDto.getLtSimulation()+1; // 시뮬레이션 횟수
            ltRepairLength = crackInfoDto.getLtRepairLength(); // 보수보강 총길이
            ltTargetValue = crackInfoDto.getLtTargetValue(); // 생애주기 목표값
            ltRecoveryPercent = crackInfoDto.getLtRecoveryPercent(); // 보수보강 회복율

            // 사용자 입력 난수 -  평균값
            double tdAverage = crackInfoDto.getLtTdAverage(); // 설계피복두깨 평균값
            double lAverage = crackInfoDto.getLtLAverage(); // 발신수신자 사이의 거리 평균값
            double toAverage = crackInfoDto.getLtToAverage(); // 균열있는경우 전달시간 평균값
            double tcAverage = crackInfoDto.getLtTcAverage(); // 균열없는경우 전달시간 평균값

            // 사용자 입력 난수 -  표준편차
            double tdStandard = crackInfoDto.getLtTdStandard(); // 설계피복두깨 표준편차
            double lStandard = crackInfoDto.getLtLStandard(); // 발신수신자 사이의 거리 표준편차
            double tcStandard = crackInfoDto.getLtTcStandard(); // 균열있는경우 전달시간 표준편차
            double toStandard = crackInfoDto.getLtToStandard(); // 균열없는경우 전달시간 표준편차

            List<Double> damageRateMean = new ArrayList<>();
            for(int i=0; i<21; i++){
                if(i != 0){
                    double val = damageRateMean.get(i-1);
                    damageRateMean.add(val+0.01);
                }else{
                    damageRateMean.add(1.0);
                }
            }
            log.info("damageRateMean : "+damageRateMean);
            log.info("damageRateMean.size() : "+damageRateMean.size());

            double cov = 0.05;
            // 압축강도 연간 표
            List<Double> damageRateStd = new ArrayList<>();
            for(int i=0; i<damageRateMean.size(); i++){
                damageRateStd.add(Math.floor(damageRateMean.get(i)*cov*10000)/10000);
            }
            log.info("damageRateStd : "+damageRateStd);
            log.info("damageRateStd.size() : "+damageRateStd.size());

            years = crackInfoDto.getLtPublicYear(); // 공용연수 평균값

            publicYear = (int) years;

            checkNum = 21;

            // 성능평가
            int num = 0;
            double rand;
            for(int area=publicYear; area<publicYear+21; area++){
                int pf_Count = 0; // PF<0 카운트
                double result;
                for(int i=0; i<ltSimulation; i++) {
                    rand = Math.random();
                    double tdNum = NormMath.inv(rand, tdAverage, tdStandard); // td 난수
//                    log.info("tdNum : "+tdNum);

                    rand = Math.random();
                    double lNum = NormMath.inv(rand, lAverage, lStandard); // L 난수
//                    log.info("lNum : "+lNum);

                    rand = Math.random();
                    double tcNum = NormMath.inv(rand, tcAverage, tcStandard); // Tc 난수
//                    log.info("tcNum : "+tcNum);

                    rand = Math.random();
                    double toNum = NormMath.inv(rand, toAverage, toStandard); // To 난수
//                    log.info("toNum : "+toNum);

                    rand = Math.random();
                    double rateNum = NormMath.inv(rand, damageRateMean.get(num), damageRateStd.get(num));
//                    log.info("rateNum : "+rateNum);

                    // =$C22-($D22/2)*SQRT($E22^2/$F22^2-1)*L22  -> 엑셀 식
//                    double a = Math.pow(tcNum,2);
//                    double b = Math.pow(toNum,2);
//                    double c = Math.sqrt(b/a-1);
//                    double e = lNum/2;
                    result = tdNum - lNum/2 * Math.sqrt(Math.pow(tcNum,2)/Math.pow(toNum,2)-1) * rateNum;

                    if(result<0){
                        pf_Count++;
                    }
                }

//                log.info(area+"번 pf_Count : "+pf_Count);
                double pf = (double)pf_Count/ltSimulation;
                // Pf 리스트
                num++;
                pf_List.add(pf);
                pf_0List.add(pf_Count);
            }

        }
        else if(number.equals("4")) {
            ChlorideInfoDto chlorideInfoDto = chlorideRepository.findByLtAutoNum(autoNum);
            ltRecoveryList.add(chlorideInfoDto.getLtRecoveryOne());
            ltRecoveryList.add(chlorideInfoDto.getLtRecoveryTwo());
            ltRecoveryList.add(chlorideInfoDto.getLtRecoveryThree());
            ltRecoveryList.add(chlorideInfoDto.getLtRecoveryFour());
            ltRecoveryList.add(chlorideInfoDto.getLtRecoveryFive());
            ltRecoveryList.add(chlorideInfoDto.getLtRecoverySix());
            ltCostList.add(chlorideInfoDto.getLtCostOne());
            ltCostList.add(chlorideInfoDto.getLtCostTwo());
            ltCostList.add(chlorideInfoDto.getLtCostThree());
            ltCostList.add(chlorideInfoDto.getLtCostFour());
            ltCostList.add(chlorideInfoDto.getLtCostFive());
            ltCostList.add(chlorideInfoDto.getLtCostSix());

            ltSimulation = chlorideInfoDto.getLtSimulation()+1; // 시뮬레이션 횟수
            ltRepairLength = chlorideInfoDto.getLtRepairLength(); // 보수보강 총길이
            ltTargetValue = chlorideInfoDto.getLtTargetValue(); // 생애주기 목표값
            ltRecoveryPercent = chlorideInfoDto.getLtRecoveryPercent(); // 보수보강 회복율

            // 사용자 입력 난수 -  평균값
            double coAverage = chlorideInfoDto.getLtCoAverage(); // 표면 염화물함유량 평균값
            double climAverage = chlorideInfoDto.getLtClimAverage(); // 임계 염화물함유량 사이의 거리 평균값
            double dAverage = chlorideInfoDto.getLtDAverage(); // 염소이온 확산계수 평균값
            double xAverage = chlorideInfoDto.getLtXAverage(); // 피복두께 평균값

            // 사용자 입력 난수 -  표준편차
            double coStandard = chlorideInfoDto.getLtCoStandard(); // 표면 염화물함유량 표준편차
            double climStandard = chlorideInfoDto.getLtClimStandard(); // 임계 염화물함유량 사이의 거리 표준편차
            double dStandard = chlorideInfoDto.getLtDStandard(); // 염소이온 확산계수 표준편차
            double xStandard = chlorideInfoDto.getLtXStandard(); // 피복두께 표준편차


            List<Double> damageRateMean = new ArrayList<>();
            for(int i=0; i<21; i++){
                if(i != 0){
                    double val = damageRateMean.get(i-1);
                    damageRateMean.add(val-val*0.02);
                }else{
                    damageRateMean.add(1.0);
                }
            }
            log.info("damageRateMean : "+damageRateMean);
            log.info("damageRateMean.size() : "+damageRateMean.size());

            double cov = 0.05;
            // 압축강도 연간 표
            List<Double> damageRateStd = new ArrayList<>();
            for(int i=0; i<damageRateMean.size(); i++){
                damageRateStd.add(Math.floor(damageRateMean.get(i)*cov*10000)/10000);
            }
            log.info("damageRateStd : "+damageRateStd);
            log.info("damageRateStd : "+damageRateStd.size());

            years = chlorideInfoDto.getLtPublicYear(); // 공용연수 평균값

            publicYear = (int) years;

            checkNum = 21;

            // 성능평가
            int num = 0;
            double rand;
            for(int area=publicYear; area<publicYear+21; area++){
                int pf_Count = 0; // PF<0 카운트
                double result;
                for(int i=0; i<ltSimulation; i++) {
                    rand = Math.random();
                    double coNum = NormMath.inv(rand, coAverage, coStandard); // co 난수
//                    System.out.println();
//                    log.info("coNum : "+coNum);

                    rand = Math.random();
                    double climNum = NormMath.inv(rand, climAverage, climStandard); // clim 난수
//                    log.info("climNum : "+climNum);

                    rand = Math.random();
                    double dNum = NormMath.inv(rand, dAverage, dStandard); // d 난수
//                    log.info("dNum : "+dNum);

                    rand = Math.random();
                    double xNum = NormMath.inv(rand, xAverage, xStandard); // x 난수
//                    log.info("xNum : "+xNum);

                    rand = Math.random();
                    double rateNum = NormMath.inv(rand, damageRateMean.get(num), damageRateStd.get(num));
//                    log.info("rateNum : "+rateNum);

                    // =$E22*$G22-$C22*(1-ERF($D22/(2*SQRT($F22*AC$18))))  -> 엑셀 식
                    result = climNum*rateNum-coNum*(1-NormMath.erf(xNum/(2*Math.sqrt(dNum*area))));
//                    log.info("result : "+result);
//                    System.out.println();

                    if(result<0){
                        pf_Count++;
                    }
                }

//                log.info(area+"번 pf_Count : "+pf_Count);
                double pf = (double)pf_Count/ltSimulation;
                // Pf 리스트
                num++;
                pf_List.add(pf);
                pf_0List.add(pf_Count);
            }

        }

        System.out.println();
        log.info("무조치시 PF<0 pf_0List : " + pf_0List);
        log.info("무조치시 PF<0 pf_0List.size() : " + pf_0List.size());
        log.info("무조치시 PF pf_List : " + pf_List);
        log.info("무조치시 PF pf_List.size() : " + pf_List.size());
        System.out.println();

        log.info("1단계 시작");
        // B(베타)리스트, b1_list 계산
        for(int i=0; i<pf_List.size(); i++){
            if(i == pf_List.size()-1){
                if(pf_List.get(i) == 0){
                    b_List.add(-NormMath.sinv(0.00000001));
                }else{
                    b_List.add(-NormMath.sinv(pf_List.get(i)));
                }
            }else{
                if(pf_List.get(i) == 0){
                    b_List.add(pf_List.get(i+1)*1.01);
                }else{
                    b_List.add(-NormMath.sinv(pf_List.get(i)));
                }
            }
            if(b_List.get(i)<ltTargetValue){
                b1_List.add(0.0);
                if(maxYear == 0){
                    maxYear = publicYear-1;
                }
            }else{
                b1_List.add(b_List.get(i));
            }
            publicYear ++;
        }
        log.info("무조치시 B b_List : " + b_List);
        log.info("무조치시 B b_List.size() : " + b_List.size());
        log.info("유지보수시 b1_List : " + b1_List);
        log.info("유지보수시 b1_List.size() : " + b1_List.size());
        log.info("유지보수 무조치 가능한 최대년수 maxYear : " + maxYear);
        System.out.println();

        // 공용연수 초기화
        publicYear = (int) years;

        log.info("2단계");
        // 우측의 step2_list, step1_value 계산
        int a = 0;
        for(int i=publicYear; i<publicYear+21; i++){
            double value;
            if(i<maxYear) {
                value = b_List.get(a)*ltRecoveryPercent;
            }else{
                value = 0.0;
            }

            if(ltTargetValue<value){
                step1_value ++;
            }
            step2_list.add(value);
            a++;
        }
        log.info("우측의 step2_list : " + step2_list);
        log.info("우측의 step2_list.size() : " + step2_list.size());
        log.info("우측의 step1_value 값 : " + step1_value);

        if(step1_value != 0 && maxYear != 0){
            log.info("유지보수가 필요합니다.");

            log.info("3단계");
            // 우측의 step1_list 계산
            int x = publicYear;
            for(int i=publicYear+1; i<publicYear+checkNum; i++) {
                step1_list.add(x+1);
                if (i % step1_value == 0) {
                    x = publicYear;
                }else{
                    x++;
                }
            }
            log.info("우측의 step1_list : " + step1_list);
            log.info("우측의 step1_list.size() : " + step1_list.size());

            log.info("4단계");
            b_List.sort(Collections.reverseOrder()); // b_List 내림차순으로 정렬
            int y = publicYear;
            for(int i=0; i<step1_list.size(); i++) {
                int step = step1_list.get(i)-y;
                double b_List_value = b_List.get(step-1);
                referenceTable_List.add(b_List_value*ltRecoveryPercent);
            }
            log.info("우측의 referenceTable_List : " + referenceTable_List);
            log.info("우측의 referenceTable_List.size() : " + referenceTable_List.size());

            log.info("5단계");
            // b2_list 계산
            int z = 0;
            int c = 0;
            for(int i=publicYear; i<publicYear+21; i++) {
                if(i-maxYear<0 || i-maxYear==0){
                    b2_List.add(b1_List.get(z));
                    z++;
                }else{
                    b2_List.add(referenceTable_List.get(c));
                    c++;
                }
            }
            log.info("유지보수시 b2_List : " + b2_List);
            log.info("유지보수시 b2_List.size() : " + b2_List.size());

            log.info("6단계");
            int publicYearNum = publicYear;
            for(int i=publicYear; i<publicYear+21; i++) {
                // 처음엔 기본 공용연수 값 넣기
                if(i==publicYear){
                    adjustYear_list.add(publicYear);
                }else{
                    double b1_value1 = b1_List.get(b1_num);
                    double b2_value1 = b2_List.get(b1_num);
                    double b2_value2 = b2_List.get(b2_num);

                    if(b1_value1 < b2_value1 || b1_value1 > b2_value1){
                        if(b2_value2 < b2_value1){
                            adjustYear_list.add(publicYearNum);
                            repairNum++;
                        }else{
                            adjustYear_list.add(publicYearNum+1);
                        }
                    }else{
                        adjustYear_list.add(publicYearNum+1);
                    }
                    publicYearNum++;
                    b1_num++;
                    b2_num++;
                }
            }
            log.info("하단의 공용연수 adjustYear_list : " + adjustYear_list);
            log.info("하단의 공용연수 adjustYear_list.size() : " + adjustYear_list.size());
            log.info("보수보강 개입 횟수 repairNum : " + repairNum);

            log.info("7단계");
            for(int i=0; i<ltRecoveryList.size(); i++){
                if(ltRecoveryPercent.equals(ltRecoveryList.get(i))){
                    repairCost = Integer.parseInt(String.valueOf(Math.round(ltRepairLength * repairNum * ltCostList.get(i))));
                    break;
                }
            }
            log.info("보수보강 총 비용(만원) repairCost : " + repairCost);
        }else{
            log.info("유지보수가 필요없습니다.");
        }

        pf_max = Collections.max(pf_List);
        pf_min = -NormMath.sinv(pf_max);
        b_max =  Collections.max(b_List);
        System.out.println();
        log.info("손상확률 최대값(PF 최댓값) : " + pf_max);
        log.info("손상확률 최소값: " + pf_min);
        log.info("신뢰성 지수 최대값 : " + b_max);
        System.out.println();

        // 차트의 JSON정보를 담을 Array 선언
        List<HashMap<String,Object>> noactionChartDataList = new ArrayList<>(); // 무조치 시 차트데이터
        List<HashMap<String,Object>> actionChartDataList = new ArrayList<>(); // 유지보수 개입시 차트데이터
        HashMap<String,Object> noactionchartData;
        HashMap<String,Object> actionchartData;

        log.info("8단계 끝");
        int chartNum = 0;
        // 차트데이터 값 for문 알고리즘 20번돌아야됨
        for(int chartData=publicYear; chartData< publicYear+21; chartData++){

            // 그래프로 보낼 데이터 뽑기 여기서 시작
            noactionchartData  = new HashMap<>();
            noactionchartData.put("publicYear", chartData);
            noactionchartData.put("redline", ltTargetValue);
            noactionchartData.put("noaction", Math.floor(b_List.get(chartNum)*1000)/1000.0);
            noactionChartDataList.add(noactionchartData);

            if(step1_value != 0 && maxYear != 0){
                actionchartData  = new HashMap<>();
                actionchartData.put("publicYear", adjustYear_list.get(chartNum));
                actionchartData.put("redline", ltTargetValue);
                actionchartData.put("action", Math.floor(b2_List.get(chartNum)*1000)/1000.0);
                actionChartDataList.add(actionchartData);
            }

            chartNum++;
        }

        System.out.println();
        log.info("무조치 차트 테스트 : "+noactionChartDataList);
        log.info("무조치 차트 데이터 길이 : "+noactionChartDataList.size());
        log.info("유지보수 차트 테스트 : "+actionChartDataList);
        log.info("유지보수 차트 데이터 길이 : "+actionChartDataList.size());

        returnData.put("publicYear",years); // 공용연수
        returnData.put("ltRecoveryList",ltRecoveryList); // 회복율
        returnData.put("ltCostList",ltCostList); // 비용

        returnData.put("ltRepairLength",ltRepairLength); // 보수보강 총 길이
        returnData.put("repairNum",repairNum); // 보수보강 개입 횟수
        returnData.put("repairCost",repairCost); // 보수보강 총 비용(만원)r®

        returnData.put("pfList",pf_List); // 무조치 시 PF
        returnData.put("bList",b_List); // 무조치 시 B

        returnData.put("bOneList",b1_List); // 유지보수 개입 시 B1
        returnData.put("bTwoList",b2_List); // 유지보수 개입 시 B2

        returnData.put("pf_max",Math.floor(pf_max*1000)/1000.0); // 손상확률 최대값(PF 최댓값)
        returnData.put("pf_min",Math.floor(pf_min*1000)/1000.0); // 손상확률 최소값(PF 최소값)
        returnData.put("bmax",Math.floor(b_max*1000)/1000.0); // 유지보수 개입 시 신뢰성지수최대값

        returnData.put("ltRecoveryPercent",ltRecoveryPercent*100); // 유지보수 개입 시 보수보강 회복율
        returnData.put("maxYear",maxYear); // 유지보수 무조치 가능한 최대년수

        returnData.put("ltTargetValue",ltTargetValue); // 생애주기 목표값(성능유지 기준값)
        returnData.put("noactionChartDataList",noactionChartDataList);
        returnData.put("actionChartDataList",actionChartDataList);
        returnData.put("repairYn","Y");

        return returnData;
    }

    // 탄산화깊이 (바닥판3개, 4개, 교량교갹 등) 아웃풋 계산 알고리즘
    public HashMap<String,Object> durabilityPlateService(String number, String autoNum) {

        HashMap<String, Object> returnData = new HashMap<>();
        CabonationThreePlateInfoDto cabonationThreePlateInfoDto = cabonationThreePlateRepository.findByLtAutoNum(autoNum);

        // 변수 선언자리
        List<Double> ltRecoveryList = new ArrayList<>();
        List<Double> ltCostList = new ArrayList<>();
        double b_target; // 생애주기 목표값
        double noaction_b_max; // 무조치시 신뢰성 지수 최대값
        List<Double> noaction_pf_List = new ArrayList<>(); // 아웃풋 Pf리스트
        List<Double> noaction_b_List = new ArrayList<>(); // 아웃풋 B리스트
        double pf_max = 0.0; // 손상확률 최대값(PF 최댓값)
        double pf_min = 0.0; //손상확률 최소값
        double b_min = 0.0; // 신뢰성 지수 최소값
        double b_max = 0.0; // 신뢰성 지수 최대값
        List<Double> b1_List = new ArrayList<>(); // 유지보수 개입 시 B1 리스트
        List<Double> b2_List = new ArrayList<>(); // 유지보수 개입 시 B2 리스트
        List<Integer> step1_list = new ArrayList<>(); // 우측의 step1_list
        List<Double> step2_list = new ArrayList<>(); // 우측의 step2_list
        int step1_value = 0; // 우측의 step1 값
        int maxYear = 0; // 유지보수 무조치 가능한 최대년수
        List<Double> referenceTable_List = new ArrayList<>(); // 우측 레퍼런스테이블
        List<Integer> adjustYear_list = new ArrayList<>(); // 하단의 공용연수 리스트
        int repairNum = 0; // 보수보강 개입 횟수
        double repairCost = 0; // 보수보강 총 비용(만원)
        int simulation; // 시뮬레이션 횟수

        if(number.equals("5")){
            List<Double> pf_List_before_plate1 = new ArrayList<>(); // 바닥판1의 PF(Before) 리스트
            List<Double> b_List_before_plate1 = new ArrayList<>(); // 바닥판1의 bBBefore) 리스트
            List<Integer> pf_List_zero_plate1 = new ArrayList<>(); // 바닥판1의 PF<0 리스트
            List<Double> pf_List_plate1 = new ArrayList<>(); // 바닥판1의 PF 리스트
            List<Double> b_List_plate1 = new ArrayList<>(); // 바닥판1의 B 리스트
            List<Double> pf_List_before_plate2 = new ArrayList<>(); // 바닥판2의 PF(Before) 리스트
            List<Double> b_List_before_plate2 = new ArrayList<>(); // 바닥판2의 bBBefore) 리스트
            List<Integer> pf_List_zero_plate2 = new ArrayList<>(); // 바닥판2의 PF<0 리스트
            List<Double> pf_List_plate2 = new ArrayList<>(); // 바닥판2의 PF 리스트
            List<Double> b_List_plate2 = new ArrayList<>(); // 바닥판2의 B 리스트
            List<Double> pf_List_before_plate3 = new ArrayList<>(); // 바닥판3의 PF(Before) 리스트
            List<Double> b_List_before_plate3 = new ArrayList<>(); // 바닥판3의 bBBefore) 리스트
            List<Integer> pf_List_zero_plate3 = new ArrayList<>(); // 바닥판3의 PF<0 리스트
            List<Double> pf_List_plate3 = new ArrayList<>(); // 바닥판3의 PF 리스트
            List<Double> b_List_plate3 = new ArrayList<>(); // 바닥판3의 B 리스트

            // 바닥판 1 : 사용자 입력 난수 -  평균값
            double tdAveragePlate1 = cabonationThreePlateInfoDto.getLtTdAveragePlate1(); // 바닥판1의 실측피복두께 평균값
            double aAveragePlate1 = cabonationThreePlateInfoDto.getLtAAveragePlate1(); // 바닥판1의 탄산화속도계수 평균값
//                double cAveragePlate1 = cabonationThreePlateInfoDto.getLtCAveragePlate1(); // 바닥판1의 탄산화깊이 평균값
            // 바닥판 1 : 사용자 입력 난수 -  표준편차
            double tdStandardPlate1 = cabonationThreePlateInfoDto.getLtTdStandardPlate1(); // 바닥판1의 실측피복두께 표준편차
            double aStandardPlate1 = cabonationThreePlateInfoDto.getLtAStandardPlate1(); // 바닥판1의 탄산화속도계수 표준편차
//                double cStandardPlate1 = cabonationThreePlateInfoDto.getLtCStandardPlate1(); // 바닥판1의 탄산화깊이 표준편차
            // 바닥판 2 : 사용자 입력 난수 -  평균값
            double tdAveragePlate2 = cabonationThreePlateInfoDto.getLtTdAveragePlate2(); // 바닥판2의 실측피복두께 평균값
            double aAveragePlate2 = cabonationThreePlateInfoDto.getLtAAveragePlate2(); // 바닥판2의 탄산화속도계수 평균값
//                double cAveragePlate2 = cabonationThreePlateInfoDto.getLtCAveragePlate2(); // 바닥판2의 탄산화깊이 평균값
            // 바닥판 2 : 사용자 입력 난수 -  표준편차
            double tdStandardPlate2 = cabonationThreePlateInfoDto.getLtTdStandardPlate2(); // 바닥판2의 실측피복두께 표준편차
            double aStandardPlate2 = cabonationThreePlateInfoDto.getLtAStandardPlate2(); // 바닥판2의 탄산화속도계수 표준편차
//                double cStandardPlate2 = cabonationThreePlateInfoDto.getLtCStandardPlate2(); // 바닥판2의 탄산화깊이 표준편차
            // 바닥판 3 : 사용자 입력 난수 -  평균값
            double tdAveragePlate3 = cabonationThreePlateInfoDto.getLtTdAveragePlate3(); // 바닥판3의 실측피복두께 평균값
            double aAveragePlate3 = cabonationThreePlateInfoDto.getLtAAveragePlate3(); // 바닥판3의 탄산화속도계수 평균값
//                double cAveragePlate3 = cabonationThreePlateInfoDto.getLtCAveragePlate3(); // 바닥판3의 탄산화깊이 평균값
            // 바닥판 3 : 사용자 입력 난수 -  표준편차
            double tdStandardPlate3 = cabonationThreePlateInfoDto.getLtTdStandardPlate3(); // 바닥판3의 실측피복두께 표준편차
            double aStandardPlate3 = cabonationThreePlateInfoDto.getLtAStandardPlate3(); // 바닥판3의 탄산화속도계수 표준편차
//                double cStandardPlate3 = cabonationThreePlateInfoDto.getLtCStandardPlate3(); // 바닥판3의 탄산화깊이 표준편차
            // 공용연수 조정계수 : 사용자 입력 난수 -  표준편차, 평균값
            double ltYStandard = cabonationThreePlateInfoDto.getLtYStandard(); // 공용연수 조정계수 표준편차
            double ltYAverage = cabonationThreePlateInfoDto.getLtYAverage(); // 공용연수 조정계수 평균값
            simulation = cabonationThreePlateInfoDto.getLtSimulation()+1; // 시뮬레이션 횟수
            b_target = cabonationThreePlateInfoDto.getLtTargetValue();
            // 공용연수
            int publicYear = cabonationThreePlateInfoDto.getLtPublicYear();
            // 테스트 완료 Apache Commons Math » 3.6.1 활용
            NormalDistribution normalDistribution = new NormalDistribution();
//                double normTest = 1-normalDistribution.cumulativeProbability(2.52);
//                log.info("원하는 결과 : 0.00586, 테스트 결과 : "+Math.floor(normTest*100000)/100000);
            // 성능평가
            double rand;
            for(int area=publicYear; area<publicYear+21; area++){
                int pf_Count_Plate1 = 0; // 바닥판1의 PF<0 카운트
                int pf_Count_Plate2 = 0; // 바닥판2의 PF<0 카운트
                int pf_Count_Plate3 = 0; // 바닥판3의 PF<0 카운트
                double result_Plate1;
                double result_Plate2;
                double result_Plate3;
                for(int i=1; i<simulation; i++) {
                    rand = Math.random();
                    double yNum = NormMath.inv(rand, ltYStandard, ltYAverage); // Y 난수
                    // =$D29- $E29*SQRT(Q$25*$C29)  -> 엑셀 식
                    // 바닥판1
                    rand = Math.random();
                    double tdNumPlate1 = NormMath.inv(rand, tdStandardPlate1, tdAveragePlate1); // 바닥판1의 td 난수
                    rand = Math.random();
                    double aNumPlate1 = NormMath.inv(rand, aStandardPlate1, aAveragePlate1); // 바닥판1의 A 난수
                    result_Plate1 = tdNumPlate1-aNumPlate1*Math.sqrt(area*yNum);
                    // 바닥판2
                    rand = Math.random();
                    double tdNumPlate2 = NormMath.inv(rand, tdStandardPlate2, tdAveragePlate2); // 바닥판2의 td 난수
                    rand = Math.random();
                    double aNumPlate2 = NormMath.inv(rand, aStandardPlate2, aAveragePlate2); // 바닥판2의 A 난수
                    result_Plate2 = tdNumPlate2-aNumPlate2*Math.sqrt(area*yNum);
                    // 바닥판3
                    rand = Math.random();
                    double tdNumPlate3 = NormMath.inv(rand, tdStandardPlate3, tdAveragePlate3); // 바닥판3의 td 난수
                    rand = Math.random();
                    double aNumPlate3 = NormMath.inv(rand, aStandardPlate3, aAveragePlate3); // 바닥판3의 A 난수
                    result_Plate3 = tdNumPlate3-aNumPlate3*Math.sqrt(area*yNum);
                    if(result_Plate1<0){
                        pf_Count_Plate1++;
                    }
                    if(result_Plate2<0){
                        pf_Count_Plate2++;
                    }
                    if(result_Plate3<0){
                        pf_Count_Plate3++;
                    }
                }
                double pf_plate1 = (double)pf_Count_Plate1/simulation;
                double pf_plate2 = (double)pf_Count_Plate2/simulation;
                double pf_plate3 = (double)pf_Count_Plate3/simulation;
                // Pf 리스트
                if(pf_plate1 == 0){
                    pf_List_plate1.add(0.00000001);
                }else{
                    pf_List_plate1.add(pf_plate1);
                }
                if(pf_plate2 == 0){
                    pf_List_plate2.add(0.00000001);
                }else{
                    pf_List_plate2.add(pf_plate2);
                }
                if(pf_plate3 == 0){
                    pf_List_plate3.add(0.00000001);
                }else{
                    pf_List_plate3.add(pf_plate3);
                }
                // Pf<0 리스트
                pf_List_zero_plate1.add(pf_Count_Plate1);
                pf_List_zero_plate2.add(pf_Count_Plate2);
                pf_List_zero_plate3.add(pf_Count_Plate3);
            }
            log.info("");
            log.info("pf_List_plate1 : "+pf_List_plate1);
            log.info("pf_List_plate2 : "+pf_List_plate2);
            log.info("pf_List_plate3 : "+pf_List_plate3);
            log.info("");
            log.info("pf_List_zero_plate1 : "+pf_List_zero_plate1);
            log.info("pf_List_zero_plate2 : "+pf_List_zero_plate2);
            log.info("pf_List_zero_plate3 : "+pf_List_zero_plate3);
            for(int i=0; i<pf_List_plate1.size(); i++){

                double bPlate1;
                double bPlate2;
                double bPlate3;

                if(i == pf_List_plate1.size()-1){
                    bPlate1 = -NormMath.sinv(pf_List_plate1.get(i));
                }
                else{
                    if(pf_List_plate1.get(i) == 0.00000001 || pf_List_plate1.get(i).equals(pf_List_plate1.get(i+1))){
                        bPlate1 = -NormMath.sinv(pf_List_plate1.get(i+1));
                    }else{
                        bPlate1 = -NormMath.sinv(pf_List_plate1.get(i));
                    }
                }
                b_List_plate1.add(bPlate1);
                pf_List_before_plate1.add(1-normalDistribution.cumulativeProbability(bPlate1));

                if(i == pf_List_plate2.size()-1){
                    bPlate2 = -NormMath.sinv(pf_List_plate2.get(i));
                }
                else{
                    if(pf_List_plate2.get(i) == 0.00000001 || pf_List_plate2.get(i).equals(pf_List_plate2.get(i+1))){
                        bPlate2 = -NormMath.sinv(pf_List_plate2.get(i+1));
                    }else{
                        bPlate2 = -NormMath.sinv(pf_List_plate2.get(i));
                    }
                }
                b_List_plate2.add(bPlate2);
                pf_List_before_plate2.add(1-normalDistribution.cumulativeProbability(bPlate2));

                if(i == pf_List_plate3.size()-1){
                    bPlate3 = -NormMath.sinv(pf_List_plate3.get(i));
                }
                else{
                    if(pf_List_plate3.get(i) == 0.00000001 || pf_List_plate3.get(i).equals(pf_List_plate3.get(i+1))){
                        bPlate3 = -NormMath.sinv(pf_List_plate3.get(i+1));
                    }else{
                        bPlate3 = -NormMath.sinv(pf_List_plate3.get(i));
                    }
                }
                b_List_plate3.add(bPlate3);
                pf_List_before_plate3.add(1-normalDistribution.cumulativeProbability(bPlate3));

                noaction_pf_List.add(1-(1-pf_List_before_plate1.get(i)) * (1-pf_List_before_plate2.get(i)) * (1-pf_List_before_plate3.get(i)));
                noaction_b_List.add(-NormMath.sinv(noaction_pf_List.get(i)));
                if(noaction_b_List.get(i)<cabonationThreePlateInfoDto.getLtTargetValue()){
                    b1_List.add(0.0);
                    if(maxYear == 0){
                        maxYear = publicYear-1;
                    }
                }else{
                    b1_List.add(noaction_b_List.get(i));
                }
                publicYear ++;
            }

            // 공용연수 초기화
            publicYear = cabonationThreePlateInfoDto.getLtPublicYear();
            log.info("");
            log.info("b_List_plate1 : "+b_List_plate1);
            log.info("b_List_plate2 : "+b_List_plate2);
            log.info("b_List_plate3 : "+b_List_plate3);
            log.info("");
            log.info("pf_List_before_plate1 : "+pf_List_before_plate1);
            log.info("pf_List_before_plate2 : "+pf_List_before_plate2);
            log.info("pf_List_before_plate3 : "+pf_List_before_plate3);
            int repairPlate1Cnt = 0;
            int repairPlate2Cnt = 0;
            int repairPlate3Cnt = 0;
            for(int i=0; i<pf_List_before_plate1.size(); i++){
                double bFeforePlate1;
                double bFeforePlate2;
                double bFeforePlate3;
                if(i == pf_List_before_plate1.size()-1){
                    bFeforePlate1 = -NormMath.sinv(pf_List_before_plate1.get(i));
                }
                else{
                    if(pf_List_before_plate1.get(i) == 0.00000001){
                        bFeforePlate1 = -NormMath.sinv(pf_List_before_plate1.get(i+1));
                    }else{
                        bFeforePlate1 = -NormMath.sinv(pf_List_before_plate1.get(i));
                    }
                }
                if(bFeforePlate1<b_target){
                    repairPlate1Cnt++;
                }
                b_List_before_plate1.add(bFeforePlate1);
                if(i == pf_List_before_plate2.size()-1){
                    bFeforePlate2 = -NormMath.sinv(pf_List_before_plate2.get(i));
                }
                else{
                    if(pf_List_before_plate2.get(i) == 0.00000001){
                        bFeforePlate2 = -NormMath.sinv(pf_List_before_plate2.get(i+1));
                    }else{
                        bFeforePlate2 = -NormMath.sinv(pf_List_before_plate2.get(i));
                    }
                }
                if(bFeforePlate2<b_target){
                    repairPlate2Cnt++;
                }
                b_List_before_plate2.add(bFeforePlate2);
                if(i == pf_List_before_plate3.size()-1){
                    bFeforePlate3 = -NormMath.sinv(pf_List_before_plate3.get(i));
                }
                else{
                    if(pf_List_before_plate3.get(i) == 0.00000001){
                        bFeforePlate3 = -NormMath.sinv(pf_List_before_plate3.get(i+1));
                    }else{
                        bFeforePlate3 = -NormMath.sinv(pf_List_before_plate3.get(i));
                    }
                }
                if(bFeforePlate3<b_target){
                    repairPlate3Cnt++;
                }
                b_List_before_plate3.add(bFeforePlate3);
            }
            log.info("");
            log.info("b_List_before_plate1 : "+b_List_before_plate1);
            log.info("b_List_before_plate2 : "+b_List_before_plate2);
            log.info("b_List_before_plate3 : "+b_List_before_plate3);
            log.info("");
            log.info("repairPlate1Cnt : "+repairPlate1Cnt);
            log.info("repairPlate2Cnt : "+repairPlate2Cnt);
            log.info("repairPlate3Cnt : "+repairPlate3Cnt);
            List<Double> noaction_b_max_List = new ArrayList<>();
            noaction_b_max_List.add(Collections.max(b_List_before_plate1));
            noaction_b_max_List.add(Collections.max(b_List_before_plate2));
            noaction_b_max_List.add(Collections.max(b_List_before_plate3));
            List<Double> noaction_b_min_List = new ArrayList<>();
            noaction_b_min_List.add(Collections.min(b_List_before_plate1));
            noaction_b_min_List.add(Collections.min(b_List_before_plate2));
            noaction_b_min_List.add(Collections.min(b_List_before_plate3));
            // 차트의 JSON정보를 담을 Array 선언
            List<HashMap<String,Object>> noactionChartDataList = new ArrayList<>(); // 무조치 시 차트데이터
            List<HashMap<String,Object>> actionChartDataList = new ArrayList<>(); // 유지보수 개입시 차트데이터
            HashMap<String,Object> noactionchartData;
            HashMap<String,Object> actionchartData;
            if(repairPlate1Cnt == 0 && repairPlate2Cnt == 0 && repairPlate3Cnt == 0){
                log.info("보수보강 불필요");
                noaction_b_max = Collections.max(noaction_b_max_List);
                log.info("무조치 Y측 최대값 : "+noaction_b_max);
                double noaction_b_min = Collections.min(noaction_b_min_List);
                log.info("신뢰성 지수 최소값 : "+noaction_b_min);
                log.info("");
                int chartNum = 0;
                // 차트데이터 값 for문 알고리즘 20번돌아야됨
                for(int chartData=publicYear; chartData< publicYear+21; chartData++){
                    // 그래프로 보낼 데이터 뽑기 여기서 시작
                    noactionchartData  = new HashMap<>();
                    noactionchartData.put("publicYear", chartData);
                    noactionchartData.put("redline", cabonationThreePlateInfoDto.getLtTargetValue());
                    noactionchartData.put("noaction_plate1", Math.floor(b_List_before_plate1.get(chartNum)*1000)/1000.0);
                    noactionchartData.put("noaction_plate2", Math.floor(b_List_before_plate2.get(chartNum)*1000)/1000.0);
                    noactionchartData.put("noaction_plate3", Math.floor(b_List_before_plate3.get(chartNum)*1000)/1000.0);
                    noactionChartDataList.add(noactionchartData);
                    chartNum++;
                }
                returnData.put("noactionChartDataList",noactionChartDataList);
                returnData.put("b_List_before_plate1",b_List_before_plate1);
                returnData.put("b_List_before_plate2",b_List_before_plate2);
                returnData.put("b_List_before_plate3",b_List_before_plate3);
                returnData.put("publicYear",publicYear);
                returnData.put("bmax",noaction_b_max);
                returnData.put("pf_min",noaction_b_min);
                returnData.put("ltTargetValue",cabonationThreePlateInfoDto.getLtTargetValue());
                returnData.put("repairYn","N");
            }
            else{
                log.info("보수보강 필요");
                ltRecoveryList.add(cabonationThreePlateInfoDto.getLtRecoveryOne());
                ltRecoveryList.add(cabonationThreePlateInfoDto.getLtRecoveryTwo());
                ltRecoveryList.add(cabonationThreePlateInfoDto.getLtRecoveryThree());
                ltRecoveryList.add(cabonationThreePlateInfoDto.getLtRecoveryFour());
                ltRecoveryList.add(cabonationThreePlateInfoDto.getLtRecoveryFive());
                ltRecoveryList.add(cabonationThreePlateInfoDto.getLtRecoverySix());
                ltCostList.add(cabonationThreePlateInfoDto.getLtCostOne());
                ltCostList.add(cabonationThreePlateInfoDto.getLtCostTwo());
                ltCostList.add(cabonationThreePlateInfoDto.getLtCostThree());
                ltCostList.add(cabonationThreePlateInfoDto.getLtCostFour());
                ltCostList.add(cabonationThreePlateInfoDto.getLtCostFive());
                ltCostList.add(cabonationThreePlateInfoDto.getLtCostSix());
                log.info("");
                log.info("noaction_pf_List : "+noaction_pf_List);
                log.info("noaction_b_List : "+noaction_b_List);
                log.info("b1_List : "+b1_List);
                pf_max = Collections.max(noaction_pf_List);
                if(pf_max == 0.0){
                    b_min = -NormMath.sinv(0.00000001);
                }else{
                    b_min = -NormMath.sinv(pf_max);
                }
                log.info("손상확률 최대값 : "+pf_max);
                log.info("신뢰성 지수 최소값 : "+b_min);
                log.info("유지보수 무조치 가능한 최대년수 : "+maxYear);

                // 공용연수 초기화
                publicYear = cabonationThreePlateInfoDto.getLtPublicYear();
                // 우측의 step2_list, step1_value 계산
                int a = 0;
                for(int i=publicYear; i<publicYear+21; i++){
                    double value;
                    if(i<maxYear) {
                        value = noaction_b_List.get(a)*cabonationThreePlateInfoDto.getLtRecoveryPercent();
                    }else{
                        value = 0.0;
                    }
                    if(cabonationThreePlateInfoDto.getLtTargetValue()<value){
                        step1_value ++;
                    }
                    step2_list.add(value);
                    a++;
                }
                // 우측의 step1_list 계산
                int x = publicYear;
                for(int i=publicYear+1; i<publicYear+22; i++) {
                    step1_list.add(x+1);
                    if (step1_value == 0) {
                        x = publicYear;
                    }else{
                        x++;
                    }
                }
                noaction_b_List.sort(Collections.reverseOrder()); // b_List 내림차순으로 정렬
                int y = publicYear;
                int referenceInt = 0;
                for(int i=0; i<step1_list.size(); i++) {
                    int step = step1_list.get(i)-y;
                    double b_List_value = noaction_b_List.get(step-1);
                    double result = b_List_value*cabonationThreePlateInfoDto.getLtRecoveryPercent();

                    if(result < b_target){
                        referenceTable_List.add(referenceTable_List.get(referenceInt));
                        referenceInt++;
                        if(referenceInt == step1_value){
                            referenceInt = 0;
                        }
                    }
                    else{
                        referenceTable_List.add(b_List_value*cabonationThreePlateInfoDto.getLtRecoveryPercent());
                    }
                }
                // b2_list 계산
                int z = 0;
                int c = 0;
                for(int i=publicYear; i<publicYear+21; i++) {
                    if(i-maxYear<0 || i-maxYear==0){
                        b2_List.add(b1_List.get(z));
                        z++;
                    }else{
                        b2_List.add(referenceTable_List.get(c));
                        c++;
                    }
                }
                // adjustYear_list 계산
                int b1_num = 1; // B1,B2 리스트의 두번째를 제어
                int b2_num = 0; // B2 첫번째값 제어
                int publicYearNum = publicYear;
                for(int i=publicYear; i<publicYear+21; i++) {
                    // 처음엔 기본 공용연수 값 넣기
                    if(i==publicYear){
                        adjustYear_list.add(publicYear);
                    }else{
                        double b1_value1 = b1_List.get(b1_num);
                        double b2_value1 = b2_List.get(b1_num);
                        double b2_value2 = b2_List.get(b2_num);
                        if(b1_value1 < b2_value1 || b1_value1 > b2_value1){
                            if(b2_value2 < b2_value1){
                                adjustYear_list.add(publicYearNum);
                                repairNum++;
                            }else{
                                adjustYear_list.add(publicYearNum+1);
                            }
                        }else{
                            adjustYear_list.add(publicYearNum+1);
                        }
                        publicYearNum++;
                        b1_num++;
                        b2_num++;
                    }
                }
                for(int i=0; i<ltRecoveryList.size(); i++){
                    if(cabonationThreePlateInfoDto.getLtRecoveryPercent().equals(ltRecoveryList.get(i))){
                        repairCost = Integer.parseInt(String.valueOf(Math.round(cabonationThreePlateInfoDto.getLtRepairLength() * repairNum * ltCostList.get(i))));
                        break;
                    }
                }
                int chartNum = 0;
                // 차트데이터 값 for문 알고리즘 20번돌아야됨
                for(int chartData=publicYear; chartData< publicYear+21; chartData++){
                    // 그래프로 보낼 데이터 뽑기 여기서 시작
                    noactionchartData  = new HashMap<>();
                    actionchartData  = new HashMap<>();
                    noactionchartData.put("publicYear", chartData);
                    noactionchartData.put("redline", cabonationThreePlateInfoDto.getLtTargetValue());
//                    noactionchartData.put("noaction_plate", Math.floor(noaction_b_List.get(chartNum)*1000)/1000.0);
                    noactionchartData.put("noaction_plate1", Math.floor(b_List_before_plate1.get(chartNum)*1000)/1000.0);
                    noactionchartData.put("noaction_plate2", Math.floor(b_List_before_plate2.get(chartNum)*1000)/1000.0);
                    noactionchartData.put("noaction_plate3", Math.floor(b_List_before_plate3.get(chartNum)*1000)/1000.0);
                    noactionChartDataList.add(noactionchartData);
                    actionchartData.put("publicYear", adjustYear_list.get(chartNum));
                    actionchartData.put("redline", cabonationThreePlateInfoDto.getLtTargetValue());
                    actionchartData.put("action_plate", Math.floor(b2_List.get(chartNum)*1000)/1000.0);
                    actionChartDataList.add(actionchartData);
                    chartNum++;
                }
                System.out.println();
                log.info("무조치 차트 테스트 : "+noactionChartDataList);
                log.info("무조치 차트 데이터 길이 : "+noactionChartDataList.size());
                log.info("유지보수 차트 테스트 : "+actionChartDataList);
                log.info("유지보수 차트 데이터 길이 : "+actionChartDataList.size());
                System.out.println();
//                    log.info("무조치시 PF<0 pf_0List : " + pf_0List);
                log.info("무조치시 PF pf_List : " + noaction_pf_List);
                log.info("무조치시 B b_List : " + noaction_b_List);
//                    log.info("무조치시 PF<0 pf_0List.size() : " + pf_0List.size());
                log.info("무조치시 PF pf_List.size() : " + noaction_pf_List.size());
                log.info("무조치시 B b_List.size() : " + noaction_b_List.size());
                pf_max = Collections.max(noaction_pf_List);
                pf_min = -NormMath.sinv(pf_max);
                b_max =  Collections.max(noaction_b_List);
                double b_max2 = Collections.max(noaction_b_max_List);
                log.info("무조치시 b_max2 : " + b_max2);
                log.info("손상확률 최대값(PF 최댓값) : " + pf_max);
                log.info("손상확률 최소값: " + pf_min);
                log.info("신뢰성 지수 최대값 : " + b_max);
                System.out.println();
                log.info("유지보수시 b1_List : " + b1_List);
                log.info("유지보수시 b1_List.size() : " + b1_List.size());
                log.info("유지보수시 b2_List : " + b2_List);
                log.info("유지보수시 b2_List.size() : " + b2_List.size());
                log.info("유지보수 무조치 가능한 최대년수 maxYear : " + maxYear);
                log.info("우측의 step2_list : " + step2_list);
                log.info("우측의 step2_list.size() : " + step2_list.size());
                log.info("우측의 step1_value 값 : " + step1_value);
                log.info("우측의 step1_list : " + step1_list);
                log.info("우측의 step1_list.size() : " + step1_list.size());
                log.info("우측의 referenceTable_List : " + referenceTable_List);
                log.info("우측의 referenceTable_List.size() : " + referenceTable_List.size());
                log.info("하단의 공용연수 adjustYear_list : " + adjustYear_list);
                log.info("하단의 공용연수 adjustYear_list.size() : " + adjustYear_list.size());
                log.info("보수보강 개입 횟수 repairNum : " + repairNum);
                log.info("보수보강 총 비용(만원) repairCost : " + repairCost);
                returnData.put("repairYn","Y");
                returnData.put("publicYear",publicYear); // 공용연수
                returnData.put("ltRecoveryList",ltRecoveryList); // 회복율
                returnData.put("ltCostList",ltCostList); // 비용
                returnData.put("ltRepairLength",cabonationThreePlateInfoDto.getLtRepairLength()); // 보수보강 총 길이
                returnData.put("repairNum",repairNum); // 보수보강 개입 횟수
                returnData.put("repairCost",repairCost); // 보수보강 총 비용(만원)r®
                returnData.put("pfList",noaction_pf_List); // 무조치 시 PF
                returnData.put("bList",noaction_b_List); // 무조치 시 B
                returnData.put("bOneList",b1_List); // 유지보수 개입 시 B1
                returnData.put("bTwoList",b2_List); // 유지보수 개입 시 B2
                returnData.put("pf_max",Math.floor(pf_max*1000)/1000.0); // 손상확률 최대값(PF 최댓값)
                returnData.put("pf_min",Math.floor(pf_min*1000)/1000.0); // 손상확률 최소값(PF 최소값)
                returnData.put("bmax",Math.floor(b_max*1000)/1000.0); // 유지보수 개입 시 신뢰성지수최대값
                returnData.put("bmax2",Math.floor(b_max2*1000)/1000.0); // 유지보수 개입 시 신뢰성지수최대값
                returnData.put("ltRecoveryPercent",cabonationThreePlateInfoDto.getLtRecoveryPercent()*100); // 유지보수 개입 시 보수보강 회복율
                returnData.put("maxYear",maxYear); // 유지보수 무조치 가능한 최대년수
                returnData.put("ltTargetValue",cabonationThreePlateInfoDto.getLtTargetValue()); // 생애주기 목표값(성능유지 기준값)
                returnData.put("noactionChartDataList",noactionChartDataList);
                returnData.put("actionChartDataList",actionChartDataList);
            }
        }
        return returnData;
    }

}
