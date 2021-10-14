package com.broadwave.backend.ltd.data.rebar;

import com.broadwave.backend.bscodes.MeasurementItemCode;
import com.broadwave.backend.bscodes.PeriodType;
import com.broadwave.backend.bscodes.SeriesCode;
import com.broadwave.backend.bscodes.TreatmentCondition;
import com.broadwave.backend.common.AjaxResponse;
import com.broadwave.backend.common.CommonUtils;
import com.broadwave.backend.common.ResponseErrorCode;
import com.broadwave.backend.ltd.dto.GraphLegend;
import com.broadwave.backend.ltd.dto.GraphValue;
import com.broadwave.backend.ltd.dto.GraphValueString;
import com.broadwave.backend.ltd.dto.GraphXLabel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author InSeok
 * Date : 2019-05-08
 * Time : 17:09
 * Remark :
 */
@RestController
@Slf4j
@RequestMapping("/api/ltd/data")
public class RebarCorrosionRestController {

    private final RebarCorrosionService rebarCorrosionService;

    @Autowired
    public RebarCorrosionRestController(RebarCorrosionService rebarCorrosionService) {
        this.rebarCorrosionService = rebarCorrosionService;
    }

    @PostMapping("rc/period")
    public ResponseEntity<Map<String,Object>> periodList(@RequestParam(value="seriescode", defaultValue="") SeriesCode seriescode,
                                     HttpServletRequest request){
         
        String currentuserid = request.getHeader("insert_id");
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        log.info("철근부식량 주기 정보 조회(ex.28일,1년,10년..) / 조회자 :'" + currentuserid
                + "' 시리즈(코드) : '" + seriescode.getDesc() + "'(" + seriescode.getCode() + ")" );
        //List<CompressiveStrengthDto> strengthDtos = compressiveStrengthService.findBySeriesCode(seriescode);

        List<RebarCorrosionDto> rebarCorrosionDtos = rebarCorrosionService.findBySeriesCode(seriescode);

        List<GraphXLabel> graphyXLabelsrow = new ArrayList<>();
        rebarCorrosionDtos.forEach(v ->{
            GraphXLabel graphyXLabel = new GraphXLabel(v.getPeriod(), v.getPeriodName());
            graphyXLabelsrow.add(graphyXLabel);

        });

        List<GraphXLabel> graphyXLabels = graphyXLabelsrow.stream()
                .distinct()
                .sorted(Comparator.comparing(GraphXLabel::getValue))
                .collect(Collectors.toList());



        data.put("datalist",graphyXLabels);
       

        log.info("철근부식량 주기 정보 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));

    }

    @PostMapping("rc")
    public ResponseEntity<Map<String,Object>> graphData(@RequestParam(value="seriescode", defaultValue="") SeriesCode seriescode,
                                    @RequestParam (value="periodtype", defaultValue="") PeriodType periodtype,
                                    @RequestParam (value="treatmentcondition", defaultValue="") TreatmentCondition treatmentcondition,
                                    @RequestParam (value="periodname", defaultValue="") String periodname,
                                    HttpServletRequest request){

        AjaxResponse res = new AjaxResponse();

        if (periodtype.equals(PeriodType.P01)) {
            if (seriescode.equals(SeriesCode.S001)) {
                return null;
            }else if (seriescode.equals(SeriesCode.S002)) {
                return line_S002(treatmentcondition, seriescode, request);
            }else if (seriescode.equals(SeriesCode.S003)) {
                return line_S003(treatmentcondition, seriescode, request);
            }else if (seriescode.equals(SeriesCode.S004)) {
                return line_S004(treatmentcondition, seriescode, request);
            }else if (seriescode.equals(SeriesCode.S005)) {
                return line_S005(treatmentcondition, seriescode, request);
            }else if (seriescode.equals(SeriesCode.S006)) {
                return line_S006(treatmentcondition, seriescode, request);
            }else if (seriescode.equals(SeriesCode.S007)) {
                return line_S007(treatmentcondition, seriescode, request);
            } else {
                return ResponseEntity.ok(res.fail(ResponseErrorCode.E008.getCode(),ResponseErrorCode.E008.getDesc(),null,null));
            }
        }

        if (periodtype.equals(PeriodType.P02)) {
            if (seriescode.equals(SeriesCode.S001)) {
                return null;
            }else if (seriescode.equals(SeriesCode.S002)) {
                return bar_S002(periodname,treatmentcondition,seriescode,request);
            }else if (seriescode.equals(SeriesCode.S003)) {
                return bar_S003(periodname,treatmentcondition,seriescode,request);
            }else if (seriescode.equals(SeriesCode.S004)) {
                return bar_S004(periodname,treatmentcondition,seriescode,request);
            }else if (seriescode.equals(SeriesCode.S005)) {
                return bar_S005(periodname,treatmentcondition,seriescode,request);
            }else if (seriescode.equals(SeriesCode.S006)) {
                return bar_S006(periodname,treatmentcondition,seriescode,request);
            }else if (seriescode.equals(SeriesCode.S007)) {
                return bar_S007(periodname,treatmentcondition,seriescode,request);
            } else {
                 return ResponseEntity.ok(res.fail(ResponseErrorCode.E008.getCode(),ResponseErrorCode.E008.getDesc(),null,null));
            }


        }

         return ResponseEntity.ok(res.fail(ResponseErrorCode.E008.getCode(),ResponseErrorCode.E008.getDesc(),null,null));

    }



     private ResponseEntity<Map<String,Object>> bar_S002(String periodname, TreatmentCondition treatmentcondition, SeriesCode seriescode, HttpServletRequest request) {

         
String currentuserid = request.getHeader("insert_id");
AjaxResponse res = new AjaxResponse();
HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 철근부식량 Bar 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")");

        List<RebarCorrosionDto>  ltdDatasRaw = rebarCorrosionService.findBySeriesCode(seriescode);
        // 해당주기데이터만 취합
        List<RebarCorrosionDto> ltdDatas = ltdDatasRaw.stream()
                .filter(v -> v.getPeriodName().equals(periodname)).collect(Collectors.toList());


        List<String> categories = new ArrayList<>();

        List<List<String>> graphDataColumns = new ArrayList<>();

        //=== 1. X라벨 저장 ========================================================
        categories.add("1cm");
        categories.add("2cm");
        categories.add("3cm");
        categories.add("4cm");


        //======================T20504 wc 에 따른 철근부식량
        if(treatmentcondition.equals(TreatmentCondition.T20504)) {


                //=== 2. 범례 저장 (고로슬래그/플라이애쉬) =========================================
                List<GraphLegend> graphLegendsrow = new ArrayList<>();
                ltdDatas.forEach(v -> {
                    GraphLegend graphLegend = new GraphLegend(1, v.getElement1());
                    graphLegendsrow.add(graphLegend);
                });

                List<GraphLegend> graphLegends = graphLegendsrow.stream()
                        .distinct()
                        .collect(Collectors.toList());
                //graphLegends.forEach(v->System.out.println(v.toString()));

                //=== 3.범례별 평균값구해서 datasets 에 넣기=======================================

                //범례를 루프돌면서 실데이터를 graphDatas추가
                for(GraphLegend graphLegend: graphLegends){
                    String label = graphLegend.getName();
                    List<GraphValueString> graphValues = new ArrayList<>();
                    List<String> inspectdata = new ArrayList<>();


                    for (int j = 1; j < 5; j++) {

                        if (j == 1) {
                            ltdDatas.stream()
                                    .filter(v -> v.getElement1().equals(label))
                                    .collect(Collectors.groupingBy(RebarCorrosionDto::getElement1
                                            , Collectors.averagingDouble(RebarCorrosionDto::getValue1)
                                    )).forEach((k, v) -> {
                                GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        } else if (j == 2) {
                            ltdDatas.stream()
                                    .filter(v -> v.getElement1().equals(label))
                                    .collect(Collectors.groupingBy(RebarCorrosionDto::getElement1
                                            , Collectors.averagingDouble(RebarCorrosionDto::getValue2)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        } else if (j == 3) {
                            ltdDatas.stream()
                                    .filter(v -> v.getElement1().equals(label))
                                    .collect(Collectors.groupingBy(RebarCorrosionDto::getElement1
                                            , Collectors.averagingDouble(RebarCorrosionDto::getValue3)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        } else {
                            ltdDatas.stream()
                                    .filter(v -> v.getElement1().equals(label))
                                    .collect(Collectors.groupingBy(RebarCorrosionDto::getElement1
                                            , Collectors.averagingDouble(RebarCorrosionDto::getValue4)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        }

                    }

                    graphValues.sort(Comparator.comparing(GraphValueString::getIndex));

                    inspectdata.add(label);
                    graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                    graphDataColumns.add(inspectdata);
                }
            }
            else//======================T20505 연분함유량에따른 철근부식량
                if(treatmentcondition.equals(TreatmentCondition.T20505)) {


                    //=== 2. 범례 저장 (고로슬래그/플라이애쉬) =========================================
                    List<GraphLegend> graphLegendsrow = new ArrayList<>();
                    ltdDatas.forEach(v -> {
                        GraphLegend graphLegend = new GraphLegend(1, Double.toString(v.getSaltRate()));
                        graphLegendsrow.add(graphLegend);
                    });

                    List<GraphLegend> graphLegends = graphLegendsrow.stream()
                            .distinct()
                            .collect(Collectors.toList());
                    //graphLegends.forEach(v->System.out.println(v.toString()));

                    //=== 3.범례별 평균값구해서 datasets 에 넣기=======================================

                    //범례를 루프돌면서 실데이터를 graphDatas추가
                    for(GraphLegend graphLegend: graphLegends){
                        String label = graphLegend.getName();
                        List<GraphValueString> graphValues = new ArrayList<>();
                        List<String> inspectdata = new ArrayList<>();


                        for (int j = 1; j < 5; j++) {

                            if (j == 1) {
                                ltdDatas.stream()
                                        .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(RebarCorrosionDto::getValue1)
                                        )).forEach((k, v) -> {
                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                    graphValues.add(graphValue);

                                });
                            } else if (j == 2) {
                                ltdDatas.stream()
                                        .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(RebarCorrosionDto::getValue2)
                                        )).forEach((k, v) -> {

                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                    graphValues.add(graphValue);

                                });
                            } else if (j == 3) {
                                ltdDatas.stream()
                                        .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(RebarCorrosionDto::getValue3)
                                        )).forEach((k, v) -> {

                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                    graphValues.add(graphValue);

                                });
                            } else {
                                ltdDatas.stream()
                                        .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(RebarCorrosionDto::getValue4)
                                        )).forEach((k, v) -> {

                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                    graphValues.add(graphValue);

                                });
                            }

                        }

                        graphValues.sort(Comparator.comparing(GraphValueString::getIndex));

                        inspectdata.add(label+"%");
                        graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                        graphDataColumns.add(inspectdata);
                    }
                }


                //없는옵션이면
                else{
                    return ResponseEntity.ok(res.fail(ResponseErrorCode.E008.getCode(),ResponseErrorCode.E008.getDesc(),null,null));
                }

        data.put("title",periodname+"차 " + treatmentcondition.getGraphTitle());
        data.put("unit", MeasurementItemCode.M005.getDesc()+"(" + MeasurementItemCode.M005.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
        data.put("categories",categories);
       

        log.info(seriescode.getDesc() + " 철근부식량 Bar 그래프 데이터 조회 성공");


        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

     private ResponseEntity<Map<String,Object>> bar_S003(String periodname, TreatmentCondition treatmentcondition, SeriesCode seriescode, HttpServletRequest request) {

         
String currentuserid = request.getHeader("insert_id");
AjaxResponse res = new AjaxResponse();
HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 철근부식량 Bar 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")");

        List<RebarCorrosionDto>  ltdDatasRaw = rebarCorrosionService.findBySeriesCode(seriescode);
        // 해당주기데이터만 취합
        List<RebarCorrosionDto> ltdDatas = ltdDatasRaw.stream()
                .filter(v -> v.getPeriodName().equals(periodname)).collect(Collectors.toList());


        List<String> categories = new ArrayList<>();

        List<List<String>> graphDataColumns = new ArrayList<>();

        //=== 1. X라벨 저장 ========================================================
        categories.add("1cm");
        categories.add("2cm");
        categories.add("3cm");
        categories.add("4cm");


        //======================T30504 wc 에 따른 철근부식량
        if(treatmentcondition.equals(TreatmentCondition.T30504)) {


            //=== 2. 범례 저장 (고로슬래그/플라이애쉬) =========================================
            List<GraphLegend> graphLegendsrow = new ArrayList<>();
            ltdDatas.forEach(v -> {
                GraphLegend graphLegend = new GraphLegend(1, v.getElement1());
                graphLegendsrow.add(graphLegend);
            });

            List<GraphLegend> graphLegends = graphLegendsrow.stream()
                    .distinct()
                    .collect(Collectors.toList());
            //graphLegends.forEach(v->System.out.println(v.toString()));

            //=== 3.범례별 평균값구해서 datasets 에 넣기=======================================

            //범례를 루프돌면서 실데이터를 graphDatas추가
            for(GraphLegend graphLegend: graphLegends){
                String label = graphLegend.getName();
                List<GraphValueString> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                for (int j = 1; j < 5; j++) {

                    if (j == 1) {
                        ltdDatas.stream()
                                .filter(v -> v.getElement1().equals(label))
                                .collect(Collectors.groupingBy(RebarCorrosionDto::getElement1
                                        , Collectors.averagingDouble(RebarCorrosionDto::getValue1)
                                )).forEach((k, v) -> {
                            GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 2) {
                        ltdDatas.stream()
                                .filter(v -> v.getElement1().equals(label))
                                .collect(Collectors.groupingBy(RebarCorrosionDto::getElement1
                                        , Collectors.averagingDouble(RebarCorrosionDto::getValue2)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 3) {
                        ltdDatas.stream()
                                .filter(v -> v.getElement1().equals(label))
                                .collect(Collectors.groupingBy(RebarCorrosionDto::getElement1
                                        , Collectors.averagingDouble(RebarCorrosionDto::getValue3)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                            graphValues.add(graphValue);

                        });
                    } else {
                        ltdDatas.stream()
                                .filter(v -> v.getElement1().equals(label))
                                .collect(Collectors.groupingBy(RebarCorrosionDto::getElement1
                                        , Collectors.averagingDouble(RebarCorrosionDto::getValue4)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                            graphValues.add(graphValue);

                        });
                    }

                }

                graphValues.sort(Comparator.comparing(GraphValueString::getIndex));

                inspectdata.add(label);
                graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                graphDataColumns.add(inspectdata);
            }
        }
        else//======================T30505 연분함유량에따른 철근부식량
            if(treatmentcondition.equals(TreatmentCondition.T30505)) {


                //=== 2. 범례 저장 (고로슬래그/플라이애쉬) =========================================
                List<GraphLegend> graphLegendsrow = new ArrayList<>();
                ltdDatas.forEach(v -> {
                    GraphLegend graphLegend = new GraphLegend(1, Double.toString(v.getSaltRate()));
                    graphLegendsrow.add(graphLegend);
                });

                List<GraphLegend> graphLegends = graphLegendsrow.stream()
                        .distinct()
                        .collect(Collectors.toList());
                //graphLegends.forEach(v->System.out.println(v.toString()));

                //=== 3.범례별 평균값구해서 datasets 에 넣기=======================================

                //범례를 루프돌면서 실데이터를 graphDatas추가
                for(GraphLegend graphLegend: graphLegends){
                    String label = graphLegend.getName();
                    List<GraphValueString> graphValues = new ArrayList<>();
                    List<String> inspectdata = new ArrayList<>();


                    for (int j = 1; j < 5; j++) {

                        if (j == 1) {
                            ltdDatas.stream()
                                    .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                    .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                            , Collectors.averagingDouble(RebarCorrosionDto::getValue1)
                                    )).forEach((k, v) -> {
                                GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        } else if (j == 2) {
                            ltdDatas.stream()
                                    .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                    .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                            , Collectors.averagingDouble(RebarCorrosionDto::getValue2)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        } else if (j == 3) {
                            ltdDatas.stream()
                                    .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                    .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                            , Collectors.averagingDouble(RebarCorrosionDto::getValue3)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        } else {
                            ltdDatas.stream()
                                    .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                    .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                            , Collectors.averagingDouble(RebarCorrosionDto::getValue4)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        }

                    }

                    graphValues.sort(Comparator.comparing(GraphValueString::getIndex));

                    inspectdata.add(label+"%");
                    graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                    graphDataColumns.add(inspectdata);
                }
            }


            //없는옵션이면
            else{
                 return ResponseEntity.ok(res.fail(ResponseErrorCode.E008.getCode(),ResponseErrorCode.E008.getDesc(),null,null));
            }

        data.put("title",periodname+"차 " + treatmentcondition.getGraphTitle());
        data.put("unit", MeasurementItemCode.M005.getDesc()+"(" + MeasurementItemCode.M005.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
        data.put("categories",categories);
       

        log.info(seriescode.getDesc() + " 철근부식량 Bar 그래프 데이터 조회 성공");


        return ResponseEntity.ok(res.dataSendSuccess(data));
    }


     private ResponseEntity<Map<String,Object>> bar_S004(String periodname, TreatmentCondition treatmentcondition, SeriesCode seriescode, HttpServletRequest request) {

         
String currentuserid = request.getHeader("insert_id");
AjaxResponse res = new AjaxResponse();
HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 철근부식량 Bar 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")");

        List<RebarCorrosionDto>  ltdDatasRaw = rebarCorrosionService.findBySeriesCode(seriescode);
        // 해당주기데이터만 취합
        List<RebarCorrosionDto> ltdDatas = ltdDatasRaw.stream()
                .filter(v -> v.getPeriodName().equals(periodname)).collect(Collectors.toList());


        List<String> categories = new ArrayList<>();

        List<List<String>> graphDataColumns = new ArrayList<>();

        //=== 1. X라벨 저장 ========================================================
        categories.add("1cm");
        categories.add("2cm");
        categories.add("3cm");
        categories.add("4cm");


        //======================T40505 노출환경에따른 철근부식량
        if(treatmentcondition.equals(TreatmentCondition.T40505)) {


            //=== 2. 범례 저장 (내륙환경/해안환경) =========================================
            List<GraphLegend> graphLegendsrow = new ArrayList<>();
            ltdDatas.forEach(v -> {
                GraphLegend graphLegend = new GraphLegend(1, v.getEnvironment());
                graphLegendsrow.add(graphLegend);
            });

            List<GraphLegend> graphLegends = new ArrayList<>();
            Set<GraphLegend> uniqueValues = new HashSet<>();
            for (GraphLegend graphLegend : graphLegendsrow) {
                if (uniqueValues.add(graphLegend)) {
                    graphLegends.add(graphLegend);
                }
            }
            graphLegends.sort(Comparator.comparing(GraphLegend::getName));
            //graphLegends.forEach(v->System.out.println(v.toString()));

            //=== 3.범례별 평균값구해서 datasets 에 넣기=======================================

            //범례를 루프돌면서 실데이터를 graphDatas추가
            for(GraphLegend graphLegend: graphLegends){
                String label = graphLegend.getName();
                List<GraphValueString> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                for (int j = 1; j < 5; j++) {

                    if (j == 1) {
                        ltdDatas.stream()
                                .filter(v -> v.getEnvironment().equals(label))
                                .collect(Collectors.groupingBy(RebarCorrosionDto::getEnvironment
                                        , Collectors.averagingDouble(RebarCorrosionDto::getValue1)
                                )).forEach((k, v) -> {
                            GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 2) {
                        ltdDatas.stream()
                                .filter(v -> v.getEnvironment().equals(label))
                                .collect(Collectors.groupingBy(RebarCorrosionDto::getEnvironment
                                        , Collectors.averagingDouble(RebarCorrosionDto::getValue2)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 3) {
                        ltdDatas.stream()
                                .filter(v -> v.getEnvironment().equals(label))
                                .collect(Collectors.groupingBy(RebarCorrosionDto::getEnvironment
                                        , Collectors.averagingDouble(RebarCorrosionDto::getValue3)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                            graphValues.add(graphValue);

                        });
                    } else {
                        ltdDatas.stream()
                                .filter(v -> v.getEnvironment().equals(label))
                                .collect(Collectors.groupingBy(RebarCorrosionDto::getEnvironment
                                        , Collectors.averagingDouble(RebarCorrosionDto::getValue4)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                            graphValues.add(graphValue);

                        });
                    }

                }

                graphValues.sort(Comparator.comparing(GraphValueString::getIndex));

                inspectdata.add(label);
                graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                graphDataColumns.add(inspectdata);
            }
        }
        else//======================T40506 사용재료에따른 철근부식량
            if(treatmentcondition.equals(TreatmentCondition.T40506)) {


                //=== 2. 범례 저장 (고로슬래그/플라이애쉬) =========================================
                List<GraphLegend> graphLegendsrow = new ArrayList<>();
                ltdDatas.forEach(v -> {
                    GraphLegend graphLegend = new GraphLegend(1, v.getElement1());
                    graphLegendsrow.add(graphLegend);
                });

                List<GraphLegend> graphLegends = graphLegendsrow.stream()
                        .distinct()
                        .collect(Collectors.toList());
                //graphLegends.forEach(v->System.out.println(v.toString()));

                //=== 3.범례별 평균값구해서 datasets 에 넣기=======================================

                //범례를 루프돌면서 실데이터를 graphDatas추가
                for(GraphLegend graphLegend: graphLegends){
                    String label = graphLegend.getName();
                    List<GraphValueString> graphValues = new ArrayList<>();
                    List<String> inspectdata = new ArrayList<>();


                    for (int j = 1; j < 5; j++) {

                        if (j == 1) {
                            ltdDatas.stream()
                                    .filter(v -> v.getElement1().equals(label))
                                    .collect(Collectors.groupingBy(RebarCorrosionDto::getElement1
                                            , Collectors.averagingDouble(RebarCorrosionDto::getValue1)
                                    )).forEach((k, v) -> {
                                GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        } else if (j == 2) {
                            ltdDatas.stream()
                                    .filter(v -> v.getElement1().equals(label))
                                    .collect(Collectors.groupingBy(RebarCorrosionDto::getElement1
                                            , Collectors.averagingDouble(RebarCorrosionDto::getValue2)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        } else if (j == 3) {
                            ltdDatas.stream()
                                    .filter(v -> v.getElement1().equals(label))
                                    .collect(Collectors.groupingBy(RebarCorrosionDto::getElement1
                                            , Collectors.averagingDouble(RebarCorrosionDto::getValue3)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        } else {
                            ltdDatas.stream()
                                    .filter(v -> v.getElement1().equals(label))
                                    .collect(Collectors.groupingBy(RebarCorrosionDto::getElement1
                                            , Collectors.averagingDouble(RebarCorrosionDto::getValue4)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        }

                    }

                    graphValues.sort(Comparator.comparing(GraphValueString::getIndex));

                    inspectdata.add(label);
                    graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                    graphDataColumns.add(inspectdata);
                }
            }
            else//======================T40507 연분함유량에따른 철근부식량
                if(treatmentcondition.equals(TreatmentCondition.T40507)) {


                    //=== 2. 범례 저장 (고로슬래그/플라이애쉬) =========================================
                    List<GraphLegend> graphLegendsrow = new ArrayList<>();
                    ltdDatas.forEach(v -> {
                        GraphLegend graphLegend = new GraphLegend(1, Double.toString(v.getSaltRate()));
                        graphLegendsrow.add(graphLegend);
                    });

                    List<GraphLegend> graphLegends = graphLegendsrow.stream()
                            .distinct()
                            .collect(Collectors.toList());
                    //graphLegends.forEach(v->System.out.println(v.toString()));

                    //=== 3.범례별 평균값구해서 datasets 에 넣기=======================================

                    //범례를 루프돌면서 실데이터를 graphDatas추가
                    for(GraphLegend graphLegend: graphLegends){
                        String label = graphLegend.getName();
                        List<GraphValueString> graphValues = new ArrayList<>();
                        List<String> inspectdata = new ArrayList<>();


                        for (int j = 1; j < 5; j++) {

                            if (j == 1) {
                                ltdDatas.stream()
                                        .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(RebarCorrosionDto::getValue1)
                                        )).forEach((k, v) -> {
                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                    graphValues.add(graphValue);

                                });
                            } else if (j == 2) {
                                ltdDatas.stream()
                                        .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(RebarCorrosionDto::getValue2)
                                        )).forEach((k, v) -> {

                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                    graphValues.add(graphValue);

                                });
                            } else if (j == 3) {
                                ltdDatas.stream()
                                        .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(RebarCorrosionDto::getValue3)
                                        )).forEach((k, v) -> {

                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                    graphValues.add(graphValue);

                                });
                            } else {
                                ltdDatas.stream()
                                        .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(RebarCorrosionDto::getValue4)
                                        )).forEach((k, v) -> {

                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                    graphValues.add(graphValue);

                                });
                            }

                        }

                        graphValues.sort(Comparator.comparing(GraphValueString::getIndex));

                        inspectdata.add(label+"%");
                        graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                        graphDataColumns.add(inspectdata);
                    }
                }


                //없는옵션이면
                else{
                     return ResponseEntity.ok(res.fail(ResponseErrorCode.E008.getCode(),ResponseErrorCode.E008.getDesc(),null,null));
                }

        data.put("title",periodname+"차 " + treatmentcondition.getGraphTitle());
        data.put("unit", MeasurementItemCode.M005.getDesc()+"(" + MeasurementItemCode.M005.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
        data.put("categories",categories);
       

        log.info(seriescode.getDesc() + " 철근부식량 Bar 그래프 데이터 조회 성공");


        return ResponseEntity.ok(res.dataSendSuccess(data));
    }


     private ResponseEntity<Map<String,Object>> bar_S005(String periodname, TreatmentCondition treatmentcondition, SeriesCode seriescode, HttpServletRequest request) {

         
String currentuserid = request.getHeader("insert_id");
AjaxResponse res = new AjaxResponse();
HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 철근부식량 Bar 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")");

        List<RebarCorrosionDto>  ltdDatasRaw = rebarCorrosionService.findBySeriesCode(seriescode);
        // 해당주기데이터만 취합
        List<RebarCorrosionDto> ltdDatas = ltdDatasRaw.stream()
                .filter(v -> v.getPeriodName().equals(periodname)).collect(Collectors.toList());


        List<String> categories = new ArrayList<>();

        List<List<String>> graphDataColumns = new ArrayList<>();

        //=== 1. X라벨 저장 ========================================================
        categories.add("1cm");
        categories.add("2cm");
        categories.add("3cm");
        categories.add("4cm");


        //======================T50505 노출환경에따른 철근부식량
        if(treatmentcondition.equals(TreatmentCondition.T50505)) {


            //=== 2. 범례 저장 (내륙환경/해안환경) =========================================
            List<GraphLegend> graphLegendsrow = new ArrayList<>();
            ltdDatas.forEach(v -> {
                GraphLegend graphLegend = new GraphLegend(1, v.getEnvironment());
                graphLegendsrow.add(graphLegend);
            });

            List<GraphLegend> graphLegends = new ArrayList<>();
            Set<GraphLegend> uniqueValues = new HashSet<>();
            for (GraphLegend graphLegend : graphLegendsrow) {
                if (uniqueValues.add(graphLegend)) {
                    graphLegends.add(graphLegend);
                }
            }
            graphLegends.sort(Comparator.comparing(GraphLegend::getName));
            //graphLegends.forEach(v->System.out.println(v.toString()));

            //=== 3.범례별 평균값구해서 datasets 에 넣기=======================================

            //범례를 루프돌면서 실데이터를 graphDatas추가
            for(GraphLegend graphLegend: graphLegends){
                String label = graphLegend.getName();
                List<GraphValueString> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                for (int j = 1; j < 5; j++) {

                    if (j == 1) {
                        ltdDatas.stream()
                                .filter(v -> v.getEnvironment().equals(label))
                                .collect(Collectors.groupingBy(RebarCorrosionDto::getEnvironment
                                        , Collectors.averagingDouble(RebarCorrosionDto::getValue1)
                                )).forEach((k, v) -> {
                            GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 2) {
                        ltdDatas.stream()
                                .filter(v -> v.getEnvironment().equals(label))
                                .collect(Collectors.groupingBy(RebarCorrosionDto::getEnvironment
                                        , Collectors.averagingDouble(RebarCorrosionDto::getValue2)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 3) {
                        ltdDatas.stream()
                                .filter(v -> v.getEnvironment().equals(label))
                                .collect(Collectors.groupingBy(RebarCorrosionDto::getEnvironment
                                        , Collectors.averagingDouble(RebarCorrosionDto::getValue3)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                            graphValues.add(graphValue);

                        });
                    } else {
                        ltdDatas.stream()
                                .filter(v -> v.getEnvironment().equals(label))
                                .collect(Collectors.groupingBy(RebarCorrosionDto::getEnvironment
                                        , Collectors.averagingDouble(RebarCorrosionDto::getValue4)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                            graphValues.add(graphValue);

                        });
                    }

                }

                graphValues.sort(Comparator.comparing(GraphValueString::getIndex));

                inspectdata.add(label);
                graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                graphDataColumns.add(inspectdata);
            }
        }
        else//======================T50506 혼화재료에따른 철근부식량
            if(treatmentcondition.equals(TreatmentCondition.T50506)) {


                //=== 2. 범례 저장 (고로슬래그/플라이애쉬) =========================================
                List<GraphLegend> graphLegendsrow = new ArrayList<>();
                ltdDatas.forEach(v -> {
                    GraphLegend graphLegend = new GraphLegend(1, v.getElement1());
                    graphLegendsrow.add(graphLegend);
                });

                List<GraphLegend> graphLegends = graphLegendsrow.stream()
                        .distinct()
                        .collect(Collectors.toList());
                //graphLegends.forEach(v->System.out.println(v.toString()));

                //=== 3.범례별 평균값구해서 datasets 에 넣기=======================================

                //범례를 루프돌면서 실데이터를 graphDatas추가
                for(GraphLegend graphLegend: graphLegends){
                    String label = graphLegend.getName();
                    List<GraphValueString> graphValues = new ArrayList<>();
                    List<String> inspectdata = new ArrayList<>();


                    for (int j = 1; j < 5; j++) {

                        if (j == 1) {
                            ltdDatas.stream()
                                    .filter(v -> v.getElement1().equals(label))
                                    .collect(Collectors.groupingBy(RebarCorrosionDto::getElement1
                                            , Collectors.averagingDouble(RebarCorrosionDto::getValue1)
                                    )).forEach((k, v) -> {
                                GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        } else if (j == 2) {
                            ltdDatas.stream()
                                    .filter(v -> v.getElement1().equals(label))
                                    .collect(Collectors.groupingBy(RebarCorrosionDto::getElement1
                                            , Collectors.averagingDouble(RebarCorrosionDto::getValue2)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        } else if (j == 3) {
                            ltdDatas.stream()
                                    .filter(v -> v.getElement1().equals(label))
                                    .collect(Collectors.groupingBy(RebarCorrosionDto::getElement1
                                            , Collectors.averagingDouble(RebarCorrosionDto::getValue3)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        } else {
                            ltdDatas.stream()
                                    .filter(v -> v.getElement1().equals(label))
                                    .collect(Collectors.groupingBy(RebarCorrosionDto::getElement1
                                            , Collectors.averagingDouble(RebarCorrosionDto::getValue4)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        }

                    }

                    graphValues.sort(Comparator.comparing(GraphValueString::getIndex));

                    inspectdata.add(label);
                    graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                    graphDataColumns.add(inspectdata);
                }
            }
            else//======================T50507 연분함유량에따른 철근부식량
                if(treatmentcondition.equals(TreatmentCondition.T50507)) {


                    //=== 2. 범례 저장 (고로슬래그/플라이애쉬) =========================================
                    List<GraphLegend> graphLegendsrow = new ArrayList<>();
                    ltdDatas.forEach(v -> {
                        GraphLegend graphLegend = new GraphLegend(1, Double.toString(v.getSaltRate()));
                        graphLegendsrow.add(graphLegend);
                    });

                    List<GraphLegend> graphLegends = graphLegendsrow.stream()
                            .distinct()
                            .collect(Collectors.toList());
                    //graphLegends.forEach(v->System.out.println(v.toString()));

                    //=== 3.범례별 평균값구해서 datasets 에 넣기=======================================

                    //범례를 루프돌면서 실데이터를 graphDatas추가
                    for(GraphLegend graphLegend: graphLegends){
                        String label = graphLegend.getName();
                        List<GraphValueString> graphValues = new ArrayList<>();
                        List<String> inspectdata = new ArrayList<>();


                        for (int j = 1; j < 5; j++) {

                            if (j == 1) {
                                ltdDatas.stream()
                                        .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(RebarCorrosionDto::getValue1)
                                        )).forEach((k, v) -> {
                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                    graphValues.add(graphValue);

                                });
                            } else if (j == 2) {
                                ltdDatas.stream()
                                        .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(RebarCorrosionDto::getValue2)
                                        )).forEach((k, v) -> {

                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                    graphValues.add(graphValue);

                                });
                            } else if (j == 3) {
                                ltdDatas.stream()
                                        .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(RebarCorrosionDto::getValue3)
                                        )).forEach((k, v) -> {

                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                    graphValues.add(graphValue);

                                });
                            } else {
                                ltdDatas.stream()
                                        .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(RebarCorrosionDto::getValue4)
                                        )).forEach((k, v) -> {

                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                    graphValues.add(graphValue);

                                });
                            }

                        }

                        graphValues.sort(Comparator.comparing(GraphValueString::getIndex));

                        inspectdata.add(label+"%");
                        graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                        graphDataColumns.add(inspectdata);
                    }
                }


                //없는옵션이면
                else{
                     return ResponseEntity.ok(res.fail(ResponseErrorCode.E008.getCode(),ResponseErrorCode.E008.getDesc(),null,null));
                }

        data.put("title",periodname+"차 " + treatmentcondition.getGraphTitle());
        data.put("unit", MeasurementItemCode.M005.getDesc()+"(" + MeasurementItemCode.M005.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
        data.put("categories",categories);
       

        log.info(seriescode.getDesc() + " 철근부식량 Bar 그래프 데이터 조회 성공");


        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

     private ResponseEntity<Map<String,Object>> bar_S006(String periodname, TreatmentCondition treatmentcondition, SeriesCode seriescode, HttpServletRequest request) {

         
String currentuserid = request.getHeader("insert_id");
AjaxResponse res = new AjaxResponse();
HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 철근부식량 Bar 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")");

        List<RebarCorrosionDto>  ltdDatasRaw = rebarCorrosionService.findBySeriesCode(seriescode);
        // 해당주기데이터만 취합
        List<RebarCorrosionDto> ltdDatas = ltdDatasRaw.stream()
                .filter(v -> v.getPeriodName().equals(periodname)).collect(Collectors.toList());


        List<String> categories = new ArrayList<>();

        List<List<String>> graphDataColumns = new ArrayList<>();

        //=== 1. X라벨 저장 ========================================================
        categories.add("1cm");
        categories.add("2cm");
        categories.add("3cm");
        categories.add("4cm");


        //======================T60504 노툴환경 에 따른 철근부식량
        if(treatmentcondition.equals(TreatmentCondition.T60504)) {


            //=== 2. 범례 저장 (고로슬래그/플라이애쉬) =========================================
            List<GraphLegend> graphLegendsrow = new ArrayList<>();
            ltdDatas.forEach(v -> {
                GraphLegend graphLegend = new GraphLegend(1, v.getEnvironment());
                graphLegendsrow.add(graphLegend);
            });

            List<GraphLegend> graphLegends = graphLegendsrow.stream()
                    .distinct()
                    .collect(Collectors.toList());
            //graphLegends.forEach(v->System.out.println(v.toString()));

            //=== 3.범례별 평균값구해서 datasets 에 넣기=======================================

            //범례를 루프돌면서 실데이터를 graphDatas추가
            for(GraphLegend graphLegend: graphLegends){
                String label = graphLegend.getName();
                List<GraphValueString> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                for (int j = 1; j < 5; j++) {

                    if (j == 1) {
                        ltdDatas.stream()
                                .filter(v -> v.getEnvironment().equals(label))
                                .collect(Collectors.groupingBy(RebarCorrosionDto::getElement1
                                        , Collectors.averagingDouble(RebarCorrosionDto::getValue1)
                                )).forEach((k, v) -> {
                            GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 2) {
                        ltdDatas.stream()
                                .filter(v -> v.getEnvironment().equals(label))
                                .collect(Collectors.groupingBy(RebarCorrosionDto::getElement1
                                        , Collectors.averagingDouble(RebarCorrosionDto::getValue2)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 3) {
                        ltdDatas.stream()
                                .filter(v -> v.getEnvironment().equals(label))
                                .collect(Collectors.groupingBy(RebarCorrosionDto::getElement1
                                        , Collectors.averagingDouble(RebarCorrosionDto::getValue3)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                            graphValues.add(graphValue);

                        });
                    } else {
                        ltdDatas.stream()
                                .filter(v -> v.getEnvironment().equals(label))
                                .collect(Collectors.groupingBy(RebarCorrosionDto::getElement1
                                        , Collectors.averagingDouble(RebarCorrosionDto::getValue4)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                            graphValues.add(graphValue);

                        });
                    }

                }

                graphValues.sort(Comparator.comparing(GraphValueString::getIndex));

                inspectdata.add(label);
                graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                graphDataColumns.add(inspectdata);
            }
        }
        else//======================T60505 연분함유량에따른 철근부식량
            if(treatmentcondition.equals(TreatmentCondition.T60505)) {


                //=== 2. 범례 저장 (고로슬래그/플라이애쉬) =========================================
                List<GraphLegend> graphLegendsrow = new ArrayList<>();
                ltdDatas.forEach(v -> {
                    GraphLegend graphLegend = new GraphLegend(1, Double.toString(v.getSaltRate()));
                    graphLegendsrow.add(graphLegend);
                });

                List<GraphLegend> graphLegends = graphLegendsrow.stream()
                        .distinct()
                        .collect(Collectors.toList());
                //graphLegends.forEach(v->System.out.println(v.toString()));

                //=== 3.범례별 평균값구해서 datasets 에 넣기=======================================

                //범례를 루프돌면서 실데이터를 graphDatas추가
                for(GraphLegend graphLegend: graphLegends){
                    String label = graphLegend.getName();
                    List<GraphValueString> graphValues = new ArrayList<>();
                    List<String> inspectdata = new ArrayList<>();


                    for (int j = 1; j < 5; j++) {

                        if (j == 1) {
                            ltdDatas.stream()
                                    .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                    .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                            , Collectors.averagingDouble(RebarCorrosionDto::getValue1)
                                    )).forEach((k, v) -> {
                                GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        } else if (j == 2) {
                            ltdDatas.stream()
                                    .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                    .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                            , Collectors.averagingDouble(RebarCorrosionDto::getValue2)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        } else if (j == 3) {
                            ltdDatas.stream()
                                    .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                    .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                            , Collectors.averagingDouble(RebarCorrosionDto::getValue3)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        } else {
                            ltdDatas.stream()
                                    .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                    .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                            , Collectors.averagingDouble(RebarCorrosionDto::getValue4)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        }

                    }

                    graphValues.sort(Comparator.comparing(GraphValueString::getIndex));

                    inspectdata.add(label+"%");
                    graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                    graphDataColumns.add(inspectdata);
                }
            }


            //없는옵션이면
            else{
                 return ResponseEntity.ok(res.fail(ResponseErrorCode.E008.getCode(),ResponseErrorCode.E008.getDesc(),null,null));
            }

        data.put("title",periodname+"차 " + treatmentcondition.getGraphTitle());
        data.put("unit", MeasurementItemCode.M005.getDesc()+"(" + MeasurementItemCode.M005.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
        data.put("categories",categories);
       

        log.info(seriescode.getDesc() + " 철근부식량 Bar 그래프 데이터 조회 성공");


        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

     private ResponseEntity<Map<String,Object>> bar_S007(String periodname, TreatmentCondition treatmentcondition, SeriesCode seriescode, HttpServletRequest request) {

         
String currentuserid = request.getHeader("insert_id");
AjaxResponse res = new AjaxResponse();
HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 철근부식량 Bar 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")");

        List<RebarCorrosionDto>  ltdDatasRaw = rebarCorrosionService.findBySeriesCode(seriescode);
        // 해당주기데이터만 취합
        List<RebarCorrosionDto> ltdDatas = ltdDatasRaw.stream()
                .filter(v -> v.getPeriodName().equals(periodname)).collect(Collectors.toList());


        List<String> categories = new ArrayList<>();

        List<List<String>> graphDataColumns = new ArrayList<>();

        //=== 1. X라벨 저장 ========================================================
        categories.add("1cm");
        categories.add("2cm");
        categories.add("3cm");
        categories.add("4cm");


        //======================T70504 wc 에 따른 철근부식량
        if(treatmentcondition.equals(TreatmentCondition.T70504)) {


            //=== 2. 범례 저장 (고로슬래그/플라이애쉬) =========================================
            List<GraphLegend> graphLegendsrow = new ArrayList<>();
            ltdDatas.forEach(v -> {
                GraphLegend graphLegend = new GraphLegend(1, v.getElement1());
                graphLegendsrow.add(graphLegend);
            });

            List<GraphLegend> graphLegends = graphLegendsrow.stream()
                    .distinct()
                    .collect(Collectors.toList());
            //graphLegends.forEach(v->System.out.println(v.toString()));

            //=== 3.범례별 평균값구해서 datasets 에 넣기=======================================

            //범례를 루프돌면서 실데이터를 graphDatas추가
            for(GraphLegend graphLegend: graphLegends){
                String label = graphLegend.getName();
                List<GraphValueString> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                for (int j = 1; j < 5; j++) {

                    if (j == 1) {
                        ltdDatas.stream()
                                .filter(v -> v.getElement1().equals(label))
                                .collect(Collectors.groupingBy(RebarCorrosionDto::getElement1
                                        , Collectors.averagingDouble(RebarCorrosionDto::getValue1)
                                )).forEach((k, v) -> {
                            GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 2) {
                        ltdDatas.stream()
                                .filter(v -> v.getElement1().equals(label))
                                .collect(Collectors.groupingBy(RebarCorrosionDto::getElement1
                                        , Collectors.averagingDouble(RebarCorrosionDto::getValue2)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 3) {
                        ltdDatas.stream()
                                .filter(v -> v.getElement1().equals(label))
                                .collect(Collectors.groupingBy(RebarCorrosionDto::getElement1
                                        , Collectors.averagingDouble(RebarCorrosionDto::getValue3)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                            graphValues.add(graphValue);

                        });
                    } else {
                        ltdDatas.stream()
                                .filter(v -> v.getElement1().equals(label))
                                .collect(Collectors.groupingBy(RebarCorrosionDto::getElement1
                                        , Collectors.averagingDouble(RebarCorrosionDto::getValue4)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                            graphValues.add(graphValue);

                        });
                    }

                }

                graphValues.sort(Comparator.comparing(GraphValueString::getIndex));

                inspectdata.add(label);
                graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                graphDataColumns.add(inspectdata);
            }
        }
        else//======================T70505 연분함유량에따른 철근부식량
            if(treatmentcondition.equals(TreatmentCondition.T70505)) {


                //=== 2. 범례 저장 (고로슬래그/플라이애쉬) =========================================
                List<GraphLegend> graphLegendsrow = new ArrayList<>();
                ltdDatas.forEach(v -> {
                    GraphLegend graphLegend = new GraphLegend(1, Double.toString(v.getSaltRate()));
                    graphLegendsrow.add(graphLegend);
                });

                List<GraphLegend> graphLegends = graphLegendsrow.stream()
                        .distinct()
                        .collect(Collectors.toList());
                //graphLegends.forEach(v->System.out.println(v.toString()));

                //=== 3.범례별 평균값구해서 datasets 에 넣기=======================================

                //범례를 루프돌면서 실데이터를 graphDatas추가
                for(GraphLegend graphLegend: graphLegends){
                    String label = graphLegend.getName();
                    List<GraphValueString> graphValues = new ArrayList<>();
                    List<String> inspectdata = new ArrayList<>();


                    for (int j = 1; j < 5; j++) {

                        if (j == 1) {
                            ltdDatas.stream()
                                    .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                    .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                            , Collectors.averagingDouble(RebarCorrosionDto::getValue1)
                                    )).forEach((k, v) -> {
                                GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        } else if (j == 2) {
                            ltdDatas.stream()
                                    .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                    .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                            , Collectors.averagingDouble(RebarCorrosionDto::getValue2)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        } else if (j == 3) {
                            ltdDatas.stream()
                                    .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                    .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                            , Collectors.averagingDouble(RebarCorrosionDto::getValue3)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        } else {
                            ltdDatas.stream()
                                    .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                    .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                            , Collectors.averagingDouble(RebarCorrosionDto::getValue4)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        }

                    }

                    graphValues.sort(Comparator.comparing(GraphValueString::getIndex));

                    inspectdata.add(label+"%");
                    graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                    graphDataColumns.add(inspectdata);
                }
            }


            //없는옵션이면
            else{
                 return ResponseEntity.ok(res.fail(ResponseErrorCode.E008.getCode(),ResponseErrorCode.E008.getDesc(),null,null));
            }

        data.put("title",periodname+"차 " + treatmentcondition.getGraphTitle());
        data.put("unit", MeasurementItemCode.M005.getDesc()+"(" + MeasurementItemCode.M005.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
        data.put("categories",categories);
       

        log.info(seriescode.getDesc() + " 철근부식량 Bar 그래프 데이터 조회 성공");


        return ResponseEntity.ok(res.dataSendSuccess(data));
    }




     private ResponseEntity<Map<String,Object>> line_S002(TreatmentCondition treatmentcondition,SeriesCode seriescode, HttpServletRequest request) {
         
        String currentuserid = request.getHeader("insert_id");
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 철근부식량 라인용(주기) 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")");

        List<RebarCorrosionDto> ltdDatas = rebarCorrosionService.findBySeriesCode(seriescode);

        //=== 1. X라벨 저장 ========================================================

        List<GraphXLabel> graphyXLabelsrow = new ArrayList<>();
        ltdDatas.forEach(v -> {
            GraphXLabel graphyXLabel = new GraphXLabel(v.getPeriod(), v.getPeriodName());
            graphyXLabelsrow.add(graphyXLabel);

        });

        List<GraphXLabel> graphyXLabels = graphyXLabelsrow.stream()
                .distinct()
                .sorted(Comparator.comparing(GraphXLabel::getValue))
                .collect(Collectors.toList());


        // 2 범례및 데이터 생성 저장

        List<List<String>> graphDataColumns = new ArrayList<>();

        // X라벨을 돌면서 graphDatas 에 추가
        List<String> xRows = new ArrayList<>();
        xRows.add("x");
        graphyXLabels.forEach(x -> xRows.add(Integer.toString(x.getValue())));
        graphDataColumns.add(xRows);

        //======================T20501 wc에따른 주기별 철근부식량
        if(treatmentcondition.equals(TreatmentCondition.T20501)) {

                //=== 2.1 범례 저장 (내륙환경/해안환경) ==========================================
                List<GraphLegend> graphLegendsrow = new ArrayList<>();
                ltdDatas.forEach(v -> {
                    GraphLegend graphLegend = new GraphLegend(1, v.getElement1());
                    graphLegendsrow.add(graphLegend);
                });

                List<GraphLegend> graphLegends = new ArrayList<>();
                Set<GraphLegend> uniqueValues = new HashSet<>();
                for (GraphLegend graphLegend : graphLegendsrow) {
                    if (uniqueValues.add(graphLegend)) {
                        graphLegends.add(graphLegend);
                    }
                }
                graphLegends.sort(Comparator.comparing(GraphLegend::getName));
                //graphLegends.forEach(v->System.out.println(v.toString()));

                //=== 2.2 범례별 평균값구해서 datasets 에 넣기=======================================
                for(GraphLegend graphLegend: graphLegends){

                    String label = graphLegend.getName();
                    List<GraphValue> graphValues = new ArrayList<>();
                    List<String> inspectdata = new ArrayList<>();


                    ltdDatas.stream()
                            .filter(v -> v.getElement1().equals(label))
                            .collect(Collectors.groupingBy(RebarCorrosionDto::getPeriod
                                    , Collectors.averagingDouble(RebarCorrosionDto::getValue)
                            )).forEach((k, v) -> {

                        GraphValue graphValue = new GraphValue(k, String.format("%.2f", v / 4 ));
                        graphValues.add(graphValue);

                    });

                    graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                    inspectdata.add(label);
                    graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                    graphDataColumns.add(inspectdata);

                }
            }
            else //======================T20502 초기 염분함유량에따른 주기별 철근부식량
                if(treatmentcondition.equals(TreatmentCondition.T20502)) {

                    //=== 2.1 범례 저장 (내륙환경/해안환경) ==========================================
                    List<GraphLegend> graphLegendsrow = new ArrayList<>();
                    ltdDatas.forEach(v -> {
                        GraphLegend graphLegend = new GraphLegend(1, Double.toString(v.getSaltRate()));
                        graphLegendsrow.add(graphLegend);
                    });

                    List<GraphLegend> graphLegends = new ArrayList<>();
                    Set<GraphLegend> uniqueValues = new HashSet<>();
                    for (GraphLegend graphLegend : graphLegendsrow) {
                        if (uniqueValues.add(graphLegend)) {
                            graphLegends.add(graphLegend);
                        }
                    }
                    graphLegends.sort(Comparator.comparing(GraphLegend::getName));
                    //graphLegends.forEach(v->System.out.println(v.toString()));

                    //=== 2.2 범례별 평균값구해서 datasets 에 넣기=======================================
                    for(GraphLegend graphLegend: graphLegends){


                        String label = graphLegend.getName();
                        List<GraphValue> graphValues = new ArrayList<>();
                        List<String> inspectdata = new ArrayList<>();


                        ltdDatas.stream()
                                .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                .collect(Collectors.groupingBy(RebarCorrosionDto::getPeriod
                                        , Collectors.averagingDouble(RebarCorrosionDto::getValue)
                                )).forEach((k, v) -> {

                            GraphValue graphValue = new GraphValue(k, String.format("%.2f", v/4));
                            graphValues.add(graphValue);

                        });

                        graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                        inspectdata.add(label + '%');
                        graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                        graphDataColumns.add(inspectdata);

                    }
                }
                else //======================T20503 피복두계에 따른 주기별 철근부식량
                    if(treatmentcondition.equals(TreatmentCondition.T20503)) {


                        //=== 2.2 범례별(1cm,2cm,3cm,4cm) 평균값구해서 datasets 에 넣기=======================================
                        for (int i = 1; i < 5; i++) {


                            String label = i + "cm";

                            List<GraphValue> graphValues = new ArrayList<>();
                            List<String> inspectdata = new ArrayList<>();

                            if (i == 1) {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(RebarCorrosionDto::getPeriod
                                                , Collectors.averagingDouble(RebarCorrosionDto::getValue1)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.2f", v));
                                    graphValues.add(graphValue);

                                });
                            }else if (i == 2) {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(RebarCorrosionDto::getPeriod
                                                , Collectors.averagingDouble(RebarCorrosionDto::getValue2)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.2f", v));
                                    graphValues.add(graphValue);

                                });
                            }else if (i == 3) {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(RebarCorrosionDto::getPeriod
                                                , Collectors.averagingDouble(RebarCorrosionDto::getValue3)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.2f", v));
                                    graphValues.add(graphValue);

                                });
                            }else  {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(RebarCorrosionDto::getPeriod
                                                , Collectors.averagingDouble(RebarCorrosionDto::getValue4)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.2f", v));
                                    graphValues.add(graphValue);

                                });
                            }

                            graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                            inspectdata.add(label);
                            graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                            graphDataColumns.add(inspectdata);

                        }
                    }
                    //없는옵션이면
                    else{
                         return ResponseEntity.ok(res.fail(ResponseErrorCode.E008.getCode(),ResponseErrorCode.E008.getDesc(),null,null));
                    }

        data.put("title",treatmentcondition.getGraphTitle());
        data.put("unit", MeasurementItemCode.M005.getDesc()+"(" + MeasurementItemCode.M005.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
       

        log.info(seriescode.getDesc() + " 철근부식량 라인용(주기) 그래프 데이터 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));

    }

     private ResponseEntity<Map<String,Object>> line_S003(TreatmentCondition treatmentcondition,SeriesCode seriescode, HttpServletRequest request) {
         
String currentuserid = request.getHeader("insert_id");
AjaxResponse res = new AjaxResponse();
HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 철근부식량 라인용(주기) 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")");

        List<RebarCorrosionDto> ltdDatas = rebarCorrosionService.findBySeriesCode(seriescode);

        //=== 1. X라벨 저장 ========================================================

        List<GraphXLabel> graphyXLabelsrow = new ArrayList<>();
        ltdDatas.forEach(v -> {
            GraphXLabel graphyXLabel = new GraphXLabel(v.getPeriod(), v.getPeriodName());
            graphyXLabelsrow.add(graphyXLabel);

        });

        List<GraphXLabel> graphyXLabels = graphyXLabelsrow.stream()
                .distinct()
                .sorted(Comparator.comparing(GraphXLabel::getValue))
                .collect(Collectors.toList());


        // 2 범례및 데이터 생성 저장

        List<List<String>> graphDataColumns = new ArrayList<>();

        // X라벨을 돌면서 graphDatas 에 추가
        List<String> xRows = new ArrayList<>();
        xRows.add("x");
        graphyXLabels.forEach(x -> xRows.add(Integer.toString(x.getValue())));
        graphDataColumns.add(xRows);

        //======================T30501 wc에따른 주기별 철근부식량
        if(treatmentcondition.equals(TreatmentCondition.T30501)) {

            //=== 2.1 범례 저장 (내륙환경/해안환경) ==========================================
            List<GraphLegend> graphLegendsrow = new ArrayList<>();
            ltdDatas.forEach(v -> {
                GraphLegend graphLegend = new GraphLegend(1, v.getElement1());
                graphLegendsrow.add(graphLegend);
            });

            List<GraphLegend> graphLegends = new ArrayList<>();
            Set<GraphLegend> uniqueValues = new HashSet<>();
            for (GraphLegend graphLegend : graphLegendsrow) {
                if (uniqueValues.add(graphLegend)) {
                    graphLegends.add(graphLegend);
                }
            }
            graphLegends.sort(Comparator.comparing(GraphLegend::getName));
            //graphLegends.forEach(v->System.out.println(v.toString()));

            //=== 2.2 범례별 평균값구해서 datasets 에 넣기=======================================
            for(GraphLegend graphLegend: graphLegends){

                String label = graphLegend.getName();
                List<GraphValue> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                ltdDatas.stream()
                        .filter(v -> v.getElement1().equals(label))
                        .collect(Collectors.groupingBy(RebarCorrosionDto::getPeriod
                                , Collectors.averagingDouble(RebarCorrosionDto::getValue)
                        )).forEach((k, v) -> {

                    GraphValue graphValue = new GraphValue(k, String.format("%.2f", v / 4 ));
                    graphValues.add(graphValue);

                });

                graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                inspectdata.add(label);
                graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                graphDataColumns.add(inspectdata);

            }
        }
        else //======================T30502 초기 염분함유량에따른 주기별 철근부식량
            if(treatmentcondition.equals(TreatmentCondition.T30502)) {

                //=== 2.1 범례 저장 (내륙환경/해안환경) ==========================================
                List<GraphLegend> graphLegendsrow = new ArrayList<>();
                ltdDatas.forEach(v -> {
                    GraphLegend graphLegend = new GraphLegend(1, Double.toString(v.getSaltRate()));
                    graphLegendsrow.add(graphLegend);
                });

                List<GraphLegend> graphLegends = new ArrayList<>();
                Set<GraphLegend> uniqueValues = new HashSet<>();
                for (GraphLegend graphLegend : graphLegendsrow) {
                    if (uniqueValues.add(graphLegend)) {
                        graphLegends.add(graphLegend);
                    }
                }
                graphLegends.sort(Comparator.comparing(GraphLegend::getName));
                //graphLegends.forEach(v->System.out.println(v.toString()));

                //=== 2.2 범례별 평균값구해서 datasets 에 넣기=======================================
                for(GraphLegend graphLegend: graphLegends){


                    String label = graphLegend.getName();
                    List<GraphValue> graphValues = new ArrayList<>();
                    List<String> inspectdata = new ArrayList<>();


                    ltdDatas.stream()
                            .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                            .collect(Collectors.groupingBy(RebarCorrosionDto::getPeriod
                                    , Collectors.averagingDouble(RebarCorrosionDto::getValue)
                            )).forEach((k, v) -> {

                        GraphValue graphValue = new GraphValue(k, String.format("%.2f", v/4));
                        graphValues.add(graphValue);

                    });

                    graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                    inspectdata.add(label + '%');
                    graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                    graphDataColumns.add(inspectdata);

                }
            }
            else //======================T30503 피복두계에 따른 주기별 철근부식량
                if(treatmentcondition.equals(TreatmentCondition.T30503)) {


                    //=== 2.2 범례별(1cm,2cm,3cm,4cm) 평균값구해서 datasets 에 넣기=======================================
                    for (int i = 1; i < 5; i++) {


                        String label = i + "cm";

                        List<GraphValue> graphValues = new ArrayList<>();
                        List<String> inspectdata = new ArrayList<>();

                        if (i == 1) {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(RebarCorrosionDto::getPeriod
                                            , Collectors.averagingDouble(RebarCorrosionDto::getValue1)
                                    )).forEach((k, v) -> {

                                GraphValue graphValue = new GraphValue(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        }else if (i == 2) {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(RebarCorrosionDto::getPeriod
                                            , Collectors.averagingDouble(RebarCorrosionDto::getValue2)
                                    )).forEach((k, v) -> {

                                GraphValue graphValue = new GraphValue(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        }else if (i == 3) {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(RebarCorrosionDto::getPeriod
                                            , Collectors.averagingDouble(RebarCorrosionDto::getValue3)
                                    )).forEach((k, v) -> {

                                GraphValue graphValue = new GraphValue(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        }else  {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(RebarCorrosionDto::getPeriod
                                            , Collectors.averagingDouble(RebarCorrosionDto::getValue4)
                                    )).forEach((k, v) -> {

                                GraphValue graphValue = new GraphValue(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        }

                        graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                        inspectdata.add(label);
                        graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                        graphDataColumns.add(inspectdata);

                    }
                }
                //없는옵션이면
                else{
                     return ResponseEntity.ok(res.fail(ResponseErrorCode.E008.getCode(),ResponseErrorCode.E008.getDesc(),null,null));
                }

        data.put("title",treatmentcondition.getGraphTitle());
        data.put("unit", MeasurementItemCode.M005.getDesc()+"(" + MeasurementItemCode.M005.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
       

        log.info(seriescode.getDesc() + " 철근부식량 라인용(주기) 그래프 데이터 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));

    }

     private ResponseEntity<Map<String,Object>> line_S004(TreatmentCondition treatmentcondition,SeriesCode seriescode, HttpServletRequest request) {
         
String currentuserid = request.getHeader("insert_id");
AjaxResponse res = new AjaxResponse();
HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 철근부식량 라인용(주기) 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")");

        List<RebarCorrosionDto> ltdDatas = rebarCorrosionService.findBySeriesCode(seriescode);

        //=== 1. X라벨 저장 ========================================================

        List<GraphXLabel> graphyXLabelsrow = new ArrayList<>();
        ltdDatas.forEach(v -> {
            GraphXLabel graphyXLabel = new GraphXLabel(v.getPeriod(), v.getPeriodName());
            graphyXLabelsrow.add(graphyXLabel);

        });

        List<GraphXLabel> graphyXLabels = graphyXLabelsrow.stream()
                .distinct()
                .sorted(Comparator.comparing(GraphXLabel::getValue))
                .collect(Collectors.toList());


        // 2 범례및 데이터 생성 저장

        List<List<String>> graphDataColumns = new ArrayList<>();

        // X라벨을 돌면서 graphDatas 에 추가
        List<String> xRows = new ArrayList<>();
        xRows.add("x");
        graphyXLabels.forEach(x -> xRows.add(Integer.toString(x.getValue())));
        graphDataColumns.add(xRows);

        //======================T40501 노출환경에따른 주기별 철근부식량
        if(treatmentcondition.equals(TreatmentCondition.T40501)) {

            //=== 2.1 범례 저장 (내륙환경/해안환경) ==========================================
            List<GraphLegend> graphLegendsrow = new ArrayList<>();
            ltdDatas.forEach(v -> {
                GraphLegend graphLegend = new GraphLegend(1, v.getEnvironment());
                graphLegendsrow.add(graphLegend);
            });

            List<GraphLegend> graphLegends = new ArrayList<>();
            Set<GraphLegend> uniqueValues = new HashSet<>();
            for (GraphLegend graphLegend : graphLegendsrow) {
                if (uniqueValues.add(graphLegend)) {
                    graphLegends.add(graphLegend);
                }
            }
            graphLegends.sort(Comparator.comparing(GraphLegend::getName));
            //graphLegends.forEach(v->System.out.println(v.toString()));

            //=== 2.2 범례별 평균값구해서 datasets 에 넣기=======================================
            for(GraphLegend graphLegend: graphLegends){

                String label = graphLegend.getName();
                List<GraphValue> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                ltdDatas.stream()
                        .filter(v -> v.getEnvironment().equals(label))
                        .collect(Collectors.groupingBy(RebarCorrosionDto::getPeriod
                                , Collectors.averagingDouble(RebarCorrosionDto::getValue)
                        )).forEach((k, v) -> {

                    GraphValue graphValue = new GraphValue(k, String.format("%.2f", v / 4));
                    graphValues.add(graphValue);

                });

                graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                inspectdata.add(label);
                graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                graphDataColumns.add(inspectdata);

            }
        }
        else //======================T40502 사용재료에따른 주기별 철근부식량
            if(treatmentcondition.equals(TreatmentCondition.T40502)) {

                //=== 2.1 범례 저장 (내륙환경/해안환경) ==========================================
                List<GraphLegend> graphLegendsrow = new ArrayList<>();
                ltdDatas.forEach(v -> {
                    GraphLegend graphLegend = new GraphLegend(1, v.getElement1());
                    graphLegendsrow.add(graphLegend);
                });

                List<GraphLegend> graphLegends = new ArrayList<>();
                Set<GraphLegend> uniqueValues = new HashSet<>();
                for (GraphLegend graphLegend : graphLegendsrow) {
                    if (uniqueValues.add(graphLegend)) {
                        graphLegends.add(graphLegend);
                    }
                }
                graphLegends.sort(Comparator.comparing(GraphLegend::getName));
                //graphLegends.forEach(v->System.out.println(v.toString()));

                //=== 2.2 범례별 평균값구해서 datasets 에 넣기=======================================
                for(GraphLegend graphLegend: graphLegends){

                    String label = graphLegend.getName();
                    List<GraphValue> graphValues = new ArrayList<>();
                    List<String> inspectdata = new ArrayList<>();


                    ltdDatas.stream()
                            .filter(v -> v.getElement1().equals(label))
                            .collect(Collectors.groupingBy(RebarCorrosionDto::getPeriod
                                    , Collectors.averagingDouble(RebarCorrosionDto::getValue)
                            )).forEach((k, v) -> {

                        GraphValue graphValue = new GraphValue(k, String.format("%.2f", v / 4 ));
                        graphValues.add(graphValue);

                    });

                    graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                    inspectdata.add(label);
                    graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                    graphDataColumns.add(inspectdata);

                }
            }
            else //======================T40503 초기 염분함유량에따른 주기별 철근부식량
                if(treatmentcondition.equals(TreatmentCondition.T40503)) {

                    //=== 2.1 범례 저장 (내륙환경/해안환경) ==========================================
                    List<GraphLegend> graphLegendsrow = new ArrayList<>();
                    ltdDatas.forEach(v -> {
                        GraphLegend graphLegend = new GraphLegend(1, Double.toString(v.getSaltRate()));
                        graphLegendsrow.add(graphLegend);
                    });

                    List<GraphLegend> graphLegends = new ArrayList<>();
                    Set<GraphLegend> uniqueValues = new HashSet<>();
                    for (GraphLegend graphLegend : graphLegendsrow) {
                        if (uniqueValues.add(graphLegend)) {
                            graphLegends.add(graphLegend);
                        }
                    }
                    graphLegends.sort(Comparator.comparing(GraphLegend::getName));
                    //graphLegends.forEach(v->System.out.println(v.toString()));

                    //=== 2.2 범례별 평균값구해서 datasets 에 넣기=======================================
                    for(GraphLegend graphLegend: graphLegends){


                        String label = graphLegend.getName();
                        List<GraphValue> graphValues = new ArrayList<>();
                        List<String> inspectdata = new ArrayList<>();


                        ltdDatas.stream()
                                .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                .collect(Collectors.groupingBy(RebarCorrosionDto::getPeriod
                                        , Collectors.averagingDouble(RebarCorrosionDto::getValue)
                                )).forEach((k, v) -> {

                            GraphValue graphValue = new GraphValue(k, String.format("%.2f", v/4));
                            graphValues.add(graphValue);

                        });

                        graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                        inspectdata.add(label + '%');
                        graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                        graphDataColumns.add(inspectdata);

                    }
                }
                else //======================T40504 피복두계에 따른 주기별 철근부식량
                    if(treatmentcondition.equals(TreatmentCondition.T40504)) {


                        //=== 2.2 범례별(1cm,2cm,3cm,4cm) 평균값구해서 datasets 에 넣기=======================================
                        for (int i = 1; i < 5; i++) {


                            String label = i + "cm";

                            List<GraphValue> graphValues = new ArrayList<>();
                            List<String> inspectdata = new ArrayList<>();

                            if (i == 1) {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(RebarCorrosionDto::getPeriod
                                                , Collectors.averagingDouble(RebarCorrosionDto::getValue1)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.2f", v));
                                    graphValues.add(graphValue);

                                });
                            }else if (i == 2) {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(RebarCorrosionDto::getPeriod
                                                , Collectors.averagingDouble(RebarCorrosionDto::getValue2)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.2f", v));
                                    graphValues.add(graphValue);

                                });
                            }else if (i == 3) {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(RebarCorrosionDto::getPeriod
                                                , Collectors.averagingDouble(RebarCorrosionDto::getValue3)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.2f", v));
                                    graphValues.add(graphValue);

                                });
                            }else  {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(RebarCorrosionDto::getPeriod
                                                , Collectors.averagingDouble(RebarCorrosionDto::getValue4)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.2f", v));
                                    graphValues.add(graphValue);

                                });
                            }

                            graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                            inspectdata.add(label);
                            graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                            graphDataColumns.add(inspectdata);

                        }
                    }
                    //없는옵션이면
                    else{
                         return ResponseEntity.ok(res.fail(ResponseErrorCode.E008.getCode(),ResponseErrorCode.E008.getDesc(),null,null));
                    }

        data.put("title",treatmentcondition.getGraphTitle());
        data.put("unit", MeasurementItemCode.M005.getDesc()+"(" + MeasurementItemCode.M005.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
       

        log.info(seriescode.getDesc() + " 철근부식량 라인용(주기) 그래프 데이터 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));

    }



     private ResponseEntity<Map<String,Object>> line_S005(TreatmentCondition treatmentcondition,SeriesCode seriescode, HttpServletRequest request) {
         
String currentuserid = request.getHeader("insert_id");
AjaxResponse res = new AjaxResponse();
HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 철근부식량 라인용(주기) 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")");

        List<RebarCorrosionDto> ltdDatas = rebarCorrosionService.findBySeriesCode(seriescode);

        //=== 1. X라벨 저장 ========================================================

        List<GraphXLabel> graphyXLabelsrow = new ArrayList<>();
        ltdDatas.forEach(v -> {
            GraphXLabel graphyXLabel = new GraphXLabel(v.getPeriod(), v.getPeriodName());
            graphyXLabelsrow.add(graphyXLabel);

        });

        List<GraphXLabel> graphyXLabels = graphyXLabelsrow.stream()
                .distinct()
                .sorted(Comparator.comparing(GraphXLabel::getValue))
                .collect(Collectors.toList());


        // 2 범례및 데이터 생성 저장

        List<List<String>> graphDataColumns = new ArrayList<>();

        // X라벨을 돌면서 graphDatas 에 추가
        List<String> xRows = new ArrayList<>();
        xRows.add("x");
        graphyXLabels.forEach(x -> xRows.add(Integer.toString(x.getValue())));
        graphDataColumns.add(xRows);

        //======================T50501 노출환경에따른 주기별 철근부식량
        if(treatmentcondition.equals(TreatmentCondition.T50501)) {

            //=== 2.1 범례 저장 (내륙환경/해안환경) ==========================================
            List<GraphLegend> graphLegendsrow = new ArrayList<>();
            ltdDatas.forEach(v -> {
                GraphLegend graphLegend = new GraphLegend(1, v.getEnvironment());
                graphLegendsrow.add(graphLegend);
            });

            List<GraphLegend> graphLegends = new ArrayList<>();
            Set<GraphLegend> uniqueValues = new HashSet<>();
            for (GraphLegend graphLegend : graphLegendsrow) {
                if (uniqueValues.add(graphLegend)) {
                    graphLegends.add(graphLegend);
                }
            }
            graphLegends.sort(Comparator.comparing(GraphLegend::getName));
            //graphLegends.forEach(v->System.out.println(v.toString()));

            //=== 2.2 범례별 평균값구해서 datasets 에 넣기=======================================
            for(GraphLegend graphLegend: graphLegends){

                String label = graphLegend.getName();
                List<GraphValue> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                ltdDatas.stream()
                        .filter(v -> v.getEnvironment().equals(label))
                        .collect(Collectors.groupingBy(RebarCorrosionDto::getPeriod
                                , Collectors.averagingDouble(RebarCorrosionDto::getValue)
                        )).forEach((k, v) -> {

                    GraphValue graphValue = new GraphValue(k, String.format("%.2f", v / 4));
                    graphValues.add(graphValue);

                });

                graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                inspectdata.add(label);
                graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                graphDataColumns.add(inspectdata);

            }
        }
        else //======================T50502 혼화재료에따른 주기별 철근부식량
            if(treatmentcondition.equals(TreatmentCondition.T50502)) {

                //=== 2.1 범례 저장 (내륙환경/해안환경) ==========================================
                List<GraphLegend> graphLegendsrow = new ArrayList<>();
                ltdDatas.forEach(v -> {
                    GraphLegend graphLegend = new GraphLegend(1, v.getElement1());
                    graphLegendsrow.add(graphLegend);
                });

                List<GraphLegend> graphLegends = new ArrayList<>();
                Set<GraphLegend> uniqueValues = new HashSet<>();
                for (GraphLegend graphLegend : graphLegendsrow) {
                    if (uniqueValues.add(graphLegend)) {
                        graphLegends.add(graphLegend);
                    }
                }
                graphLegends.sort(Comparator.comparing(GraphLegend::getName));
                //graphLegends.forEach(v->System.out.println(v.toString()));

                //=== 2.2 범례별 평균값구해서 datasets 에 넣기=======================================
                for(GraphLegend graphLegend: graphLegends){

                    String label = graphLegend.getName();
                    List<GraphValue> graphValues = new ArrayList<>();
                    List<String> inspectdata = new ArrayList<>();


                    ltdDatas.stream()
                            .filter(v -> v.getElement1().equals(label))
                            .collect(Collectors.groupingBy(RebarCorrosionDto::getPeriod
                                    , Collectors.averagingDouble(RebarCorrosionDto::getValue)
                            )).forEach((k, v) -> {

                        GraphValue graphValue = new GraphValue(k, String.format("%.2f", v / 4 ));
                        graphValues.add(graphValue);

                    });

                    graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                    inspectdata.add(label);
                    graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                    graphDataColumns.add(inspectdata);

                }
            }
            else //======================T50503 초기 염분함유량에따른 주기별 철근부식량
                if(treatmentcondition.equals(TreatmentCondition.T50503)) {

                    //=== 2.1 범례 저장 (내륙환경/해안환경) ==========================================
                    List<GraphLegend> graphLegendsrow = new ArrayList<>();
                    ltdDatas.forEach(v -> {
                        GraphLegend graphLegend = new GraphLegend(1, Double.toString(v.getSaltRate()));
                        graphLegendsrow.add(graphLegend);
                    });

                    List<GraphLegend> graphLegends = new ArrayList<>();
                    Set<GraphLegend> uniqueValues = new HashSet<>();
                    for (GraphLegend graphLegend : graphLegendsrow) {
                        if (uniqueValues.add(graphLegend)) {
                            graphLegends.add(graphLegend);
                        }
                    }
                    graphLegends.sort(Comparator.comparing(GraphLegend::getName));
                    //graphLegends.forEach(v->System.out.println(v.toString()));

                    //=== 2.2 범례별 평균값구해서 datasets 에 넣기=======================================
                    for(GraphLegend graphLegend: graphLegends){


                        String label = graphLegend.getName();
                        List<GraphValue> graphValues = new ArrayList<>();
                        List<String> inspectdata = new ArrayList<>();


                        ltdDatas.stream()
                                .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                .collect(Collectors.groupingBy(RebarCorrosionDto::getPeriod
                                        , Collectors.averagingDouble(RebarCorrosionDto::getValue)
                                )).forEach((k, v) -> {

                            GraphValue graphValue = new GraphValue(k, String.format("%.2f", v/4));
                            graphValues.add(graphValue);

                        });

                        graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                        inspectdata.add(label + '%');
                        graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                        graphDataColumns.add(inspectdata);

                    }
                }
                else //======================T50504 피복두계에 따른 주기별 철근부식량
                    if(treatmentcondition.equals(TreatmentCondition.T50504)) {


                        //=== 2.2 범례별(1cm,2cm,3cm,4cm) 평균값구해서 datasets 에 넣기=======================================
                        for (int i = 1; i < 5; i++) {


                            String label = i + "cm";

                            List<GraphValue> graphValues = new ArrayList<>();
                            List<String> inspectdata = new ArrayList<>();

                            if (i == 1) {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(RebarCorrosionDto::getPeriod
                                                , Collectors.averagingDouble(RebarCorrosionDto::getValue1)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.2f", v));
                                    graphValues.add(graphValue);

                                });
                            }else if (i == 2) {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(RebarCorrosionDto::getPeriod
                                                , Collectors.averagingDouble(RebarCorrosionDto::getValue2)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.2f", v));
                                    graphValues.add(graphValue);

                                });
                            }else if (i == 3) {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(RebarCorrosionDto::getPeriod
                                                , Collectors.averagingDouble(RebarCorrosionDto::getValue3)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.2f", v));
                                    graphValues.add(graphValue);

                                });
                            }else  {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(RebarCorrosionDto::getPeriod
                                                , Collectors.averagingDouble(RebarCorrosionDto::getValue4)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.2f", v));
                                    graphValues.add(graphValue);

                                });
                            }

                            graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                            inspectdata.add(label);
                            graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                            graphDataColumns.add(inspectdata);

                        }
                    }
                    //없는옵션이면
                    else{
                         return ResponseEntity.ok(res.fail(ResponseErrorCode.E008.getCode(),ResponseErrorCode.E008.getDesc(),null,null));
                    }

        data.put("title",treatmentcondition.getGraphTitle());
        data.put("unit", MeasurementItemCode.M005.getDesc()+"(" + MeasurementItemCode.M005.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
       

        log.info(seriescode.getDesc() + " 철근부식량 라인용(주기) 그래프 데이터 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));

    }


     private ResponseEntity<Map<String,Object>> line_S006(TreatmentCondition treatmentcondition,SeriesCode seriescode, HttpServletRequest request) {
         
String currentuserid = request.getHeader("insert_id");
AjaxResponse res = new AjaxResponse();
HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 철근부식량 라인용(주기) 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")");

        List<RebarCorrosionDto> ltdDatas = rebarCorrosionService.findBySeriesCode(seriescode);

        //=== 1. X라벨 저장 ========================================================

        List<GraphXLabel> graphyXLabelsrow = new ArrayList<>();
        ltdDatas.forEach(v -> {
            GraphXLabel graphyXLabel = new GraphXLabel(v.getPeriod(), v.getPeriodName());
            graphyXLabelsrow.add(graphyXLabel);

        });

        List<GraphXLabel> graphyXLabels = graphyXLabelsrow.stream()
                .distinct()
                .sorted(Comparator.comparing(GraphXLabel::getValue))
                .collect(Collectors.toList());


        // 2 범례및 데이터 생성 저장

        List<List<String>> graphDataColumns = new ArrayList<>();

        // X라벨을 돌면서 graphDatas 에 추가
        List<String> xRows = new ArrayList<>();
        xRows.add("x");
        graphyXLabels.forEach(x -> xRows.add(Integer.toString(x.getValue())));
        graphDataColumns.add(xRows);

        //======================T60501 노출환경에따른 주기별 철근부식량
        if(treatmentcondition.equals(TreatmentCondition.T60501)) {

            //=== 2.1 범례 저장 (내륙환경/해안환경) ==========================================
            List<GraphLegend> graphLegendsrow = new ArrayList<>();
            ltdDatas.forEach(v -> {
                GraphLegend graphLegend = new GraphLegend(1, v.getEnvironment());
                graphLegendsrow.add(graphLegend);
            });

            List<GraphLegend> graphLegends = new ArrayList<>();
            Set<GraphLegend> uniqueValues = new HashSet<>();
            for (GraphLegend graphLegend : graphLegendsrow) {
                if (uniqueValues.add(graphLegend)) {
                    graphLegends.add(graphLegend);
                }
            }
            graphLegends.sort(Comparator.comparing(GraphLegend::getName));
            //graphLegends.forEach(v->System.out.println(v.toString()));

            //=== 2.2 범례별 평균값구해서 datasets 에 넣기=======================================
            for(GraphLegend graphLegend: graphLegends){

                String label = graphLegend.getName();
                List<GraphValue> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                ltdDatas.stream()
                        .filter(v -> v.getEnvironment().equals(label))
                        .collect(Collectors.groupingBy(RebarCorrosionDto::getPeriod
                                , Collectors.averagingDouble(RebarCorrosionDto::getValue)
                        )).forEach((k, v) -> {

                    GraphValue graphValue = new GraphValue(k, String.format("%.2f", v / 4 ));
                    graphValues.add(graphValue);

                });

                graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                inspectdata.add(label);
                graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                graphDataColumns.add(inspectdata);

            }
        }
        else //======================T60502 초기 염분함유량에따른 주기별 철근부식량
            if(treatmentcondition.equals(TreatmentCondition.T60502)) {

                //=== 2.1 범례 저장 (내륙환경/해안환경) ==========================================
                List<GraphLegend> graphLegendsrow = new ArrayList<>();
                ltdDatas.forEach(v -> {
                    GraphLegend graphLegend = new GraphLegend(1, Double.toString(v.getSaltRate()));
                    graphLegendsrow.add(graphLegend);
                });

                List<GraphLegend> graphLegends = new ArrayList<>();
                Set<GraphLegend> uniqueValues = new HashSet<>();
                for (GraphLegend graphLegend : graphLegendsrow) {
                    if (uniqueValues.add(graphLegend)) {
                        graphLegends.add(graphLegend);
                    }
                }
                graphLegends.sort(Comparator.comparing(GraphLegend::getName));
                //graphLegends.forEach(v->System.out.println(v.toString()));

                //=== 2.2 범례별 평균값구해서 datasets 에 넣기=======================================
                for(GraphLegend graphLegend: graphLegends){


                    String label = graphLegend.getName();
                    List<GraphValue> graphValues = new ArrayList<>();
                    List<String> inspectdata = new ArrayList<>();


                    ltdDatas.stream()
                            .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                            .collect(Collectors.groupingBy(RebarCorrosionDto::getPeriod
                                    , Collectors.averagingDouble(RebarCorrosionDto::getValue)
                            )).forEach((k, v) -> {

                        GraphValue graphValue = new GraphValue(k, String.format("%.2f", v/4));
                        graphValues.add(graphValue);

                    });

                    graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                    inspectdata.add(label + '%');
                    graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                    graphDataColumns.add(inspectdata);

                }
            }
            else //======================T60503 피복두계에 따른 주기별 철근부식량
                if(treatmentcondition.equals(TreatmentCondition.T60503)) {


                    //=== 2.2 범례별(1cm,2cm,3cm,4cm) 평균값구해서 datasets 에 넣기=======================================
                    for (int i = 1; i < 5; i++) {


                        String label = i + "cm";

                        List<GraphValue> graphValues = new ArrayList<>();
                        List<String> inspectdata = new ArrayList<>();

                        if (i == 1) {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(RebarCorrosionDto::getPeriod
                                            , Collectors.averagingDouble(RebarCorrosionDto::getValue1)
                                    )).forEach((k, v) -> {

                                GraphValue graphValue = new GraphValue(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        }else if (i == 2) {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(RebarCorrosionDto::getPeriod
                                            , Collectors.averagingDouble(RebarCorrosionDto::getValue2)
                                    )).forEach((k, v) -> {

                                GraphValue graphValue = new GraphValue(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        }else if (i == 3) {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(RebarCorrosionDto::getPeriod
                                            , Collectors.averagingDouble(RebarCorrosionDto::getValue3)
                                    )).forEach((k, v) -> {

                                GraphValue graphValue = new GraphValue(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        }else  {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(RebarCorrosionDto::getPeriod
                                            , Collectors.averagingDouble(RebarCorrosionDto::getValue4)
                                    )).forEach((k, v) -> {

                                GraphValue graphValue = new GraphValue(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        }

                        graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                        inspectdata.add(label);
                        graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                        graphDataColumns.add(inspectdata);

                    }
                }
                //없는옵션이면
                else{
                     return ResponseEntity.ok(res.fail(ResponseErrorCode.E008.getCode(),ResponseErrorCode.E008.getDesc(),null,null));
                }

        data.put("title",treatmentcondition.getGraphTitle());
        data.put("unit", MeasurementItemCode.M005.getDesc()+"(" + MeasurementItemCode.M005.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
       

        log.info(seriescode.getDesc() + " 철근부식량 라인용(주기) 그래프 데이터 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));

    }


     private ResponseEntity<Map<String,Object>> line_S007(TreatmentCondition treatmentcondition,SeriesCode seriescode, HttpServletRequest request) {
         
String currentuserid = request.getHeader("insert_id");
AjaxResponse res = new AjaxResponse();
HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 철근부식량 라인용(주기) 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")");

        List<RebarCorrosionDto> ltdDatas = rebarCorrosionService.findBySeriesCode(seriescode);

        //=== 1. X라벨 저장 ========================================================

        List<GraphXLabel> graphyXLabelsrow = new ArrayList<>();
        ltdDatas.forEach(v -> {
            GraphXLabel graphyXLabel = new GraphXLabel(v.getPeriod(), v.getPeriodName());
            graphyXLabelsrow.add(graphyXLabel);

        });

        List<GraphXLabel> graphyXLabels = graphyXLabelsrow.stream()
                .distinct()
                .sorted(Comparator.comparing(GraphXLabel::getValue))
                .collect(Collectors.toList());


        // 2 범례및 데이터 생성 저장

        List<List<String>> graphDataColumns = new ArrayList<>();

        // X라벨을 돌면서 graphDatas 에 추가
        List<String> xRows = new ArrayList<>();
        xRows.add("x");
        graphyXLabels.forEach(x -> xRows.add(Integer.toString(x.getValue())));
        graphDataColumns.add(xRows);

        //======================T70501 wc에따른 주기별 철근부식량
        if(treatmentcondition.equals(TreatmentCondition.T70501)) {

            //=== 2.1 범례 저장 (내륙환경/해안환경) ==========================================
            List<GraphLegend> graphLegendsrow = new ArrayList<>();
            ltdDatas.forEach(v -> {
                GraphLegend graphLegend = new GraphLegend(1, v.getElement1());
                graphLegendsrow.add(graphLegend);
            });

            List<GraphLegend> graphLegends = new ArrayList<>();
            Set<GraphLegend> uniqueValues = new HashSet<>();
            for (GraphLegend graphLegend : graphLegendsrow) {
                if (uniqueValues.add(graphLegend)) {
                    graphLegends.add(graphLegend);
                }
            }
            graphLegends.sort(Comparator.comparing(GraphLegend::getName));
            //graphLegends.forEach(v->System.out.println(v.toString()));

            //=== 2.2 범례별 평균값구해서 datasets 에 넣기=======================================
            for(GraphLegend graphLegend: graphLegends){

                String label = graphLegend.getName();
                List<GraphValue> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                ltdDatas.stream()
                        .filter(v -> v.getElement1().equals(label))
                        .collect(Collectors.groupingBy(RebarCorrosionDto::getPeriod
                                , Collectors.averagingDouble(RebarCorrosionDto::getValue)
                        )).forEach((k, v) -> {

                    GraphValue graphValue = new GraphValue(k, String.format("%.2f", v / 4 ));
                    graphValues.add(graphValue);

                });

                graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                inspectdata.add(label);
                graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                graphDataColumns.add(inspectdata);

            }
        }
        else //======================T70502 초기 염분함유량에따른 주기별 철근부식량
            if(treatmentcondition.equals(TreatmentCondition.T70502)) {

                //=== 2.1 범례 저장 (내륙환경/해안환경) ==========================================
                List<GraphLegend> graphLegendsrow = new ArrayList<>();
                ltdDatas.forEach(v -> {
                    GraphLegend graphLegend = new GraphLegend(1, Double.toString(v.getSaltRate()));
                    graphLegendsrow.add(graphLegend);
                });

                List<GraphLegend> graphLegends = new ArrayList<>();
                Set<GraphLegend> uniqueValues = new HashSet<>();
                for (GraphLegend graphLegend : graphLegendsrow) {
                    if (uniqueValues.add(graphLegend)) {
                        graphLegends.add(graphLegend);
                    }
                }
                graphLegends.sort(Comparator.comparing(GraphLegend::getName));
                //graphLegends.forEach(v->System.out.println(v.toString()));

                //=== 2.2 범례별 평균값구해서 datasets 에 넣기=======================================
                for(GraphLegend graphLegend: graphLegends){


                    String label = graphLegend.getName();
                    List<GraphValue> graphValues = new ArrayList<>();
                    List<String> inspectdata = new ArrayList<>();


                    ltdDatas.stream()
                            .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                            .collect(Collectors.groupingBy(RebarCorrosionDto::getPeriod
                                    , Collectors.averagingDouble(RebarCorrosionDto::getValue)
                            )).forEach((k, v) -> {

                        GraphValue graphValue = new GraphValue(k, String.format("%.2f", v/4));
                        graphValues.add(graphValue);

                    });

                    graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                    inspectdata.add(label + '%');
                    graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                    graphDataColumns.add(inspectdata);

                }
            }
            else //======================T70503 피복두계에 따른 주기별 철근부식량
                if(treatmentcondition.equals(TreatmentCondition.T70503)) {


                    //=== 2.2 범례별(1cm,2cm,3cm,4cm) 평균값구해서 datasets 에 넣기=======================================
                    for (int i = 1; i < 5; i++) {


                        String label = i + "cm";

                        List<GraphValue> graphValues = new ArrayList<>();
                        List<String> inspectdata = new ArrayList<>();

                        if (i == 1) {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(RebarCorrosionDto::getPeriod
                                            , Collectors.averagingDouble(RebarCorrosionDto::getValue1)
                                    )).forEach((k, v) -> {

                                GraphValue graphValue = new GraphValue(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        }else if (i == 2) {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(RebarCorrosionDto::getPeriod
                                            , Collectors.averagingDouble(RebarCorrosionDto::getValue2)
                                    )).forEach((k, v) -> {

                                GraphValue graphValue = new GraphValue(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        }else if (i == 3) {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(RebarCorrosionDto::getPeriod
                                            , Collectors.averagingDouble(RebarCorrosionDto::getValue3)
                                    )).forEach((k, v) -> {

                                GraphValue graphValue = new GraphValue(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        }else  {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(RebarCorrosionDto::getPeriod
                                            , Collectors.averagingDouble(RebarCorrosionDto::getValue4)
                                    )).forEach((k, v) -> {

                                GraphValue graphValue = new GraphValue(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        }

                        graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                        inspectdata.add(label);
                        graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                        graphDataColumns.add(inspectdata);

                    }
                }
                //없는옵션이면
                else{
                     return ResponseEntity.ok(res.fail(ResponseErrorCode.E008.getCode(),ResponseErrorCode.E008.getDesc(),null,null));
                }

        data.put("title",treatmentcondition.getGraphTitle());
        data.put("unit", MeasurementItemCode.M005.getDesc()+"(" + MeasurementItemCode.M005.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
       

        log.info(seriescode.getDesc() + " 철근부식량 라인용(주기) 그래프 데이터 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));

    }


}
