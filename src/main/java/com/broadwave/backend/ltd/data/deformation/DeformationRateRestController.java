package com.broadwave.backend.ltd.data.deformation;

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
 * Date : 2019-05-07
 * Time : 15:04
 * Remark :
 */
@RestController
@Slf4j
@RequestMapping("/api/ltd/data")
public class DeformationRateRestController {

    private final DeformationRateService deformationRateService;

    @Autowired
    public DeformationRateRestController(DeformationRateService deformationRateService) {
        this.deformationRateService = deformationRateService;
    }

    @PostMapping("dr/period")
    public ResponseEntity<Map<String,Object>> periodList(@RequestParam(value="seriescode", defaultValue="") SeriesCode seriescode,
                                     HttpServletRequest request){
         
        String currentuserid = request.getHeader("insert_id");
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        log.info("길이변형률 주기 정보 조회(ex.28일,1년,10년..) / 조회자 :'" + currentuserid
                + "' 시리즈(코드) : '" + seriescode.getDesc() + "'(" + seriescode.getCode() + ")" );
        //List<CompressiveStrengthDto> strengthDtos = compressiveStrengthService.findBySeriesCode(seriescode);

        List<DeformationRateDto> deformationDtos = deformationRateService.findBySeriesCode(seriescode);

        List<GraphXLabel> graphyXLabelsrow = new ArrayList<>();
        deformationDtos.forEach(v ->{
            GraphXLabel graphyXLabel = new GraphXLabel(v.getPeriod(), v.getPeriodName());
            graphyXLabelsrow.add(graphyXLabel);

        });

        List<GraphXLabel> graphyXLabels = graphyXLabelsrow.stream()
                .distinct()
                .sorted(Comparator.comparing(GraphXLabel::getValue))
                .collect(Collectors.toList());


        data.put("datalist",graphyXLabels);
       

        log.info("길이변형률 주기 정보 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));



    }

    @PostMapping("dr")
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
        log.info( seriescode.getDesc() + " 길이변형율 Bar 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")");

        List<DeformationRateDto>  ltdDatasRaw = deformationRateService.findBySeriesCode(seriescode);
        // 해당주기데이터만 취합
        List<DeformationRateDto> ltdDatas = ltdDatasRaw.stream()
                .filter(v -> v.getPeriodName().equals(periodname)).collect(Collectors.toList());


        List<String> categories = new ArrayList<>();

        List<List<String>> graphDataColumns = new ArrayList<>();

        //=== 1. X라벨 저장 ========================================================
        categories.add("1cm");
        categories.add("2cm");
        categories.add("3cm");
        categories.add("4cm");


        //======================T20304 노출환경에따른 길이변형율
        if(treatmentcondition.equals(TreatmentCondition.T20304)) {


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
                                    .collect(Collectors.groupingBy(DeformationRateDto::getElement1
                                            , Collectors.averagingDouble(DeformationRateDto::getValue1)
                                    )).forEach((k, v) -> {
                                GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
                                graphValues.add(graphValue);

                            });
                        } else if (j == 2) {
                            ltdDatas.stream()
                                    .filter(v -> v.getElement1().equals(label))
                                    .collect(Collectors.groupingBy(DeformationRateDto::getElement1
                                            , Collectors.averagingDouble(DeformationRateDto::getValue2)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
                                graphValues.add(graphValue);

                            });
                        } else if (j == 3) {
                            ltdDatas.stream()
                                    .filter(v -> v.getElement1().equals(label))
                                    .collect(Collectors.groupingBy(DeformationRateDto::getElement1
                                            , Collectors.averagingDouble(DeformationRateDto::getValue3)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
                                graphValues.add(graphValue);

                            });
                        } else {
                            ltdDatas.stream()
                                    .filter(v -> v.getElement1().equals(label))
                                    .collect(Collectors.groupingBy(DeformationRateDto::getElement1
                                            , Collectors.averagingDouble(DeformationRateDto::getValue4)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
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
            else//======================T20305 연분함유량에따른 길이변형율
                if(treatmentcondition.equals(TreatmentCondition.T20305)) {


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
                                                , Collectors.averagingDouble(DeformationRateDto::getValue1)
                                        )).forEach((k, v) -> {
                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
                                    graphValues.add(graphValue);

                                });
                            } else if (j == 2) {
                                ltdDatas.stream()
                                        .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(DeformationRateDto::getValue2)
                                        )).forEach((k, v) -> {

                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
                                    graphValues.add(graphValue);

                                });
                            } else if (j == 3) {
                                ltdDatas.stream()
                                        .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(DeformationRateDto::getValue3)
                                        )).forEach((k, v) -> {

                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
                                    graphValues.add(graphValue);

                                });
                            } else {
                                ltdDatas.stream()
                                        .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(DeformationRateDto::getValue4)
                                        )).forEach((k, v) -> {

                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
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
        data.put("unit", MeasurementItemCode.M003.getDesc()+"(" + MeasurementItemCode.M003.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
        data.put("categories",categories);
       

        log.info(seriescode.getDesc() + " 길이변형률 Bar 그래프 데이터 조회 성공");


        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

     private ResponseEntity<Map<String,Object>> bar_S003(String periodname, TreatmentCondition treatmentcondition, SeriesCode seriescode, HttpServletRequest request) {

         
String currentuserid = request.getHeader("insert_id");
AjaxResponse res = new AjaxResponse();
HashMap<String, Object> data = new HashMap<>();
        log.info( seriescode.getDesc() + " 길이변형율 Bar 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")");

        List<DeformationRateDto>  ltdDatasRaw = deformationRateService.findBySeriesCode(seriescode);
        // 해당주기데이터만 취합
        List<DeformationRateDto> ltdDatas = ltdDatasRaw.stream()
                .filter(v -> v.getPeriodName().equals(periodname)).collect(Collectors.toList());


        List<String> categories = new ArrayList<>();


        List<List<String>> graphDataColumns = new ArrayList<>();

        //=== 1. X라벨 저장 ========================================================
        categories.add("1cm");
        categories.add("2cm");
        categories.add("3cm");
        categories.add("4cm");


        //======================T30304 노출환경에따른 길이변형율
        if(treatmentcondition.equals(TreatmentCondition.T30304)) {


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
                                .collect(Collectors.groupingBy(DeformationRateDto::getElement1
                                        , Collectors.averagingDouble(DeformationRateDto::getValue1)
                                )).forEach((k, v) -> {
                            GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 2) {
                        ltdDatas.stream()
                                .filter(v -> v.getElement1().equals(label))
                                .collect(Collectors.groupingBy(DeformationRateDto::getElement1
                                        , Collectors.averagingDouble(DeformationRateDto::getValue2)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 3) {
                        ltdDatas.stream()
                                .filter(v -> v.getElement1().equals(label))
                                .collect(Collectors.groupingBy(DeformationRateDto::getElement1
                                        , Collectors.averagingDouble(DeformationRateDto::getValue3)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
                            graphValues.add(graphValue);

                        });
                    } else {
                        ltdDatas.stream()
                                .filter(v -> v.getElement1().equals(label))
                                .collect(Collectors.groupingBy(DeformationRateDto::getElement1
                                        , Collectors.averagingDouble(DeformationRateDto::getValue4)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
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
        else//======================T30305 연분함유량에따른 길이변형율
            if(treatmentcondition.equals(TreatmentCondition.T30305)) {


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
                                            , Collectors.averagingDouble(DeformationRateDto::getValue1)
                                    )).forEach((k, v) -> {
                                GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
                                graphValues.add(graphValue);

                            });
                        } else if (j == 2) {
                            ltdDatas.stream()
                                    .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                    .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                            , Collectors.averagingDouble(DeformationRateDto::getValue2)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
                                graphValues.add(graphValue);

                            });
                        } else if (j == 3) {
                            ltdDatas.stream()
                                    .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                    .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                            , Collectors.averagingDouble(DeformationRateDto::getValue3)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
                                graphValues.add(graphValue);

                            });
                        } else {
                            ltdDatas.stream()
                                    .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                    .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                            , Collectors.averagingDouble(DeformationRateDto::getValue4)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
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
        data.put("unit", MeasurementItemCode.M003.getDesc()+"(" + MeasurementItemCode.M003.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
        data.put("categories",categories);
       

        log.info(seriescode.getDesc() + " 길이변형률 Bar 그래프 데이터 조회 성공");


        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

     private ResponseEntity<Map<String,Object>> bar_S004(String periodname, TreatmentCondition treatmentcondition, SeriesCode seriescode, HttpServletRequest request) {


        String currentuserid = request.getHeader("insert_id");
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        log.info( seriescode.getDesc() + " 길이변형율 Bar 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")");

        List<DeformationRateDto>  ltdDatasRaw = deformationRateService.findBySeriesCode(seriescode);
        // 해당주기데이터만 취합
        List<DeformationRateDto> ltdDatas = ltdDatasRaw.stream()
                .filter(v -> v.getPeriodName().equals(periodname)).collect(Collectors.toList());


        List<String> categories = new ArrayList<>();

        List<List<String>> graphDataColumns = new ArrayList<>();

        //=== 1. X라벨 저장 ========================================================
        categories.add("1cm");
        categories.add("2cm");
        categories.add("3cm");
        categories.add("4cm");


        //======================T40305 노출환경에따른 길이변형율
        if(treatmentcondition.equals(TreatmentCondition.T40305)) {


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
                                .collect(Collectors.groupingBy(DeformationRateDto::getEnvironment
                                        , Collectors.averagingDouble(DeformationRateDto::getValue1)
                                )).forEach((k, v) -> {
                            GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 2) {
                        ltdDatas.stream()
                                .filter(v -> v.getEnvironment().equals(label))
                                .collect(Collectors.groupingBy(DeformationRateDto::getEnvironment
                                        , Collectors.averagingDouble(DeformationRateDto::getValue2)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 3) {
                        ltdDatas.stream()
                                .filter(v -> v.getEnvironment().equals(label))
                                .collect(Collectors.groupingBy(DeformationRateDto::getEnvironment
                                        , Collectors.averagingDouble(DeformationRateDto::getValue3)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
                            graphValues.add(graphValue);

                        });
                    } else {
                        ltdDatas.stream()
                                .filter(v -> v.getEnvironment().equals(label))
                                .collect(Collectors.groupingBy(DeformationRateDto::getEnvironment
                                        , Collectors.averagingDouble(DeformationRateDto::getValue4)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
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
        else//======================T40306 사용재료에따른 길이변형율
            if(treatmentcondition.equals(TreatmentCondition.T40306)) {


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
                                    .collect(Collectors.groupingBy(DeformationRateDto::getElement1
                                            , Collectors.averagingDouble(DeformationRateDto::getValue1)
                                    )).forEach((k, v) -> {
                                GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
                                graphValues.add(graphValue);

                            });
                        } else if (j == 2) {
                            ltdDatas.stream()
                                    .filter(v -> v.getElement1().equals(label))
                                    .collect(Collectors.groupingBy(DeformationRateDto::getElement1
                                            , Collectors.averagingDouble(DeformationRateDto::getValue2)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
                                graphValues.add(graphValue);

                            });
                        } else if (j == 3) {
                            ltdDatas.stream()
                                    .filter(v -> v.getElement1().equals(label))
                                    .collect(Collectors.groupingBy(DeformationRateDto::getElement1
                                            , Collectors.averagingDouble(DeformationRateDto::getValue3)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
                                graphValues.add(graphValue);

                            });
                        } else {
                            ltdDatas.stream()
                                    .filter(v -> v.getElement1().equals(label))
                                    .collect(Collectors.groupingBy(DeformationRateDto::getElement1
                                            , Collectors.averagingDouble(DeformationRateDto::getValue4)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
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
            else//======================T40307 염분함유량에따른 길이변형율
                if(treatmentcondition.equals(TreatmentCondition.T40307)) {


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
                                                , Collectors.averagingDouble(DeformationRateDto::getValue1)
                                        )).forEach((k, v) -> {
                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
                                    graphValues.add(graphValue);

                                });
                            } else if (j == 2) {
                                ltdDatas.stream()
                                        .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(DeformationRateDto::getValue2)
                                        )).forEach((k, v) -> {

                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
                                    graphValues.add(graphValue);

                                });
                            } else if (j == 3) {
                                ltdDatas.stream()
                                        .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(DeformationRateDto::getValue3)
                                        )).forEach((k, v) -> {

                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
                                    graphValues.add(graphValue);

                                });
                            } else {
                                ltdDatas.stream()
                                        .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(DeformationRateDto::getValue4)
                                        )).forEach((k, v) -> {

                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
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
        data.put("unit", MeasurementItemCode.M003.getDesc()+"(" + MeasurementItemCode.M003.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
        data.put("categories",categories);
       

        log.info(seriescode.getDesc() + " 길이변형률 Bar 그래프 데이터 조회 성공");


        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

     private ResponseEntity<Map<String,Object>> bar_S005(String periodname, TreatmentCondition treatmentcondition, SeriesCode seriescode, HttpServletRequest request) {

         
String currentuserid = request.getHeader("insert_id");
AjaxResponse res = new AjaxResponse();
HashMap<String, Object> data = new HashMap<>();
        log.info( seriescode.getDesc() + " 길이변형율 Bar 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")");

        List<DeformationRateDto>  ltdDatasRaw = deformationRateService.findBySeriesCode(seriescode);
        // 해당주기데이터만 취합
        List<DeformationRateDto> ltdDatas = ltdDatasRaw.stream()
                .filter(v -> v.getPeriodName().equals(periodname)).collect(Collectors.toList());


        List<String> categories = new ArrayList<>();

        List<List<String>> graphDataColumns = new ArrayList<>();

        //=== 1. X라벨 저장 ========================================================
        categories.add("1cm");
        categories.add("2cm");
        categories.add("3cm");
        categories.add("4cm");


        //======================T50305 노출환경에따른 길이변형율
        if(treatmentcondition.equals(TreatmentCondition.T50305)) {


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
                                .collect(Collectors.groupingBy(DeformationRateDto::getEnvironment
                                        , Collectors.averagingDouble(DeformationRateDto::getValue1)
                                )).forEach((k, v) -> {
                            GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 2) {
                        ltdDatas.stream()
                                .filter(v -> v.getEnvironment().equals(label))
                                .collect(Collectors.groupingBy(DeformationRateDto::getEnvironment
                                        , Collectors.averagingDouble(DeformationRateDto::getValue2)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 3) {
                        ltdDatas.stream()
                                .filter(v -> v.getEnvironment().equals(label))
                                .collect(Collectors.groupingBy(DeformationRateDto::getEnvironment
                                        , Collectors.averagingDouble(DeformationRateDto::getValue3)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
                            graphValues.add(graphValue);

                        });
                    } else {
                        ltdDatas.stream()
                                .filter(v -> v.getEnvironment().equals(label))
                                .collect(Collectors.groupingBy(DeformationRateDto::getEnvironment
                                        , Collectors.averagingDouble(DeformationRateDto::getValue4)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
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
        else//======================T50306 혼화재료에따른 길이변형율
            if(treatmentcondition.equals(TreatmentCondition.T50306)) {


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
                                .collect(Collectors.groupingBy(DeformationRateDto::getElement1
                                        , Collectors.averagingDouble(DeformationRateDto::getValue1)
                                )).forEach((k, v) -> {
                            GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 2) {
                        ltdDatas.stream()
                                .filter(v -> v.getElement1().equals(label))
                                .collect(Collectors.groupingBy(DeformationRateDto::getElement1
                                        , Collectors.averagingDouble(DeformationRateDto::getValue2)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 3) {
                        ltdDatas.stream()
                                .filter(v -> v.getElement1().equals(label))
                                .collect(Collectors.groupingBy(DeformationRateDto::getElement1
                                        , Collectors.averagingDouble(DeformationRateDto::getValue3)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
                            graphValues.add(graphValue);

                        });
                    } else {
                        ltdDatas.stream()
                                .filter(v -> v.getElement1().equals(label))
                                .collect(Collectors.groupingBy(DeformationRateDto::getElement1
                                        , Collectors.averagingDouble(DeformationRateDto::getValue4)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
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
            else//======================T50307 연분함유량에따른 길이변형율
                if(treatmentcondition.equals(TreatmentCondition.T50307)) {


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
                                                , Collectors.averagingDouble(DeformationRateDto::getValue1)
                                        )).forEach((k, v) -> {
                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
                                    graphValues.add(graphValue);

                                });
                            } else if (j == 2) {
                                ltdDatas.stream()
                                        .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(DeformationRateDto::getValue2)
                                        )).forEach((k, v) -> {

                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
                                    graphValues.add(graphValue);

                                });
                            } else if (j == 3) {
                                ltdDatas.stream()
                                        .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(DeformationRateDto::getValue3)
                                        )).forEach((k, v) -> {

                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
                                    graphValues.add(graphValue);

                                });
                            } else {
                                ltdDatas.stream()
                                        .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(DeformationRateDto::getValue4)
                                        )).forEach((k, v) -> {

                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
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
        data.put("unit", MeasurementItemCode.M003.getDesc()+"(" + MeasurementItemCode.M003.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
        data.put("categories",categories);
       

        log.info(seriescode.getDesc() + " 길이변형률 Bar 그래프 데이터 조회 성공");


        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

     private ResponseEntity<Map<String,Object>> bar_S006(String periodname, TreatmentCondition treatmentcondition, SeriesCode seriescode, HttpServletRequest request) {


        String currentuserid = request.getHeader("insert_id");
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        log.info( seriescode.getDesc() + " 길이변형율 Bar 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")");

        List<DeformationRateDto>  ltdDatasRaw = deformationRateService.findBySeriesCode(seriescode);
        // 해당주기데이터만 취합
        List<DeformationRateDto> ltdDatas = ltdDatasRaw.stream()
                .filter(v -> v.getPeriodName().equals(periodname)).collect(Collectors.toList());


        List<String> categories = new ArrayList<>();

        List<List<String>> graphDataColumns = new ArrayList<>();

        //=== 1. X라벨 저장 ========================================================
        categories.add("1cm");
        categories.add("2cm");
        categories.add("3cm");
        categories.add("4cm");


        //======================T60304 노출환경에따른 길이변형율
        if(treatmentcondition.equals(TreatmentCondition.T60304)) {


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
                                .collect(Collectors.groupingBy(DeformationRateDto::getElement1
                                        , Collectors.averagingDouble(DeformationRateDto::getValue1)
                                )).forEach((k, v) -> {
                            GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 2) {
                        ltdDatas.stream()
                                .filter(v -> v.getEnvironment().equals(label))
                                .collect(Collectors.groupingBy(DeformationRateDto::getElement1
                                        , Collectors.averagingDouble(DeformationRateDto::getValue2)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 3) {
                        ltdDatas.stream()
                                .filter(v -> v.getEnvironment().equals(label))
                                .collect(Collectors.groupingBy(DeformationRateDto::getElement1
                                        , Collectors.averagingDouble(DeformationRateDto::getValue3)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
                            graphValues.add(graphValue);

                        });
                    } else {
                        ltdDatas.stream()
                                .filter(v -> v.getEnvironment().equals(label))
                                .collect(Collectors.groupingBy(DeformationRateDto::getElement1
                                        , Collectors.averagingDouble(DeformationRateDto::getValue4)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
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
        else//======================T60305 연분함유량에따른 길이변형율
            if(treatmentcondition.equals(TreatmentCondition.T60305)) {


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
                                            , Collectors.averagingDouble(DeformationRateDto::getValue1)
                                    )).forEach((k, v) -> {
                                GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
                                graphValues.add(graphValue);

                            });
                        } else if (j == 2) {
                            ltdDatas.stream()
                                    .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                    .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                            , Collectors.averagingDouble(DeformationRateDto::getValue2)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
                                graphValues.add(graphValue);

                            });
                        } else if (j == 3) {
                            ltdDatas.stream()
                                    .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                    .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                            , Collectors.averagingDouble(DeformationRateDto::getValue3)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
                                graphValues.add(graphValue);

                            });
                        } else {
                            ltdDatas.stream()
                                    .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                    .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                            , Collectors.averagingDouble(DeformationRateDto::getValue4)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
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
        data.put("unit", MeasurementItemCode.M003.getDesc()+"(" + MeasurementItemCode.M003.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
        data.put("categories",categories);
       

        log.info(seriescode.getDesc() + " 길이변형률 Bar 그래프 데이터 조회 성공");


        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

     private ResponseEntity<Map<String,Object>> bar_S007(String periodname, TreatmentCondition treatmentcondition, SeriesCode seriescode, HttpServletRequest request) {

         
String currentuserid = request.getHeader("insert_id");
AjaxResponse res = new AjaxResponse();
HashMap<String, Object> data = new HashMap<>();
        log.info( seriescode.getDesc() + " 길이변형율 Bar 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")");

        List<DeformationRateDto>  ltdDatasRaw = deformationRateService.findBySeriesCode(seriescode);
        // 해당주기데이터만 취합
        List<DeformationRateDto> ltdDatas = ltdDatasRaw.stream()
                .filter(v -> v.getPeriodName().equals(periodname)).collect(Collectors.toList());


        List<String> categories = new ArrayList<>();

        List<List<String>> graphDataColumns = new ArrayList<>();

        //=== 1. X라벨 저장 ========================================================
        categories.add("1cm");
        categories.add("2cm");
        categories.add("3cm");
        categories.add("4cm");


        //======================T70304 w/c에따른 길이변형율
        if(treatmentcondition.equals(TreatmentCondition.T70304)) {


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
                                .collect(Collectors.groupingBy(DeformationRateDto::getElement1
                                        , Collectors.averagingDouble(DeformationRateDto::getValue1)
                                )).forEach((k, v) -> {
                            GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 2) {
                        ltdDatas.stream()
                                .filter(v -> v.getElement1().equals(label))
                                .collect(Collectors.groupingBy(DeformationRateDto::getElement1
                                        , Collectors.averagingDouble(DeformationRateDto::getValue2)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 3) {
                        ltdDatas.stream()
                                .filter(v -> v.getElement1().equals(label))
                                .collect(Collectors.groupingBy(DeformationRateDto::getElement1
                                        , Collectors.averagingDouble(DeformationRateDto::getValue3)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
                            graphValues.add(graphValue);

                        });
                    } else {
                        ltdDatas.stream()
                                .filter(v -> v.getElement1().equals(label))
                                .collect(Collectors.groupingBy(DeformationRateDto::getElement1
                                        , Collectors.averagingDouble(DeformationRateDto::getValue4)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
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
        else//======================T70305 연분함유량에따른 길이변형율
            if(treatmentcondition.equals(TreatmentCondition.T70305)) {


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
                                            , Collectors.averagingDouble(DeformationRateDto::getValue1)
                                    )).forEach((k, v) -> {
                                GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
                                graphValues.add(graphValue);

                            });
                        } else if (j == 2) {
                            ltdDatas.stream()
                                    .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                    .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                            , Collectors.averagingDouble(DeformationRateDto::getValue2)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
                                graphValues.add(graphValue);

                            });
                        } else if (j == 3) {
                            ltdDatas.stream()
                                    .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                    .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                            , Collectors.averagingDouble(DeformationRateDto::getValue3)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
                                graphValues.add(graphValue);

                            });
                        } else {
                            ltdDatas.stream()
                                    .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                    .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                            , Collectors.averagingDouble(DeformationRateDto::getValue4)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.0f", v));
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
        data.put("unit", MeasurementItemCode.M003.getDesc()+"(" + MeasurementItemCode.M003.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
        data.put("categories",categories);
       

        log.info(seriescode.getDesc() + " 길이변형률 Bar 그래프 데이터 조회 성공");


        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

     private ResponseEntity<Map<String,Object>> line_S002(TreatmentCondition treatmentcondition,SeriesCode seriescode, HttpServletRequest request) {

         
String currentuserid = request.getHeader("insert_id");
AjaxResponse res = new AjaxResponse();
HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 길이변형률 라인용(주기) 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")");

        List<DeformationRateDto> ltdDatas = deformationRateService.findBySeriesCode(seriescode);

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

        //======================T20301 W/C 주기별 압축강도
        if(treatmentcondition.equals(TreatmentCondition.T20301)) {

            //=== 2.1 범례 저장 (내륙환경/해안환경) ==========================================
            List<GraphLegend> graphLegendsrow = new ArrayList<>();
            ltdDatas.forEach(v -> {
                GraphLegend graphLegend = new GraphLegend(1, v.getElement1());
                graphLegendsrow.add(graphLegend);
            });

            List<GraphLegend> graphLegends = graphLegendsrow.stream()
                    .distinct()
                    .collect(Collectors.toList());
            //graphLegends.forEach(v->System.out.println(v.toString()));

            //=== 2.2 범례별 평균값구해서 datasets 에 넣기=======================================
            for(GraphLegend graphLegend: graphLegends){


                String label = graphLegend.getName();
                List<GraphValue> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                ltdDatas.stream()
                        .filter(v -> v.getElement1().equals(label))
                        .collect(Collectors.groupingBy(DeformationRateDto::getPeriod
                                , Collectors.averagingDouble(DeformationRateDto::getValue)
                        )).forEach((k, v) -> {

                    GraphValue graphValue = new GraphValue(k, String.format("%.0f", v / 4));
                    graphValues.add(graphValue);

                });

                graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                inspectdata.add(label);
                graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                graphDataColumns.add(inspectdata);

            }
        }

            else //======================T20302 초기 염분함유량에따른 주기별 압축강도
                if(treatmentcondition.equals(TreatmentCondition.T20302)) {

                    //=== 2.1 범례 저장 (0.04% 0.16%) ==========================================
                    List<GraphLegend> graphLegendsrow = new ArrayList<>();
                    ltdDatas.forEach(v -> {
                        GraphLegend graphLegend = new GraphLegend(1, Double.toString(v.getSaltRate()));
                        graphLegendsrow.add(graphLegend);
                    });

                    List<GraphLegend> graphLegends = graphLegendsrow.stream()
                            .distinct()
                            .collect(Collectors.toList());
                    //graphLegends.forEach(v->System.out.println(v.toString()));

                    //=== 2.2 범례별 평균값구해서 datasets 에 넣기=======================================
                    //for (int i = 0; i < graphLegends.size(); i++) {
                    for(GraphLegend graphLegend: graphLegends){

                        String label = graphLegend.getName();
                        List<GraphValue> graphValues = new ArrayList<>();
                        List<String> inspectdata = new ArrayList<>();


                        ltdDatas.stream()
                                .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                .collect(Collectors.groupingBy(DeformationRateDto::getPeriod
                                        , Collectors.averagingDouble(DeformationRateDto::getValue)
                                )).forEach((k, v) -> {

                            GraphValue graphValue = new GraphValue(k, String.format("%.0f", v/4));
                            graphValues.add(graphValue);

                        });

                        graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                        inspectdata.add(label + '%');
                        graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                        graphDataColumns.add(inspectdata);

                    }
                }
                else //======================T20303 피복두계에 따른 주기별 압축강도
                    if(treatmentcondition.equals(TreatmentCondition.T20303)) {


                        //=== 2.2 범례별(1cm,2cm,3cm,4cm) 평균값구해서 datasets 에 넣기=======================================
                        for (int i = 1; i < 5; i++) {


                            String label = i + "cm";

                            List<GraphValue> graphValues = new ArrayList<>();
                            List<String> inspectdata = new ArrayList<>();

                            if (i == 1) {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(DeformationRateDto::getPeriod
                                                , Collectors.averagingDouble(DeformationRateDto::getValue1)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.0f", v));
                                    graphValues.add(graphValue);

                                });
                            }else if (i == 2) {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(DeformationRateDto::getPeriod
                                                , Collectors.averagingDouble(DeformationRateDto::getValue2)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.0f", v));
                                    graphValues.add(graphValue);

                                });
                            }else if (i == 3) {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(DeformationRateDto::getPeriod
                                                , Collectors.averagingDouble(DeformationRateDto::getValue3)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.0f", v));
                                    graphValues.add(graphValue);

                                });
                            }else  {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(DeformationRateDto::getPeriod
                                                , Collectors.averagingDouble(DeformationRateDto::getValue4)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.0f", v));
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
        data.put("unit", MeasurementItemCode.M003.getDesc()+"(" + MeasurementItemCode.M003.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
       

        log.info(seriescode.getDesc() + " 길이변형률 라인용(주기) 그래프 데이터 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));

    }

     private ResponseEntity<Map<String,Object>> line_S003(TreatmentCondition treatmentcondition,SeriesCode seriescode, HttpServletRequest request) {

         
String currentuserid = request.getHeader("insert_id");
AjaxResponse res = new AjaxResponse();
HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 길이변형률 라인용(주기) 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")");

        List<DeformationRateDto> ltdDatas = deformationRateService.findBySeriesCode(seriescode);

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

        //======================T30301 W/C 주기별 압축강도
        if(treatmentcondition.equals(TreatmentCondition.T30301)) {

            //=== 2.1 범례 저장 (내륙환경/해안환경) ==========================================
            List<GraphLegend> graphLegendsrow = new ArrayList<>();
            ltdDatas.forEach(v -> {
                GraphLegend graphLegend = new GraphLegend(1, v.getElement1());
                graphLegendsrow.add(graphLegend);
            });

            List<GraphLegend> graphLegends = graphLegendsrow.stream()
                    .distinct()
                    .collect(Collectors.toList());
            //graphLegends.forEach(v->System.out.println(v.toString()));

            //=== 2.2 범례별 평균값구해서 datasets 에 넣기=======================================
            for(GraphLegend graphLegend: graphLegends){


                String label = graphLegend.getName();
                List<GraphValue> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                ltdDatas.stream()
                        .filter(v -> v.getElement1().equals(label))
                        .collect(Collectors.groupingBy(DeformationRateDto::getPeriod
                                , Collectors.averagingDouble(DeformationRateDto::getValue)
                        )).forEach((k, v) -> {

                    GraphValue graphValue = new GraphValue(k, String.format("%.0f", v / 4));
                    graphValues.add(graphValue);

                });

                graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                inspectdata.add(label);
                graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                graphDataColumns.add(inspectdata);

            }
        }

        else //======================T30302 초기 염분함유량에따른 주기별 압축강도
            if(treatmentcondition.equals(TreatmentCondition.T30302)) {

                //=== 2.1 범례 저장 (0.04% 0.16%) ==========================================
                List<GraphLegend> graphLegendsrow = new ArrayList<>();
                ltdDatas.forEach(v -> {
                    GraphLegend graphLegend = new GraphLegend(1, Double.toString(v.getSaltRate()));
                    graphLegendsrow.add(graphLegend);
                });

                List<GraphLegend> graphLegends = graphLegendsrow.stream()
                        .distinct()
                        .collect(Collectors.toList());
                //graphLegends.forEach(v->System.out.println(v.toString()));

                //=== 2.2 범례별 평균값구해서 datasets 에 넣기=======================================
                //for (int i = 0; i < graphLegends.size(); i++) {
                for(GraphLegend graphLegend: graphLegends){

                    String label = graphLegend.getName();
                    List<GraphValue> graphValues = new ArrayList<>();
                    List<String> inspectdata = new ArrayList<>();


                    ltdDatas.stream()
                            .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                            .collect(Collectors.groupingBy(DeformationRateDto::getPeriod
                                    , Collectors.averagingDouble(DeformationRateDto::getValue)
                            )).forEach((k, v) -> {

                        GraphValue graphValue = new GraphValue(k, String.format("%.0f", v/4));
                        graphValues.add(graphValue);

                    });

                    graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                    inspectdata.add(label + '%');
                    graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                    graphDataColumns.add(inspectdata);

                }
            }
            else //======================T30303 피복두계에 따른 주기별 압축강도
                if(treatmentcondition.equals(TreatmentCondition.T30303)) {


                    //=== 2.2 범례별(1cm,2cm,3cm,4cm) 평균값구해서 datasets 에 넣기=======================================
                    for (int i = 1; i < 5; i++) {


                        String label = i + "cm";

                        List<GraphValue> graphValues = new ArrayList<>();
                        List<String> inspectdata = new ArrayList<>();

                        if (i == 1) {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(DeformationRateDto::getPeriod
                                            , Collectors.averagingDouble(DeformationRateDto::getValue1)
                                    )).forEach((k, v) -> {

                                GraphValue graphValue = new GraphValue(k, String.format("%.0f", v));
                                graphValues.add(graphValue);

                            });
                        }else if (i == 2) {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(DeformationRateDto::getPeriod
                                            , Collectors.averagingDouble(DeformationRateDto::getValue2)
                                    )).forEach((k, v) -> {

                                GraphValue graphValue = new GraphValue(k, String.format("%.0f", v));
                                graphValues.add(graphValue);

                            });
                        }else if (i == 3) {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(DeformationRateDto::getPeriod
                                            , Collectors.averagingDouble(DeformationRateDto::getValue3)
                                    )).forEach((k, v) -> {

                                GraphValue graphValue = new GraphValue(k, String.format("%.0f", v));
                                graphValues.add(graphValue);

                            });
                        }else  {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(DeformationRateDto::getPeriod
                                            , Collectors.averagingDouble(DeformationRateDto::getValue4)
                                    )).forEach((k, v) -> {

                                GraphValue graphValue = new GraphValue(k, String.format("%.0f", v));
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
        data.put("unit", MeasurementItemCode.M003.getDesc()+"(" + MeasurementItemCode.M003.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
       

        log.info(seriescode.getDesc() + " 길이변형률 라인용(주기) 그래프 데이터 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));

    }
     private ResponseEntity<Map<String,Object>> line_S004(TreatmentCondition treatmentcondition,SeriesCode seriescode, HttpServletRequest request) {

         
String currentuserid = request.getHeader("insert_id");
AjaxResponse res = new AjaxResponse();
HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 길이변형률 라인용(주기) 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")");

        List<DeformationRateDto> ltdDatas = deformationRateService.findBySeriesCode(seriescode);

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

        //======================T40301 노출환경에따른 주기별 압축강도
        if(treatmentcondition.equals(TreatmentCondition.T40301)) {

            //=== 2.1 범례 저장 (내륙환경/해안환경) ==========================================
            List<GraphLegend> graphLegendsrow = new ArrayList<>();
            ltdDatas.forEach(v -> {
                GraphLegend graphLegend = new GraphLegend(1, v.getEnvironment());
                graphLegendsrow.add(graphLegend);
            });

            List<GraphLegend> graphLegends = graphLegendsrow.stream()
                    .distinct()
                    .collect(Collectors.toList());
            //graphLegends.forEach(v->System.out.println(v.toString()));

            //=== 2.2 범례별 평균값구해서 datasets 에 넣기=======================================
            for(GraphLegend graphLegend: graphLegends){


                String label = graphLegend.getName();
                List<GraphValue> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                ltdDatas.stream()
                        .filter(v -> v.getEnvironment().equals(label))
                        .collect(Collectors.groupingBy(DeformationRateDto::getPeriod
                                , Collectors.averagingDouble(DeformationRateDto::getValue)
                        )).forEach((k, v) -> {

                    GraphValue graphValue = new GraphValue(k, String.format("%.0f", v / 4));
                    graphValues.add(graphValue);

                });

                graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                inspectdata.add(label);
                graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                graphDataColumns.add(inspectdata);

            }
        }
        else //======================T40302 사용재료에따른 주기별 압축강도
            if(treatmentcondition.equals(TreatmentCondition.T40302)) {

                //=== 2.1 범례 저장 (내륙환경/해안환경) ==========================================
                List<GraphLegend> graphLegendsrow = new ArrayList<>();
                ltdDatas.forEach(v -> {
                    GraphLegend graphLegend = new GraphLegend(1, v.getElement1());
                    graphLegendsrow.add(graphLegend);
                });

                List<GraphLegend> graphLegends = graphLegendsrow.stream()
                        .distinct()
                        .collect(Collectors.toList());
                //graphLegends.forEach(v->System.out.println(v.toString()));

                //=== 2.2 범례별 평균값구해서 datasets 에 넣기=======================================
                for(GraphLegend graphLegend: graphLegends){


                    String label = graphLegend.getName();
                    List<GraphValue> graphValues = new ArrayList<>();
                    List<String> inspectdata = new ArrayList<>();


                    ltdDatas.stream()
                            .filter(v -> v.getElement1().equals(label))
                            .collect(Collectors.groupingBy(DeformationRateDto::getPeriod
                                    , Collectors.averagingDouble(DeformationRateDto::getValue)
                            )).forEach((k, v) -> {

                        GraphValue graphValue = new GraphValue(k, String.format("%.0f", v / 4 ));
                        graphValues.add(graphValue);

                    });

                    graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                    inspectdata.add(label);
                    graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                    graphDataColumns.add(inspectdata);

                }
            }
            else //======================T40303 초기 염분함유량에따른 주기별 압축강도
                if(treatmentcondition.equals(TreatmentCondition.T40303)) {

                    //=== 2.1 범례 저장 (내륙환경/해안환경) ==========================================
                    List<GraphLegend> graphLegendsrow = new ArrayList<>();
                    ltdDatas.forEach(v -> {
                        GraphLegend graphLegend = new GraphLegend(1, Double.toString(v.getSaltRate()));
                        graphLegendsrow.add(graphLegend);
                    });

                    List<GraphLegend> graphLegends = graphLegendsrow.stream()
                            .distinct()
                            .collect(Collectors.toList());
                    //graphLegends.forEach(v->System.out.println(v.toString()));

                    //=== 2.2 범례별 평균값구해서 datasets 에 넣기=======================================
                    //for (int i = 0; i < graphLegends.size(); i++) {
                    for(GraphLegend graphLegend: graphLegends){

                        String label = graphLegend.getName();
                        List<GraphValue> graphValues = new ArrayList<>();
                        List<String> inspectdata = new ArrayList<>();


                        ltdDatas.stream()
                                .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                .collect(Collectors.groupingBy(DeformationRateDto::getPeriod
                                        , Collectors.averagingDouble(DeformationRateDto::getValue)
                                )).forEach((k, v) -> {

                            GraphValue graphValue = new GraphValue(k, String.format("%.0f", v/4));
                            graphValues.add(graphValue);

                        });

                        graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                        inspectdata.add(label + '%');
                        graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                        graphDataColumns.add(inspectdata);

                    }
                }
                else //======================T40304 피복두계에 따른 주기별 압축강도
                    if(treatmentcondition.equals(TreatmentCondition.T40304)) {


                        //=== 2.2 범례별(1cm,2cm,3cm,4cm) 평균값구해서 datasets 에 넣기=======================================
                        for (int i = 1; i < 5; i++) {


                            String label = i + "cm";

                            List<GraphValue> graphValues = new ArrayList<>();
                            List<String> inspectdata = new ArrayList<>();

                            if (i == 1) {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(DeformationRateDto::getPeriod
                                                , Collectors.averagingDouble(DeformationRateDto::getValue1)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.0f", v));
                                    graphValues.add(graphValue);

                                });
                            }else if (i == 2) {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(DeformationRateDto::getPeriod
                                                , Collectors.averagingDouble(DeformationRateDto::getValue2)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.0f", v));
                                    graphValues.add(graphValue);

                                });
                            }else if (i == 3) {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(DeformationRateDto::getPeriod
                                                , Collectors.averagingDouble(DeformationRateDto::getValue3)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.0f", v));
                                    graphValues.add(graphValue);

                                });
                            }else  {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(DeformationRateDto::getPeriod
                                                , Collectors.averagingDouble(DeformationRateDto::getValue4)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.0f", v));
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
        data.put("unit", MeasurementItemCode.M003.getDesc()+"(" + MeasurementItemCode.M003.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
       

        log.info(seriescode.getDesc() + " 길이변형률 라인용(주기) 그래프 데이터 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));

    }
     private ResponseEntity<Map<String,Object>> line_S005(TreatmentCondition treatmentcondition,SeriesCode seriescode, HttpServletRequest request) {

         
String currentuserid = request.getHeader("insert_id");
AjaxResponse res = new AjaxResponse();
HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 길이변형률 라인용(주기) 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")");

        List<DeformationRateDto> ltdDatas = deformationRateService.findBySeriesCode(seriescode);

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

        //======================T50301 노출환경에따른 주기별 압축강도
        if(treatmentcondition.equals(TreatmentCondition.T50301)) {

            //=== 2.1 범례 저장 (내륙환경/해안환경) ==========================================
            List<GraphLegend> graphLegendsrow = new ArrayList<>();
            ltdDatas.forEach(v -> {
                GraphLegend graphLegend = new GraphLegend(1, v.getEnvironment());
                graphLegendsrow.add(graphLegend);
            });

            List<GraphLegend> graphLegends = graphLegendsrow.stream()
                    .distinct()
                    .collect(Collectors.toList());
            //graphLegends.forEach(v->System.out.println(v.toString()));

            //=== 2.2 범례별 평균값구해서 datasets 에 넣기=======================================
            for(GraphLegend graphLegend: graphLegends){


                String label = graphLegend.getName();
                List<GraphValue> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                ltdDatas.stream()
                        .filter(v -> v.getEnvironment().equals(label))
                        .collect(Collectors.groupingBy(DeformationRateDto::getPeriod
                                , Collectors.averagingDouble(DeformationRateDto::getValue)
                        )).forEach((k, v) -> {

                    GraphValue graphValue = new GraphValue(k, String.format("%.0f", v / 4));
                    graphValues.add(graphValue);

                });

                graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                inspectdata.add(label);
                graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                graphDataColumns.add(inspectdata);

            }
        }
        else //======================T50302 혼화재료에따른 주기별 압축강도
            if(treatmentcondition.equals(TreatmentCondition.T50302)) {

                //=== 2.1 범례 저장 (내륙환경/해안환경) ==========================================
                List<GraphLegend> graphLegendsrow = new ArrayList<>();
                ltdDatas.forEach(v -> {
                    GraphLegend graphLegend = new GraphLegend(1, v.getElement1());
                    graphLegendsrow.add(graphLegend);
                });

                List<GraphLegend> graphLegends = graphLegendsrow.stream()
                        .distinct()
                        .collect(Collectors.toList());
                //graphLegends.forEach(v->System.out.println(v.toString()));

                //=== 2.2 범례별 평균값구해서 datasets 에 넣기=======================================
                for(GraphLegend graphLegend: graphLegends){


                    String label = graphLegend.getName();
                    List<GraphValue> graphValues = new ArrayList<>();
                    List<String> inspectdata = new ArrayList<>();


                    ltdDatas.stream()
                            .filter(v -> v.getElement1().equals(label))
                            .collect(Collectors.groupingBy(DeformationRateDto::getPeriod
                                    , Collectors.averagingDouble(DeformationRateDto::getValue)
                            )).forEach((k, v) -> {

                        GraphValue graphValue = new GraphValue(k, String.format("%.0f", v / 4 ));
                        graphValues.add(graphValue);

                    });

                    graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                    inspectdata.add(label);
                    graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                    graphDataColumns.add(inspectdata);

                }
            }
            else //======================T50303 초기 염분함유량에따른 주기별 압축강도
                if(treatmentcondition.equals(TreatmentCondition.T50303)) {

                    //=== 2.1 범례 저장 (내륙환경/해안환경) ==========================================
                    List<GraphLegend> graphLegendsrow = new ArrayList<>();
                    ltdDatas.forEach(v -> {
                        GraphLegend graphLegend = new GraphLegend(1, Double.toString(v.getSaltRate()));
                        graphLegendsrow.add(graphLegend);
                    });

                    List<GraphLegend> graphLegends = graphLegendsrow.stream()
                            .distinct()
                            .collect(Collectors.toList());
                    //graphLegends.forEach(v->System.out.println(v.toString()));

                    //=== 2.2 범례별 평균값구해서 datasets 에 넣기=======================================
                    //for (int i = 0; i < graphLegends.size(); i++) {
                    for(GraphLegend graphLegend: graphLegends){

                        String label = graphLegend.getName();
                        List<GraphValue> graphValues = new ArrayList<>();
                        List<String> inspectdata = new ArrayList<>();


                        ltdDatas.stream()
                                .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                                .collect(Collectors.groupingBy(DeformationRateDto::getPeriod
                                        , Collectors.averagingDouble(DeformationRateDto::getValue)
                                )).forEach((k, v) -> {

                            GraphValue graphValue = new GraphValue(k, String.format("%.0f", v/4));
                            graphValues.add(graphValue);

                        });

                        graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                        inspectdata.add(label + '%');
                        graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                        graphDataColumns.add(inspectdata);

                    }
                }
                else //======================T50304 피복두계에 따른 주기별 압축강도
                    if(treatmentcondition.equals(TreatmentCondition.T50304)) {


                        //=== 2.2 범례별(1cm,2cm,3cm,4cm) 평균값구해서 datasets 에 넣기=======================================
                        for (int i = 1; i < 5; i++) {


                            String label = i + "cm";

                            List<GraphValue> graphValues = new ArrayList<>();
                            List<String> inspectdata = new ArrayList<>();

                            if (i == 1) {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(DeformationRateDto::getPeriod
                                                , Collectors.averagingDouble(DeformationRateDto::getValue1)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.0f", v));
                                    graphValues.add(graphValue);

                                });
                            }else if (i == 2) {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(DeformationRateDto::getPeriod
                                                , Collectors.averagingDouble(DeformationRateDto::getValue2)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.0f", v));
                                    graphValues.add(graphValue);

                                });
                            }else if (i == 3) {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(DeformationRateDto::getPeriod
                                                , Collectors.averagingDouble(DeformationRateDto::getValue3)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.0f", v));
                                    graphValues.add(graphValue);

                                });
                            }else  {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(DeformationRateDto::getPeriod
                                                , Collectors.averagingDouble(DeformationRateDto::getValue4)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.0f", v));
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
        data.put("unit", MeasurementItemCode.M003.getDesc()+"(" + MeasurementItemCode.M003.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
       

        log.info(seriescode.getDesc() + " 길이변형률 라인용(주기) 그래프 데이터 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));

    }

     private ResponseEntity<Map<String,Object>> line_S006(TreatmentCondition treatmentcondition,SeriesCode seriescode, HttpServletRequest request) {

         
String currentuserid = request.getHeader("insert_id");
AjaxResponse res = new AjaxResponse();
HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 길이변형률 라인용(주기) 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")");

        List<DeformationRateDto> ltdDatas = deformationRateService.findBySeriesCode(seriescode);

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

        //======================T60301 노툴환경 주기별 압축강도
        if(treatmentcondition.equals(TreatmentCondition.T60301)) {

            //=== 2.1 범례 저장 (내륙환경/해안환경) ==========================================
            List<GraphLegend> graphLegendsrow = new ArrayList<>();
            ltdDatas.forEach(v -> {
                GraphLegend graphLegend = new GraphLegend(1, v.getEnvironment());
                graphLegendsrow.add(graphLegend);
            });

            List<GraphLegend> graphLegends = graphLegendsrow.stream()
                    .distinct()
                    .collect(Collectors.toList());
            //graphLegends.forEach(v->System.out.println(v.toString()));

            //=== 2.2 범례별 평균값구해서 datasets 에 넣기=======================================
            for(GraphLegend graphLegend: graphLegends){


                String label = graphLegend.getName();
                List<GraphValue> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                ltdDatas.stream()
                        .filter(v -> v.getEnvironment().equals(label))
                        .collect(Collectors.groupingBy(DeformationRateDto::getPeriod
                                , Collectors.averagingDouble(DeformationRateDto::getValue)
                        )).forEach((k, v) -> {

                    GraphValue graphValue = new GraphValue(k, String.format("%.0f", v / 4));
                    graphValues.add(graphValue);

                });

                graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                inspectdata.add(label);
                graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                graphDataColumns.add(inspectdata);

            }
        }

        else //======================T60302 초기 염분함유량에따른 주기별 압축강도
            if(treatmentcondition.equals(TreatmentCondition.T60302)) {

                //=== 2.1 범례 저장 (0.04% 0.16%) ==========================================
                List<GraphLegend> graphLegendsrow = new ArrayList<>();
                ltdDatas.forEach(v -> {
                    GraphLegend graphLegend = new GraphLegend(1, Double.toString(v.getSaltRate()));
                    graphLegendsrow.add(graphLegend);
                });

                List<GraphLegend> graphLegends = graphLegendsrow.stream()
                        .distinct()
                        .collect(Collectors.toList());
                //graphLegends.forEach(v->System.out.println(v.toString()));

                //=== 2.2 범례별 평균값구해서 datasets 에 넣기=======================================
                //for (int i = 0; i < graphLegends.size(); i++) {
                for(GraphLegend graphLegend: graphLegends){

                    String label = graphLegend.getName();
                    List<GraphValue> graphValues = new ArrayList<>();
                    List<String> inspectdata = new ArrayList<>();


                    ltdDatas.stream()
                            .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                            .collect(Collectors.groupingBy(DeformationRateDto::getPeriod
                                    , Collectors.averagingDouble(DeformationRateDto::getValue)
                            )).forEach((k, v) -> {

                        GraphValue graphValue = new GraphValue(k, String.format("%.0f", v/4));
                        graphValues.add(graphValue);

                    });

                    graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                    inspectdata.add(label + '%');
                    graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                    graphDataColumns.add(inspectdata);

                }
            }
            else //======================T60303 피복두계에 따른 주기별 압축강도
                if(treatmentcondition.equals(TreatmentCondition.T60303)) {


                    //=== 2.2 범례별(1cm,2cm,3cm,4cm) 평균값구해서 datasets 에 넣기=======================================
                    for (int i = 1; i < 5; i++) {


                        String label = i + "cm";

                        List<GraphValue> graphValues = new ArrayList<>();
                        List<String> inspectdata = new ArrayList<>();

                        if (i == 1) {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(DeformationRateDto::getPeriod
                                            , Collectors.averagingDouble(DeformationRateDto::getValue1)
                                    )).forEach((k, v) -> {

                                GraphValue graphValue = new GraphValue(k, String.format("%.0f", v));
                                graphValues.add(graphValue);

                            });
                        }else if (i == 2) {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(DeformationRateDto::getPeriod
                                            , Collectors.averagingDouble(DeformationRateDto::getValue2)
                                    )).forEach((k, v) -> {

                                GraphValue graphValue = new GraphValue(k, String.format("%.0f", v));
                                graphValues.add(graphValue);

                            });
                        }else if (i == 3) {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(DeformationRateDto::getPeriod
                                            , Collectors.averagingDouble(DeformationRateDto::getValue3)
                                    )).forEach((k, v) -> {

                                GraphValue graphValue = new GraphValue(k, String.format("%.0f", v));
                                graphValues.add(graphValue);

                            });
                        }else  {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(DeformationRateDto::getPeriod
                                            , Collectors.averagingDouble(DeformationRateDto::getValue4)
                                    )).forEach((k, v) -> {

                                GraphValue graphValue = new GraphValue(k, String.format("%.0f", v));
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
        data.put("unit", MeasurementItemCode.M003.getDesc()+"(" + MeasurementItemCode.M003.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
       

        log.info(seriescode.getDesc() + " 길이변형률 라인용(주기) 그래프 데이터 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));

    }

     private ResponseEntity<Map<String,Object>> line_S007(TreatmentCondition treatmentcondition,SeriesCode seriescode, HttpServletRequest request) {

         
String currentuserid = request.getHeader("insert_id");
AjaxResponse res = new AjaxResponse();
HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 길이변형률 라인용(주기) 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")");

        List<DeformationRateDto> ltdDatas = deformationRateService.findBySeriesCode(seriescode);

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

        //======================T70301 W/C 주기별 압축강도
        if(treatmentcondition.equals(TreatmentCondition.T70301)) {

            //=== 2.1 범례 저장 (내륙환경/해안환경) ==========================================
            List<GraphLegend> graphLegendsrow = new ArrayList<>();
            ltdDatas.forEach(v -> {
                GraphLegend graphLegend = new GraphLegend(1, v.getElement1());
                graphLegendsrow.add(graphLegend);
            });

            List<GraphLegend> graphLegends = graphLegendsrow.stream()
                    .distinct()
                    .collect(Collectors.toList());
            //graphLegends.forEach(v->System.out.println(v.toString()));

            //=== 2.2 범례별 평균값구해서 datasets 에 넣기=======================================
            for(GraphLegend graphLegend: graphLegends){


                String label = graphLegend.getName();
                List<GraphValue> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                ltdDatas.stream()
                        .filter(v -> v.getElement1().equals(label))
                        .collect(Collectors.groupingBy(DeformationRateDto::getPeriod
                                , Collectors.averagingDouble(DeformationRateDto::getValue)
                        )).forEach((k, v) -> {

                    GraphValue graphValue = new GraphValue(k, String.format("%.0f", v / 4));
                    graphValues.add(graphValue);

                });

                graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                inspectdata.add(label);
                graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                graphDataColumns.add(inspectdata);

            }
        }

        else //======================T70302 초기 염분함유량에따른 주기별 압축강도
            if(treatmentcondition.equals(TreatmentCondition.T70302)) {

                //=== 2.1 범례 저장 (0.04% 0.16%) ==========================================
                List<GraphLegend> graphLegendsrow = new ArrayList<>();
                ltdDatas.forEach(v -> {
                    GraphLegend graphLegend = new GraphLegend(1, Double.toString(v.getSaltRate()));
                    graphLegendsrow.add(graphLegend);
                });

                List<GraphLegend> graphLegends = graphLegendsrow.stream()
                        .distinct()
                        .collect(Collectors.toList());
                //graphLegends.forEach(v->System.out.println(v.toString()));

                //=== 2.2 범례별 평균값구해서 datasets 에 넣기=======================================
                //for (int i = 0; i < graphLegends.size(); i++) {
                for(GraphLegend graphLegend: graphLegends){

                    String label = graphLegend.getName();
                    List<GraphValue> graphValues = new ArrayList<>();
                    List<String> inspectdata = new ArrayList<>();


                    ltdDatas.stream()
                            .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                            .collect(Collectors.groupingBy(DeformationRateDto::getPeriod
                                    , Collectors.averagingDouble(DeformationRateDto::getValue)
                            )).forEach((k, v) -> {

                        GraphValue graphValue = new GraphValue(k, String.format("%.0f", v/4));
                        graphValues.add(graphValue);

                    });

                    graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                    inspectdata.add(label + '%');
                    graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                    graphDataColumns.add(inspectdata);

                }
            }
            else //======================T70303 피복두계에 따른 주기별 압축강도
                if(treatmentcondition.equals(TreatmentCondition.T70303)) {


                    //=== 2.2 범례별(1cm,2cm,3cm,4cm) 평균값구해서 datasets 에 넣기=======================================
                    for (int i = 1; i < 5; i++) {


                        String label = i + "cm";

                        List<GraphValue> graphValues = new ArrayList<>();
                        List<String> inspectdata = new ArrayList<>();

                        if (i == 1) {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(DeformationRateDto::getPeriod
                                            , Collectors.averagingDouble(DeformationRateDto::getValue1)
                                    )).forEach((k, v) -> {

                                GraphValue graphValue = new GraphValue(k, String.format("%.0f", v));
                                graphValues.add(graphValue);

                            });
                        }else if (i == 2) {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(DeformationRateDto::getPeriod
                                            , Collectors.averagingDouble(DeformationRateDto::getValue2)
                                    )).forEach((k, v) -> {

                                GraphValue graphValue = new GraphValue(k, String.format("%.0f", v));
                                graphValues.add(graphValue);

                            });
                        }else if (i == 3) {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(DeformationRateDto::getPeriod
                                            , Collectors.averagingDouble(DeformationRateDto::getValue3)
                                    )).forEach((k, v) -> {

                                GraphValue graphValue = new GraphValue(k, String.format("%.0f", v));
                                graphValues.add(graphValue);

                            });
                        }else  {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(DeformationRateDto::getPeriod
                                            , Collectors.averagingDouble(DeformationRateDto::getValue4)
                                    )).forEach((k, v) -> {

                                GraphValue graphValue = new GraphValue(k, String.format("%.0f", v));
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
        data.put("unit", MeasurementItemCode.M003.getDesc()+"(" + MeasurementItemCode.M003.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
       

        log.info(seriescode.getDesc() + " 길이변형률 라인용(주기) 그래프 데이터 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));

    }
}