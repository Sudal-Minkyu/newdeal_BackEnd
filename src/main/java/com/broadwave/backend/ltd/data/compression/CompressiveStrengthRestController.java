package com.broadwave.backend.ltd.data.compression;

import com.broadwave.backend.bscodes.SeriesCode;
import com.broadwave.backend.common.AjaxResponse;
import com.broadwave.backend.bscodes.MeasurementItemCode;
import com.broadwave.backend.bscodes.PeriodType;
import com.broadwave.backend.bscodes.SeriesCode;
import com.broadwave.backend.bscodes.TreatmentCondition;
import com.broadwave.backend.common.AjaxResponse;
import com.broadwave.backend.common.CommonUtils;
import com.broadwave.backend.common.ResponseErrorCode;
import com.broadwave.backend.ltd.dto.*;
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
 * Date : 2019-05-02
 * Time : 11:05
 * Remark :
 */
@Slf4j
@RestController
@RequestMapping("/api/ltd/data")
public class CompressiveStrengthRestController {

    List<HashMap<String, Object>> datasets = new ArrayList<>();


    private final CompressiveStrengthService compressiveStrengthService;

    @Autowired
    public CompressiveStrengthRestController(CompressiveStrengthService compressiveStrengthService) {
        this.compressiveStrengthService = compressiveStrengthService;
    }

    @PostMapping("cs/period")
    public ResponseEntity<Map<String,Object>> periodList(@RequestParam(value="seriescode", defaultValue="") SeriesCode seriescode,
                                    HttpServletRequest request){


        String currentuserid = request.getHeader("insert_id");
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        log.info("압축강도 주기 정보 조회(ex.28일,1년,10년..) / 조회자 :'" + currentuserid
                + "' 시리즈(코드) : '" + seriescode.getDesc() + "'(" + seriescode.getCode() + ")" );

        List<CompressiveStrengthDto> strengthDtos = compressiveStrengthService.findBySeriesCode(seriescode);

        List<GraphXLabel> graphyXLabelsrow = new ArrayList<>();
        strengthDtos.forEach(v ->{
            GraphXLabel graphyXLabel = new GraphXLabel(v.getPeriod(), v.getPeriodName());
            graphyXLabelsrow.add(graphyXLabel);

        });

        List<GraphXLabel> graphyXLabels = graphyXLabelsrow.stream()
                .distinct()
                .sorted(Comparator.comparing(GraphXLabel::getValue))
                .collect(Collectors.toList());

        data.put("datalist",graphyXLabels);
       

        log.info("압축강도 주기 정보 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }



    //고로슬래그/플라이애쉬 사용 시험체 압축강도 그래프
    @PostMapping("cs")
    public ResponseEntity<Map<String,Object>> graphdata(@RequestParam(value="seriescode", defaultValue="") SeriesCode seriescode,
                                    @RequestParam (value="periodtype", defaultValue="") PeriodType periodtype,
                                    @RequestParam (value="treatmentcondition", defaultValue="") TreatmentCondition treatmentcondition,
                                    @RequestParam (value="periodname", defaultValue="") String periodname,
                                    HttpServletRequest request){

        AjaxResponse res = new AjaxResponse();

        //seriescode:'S005',graphtype:'line',graphoption:'G01',periodname:"1년"
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


    //==========================================================================================================
    // 내륙환경시험체 라인그래프
    private ResponseEntity<Map<String,Object>> line_S002(TreatmentCondition treatmentcondition,SeriesCode seriescode ,HttpServletRequest request){

        String currentuserid = request.getHeader("insert_id");
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 라인용(주기) 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")" );

        List<CompressiveStrengthDto> ltdDatas = compressiveStrengthService.findBySeriesCode(seriescode);

        //=== 1. X라벨 저장 ========================================================

        List<GraphXLabel> graphyXLabelsrow = new ArrayList<>();
        ltdDatas.forEach(v ->{
            GraphXLabel graphyXLabel = new GraphXLabel(v.getPeriod(),v.getPeriodName());
            graphyXLabelsrow.add(graphyXLabel);

        });

        List<GraphXLabel> graphyXLabels = graphyXLabelsrow.stream()
                .distinct()
                .sorted(Comparator.comparing(GraphXLabel::getValue))
                .collect(Collectors.toList());


        // 2 범례및 데이터 생성 저장
        datasets.clear();
        List<List<String>> graphDataColumns = new ArrayList<>();

        // X라벨을 돌면서 graphDatas 에 추가
        List<String> xRows = new ArrayList<>();
        xRows.add("x");
        graphyXLabels.forEach(x -> {
            xRows.add(Integer.toString(x.getValue()));

        });
        graphDataColumns.add(xRows);

        //======================T20101 W/C 에 따른 주기별 압축강도
        if(treatmentcondition.equals(TreatmentCondition.T20101)) {

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
            for (int i = 0; i < graphLegends.size(); i++) {


                String label = graphLegends.get(i).getName();
                List<GraphValue> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                ltdDatas.stream()
                        .filter(v -> v.getElement1().equals(label))
                        .collect(Collectors.groupingBy(CompressiveStrengthDto::getPeriod
                                , Collectors.averagingDouble(CompressiveStrengthDto::getValue)
                        )).forEach((k, v) -> {

                    GraphValue graphValue = new GraphValue(k, String.format("%.1f", v));
                    graphValues.add(graphValue);

                });

                graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                inspectdata.add(label);
                graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                graphDataColumns.add(inspectdata);

            }
        }//======================T20102 초기염붐함유량 따른 주기별 압축강도
        else if(treatmentcondition.equals(TreatmentCondition.T20102)) {
            //=== 2.1 범례 저장 (고로슬래그,플라이에쉬) ==========================================
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
            for (int i = 0; i < graphLegends.size(); i++) {


                String label = graphLegends.get(i).getName();
                List<GraphValue> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                ltdDatas.stream()
                        .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                        .collect(Collectors.groupingBy(CompressiveStrengthDto::getPeriod
                                , Collectors.averagingDouble(CompressiveStrengthDto::getValue)
                        )).forEach((k, v) -> {

                    GraphValue graphValue = new GraphValue(k, String.format("%.1f", v));
                    graphValues.add(graphValue);

                });

                graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                inspectdata.add(label+'%');
                graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                graphDataColumns.add(inspectdata);

            }
        }



        //없는옵션이면
        else{
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E008.getCode(),ResponseErrorCode.E008.getDesc(),null,null));
        }



        data.put("title",treatmentcondition.getGraphTitle());
        data.put("unit", MeasurementItemCode.M001.getDesc()+"(" + MeasurementItemCode.M001.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
       

        log.info(seriescode.getDesc() + " 압축강도 라인용(주기) 그래프 데이터 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    //==========================================================================================================
    //시리즈1 해사  라인그래프
    private ResponseEntity line_S001(TreatmentCondition treatmentcondition,SeriesCode seriescode ,HttpServletRequest request){


         
String currentuserid = request.getHeader("insert_id");
AjaxResponse res = new AjaxResponse();
HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 라인용(주기) 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")" );

        List<CompressiveStrengthDto> ltdDatas = compressiveStrengthService.findBySeriesCode(seriescode);

        //=== 1. X라벨 저장 ========================================================

        List<GraphXLabel> graphyXLabelsrow = new ArrayList<>();
        ltdDatas.forEach(v ->{
            GraphXLabel graphyXLabel = new GraphXLabel(v.getPeriod(),v.getPeriodName());
            graphyXLabelsrow.add(graphyXLabel);

        });

        List<GraphXLabel> graphyXLabels = graphyXLabelsrow.stream()
                .distinct()
                .sorted(Comparator.comparing(GraphXLabel::getValue))
                .collect(Collectors.toList());


        // 2 범례및 데이터 생성 저장
        datasets.clear();
        List<List<String>> graphDataColumns = new ArrayList<>();

        // X라벨을 돌면서 graphDatas 에 추가
        List<String> xRows = new ArrayList<>();
        xRows.add("x");
        graphyXLabels.forEach(x -> {
            xRows.add(Integer.toString(x.getValue()));

        });
        graphDataColumns.add(xRows);

        //======================T10101 노출환경에따른 주기별 압축강도
        if(treatmentcondition.equals(TreatmentCondition.T10101)) {

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
            for (int i = 0; i < graphLegends.size(); i++) {


                String label = graphLegends.get(i).getName();
                List<GraphValue> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                ltdDatas.stream()
                        .filter(v -> v.getEnvironment().equals(label))
                        .collect(Collectors.groupingBy(CompressiveStrengthDto::getPeriod
                                , Collectors.averagingDouble(CompressiveStrengthDto::getValue)
                        )).forEach((k, v) -> {

                    GraphValue graphValue = new GraphValue(k, String.format("%.1f", v));
                    graphValues.add(graphValue);

                });

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
        data.put("unit", MeasurementItemCode.M001.getDesc()+"(" + MeasurementItemCode.M001.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
       

        log.info(seriescode.getDesc() + " 압축강도 라인용(주기) 그래프 데이터 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }



    //==========================================================================================================
    //시리즈5 고로슬래그/플라이애쉬 라인그래프
    private ResponseEntity line_S005(TreatmentCondition treatmentcondition,SeriesCode seriescode ,HttpServletRequest request){


         
String currentuserid = request.getHeader("insert_id");
AjaxResponse res = new AjaxResponse();
HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() +  " 압축강도 라인용(주기) 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")" );

        List<CompressiveStrengthDto> ltdDatas = compressiveStrengthService.findBySeriesCode(seriescode);

        //=== 1. X라벨 저장 ========================================================

        List<GraphXLabel> graphyXLabelsrow = new ArrayList<>();
        ltdDatas.forEach(v ->{
            GraphXLabel graphyXLabel = new GraphXLabel(v.getPeriod(),v.getPeriodName());
            graphyXLabelsrow.add(graphyXLabel);

        });

        List<GraphXLabel> graphyXLabels = graphyXLabelsrow.stream()
                .distinct()
                .sorted(Comparator.comparing(GraphXLabel::getValue))
                .collect(Collectors.toList());


        // 2 범례및 데이터 생성 저장
        datasets.clear();
        List<List<String>> graphDataColumns = new ArrayList<>();

        // X라벨을 돌면서 graphDatas 에 추가
        List<String> xRows = new ArrayList<>();
        xRows.add("x");
        graphyXLabels.forEach(x -> {
            xRows.add(Integer.toString(x.getValue()));

        });
        graphDataColumns.add(xRows);

        //======================T0101 노출환경에따른 주기별 압축강도
        if(treatmentcondition.equals(TreatmentCondition.T50101)) {

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
            for (int i = 0; i < graphLegends.size(); i++) {


                String label = graphLegends.get(i).getName();
                List<GraphValue> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                ltdDatas.stream()
                        .filter(v -> v.getEnvironment().equals(label))
                        .collect(Collectors.groupingBy(CompressiveStrengthDto::getPeriod
                                , Collectors.averagingDouble(CompressiveStrengthDto::getValue)
                        )).forEach((k, v) -> {

                    GraphValue graphValue = new GraphValue(k, String.format("%.1f", v));
                    graphValues.add(graphValue);

                });

                graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                inspectdata.add(label);
                graphValues.forEach(v -> {
                    inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue()));
                });

                graphDataColumns.add(inspectdata);

            }
        }

        //======================T0102 혼화재료에 따른 주기별 압축강도
        else if(treatmentcondition.equals(TreatmentCondition.T50102)) {
            //=== 2.1 범례 저장 (고로슬래그,플라이에쉬) ==========================================
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
            for (int i = 0; i < graphLegends.size(); i++) {


                String label = graphLegends.get(i).getName();
                List<GraphValue> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                ltdDatas.stream()
                        .filter(v -> v.getElement1().equals(label))
                        .collect(Collectors.groupingBy(CompressiveStrengthDto::getPeriod
                                , Collectors.averagingDouble(CompressiveStrengthDto::getValue)
                        )).forEach((k, v) -> {

                    GraphValue graphValue = new GraphValue(k, String.format("%.1f", v));
                    graphValues.add(graphValue);

                });

                graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                inspectdata.add(label);
                graphValues.forEach(v -> {
                    inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue()));
                });

                graphDataColumns.add(inspectdata);

            }
        }

        //======================T0103 초기염붐함유량 따른 주기별 압축강도
        else if(treatmentcondition.equals(TreatmentCondition.T50103)) {
            //=== 2.1 범례 저장 (고로슬래그,플라이에쉬) ==========================================
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
            for (int i = 0; i < graphLegends.size(); i++) {


                String label = graphLegends.get(i).getName();
                List<GraphValue> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                ltdDatas.stream()
                        .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                        .collect(Collectors.groupingBy(CompressiveStrengthDto::getPeriod
                                , Collectors.averagingDouble(CompressiveStrengthDto::getValue)
                        )).forEach((k, v) -> {

                    GraphValue graphValue = new GraphValue(k, String.format("%.2f", v));
                    graphValues.add(graphValue);

                });

                graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                inspectdata.add(label+'%');
                graphValues.forEach(v -> {
                    inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue()));
                });

                graphDataColumns.add(inspectdata);

            }
        }
        //없는옵션이면
        else{
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E008.getCode(),ResponseErrorCode.E008.getDesc(),null,null));
        }



        data.put("title",treatmentcondition.getGraphTitle());
        data.put("unit", MeasurementItemCode.M001.getDesc()+"(" + MeasurementItemCode.M001.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
       

        log.info(seriescode.getDesc() + " 압축강도 라인용(주기) 그래프 데이터 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }


    //==========================================================================================================
    //시리즈5 고로슬래그/플라이애쉬 Bar 그래프
    private ResponseEntity bar_S005(String periodname,TreatmentCondition treatmentcondition,SeriesCode seriescode ,HttpServletRequest request){



         
String currentuserid = request.getHeader("insert_id");
AjaxResponse res = new AjaxResponse();
HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + "압축강도 Bar 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid + "'");

        List<CompressiveStrengthDto> ltdDatasRaw = compressiveStrengthService.findBySeriesCode(seriescode);
        // 해당주기데이터만 취합
        List<CompressiveStrengthDto> ltdDatas = ltdDatasRaw.stream()
                .filter(v -> v.getPeriodName().equals(periodname)).collect(Collectors.toList());


        List<String> categories = new ArrayList<>();
        datasets.clear();
        List<List<String>> graphDataColumns = new ArrayList<>();

        //======================T0104 노출환경에따른 압축강도
        if(treatmentcondition.equals(TreatmentCondition.T50104)) {
            //=== 1. X라벨 저장 ========================================================

            List<GraphXLabel> graphyXLabelsrow = new ArrayList<>();

            ltdDatas.forEach(v -> {
                GraphXLabel graphyXLabel = new GraphXLabel(1, v.getEnvironment());
                graphyXLabelsrow.add(graphyXLabel);

            });

            List<GraphXLabel> graphyXLabels = graphyXLabelsrow.stream()
                    .distinct()
                    .sorted(Comparator.comparing(GraphXLabel::getValue))
                    .collect(Collectors.toList());

            graphyXLabels.sort(Comparator.comparing(GraphXLabel::getName));
            graphyXLabels.forEach(v -> categories.add(v.getName()));


            //=== 2. 범례 저장 (고로슬래쉬/플라이애쉬) ==========================================
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
            for (int i = 0; i < graphLegends.size(); i++) {


                String label = graphLegends.get(i).getName();
                List<GraphValueString> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                ltdDatas.stream()
                        .filter(v -> v.getElement1().equals(label))
                        .collect(Collectors.groupingBy(CompressiveStrengthDto::getEnvironment
                                , Collectors.averagingDouble(CompressiveStrengthDto::getValue)
                        )).forEach((k, v) -> {

                    GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                    graphValues.add(graphValue);

                });

                graphValues.sort(Comparator.comparing(v -> v.getIndex()));

                inspectdata.add(label);
                graphValues.forEach(v -> inspectdata.add(v.getValue()));

                graphDataColumns.add(inspectdata);

            }
        }else //======================T0105 초기염분함유량 에따른 압축강도
            if(treatmentcondition.equals(TreatmentCondition.T50105)) {
                //=== 1. X라벨 저장 ========================================================

                List<GraphXLabel> graphyXLabelsrow = new ArrayList<>();

                ltdDatas.forEach(v -> {
                    GraphXLabel graphyXLabel = new GraphXLabel(1, Double.toString(v.getSaltRate()));
                    graphyXLabelsrow.add(graphyXLabel);

                });

                List<GraphXLabel> graphyXLabels = graphyXLabelsrow.stream()
                        .distinct()
                        .sorted(Comparator.comparing(GraphXLabel::getValue))
                        .collect(Collectors.toList());

                graphyXLabels.sort(Comparator.comparing(GraphXLabel::getName));
                graphyXLabels.forEach(v -> {
                    categories.add(v.getName() + '%');
                });


                //=== 2. 범례 저장 (고로슬래쉬/플라이애쉬) ==========================================
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
                for (int i = 0; i < graphLegends.size(); i++) {


                    String label = graphLegends.get(i).getName();
                    List<GraphValueString> graphValues = new ArrayList<>();
                    List<String> inspectdata = new ArrayList<>();


                    ltdDatas.stream()
                            .filter(v -> v.getElement1().equals(label))
                            .collect(Collectors.groupingBy(CompressiveStrengthDto::getSaltRate
                                    , Collectors.averagingDouble(CompressiveStrengthDto::getValue)
                            )).forEach((k, v) -> {

                        GraphValueString graphValue = new GraphValueString(Double.toString(k), String.format("%.2f", v));
                        graphValues.add(graphValue);

                    });

                    graphValues.sort(Comparator.comparing(GraphValueString::getIndex));

                    inspectdata.add(label);
                    graphValues.forEach(v -> inspectdata.add(v.getValue()));

                    graphDataColumns.add(inspectdata);

                }
            }

        //없는옵션이면
        else{
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E008.getCode(),ResponseErrorCode.E008.getDesc(),null,null));
        }

        data.put("title",periodname+"차 " + treatmentcondition.getGraphTitle());
        data.put("unit", MeasurementItemCode.M001.getDesc()+"(" + MeasurementItemCode.M001.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
        data.put("categories",categories);
       

        log.info(seriescode.getDesc() + "압축강도 Bar 그래프 데이터 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    //==========================================================================================================
    //내률환경기준시험 Bar 그래프
    private ResponseEntity bar_S002(String periodname,TreatmentCondition treatmentcondition,SeriesCode seriescode ,HttpServletRequest request){



         
String currentuserid = request.getHeader("insert_id");
AjaxResponse res = new AjaxResponse();
HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + "압축강도 Bar 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid + "'");

        List<CompressiveStrengthDto> ltdDatasRaw = compressiveStrengthService.findBySeriesCode(seriescode);
        // 해당주기데이터만 취합
        List<CompressiveStrengthDto> ltdDatas = ltdDatasRaw.stream()
                .filter(v -> v.getPeriodName().equals(periodname)).collect(Collectors.toList());


        List<String> categories = new ArrayList<>();
        datasets.clear();
        List<List<String>> graphDataColumns = new ArrayList<>();

        //======================T20103 W/C 에따른 압축강도
            if(treatmentcondition.equals(TreatmentCondition.T20103)) {
                //=== 1. X라벨 저장 ========================================================

                List<GraphXLabel> graphyXLabelsrow = new ArrayList<>();

                ltdDatas.forEach(v -> {
                    GraphXLabel graphyXLabel = new GraphXLabel(1, v.getElement1() );
                    graphyXLabelsrow.add(graphyXLabel);

                });

                List<GraphXLabel> graphyXLabels = graphyXLabelsrow.stream()
                        .distinct()
                        .sorted(Comparator.comparing(GraphXLabel::getValue))
                        .collect(Collectors.toList());

                graphyXLabels.sort(Comparator.comparing(v -> v.getName()));
                graphyXLabels.forEach(v -> {
                    categories.add(v.getName());
                });


                //=== 2. 범례 저장 (0.04%,0.16%) ==========================================
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
                for (int i = 0; i < graphLegends.size(); i++) {


                    String label = graphLegends.get(i).getName();
                    List<GraphValueString> graphValues = new ArrayList<>();
                    List<String> inspectdata = new ArrayList<>();


                    ltdDatas.stream()
                            .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                            .collect(Collectors.groupingBy(v -> v.getElement1()
                                    , Collectors.averagingDouble(value -> value.getValue())
                            )).forEach((k, v) -> {

                        GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                        graphValues.add(graphValue);

                    });

                    graphValues.sort(Comparator.comparing(v -> v.getIndex()));

                    inspectdata.add(label+'%');
                    graphValues.forEach(v -> {
                        inspectdata.add(v.getValue());
                    });

                    graphDataColumns.add(inspectdata);

                }
            }

            //없는옵션이면
            else{
                return ResponseEntity.ok(res.fail(ResponseErrorCode.E008.getCode(),ResponseErrorCode.E008.getDesc(),null,null));
            }

        data.put("title",periodname+"차 " + treatmentcondition.getGraphTitle());
        data.put("unit", MeasurementItemCode.M001.getDesc()+"(" + MeasurementItemCode.M001.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
        data.put("categories",categories);
       

        log.info(seriescode.getDesc() + "압축강도 Bar 그래프 데이터 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    //==========================================================================================================
    //시리즈4 부순골재/재생모래 사용 시험체 라인그래프
    private ResponseEntity line_S004(TreatmentCondition treatmentcondition,SeriesCode seriescode ,HttpServletRequest request){


         
String currentuserid = request.getHeader("insert_id");
AjaxResponse res = new AjaxResponse();
HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() +  " 압축강도 라인용(주기) 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")" );

        List<CompressiveStrengthDto> ltdDatas = compressiveStrengthService.findBySeriesCode(seriescode);

        //=== 1. X라벨 저장 ========================================================

        List<GraphXLabel> graphyXLabelsrow = new ArrayList<>();
        ltdDatas.forEach(v ->{
            GraphXLabel graphyXLabel = new GraphXLabel(v.getPeriod(),v.getPeriodName());
            graphyXLabelsrow.add(graphyXLabel);

        });

        List<GraphXLabel> graphyXLabels = graphyXLabelsrow.stream()
                .distinct()
                .sorted(Comparator.comparing(GraphXLabel::getValue))
                .collect(Collectors.toList());


        // 2 범례및 데이터 생성 저장

        datasets.clear();
        List<List<String>> graphDataColumns = new ArrayList<>();

        // X라벨을 돌면서 graphDatas 에 추가
        List<String> xRows = new ArrayList<>();
        xRows.add("x");
        graphyXLabels.forEach(x -> xRows.add(Integer.toString(x.getValue())));
        graphDataColumns.add(xRows);

        //======================T40101 노출환경에따른 주기별 압축강도
        if(treatmentcondition.equals(TreatmentCondition.T40101)) {

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
            for (int i = 0; i < graphLegends.size(); i++) {


                String label = graphLegends.get(i).getName();
                List<GraphValue> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                ltdDatas.stream()
                        .filter(v -> v.getEnvironment().equals(label))
                        .collect(Collectors.groupingBy(CompressiveStrengthDto::getPeriod
                                , Collectors.averagingDouble(CompressiveStrengthDto::getValue)
                        )).forEach((k, v) -> {

                    GraphValue graphValue = new GraphValue(k, String.format("%.1f", v));
                    graphValues.add(graphValue);

                });

                graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                inspectdata.add(label);
                graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                graphDataColumns.add(inspectdata);

            }
        }

        //======================T40102 혼화재료에 따른 주기별 압축강도
        else if(treatmentcondition.equals(TreatmentCondition.T40102)) {
            //=== 2.1 범례 저장 (고로슬래그,플라이에쉬) ==========================================
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
            for (int i = 0; i < graphLegends.size(); i++) {


                String label = graphLegends.get(i).getName();
                List<GraphValue> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                ltdDatas.stream()
                        .filter(v -> v.getElement1().equals(label))
                        .collect(Collectors.groupingBy(CompressiveStrengthDto::getPeriod
                                , Collectors.averagingDouble(CompressiveStrengthDto::getValue)
                        )).forEach((k, v) -> {

                    GraphValue graphValue = new GraphValue(k, String.format("%.1f", v));
                    graphValues.add(graphValue);

                });

                graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                inspectdata.add(label);
                graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                graphDataColumns.add(inspectdata);

            }
        }

        //======================T40103 초기염붐함유량 따른 주기별 압축강도
        else if(treatmentcondition.equals(TreatmentCondition.T40103)) {
            //=== 2.1 범례 저장 (고로슬래그,플라이에쉬) ==========================================
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
            for (int i = 0; i < graphLegends.size(); i++) {


                String label = graphLegends.get(i).getName();
                List<GraphValue> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                ltdDatas.stream()
                        .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                        .collect(Collectors.groupingBy(CompressiveStrengthDto::getPeriod
                                , Collectors.averagingDouble(CompressiveStrengthDto::getValue)
                        )).forEach((k, v) -> {

                    GraphValue graphValue = new GraphValue(k, String.format("%.1f", v));
                    graphValues.add(graphValue);

                });

                graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                inspectdata.add(label+'%');
                graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                graphDataColumns.add(inspectdata);

            }
        }
        //없는옵션이면
        else{
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E008.getCode(),ResponseErrorCode.E008.getDesc(),null,null));
        }



        data.put("title",treatmentcondition.getGraphTitle());
        data.put("unit", MeasurementItemCode.M001.getDesc()+"(" + MeasurementItemCode.M001.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
       

        log.info(seriescode.getDesc() + " 압축강도 라인용(주기) 그래프 데이터 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    //==========================================================================================================
    //시리즈4 부순골재/재생모래 사용 Bar 그래프
    private ResponseEntity bar_S004(String periodname,TreatmentCondition treatmentcondition,SeriesCode seriescode ,HttpServletRequest request){



         
String currentuserid = request.getHeader("insert_id");
AjaxResponse res = new AjaxResponse();
HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + "압축강도 Bar 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid + "'");

        List<CompressiveStrengthDto> ltdDatasRaw = compressiveStrengthService.findBySeriesCode(seriescode);
        // 해당주기데이터만 취합
        List<CompressiveStrengthDto> ltdDatas = ltdDatasRaw.stream()
                .filter(v -> v.getPeriodName().equals(periodname)).collect(Collectors.toList());


        List<String> categories = new ArrayList<>();

        datasets.clear();
        List<List<String>> graphDataColumns = new ArrayList<>();

        //======================T40104 노출환경에따른 압축강도
        if(treatmentcondition.equals(TreatmentCondition.T40104)) {
            //=== 1. X라벨 저장 ========================================================

            List<GraphXLabel> graphyXLabelsrow = new ArrayList<>();

            ltdDatas.forEach(v -> {
                GraphXLabel graphyXLabel = new GraphXLabel(1, v.getEnvironment());
                graphyXLabelsrow.add(graphyXLabel);

            });

            List<GraphXLabel> graphyXLabels = graphyXLabelsrow.stream()
                    .distinct()
                    .sorted(Comparator.comparing(GraphXLabel::getValue))
                    .collect(Collectors.toList());

            graphyXLabels.sort(Comparator.comparing(v -> v.getName()));
            graphyXLabels.forEach(v -> {
                categories.add(v.getName());
            });


            //=== 2. 범례 저장 (고로슬래쉬/플라이애쉬) ==========================================
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
            for (int i = 0; i < graphLegends.size(); i++) {


                String label = graphLegends.get(i).getName();
                List<GraphValueString> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                ltdDatas.stream()
                        .filter(v -> v.getElement1().equals(label))
                        .collect(Collectors.groupingBy(v -> v.getEnvironment()
                                , Collectors.averagingDouble(value -> value.getValue())
                        )).forEach((k, v) -> {

                    GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                    graphValues.add(graphValue);

                });

                graphValues.sort(Comparator.comparing(v -> v.getIndex()));

                inspectdata.add(label);
                graphValues.forEach(v -> {
                    inspectdata.add(v.getValue());
                });

                graphDataColumns.add(inspectdata);

            }
        }else //======================T40105 초기염분함유량 에따른 압축강도
            if(treatmentcondition.equals(TreatmentCondition.T40105)) {
                //=== 1. X라벨 저장 ========================================================

                List<GraphXLabel> graphyXLabelsrow = new ArrayList<>();

                ltdDatas.forEach(v -> {
                    GraphXLabel graphyXLabel = new GraphXLabel(1, Double.toString(v.getSaltRate()));
                    graphyXLabelsrow.add(graphyXLabel);

                });

                List<GraphXLabel> graphyXLabels = graphyXLabelsrow.stream()
                        .distinct()
                        .sorted(Comparator.comparing(GraphXLabel::getValue))
                        .collect(Collectors.toList());

                graphyXLabels.sort(Comparator.comparing(v -> v.getName()));
                graphyXLabels.forEach(v -> {
                    categories.add(v.getName() + '%');
                });


                //=== 2. 범례 저장 (고로슬래쉬/플라이애쉬) ==========================================
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
                for (int i = 0; i < graphLegends.size(); i++) {


                    String label = graphLegends.get(i).getName();
                    List<GraphValueString> graphValues = new ArrayList<>();
                    List<String> inspectdata = new ArrayList<>();


                    ltdDatas.stream()
                            .filter(v -> v.getElement1().equals(label))
                            .collect(Collectors.groupingBy(v -> v.getSaltRate()
                                    , Collectors.averagingDouble(value -> value.getValue())
                            )).forEach((k, v) -> {

                        GraphValueString graphValue = new GraphValueString(Double.toString(k), String.format("%.2f", v));
                        graphValues.add(graphValue);

                    });

                    graphValues.sort(Comparator.comparing(v -> v.getIndex()));

                    inspectdata.add(label);
                    graphValues.forEach(v -> {
                        inspectdata.add(v.getValue());
                    });

                    graphDataColumns.add(inspectdata);

                }
            }

            //없는옵션이면
            else{
                return ResponseEntity.ok(res.fail(ResponseErrorCode.E008.getCode(),ResponseErrorCode.E008.getDesc(),null,null));
            }

        data.put("title",periodname+"차 " + treatmentcondition.getGraphTitle());
        data.put("unit", MeasurementItemCode.M001.getDesc()+"(" + MeasurementItemCode.M001.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
        data.put("categories",categories);
       

        log.info(seriescode.getDesc() + "압축강도 Bar 그래프 데이터 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }


    //==========================================================================================================
    // 해안환경시험체 라인그래프
    private ResponseEntity line_S003(TreatmentCondition treatmentcondition,SeriesCode seriescode ,HttpServletRequest request){


         
String currentuserid = request.getHeader("insert_id");
AjaxResponse res = new AjaxResponse();
HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 라인용(주기) 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")" );

        List<CompressiveStrengthDto> ltdDatas = compressiveStrengthService.findBySeriesCode(seriescode);

        //=== 1. X라벨 저장 ========================================================

        List<GraphXLabel> graphyXLabelsrow = new ArrayList<>();
        ltdDatas.forEach(v ->{
            GraphXLabel graphyXLabel = new GraphXLabel(v.getPeriod(),v.getPeriodName());
            graphyXLabelsrow.add(graphyXLabel);

        });

        List<GraphXLabel> graphyXLabels = graphyXLabelsrow.stream()
                .distinct()
                .sorted(Comparator.comparing(GraphXLabel::getValue))
                .collect(Collectors.toList());


        // 2 범례및 데이터 생성 저장
        datasets.clear();
        List<List<String>> graphDataColumns = new ArrayList<>();

        // X라벨을 돌면서 graphDatas 에 추가
        List<String> xRows = new ArrayList<>();
        xRows.add("x");
        graphyXLabels.forEach(x -> {
            xRows.add(Integer.toString(x.getValue()));

        });
        graphDataColumns.add(xRows);

        //======================T30101 W/C 에 따른 주기별 압축강도
        if(treatmentcondition.equals(TreatmentCondition.T30101)) {

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
            for (int i = 0; i < graphLegends.size(); i++) {


                String label = graphLegends.get(i).getName();
                List<GraphValue> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                ltdDatas.stream()
                        .filter(v -> v.getElement1().equals(label))
                        .collect(Collectors.groupingBy(CompressiveStrengthDto::getPeriod
                                , Collectors.averagingDouble(CompressiveStrengthDto::getValue)
                        )).forEach((k, v) -> {

                    GraphValue graphValue = new GraphValue(k, String.format("%.1f", v));
                    graphValues.add(graphValue);

                });

                graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                inspectdata.add(label);
                graphValues.forEach(v -> {
                    inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue()));

                });

                graphDataColumns.add(inspectdata);

            }
        }//======================T30102 초기염붐함유량 따른 주기별 압축강도
        else if(treatmentcondition.equals(TreatmentCondition.T30102)) {
            //=== 2.1 범례 저장 (고로슬래그,플라이에쉬) ==========================================
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
            for (int i = 0; i < graphLegends.size(); i++) {


                String label = graphLegends.get(i).getName();
                List<GraphValue> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                ltdDatas.stream()
                        .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                        .collect(Collectors.groupingBy(CompressiveStrengthDto::getPeriod
                                , Collectors.averagingDouble(CompressiveStrengthDto::getValue)
                        )).forEach((k, v) -> {

                    GraphValue graphValue = new GraphValue(k, String.format("%.1f", v));
                    graphValues.add(graphValue);

                });

                graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                inspectdata.add(label+'%');
                graphValues.forEach(v -> {
                    inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue()));
                });

                graphDataColumns.add(inspectdata);

            }
        }



        //없는옵션이면
        else{
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E008.getCode(),ResponseErrorCode.E008.getDesc(),null,null));
        }



        data.put("title",treatmentcondition.getGraphTitle());
        data.put("unit", MeasurementItemCode.M001.getDesc()+"(" + MeasurementItemCode.M001.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
       

        log.info(seriescode.getDesc() + " 압축강도 라인용(주기) 그래프 데이터 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }
    //==========================================================================================================
    //해안환경기준시험 Bar 그래프
    private ResponseEntity bar_S003(String periodname,TreatmentCondition treatmentcondition,SeriesCode seriescode ,HttpServletRequest request){



         
String currentuserid = request.getHeader("insert_id");
AjaxResponse res = new AjaxResponse();
HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + "압축강도 Bar 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid + "'");

        List<CompressiveStrengthDto> ltdDatasRaw = compressiveStrengthService.findBySeriesCode(seriescode);
        // 해당주기데이터만 취합
        List<CompressiveStrengthDto> ltdDatas = ltdDatasRaw.stream()
                .filter(v -> v.getPeriodName().equals(periodname)).collect(Collectors.toList());


        List<String> categories = new ArrayList<>();

        datasets.clear();
        List<List<String>> graphDataColumns = new ArrayList<>();

        //======================T30103 W/C 에따른 압축강도
        if(treatmentcondition.equals(TreatmentCondition.T30103)) {
            //=== 1. X라벨 저장 ========================================================

            List<GraphXLabel> graphyXLabelsrow = new ArrayList<>();

            ltdDatas.forEach(v -> {
                GraphXLabel graphyXLabel = new GraphXLabel(1, v.getElement1() );
                graphyXLabelsrow.add(graphyXLabel);

            });

            List<GraphXLabel> graphyXLabels = graphyXLabelsrow.stream()
                    .distinct()
                    .sorted(Comparator.comparing(GraphXLabel::getValue))
                    .collect(Collectors.toList());

            graphyXLabels.sort(Comparator.comparing(GraphXLabel::getName));
            graphyXLabels.forEach(v -> {
                categories.add(v.getName());
            });


            //=== 2. 범례 저장 (0.04%,0.16%) ==========================================
            List<GraphLegend> graphLegendsrow = new ArrayList<>();
            ltdDatas.forEach(v -> {
                GraphLegend graphLegend = new GraphLegend(1, Double.toString(v.getSaltRate()));
                graphLegendsrow.add(graphLegend);
            });

            List<GraphLegend> graphLegends = graphLegendsrow.stream()
                    .distinct()
                    .collect(Collectors.toList());
            graphLegends.sort(Comparator.comparing(GraphLegend::getName));
            //graphLegends.forEach(v->System.out.println(v.toString()));

            //=== 3.범례별 평균값구해서 datasets 에 넣기=======================================




            //범례를 루프돌면서 실데이터를 graphDatas추가
            for (int i = 0; i < graphLegends.size(); i++) {


                String label = graphLegends.get(i).getName();
                List<GraphValueString> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();

                for(int j = 0 ; j < categories.size();j++){
                    String category = categories.get(j);

                    List<CompressiveStrengthDto> valuelist = ltdDatas.stream()
                            .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                            .filter(v -> v.getElement1().equals(category))
                            .collect(Collectors.toList());
                    Double avgvalue = valuelist.stream()
                            .collect(Collectors.averagingDouble(CompressiveStrengthDto::getValue));

                    GraphValueString graphValue = new GraphValueString(category, CommonUtils.GetZeroToNullString(String.format("%.1f", avgvalue)));
                    graphValues.add(graphValue);


                }



//                ltdDatas.stream()
//                        .filter(v -> Double.toString(v.getSaltRate()).equals(label))
//                        .collect(Collectors.groupingBy(CompressiveStrengthDto::getElement1
//                                , Collectors.averagingDouble(CompressiveStrengthDto::getValue)
//                        )).forEach((k, v) -> {
//
//                    GraphValueString graphValue = new GraphValueString(k, String.format("%.1f", v));
//                    graphValues.add(graphValue);
//
//                });

                graphValues.sort(Comparator.comparing(GraphValueString::getIndex));

                inspectdata.add(label+'%');
                graphValues.forEach(v -> inspectdata.add(v.getValue()));

                graphDataColumns.add(inspectdata);

            }
        }

        //없는옵션이면
        else{
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E008.getCode(),ResponseErrorCode.E008.getDesc(),null,null));
        }

        data.put("title",periodname+"차 " + treatmentcondition.getGraphTitle());
        data.put("unit", MeasurementItemCode.M001.getDesc()+"(" + MeasurementItemCode.M001.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
        data.put("categories",categories);
       

        log.info(seriescode.getDesc() + "압축강도 Bar 그래프 데이터 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    //==========================================================================================================
    // 실리카흄 라인그래프
    private ResponseEntity line_S006(TreatmentCondition treatmentcondition,SeriesCode seriescode ,HttpServletRequest request){


         
String currentuserid = request.getHeader("insert_id");
AjaxResponse res = new AjaxResponse();
HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 라인용(주기) 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")" );

        List<CompressiveStrengthDto> ltdDatas = compressiveStrengthService.findBySeriesCode(seriescode);

        //=== 1. X라벨 저장 ========================================================

        List<GraphXLabel> graphyXLabelsrow = new ArrayList<>();
        ltdDatas.forEach(v ->{
            GraphXLabel graphyXLabel = new GraphXLabel(v.getPeriod(),v.getPeriodName());
            graphyXLabelsrow.add(graphyXLabel);

        });

        List<GraphXLabel> graphyXLabels = graphyXLabelsrow.stream()
                .distinct()
                .sorted(Comparator.comparing(GraphXLabel::getValue))
                .collect(Collectors.toList());


        // 2 범례및 데이터 생성 저장

        datasets.clear();
        List<List<String>> graphDataColumns = new ArrayList<>();

        // X라벨을 돌면서 graphDatas 에 추가
        List<String> xRows = new ArrayList<>();
        xRows.add("x");
        graphyXLabels.forEach(x -> {
            xRows.add(Integer.toString(x.getValue()));

        });
        graphDataColumns.add(xRows);

        //======================T60101 노출환경 에 따른 주기별 압축강도
        if(treatmentcondition.equals(TreatmentCondition.T60101)) {

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
            for (int i = 0; i < graphLegends.size(); i++) {


                String label = graphLegends.get(i).getName();
                List<GraphValue> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                ltdDatas.stream()
                        .filter(v -> v.getEnvironment().equals(label))
                        .collect(Collectors.groupingBy(CompressiveStrengthDto::getPeriod
                                , Collectors.averagingDouble(CompressiveStrengthDto::getValue)
                        )).forEach((k, v) -> {

                    GraphValue graphValue = new GraphValue(k, String.format("%.1f", v));
                    graphValues.add(graphValue);

                });

                graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                inspectdata.add(label);
                graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                graphDataColumns.add(inspectdata);

            }
        }//======================T60102 초기염분함유량 따른 주기별 압축강도
        else if(treatmentcondition.equals(TreatmentCondition.T60102)) {
            //=== 2.1 범례 저장 (고로슬래그,플라이에쉬) ==========================================
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
            for (int i = 0; i < graphLegends.size(); i++) {


                String label = graphLegends.get(i).getName();
                List<GraphValue> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                ltdDatas.stream()
                        .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                        .collect(Collectors.groupingBy(CompressiveStrengthDto::getPeriod
                                , Collectors.averagingDouble(CompressiveStrengthDto::getValue)
                        )).forEach((k, v) -> {

                    GraphValue graphValue = new GraphValue(k, String.format("%.2f", v));
                    graphValues.add(graphValue);

                });

                graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                inspectdata.add(label+'%');
                graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                graphDataColumns.add(inspectdata);

            }
        }



        //없는옵션이면
        else{
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E008.getCode(),ResponseErrorCode.E008.getDesc(),null,null));
        }



        data.put("title",treatmentcondition.getGraphTitle());
        data.put("unit", MeasurementItemCode.M001.getDesc()+"(" + MeasurementItemCode.M001.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
       

        log.info(seriescode.getDesc() + " 압축강도 라인용(주기) 그래프 데이터 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    //==========================================================================================================
    //실리카흄 사용 시험체 Bar 그래프
    private ResponseEntity bar_S006(String periodname,TreatmentCondition treatmentcondition,SeriesCode seriescode ,HttpServletRequest request){



         
String currentuserid = request.getHeader("insert_id");
AjaxResponse res = new AjaxResponse();
HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + "압축강도 Bar 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid + "'");

        List<CompressiveStrengthDto> ltdDatasRaw = compressiveStrengthService.findBySeriesCode(seriescode);
        // 해당주기데이터만 취합
        List<CompressiveStrengthDto> ltdDatas = ltdDatasRaw.stream()
                .filter(v -> v.getPeriodName().equals(periodname)).collect(Collectors.toList());


        List<String> categories = new ArrayList<>();

        datasets.clear();
        List<List<String>> graphDataColumns = new ArrayList<>();

        //======================T60103 노출환경 에따른 압축강도
        if(treatmentcondition.equals(TreatmentCondition.T60103)) {
            //=== 1. X라벨 저장 ========================================================

            List<GraphXLabel> graphyXLabelsrow = new ArrayList<>();

            ltdDatas.forEach(v -> {
                GraphXLabel graphyXLabel = new GraphXLabel(1, v.getEnvironment() );
                graphyXLabelsrow.add(graphyXLabel);

            });

            List<GraphXLabel> graphyXLabels = graphyXLabelsrow.stream()
                    .distinct()
                    .sorted(Comparator.comparing(GraphXLabel::getValue))
                    .collect(Collectors.toList());

            graphyXLabels.sort(Comparator.comparing(v -> v.getName()));
            graphyXLabels.forEach(v -> {
                categories.add(v.getName());
            });


            //=== 2. 범례 저장 (0.04%,0.16%) ==========================================
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
            for (int i = 0; i < graphLegends.size(); i++) {


                String label = graphLegends.get(i).getName();
                List<GraphValueString> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                ltdDatas.stream()
                        .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                        .collect(Collectors.groupingBy(v -> v.getEnvironment()
                                , Collectors.averagingDouble(value -> value.getValue())
                        )).forEach((k, v) -> {

                    GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                    graphValues.add(graphValue);

                });

                graphValues.sort(Comparator.comparing(v -> v.getIndex()));

                inspectdata.add(label+'%');
                graphValues.forEach(v -> {
                    inspectdata.add(v.getValue());
                });

                graphDataColumns.add(inspectdata);

            }
        }

        //없는옵션이면
        else{
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E008.getCode(),ResponseErrorCode.E008.getDesc(),null,null));
        }

        data.put("title",periodname+"차 " + treatmentcondition.getGraphTitle());
        data.put("unit", MeasurementItemCode.M001.getDesc()+"(" + MeasurementItemCode.M001.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
        data.put("categories",categories);
       

        log.info(seriescode.getDesc() + "압축강도 Bar 그래프 데이터 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }
    //==========================================================================================================
    // 염붐함유기준시험체 라인그래프
    private ResponseEntity line_S007(TreatmentCondition treatmentcondition,SeriesCode seriescode ,HttpServletRequest request){


         
String currentuserid = request.getHeader("insert_id");
AjaxResponse res = new AjaxResponse();
HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 라인용(주기) 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")" );

        List<CompressiveStrengthDto> ltdDatas = compressiveStrengthService.findBySeriesCode(seriescode);

        //=== 1. X라벨 저장 ========================================================

        List<GraphXLabel> graphyXLabelsrow = new ArrayList<>();
        ltdDatas.forEach(v ->{
            GraphXLabel graphyXLabel = new GraphXLabel(v.getPeriod(),v.getPeriodName());
            graphyXLabelsrow.add(graphyXLabel);

        });

        List<GraphXLabel> graphyXLabels = graphyXLabelsrow.stream()
                .distinct()
                .sorted(Comparator.comparing(GraphXLabel::getValue))
                .collect(Collectors.toList());


        // 2 범례및 데이터 생성 저장

        datasets.clear();
        List<List<String>> graphDataColumns = new ArrayList<>();

        // X라벨을 돌면서 graphDatas 에 추가
        List<String> xRows = new ArrayList<>();
        xRows.add("x");
        graphyXLabels.forEach(x -> {
            xRows.add(Integer.toString(x.getValue()));

        });
        graphDataColumns.add(xRows);

        //======================T70101 노출환경 에 따른 주기별 압축강도
        if(treatmentcondition.equals(TreatmentCondition.T70101)) {

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
            for (int i = 0; i < graphLegends.size(); i++) {


                String label = graphLegends.get(i).getName();
                List<GraphValue> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                ltdDatas.stream()
                        .filter(v -> v.getEnvironment().equals(label))
                        .collect(Collectors.groupingBy(CompressiveStrengthDto::getPeriod
                                , Collectors.averagingDouble(CompressiveStrengthDto::getValue)
                        )).forEach((k, v) -> {

                    GraphValue graphValue = new GraphValue(k, String.format("%.2f", v));
                    graphValues.add(graphValue);

                });

                graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                inspectdata.add(label);
                graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                graphDataColumns.add(inspectdata);

            }
        }//======================T70102 초기염분함유량 따른 주기별 압축강도
        else if(treatmentcondition.equals(TreatmentCondition.T70102)) {
            //=== 2.1 범례 저장 (고로슬래그,플라이에쉬) ==========================================
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
            for (int i = 0; i < graphLegends.size(); i++) {


                String label = graphLegends.get(i).getName();
                List<GraphValue> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                ltdDatas.stream()
                        .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                        .collect(Collectors.groupingBy(CompressiveStrengthDto::getPeriod
                                , Collectors.averagingDouble(CompressiveStrengthDto::getValue)
                        )).forEach((k, v) -> {

                    GraphValue graphValue = new GraphValue(k, String.format("%.2f", v));
                    graphValues.add(graphValue);

                });

                graphValues.sort(Comparator.comparing(GraphValue::getIndex));

                inspectdata.add(label+'%');
                graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                graphDataColumns.add(inspectdata);

            }
        }



        //없는옵션이면
        else{
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E008.getCode(),ResponseErrorCode.E008.getDesc(),null,null));
        }



        data.put("title",treatmentcondition.getGraphTitle());
        data.put("unit", MeasurementItemCode.M001.getDesc()+"(" + MeasurementItemCode.M001.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
       

        log.info(seriescode.getDesc() + " 압축강도 라인용(주기) 그래프 데이터 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    //==========================================================================================================
    //염분 함유 기준 시험체 사용 시험체 Bar 그래프
    private ResponseEntity bar_S007(String periodname,TreatmentCondition treatmentcondition,SeriesCode seriescode ,HttpServletRequest request){



         
String currentuserid = request.getHeader("insert_id");
AjaxResponse res = new AjaxResponse();
HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + "압축강도 Bar 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid + "'");

        List<CompressiveStrengthDto> ltdDatasRaw = compressiveStrengthService.findBySeriesCode(seriescode);
        // 해당주기데이터만 취합
        List<CompressiveStrengthDto> ltdDatas = ltdDatasRaw.stream()
                .filter(v -> v.getPeriodName().equals(periodname)).collect(Collectors.toList());


        List<String> categories = new ArrayList<>();

        datasets.clear();
        List<List<String>> graphDataColumns = new ArrayList<>();

        //======================T70103 노출환경 에따른 압축강도
        if(treatmentcondition.equals(TreatmentCondition.T70103)) {
            //=== 1. X라벨 저장 ========================================================

            List<GraphXLabel> graphyXLabelsrow = new ArrayList<>();

            ltdDatas.forEach(v -> {
                GraphXLabel graphyXLabel = new GraphXLabel(1, v.getEnvironment() );
                graphyXLabelsrow.add(graphyXLabel);

            });

            List<GraphXLabel> graphyXLabels = graphyXLabelsrow.stream()
                    .distinct()
                    .sorted(Comparator.comparing(GraphXLabel::getValue))
                    .collect(Collectors.toList());

            graphyXLabels.sort(Comparator.comparing(v -> v.getName()));
            graphyXLabels.forEach(v -> {
                categories.add(v.getName());
            });


            //=== 2. 범례 저장 (0.04%,0.16%) ==========================================
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
            for (int i = 0; i < graphLegends.size(); i++) {


                String label = graphLegends.get(i).getName();
                List<GraphValueString> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                ltdDatas.stream()
                        .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                        .collect(Collectors.groupingBy(v -> v.getEnvironment()
                                , Collectors.averagingDouble(value -> value.getValue())
                        )).forEach((k, v) -> {

                    GraphValueString graphValue = new GraphValueString(k, String.format("%.2f", v));
                    graphValues.add(graphValue);

                });

                graphValues.sort(Comparator.comparing(v -> v.getIndex()));

                inspectdata.add(label+'%');
                graphValues.forEach(v -> {
                    inspectdata.add(v.getValue());
                });

                graphDataColumns.add(inspectdata);

            }
        }

        //없는옵션이면
        else{
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E008.getCode(),ResponseErrorCode.E008.getDesc(),null,null));
        }

        data.put("title",periodname+"차 " + treatmentcondition.getGraphTitle());
        data.put("unit", MeasurementItemCode.M001.getDesc()+"(" + MeasurementItemCode.M001.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
        data.put("categories",categories);
       

        log.info(seriescode.getDesc() + "압축강도 Bar 그래프 데이터 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }


}
