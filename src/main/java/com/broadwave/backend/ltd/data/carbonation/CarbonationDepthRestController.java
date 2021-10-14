package com.broadwave.backend.ltd.data.carbonation;

import com.broadwave.backend.bscodes.SeriesCode;
import com.broadwave.backend.common.AjaxResponse;
import com.broadwave.backend.bscodes.MeasurementItemCode;
import com.broadwave.backend.bscodes.PeriodType;
import com.broadwave.backend.bscodes.TreatmentCondition;
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
 * Time : 11:20
 * Remark :
 */
@RestController
@Slf4j
@RequestMapping("/api/ltd/data")
public class CarbonationDepthRestController {

    private final CarbonationDepthService carbonationDepthService;

    @Autowired
    public CarbonationDepthRestController(CarbonationDepthService carbonationDepthService) {
        this.carbonationDepthService = carbonationDepthService;
    }

    @PostMapping("cd/period")
    public ResponseEntity<Map<String,Object>> periodList(@RequestParam(value="seriescode", defaultValue="") SeriesCode seriescode,
                                     HttpServletRequest request){

        String currentuserid = request.getHeader("insert_id");
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        log.info("탄산화깊이 주기 정보 조회(ex.28일,1년,10년..) / 조회자 :'" + currentuserid
                + "' 시리즈(코드) : '" + seriescode.getDesc() + "'(" + seriescode.getCode() + ")" );

        List<CarbonationDepthDto> carbonationDepthDtos = carbonationDepthService.findBySeriesCode(seriescode);

        List<GraphXLabel> graphyXLabelsrow = new ArrayList<>();
        carbonationDepthDtos.forEach(v ->{
            GraphXLabel graphyXLabel = new GraphXLabel(v.getPeriod(), v.getPeriodName());
            graphyXLabelsrow.add(graphyXLabel);

        });

        List<GraphXLabel> graphyXLabels = graphyXLabelsrow.stream()
                .distinct()
                .sorted(Comparator.comparing(GraphXLabel::getValue))
                .collect(Collectors.toList());

        data.put("datalist",graphyXLabels);

        log.info("탄산화깊이 주기 정보 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    //고로슬래그/플라이애쉬 사용 시험체 압축강도 그래프
    @PostMapping("cd")
    public ResponseEntity<Map<String,Object>> graphdata(@RequestParam(value="seriescode", defaultValue="") SeriesCode seriescode,
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

    //==========================================================================================================
    //시리즈1 해사사용 장기시험체 라인그래프
    private ResponseEntity<Map<String,Object>> line_S001(TreatmentCondition treatmentcondition,SeriesCode seriescode ,HttpServletRequest request){

        String currentuserid = request.getHeader("insert_id");
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        log.info(seriescode.getDesc() + " 탄산화깊이 라인용(주기) 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")" );

        List<CarbonationDepthDto> ltdDatas = carbonationDepthService.findBySeriesCode(seriescode);

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
        List<List<String>> graphDataColumns = new ArrayList<>();

        // X라벨을 돌면서 graphDatas 에 추가
        List<String> xRows = new ArrayList<>();
        xRows.add("x");
        graphyXLabels.forEach(x -> xRows.add(Integer.toString(x.getValue())));
        graphDataColumns.add(xRows);

        //======================T10201 W/C에따른 주기별 압축강도
        if(treatmentcondition.equals(TreatmentCondition.T10201)) {

            //=== 2.1 범례 저장 (56%/46%) ==========================================
            List<GraphLegend> graphLegendsrow = new ArrayList<>();
            ltdDatas.forEach(v -> {
                GraphLegend graphLegend = new GraphLegend(1, v.getElement2());
                graphLegendsrow.add(graphLegend);
            });

            List<GraphLegend> graphLegends = graphLegendsrow.stream()
                    .distinct()
                    .collect(Collectors.toList());
            //graphLegends.forEach(v->System.out.println(v.toString()));

            //=== 2.2 범례별 평균값구해서  에 넣기=======================================
            //for (int i = 0; i < graphLegends.size(); i++) {
            for(GraphLegend graphLegend: graphLegends){


                String label = graphLegend.getName();
                List<GraphValue> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                ltdDatas.stream()
                        .filter(v -> v.getElement2().equals(label))
                        .collect(Collectors.groupingBy(CarbonationDepthDto::getPeriod
                                , Collectors.averagingDouble(CarbonationDepthDto::getValue)
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

        //======================T10202 방청제 따른 주기별 압축강도
        else if(treatmentcondition.equals(TreatmentCondition.T10202)) {
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

            //=== 2.2 범례별 평균값구해서  에 넣기=======================================
            for(GraphLegend graphLegend: graphLegends){


                String label = graphLegend.getName();
                List<GraphValue> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                ltdDatas.stream()
                        .filter(v -> v.getElement1().equals(label))
                        .collect(Collectors.groupingBy(CarbonationDepthDto::getPeriod
                                , Collectors.averagingDouble(CarbonationDepthDto::getValue)
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

        //======================T10203 초기염붐함유량 따른 주기별 압축강도
        else if(treatmentcondition.equals(TreatmentCondition.T10203)) {
            //=== 2.1 범례 저장 (고로슬래그,플라이에쉬) ==========================================
            List<GraphLegend> graphLegendsrow = new ArrayList<>();
            ltdDatas.forEach(v -> {
                GraphLegend graphLegend = new GraphLegend(1, String.format("%.2f",v.getSaltRate()));
                graphLegendsrow.add(graphLegend);
            });

            List<GraphLegend> graphLegends = graphLegendsrow.stream()
                    .distinct()
                    .collect(Collectors.toList());
            //graphLegends.forEach(v->System.out.println(v.toString()));

            //=== 2.2 범례별 평균값구해서  에 넣기=======================================
            for(GraphLegend graphLegend: graphLegends){

                String label = graphLegend.getName();
                List<GraphValue> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                ltdDatas.stream()
                        .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                        .collect(Collectors.groupingBy(CarbonationDepthDto::getPeriod
                                , Collectors.averagingDouble(CarbonationDepthDto::getValue)
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

        log.info(seriescode.getDesc() + " 탄산화깊이 라인용(주기) 그래프 데이터 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    //==========================================================================================================
    //시리즈2 내륙환경 기준 시험체 라인그래프
    private ResponseEntity<Map<String,Object>> line_S002(TreatmentCondition treatmentcondition,SeriesCode seriescode ,HttpServletRequest request){

        String currentuserid = request.getHeader("insert_id");
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        log.info(seriescode.getDesc() + " 탄산화깊이 라인용(주기) 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")" );

        List<CarbonationDepthDto> ltdDatas = carbonationDepthService.findBySeriesCode(seriescode);

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
        List<List<String>> graphDataColumns = new ArrayList<>();

        // X라벨을 돌면서 graphDatas 에 추가
        List<String> xRows = new ArrayList<>();
        xRows.add("x");
        graphyXLabels.forEach(x -> xRows.add(Integer.toString(x.getValue())));
        graphDataColumns.add(xRows);

        //======================T20201 W/C에따른 주기별 압축강도
        if(treatmentcondition.equals(TreatmentCondition.T20201)) {

            //=== 2.1 범례 저장 (56%/46%) ==========================================
            List<GraphLegend> graphLegendsrow = new ArrayList<>();
            ltdDatas.forEach(v -> {
                GraphLegend graphLegend = new GraphLegend(1, v.getElement1());
                graphLegendsrow.add(graphLegend);
            });

            List<GraphLegend> graphLegends = graphLegendsrow.stream()
                    .distinct()
                    .collect(Collectors.toList());
            //graphLegends.forEach(v->System.out.println(v.toString()));

            //=== 2.2 범례별 평균값구해서  에 넣기=======================================
            //for (int i = 0; i < graphLegends.size(); i++) {
            for(GraphLegend graphLegend: graphLegends){


                String label = graphLegend.getName();
                List<GraphValue> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                ltdDatas.stream()
                        .filter(v -> v.getElement1().equals(label))
                        .collect(Collectors.groupingBy(CarbonationDepthDto::getPeriod
                                , Collectors.averagingDouble(CarbonationDepthDto::getValue)
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


        //======================T20202 초기염붐함유량 따른 주기별 압축강도
        else if(treatmentcondition.equals(TreatmentCondition.T20202)) {
            //=== 2.1 범례 저장 (고로슬래그,플라이에쉬) ==========================================
            List<GraphLegend> graphLegendsrow = new ArrayList<>();
            ltdDatas.forEach(v -> {
                GraphLegend graphLegend = new GraphLegend(1, String.format("%.2f",v.getSaltRate()));
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
                        .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                        .collect(Collectors.groupingBy(CarbonationDepthDto::getPeriod
                                , Collectors.averagingDouble(CarbonationDepthDto::getValue)
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

        log.info(seriescode.getDesc() + " 탄산화깊이 라인용(주기) 그래프 데이터 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    //==========================================================================================================
    //시리즈3 해안환경 기준 시험체 라인그래프
    private ResponseEntity<Map<String,Object>> line_S003(TreatmentCondition treatmentcondition,SeriesCode seriescode ,HttpServletRequest request){
        String currentuserid = request.getHeader("insert_id");
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 탄산화깊이 라인용(주기) 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")" );

        List<CarbonationDepthDto> ltdDatas = carbonationDepthService.findBySeriesCode(seriescode);

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
        List<List<String>> graphDataColumns = new ArrayList<>();

        // X라벨을 돌면서 graphDatas 에 추가
        List<String> xRows = new ArrayList<>();
        xRows.add("x");
        graphyXLabels.forEach(x -> xRows.add(Integer.toString(x.getValue())));
        graphDataColumns.add(xRows);

        //======================T30201 W/C에따른 주기별 압축강도
        if(treatmentcondition.equals(TreatmentCondition.T30201)) {

            //=== 2.1 범례 저장 (56%/46%) ==========================================
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
            //for (int i = 0; i < graphLegends.size(); i++) {
            for(GraphLegend graphLegend: graphLegends){


                String label = graphLegend.getName();
                List<GraphValue> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                ltdDatas.stream()
                        .filter(v -> v.getElement1().equals(label))
                        .collect(Collectors.groupingBy(CarbonationDepthDto::getPeriod
                                , Collectors.averagingDouble(CarbonationDepthDto::getValue)
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


        //======================T30202 초기염붐함유량 따른 주기별 압축강도
        else if(treatmentcondition.equals(TreatmentCondition.T30202)) {
            //=== 2.1 범례 저장 (고로슬래그,플라이에쉬) ==========================================
            List<GraphLegend> graphLegendsrow = new ArrayList<>();
            ltdDatas.forEach(v -> {
                GraphLegend graphLegend = new GraphLegend(1, String.format("%.2f",v.getSaltRate()));
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
                        .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                        .collect(Collectors.groupingBy(CarbonationDepthDto::getPeriod
                                , Collectors.averagingDouble(CarbonationDepthDto::getValue)
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

        log.info(seriescode.getDesc() + " 탄산화깊이 라인용(주기) 그래프 데이터 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    //==========================================================================================================
    //시리즈4 부순골재/재생모래 라인그래프
    private ResponseEntity<Map<String,Object>> line_S004(TreatmentCondition treatmentcondition,SeriesCode seriescode ,HttpServletRequest request){

        String currentuserid = request.getHeader("insert_id");
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        log.info(seriescode.getDesc() + " 탄산화깊이 라인용(주기) 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")" );

        List<CarbonationDepthDto> ltdDatas = carbonationDepthService.findBySeriesCode(seriescode);

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
        List<List<String>> graphDataColumns = new ArrayList<>();

        // X라벨을 돌면서 graphDatas 에 추가
        List<String> xRows = new ArrayList<>();
        xRows.add("x");
        graphyXLabels.forEach(x -> xRows.add(Integer.toString(x.getValue())));
        graphDataColumns.add(xRows);

        //======================T40201 노출환경에따른 주기별 압축강도
        if(treatmentcondition.equals(TreatmentCondition.T40201)) {

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
            //for (int i = 0; i < graphLegends.size(); i++) {
            for(GraphLegend graphLegend: graphLegends){


                String label = graphLegend.getName();
                List<GraphValue> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                ltdDatas.stream()
                        .filter(v -> v.getEnvironment().equals(label))
                        .collect(Collectors.groupingBy(CarbonationDepthDto::getPeriod
                                , Collectors.averagingDouble(CarbonationDepthDto::getValue)
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

        //======================T40202 사용재료에 따른 주기별 압축강도
        else if(treatmentcondition.equals(TreatmentCondition.T40202)) {
            //=== 2.1 범례 저장 (부순골재,재생골재) ==========================================
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
                        .collect(Collectors.groupingBy(CarbonationDepthDto::getPeriod
                                , Collectors.averagingDouble(CarbonationDepthDto::getValue)
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

        //======================T40203 초기염분함유량 따른 주기별 압축강도
        else if(treatmentcondition.equals(TreatmentCondition.T40203)) {
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
            for(GraphLegend graphLegend: graphLegends){

                String label = graphLegend.getName();
                List<GraphValue> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                ltdDatas.stream()
                        .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                        .collect(Collectors.groupingBy(CarbonationDepthDto::getPeriod
                                , Collectors.averagingDouble(CarbonationDepthDto::getValue)
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

        log.info(seriescode.getDesc() + " 탄산화깊이 라인용(주기) 그래프 데이터 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }


    //==========================================================================================================
    //시리즈5 고로슬래그/플라이애쉬 라인그래프
    private ResponseEntity<Map<String,Object>> line_S005(TreatmentCondition treatmentcondition,SeriesCode seriescode ,HttpServletRequest request){


        String currentuserid = request.getHeader("insert_id");
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 탄산화깊이 라인용(주기) 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")" );

        List<CarbonationDepthDto> ltdDatas = carbonationDepthService.findBySeriesCode(seriescode);

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
        List<List<String>> graphDataColumns = new ArrayList<>();

        // X라벨을 돌면서 graphDatas 에 추가
        List<String> xRows = new ArrayList<>();
        xRows.add("x");
        graphyXLabels.forEach(x -> xRows.add(Integer.toString(x.getValue())));
        graphDataColumns.add(xRows);

        //======================T50201 노출환경에따른 주기별 압축강도
        if(treatmentcondition.equals(TreatmentCondition.T50201)) {

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
            //for (int i = 0; i < graphLegends.size(); i++) {
            for(GraphLegend graphLegend: graphLegends){


                String label = graphLegend.getName();
                List<GraphValue> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                ltdDatas.stream()
                        .filter(v -> v.getEnvironment().equals(label))
                        .collect(Collectors.groupingBy(CarbonationDepthDto::getPeriod
                                , Collectors.averagingDouble(CarbonationDepthDto::getValue)
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

        //======================T50202 혼화재료에 따른 주기별 압축강도
        else if(treatmentcondition.equals(TreatmentCondition.T50202)) {
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
            for(GraphLegend graphLegend: graphLegends){


                String label = graphLegend.getName();
                List<GraphValue> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                ltdDatas.stream()
                        .filter(v -> v.getElement1().equals(label))
                        .collect(Collectors.groupingBy(CarbonationDepthDto::getPeriod
                                , Collectors.averagingDouble(CarbonationDepthDto::getValue)
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

        //======================T50203 초기염붐함유량 따른 주기별 압축강도
        else if(treatmentcondition.equals(TreatmentCondition.T50203)) {
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
            for(GraphLegend graphLegend: graphLegends){

                String label = graphLegend.getName();
                List<GraphValue> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                ltdDatas.stream()
                        .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                        .collect(Collectors.groupingBy(CarbonationDepthDto::getPeriod
                                , Collectors.averagingDouble(CarbonationDepthDto::getValue)
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

        log.info(seriescode.getDesc() + " 탄산화깊이 라인용(주기) 그래프 데이터 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }
    //==========================================================================================================
    //시리즈6 실리카 라인그래프
    private ResponseEntity<Map<String,Object>> line_S006(TreatmentCondition treatmentcondition,SeriesCode seriescode ,HttpServletRequest request){


        String currentuserid = request.getHeader("insert_id");
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 탄산화깊이 라인용(주기) 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")" );

        List<CarbonationDepthDto> ltdDatas = carbonationDepthService.findBySeriesCode(seriescode);

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
        List<List<String>> graphDataColumns = new ArrayList<>();

        // X라벨을 돌면서 graphDatas 에 추가
        List<String> xRows = new ArrayList<>();
        xRows.add("x");
        graphyXLabels.forEach(x -> xRows.add(Integer.toString(x.getValue())));
        graphDataColumns.add(xRows);

        //======================T650201 노출환경에따른 주기별 압축강도
        if(treatmentcondition.equals(TreatmentCondition.T60201)) {

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
            //for (int i = 0; i < graphLegends.size(); i++) {
            for(GraphLegend graphLegend: graphLegends){


                String label = graphLegend.getName();
                List<GraphValue> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                ltdDatas.stream()
                        .filter(v -> v.getEnvironment().equals(label))
                        .collect(Collectors.groupingBy(CarbonationDepthDto::getPeriod
                                , Collectors.averagingDouble(CarbonationDepthDto::getValue)
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



        //======================T60202 초기염붐함유량 따른 주기별 압축강도
        else if(treatmentcondition.equals(TreatmentCondition.T60202)) {
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
            for(GraphLegend graphLegend: graphLegends){

                String label = graphLegend.getName();
                List<GraphValue> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                ltdDatas.stream()
                        .filter(v -> Double.toString(v.getSaltRate()).equals(label))
                        .collect(Collectors.groupingBy(CarbonationDepthDto::getPeriod
                                , Collectors.averagingDouble(CarbonationDepthDto::getValue)
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

        log.info(seriescode.getDesc() + " 탄산화깊이 라인용(주기) 그래프 데이터 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }


    //==========================================================================================================
    //시리즈7 염분함율 기준 시험체 라인그래프
    private ResponseEntity<Map<String,Object>> line_S007(TreatmentCondition treatmentcondition,SeriesCode seriescode ,HttpServletRequest request){


        String currentuserid = request.getHeader("insert_id");
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 탄산화깊이 라인용(주기) 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid
                + "' 분석조건(코드) : '" + treatmentcondition.getDesc() + "'(" + treatmentcondition.getCode() + ")" );

        List<CarbonationDepthDto> ltdDatas = carbonationDepthService.findBySeriesCode(seriescode);

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
        List<List<String>> graphDataColumns = new ArrayList<>();

        // X라벨을 돌면서 graphDatas 에 추가
        List<String> xRows = new ArrayList<>();
        xRows.add("x");
        graphyXLabels.forEach(x -> xRows.add(Integer.toString(x.getValue())));
        graphDataColumns.add(xRows);

        //======================T70201 W/C에따른 주기별 압축강도
        if(treatmentcondition.equals(TreatmentCondition.T70201)) {

            //=== 2.1 범례 저장 (56%/46%) ==========================================
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
            //for (int i = 0; i < graphLegends.size(); i++) {
            for(GraphLegend graphLegend: graphLegends){


                String label = graphLegend.getName();
                List<GraphValue> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                ltdDatas.stream()
                        .filter(v -> v.getElement1().equals(label))
                        .collect(Collectors.groupingBy(CarbonationDepthDto::getPeriod
                                , Collectors.averagingDouble(CarbonationDepthDto::getValue)
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


        //======================T70202 초기염붐함유량 따른 주기별 압축강도
        else if(treatmentcondition.equals(TreatmentCondition.T70202)) {
            //=== 2.1 범례 저장 (고로슬래그,플라이에쉬) ==========================================
            List<GraphLegend> graphLegendsrow = new ArrayList<>();
            ltdDatas.forEach(v -> {
                GraphLegend graphLegend = new GraphLegend(1, String.format("%.2f",v.getSaltRate()));
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
                        .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                        .collect(Collectors.groupingBy(CarbonationDepthDto::getPeriod
                                , Collectors.averagingDouble(CarbonationDepthDto::getValue)
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

        log.info(seriescode.getDesc() + " 탄산화깊이 라인용(주기) 그래프 데이터 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    //==========================================================================================================
    //시리즈1 해사 사용 장기 시험체 Bar 그래프
    private ResponseEntity<Map<String,Object>> bar_S001(String periodname,TreatmentCondition treatmentcondition,SeriesCode seriescode ,HttpServletRequest request){



        String currentuserid = request.getHeader("insert_id");
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 탄산화깊이 Bar 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid + "'");

        List<CarbonationDepthDto> ltdDatasRaw = carbonationDepthService.findBySeriesCode(seriescode);
        // 해당주기데이터만 취합
        List<CarbonationDepthDto> ltdDatas = ltdDatasRaw.stream()
                .filter(v -> v.getPeriodName().equals(periodname)).collect(Collectors.toList());


        List<String> categories = new ArrayList<>();
        List<List<String>> graphDataColumns = new ArrayList<>();

        //======================T10204 방청제에따른 탄산화깊디
        if(treatmentcondition.equals(TreatmentCondition.T10204)) {
            //=== 1. X라벨 저장 ========================================================

            List<GraphXLabel> graphyXLabelsrow = new ArrayList<>();

            ltdDatas.forEach(v -> {
                GraphXLabel graphyXLabel = new GraphXLabel(1, v.getElement1());
                graphyXLabelsrow.add(graphyXLabel);

            });

            List<GraphXLabel> graphyXLabels = new ArrayList<>();
            Set<GraphXLabel> uniqueValues = new HashSet<>();
            for (GraphXLabel graphXLabel : graphyXLabelsrow) {
                if (uniqueValues.add(graphXLabel)) {
                    graphyXLabels.add(graphXLabel);
                }
            }
            graphyXLabels.sort(Comparator.comparing(GraphXLabel::getValue));

            graphyXLabels.sort(Comparator.comparing(GraphXLabel::getName));
            graphyXLabels.forEach(v -> categories.add(v.getName()));


            //=== 2. 범례 저장 (고로슬래쉬/플라이애쉬) ==========================================
            List<GraphLegend> graphLegendsrow = new ArrayList<>();
            ltdDatas.forEach(v -> {
                GraphLegend graphLegend = new GraphLegend(1, v.getElement2());
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


                ltdDatas.stream()
                        .filter(v -> v.getElement2().equals(label))
                        .collect(Collectors.groupingBy(CarbonationDepthDto::getElement1
                                , Collectors.averagingDouble(CarbonationDepthDto::getValue)
                        )).forEach((k, v) -> {

                    GraphValueString graphValue = new GraphValueString(k, String.format("%.1f", v));
                    graphValues.add(graphValue);

                });

                graphValues.sort(Comparator.comparing(GraphValueString::getIndex));

                inspectdata.add(label);
                graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                graphDataColumns.add(inspectdata);

            }
        }else //======================T10205 초기염분함유량 에따른 압축강도
            if(treatmentcondition.equals(TreatmentCondition.T10205)) {
                //=== 1. X라벨 저장 ========================================================

                List<GraphXLabel> graphyXLabelsrow = new ArrayList<>();

                ltdDatas.forEach(v -> {
                    GraphXLabel graphyXLabel = new GraphXLabel(1, String.format("%.2f",v.getSaltRate()));
                    graphyXLabelsrow.add(graphyXLabel);

                });

                List<GraphXLabel> graphyXLabels = new ArrayList<>();
                Set<GraphXLabel> uniqueValues = new HashSet<>();
                for (GraphXLabel graphXLabel : graphyXLabelsrow) {
                    if (uniqueValues.add(graphXLabel)) {
                        graphyXLabels.add(graphXLabel);
                    }
                }
                graphyXLabels.sort(Comparator.comparing(GraphXLabel::getValue));

                graphyXLabels.sort(Comparator.comparing(GraphXLabel::getName));
                graphyXLabels.forEach(v -> categories.add(v.getName() + '%'));


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
                for(GraphLegend graphLegend: graphLegends){


                    String label = graphLegend.getName();
                    List<GraphValueString> graphValues = new ArrayList<>();
                    List<String> inspectdata = new ArrayList<>();


                    ltdDatas.stream()
                            .filter(v -> v.getElement1().equals(label))
                            .collect(Collectors.groupingBy(CarbonationDepthDto::getSaltRate
                                    , Collectors.averagingDouble(CarbonationDepthDto::getValue)
                            )).forEach((k, v) -> {

                        GraphValueString graphValue = new GraphValueString(Double.toString(k), String.format("%.1f", v));
                        graphValues.add(graphValue);

                    });

                    graphValues.sort(Comparator.comparing(GraphValueString::getIndex));

                    inspectdata.add(label);
                    graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

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

        log.info(seriescode.getDesc() + " 탄산화갚이 Bar 그래프 데이터 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    //==========================================================================================================
    //시리즈2 내륙환경기준 시험체 Bar 그래프
    private ResponseEntity<Map<String,Object>> bar_S002(String periodname,TreatmentCondition treatmentcondition,SeriesCode seriescode ,HttpServletRequest request){



        String currentuserid = request.getHeader("insert_id");
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 탄산화깊이 Bar 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid + "'");

        List<CarbonationDepthDto> ltdDatasRaw = carbonationDepthService.findBySeriesCode(seriescode);
        // 해당주기데이터만 취합
        List<CarbonationDepthDto> ltdDatas = ltdDatasRaw.stream()
                .filter(v -> v.getPeriodName().equals(periodname)).collect(Collectors.toList());


        List<String> categories = new ArrayList<>();
        List<List<String>> graphDataColumns = new ArrayList<>();

        //======================T20203 W/C 탄산화깊이
        if(treatmentcondition.equals(TreatmentCondition.T20203)) {
            //=== 1. X라벨 저장 ========================================================

            List<GraphXLabel> graphyXLabelsrow = new ArrayList<>();

            ltdDatas.forEach(v -> {
                GraphXLabel graphyXLabel = new GraphXLabel(1, v.getElement1());
                graphyXLabelsrow.add(graphyXLabel);

            });

            List<GraphXLabel> graphyXLabels = new ArrayList<>();
            Set<GraphXLabel> uniqueValues = new HashSet<>();
            for (GraphXLabel graphXLabel : graphyXLabelsrow) {
                if (uniqueValues.add(graphXLabel)) {
                    graphyXLabels.add(graphXLabel);
                }
            }
            graphyXLabels.sort(Comparator.comparing(GraphXLabel::getValue));

            graphyXLabels.sort(Comparator.comparing(GraphXLabel::getName));
            graphyXLabels.forEach(v -> categories.add(v.getName()));


            //=== 2. 범례 저장 (고로슬래쉬/플라이애쉬) ==========================================
            List<GraphLegend> graphLegendsrow = new ArrayList<>();
            ltdDatas.forEach(v -> {
                GraphLegend graphLegend = new GraphLegend(1, String.format("%.2f",v.getSaltRate()));
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


                ltdDatas.stream()
                        .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                        .collect(Collectors.groupingBy(CarbonationDepthDto::getElement1
                                , Collectors.averagingDouble(CarbonationDepthDto::getValue)
                        )).forEach((k, v) -> {

                    GraphValueString graphValue = new GraphValueString(k, String.format("%.1f", v));
                    graphValues.add(graphValue);

                });

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
        data.put("unit", MeasurementItemCode.M001.getDesc()+"(" + MeasurementItemCode.M001.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
        data.put("categories",categories);

        log.info(seriescode.getDesc() + " 탄산화갚이 Bar 그래프 데이터 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    //==========================================================================================================
    //시리즈3 해안환경기준 시험체 Bar 그래프
    private ResponseEntity<Map<String,Object>> bar_S003(String periodname,TreatmentCondition treatmentcondition,SeriesCode seriescode ,HttpServletRequest request){



        String currentuserid = request.getHeader("insert_id");
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 탄산화깊이 Bar 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid + "'");

        List<CarbonationDepthDto> ltdDatasRaw = carbonationDepthService.findBySeriesCode(seriescode);
        // 해당주기데이터만 취합
        List<CarbonationDepthDto> ltdDatas = ltdDatasRaw.stream()
                .filter(v -> v.getPeriodName().equals(periodname)).collect(Collectors.toList());


        List<String> categories = new ArrayList<>();
        List<List<String>> graphDataColumns = new ArrayList<>();

        //======================T30203 W/C 탄산화깊이
        if(treatmentcondition.equals(TreatmentCondition.T30203)) {
            //=== 1. X라벨 저장 ========================================================

            List<GraphXLabel> graphyXLabelsrow = new ArrayList<>();

            ltdDatas.forEach(v -> {
                GraphXLabel graphyXLabel = new GraphXLabel(1, v.getElement1());
                graphyXLabelsrow.add(graphyXLabel);

            });

            List<GraphXLabel> graphyXLabels = new ArrayList<>();
            Set<GraphXLabel> uniqueValues = new HashSet<>();
            for (GraphXLabel graphXLabel : graphyXLabelsrow) {
                if (uniqueValues.add(graphXLabel)) {
                    graphyXLabels.add(graphXLabel);
                }
            }
            graphyXLabels.sort(Comparator.comparing(GraphXLabel::getValue));

            graphyXLabels.sort(Comparator.comparing(GraphXLabel::getName));
            graphyXLabels.forEach(v -> categories.add(v.getName()));


            //=== 2. 범례 저장 (고로슬래쉬/플라이애쉬) ==========================================
            List<GraphLegend> graphLegendsrow = new ArrayList<>();
            ltdDatas.forEach(v -> {
                GraphLegend graphLegend = new GraphLegend(1, String.format("%.2f",v.getSaltRate()));
                graphLegendsrow.add(graphLegend);
            });

            List<GraphLegend> graphLegends = graphLegendsrow.stream()
                    .distinct().sorted(Comparator.comparing(GraphLegend::getName)).collect(Collectors.toList());
            //graphLegends.forEach(v->System.out.println(v.toString()));

            //=== 3.범례별 평균값구해서 datasets 에 넣기=======================================




            //범례를 루프돌면서 실데이터를 graphDatas추가
            for(GraphLegend graphLegend: graphLegends){


                String label = graphLegend.getName();
                List<GraphValueString> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();

                for (String category : categories) {
                    List<CarbonationDepthDto> valuelist = ltdDatas.stream()
                            .filter(v -> String.format("%.2f", v.getSaltRate()).equals(label))
                            .filter(v -> v.getElement1().equals(category))
                            .collect(Collectors.toList());

                    Double avgvalue = valuelist.stream()
                            .collect(Collectors.averagingDouble(CarbonationDepthDto::getValue));

                    GraphValueString graphValue = new GraphValueString(category, CommonUtils.GetZeroToNullString(String.format("%.1f", avgvalue)));
                    graphValues.add(graphValue);


                }

                graphValues.sort(Comparator.comparing(GraphValueString::getIndex));

                inspectdata.add(label+"%");
                graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                graphDataColumns.add(inspectdata);

            }
        }

        //없는옵션이면
        else{
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E008.getCode(),ResponseErrorCode.E008.getDesc()
                    ,null,null));
        }

        data.put("title",periodname+"차 " + treatmentcondition.getGraphTitle());
        data.put("unit", MeasurementItemCode.M001.getDesc()+"(" + MeasurementItemCode.M001.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
        data.put("categories",categories);

        log.info(seriescode.getDesc() + " 탄산화갚이 Bar 그래프 데이터 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    //==========================================================================================================
    //시리즈4 부순골재/재생모래 Bar 그래프
    private ResponseEntity<Map<String,Object>> bar_S004(String periodname,TreatmentCondition treatmentcondition,SeriesCode seriescode ,HttpServletRequest request){



        String currentuserid = request.getHeader("insert_id");
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 탄산화깊이 Bar 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid + "'");

        List<CarbonationDepthDto> ltdDatasRaw = carbonationDepthService.findBySeriesCode(seriescode);
        // 해당주기데이터만 취합
        List<CarbonationDepthDto> ltdDatas = ltdDatasRaw.stream()
                .filter(v -> v.getPeriodName().equals(periodname)).collect(Collectors.toList());


        List<String> categories = new ArrayList<>();
        List<List<String>> graphDataColumns = new ArrayList<>();

        //======================T40204 노출환경에따른 압축강도
        if(treatmentcondition.equals(TreatmentCondition.T40204)) {
            //=== 1. X라벨 저장 ========================================================

            List<GraphXLabel> graphyXLabelsrow = new ArrayList<>();

            ltdDatas.forEach(v -> {
                GraphXLabel graphyXLabel = new GraphXLabel(1, v.getEnvironment());
                graphyXLabelsrow.add(graphyXLabel);

            });

            List<GraphXLabel> graphyXLabels = new ArrayList<>();
            Set<GraphXLabel> uniqueValues = new HashSet<>();
            for (GraphXLabel graphXLabel : graphyXLabelsrow) {
                if (uniqueValues.add(graphXLabel)) {
                    graphyXLabels.add(graphXLabel);
                }
            }
            graphyXLabels.sort(Comparator.comparing(GraphXLabel::getValue));

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
            for(GraphLegend graphLegend: graphLegends){


                String label = graphLegend.getName();
                List<GraphValueString> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                ltdDatas.stream()
                        .filter(v -> v.getElement1().equals(label))
                        .collect(Collectors.groupingBy(CarbonationDepthDto::getEnvironment
                                , Collectors.averagingDouble(CarbonationDepthDto::getValue)
                        )).forEach((k, v) -> {

                    GraphValueString graphValue = new GraphValueString(k, String.format("%.1f", v));
                    graphValues.add(graphValue);

                });

                graphValues.sort(Comparator.comparing(GraphValueString::getIndex));

                inspectdata.add(label);
                graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                graphDataColumns.add(inspectdata);

            }
        }else //======================T40205 초기염분함유량 에따른 압축강도
            if(treatmentcondition.equals(TreatmentCondition.T40205)) {
                //=== 1. X라벨 저장 ========================================================

                List<GraphXLabel> graphyXLabelsrow = new ArrayList<>();

                ltdDatas.forEach(v -> {
                    GraphXLabel graphyXLabel = new GraphXLabel(1, Double.toString(v.getSaltRate()));
                    graphyXLabelsrow.add(graphyXLabel);

                });

                List<GraphXLabel> graphyXLabels = new ArrayList<>();
                Set<GraphXLabel> uniqueValues = new HashSet<>();
                for (GraphXLabel graphXLabel : graphyXLabelsrow) {
                    if (uniqueValues.add(graphXLabel)) {
                        graphyXLabels.add(graphXLabel);
                    }
                }
                graphyXLabels.sort(Comparator.comparing(GraphXLabel::getValue));

                graphyXLabels.sort(Comparator.comparing(GraphXLabel::getName));
                graphyXLabels.forEach(v -> categories.add(v.getName() + '%'));


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
                for(GraphLegend graphLegend: graphLegends){


                    String label = graphLegend.getName();
                    List<GraphValueString> graphValues = new ArrayList<>();
                    List<String> inspectdata = new ArrayList<>();


                    ltdDatas.stream()
                            .filter(v -> v.getElement1().equals(label))
                            .collect(Collectors.groupingBy(CarbonationDepthDto::getSaltRate
                                    , Collectors.averagingDouble(CarbonationDepthDto::getValue)
                            )).forEach((k, v) -> {

                        GraphValueString graphValue = new GraphValueString(Double.toString(k), String.format("%.1f", v));
                        graphValues.add(graphValue);

                    });

                    graphValues.sort(Comparator.comparing(GraphValueString::getIndex));

                    inspectdata.add(label);
                    graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

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

        log.info(seriescode.getDesc() + " 탄산화갚이 Bar 그래프 데이터 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }



    //==========================================================================================================
    //시리즈5 고로슬래그/플라이애쉬 Bar 그래프
    private ResponseEntity<Map<String,Object>> bar_S005(String periodname,TreatmentCondition treatmentcondition,SeriesCode seriescode ,HttpServletRequest request){



        String currentuserid = request.getHeader("insert_id");
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 탄산화깊이 Bar 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid + "'");

        List<CarbonationDepthDto> ltdDatasRaw = carbonationDepthService.findBySeriesCode(seriescode);
        // 해당주기데이터만 취합
        List<CarbonationDepthDto> ltdDatas = ltdDatasRaw.stream()
                .filter(v -> v.getPeriodName().equals(periodname)).collect(Collectors.toList());


        List<String> categories = new ArrayList<>();
        List<List<String>> graphDataColumns = new ArrayList<>();

        //======================T50204 노출환경에따른 압축강도
        if(treatmentcondition.equals(TreatmentCondition.T50204)) {
            //=== 1. X라벨 저장 ========================================================

            List<GraphXLabel> graphyXLabelsrow = new ArrayList<>();

            ltdDatas.forEach(v -> {
                GraphXLabel graphyXLabel = new GraphXLabel(1, v.getEnvironment());
                graphyXLabelsrow.add(graphyXLabel);

            });

            List<GraphXLabel> graphyXLabels = new ArrayList<>();
            Set<GraphXLabel> uniqueValues = new HashSet<>();
            for (GraphXLabel graphXLabel : graphyXLabelsrow) {
                if (uniqueValues.add(graphXLabel)) {
                    graphyXLabels.add(graphXLabel);
                }
            }
            graphyXLabels.sort(Comparator.comparing(GraphXLabel::getValue));

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
            for(GraphLegend graphLegend: graphLegends){


                String label = graphLegend.getName();
                List<GraphValueString> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();


                ltdDatas.stream()
                        .filter(v -> v.getElement1().equals(label))
                        .collect(Collectors.groupingBy(CarbonationDepthDto::getEnvironment
                                , Collectors.averagingDouble(CarbonationDepthDto::getValue)
                        )).forEach((k, v) -> {

                    GraphValueString graphValue = new GraphValueString(k, String.format("%.1f", v));
                    graphValues.add(graphValue);

                });

                graphValues.sort(Comparator.comparing(GraphValueString::getIndex));

                inspectdata.add(label);
                graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

                graphDataColumns.add(inspectdata);

            }
        }else //======================T50205 초기염분함유량 에따른 압축강도
            if(treatmentcondition.equals(TreatmentCondition.T50205)) {
                //=== 1. X라벨 저장 ========================================================

                List<GraphXLabel> graphyXLabelsrow = new ArrayList<>();

                ltdDatas.forEach(v -> {
                    GraphXLabel graphyXLabel = new GraphXLabel(1, Double.toString(v.getSaltRate()));
                    graphyXLabelsrow.add(graphyXLabel);

                });

                List<GraphXLabel> graphyXLabels = new ArrayList<>();
                Set<GraphXLabel> uniqueValues = new HashSet<>();
                for (GraphXLabel graphXLabel : graphyXLabelsrow) {
                    if (uniqueValues.add(graphXLabel)) {
                        graphyXLabels.add(graphXLabel);
                    }
                }
                graphyXLabels.sort(Comparator.comparing(GraphXLabel::getValue));

                graphyXLabels.sort(Comparator.comparing(GraphXLabel::getName));
                graphyXLabels.forEach(v -> categories.add(v.getName() + '%'));


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
                for(GraphLegend graphLegend: graphLegends){


                    String label = graphLegend.getName();
                    List<GraphValueString> graphValues = new ArrayList<>();
                    List<String> inspectdata = new ArrayList<>();


                    ltdDatas.stream()
                            .filter(v -> v.getElement1().equals(label))
                            .collect(Collectors.groupingBy(CarbonationDepthDto::getSaltRate
                                    , Collectors.averagingDouble(CarbonationDepthDto::getValue)
                            )).forEach((k, v) -> {

                        GraphValueString graphValue = new GraphValueString(Double.toString(k), String.format("%.1f", v));
                        graphValues.add(graphValue);

                    });

                    graphValues.sort(Comparator.comparing(GraphValueString::getIndex));

                    inspectdata.add(label);
                    graphValues.forEach(v -> inspectdata.add(CommonUtils.GetZeroToNullString(v.getValue())));

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

        log.info(seriescode.getDesc() + " 탄산화갚이 Bar 그래프 데이터 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    //==========================================================================================================
    //시리즈6 실리카흄 Bar 그래프
    private ResponseEntity<Map<String,Object>> bar_S006(String periodname,TreatmentCondition treatmentcondition,SeriesCode seriescode ,HttpServletRequest request){

        String currentuserid = request.getHeader("insert_id");
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 탄산화깊이 Bar 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid + "'");

        List<CarbonationDepthDto> ltdDatasRaw = carbonationDepthService.findBySeriesCode(seriescode);
        // 해당주기데이터만 취합
        List<CarbonationDepthDto> ltdDatas = ltdDatasRaw.stream()
                .filter(v -> v.getPeriodName().equals(periodname)).collect(Collectors.toList());


        List<String> categories = new ArrayList<>();
        List<List<String>> graphDataColumns = new ArrayList<>();

        //======================T60203 노출환경에따른 압축강도
        if(treatmentcondition.equals(TreatmentCondition.T60203)) {
            //=== 1. X라벨 저장 ========================================================

            List<GraphXLabel> graphyXLabelsrow = new ArrayList<>();

            ltdDatas.forEach(v -> {
                GraphXLabel graphyXLabel = new GraphXLabel(1, v.getEnvironment());
                graphyXLabelsrow.add(graphyXLabel);

            });

            List<GraphXLabel> graphyXLabels = new ArrayList<>();
            Set<GraphXLabel> uniqueValues = new HashSet<>();
            for (GraphXLabel graphXLabel : graphyXLabelsrow) {
                if (uniqueValues.add(graphXLabel)) {
                    graphyXLabels.add(graphXLabel);
                }
            }
            graphyXLabels.sort(Comparator.comparing(GraphXLabel::getValue));

            graphyXLabels.sort(Comparator.comparing(GraphXLabel::getName));
            graphyXLabels.forEach(v -> categories.add(v.getName()));


            //=== 2. 범례 저장 (고로슬래쉬/플라이애쉬) ==========================================
            List<GraphLegend> graphLegendsrow = new ArrayList<>();
            ltdDatas.forEach(v -> {
                GraphLegend graphLegend = new GraphLegend(1, String.format("%.2f",v.getSaltRate()));
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


                ltdDatas.stream()
                        .filter(v -> String.format("%.2f",v.getSaltRate()).equals(label))
                        .collect(Collectors.groupingBy(CarbonationDepthDto::getEnvironment
                                , Collectors.averagingDouble(CarbonationDepthDto::getValue)
                        )).forEach((k, v) -> {

                    GraphValueString graphValue = new GraphValueString(k, String.format("%.1f", v));
                    graphValues.add(graphValue);

                });

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
        data.put("unit", MeasurementItemCode.M001.getDesc()+"(" + MeasurementItemCode.M001.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
        data.put("categories",categories);

        log.info(seriescode.getDesc() + " 탄산화갚이 Bar 그래프 데이터 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }
    //==========================================================================================================
    //시리즈7 염분함유 시험체 Bar 그래프
    private ResponseEntity<Map<String,Object>> bar_S007(String periodname,TreatmentCondition treatmentcondition,SeriesCode seriescode ,HttpServletRequest request){

        String currentuserid = request.getHeader("insert_id");
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        log.info(seriescode.getDesc() + " 탄산화깊이 Bar 그래프 데이터 조회 시작  / 조회자 :'" + currentuserid + "'");

        List<CarbonationDepthDto> ltdDatasRaw = carbonationDepthService.findBySeriesCode(seriescode);
        // 해당주기데이터만 취합
        List<CarbonationDepthDto> ltdDatas = ltdDatasRaw.stream()
                .filter(v -> v.getPeriodName().equals(periodname)).collect(Collectors.toList());


        List<String> categories = new ArrayList<>();
        List<List<String>> graphDataColumns = new ArrayList<>();

        //======================T70203 W/C 탄산화깊이
        if(treatmentcondition.equals(TreatmentCondition.T70203)) {
            //=== 1. X라벨 저장 ========================================================

            List<GraphXLabel> graphyXLabelsrow = new ArrayList<>();

            ltdDatas.forEach(v -> {
                GraphXLabel graphyXLabel = new GraphXLabel(1, v.getElement1());
                graphyXLabelsrow.add(graphyXLabel);

            });

            List<GraphXLabel> graphyXLabels = new ArrayList<>();
            Set<GraphXLabel> uniqueValues = new HashSet<>();
            for (GraphXLabel graphXLabel : graphyXLabelsrow) {
                if (uniqueValues.add(graphXLabel)) {
                    graphyXLabels.add(graphXLabel);
                }
            }
            graphyXLabels.sort(Comparator.comparing(GraphXLabel::getValue));

            graphyXLabels.sort(Comparator.comparing(GraphXLabel::getName));
            graphyXLabels.forEach(v -> categories.add(v.getName()));


            //=== 2. 범례 저장 (고로슬래쉬/플라이애쉬) ==========================================
            List<GraphLegend> graphLegendsrow = new ArrayList<>();
            ltdDatas.forEach(v -> {
                GraphLegend graphLegend = new GraphLegend(1, String.format("%.2f",v.getSaltRate()));
                graphLegendsrow.add(graphLegend);
            });

            List<GraphLegend> graphLegends = graphLegendsrow.stream()
                    .distinct().sorted(Comparator.comparing(GraphLegend::getName)).collect(Collectors.toList());
            //graphLegends.forEach(v->System.out.println(v.toString()));

            //=== 3.범례별 평균값구해서 datasets 에 넣기=======================================




            //범례를 루프돌면서 실데이터를 graphDatas추가
            for(GraphLegend graphLegend: graphLegends){


                String label = graphLegend.getName();
                List<GraphValueString> graphValues = new ArrayList<>();
                List<String> inspectdata = new ArrayList<>();

                for (String category : categories) {
                    List<CarbonationDepthDto> valuelist = ltdDatas.stream()
                            .filter(v -> String.format("%.2f", v.getSaltRate()).equals(label))
                            .filter(v -> v.getElement1().equals(category))
                            .collect(Collectors.toList());

                    Double avgvalue = valuelist.stream()
                            .collect(Collectors.averagingDouble(CarbonationDepthDto::getValue));

                    GraphValueString graphValue = new GraphValueString(category, CommonUtils.GetZeroToNullString(String.format("%.1f", avgvalue)));
                    graphValues.add(graphValue);


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
        data.put("unit", MeasurementItemCode.M001.getDesc()+"(" + MeasurementItemCode.M001.getUnit()+ ")");
        data.put("datacolumns",graphDataColumns);
        data.put("categories",categories);

        log.info(seriescode.getDesc() + " 탄산화갚이 Bar 그래프 데이터 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }


}
