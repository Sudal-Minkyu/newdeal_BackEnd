package com.broadwave.backend.ltd.data.ultrasonic;

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
 * Time : 14:26
 * Remark :
 */
@RestController
@Slf4j
@RequestMapping("/api/ltd/data")
public class UltrasonicSpeedRestController {

    private final UltrasonicSpeedService ultrasonicSpeedService;

    @Autowired
    public UltrasonicSpeedRestController(UltrasonicSpeedService ultrasonicSpeedService) {
        this.ultrasonicSpeedService = ultrasonicSpeedService;
    }

    @PostMapping("us/period")
     public ResponseEntity<Map<String,Object>> periodList(@RequestParam(value="seriescode", defaultValue="") SeriesCode seriescode,
                                     HttpServletRequest request){
         
String currentuserid = request.getHeader("insert_id");
AjaxResponse res = new AjaxResponse();
HashMap<String, Object> data = new HashMap<>();
        log.info("초음파속도 주기 정보 조회(ex.28일,1년,10년..) / 조회자 :'" + currentuserid
                + "' 시리즈(코드) : '" + seriescode.getDesc() + "'(" + seriescode.getCode() + ")" );
        //List<CompressiveStrengthDto> strengthDtos = compressiveStrengthService.findBySeriesCode(seriescode);

        List<UltrasonicSpeedDto> ultrasonicSpeedDtos = ultrasonicSpeedService.findBySeriesCode(seriescode);

        List<GraphXLabel> graphyXLabelsrow = new ArrayList<>();
        ultrasonicSpeedDtos.forEach(v ->{
            GraphXLabel graphyXLabel = new GraphXLabel(v.getPeriod(), v.getPeriodName());
            graphyXLabelsrow.add(graphyXLabel);

        });

        List<GraphXLabel> graphyXLabels = graphyXLabelsrow.stream()
                .distinct()
                .sorted(Comparator.comparing(GraphXLabel::getValue))
                .collect(Collectors.toList());



        data.put("datalist",graphyXLabels);
       

        log.info("초음파속도 주기 정보 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));

    }

    @PostMapping("us")
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
        log.info(seriescode.getDesc() + " 초음파속도 Bar 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")");

        List<UltrasonicSpeedDto>  ltdDatasRaw = ultrasonicSpeedService.findBySeriesCode(seriescode);
        // 해당주기데이터만 취합
        List<UltrasonicSpeedDto> ltdDatas = ltdDatasRaw.stream()
                .filter(v -> v.getPeriodName().equals(periodname)).collect(Collectors.toList());


        List<String> categories = new ArrayList<>();

        List<List<String>> graphDataColumns = new ArrayList<>();

        //=== 1. X라벨 저장 ========================================================
        categories.add("1cm");
        categories.add("2cm");
        categories.add("3cm");
        categories.add("4cm");


        //======================T20404 w/c 길이변형율
        if(treatmentcondition.equals(TreatmentCondition.T20404)) {


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

                //=== 3.범례별 평균값구해서 graphDataColumns 에 넣기=======================================

                //범례를 루프돌면서 실데이터를 graphDatas추가
                for(GraphLegend graphLegend: graphLegends){
                    String label = graphLegend.getName();
                    List<GraphValueString> graphValues = new ArrayList<>();
                    List<String> inspectdata = new ArrayList<>();


                    for (int j = 1; j < 5; j++) {

                        if (j == 1) {
                            ltdDatas.stream()
                                    .filter(v -> v.getElement1().equals(label))
                                    .collect(Collectors.groupingBy(UltrasonicSpeedDto::getElement1
                                            , Collectors.averagingDouble(UltrasonicSpeedDto::getValue1)
                                    )).forEach((k, v) -> {
                                GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        } else if (j == 2) {
                            ltdDatas.stream()
                                    .filter(v -> v.getElement1().equals(label))
                                    .collect(Collectors.groupingBy(UltrasonicSpeedDto::getElement1
                                            , Collectors.averagingDouble(UltrasonicSpeedDto::getValue2)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        } else if (j == 3) {
                            ltdDatas.stream()
                                    .filter(v -> v.getElement1().equals(label))
                                    .collect(Collectors.groupingBy(UltrasonicSpeedDto::getElement1
                                            , Collectors.averagingDouble(UltrasonicSpeedDto::getValue3)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        } else {
                            ltdDatas.stream()
                                    .filter(v -> v.getElement1().equals(label))
                                    .collect(Collectors.groupingBy(UltrasonicSpeedDto::getElement1
                                            , Collectors.averagingDouble(UltrasonicSpeedDto::getValue4)
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
            else//======================T20405 염분함유량에따른 길이변형율
                if(treatmentcondition.equals(TreatmentCondition.T20405)) {


                    //=== 2. 범례 저장 (고로슬래그/플라이애쉬) =========================================
                    List<GraphLegend> graphLegendsrow = new ArrayList<>();
                    ltdDatas.forEach(v -> {
                        GraphLegend graphLegend = new GraphLegend(1, Double.toString(v.getSaltRate()));
                        graphLegendsrow.add(graphLegend);
                    });

                    List<GraphLegend> graphLegends = new ArrayList<>();
                    Set<GraphLegend> uniqueValues = new HashSet<>();
                    for (GraphLegend legend : graphLegendsrow) {
                        if (uniqueValues.add(legend)) {
                            graphLegends.add(legend);
                        }
                    }
                    graphLegends.sort(Comparator.comparing(GraphLegend::getName));
                    //graphLegends.forEach(v->System.out.println(v.toString()));

                    //=== 3.범례별 평균값구해서 graphDataColumns 에 넣기=======================================

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
                                                , Collectors.averagingDouble(UltrasonicSpeedDto::getValue1)
                                        )).forEach((k, v) -> {
                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                    graphValues.add(graphValue);

                                });
                            } else if (j == 2) {
                                ltdDatas.stream()
                                        .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(UltrasonicSpeedDto::getValue2)
                                        )).forEach((k, v) -> {

                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                    graphValues.add(graphValue);

                                });
                            } else if (j == 3) {
                                ltdDatas.stream()
                                        .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(UltrasonicSpeedDto::getValue3)
                                        )).forEach((k, v) -> {

                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                    graphValues.add(graphValue);

                                });
                            } else {
                                ltdDatas.stream()
                                        .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(UltrasonicSpeedDto::getValue4)
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
        data.put("unit", MeasurementItemCode.M004.getDesc()+"(" + MeasurementItemCode.M004.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
        data.put("categories",categories);
       

        log.info(seriescode.getDesc() + " 초음파속도 Bar 그래프 데이터 조회 성공");


        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    private ResponseEntity<Map<String,Object>> bar_S003(String periodname, TreatmentCondition treatmentcondition, SeriesCode seriescode, HttpServletRequest request) {

         
String currentuserid = request.getHeader("insert_id");
AjaxResponse res = new AjaxResponse();
HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 초음파속도 Bar 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")");

        List<UltrasonicSpeedDto>  ltdDatasRaw = ultrasonicSpeedService.findBySeriesCode(seriescode);
        // 해당주기데이터만 취합
        List<UltrasonicSpeedDto> ltdDatas = ltdDatasRaw.stream()
                .filter(v -> v.getPeriodName().equals(periodname)).collect(Collectors.toList());


        List<String> categories = new ArrayList<>();

        List<List<String>> graphDataColumns = new ArrayList<>();

        //=== 1. X라벨 저장 ========================================================
        categories.add("1cm");
        categories.add("2cm");
        categories.add("3cm");
        categories.add("4cm");


        //======================T30404 w/c 길이변형율
        if(treatmentcondition.equals(TreatmentCondition.T30404)) {


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

            //=== 3.범례별 평균값구해서 graphDataColumns 에 넣기=======================================

            //범례를 루프돌면서 실데이터를 graphDatas추가
            for(GraphLegend graphLegend: graphLegends){
                String label = graphLegend.getName();
                List<GraphValueString> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                for (int j = 1; j < 5; j++) {

                    if (j == 1) {
                        ltdDatas.stream()
                                .filter(v -> v.getElement1().equals(label))
                                .collect(Collectors.groupingBy(UltrasonicSpeedDto::getElement1
                                        , Collectors.averagingDouble(UltrasonicSpeedDto::getValue1)
                                )).forEach((k, v) -> {
                            GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 2) {
                        ltdDatas.stream()
                                .filter(v -> v.getElement1().equals(label))
                                .collect(Collectors.groupingBy(UltrasonicSpeedDto::getElement1
                                        , Collectors.averagingDouble(UltrasonicSpeedDto::getValue2)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 3) {
                        ltdDatas.stream()
                                .filter(v -> v.getElement1().equals(label))
                                .collect(Collectors.groupingBy(UltrasonicSpeedDto::getElement1
                                        , Collectors.averagingDouble(UltrasonicSpeedDto::getValue3)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                            graphValues.add(graphValue);

                        });
                    } else {
                        ltdDatas.stream()
                                .filter(v -> v.getElement1().equals(label))
                                .collect(Collectors.groupingBy(UltrasonicSpeedDto::getElement1
                                        , Collectors.averagingDouble(UltrasonicSpeedDto::getValue4)
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
        else//======================T30405 염분함유량에따른 길이변형율
            if(treatmentcondition.equals(TreatmentCondition.T30405)) {


                //=== 2. 범례 저장 (고로슬래그/플라이애쉬) =========================================
                List<GraphLegend> graphLegendsrow = new ArrayList<>();
                ltdDatas.forEach(v -> {
                    GraphLegend graphLegend = new GraphLegend(1, Double.toString(v.getSaltRate()));
                    graphLegendsrow.add(graphLegend);
                });

                List<GraphLegend> graphLegends = new ArrayList<>();
                Set<GraphLegend> uniqueValues = new HashSet<>();
                for (GraphLegend legend : graphLegendsrow) {
                    if (uniqueValues.add(legend)) {
                        graphLegends.add(legend);
                    }
                }
                graphLegends.sort(Comparator.comparing(GraphLegend::getName));
                //graphLegends.forEach(v->System.out.println(v.toString()));

                //=== 3.범례별 평균값구해서 graphDataColumns 에 넣기=======================================

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
                                            , Collectors.averagingDouble(UltrasonicSpeedDto::getValue1)
                                    )).forEach((k, v) -> {
                                GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        } else if (j == 2) {
                            ltdDatas.stream()
                                    .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                    .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                            , Collectors.averagingDouble(UltrasonicSpeedDto::getValue2)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        } else if (j == 3) {
                            ltdDatas.stream()
                                    .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                    .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                            , Collectors.averagingDouble(UltrasonicSpeedDto::getValue3)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        } else {
                            ltdDatas.stream()
                                    .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                    .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                            , Collectors.averagingDouble(UltrasonicSpeedDto::getValue4)
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
        data.put("unit", MeasurementItemCode.M004.getDesc()+"(" + MeasurementItemCode.M004.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
        data.put("categories",categories);
       

        log.info(seriescode.getDesc() + " 초음파속도 Bar 그래프 데이터 조회 성공");


        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    private ResponseEntity<Map<String,Object>> bar_S004(String periodname, TreatmentCondition treatmentcondition, SeriesCode seriescode, HttpServletRequest request) {

         
String currentuserid = request.getHeader("insert_id");
AjaxResponse res = new AjaxResponse();
HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 초음파속도 Bar 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")");

        List<UltrasonicSpeedDto>  ltdDatasRaw = ultrasonicSpeedService.findBySeriesCode(seriescode);
        // 해당주기데이터만 취합
        List<UltrasonicSpeedDto> ltdDatas = ltdDatasRaw.stream()
                .filter(v -> v.getPeriodName().equals(periodname)).collect(Collectors.toList());


        List<String> categories = new ArrayList<>();

        List<List<String>> graphDataColumns = new ArrayList<>();

        //=== 1. X라벨 저장 ========================================================
        categories.add("1cm");
        categories.add("2cm");
        categories.add("3cm");
        categories.add("4cm");


        //======================T40405 노출환경에따른 길이변형율
        if(treatmentcondition.equals(TreatmentCondition.T40405)) {


            //=== 2. 범례 저장 (내륙환경/해안환경) =========================================
            List<GraphLegend> graphLegendsrow = new ArrayList<>();
            ltdDatas.forEach(v -> {
                GraphLegend graphLegend = new GraphLegend(1, v.getEnvironment());
                graphLegendsrow.add(graphLegend);
            });

            List<GraphLegend> graphLegends = new ArrayList<>();
            Set<GraphLegend> uniqueValues = new HashSet<>();
            for (GraphLegend legend : graphLegendsrow) {
                if (uniqueValues.add(legend)) {
                    graphLegends.add(legend);
                }
            }

            graphLegends.sort(Comparator.comparing(GraphLegend::getName));
            //graphLegends.forEach(v->System.out.println(v.toString()));

            //=== 3.범례별 평균값구해서 graphDataColumns 에 넣기=======================================

            //범례를 루프돌면서 실데이터를 graphDatas추가
            for(GraphLegend graphLegend: graphLegends){
                String label = graphLegend.getName();
                List<GraphValueString> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                for (int j = 1; j < 5; j++) {

                    if (j == 1) {
                        ltdDatas.stream()
                                .filter(v -> v.getEnvironment().equals(label))
                                .collect(Collectors.groupingBy(UltrasonicSpeedDto::getEnvironment
                                        , Collectors.averagingDouble(UltrasonicSpeedDto::getValue1)
                                )).forEach((k, v) -> {
                            GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 2) {
                        ltdDatas.stream()
                                .filter(v -> v.getEnvironment().equals(label))
                                .collect(Collectors.groupingBy(UltrasonicSpeedDto::getEnvironment
                                        , Collectors.averagingDouble(UltrasonicSpeedDto::getValue2)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 3) {
                        ltdDatas.stream()
                                .filter(v -> v.getEnvironment().equals(label))
                                .collect(Collectors.groupingBy(UltrasonicSpeedDto::getEnvironment
                                        , Collectors.averagingDouble(UltrasonicSpeedDto::getValue3)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                            graphValues.add(graphValue);

                        });
                    } else {
                        ltdDatas.stream()
                                .filter(v -> v.getEnvironment().equals(label))
                                .collect(Collectors.groupingBy(UltrasonicSpeedDto::getEnvironment
                                        , Collectors.averagingDouble(UltrasonicSpeedDto::getValue4)
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
        else//======================T40406 사용재료에따른 길이변형율
            if(treatmentcondition.equals(TreatmentCondition.T40406)) {


                //=== 2. 범례 저장 (고로슬래그/플라이애쉬) =========================================
                List<GraphLegend> graphLegendsrow = new ArrayList<>();
                ltdDatas.forEach(v -> {
                    GraphLegend graphLegend = new GraphLegend(1, v.getElement1());
                    graphLegendsrow.add(graphLegend);
                });

                List<GraphLegend> graphLegends = new ArrayList<>();
                Set<GraphLegend> uniqueValues = new HashSet<>();
                for (GraphLegend legend : graphLegendsrow) {
                    if (uniqueValues.add(legend)) {
                        graphLegends.add(legend);
                    }
                }
                graphLegends.sort(Comparator.comparing(GraphLegend::getName));
                //graphLegends.forEach(v->System.out.println(v.toString()));

                //=== 3.범례별 평균값구해서 graphDataColumns 에 넣기=======================================

                //범례를 루프돌면서 실데이터를 graphDatas추가
                for(GraphLegend graphLegend: graphLegends){
                    String label = graphLegend.getName();
                    List<GraphValueString> graphValues = new ArrayList<>();
                    List<String> inspectdata = new ArrayList<>();


                    for (int j = 1; j < 5; j++) {

                        if (j == 1) {
                            ltdDatas.stream()
                                    .filter(v -> v.getElement1().equals(label))
                                    .collect(Collectors.groupingBy(UltrasonicSpeedDto::getElement1
                                            , Collectors.averagingDouble(UltrasonicSpeedDto::getValue1)
                                    )).forEach((k, v) -> {
                                GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        } else if (j == 2) {
                            ltdDatas.stream()
                                    .filter(v -> v.getElement1().equals(label))
                                    .collect(Collectors.groupingBy(UltrasonicSpeedDto::getElement1
                                            , Collectors.averagingDouble(UltrasonicSpeedDto::getValue2)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        } else if (j == 3) {
                            ltdDatas.stream()
                                    .filter(v -> v.getElement1().equals(label))
                                    .collect(Collectors.groupingBy(UltrasonicSpeedDto::getElement1
                                            , Collectors.averagingDouble(UltrasonicSpeedDto::getValue3)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        } else {
                            ltdDatas.stream()
                                    .filter(v -> v.getElement1().equals(label))
                                    .collect(Collectors.groupingBy(UltrasonicSpeedDto::getElement1
                                            , Collectors.averagingDouble(UltrasonicSpeedDto::getValue4)
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
            else//======================T40407 연분함유량에따른 길이변형율
                if(treatmentcondition.equals(TreatmentCondition.T40407)) {


                    //=== 2. 범례 저장 (고로슬래그/플라이애쉬) =========================================
                    List<GraphLegend> graphLegendsrow = new ArrayList<>();
                    ltdDatas.forEach(v -> {
                        GraphLegend graphLegend = new GraphLegend(1, Double.toString(v.getSaltRate()));
                        graphLegendsrow.add(graphLegend);
                    });

                    List<GraphLegend> graphLegends = new ArrayList<>();
                    Set<GraphLegend> uniqueValues = new HashSet<>();
                    for (GraphLegend legend : graphLegendsrow) {
                        if (uniqueValues.add(legend)) {
                            graphLegends.add(legend);
                        }
                    }
                    graphLegends.sort(Comparator.comparing(GraphLegend::getName));
                    //graphLegends.forEach(v->System.out.println(v.toString()));

                    //=== 3.범례별 평균값구해서 graphDataColumns 에 넣기=======================================

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
                                                , Collectors.averagingDouble(UltrasonicSpeedDto::getValue1)
                                        )).forEach((k, v) -> {
                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                    graphValues.add(graphValue);

                                });
                            } else if (j == 2) {
                                ltdDatas.stream()
                                        .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(UltrasonicSpeedDto::getValue2)
                                        )).forEach((k, v) -> {

                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                    graphValues.add(graphValue);

                                });
                            } else if (j == 3) {
                                ltdDatas.stream()
                                        .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(UltrasonicSpeedDto::getValue3)
                                        )).forEach((k, v) -> {

                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                    graphValues.add(graphValue);

                                });
                            } else {
                                ltdDatas.stream()
                                        .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(UltrasonicSpeedDto::getValue4)
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
        data.put("unit", MeasurementItemCode.M004.getDesc()+"(" + MeasurementItemCode.M004.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
        data.put("categories",categories);
       

        log.info(seriescode.getDesc() + " 초음파속도 Bar 그래프 데이터 조회 성공");


        return ResponseEntity.ok(res.dataSendSuccess(data));
    }



    private ResponseEntity<Map<String,Object>> bar_S005(String periodname, TreatmentCondition treatmentcondition, SeriesCode seriescode, HttpServletRequest request) {

         
String currentuserid = request.getHeader("insert_id");
AjaxResponse res = new AjaxResponse();
HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 초음파속도 Bar 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")");

        List<UltrasonicSpeedDto>  ltdDatasRaw = ultrasonicSpeedService.findBySeriesCode(seriescode);
        // 해당주기데이터만 취합
        List<UltrasonicSpeedDto> ltdDatas = ltdDatasRaw.stream()
                .filter(v -> v.getPeriodName().equals(periodname)).collect(Collectors.toList());


        List<String> categories = new ArrayList<>();

        List<List<String>> graphDataColumns = new ArrayList<>();

        //=== 1. X라벨 저장 ========================================================
        categories.add("1cm");
        categories.add("2cm");
        categories.add("3cm");
        categories.add("4cm");


        //======================T50405 노출환경에따른 길이변형율
        if(treatmentcondition.equals(TreatmentCondition.T50405)) {


            //=== 2. 범례 저장 (내륙환경/해안환경) =========================================
            List<GraphLegend> graphLegendsrow = new ArrayList<>();
            ltdDatas.forEach(v -> {
                GraphLegend graphLegend = new GraphLegend(1, v.getEnvironment());
                graphLegendsrow.add(graphLegend);
            });

            List<GraphLegend> graphLegends = new ArrayList<>();
            Set<GraphLegend> uniqueValues = new HashSet<>();
            for (GraphLegend legend : graphLegendsrow) {
                if (uniqueValues.add(legend)) {
                    graphLegends.add(legend);
                }
            }

            graphLegends.sort(Comparator.comparing(GraphLegend::getName));
            //graphLegends.forEach(v->System.out.println(v.toString()));

            //=== 3.범례별 평균값구해서 graphDataColumns 에 넣기=======================================

            //범례를 루프돌면서 실데이터를 graphDatas추가
            for(GraphLegend graphLegend: graphLegends){
                String label = graphLegend.getName();
                List<GraphValueString> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                for (int j = 1; j < 5; j++) {

                    if (j == 1) {
                        ltdDatas.stream()
                                .filter(v -> v.getEnvironment().equals(label))
                                .collect(Collectors.groupingBy(UltrasonicSpeedDto::getEnvironment
                                        , Collectors.averagingDouble(UltrasonicSpeedDto::getValue1)
                                )).forEach((k, v) -> {
                            GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 2) {
                        ltdDatas.stream()
                                .filter(v -> v.getEnvironment().equals(label))
                                .collect(Collectors.groupingBy(UltrasonicSpeedDto::getEnvironment
                                        , Collectors.averagingDouble(UltrasonicSpeedDto::getValue2)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 3) {
                        ltdDatas.stream()
                                .filter(v -> v.getEnvironment().equals(label))
                                .collect(Collectors.groupingBy(UltrasonicSpeedDto::getEnvironment
                                        , Collectors.averagingDouble(UltrasonicSpeedDto::getValue3)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                            graphValues.add(graphValue);

                        });
                    } else {
                        ltdDatas.stream()
                                .filter(v -> v.getEnvironment().equals(label))
                                .collect(Collectors.groupingBy(UltrasonicSpeedDto::getEnvironment
                                        , Collectors.averagingDouble(UltrasonicSpeedDto::getValue4)
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
        else//======================T50406 혼화재료에따른 길이변형율
            if(treatmentcondition.equals(TreatmentCondition.T50406)) {


                //=== 2. 범례 저장 (고로슬래그/플라이애쉬) =========================================
                List<GraphLegend> graphLegendsrow = new ArrayList<>();
                ltdDatas.forEach(v -> {
                    GraphLegend graphLegend = new GraphLegend(1, v.getElement1());
                    graphLegendsrow.add(graphLegend);
                });

                List<GraphLegend> graphLegends = new ArrayList<>();
                Set<GraphLegend> uniqueValues = new HashSet<>();
                for (GraphLegend legend : graphLegendsrow) {
                    if (uniqueValues.add(legend)) {
                        graphLegends.add(legend);
                    }
                }
                graphLegends.sort(Comparator.comparing(GraphLegend::getName));
                //graphLegends.forEach(v->System.out.println(v.toString()));

                //=== 3.범례별 평균값구해서 graphDataColumns 에 넣기=======================================

                //범례를 루프돌면서 실데이터를 graphDatas추가
                for(GraphLegend graphLegend: graphLegends){
                    String label = graphLegend.getName();
                    List<GraphValueString> graphValues = new ArrayList<>();
                    List<String> inspectdata = new ArrayList<>();


                    for (int j = 1; j < 5; j++) {

                        if (j == 1) {
                            ltdDatas.stream()
                                    .filter(v -> v.getElement1().equals(label))
                                    .collect(Collectors.groupingBy(UltrasonicSpeedDto::getElement1
                                            , Collectors.averagingDouble(UltrasonicSpeedDto::getValue1)
                                    )).forEach((k, v) -> {
                                GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        } else if (j == 2) {
                            ltdDatas.stream()
                                    .filter(v -> v.getElement1().equals(label))
                                    .collect(Collectors.groupingBy(UltrasonicSpeedDto::getElement1
                                            , Collectors.averagingDouble(UltrasonicSpeedDto::getValue2)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        } else if (j == 3) {
                            ltdDatas.stream()
                                    .filter(v -> v.getElement1().equals(label))
                                    .collect(Collectors.groupingBy(UltrasonicSpeedDto::getElement1
                                            , Collectors.averagingDouble(UltrasonicSpeedDto::getValue3)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        } else {
                            ltdDatas.stream()
                                    .filter(v -> v.getElement1().equals(label))
                                    .collect(Collectors.groupingBy(UltrasonicSpeedDto::getElement1
                                            , Collectors.averagingDouble(UltrasonicSpeedDto::getValue4)
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
            else//======================T50407 연분함유량에따른 길이변형율
                if(treatmentcondition.equals(TreatmentCondition.T50407)) {


                    //=== 2. 범례 저장 (고로슬래그/플라이애쉬) =========================================
                    List<GraphLegend> graphLegendsrow = new ArrayList<>();
                    ltdDatas.forEach(v -> {
                        GraphLegend graphLegend = new GraphLegend(1, Double.toString(v.getSaltRate()));
                        graphLegendsrow.add(graphLegend);
                    });

                    List<GraphLegend> graphLegends = new ArrayList<>();
                    Set<GraphLegend> uniqueValues = new HashSet<>();
                    for (GraphLegend legend : graphLegendsrow) {
                        if (uniqueValues.add(legend)) {
                            graphLegends.add(legend);
                        }
                    }
                    graphLegends.sort(Comparator.comparing(GraphLegend::getName));
                    //graphLegends.forEach(v->System.out.println(v.toString()));

                    //=== 3.범례별 평균값구해서 graphDataColumns 에 넣기=======================================

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
                                                , Collectors.averagingDouble(UltrasonicSpeedDto::getValue1)
                                        )).forEach((k, v) -> {
                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                    graphValues.add(graphValue);

                                });
                            } else if (j == 2) {
                                ltdDatas.stream()
                                        .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(UltrasonicSpeedDto::getValue2)
                                        )).forEach((k, v) -> {

                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                    graphValues.add(graphValue);

                                });
                            } else if (j == 3) {
                                ltdDatas.stream()
                                        .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(UltrasonicSpeedDto::getValue3)
                                        )).forEach((k, v) -> {

                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                    graphValues.add(graphValue);

                                });
                            } else {
                                ltdDatas.stream()
                                        .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(UltrasonicSpeedDto::getValue4)
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
        data.put("unit", MeasurementItemCode.M004.getDesc()+"(" + MeasurementItemCode.M004.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
        data.put("categories",categories);
       

        log.info(seriescode.getDesc() + " 초음파속도 Bar 그래프 데이터 조회 성공");


        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    private ResponseEntity<Map<String,Object>> bar_S006(String periodname, TreatmentCondition treatmentcondition, SeriesCode seriescode, HttpServletRequest request) {

         
String currentuserid = request.getHeader("insert_id");
AjaxResponse res = new AjaxResponse();
HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 초음파속도 Bar 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")");

        List<UltrasonicSpeedDto>  ltdDatasRaw = ultrasonicSpeedService.findBySeriesCode(seriescode);
        // 해당주기데이터만 취합
        List<UltrasonicSpeedDto> ltdDatas = ltdDatasRaw.stream()
                .filter(v -> v.getPeriodName().equals(periodname)).collect(Collectors.toList());


        List<String> categories = new ArrayList<>();

        List<List<String>> graphDataColumns = new ArrayList<>();

        //=== 1. X라벨 저장 ========================================================
        categories.add("1cm");
        categories.add("2cm");
        categories.add("3cm");
        categories.add("4cm");


        //======================T60404 노출환경 초음파
        if(treatmentcondition.equals(TreatmentCondition.T60404)) {


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

            //=== 3.범례별 평균값구해서 graphDataColumns 에 넣기=======================================

            //범례를 루프돌면서 실데이터를 graphDatas추가
            for(GraphLegend graphLegend: graphLegends){
                String label = graphLegend.getName();
                List<GraphValueString> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                for (int j = 1; j < 5; j++) {

                    if (j == 1) {
                        ltdDatas.stream()
                                .filter(v -> v.getEnvironment().equals(label))
                                .collect(Collectors.groupingBy(UltrasonicSpeedDto::getElement1
                                        , Collectors.averagingDouble(UltrasonicSpeedDto::getValue1)
                                )).forEach((k, v) -> {
                            GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 2) {
                        ltdDatas.stream()
                                .filter(v -> v.getEnvironment().equals(label))
                                .collect(Collectors.groupingBy(UltrasonicSpeedDto::getElement1
                                        , Collectors.averagingDouble(UltrasonicSpeedDto::getValue2)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 3) {
                        ltdDatas.stream()
                                .filter(v -> v.getEnvironment().equals(label))
                                .collect(Collectors.groupingBy(UltrasonicSpeedDto::getElement1
                                        , Collectors.averagingDouble(UltrasonicSpeedDto::getValue3)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                            graphValues.add(graphValue);

                        });
                    } else {
                        ltdDatas.stream()
                                .filter(v -> v.getEnvironment().equals(label))
                                .collect(Collectors.groupingBy(UltrasonicSpeedDto::getElement1
                                        , Collectors.averagingDouble(UltrasonicSpeedDto::getValue4)
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
        else//======================T60405 염분함유량에따른 길이변형율
            if(treatmentcondition.equals(TreatmentCondition.T60405)) {


                //=== 2. 범례 저장 (고로슬래그/플라이애쉬) =========================================
                List<GraphLegend> graphLegendsrow = new ArrayList<>();
                ltdDatas.forEach(v -> {
                    GraphLegend graphLegend = new GraphLegend(1, Double.toString(v.getSaltRate()));
                    graphLegendsrow.add(graphLegend);
                });

                List<GraphLegend> graphLegends = new ArrayList<>();
                Set<GraphLegend> uniqueValues = new HashSet<>();
                for (GraphLegend legend : graphLegendsrow) {
                    if (uniqueValues.add(legend)) {
                        graphLegends.add(legend);
                    }
                }
                graphLegends.sort(Comparator.comparing(GraphLegend::getName));
                //graphLegends.forEach(v->System.out.println(v.toString()));

                //=== 3.범례별 평균값구해서 graphDataColumns 에 넣기=======================================

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
                                            , Collectors.averagingDouble(UltrasonicSpeedDto::getValue1)
                                    )).forEach((k, v) -> {
                                GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        } else if (j == 2) {
                            ltdDatas.stream()
                                    .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                    .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                            , Collectors.averagingDouble(UltrasonicSpeedDto::getValue2)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        } else if (j == 3) {
                            ltdDatas.stream()
                                    .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                    .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                            , Collectors.averagingDouble(UltrasonicSpeedDto::getValue3)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        } else {
                            ltdDatas.stream()
                                    .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                    .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                            , Collectors.averagingDouble(UltrasonicSpeedDto::getValue4)
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
        data.put("unit", MeasurementItemCode.M004.getDesc()+"(" + MeasurementItemCode.M004.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
        data.put("categories",categories);
       

        log.info(seriescode.getDesc() + " 초음파속도 Bar 그래프 데이터 조회 성공");


        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    private ResponseEntity<Map<String,Object>> bar_S007(String periodname, TreatmentCondition treatmentcondition, SeriesCode seriescode, HttpServletRequest request) {

         
String currentuserid = request.getHeader("insert_id");
AjaxResponse res = new AjaxResponse();
HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 초음파속도 Bar 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")");

        List<UltrasonicSpeedDto>  ltdDatasRaw = ultrasonicSpeedService.findBySeriesCode(seriescode);
        // 해당주기데이터만 취합
        List<UltrasonicSpeedDto> ltdDatas = ltdDatasRaw.stream()
                .filter(v -> v.getPeriodName().equals(periodname)).collect(Collectors.toList());


        List<String> categories = new ArrayList<>();

        List<List<String>> graphDataColumns = new ArrayList<>();

        //=== 1. X라벨 저장 ========================================================
        categories.add("1cm");
        categories.add("2cm");
        categories.add("3cm");
        categories.add("4cm");


        //======================T70404 w/c 길이변형율
        if(treatmentcondition.equals(TreatmentCondition.T70404)) {


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

            //=== 3.범례별 평균값구해서 graphDataColumns 에 넣기=======================================

            //범례를 루프돌면서 실데이터를 graphDatas추가
            for(GraphLegend graphLegend: graphLegends){
                String label = graphLegend.getName();
                List<GraphValueString> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                for (int j = 1; j < 5; j++) {

                    if (j == 1) {
                        ltdDatas.stream()
                                .filter(v -> v.getElement1().equals(label))
                                .collect(Collectors.groupingBy(UltrasonicSpeedDto::getElement1
                                        , Collectors.averagingDouble(UltrasonicSpeedDto::getValue1)
                                )).forEach((k, v) -> {
                            GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 2) {
                        ltdDatas.stream()
                                .filter(v -> v.getElement1().equals(label))
                                .collect(Collectors.groupingBy(UltrasonicSpeedDto::getElement1
                                        , Collectors.averagingDouble(UltrasonicSpeedDto::getValue2)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 3) {
                        ltdDatas.stream()
                                .filter(v -> v.getElement1().equals(label))
                                .collect(Collectors.groupingBy(UltrasonicSpeedDto::getElement1
                                        , Collectors.averagingDouble(UltrasonicSpeedDto::getValue3)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                            graphValues.add(graphValue);

                        });
                    } else {
                        ltdDatas.stream()
                                .filter(v -> v.getElement1().equals(label))
                                .collect(Collectors.groupingBy(UltrasonicSpeedDto::getElement1
                                        , Collectors.averagingDouble(UltrasonicSpeedDto::getValue4)
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
        else//======================T70405 염분함유량에따른 길이변형율
            if(treatmentcondition.equals(TreatmentCondition.T70405)) {


                //=== 2. 범례 저장 (고로슬래그/플라이애쉬) =========================================
                List<GraphLegend> graphLegendsrow = new ArrayList<>();
                ltdDatas.forEach(v -> {
                    GraphLegend graphLegend = new GraphLegend(1, Double.toString(v.getSaltRate()));
                    graphLegendsrow.add(graphLegend);
                });

                List<GraphLegend> graphLegends = new ArrayList<>();
                Set<GraphLegend> uniqueValues = new HashSet<>();
                for (GraphLegend legend : graphLegendsrow) {
                    if (uniqueValues.add(legend)) {
                        graphLegends.add(legend);
                    }
                }
                graphLegends.sort(Comparator.comparing(GraphLegend::getName));
                //graphLegends.forEach(v->System.out.println(v.toString()));

                //=== 3.범례별 평균값구해서 graphDataColumns 에 넣기=======================================

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
                                            , Collectors.averagingDouble(UltrasonicSpeedDto::getValue1)
                                    )).forEach((k, v) -> {
                                GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        } else if (j == 2) {
                            ltdDatas.stream()
                                    .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                    .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                            , Collectors.averagingDouble(UltrasonicSpeedDto::getValue2)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        } else if (j == 3) {
                            ltdDatas.stream()
                                    .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                    .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                            , Collectors.averagingDouble(UltrasonicSpeedDto::getValue3)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        } else {
                            ltdDatas.stream()
                                    .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                    .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                            , Collectors.averagingDouble(UltrasonicSpeedDto::getValue4)
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
        data.put("unit", MeasurementItemCode.M004.getDesc()+"(" + MeasurementItemCode.M004.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
        data.put("categories",categories);
       

        log.info(seriescode.getDesc() + " 초음파속도 Bar 그래프 데이터 조회 성공");


        return ResponseEntity.ok(res.dataSendSuccess(data));
    }




    private ResponseEntity<Map<String,Object>> line_S002(TreatmentCondition treatmentcondition,SeriesCode seriescode, HttpServletRequest request) {
         
String currentuserid = request.getHeader("insert_id");
AjaxResponse res = new AjaxResponse();
HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 초음파속도 라인용(주기) 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")");

        List<UltrasonicSpeedDto> ltdDatas = ultrasonicSpeedService.findBySeriesCode(seriescode);

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

        //======================T20401 w/c 주기별 압축강도
        if(treatmentcondition.equals(TreatmentCondition.T20401)) {

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

                //=== 2.2 범례별 평균값구해서 graphDataColumns 에 넣기=======================================
                for(GraphLegend graphLegend: graphLegends){


                    String label = graphLegend.getName();
                    List<GraphValue> graphValues = new ArrayList<>();
                    List<String> inspectdata = new ArrayList<>();


                    ltdDatas.stream()
                            .filter(v -> v.getElement1().equals(label))
                            .collect(Collectors.groupingBy(UltrasonicSpeedDto::getPeriod
                                    , Collectors.averagingDouble(UltrasonicSpeedDto::getValue)
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
            else //======================T40402 초기 염분함유량에따른 주기별 압축강도
                if(treatmentcondition.equals(TreatmentCondition.T20402)) {

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

                    //=== 2.2 범례별 평균값구해서 graphDataColumns 에 넣기=======================================
                    for(GraphLegend graphLegend: graphLegends){


                        String label = graphLegend.getName();
                        List<GraphValue> graphValues = new ArrayList<>();
                        List<String> inspectdata = new ArrayList<>();


                        ltdDatas.stream()
                                .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                .collect(Collectors.groupingBy(UltrasonicSpeedDto::getPeriod
                                        , Collectors.averagingDouble(UltrasonicSpeedDto::getValue)
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
                else //======================T20403 피복두계에 따른 주기별 압축강도
                    if(treatmentcondition.equals(TreatmentCondition.T20403)) {


                        //=== 2.2 범례별(1cm,2cm,3cm,4cm) 평균값구해서 graphDataColumns 에 넣기=======================================
                        for (int i = 1; i < 5; i++) {


                            String label = i + "cm";

                            List<GraphValue> graphValues = new ArrayList<>();
                            List<String> inspectdata = new ArrayList<>();

                            if (i == 1) {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(UltrasonicSpeedDto::getPeriod
                                                , Collectors.averagingDouble(UltrasonicSpeedDto::getValue1)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.2f", v));
                                    graphValues.add(graphValue);

                                });
                            }else if (i == 2) {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(UltrasonicSpeedDto::getPeriod
                                                , Collectors.averagingDouble(UltrasonicSpeedDto::getValue2)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.2f", v));
                                    graphValues.add(graphValue);

                                });
                            }else if (i == 3) {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(UltrasonicSpeedDto::getPeriod
                                                , Collectors.averagingDouble(UltrasonicSpeedDto::getValue3)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.2f", v));
                                    graphValues.add(graphValue);

                                });
                            }else  {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(UltrasonicSpeedDto::getPeriod
                                                , Collectors.averagingDouble(UltrasonicSpeedDto::getValue4)
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
        data.put("unit", MeasurementItemCode.M004.getDesc()+"(" + MeasurementItemCode.M004.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
       

        log.info(seriescode.getDesc() + " 초음파속도 라인용(주기) 그래프 데이터 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));

    }

    private ResponseEntity<Map<String,Object>> line_S003(TreatmentCondition treatmentcondition,SeriesCode seriescode, HttpServletRequest request) {
         
String currentuserid = request.getHeader("insert_id");
AjaxResponse res = new AjaxResponse();
HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 초음파속도 라인용(주기) 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")");

        List<UltrasonicSpeedDto> ltdDatas = ultrasonicSpeedService.findBySeriesCode(seriescode);

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

        //======================T30401 w/c 주기별
        if(treatmentcondition.equals(TreatmentCondition.T30401)) {

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

            //=== 2.2 범례별 평균값구해서 graphDataColumns 에 넣기=======================================
            for(GraphLegend graphLegend: graphLegends){


                String label = graphLegend.getName();
                List<GraphValue> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                ltdDatas.stream()
                        .filter(v -> v.getElement1().equals(label))
                        .collect(Collectors.groupingBy(UltrasonicSpeedDto::getPeriod
                                , Collectors.averagingDouble(UltrasonicSpeedDto::getValue)
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
        else //======================T30402 초기 염분함유량에따른 주기별
            if(treatmentcondition.equals(TreatmentCondition.T30402)) {

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

                //=== 2.2 범례별 평균값구해서 graphDataColumns 에 넣기=======================================
                for(GraphLegend graphLegend: graphLegends){


                    String label = graphLegend.getName();
                    List<GraphValue> graphValues = new ArrayList<>();
                    List<String> inspectdata = new ArrayList<>();


                    ltdDatas.stream()
                            .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                            .collect(Collectors.groupingBy(UltrasonicSpeedDto::getPeriod
                                    , Collectors.averagingDouble(UltrasonicSpeedDto::getValue)
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
            else //======================T30403 피복두계에 따른 주기별
                if(treatmentcondition.equals(TreatmentCondition.T30403)) {


                    //=== 2.2 범례별(1cm,2cm,3cm,4cm) 평균값구해서 graphDataColumns 에 넣기=======================================
                    for (int i = 1; i < 5; i++) {


                        String label = i + "cm";

                        List<GraphValue> graphValues = new ArrayList<>();
                        List<String> inspectdata = new ArrayList<>();

                        if (i == 1) {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(UltrasonicSpeedDto::getPeriod
                                            , Collectors.averagingDouble(UltrasonicSpeedDto::getValue1)
                                    )).forEach((k, v) -> {

                                GraphValue graphValue = new GraphValue(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        }else if (i == 2) {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(UltrasonicSpeedDto::getPeriod
                                            , Collectors.averagingDouble(UltrasonicSpeedDto::getValue2)
                                    )).forEach((k, v) -> {

                                GraphValue graphValue = new GraphValue(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        }else if (i == 3) {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(UltrasonicSpeedDto::getPeriod
                                            , Collectors.averagingDouble(UltrasonicSpeedDto::getValue3)
                                    )).forEach((k, v) -> {

                                GraphValue graphValue = new GraphValue(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        }else  {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(UltrasonicSpeedDto::getPeriod
                                            , Collectors.averagingDouble(UltrasonicSpeedDto::getValue4)
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
        data.put("unit", MeasurementItemCode.M004.getDesc()+"(" + MeasurementItemCode.M004.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
       

        log.info(seriescode.getDesc() + " 초음파속도 라인용(주기) 그래프 데이터 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));

    }


    private ResponseEntity<Map<String,Object>> line_S004(TreatmentCondition treatmentcondition,SeriesCode seriescode, HttpServletRequest request) {
         
String currentuserid = request.getHeader("insert_id");
AjaxResponse res = new AjaxResponse();
HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 초음파속도 라인용(주기) 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")");

        List<UltrasonicSpeedDto> ltdDatas = ultrasonicSpeedService.findBySeriesCode(seriescode);

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

        //======================T40401 노출환경에따른 주기별 압축강도
        if(treatmentcondition.equals(TreatmentCondition.T40401)) {

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

            //=== 2.2 범례별 평균값구해서 graphDataColumns 에 넣기=======================================
            for(GraphLegend graphLegend: graphLegends){


                String label = graphLegend.getName();
                List<GraphValue> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                ltdDatas.stream()
                        .filter(v -> v.getEnvironment().equals(label))
                        .collect(Collectors.groupingBy(UltrasonicSpeedDto::getPeriod
                                , Collectors.averagingDouble(UltrasonicSpeedDto::getValue)
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
        else //======================T40402 사용재료에따른 주기별 압축강도
            if(treatmentcondition.equals(TreatmentCondition.T40402)) {

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

                //=== 2.2 범례별 평균값구해서 graphDataColumns 에 넣기=======================================
                for(GraphLegend graphLegend: graphLegends){


                    String label = graphLegend.getName();
                    List<GraphValue> graphValues = new ArrayList<>();
                    List<String> inspectdata = new ArrayList<>();


                    ltdDatas.stream()
                            .filter(v -> v.getElement1().equals(label))
                            .collect(Collectors.groupingBy(UltrasonicSpeedDto::getPeriod
                                    , Collectors.averagingDouble(UltrasonicSpeedDto::getValue)
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
            else //======================T40403 초기 염분함유량에따른 주기별 압축강도
                if(treatmentcondition.equals(TreatmentCondition.T40403)) {

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

                    //=== 2.2 범례별 평균값구해서 graphDataColumns 에 넣기=======================================
                    for(GraphLegend graphLegend: graphLegends){


                        String label = graphLegend.getName();
                        List<GraphValue> graphValues = new ArrayList<>();
                        List<String> inspectdata = new ArrayList<>();


                        ltdDatas.stream()
                                .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                .collect(Collectors.groupingBy(UltrasonicSpeedDto::getPeriod
                                        , Collectors.averagingDouble(UltrasonicSpeedDto::getValue)
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
                else //======================T40404 피복두계에 따른 주기별 압축강도
                    if(treatmentcondition.equals(TreatmentCondition.T40404)) {


                        //=== 2.2 범례별(1cm,2cm,3cm,4cm) 평균값구해서 graphDataColumns 에 넣기=======================================
                        for (int i = 1; i < 5; i++) {


                            String label = i + "cm";

                            List<GraphValue> graphValues = new ArrayList<>();
                            List<String> inspectdata = new ArrayList<>();

                            if (i == 1) {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(UltrasonicSpeedDto::getPeriod
                                                , Collectors.averagingDouble(UltrasonicSpeedDto::getValue1)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.2f", v));
                                    graphValues.add(graphValue);

                                });
                            }else if (i == 2) {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(UltrasonicSpeedDto::getPeriod
                                                , Collectors.averagingDouble(UltrasonicSpeedDto::getValue2)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.2f", v));
                                    graphValues.add(graphValue);

                                });
                            }else if (i == 3) {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(UltrasonicSpeedDto::getPeriod
                                                , Collectors.averagingDouble(UltrasonicSpeedDto::getValue3)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.2f", v));
                                    graphValues.add(graphValue);

                                });
                            }else  {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(UltrasonicSpeedDto::getPeriod
                                                , Collectors.averagingDouble(UltrasonicSpeedDto::getValue4)
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
        data.put("unit", MeasurementItemCode.M004.getDesc()+"(" + MeasurementItemCode.M004.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
       

        log.info(seriescode.getDesc() + " 초음파속도 라인용(주기) 그래프 데이터 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));

    }


    private ResponseEntity<Map<String,Object>> line_S005(TreatmentCondition treatmentcondition,SeriesCode seriescode, HttpServletRequest request) {
         
String currentuserid = request.getHeader("insert_id");
AjaxResponse res = new AjaxResponse();
HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 초음파속도 라인용(주기) 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")");

        List<UltrasonicSpeedDto> ltdDatas = ultrasonicSpeedService.findBySeriesCode(seriescode);

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

        //======================T50401 노출환경에따른 주기별 압축강도
        if(treatmentcondition.equals(TreatmentCondition.T50401)) {

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

            //=== 2.2 범례별 평균값구해서 graphDataColumns 에 넣기=======================================
            for(GraphLegend graphLegend: graphLegends){


                String label = graphLegend.getName();
                List<GraphValue> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                ltdDatas.stream()
                        .filter(v -> v.getEnvironment().equals(label))
                        .collect(Collectors.groupingBy(UltrasonicSpeedDto::getPeriod
                                , Collectors.averagingDouble(UltrasonicSpeedDto::getValue)
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
        else //======================T50402 혼화재료에따른 주기별 압축강도
            if(treatmentcondition.equals(TreatmentCondition.T50402)) {

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

                //=== 2.2 범례별 평균값구해서 graphDataColumns 에 넣기=======================================
                for(GraphLegend graphLegend: graphLegends){


                    String label = graphLegend.getName();
                    List<GraphValue> graphValues = new ArrayList<>();
                    List<String> inspectdata = new ArrayList<>();


                    ltdDatas.stream()
                            .filter(v -> v.getElement1().equals(label))
                            .collect(Collectors.groupingBy(UltrasonicSpeedDto::getPeriod
                                    , Collectors.averagingDouble(UltrasonicSpeedDto::getValue)
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
            else //======================T50403 초기 염분함유량에따른 주기별 압축강도
                if(treatmentcondition.equals(TreatmentCondition.T50403)) {

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

                    //=== 2.2 범례별 평균값구해서 graphDataColumns 에 넣기=======================================
                    for(GraphLegend graphLegend: graphLegends){


                        String label = graphLegend.getName();
                        List<GraphValue> graphValues = new ArrayList<>();
                        List<String> inspectdata = new ArrayList<>();


                        ltdDatas.stream()
                                .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                .collect(Collectors.groupingBy(UltrasonicSpeedDto::getPeriod
                                        , Collectors.averagingDouble(UltrasonicSpeedDto::getValue)
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
                else //======================T50404 피복두계에 따른 주기별 압축강도
                    if(treatmentcondition.equals(TreatmentCondition.T50404)) {


                        //=== 2.2 범례별(1cm,2cm,3cm,4cm) 평균값구해서 graphDataColumns 에 넣기=======================================
                        for (int i = 1; i < 5; i++) {


                            String label = i + "cm";

                            List<GraphValue> graphValues = new ArrayList<>();
                            List<String> inspectdata = new ArrayList<>();

                            if (i == 1) {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(UltrasonicSpeedDto::getPeriod
                                                , Collectors.averagingDouble(UltrasonicSpeedDto::getValue1)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.2f", v));
                                    graphValues.add(graphValue);

                                });
                            }else if (i == 2) {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(UltrasonicSpeedDto::getPeriod
                                                , Collectors.averagingDouble(UltrasonicSpeedDto::getValue2)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.2f", v));
                                    graphValues.add(graphValue);

                                });
                            }else if (i == 3) {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(UltrasonicSpeedDto::getPeriod
                                                , Collectors.averagingDouble(UltrasonicSpeedDto::getValue3)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.2f", v));
                                    graphValues.add(graphValue);

                                });
                            }else  {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(UltrasonicSpeedDto::getPeriod
                                                , Collectors.averagingDouble(UltrasonicSpeedDto::getValue4)
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
        data.put("unit", MeasurementItemCode.M004.getDesc()+"(" + MeasurementItemCode.M004.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
       

        log.info(seriescode.getDesc() + " 초음파속도 라인용(주기) 그래프 데이터 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));

    }

    private ResponseEntity<Map<String,Object>> line_S006(TreatmentCondition treatmentcondition,SeriesCode seriescode, HttpServletRequest request) {
         
String currentuserid = request.getHeader("insert_id");
AjaxResponse res = new AjaxResponse();
HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 초음파속도 라인용(주기) 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")");

        List<UltrasonicSpeedDto> ltdDatas = ultrasonicSpeedService.findBySeriesCode(seriescode);

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

        //======================T60401 노출환경 주기별
        if(treatmentcondition.equals(TreatmentCondition.T60401)) {

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

            //=== 2.2 범례별 평균값구해서 graphDataColumns 에 넣기=======================================
            for(GraphLegend graphLegend: graphLegends){


                String label = graphLegend.getName();
                List<GraphValue> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                ltdDatas.stream()
                        .filter(v -> v.getEnvironment().equals(label))
                        .collect(Collectors.groupingBy(UltrasonicSpeedDto::getPeriod
                                , Collectors.averagingDouble(UltrasonicSpeedDto::getValue)
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
        else //======================T60402 초기 염분함유량에따른 주기별
            if(treatmentcondition.equals(TreatmentCondition.T60402)) {

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

                //=== 2.2 범례별 평균값구해서 graphDataColumns 에 넣기=======================================
                for(GraphLegend graphLegend: graphLegends){


                    String label = graphLegend.getName();
                    List<GraphValue> graphValues = new ArrayList<>();
                    List<String> inspectdata = new ArrayList<>();


                    ltdDatas.stream()
                            .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                            .collect(Collectors.groupingBy(UltrasonicSpeedDto::getPeriod
                                    , Collectors.averagingDouble(UltrasonicSpeedDto::getValue)
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
            else //======================T60403 피복두계에 따른 주기별
                if(treatmentcondition.equals(TreatmentCondition.T60403)) {


                    //=== 2.2 범례별(1cm,2cm,3cm,4cm) 평균값구해서 graphDataColumns 에 넣기=======================================
                    for (int i = 1; i < 5; i++) {


                        String label = i + "cm";

                        List<GraphValue> graphValues = new ArrayList<>();
                        List<String> inspectdata = new ArrayList<>();

                        if (i == 1) {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(UltrasonicSpeedDto::getPeriod
                                            , Collectors.averagingDouble(UltrasonicSpeedDto::getValue1)
                                    )).forEach((k, v) -> {

                                GraphValue graphValue = new GraphValue(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        }else if (i == 2) {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(UltrasonicSpeedDto::getPeriod
                                            , Collectors.averagingDouble(UltrasonicSpeedDto::getValue2)
                                    )).forEach((k, v) -> {

                                GraphValue graphValue = new GraphValue(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        }else if (i == 3) {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(UltrasonicSpeedDto::getPeriod
                                            , Collectors.averagingDouble(UltrasonicSpeedDto::getValue3)
                                    )).forEach((k, v) -> {

                                GraphValue graphValue = new GraphValue(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        }else  {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(UltrasonicSpeedDto::getPeriod
                                            , Collectors.averagingDouble(UltrasonicSpeedDto::getValue4)
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
        data.put("unit", MeasurementItemCode.M004.getDesc()+"(" + MeasurementItemCode.M004.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
       

        log.info(seriescode.getDesc() + " 초음파속도 라인용(주기) 그래프 데이터 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));

    }

    private ResponseEntity<Map<String,Object>> line_S007(TreatmentCondition treatmentcondition,SeriesCode seriescode, HttpServletRequest request) {
         
String currentuserid = request.getHeader("insert_id");
AjaxResponse res = new AjaxResponse();
HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 초음파속도 라인용(주기) 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")");

        List<UltrasonicSpeedDto> ltdDatas = ultrasonicSpeedService.findBySeriesCode(seriescode);

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

        //======================T70401 w/c 주기별
        if(treatmentcondition.equals(TreatmentCondition.T70401)) {

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

            //=== 2.2 범례별 평균값구해서 graphDataColumns 에 넣기=======================================
            for(GraphLegend graphLegend: graphLegends){


                String label = graphLegend.getName();
                List<GraphValue> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                ltdDatas.stream()
                        .filter(v -> v.getElement1().equals(label))
                        .collect(Collectors.groupingBy(UltrasonicSpeedDto::getPeriod
                                , Collectors.averagingDouble(UltrasonicSpeedDto::getValue)
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
        else //======================T70402 초기 염분함유량에따른 주기별
            if(treatmentcondition.equals(TreatmentCondition.T70402)) {

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

                //=== 2.2 범례별 평균값구해서 graphDataColumns 에 넣기=======================================
                for(GraphLegend graphLegend: graphLegends){


                    String label = graphLegend.getName();
                    List<GraphValue> graphValues = new ArrayList<>();
                    List<String> inspectdata = new ArrayList<>();


                    ltdDatas.stream()
                            .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                            .collect(Collectors.groupingBy(UltrasonicSpeedDto::getPeriod
                                    , Collectors.averagingDouble(UltrasonicSpeedDto::getValue)
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
            else //======================T70403 피복두계에 따른 주기별
                if(treatmentcondition.equals(TreatmentCondition.T70403)) {


                    //=== 2.2 범례별(1cm,2cm,3cm,4cm) 평균값구해서 graphDataColumns 에 넣기=======================================
                    for (int i = 1; i < 5; i++) {


                        String label = i + "cm";

                        List<GraphValue> graphValues = new ArrayList<>();
                        List<String> inspectdata = new ArrayList<>();

                        if (i == 1) {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(UltrasonicSpeedDto::getPeriod
                                            , Collectors.averagingDouble(UltrasonicSpeedDto::getValue1)
                                    )).forEach((k, v) -> {

                                GraphValue graphValue = new GraphValue(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        }else if (i == 2) {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(UltrasonicSpeedDto::getPeriod
                                            , Collectors.averagingDouble(UltrasonicSpeedDto::getValue2)
                                    )).forEach((k, v) -> {

                                GraphValue graphValue = new GraphValue(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        }else if (i == 3) {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(UltrasonicSpeedDto::getPeriod
                                            , Collectors.averagingDouble(UltrasonicSpeedDto::getValue3)
                                    )).forEach((k, v) -> {

                                GraphValue graphValue = new GraphValue(k, String.format("%.2f", v));
                                graphValues.add(graphValue);

                            });
                        }else  {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(UltrasonicSpeedDto::getPeriod
                                            , Collectors.averagingDouble(UltrasonicSpeedDto::getValue4)
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
        data.put("unit", MeasurementItemCode.M004.getDesc()+"(" + MeasurementItemCode.M004.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
       

        log.info(seriescode.getDesc() + " 초음파속도 라인용(주기) 그래프 데이터 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));

    }


}
