package com.broadwave.backend.ltd.data.chloride;

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
 * Date : 2019-05-10
 * Time : 11:29
 * Remark :
 */
@Slf4j
@RestController
@RequestMapping("/api/ltd/data")
public class PenetratedChlorideRestController {

    private final PenetratedChlorideService penetratedChlorideService;

    @Autowired
    public PenetratedChlorideRestController(PenetratedChlorideService penetratedChlorideService) {
        this.penetratedChlorideService = penetratedChlorideService;
    }

    @PostMapping("pc/period")
    public ResponseEntity<Map<String,Object>> periodList(@RequestParam(value="seriescode", defaultValue="") SeriesCode seriescode,
                                     HttpServletRequest request){


        String currentuserid = request.getHeader("insert_id");
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        log.info("염분함유량 주기 정보 조회(ex.28일,1년,10년..) / 조회자 :'" + currentuserid
                + "' 시리즈(코드) : '" + seriescode.getDesc() + "'(" + seriescode.getCode() + ")" );
        //List<CompressiveStrengthDto> strengthDtos = compressiveStrengthService.findBySeriesCode(seriescode);

        List<PenetratedChlorideDto> penetratedChlorideDtos = penetratedChlorideService.findBySeriesCode(seriescode);

        List<GraphXLabel> graphyXLabelsrow = new ArrayList<>();
        penetratedChlorideDtos.forEach(v ->{
            GraphXLabel graphyXLabel = new GraphXLabel(v.getPeriod(), v.getPeriodName());
            graphyXLabelsrow.add(graphyXLabel);

        });

        List<GraphXLabel> graphyXLabels = graphyXLabelsrow.stream()
                .distinct()
                .sorted(Comparator.comparing(GraphXLabel::getValue))
                .collect(Collectors.toList());

        data.put("datalist",graphyXLabels);

        log.info("염분함유량 주기 정보 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }
    @PostMapping("pc")
    public ResponseEntity<Map<String,Object>> graphData(@RequestParam(value="seriescode", defaultValue="") SeriesCode seriescode,
                                    @RequestParam (value="periodtype", defaultValue="") PeriodType periodtype,
                                    @RequestParam (value="treatmentcondition", defaultValue="") TreatmentCondition treatmentcondition,
                                    @RequestParam (value="periodname", defaultValue="") String periodname,
                                    HttpServletRequest request){

        AjaxResponse res = new AjaxResponse();

        if (periodtype.equals(PeriodType.P01)) {
            if (seriescode.equals(SeriesCode.S001)) {
                return line_S001(treatmentcondition, seriescode, request);
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
                return bar_S001(periodname,treatmentcondition,seriescode,request);
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

    private ResponseEntity<Map<String,Object>> line_S001(TreatmentCondition treatmentcondition,SeriesCode seriescode, HttpServletRequest request) {

        String currentuserid = request.getHeader("insert_id");
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 염분함유량 라인용(주기) 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")");

        List<PenetratedChlorideDto> ltdDatas = penetratedChlorideService.findBySeriesCode(seriescode);

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

        //======================T10701 wc 에따른 주기별 염분함유량
        if(treatmentcondition.equals(TreatmentCondition.T10701)) {

            //=== 2.1 범례 저장 (내륙환경/해안환경) ==========================================
            List<GraphLegend> graphLegendsrow = new ArrayList<>();
            ltdDatas.forEach(v -> {
                GraphLegend graphLegend = new GraphLegend(1, v.getElement2());
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
                        .filter(v -> v.getElement2().equals(label))
                        .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue)
                        )).forEach((k, v) -> {

                    GraphValue graphValue = new GraphValue(k, String.format("%.3f", v / 6));
                    graphValues.add(graphValue);

                });

                graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                inspectdata.add(label);
                graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                graphDataColumns.add(inspectdata);

            }
        }
        else //======================T10702 방청제 에따른 주기별 염분함유량
            if(treatmentcondition.equals(TreatmentCondition.T10702)) {

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
                            .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                    , Collectors.averagingDouble(PenetratedChlorideDto::getValue)
                            )).forEach((k, v) -> {

                        GraphValue graphValue = new GraphValue(k, String.format("%.3f", v / 6 ));
                        graphValues.add(graphValue);

                    });

                    graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                    inspectdata.add(label);
                    graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                    graphDataColumns.add(inspectdata);

                }
            }
            else //======================T10703 초기 염분함유량에따른 주기별 염분함유량
                if(treatmentcondition.equals(TreatmentCondition.T10703)) {

                    //=== 2.1 범례 저장 (내륙환경/해안환경) ==========================================
                    List<GraphLegend> graphLegendsrow = new ArrayList<>();
                    ltdDatas.forEach(v -> {
                        GraphLegend graphLegend = new GraphLegend(1, String.format("%.2f",v.getSaltRate()));
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
                                .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                                .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                        , Collectors.averagingDouble(PenetratedChlorideDto::getValue)
                                )).forEach((k, v) -> {

                            GraphValue graphValue = new GraphValue(k, String.format("%.3f", v/6));
                            graphValues.add(graphValue);

                        });

                        graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                        inspectdata.add(label + '%');
                        graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                        graphDataColumns.add(inspectdata);

                    }
                }
                else //======================T10704 콘크리트깊이 따른 주기별 염분함유량
                    if(treatmentcondition.equals(TreatmentCondition.T10704)) {


                        //=== 2.2 범례별(1cm,2cm,3cm,4cm) 평균값구해서 datasets 에 넣기=======================================
                        for (int i = 1; i < 7; i++) {

                            String label = "";

                            if (i==1) {label = "0-0.15cm";}
                            if (i==2) {label = "0.15-1.5cm";}
                            if (i==3) {label = "1.5-3cm";}
                            if (i==4) {label = "3-4.5cm";}
                            if (i==5) {label = "4.5-6cm";}
                            if (i==6) {label = "10cm";}

                            List<GraphValue> graphValues = new ArrayList<>();
                            List<String> inspectdata = new ArrayList<>();

                            if (i == 1) {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue1)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.3f", v));
                                    graphValues.add(graphValue);

                                });
                            }else if (i == 2) {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue2)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.3f", v));
                                    graphValues.add(graphValue);

                                });
                            }else if (i == 3) {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue3)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.3f", v));
                                    graphValues.add(graphValue);

                                });
                            }else if (i == 4) {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue4)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.3f", v));
                                    graphValues.add(graphValue);

                                });
                            }else if (i == 5) {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue5)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.3f", v));
                                    graphValues.add(graphValue);

                                });
                            }else  {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue6)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.3f", v));
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
        data.put("unit", MeasurementItemCode.M007.getDesc()+"(" + MeasurementItemCode.M007.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);

        log.info(seriescode.getDesc() + " 염분함유량 라인용(주기) 그래프 데이터 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));

    }

    private ResponseEntity<Map<String,Object>> line_S002(TreatmentCondition treatmentcondition,SeriesCode seriescode, HttpServletRequest request) {

        String currentuserid = request.getHeader("insert_id");
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 염분함유량 라인용(주기) 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")");

        List<PenetratedChlorideDto> ltdDatas = penetratedChlorideService.findBySeriesCode(seriescode);

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

        //======================T20701 wc 에따른 주기별 염분함유량
        if(treatmentcondition.equals(TreatmentCondition.T20701)) {

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
                            .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                    , Collectors.averagingDouble(PenetratedChlorideDto::getValue)
                            )).forEach((k, v) -> {

                        GraphValue graphValue = new GraphValue(k, String.format("%.3f", v / 6 ));
                        graphValues.add(graphValue);

                    });

                    graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                    inspectdata.add(label);
                    graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                    graphDataColumns.add(inspectdata);

                }
            }
            else //======================T20702 초기 염분함유량에따른 주기별 염분함유량
                if(treatmentcondition.equals(TreatmentCondition.T20702)) {

                    //=== 2.1 범례 저장 (내륙환경/해안환경) ==========================================
                    List<GraphLegend> graphLegendsrow = new ArrayList<>();
                    ltdDatas.forEach(v -> {
                        GraphLegend graphLegend = new GraphLegend(1, String.format("%.2f",v.getSaltRate()));
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
                                .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                                .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                        , Collectors.averagingDouble(PenetratedChlorideDto::getValue)
                                )).forEach((k, v) -> {

                            GraphValue graphValue = new GraphValue(k, String.format("%.3f", v/6));
                            graphValues.add(graphValue);

                        });

                        graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                        inspectdata.add(label + '%');
                        graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                        graphDataColumns.add(inspectdata);

                    }
                }
                else //======================T20703 콘크리트 두께 에 따른 주기별 염분함유량
                    if(treatmentcondition.equals(TreatmentCondition.T20703)) {


                        //=== 2.2 범례별(1cm,2cm,3cm,4cm) 평균값구해서 datasets 에 넣기=======================================
                        for (int i = 1; i < 7; i++) {

                            String label = "";

                            if (i==1) {label = "0-0.15cm";}
                            if (i==2) {label = "0.15-1.5cm";}
                            if (i==3) {label = "1.5-3cm";}
                            if (i==4) {label = "3-4.5cm";}
                            if (i==5) {label = "4.5-6cm";}
                            if (i==6) {label = "10cm";}

                            List<GraphValue> graphValues = new ArrayList<>();
                            List<String> inspectdata = new ArrayList<>();

                            if (i == 1) {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue1)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.3f", v));
                                    graphValues.add(graphValue);

                                });
                            }else if (i == 2) {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue2)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.3f", v));
                                    graphValues.add(graphValue);

                                });
                            }else if (i == 3) {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue3)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.3f", v));
                                    graphValues.add(graphValue);

                                });
                            }else if (i == 4) {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue4)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.3f", v));
                                    graphValues.add(graphValue);

                                });
                            }else if (i == 5) {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue5)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.3f", v));
                                    graphValues.add(graphValue);

                                });
                            }else  {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue6)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.3f", v));
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
        data.put("unit", MeasurementItemCode.M007.getDesc()+"(" + MeasurementItemCode.M007.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);

        log.info(seriescode.getDesc() + " 염분함유량 라인용(주기) 그래프 데이터 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));

    }

    private ResponseEntity<Map<String,Object>> line_S003(TreatmentCondition treatmentcondition,SeriesCode seriescode, HttpServletRequest request) {

        String currentuserid = request.getHeader("insert_id");
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 염분함유량 라인용(주기) 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")");

        List<PenetratedChlorideDto> ltdDatas = penetratedChlorideService.findBySeriesCode(seriescode);

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

        //======================T30701 wc 에따른 주기별 염분함유량
        if(treatmentcondition.equals(TreatmentCondition.T30701)) {

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
                        .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue)
                        )).forEach((k, v) -> {

                    GraphValue graphValue = new GraphValue(k, String.format("%.3f", v / 6 ));
                    graphValues.add(graphValue);

                });

                graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                inspectdata.add(label);
                graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                graphDataColumns.add(inspectdata);

            }
        }
        else //======================T30702 초기 염분함유량에따른 주기별 염분함유량
            if(treatmentcondition.equals(TreatmentCondition.T30702)) {

                //=== 2.1 범례 저장 (내륙환경/해안환경) ==========================================
                List<GraphLegend> graphLegendsrow = new ArrayList<>();
                ltdDatas.forEach(v -> {
                    GraphLegend graphLegend = new GraphLegend(1, String.format("%.2f",v.getSaltRate()));
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
                            .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                            .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                    , Collectors.averagingDouble(PenetratedChlorideDto::getValue)
                            )).forEach((k, v) -> {

                        GraphValue graphValue = new GraphValue(k, String.format("%.3f", v/6));
                        graphValues.add(graphValue);

                    });

                    graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                    inspectdata.add(label + '%');
                    graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                    graphDataColumns.add(inspectdata);

                }
            }
            else //======================T30703 콘크리트 두께 에 따른 주기별 염분함유량
                if(treatmentcondition.equals(TreatmentCondition.T30703)) {


                    //=== 2.2 범례별(1cm,2cm,3cm,4cm) 평균값구해서 datasets 에 넣기=======================================
                    for (int i = 1; i < 7; i++) {

                        String label = "";

                        if (i==1) {label = "0-0.15cm";}
                        if (i==2) {label = "0.15-1.5cm";}
                        if (i==3) {label = "1.5-3cm";}
                        if (i==4) {label = "3-4.5cm";}
                        if (i==5) {label = "4.5-6cm";}
                        if (i==6) {label = "10cm";}

                        List<GraphValue> graphValues = new ArrayList<>();
                        List<String> inspectdata = new ArrayList<>();

                        if (i == 1) {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue1)
                                    )).forEach((k, v) -> {

                                GraphValue graphValue = new GraphValue(k, String.format("%.3f", v));
                                graphValues.add(graphValue);

                            });
                        }else if (i == 2) {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue2)
                                    )).forEach((k, v) -> {

                                GraphValue graphValue = new GraphValue(k, String.format("%.3f", v));
                                graphValues.add(graphValue);

                            });
                        }else if (i == 3) {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue3)
                                    )).forEach((k, v) -> {

                                GraphValue graphValue = new GraphValue(k, String.format("%.3f", v));
                                graphValues.add(graphValue);

                            });
                        }else if (i == 4) {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue4)
                                    )).forEach((k, v) -> {

                                GraphValue graphValue = new GraphValue(k, String.format("%.3f", v));
                                graphValues.add(graphValue);

                            });
                        }else if (i == 5) {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue5)
                                    )).forEach((k, v) -> {

                                GraphValue graphValue = new GraphValue(k, String.format("%.3f", v));
                                graphValues.add(graphValue);

                            });
                        }else  {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue6)
                                    )).forEach((k, v) -> {

                                GraphValue graphValue = new GraphValue(k, String.format("%.3f", v));
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
        data.put("unit", MeasurementItemCode.M007.getDesc()+"(" + MeasurementItemCode.M007.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);

        log.info(seriescode.getDesc() + " 염분함유량 라인용(주기) 그래프 데이터 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));

    }


    private ResponseEntity<Map<String,Object>> line_S004(TreatmentCondition treatmentcondition,SeriesCode seriescode, HttpServletRequest request) {

        String currentuserid = request.getHeader("insert_id");
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 염분함유량 라인용(주기) 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")");

        List<PenetratedChlorideDto> ltdDatas = penetratedChlorideService.findBySeriesCode(seriescode);

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

        //======================T40701 노출환경에따른 주기별 염분함유량
        if(treatmentcondition.equals(TreatmentCondition.T40701)) {

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
                        .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue)
                        )).forEach((k, v) -> {

                    GraphValue graphValue = new GraphValue(k, String.format("%.3f", v / 6));
                    graphValues.add(graphValue);

                });

                graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                inspectdata.add(label);
                graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                graphDataColumns.add(inspectdata);

            }
        }
        else //======================T40702 사용재료에따른 주기별 염분함유량
            if(treatmentcondition.equals(TreatmentCondition.T40702)) {

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
                            .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                    , Collectors.averagingDouble(PenetratedChlorideDto::getValue)
                            )).forEach((k, v) -> {

                        GraphValue graphValue = new GraphValue(k, String.format("%.3f", v / 6 ));
                        graphValues.add(graphValue);

                    });

                    graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                    inspectdata.add(label);
                    graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                    graphDataColumns.add(inspectdata);

                }
            }
            else //======================T40703 초기 염분함유량에따른 주기별 염분함유량
                if(treatmentcondition.equals(TreatmentCondition.T40703)) {

                    //=== 2.1 범례 저장 (내륙환경/해안환경) ==========================================
                    List<GraphLegend> graphLegendsrow = new ArrayList<>();
                    ltdDatas.forEach(v -> {
                        GraphLegend graphLegend = new GraphLegend(1, String.format("%.2f",v.getSaltRate()));
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
                                .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                                .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                        , Collectors.averagingDouble(PenetratedChlorideDto::getValue)
                                )).forEach((k, v) -> {

                            GraphValue graphValue = new GraphValue(k, String.format("%.3f", v/6));
                            graphValues.add(graphValue);

                        });

                        graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                        inspectdata.add(label + '%');
                        graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                        graphDataColumns.add(inspectdata);

                    }
                }
                else //======================T40704 피복두계에 따른 주기별 염분함유량
                    if(treatmentcondition.equals(TreatmentCondition.T40704)) {


                        //=== 2.2 범례별(1cm,2cm,3cm,4cm) 평균값구해서 datasets 에 넣기=======================================
                        for (int i = 1; i < 7; i++) {

                            String label = "";

                            if (i==1) {label = "0-0.15cm";}
                            if (i==2) {label = "0.15-1.5cm";}
                            if (i==3) {label = "1.5-3cm";}
                            if (i==4) {label = "3-4.5cm";}
                            if (i==5) {label = "4.5-6cm";}
                            if (i==6) {label = "10cm";}

                            List<GraphValue> graphValues = new ArrayList<>();
                            List<String> inspectdata = new ArrayList<>();

                            if (i == 1) {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue1)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.3f", v));
                                    graphValues.add(graphValue);

                                });
                            }else if (i == 2) {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue2)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.3f", v));
                                    graphValues.add(graphValue);

                                });
                            }else if (i == 3) {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue3)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.3f", v));
                                    graphValues.add(graphValue);

                                });
                            }else if (i == 4) {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue4)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.3f", v));
                                    graphValues.add(graphValue);

                                });
                            }else if (i == 5) {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue5)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.3f", v));
                                    graphValues.add(graphValue);

                                });
                            }else  {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue6)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.3f", v));
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
        data.put("unit", MeasurementItemCode.M007.getDesc()+"(" + MeasurementItemCode.M007.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);

        log.info(seriescode.getDesc() + " 염분함유량 라인용(주기) 그래프 데이터 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));

    }



    private ResponseEntity<Map<String,Object>> line_S005(TreatmentCondition treatmentcondition,SeriesCode seriescode, HttpServletRequest request) {

        String currentuserid = request.getHeader("insert_id");
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 염분함유량 라인용(주기) 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")");

        List<PenetratedChlorideDto> ltdDatas = penetratedChlorideService.findBySeriesCode(seriescode);

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

        //======================T50701 노출환경에따른 주기별 염분함유량
        if(treatmentcondition.equals(TreatmentCondition.T50701)) {

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
                        .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue)
                        )).forEach((k, v) -> {

                    GraphValue graphValue = new GraphValue(k, String.format("%.3f", v / 6));
                    graphValues.add(graphValue);

                });

                graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                inspectdata.add(label);
                graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                graphDataColumns.add(inspectdata);

            }
        }
        else //======================T50702 혼화재료에따른 주기별 염분함유량
            if(treatmentcondition.equals(TreatmentCondition.T50702)) {

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
                            .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                    , Collectors.averagingDouble(PenetratedChlorideDto::getValue)
                            )).forEach((k, v) -> {

                        GraphValue graphValue = new GraphValue(k, String.format("%.3f", v / 6 ));
                        graphValues.add(graphValue);

                    });

                    graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                    inspectdata.add(label);
                    graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                    graphDataColumns.add(inspectdata);

                }
            }
            else //======================T50703 초기 염분함유량에따른 주기별 염분함유량
                if(treatmentcondition.equals(TreatmentCondition.T50703)) {

                    //=== 2.1 범례 저장 (내륙환경/해안환경) ==========================================
                    List<GraphLegend> graphLegendsrow = new ArrayList<>();
                    ltdDatas.forEach(v -> {
                        GraphLegend graphLegend = new GraphLegend(1, String.format("%.2f",v.getSaltRate()));
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
                                .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                                .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                        , Collectors.averagingDouble(PenetratedChlorideDto::getValue)
                                )).forEach((k, v) -> {

                            GraphValue graphValue = new GraphValue(k, String.format("%.3f", v/6));
                            graphValues.add(graphValue);

                        });

                        graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                        inspectdata.add(label + '%');
                        graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                        graphDataColumns.add(inspectdata);

                    }
                }
                else //======================T50704 피복두계에 따른 주기별 염분함유량
                    if(treatmentcondition.equals(TreatmentCondition.T50704)) {


                        //=== 2.2 범례별(1cm,2cm,3cm,4cm) 평균값구해서 datasets 에 넣기=======================================
                        for (int i = 1; i < 7; i++) {

                            String label = "";

                            if (i==1) {label = "0-0.15cm";}
                            if (i==2) {label = "0.15-1.5cm";}
                            if (i==3) {label = "1.5-3cm";}
                            if (i==4) {label = "3-4.5cm";}
                            if (i==5) {label = "4.5-6cm";}
                            if (i==6) {label = "10cm";}

                            List<GraphValue> graphValues = new ArrayList<>();
                            List<String> inspectdata = new ArrayList<>();

                            if (i == 1) {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue1)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.3f", v));
                                    graphValues.add(graphValue);

                                });
                            }else if (i == 2) {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue2)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.3f", v));
                                    graphValues.add(graphValue);

                                });
                            }else if (i == 3) {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue3)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.3f", v));
                                    graphValues.add(graphValue);

                                });
                            }else if (i == 4) {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue4)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.3f", v));
                                    graphValues.add(graphValue);

                                });
                            }else if (i == 5) {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue5)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.3f", v));
                                    graphValues.add(graphValue);

                                });
                            }else  {
                                ltdDatas.stream()
                                        .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue6)
                                        )).forEach((k, v) -> {

                                    GraphValue graphValue = new GraphValue(k, String.format("%.3f", v));
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
        data.put("unit", MeasurementItemCode.M007.getDesc()+"(" + MeasurementItemCode.M007.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);

        log.info(seriescode.getDesc() + " 염분함유량 라인용(주기) 그래프 데이터 조회 성공");


        return ResponseEntity.ok(res.dataSendSuccess(data));

    }

    private ResponseEntity<Map<String,Object>> line_S006(TreatmentCondition treatmentcondition,SeriesCode seriescode, HttpServletRequest request) {

        String currentuserid = request.getHeader("insert_id");
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 염분함유량 라인용(주기) 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")");

        List<PenetratedChlorideDto> ltdDatas = penetratedChlorideService.findBySeriesCode(seriescode);

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

        //======================T60701 노출환경 에따른 주기별 염분함유량
        if(treatmentcondition.equals(TreatmentCondition.T60701)) {

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
                        .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue)
                        )).forEach((k, v) -> {

                    GraphValue graphValue = new GraphValue(k, String.format("%.3f", v / 6 ));
                    graphValues.add(graphValue);

                });

                graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                inspectdata.add(label);
                graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                graphDataColumns.add(inspectdata);

            }
        }
        else //======================T60702 초기 염분함유량에따른 주기별 염분함유량
            if(treatmentcondition.equals(TreatmentCondition.T60702)) {

                //=== 2.1 범례 저장 (내륙환경/해안환경) ==========================================
                List<GraphLegend> graphLegendsrow = new ArrayList<>();
                ltdDatas.forEach(v -> {
                    GraphLegend graphLegend = new GraphLegend(1, String.format("%.2f",v.getSaltRate()));
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
                            .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                            .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                    , Collectors.averagingDouble(PenetratedChlorideDto::getValue)
                            )).forEach((k, v) -> {

                        GraphValue graphValue = new GraphValue(k, String.format("%.3f", v/6));
                        graphValues.add(graphValue);

                    });

                    graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                    inspectdata.add(label + '%');
                    graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                    graphDataColumns.add(inspectdata);

                }
            }
            else //======================T60703 콘크리트 두께 에 따른 주기별 염분함유량
                if(treatmentcondition.equals(TreatmentCondition.T60703)) {


                    //=== 2.2 범례별(1cm,2cm,3cm,4cm) 평균값구해서 datasets 에 넣기=======================================
                    for (int i = 1; i < 7; i++) {

                        String label = "";

                        if (i==1) {label = "0-0.15cm";}
                        if (i==2) {label = "0.15-1.5cm";}
                        if (i==3) {label = "1.5-3cm";}
                        if (i==4) {label = "3-4.5cm";}
                        if (i==5) {label = "4.5-6cm";}
                        if (i==6) {label = "10cm";}

                        List<GraphValue> graphValues = new ArrayList<>();
                        List<String> inspectdata = new ArrayList<>();

                        if (i == 1) {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue1)
                                    )).forEach((k, v) -> {

                                GraphValue graphValue = new GraphValue(k, String.format("%.3f", v));
                                graphValues.add(graphValue);

                            });
                        }else if (i == 2) {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue2)
                                    )).forEach((k, v) -> {

                                GraphValue graphValue = new GraphValue(k, String.format("%.3f", v));
                                graphValues.add(graphValue);

                            });
                        }else if (i == 3) {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue3)
                                    )).forEach((k, v) -> {

                                GraphValue graphValue = new GraphValue(k, String.format("%.3f", v));
                                graphValues.add(graphValue);

                            });
                        }else if (i == 4) {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue4)
                                    )).forEach((k, v) -> {

                                GraphValue graphValue = new GraphValue(k, String.format("%.3f", v));
                                graphValues.add(graphValue);

                            });
                        }else if (i == 5) {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue5)
                                    )).forEach((k, v) -> {

                                GraphValue graphValue = new GraphValue(k, String.format("%.3f", v));
                                graphValues.add(graphValue);

                            });
                        }else  {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue6)
                                    )).forEach((k, v) -> {

                                GraphValue graphValue = new GraphValue(k, String.format("%.3f", v));
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
        data.put("unit", MeasurementItemCode.M007.getDesc()+"(" + MeasurementItemCode.M007.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);

        log.info(seriescode.getDesc() + " 염분함유량 라인용(주기) 그래프 데이터 조회 성공");


        return ResponseEntity.ok(res.dataSendSuccess(data));

    }

    private ResponseEntity<Map<String,Object>> line_S007(TreatmentCondition treatmentcondition,SeriesCode seriescode, HttpServletRequest request) {

        String currentuserid = request.getHeader("insert_id");
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 염분함유량 라인용(주기) 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")");

        List<PenetratedChlorideDto> ltdDatas = penetratedChlorideService.findBySeriesCode(seriescode);

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

        //======================T70701 wc 에따른 주기별 염분함유량
        if(treatmentcondition.equals(TreatmentCondition.T70701)) {

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
                        .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue)
                        )).forEach((k, v) -> {

                    GraphValue graphValue = new GraphValue(k, String.format("%.3f", v / 6 ));
                    graphValues.add(graphValue);

                });

                graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                inspectdata.add(label);
                graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                graphDataColumns.add(inspectdata);

            }
        }
        else //======================T70702 초기 염분함유량에따른 주기별 염분함유량
            if(treatmentcondition.equals(TreatmentCondition.T70702)) {

                //=== 2.1 범례 저장 (내륙환경/해안환경) ==========================================
                List<GraphLegend> graphLegendsrow = new ArrayList<>();
                ltdDatas.forEach(v -> {
                    GraphLegend graphLegend = new GraphLegend(1, String.format("%.2f",v.getSaltRate()));
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
                            .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                            .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                    , Collectors.averagingDouble(PenetratedChlorideDto::getValue)
                            )).forEach((k, v) -> {

                        GraphValue graphValue = new GraphValue(k, String.format("%.3f", v/6));
                        graphValues.add(graphValue);

                    });

                    graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                    inspectdata.add(label + '%');
                    graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                    graphDataColumns.add(inspectdata);

                }
            }
            else //======================T70703 콘크리트 두께 에 따른 주기별 염분함유량
                if(treatmentcondition.equals(TreatmentCondition.T70703)) {


                    //=== 2.2 범례별(1cm,2cm,3cm,4cm) 평균값구해서 datasets 에 넣기=======================================
                    for (int i = 1; i < 7; i++) {

                        String label = "";

                        if (i==1) {label = "0-0.15cm";}
                        if (i==2) {label = "0.15-1.5cm";}
                        if (i==3) {label = "1.5-3cm";}
                        if (i==4) {label = "3-4.5cm";}
                        if (i==5) {label = "4.5-6cm";}
                        if (i==6) {label = "10cm";}

                        List<GraphValue> graphValues = new ArrayList<>();
                        List<String> inspectdata = new ArrayList<>();

                        if (i == 1) {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue1)
                                    )).forEach((k, v) -> {

                                GraphValue graphValue = new GraphValue(k, String.format("%.3f", v));
                                graphValues.add(graphValue);

                            });
                        }else if (i == 2) {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue2)
                                    )).forEach((k, v) -> {

                                GraphValue graphValue = new GraphValue(k, String.format("%.3f", v));
                                graphValues.add(graphValue);

                            });
                        }else if (i == 3) {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue3)
                                    )).forEach((k, v) -> {

                                GraphValue graphValue = new GraphValue(k, String.format("%.3f", v));
                                graphValues.add(graphValue);

                            });
                        }else if (i == 4) {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue4)
                                    )).forEach((k, v) -> {

                                GraphValue graphValue = new GraphValue(k, String.format("%.3f", v));
                                graphValues.add(graphValue);

                            });
                        }else if (i == 5) {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue5)
                                    )).forEach((k, v) -> {

                                GraphValue graphValue = new GraphValue(k, String.format("%.3f", v));
                                graphValues.add(graphValue);

                            });
                        }else  {
                            ltdDatas.stream()
                                    .collect(Collectors.groupingBy(PenetratedChlorideDto::getPeriod
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue6)
                                    )).forEach((k, v) -> {

                                GraphValue graphValue = new GraphValue(k, String.format("%.3f", v));
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
        data.put("unit", MeasurementItemCode.M007.getDesc()+"(" + MeasurementItemCode.M007.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);

        log.info(seriescode.getDesc() + " 염분함유량 라인용(주기) 그래프 데이터 조회 성공");


        return ResponseEntity.ok(res.dataSendSuccess(data));

    }


    private ResponseEntity<Map<String,Object>> bar_S001(String periodname, TreatmentCondition treatmentcondition, SeriesCode seriescode, HttpServletRequest request) {


        String currentuserid = request.getHeader("insert_id");
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 염분함유량 Bar 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")");

        List<PenetratedChlorideDto>  ltdDatasRaw = penetratedChlorideService.findBySeriesCode(seriescode);
        // 해당주기데이터만 취합
        List<PenetratedChlorideDto> ltdDatas = ltdDatasRaw.stream()
                .filter(v -> v.getPeriodName().equals(periodname)).collect(Collectors.toList());


        List<String> categories = new ArrayList<>();
        List<List<String>> graphDataColumns = new ArrayList<>();

        //=== 1. X라벨 저장 ========================================================
        categories.add("0-0.15cm");
        categories.add("0.15-1.5cm");
        categories.add("1.5-3cm");
        categories.add("3-4.5cm");
        categories.add("4.5-6cm");
        categories.add("10cm");


        //======================T10705 wc 에따른 염분함유량
        if(treatmentcondition.equals(TreatmentCondition.T10705)) {


            //=== 2. 범례 저장 (내륙환경/해안환경) =========================================
            List<GraphLegend> graphLegendsrow = new ArrayList<>();
            ltdDatas.forEach(v -> {
                GraphLegend graphLegend = new GraphLegend(1, v.getElement2());
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


                for (int j = 1; j < 7; j++) {

                    if (j == 1) {
                        ltdDatas.stream()
                                .filter(v -> v.getElement2().equals(label))
                                .collect(Collectors.groupingBy(PenetratedChlorideDto::getEnvironment
                                        , Collectors.averagingDouble(PenetratedChlorideDto::getValue1)
                                )).forEach((k, v) -> {
                            GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 2) {
                        ltdDatas.stream()
                                .filter(v -> v.getElement2().equals(label))
                                .collect(Collectors.groupingBy(PenetratedChlorideDto::getEnvironment
                                        , Collectors.averagingDouble(PenetratedChlorideDto::getValue2)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 3) {
                        ltdDatas.stream()
                                .filter(v -> v.getElement2().equals(label))
                                .collect(Collectors.groupingBy(PenetratedChlorideDto::getEnvironment
                                        , Collectors.averagingDouble(PenetratedChlorideDto::getValue3)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 4) {
                        ltdDatas.stream()
                                .filter(v -> v.getElement2().equals(label))
                                .collect(Collectors.groupingBy(PenetratedChlorideDto::getEnvironment
                                        , Collectors.averagingDouble(PenetratedChlorideDto::getValue4)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                            graphValues.add(graphValue);

                        });
                    }else if (j == 5) {
                        ltdDatas.stream()
                                .filter(v -> v.getElement2().equals(label))
                                .collect(Collectors.groupingBy(PenetratedChlorideDto::getEnvironment
                                        , Collectors.averagingDouble(PenetratedChlorideDto::getValue5)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                            graphValues.add(graphValue);

                        });
                    } else {
                        ltdDatas.stream()
                                .filter(v -> v.getElement2().equals(label))
                                .collect(Collectors.groupingBy(PenetratedChlorideDto::getEnvironment
                                        , Collectors.averagingDouble(PenetratedChlorideDto::getValue6)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
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
        else//======================T10706 방청제 에따른 염분함유량
            if(treatmentcondition.equals(TreatmentCondition.T10706)) {


                //=== 2. 범례 저장 (고로슬래그/플라이애쉬) =========================================
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

                //=== 3.범례별 평균값구해서 datasets 에 넣기=======================================

                //범례를 루프돌면서 실데이터를 graphDatas추가
                for(GraphLegend graphLegend: graphLegends){
                    String label = graphLegend.getName();
                    List<GraphValueString> graphValues = new ArrayList<>();
                    List<String> inspectdata = new ArrayList<>();


                    for (int j = 1; j < 7; j++) {

                        if (j == 1) {
                            ltdDatas.stream()
                                    .filter(v -> v.getElement1().equals(label))
                                    .collect(Collectors.groupingBy(PenetratedChlorideDto::getElement1
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue1)
                                    )).forEach((k, v) -> {
                                GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                                graphValues.add(graphValue);

                            });
                        } else if (j == 2) {
                            ltdDatas.stream()
                                    .filter(v -> v.getElement1().equals(label))
                                    .collect(Collectors.groupingBy(PenetratedChlorideDto::getElement1
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue2)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                                graphValues.add(graphValue);

                            });
                        } else if (j == 3) {
                            ltdDatas.stream()
                                    .filter(v -> v.getElement1().equals(label))
                                    .collect(Collectors.groupingBy(PenetratedChlorideDto::getElement1
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue3)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                                graphValues.add(graphValue);

                            });
                        } else if (j == 4) {
                            ltdDatas.stream()
                                    .filter(v -> v.getElement1().equals(label))
                                    .collect(Collectors.groupingBy(PenetratedChlorideDto::getElement1
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue4)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                                graphValues.add(graphValue);

                            });
                        }else if (j == 5) {
                            ltdDatas.stream()
                                    .filter(v -> v.getElement1().equals(label))
                                    .collect(Collectors.groupingBy(PenetratedChlorideDto::getElement1
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue5)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                                graphValues.add(graphValue);

                            });
                        }else {
                            ltdDatas.stream()
                                    .filter(v -> v.getElement1().equals(label))
                                    .collect(Collectors.groupingBy(PenetratedChlorideDto::getElement1
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue6)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
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
            else//======================T10707 연분함유량에따른 염분함유량
                if(treatmentcondition.equals(TreatmentCondition.T10707)) {


                    //=== 2. 범례 저장 (고로슬래그/플라이애쉬) =========================================
                    List<GraphLegend> graphLegendsrow = new ArrayList<>();
                    ltdDatas.forEach(v -> {
                        GraphLegend graphLegend = new GraphLegend(1, String.format("%.2f",v.getSaltRate()));
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


                        for (int j = 1; j < 7; j++) {

                            if (j == 1) {
                                ltdDatas.stream()
                                        .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue1)
                                        )).forEach((k, v) -> {
                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                                    graphValues.add(graphValue);

                                });
                            } else if (j == 2) {
                                ltdDatas.stream()
                                        .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue2)
                                        )).forEach((k, v) -> {

                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                                    graphValues.add(graphValue);

                                });
                            } else if (j == 3) {
                                ltdDatas.stream()
                                        .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue3)
                                        )).forEach((k, v) -> {

                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                                    graphValues.add(graphValue);

                                });
                            }else if (j == 4) {
                                ltdDatas.stream()
                                        .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue4)
                                        )).forEach((k, v) -> {

                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                                    graphValues.add(graphValue);

                                });
                            }else if (j == 5) {
                                ltdDatas.stream()
                                        .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue5)
                                        )).forEach((k, v) -> {

                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                                    graphValues.add(graphValue);

                                });
                            }else {
                                ltdDatas.stream()
                                        .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue6)
                                        )).forEach((k, v) -> {

                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
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
        data.put("unit", MeasurementItemCode.M007.getDesc()+"(" + MeasurementItemCode.M007.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
        data.put("categories",categories);

        log.info(seriescode.getDesc() + " 염분함유량 Bar 그래프 데이터 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    private ResponseEntity<Map<String,Object>> bar_S002(String periodname, TreatmentCondition treatmentcondition, SeriesCode seriescode, HttpServletRequest request) {


        String currentuserid = request.getHeader("insert_id");
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 염분함유량 Bar 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")");

        List<PenetratedChlorideDto>  ltdDatasRaw = penetratedChlorideService.findBySeriesCode(seriescode);
        // 해당주기데이터만 취합
        List<PenetratedChlorideDto> ltdDatas = ltdDatasRaw.stream()
                .filter(v -> v.getPeriodName().equals(periodname)).collect(Collectors.toList());


        List<String> categories = new ArrayList<>();
        List<List<String>> graphDataColumns = new ArrayList<>();

        //=== 1. X라벨 저장 ========================================================
        categories.add("0-0.15cm");
        categories.add("0.15-1.5cm");
        categories.add("1.5-3cm");
        categories.add("3-4.5cm");
        categories.add("4.5-6cm");
        categories.add("10cm");


        //======================T20705 wc 에따른 염분함유량
        if(treatmentcondition.equals(TreatmentCondition.T20704)) {


            //=== 2. 범례 저장 (내륙환경/해안환경) =========================================
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

            //=== 3.범례별 평균값구해서 datasets 에 넣기=======================================

            //범례를 루프돌면서 실데이터를 graphDatas추가
            for(GraphLegend graphLegend: graphLegends){
                String label = graphLegend.getName();
                List<GraphValueString> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                for (int j = 1; j < 7; j++) {

                    if (j == 1) {
                        ltdDatas.stream()
                                .filter(v -> v.getElement1().equals(label))
                                .collect(Collectors.groupingBy(PenetratedChlorideDto::getEnvironment
                                        , Collectors.averagingDouble(PenetratedChlorideDto::getValue1)
                                )).forEach((k, v) -> {
                            GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 2) {
                        ltdDatas.stream()
                                .filter(v -> v.getElement1().equals(label))
                                .collect(Collectors.groupingBy(PenetratedChlorideDto::getEnvironment
                                        , Collectors.averagingDouble(PenetratedChlorideDto::getValue2)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 3) {
                        ltdDatas.stream()
                                .filter(v -> v.getElement1().equals(label))
                                .collect(Collectors.groupingBy(PenetratedChlorideDto::getEnvironment
                                        , Collectors.averagingDouble(PenetratedChlorideDto::getValue3)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 4) {
                        ltdDatas.stream()
                                .filter(v -> v.getElement1().equals(label))
                                .collect(Collectors.groupingBy(PenetratedChlorideDto::getEnvironment
                                        , Collectors.averagingDouble(PenetratedChlorideDto::getValue4)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                            graphValues.add(graphValue);

                        });
                    }else if (j == 5) {
                        ltdDatas.stream()
                                .filter(v -> v.getElement1().equals(label))
                                .collect(Collectors.groupingBy(PenetratedChlorideDto::getEnvironment
                                        , Collectors.averagingDouble(PenetratedChlorideDto::getValue5)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                            graphValues.add(graphValue);

                        });
                    } else {
                        ltdDatas.stream()
                                .filter(v -> v.getElement1().equals(label))
                                .collect(Collectors.groupingBy(PenetratedChlorideDto::getEnvironment
                                        , Collectors.averagingDouble(PenetratedChlorideDto::getValue6)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
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

        else//======================T20705 연분함유량에따른 염분함유량
                if(treatmentcondition.equals(TreatmentCondition.T20705)) {


                    //=== 2. 범례 저장 (고로슬래그/플라이애쉬) =========================================
                    List<GraphLegend> graphLegendsrow = new ArrayList<>();
                    ltdDatas.forEach(v -> {
                        GraphLegend graphLegend = new GraphLegend(1, String.format("%.2f",v.getSaltRate()));
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


                        for (int j = 1; j < 7; j++) {

                            if (j == 1) {
                                ltdDatas.stream()
                                        .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue1)
                                        )).forEach((k, v) -> {
                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                                    graphValues.add(graphValue);

                                });
                            } else if (j == 2) {
                                ltdDatas.stream()
                                        .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue2)
                                        )).forEach((k, v) -> {

                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                                    graphValues.add(graphValue);

                                });
                            } else if (j == 3) {
                                ltdDatas.stream()
                                        .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue3)
                                        )).forEach((k, v) -> {

                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                                    graphValues.add(graphValue);

                                });
                            }else if (j == 4) {
                                ltdDatas.stream()
                                        .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue4)
                                        )).forEach((k, v) -> {

                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                                    graphValues.add(graphValue);

                                });
                            }else if (j == 5) {
                                ltdDatas.stream()
                                        .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue5)
                                        )).forEach((k, v) -> {

                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                                    graphValues.add(graphValue);

                                });
                            }else {
                                ltdDatas.stream()
                                        .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue6)
                                        )).forEach((k, v) -> {

                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
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
        data.put("unit", MeasurementItemCode.M007.getDesc()+"(" + MeasurementItemCode.M007.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
        data.put("categories",categories);

        log.info(seriescode.getDesc() + " 염분함유량 Bar 그래프 데이터 조회 성공");



        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    private ResponseEntity<Map<String,Object>> bar_S003(String periodname, TreatmentCondition treatmentcondition, SeriesCode seriescode, HttpServletRequest request) {


        String currentuserid = request.getHeader("insert_id");
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 염분함유량 Bar 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")");

        List<PenetratedChlorideDto>  ltdDatasRaw = penetratedChlorideService.findBySeriesCode(seriescode);
        // 해당주기데이터만 취합
        List<PenetratedChlorideDto> ltdDatas = ltdDatasRaw.stream()
                .filter(v -> v.getPeriodName().equals(periodname)).collect(Collectors.toList());


        List<String> categories = new ArrayList<>();
        List<List<String>> graphDataColumns = new ArrayList<>();

        //=== 1. X라벨 저장 ========================================================
        categories.add("0-0.15cm");
        categories.add("0.15-1.5cm");
        categories.add("1.5-3cm");
        categories.add("3-4.5cm");
        categories.add("4.5-6cm");
        categories.add("10cm");


        //======================T30705 wc 에따른 염분함유량
        if(treatmentcondition.equals(TreatmentCondition.T30704)) {


            //=== 2. 범례 저장 (내륙환경/해안환경) =========================================
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

            //=== 3.범례별 평균값구해서 datasets 에 넣기=======================================

            //범례를 루프돌면서 실데이터를 graphDatas추가
            for(GraphLegend graphLegend: graphLegends){
                String label = graphLegend.getName();
                List<GraphValueString> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                for (int j = 1; j < 7; j++) {

                    if (j == 1) {
                        ltdDatas.stream()
                                .filter(v -> v.getElement1().equals(label))
                                .collect(Collectors.groupingBy(PenetratedChlorideDto::getEnvironment
                                        , Collectors.averagingDouble(PenetratedChlorideDto::getValue1)
                                )).forEach((k, v) -> {
                            GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 2) {
                        ltdDatas.stream()
                                .filter(v -> v.getElement1().equals(label))
                                .collect(Collectors.groupingBy(PenetratedChlorideDto::getEnvironment
                                        , Collectors.averagingDouble(PenetratedChlorideDto::getValue2)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 3) {
                        ltdDatas.stream()
                                .filter(v -> v.getElement1().equals(label))
                                .collect(Collectors.groupingBy(PenetratedChlorideDto::getEnvironment
                                        , Collectors.averagingDouble(PenetratedChlorideDto::getValue3)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 4) {
                        ltdDatas.stream()
                                .filter(v -> v.getElement1().equals(label))
                                .collect(Collectors.groupingBy(PenetratedChlorideDto::getEnvironment
                                        , Collectors.averagingDouble(PenetratedChlorideDto::getValue4)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                            graphValues.add(graphValue);

                        });
                    }else if (j == 5) {
                        ltdDatas.stream()
                                .filter(v -> v.getElement1().equals(label))
                                .collect(Collectors.groupingBy(PenetratedChlorideDto::getEnvironment
                                        , Collectors.averagingDouble(PenetratedChlorideDto::getValue5)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                            graphValues.add(graphValue);

                        });
                    } else {
                        ltdDatas.stream()
                                .filter(v -> v.getElement1().equals(label))
                                .collect(Collectors.groupingBy(PenetratedChlorideDto::getEnvironment
                                        , Collectors.averagingDouble(PenetratedChlorideDto::getValue6)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
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

        else//======================T30705 연분함유량에따른 염분함유량
            if(treatmentcondition.equals(TreatmentCondition.T30705)) {


                //=== 2. 범례 저장 (고로슬래그/플라이애쉬) =========================================
                List<GraphLegend> graphLegendsrow = new ArrayList<>();
                ltdDatas.forEach(v -> {
                    GraphLegend graphLegend = new GraphLegend(1, String.format("%.2f",v.getSaltRate()));
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


                    for (int j = 1; j < 7; j++) {

                        if (j == 1) {
                            ltdDatas.stream()
                                    .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                                    .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue1)
                                    )).forEach((k, v) -> {
                                GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                                graphValues.add(graphValue);

                            });
                        } else if (j == 2) {
                            ltdDatas.stream()
                                    .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                                    .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue2)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                                graphValues.add(graphValue);

                            });
                        } else if (j == 3) {
                            ltdDatas.stream()
                                    .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                                    .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue3)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                                graphValues.add(graphValue);

                            });
                        }else if (j == 4) {
                            ltdDatas.stream()
                                    .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                                    .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue4)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                                graphValues.add(graphValue);

                            });
                        }else if (j == 5) {
                            ltdDatas.stream()
                                    .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                                    .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue5)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                                graphValues.add(graphValue);

                            });
                        }else {
                            ltdDatas.stream()
                                    .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                                    .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue6)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
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
        data.put("unit", MeasurementItemCode.M007.getDesc()+"(" + MeasurementItemCode.M007.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
        data.put("categories",categories);

        log.info(seriescode.getDesc() + " 염분함유량 Bar 그래프 데이터 조회 성공");



        return ResponseEntity.ok(res.dataSendSuccess(data));
    }


    private ResponseEntity<Map<String,Object>> bar_S004(String periodname, TreatmentCondition treatmentcondition, SeriesCode seriescode, HttpServletRequest request) {


        String currentuserid = request.getHeader("insert_id");
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 염분함유량 Bar 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")");

        List<PenetratedChlorideDto>  ltdDatasRaw = penetratedChlorideService.findBySeriesCode(seriescode);
        // 해당주기데이터만 취합
        List<PenetratedChlorideDto> ltdDatas = ltdDatasRaw.stream()
                .filter(v -> v.getPeriodName().equals(periodname)).collect(Collectors.toList());


        List<String> categories = new ArrayList<>();
        List<List<String>> graphDataColumns = new ArrayList<>();

        //=== 1. X라벨 저장 ========================================================
        categories.add("0-0.15cm");
        categories.add("0.15-1.5cm");
        categories.add("1.5-3cm");
        categories.add("3-4.5cm");
        categories.add("4.5-6cm");
        categories.add("10cm");


        //======================T40705 노출환경에따른 염분함유량
        if(treatmentcondition.equals(TreatmentCondition.T40705)) {


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


                for (int j = 1; j < 7; j++) {

                    if (j == 1) {
                        ltdDatas.stream()
                                .filter(v -> v.getEnvironment().equals(label))
                                .collect(Collectors.groupingBy(PenetratedChlorideDto::getEnvironment
                                        , Collectors.averagingDouble(PenetratedChlorideDto::getValue1)
                                )).forEach((k, v) -> {
                            GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 2) {
                        ltdDatas.stream()
                                .filter(v -> v.getEnvironment().equals(label))
                                .collect(Collectors.groupingBy(PenetratedChlorideDto::getEnvironment
                                        , Collectors.averagingDouble(PenetratedChlorideDto::getValue2)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 3) {
                        ltdDatas.stream()
                                .filter(v -> v.getEnvironment().equals(label))
                                .collect(Collectors.groupingBy(PenetratedChlorideDto::getEnvironment
                                        , Collectors.averagingDouble(PenetratedChlorideDto::getValue3)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 4) {
                        ltdDatas.stream()
                                .filter(v -> v.getEnvironment().equals(label))
                                .collect(Collectors.groupingBy(PenetratedChlorideDto::getEnvironment
                                        , Collectors.averagingDouble(PenetratedChlorideDto::getValue4)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                            graphValues.add(graphValue);

                        });
                    }else if (j == 5) {
                        ltdDatas.stream()
                                .filter(v -> v.getEnvironment().equals(label))
                                .collect(Collectors.groupingBy(PenetratedChlorideDto::getEnvironment
                                        , Collectors.averagingDouble(PenetratedChlorideDto::getValue5)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                            graphValues.add(graphValue);

                        });
                    } else {
                        ltdDatas.stream()
                                .filter(v -> v.getEnvironment().equals(label))
                                .collect(Collectors.groupingBy(PenetratedChlorideDto::getEnvironment
                                        , Collectors.averagingDouble(PenetratedChlorideDto::getValue6)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
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
        else//======================T40706 사용재료에따른 염분함유량
            if(treatmentcondition.equals(TreatmentCondition.T40706)) {


                //=== 2. 범례 저장 (고로슬래그/플라이애쉬) =========================================
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

                //=== 3.범례별 평균값구해서 datasets 에 넣기=======================================

                //범례를 루프돌면서 실데이터를 graphDatas추가
                for(GraphLegend graphLegend: graphLegends){
                    String label = graphLegend.getName();
                    List<GraphValueString> graphValues = new ArrayList<>();
                    List<String> inspectdata = new ArrayList<>();


                    for (int j = 1; j < 7; j++) {

                        if (j == 1) {
                            ltdDatas.stream()
                                    .filter(v -> v.getElement1().equals(label))
                                    .collect(Collectors.groupingBy(PenetratedChlorideDto::getElement1
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue1)
                                    )).forEach((k, v) -> {
                                GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                                graphValues.add(graphValue);

                            });
                        } else if (j == 2) {
                            ltdDatas.stream()
                                    .filter(v -> v.getElement1().equals(label))
                                    .collect(Collectors.groupingBy(PenetratedChlorideDto::getElement1
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue2)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                                graphValues.add(graphValue);

                            });
                        } else if (j == 3) {
                            ltdDatas.stream()
                                    .filter(v -> v.getElement1().equals(label))
                                    .collect(Collectors.groupingBy(PenetratedChlorideDto::getElement1
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue3)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                                graphValues.add(graphValue);

                            });
                        } else if (j == 4) {
                            ltdDatas.stream()
                                    .filter(v -> v.getElement1().equals(label))
                                    .collect(Collectors.groupingBy(PenetratedChlorideDto::getElement1
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue4)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                                graphValues.add(graphValue);

                            });
                        }else if (j == 5) {
                            ltdDatas.stream()
                                    .filter(v -> v.getElement1().equals(label))
                                    .collect(Collectors.groupingBy(PenetratedChlorideDto::getElement1
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue5)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                                graphValues.add(graphValue);

                            });
                        }else {
                            ltdDatas.stream()
                                    .filter(v -> v.getElement1().equals(label))
                                    .collect(Collectors.groupingBy(PenetratedChlorideDto::getElement1
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue6)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
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
            else//======================T40707 연분함유량에따른 염분함유량
                if(treatmentcondition.equals(TreatmentCondition.T40707)) {


                    //=== 2. 범례 저장 (고로슬래그/플라이애쉬) =========================================
                    List<GraphLegend> graphLegendsrow = new ArrayList<>();
                    ltdDatas.forEach(v -> {
                        GraphLegend graphLegend = new GraphLegend(1, String.format("%.2f",v.getSaltRate()));
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


                        for (int j = 1; j < 7; j++) {

                            if (j == 1) {
                                ltdDatas.stream()
                                        .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue1)
                                        )).forEach((k, v) -> {
                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                                    graphValues.add(graphValue);

                                });
                            } else if (j == 2) {
                                ltdDatas.stream()
                                        .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue2)
                                        )).forEach((k, v) -> {

                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                                    graphValues.add(graphValue);

                                });
                            } else if (j == 3) {
                                ltdDatas.stream()
                                        .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue3)
                                        )).forEach((k, v) -> {

                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                                    graphValues.add(graphValue);

                                });
                            }else if (j == 4) {
                                ltdDatas.stream()
                                        .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue4)
                                        )).forEach((k, v) -> {

                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                                    graphValues.add(graphValue);

                                });
                            }else if (j == 5) {
                                ltdDatas.stream()
                                        .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue5)
                                        )).forEach((k, v) -> {

                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                                    graphValues.add(graphValue);

                                });
                            }else {
                                ltdDatas.stream()
                                        .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue6)
                                        )).forEach((k, v) -> {

                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
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
        data.put("unit", MeasurementItemCode.M007.getDesc()+"(" + MeasurementItemCode.M007.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
        data.put("categories",categories);


        log.info(seriescode.getDesc() + " 염분함유량 Bar 그래프 데이터 조회 성공");



        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    private ResponseEntity<Map<String,Object>> bar_S005(String periodname, TreatmentCondition treatmentcondition, SeriesCode seriescode, HttpServletRequest request) {


        String currentuserid = request.getHeader("insert_id");
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 염분함유량 Bar 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")");

        List<PenetratedChlorideDto>  ltdDatasRaw = penetratedChlorideService.findBySeriesCode(seriescode);
        // 해당주기데이터만 취합
        List<PenetratedChlorideDto> ltdDatas = ltdDatasRaw.stream()
                .filter(v -> v.getPeriodName().equals(periodname)).collect(Collectors.toList());


        List<String> categories = new ArrayList<>();
        List<List<String>> graphDataColumns = new ArrayList<>();

        //=== 1. X라벨 저장 ========================================================
        categories.add("0-0.15cm");
        categories.add("0.15-1.5cm");
        categories.add("1.5-3cm");
        categories.add("3-4.5cm");
        categories.add("4.5-6cm");
        categories.add("10cm");


        //======================T50705 노출환경에따른 염분함유량
        if(treatmentcondition.equals(TreatmentCondition.T50705)) {


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


                for (int j = 1; j < 7; j++) {

                    if (j == 1) {
                        ltdDatas.stream()
                                .filter(v -> v.getEnvironment().equals(label))
                                .collect(Collectors.groupingBy(PenetratedChlorideDto::getEnvironment
                                        , Collectors.averagingDouble(PenetratedChlorideDto::getValue1)
                                )).forEach((k, v) -> {
                            GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 2) {
                        ltdDatas.stream()
                                .filter(v -> v.getEnvironment().equals(label))
                                .collect(Collectors.groupingBy(PenetratedChlorideDto::getEnvironment
                                        , Collectors.averagingDouble(PenetratedChlorideDto::getValue2)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 3) {
                        ltdDatas.stream()
                                .filter(v -> v.getEnvironment().equals(label))
                                .collect(Collectors.groupingBy(PenetratedChlorideDto::getEnvironment
                                        , Collectors.averagingDouble(PenetratedChlorideDto::getValue3)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 4) {
                        ltdDatas.stream()
                                .filter(v -> v.getEnvironment().equals(label))
                                .collect(Collectors.groupingBy(PenetratedChlorideDto::getEnvironment
                                        , Collectors.averagingDouble(PenetratedChlorideDto::getValue4)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                            graphValues.add(graphValue);

                        });
                    }else if (j == 5) {
                        ltdDatas.stream()
                                .filter(v -> v.getEnvironment().equals(label))
                                .collect(Collectors.groupingBy(PenetratedChlorideDto::getEnvironment
                                        , Collectors.averagingDouble(PenetratedChlorideDto::getValue5)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                            graphValues.add(graphValue);

                        });
                    } else {
                        ltdDatas.stream()
                                .filter(v -> v.getEnvironment().equals(label))
                                .collect(Collectors.groupingBy(PenetratedChlorideDto::getEnvironment
                                        , Collectors.averagingDouble(PenetratedChlorideDto::getValue6)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
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
        else//======================T50706 혼화재료에따른 염분함유량
            if(treatmentcondition.equals(TreatmentCondition.T50706)) {


                //=== 2. 범례 저장 (고로슬래그/플라이애쉬) =========================================
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

                //=== 3.범례별 평균값구해서 datasets 에 넣기=======================================

                //범례를 루프돌면서 실데이터를 graphDatas추가
                for(GraphLegend graphLegend: graphLegends){
                    String label = graphLegend.getName();
                    List<GraphValueString> graphValues = new ArrayList<>();
                    List<String> inspectdata = new ArrayList<>();


                    for (int j = 1; j < 7; j++) {

                        if (j == 1) {
                            ltdDatas.stream()
                                    .filter(v -> v.getElement1().equals(label))
                                    .collect(Collectors.groupingBy(PenetratedChlorideDto::getElement1
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue1)
                                    )).forEach((k, v) -> {
                                GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                                graphValues.add(graphValue);

                            });
                        } else if (j == 2) {
                            ltdDatas.stream()
                                    .filter(v -> v.getElement1().equals(label))
                                    .collect(Collectors.groupingBy(PenetratedChlorideDto::getElement1
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue2)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                                graphValues.add(graphValue);

                            });
                        } else if (j == 3) {
                            ltdDatas.stream()
                                    .filter(v -> v.getElement1().equals(label))
                                    .collect(Collectors.groupingBy(PenetratedChlorideDto::getElement1
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue3)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                                graphValues.add(graphValue);

                            });
                        } else if (j == 4) {
                            ltdDatas.stream()
                                    .filter(v -> v.getElement1().equals(label))
                                    .collect(Collectors.groupingBy(PenetratedChlorideDto::getElement1
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue4)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                                graphValues.add(graphValue);

                            });
                        }else if (j == 5) {
                            ltdDatas.stream()
                                    .filter(v -> v.getElement1().equals(label))
                                    .collect(Collectors.groupingBy(PenetratedChlorideDto::getElement1
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue5)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                                graphValues.add(graphValue);

                            });
                        }else {
                            ltdDatas.stream()
                                    .filter(v -> v.getElement1().equals(label))
                                    .collect(Collectors.groupingBy(PenetratedChlorideDto::getElement1
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue6)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
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
            else//======================T50707 연분함유량에따른 염분함유량
                if(treatmentcondition.equals(TreatmentCondition.T50707)) {


                    //=== 2. 범례 저장 (고로슬래그/플라이애쉬) =========================================
                    List<GraphLegend> graphLegendsrow = new ArrayList<>();
                    ltdDatas.forEach(v -> {
                        GraphLegend graphLegend = new GraphLegend(1, String.format("%.2f",v.getSaltRate()));
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


                        for (int j = 1; j < 7; j++) {

                            if (j == 1) {
                                ltdDatas.stream()
                                        .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue1)
                                        )).forEach((k, v) -> {
                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                                    graphValues.add(graphValue);

                                });
                            } else if (j == 2) {
                                ltdDatas.stream()
                                        .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue2)
                                        )).forEach((k, v) -> {

                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                                    graphValues.add(graphValue);

                                });
                            } else if (j == 3) {
                                ltdDatas.stream()
                                        .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue3)
                                        )).forEach((k, v) -> {

                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                                    graphValues.add(graphValue);

                                });
                            }else if (j == 4) {
                                ltdDatas.stream()
                                        .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue4)
                                        )).forEach((k, v) -> {

                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                                    graphValues.add(graphValue);

                                });
                            }else if (j == 5) {
                                ltdDatas.stream()
                                        .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue5)
                                        )).forEach((k, v) -> {

                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                                    graphValues.add(graphValue);

                                });
                            }else {
                                ltdDatas.stream()
                                        .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                                        .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                                , Collectors.averagingDouble(PenetratedChlorideDto::getValue6)
                                        )).forEach((k, v) -> {

                                    GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
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
        data.put("unit", MeasurementItemCode.M007.getDesc()+"(" + MeasurementItemCode.M007.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
        data.put("categories",categories);


        log.info(seriescode.getDesc() + " 염분함유량 Bar 그래프 데이터 조회 성공");



        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    private ResponseEntity<Map<String,Object>> bar_S006(String periodname, TreatmentCondition treatmentcondition, SeriesCode seriescode, HttpServletRequest request) {


        String currentuserid = request.getHeader("insert_id");
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 염분함유량 Bar 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")");

        List<PenetratedChlorideDto>  ltdDatasRaw = penetratedChlorideService.findBySeriesCode(seriescode);
        // 해당주기데이터만 취합
        List<PenetratedChlorideDto> ltdDatas = ltdDatasRaw.stream()
                .filter(v -> v.getPeriodName().equals(periodname)).collect(Collectors.toList());


        List<String> categories = new ArrayList<>();
        List<List<String>> graphDataColumns = new ArrayList<>();

        //=== 1. X라벨 저장 ========================================================
        categories.add("0-0.15cm");
        categories.add("0.15-1.5cm");
        categories.add("1.5-3cm");
        categories.add("3-4.5cm");
        categories.add("4.5-6cm");
        categories.add("10cm");


        //======================T60705 노출환경 에따른 염분함유량
        if(treatmentcondition.equals(TreatmentCondition.T60704)) {


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


                for (int j = 1; j < 7; j++) {

                    if (j == 1) {
                        ltdDatas.stream()
                                .filter(v -> v.getEnvironment().equals(label))
                                .collect(Collectors.groupingBy(PenetratedChlorideDto::getEnvironment
                                        , Collectors.averagingDouble(PenetratedChlorideDto::getValue1)
                                )).forEach((k, v) -> {
                            GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 2) {
                        ltdDatas.stream()
                                .filter(v -> v.getEnvironment().equals(label))
                                .collect(Collectors.groupingBy(PenetratedChlorideDto::getEnvironment
                                        , Collectors.averagingDouble(PenetratedChlorideDto::getValue2)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 3) {
                        ltdDatas.stream()
                                .filter(v -> v.getEnvironment().equals(label))
                                .collect(Collectors.groupingBy(PenetratedChlorideDto::getEnvironment
                                        , Collectors.averagingDouble(PenetratedChlorideDto::getValue3)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 4) {
                        ltdDatas.stream()
                                .filter(v -> v.getEnvironment().equals(label))
                                .collect(Collectors.groupingBy(PenetratedChlorideDto::getEnvironment
                                        , Collectors.averagingDouble(PenetratedChlorideDto::getValue4)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                            graphValues.add(graphValue);

                        });
                    }else if (j == 5) {
                        ltdDatas.stream()
                                .filter(v -> v.getEnvironment().equals(label))
                                .collect(Collectors.groupingBy(PenetratedChlorideDto::getEnvironment
                                        , Collectors.averagingDouble(PenetratedChlorideDto::getValue5)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                            graphValues.add(graphValue);

                        });
                    } else {
                        ltdDatas.stream()
                                .filter(v -> v.getEnvironment().equals(label))
                                .collect(Collectors.groupingBy(PenetratedChlorideDto::getEnvironment
                                        , Collectors.averagingDouble(PenetratedChlorideDto::getValue6)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
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

        else//======================T60705 연분함유량에따른 염분함유량
            if(treatmentcondition.equals(TreatmentCondition.T60705)) {


                //=== 2. 범례 저장 (고로슬래그/플라이애쉬) =========================================
                List<GraphLegend> graphLegendsrow = new ArrayList<>();
                ltdDatas.forEach(v -> {
                    GraphLegend graphLegend = new GraphLegend(1, String.format("%.2f",v.getSaltRate()));
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


                    for (int j = 1; j < 7; j++) {

                        if (j == 1) {
                            ltdDatas.stream()
                                    .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                                    .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue1)
                                    )).forEach((k, v) -> {
                                GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                                graphValues.add(graphValue);

                            });
                        } else if (j == 2) {
                            ltdDatas.stream()
                                    .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                                    .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue2)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                                graphValues.add(graphValue);

                            });
                        } else if (j == 3) {
                            ltdDatas.stream()
                                    .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                                    .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue3)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                                graphValues.add(graphValue);

                            });
                        }else if (j == 4) {
                            ltdDatas.stream()
                                    .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                                    .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue4)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                                graphValues.add(graphValue);

                            });
                        }else if (j == 5) {
                            ltdDatas.stream()
                                    .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                                    .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue5)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                                graphValues.add(graphValue);

                            });
                        }else {
                            ltdDatas.stream()
                                    .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                                    .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue6)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
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
        data.put("unit", MeasurementItemCode.M007.getDesc()+"(" + MeasurementItemCode.M007.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
        data.put("categories",categories);

        log.info(seriescode.getDesc() + " 염분함유량 Bar 그래프 데이터 조회 성공");


        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    private ResponseEntity<Map<String,Object>> bar_S007(String periodname, TreatmentCondition treatmentcondition, SeriesCode seriescode, HttpServletRequest request) {


        String currentuserid = request.getHeader("insert_id");
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 염분함유량 Bar 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")");

        List<PenetratedChlorideDto>  ltdDatasRaw = penetratedChlorideService.findBySeriesCode(seriescode);
        // 해당주기데이터만 취합
        List<PenetratedChlorideDto> ltdDatas = ltdDatasRaw.stream()
                .filter(v -> v.getPeriodName().equals(periodname)).collect(Collectors.toList());


        List<String> categories = new ArrayList<>();
        List<List<String>> graphDataColumns = new ArrayList<>();

        //=== 1. X라벨 저장 ========================================================
        categories.add("0-0.15cm");
        categories.add("0.15-1.5cm");
        categories.add("1.5-3cm");
        categories.add("3-4.5cm");
        categories.add("4.5-6cm");
        categories.add("10cm");


        //======================T70705 wc 에따른 염분함유량
        if(treatmentcondition.equals(TreatmentCondition.T70704)) {


            //=== 2. 범례 저장 (내륙환경/해안환경) =========================================
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

            //=== 3.범례별 평균값구해서 datasets 에 넣기=======================================

            //범례를 루프돌면서 실데이터를 graphDatas추가
            for(GraphLegend graphLegend: graphLegends){
                String label = graphLegend.getName();
                List<GraphValueString> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                for (int j = 1; j < 7; j++) {

                    if (j == 1) {
                        ltdDatas.stream()
                                .filter(v -> v.getElement1().equals(label))
                                .collect(Collectors.groupingBy(PenetratedChlorideDto::getEnvironment
                                        , Collectors.averagingDouble(PenetratedChlorideDto::getValue1)
                                )).forEach((k, v) -> {
                            GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 2) {
                        ltdDatas.stream()
                                .filter(v -> v.getElement1().equals(label))
                                .collect(Collectors.groupingBy(PenetratedChlorideDto::getEnvironment
                                        , Collectors.averagingDouble(PenetratedChlorideDto::getValue2)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 3) {
                        ltdDatas.stream()
                                .filter(v -> v.getElement1().equals(label))
                                .collect(Collectors.groupingBy(PenetratedChlorideDto::getEnvironment
                                        , Collectors.averagingDouble(PenetratedChlorideDto::getValue3)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                            graphValues.add(graphValue);

                        });
                    } else if (j == 4) {
                        ltdDatas.stream()
                                .filter(v -> v.getElement1().equals(label))
                                .collect(Collectors.groupingBy(PenetratedChlorideDto::getEnvironment
                                        , Collectors.averagingDouble(PenetratedChlorideDto::getValue4)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                            graphValues.add(graphValue);

                        });
                    }else if (j == 5) {
                        ltdDatas.stream()
                                .filter(v -> v.getElement1().equals(label))
                                .collect(Collectors.groupingBy(PenetratedChlorideDto::getEnvironment
                                        , Collectors.averagingDouble(PenetratedChlorideDto::getValue5)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                            graphValues.add(graphValue);

                        });
                    } else {
                        ltdDatas.stream()
                                .filter(v -> v.getElement1().equals(label))
                                .collect(Collectors.groupingBy(PenetratedChlorideDto::getEnvironment
                                        , Collectors.averagingDouble(PenetratedChlorideDto::getValue6)
                                )).forEach((k, v) -> {

                            GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
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

        else//======================T70705 연분함유량에따른 염분함유량
            if(treatmentcondition.equals(TreatmentCondition.T70705)) {


                //=== 2. 범례 저장 (고로슬래그/플라이애쉬) =========================================
                List<GraphLegend> graphLegendsrow = new ArrayList<>();
                ltdDatas.forEach(v -> {
                    GraphLegend graphLegend = new GraphLegend(1, String.format("%.2f",v.getSaltRate()));
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


                    for (int j = 1; j < 7; j++) {

                        if (j == 1) {
                            ltdDatas.stream()
                                    .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                                    .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue1)
                                    )).forEach((k, v) -> {
                                GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                                graphValues.add(graphValue);

                            });
                        } else if (j == 2) {
                            ltdDatas.stream()
                                    .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                                    .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue2)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                                graphValues.add(graphValue);

                            });
                        } else if (j == 3) {
                            ltdDatas.stream()
                                    .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                                    .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue3)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                                graphValues.add(graphValue);

                            });
                        }else if (j == 4) {
                            ltdDatas.stream()
                                    .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                                    .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue4)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                                graphValues.add(graphValue);

                            });
                        }else if (j == 5) {
                            ltdDatas.stream()
                                    .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                                    .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue5)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
                                graphValues.add(graphValue);

                            });
                        }else {
                            ltdDatas.stream()
                                    .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                                    .collect(Collectors.groupingBy(v -> Double.toString(v.getSaltRate())
                                            , Collectors.averagingDouble(PenetratedChlorideDto::getValue6)
                                    )).forEach((k, v) -> {

                                GraphValueString graphValue = new GraphValueString(k, String.format("%.3f", v));
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
        data.put("unit", MeasurementItemCode.M007.getDesc()+"(" + MeasurementItemCode.M007.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
        data.put("categories",categories);

        log.info(seriescode.getDesc() + " 염분함유량 Bar 그래프 데이터 조회 성공");


        return ResponseEntity.ok(res.dataSendSuccess(data));
    }


}
