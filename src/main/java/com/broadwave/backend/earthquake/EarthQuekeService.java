package com.broadwave.backend.earthquake;

import com.broadwave.backend.common.AjaxResponse;
import com.broadwave.backend.common.ResponseErrorCode;
import com.broadwave.backend.earthquake.EarthQuakeDtos.EarthQuakeDto;
import com.broadwave.backend.earthquake.EarthQuakeDtos.EarthQuakeListDto;
import com.broadwave.backend.earthquake.EarthQuakeDtos.EarthQuakeMapperDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Minkyu
 * Date : 2021-06-10
 * Time :
 * Remark : NewDeal EarthQueke Service
*/
@Slf4j
@Service
public class EarthQuekeService {

    private final ModelMapper modelMapper;
    private final EarthQuakeRepository earthQuakeRepository;

    @Autowired
    public EarthQuekeService(ModelMapper modelMapper, EarthQuakeRepository earthQuakeRepository){
        this.modelMapper = modelMapper;
        this.earthQuakeRepository = earthQuakeRepository;
    }

    // NEWDEAL 내진성능 추정서비스 단일 교량 수정 API
    public ResponseEntity<Map<String, Object>> update(EarthQuakeMapperDto earthQuakeMapperDto, HttpServletRequest request) {
        log.info("update 호출");

        String insert_id = request.getHeader("insert_id");
        log.info("insert_id : "+insert_id);

        AjaxResponse res = new AjaxResponse();

        Optional<EarthQuake> optionalEarthQuake = earthQuakeRepository.findByEarthQuake(earthQuakeMapperDto.getEqBridge());
        if(optionalEarthQuake.isPresent()){
            return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE032.getCode(), ResponseErrorCode.NDE032.getDesc()+"교량명 입니다.", ResponseErrorCode.NDE033.getCode(), "수정후 "+ResponseErrorCode.NDE033.getDesc()));
        }else{
            Optional<EarthQuake> optionalEarthQuake2 = earthQuakeRepository.findById(earthQuakeMapperDto.getId());
            if(optionalEarthQuake2.isPresent()){
                EarthQuake earthQuake = modelMapper.map(earthQuakeMapperDto, EarthQuake.class);
                earthQuake.setId(optionalEarthQuake2.get().getId());
                earthQuake.setInsert_id(optionalEarthQuake2.get().getInsert_id());
                earthQuake.setInsertDateTime(optionalEarthQuake2.get().getInsertDateTime());
                earthQuake.setModify_id(insert_id);
                earthQuake.setModifyDateTime(LocalDateTime.now());

                String eqBridgeClassificationStr;
                if(earthQuake.getEqLength().equals("150m이상")){
                    eqBridgeClassificationStr = "HWB1";
                }else {
                    if (earthQuake.getEqConfiguration().equals("단일경간")) {
                        eqBridgeClassificationStr = "HWB3";
                    } else {
                        if (earthQuake.getEqPillar().equals("다중기둥") && earthQuake.getEqGirder().equals("콘크리트")) {
                            eqBridgeClassificationStr = "HWB5";
                        } else if (earthQuake.getEqPillar().equals("단일기둥") && earthQuake.getEqDivision().equals("연속교") && earthQuake.getEqGirder().equals("콘크리트")) {
                            eqBridgeClassificationStr = "HWB8";
                        } else if (earthQuake.getEqDivision().equals("연속교") && earthQuake.getEqGirder().equals("콘크리트")) {
                            eqBridgeClassificationStr = "HWB10";
                        } else if (earthQuake.getEqPillar().equals("다중기둥") && earthQuake.getEqGirder().equals("강재")) {
                            eqBridgeClassificationStr = "HWB12";
                        } else if (earthQuake.getEqDivision().equals("연속교") && earthQuake.getEqGirder().equals("강재")) {
                            eqBridgeClassificationStr = "HWB15";
                        } else if (earthQuake.getEqPillar().equals("다중기둥") && earthQuake.getEqGirder().equals("PSC")) {
                            eqBridgeClassificationStr = "HWB17";
                        } else if (earthQuake.getEqPillar().equals("단일기둥") && earthQuake.getEqGirder().equals("PSC")) {
                            eqBridgeClassificationStr = "HWB20";
                        } else {
                            eqBridgeClassificationStr = "HWB28";
                        }
                    }
                }
                earthQuake.setEqBridgeClassification(eqBridgeClassificationStr);

                earthQuakeRepository.save(earthQuake);
            }else{
                return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE006.getCode(), ResponseErrorCode.NDE006.getDesc(), ResponseErrorCode.NDE031.getCode(), ResponseErrorCode.NDE031.getDesc()));
            }
        }

        return ResponseEntity.ok(res.success());
    }

    // NEWDEAL 내진성능 추정서비스 다중 교량 등록(엑셀 파일업로드 형식)
    public ResponseEntity<Map<String, Object>> filesave(MultipartFile excelfile, HttpServletRequest request) throws IOException {
        log.info("multisave 호출");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        String insert_id = request.getHeader("insert_id");
        log.info("insert_id : "+insert_id);

        String extension = FilenameUtils.getExtension(excelfile.getOriginalFilename());
        log.info("확장자 : " + extension);

        // 확장자가 엑셀이 맞는지 확인
        Workbook workbook;
        assert extension != null;
        if (extension.equals("xlsx")) {
            workbook = new XSSFWorkbook(excelfile.getInputStream());  // -> .xlsx
        } else {
            workbook = new HSSFWorkbook(excelfile.getInputStream());  // -> .xls
        }

        Sheet worksheet = workbook.getSheetAt(0); // 첫번째 시트

        // 제공한 양식 엑셀파일이 맞는지 확인 (첫번째시트)
        try {
            Row rowCheck;
            Object cellDataCheck;

            // 양식파일 검증
            log.info("검증 시작");
            rowCheck = worksheet.getRow(2);
            cellDataCheck = rowCheck.getCell(0);
//            log.info("검증 1 : " + cellDataCheck.toString());
            if(!cellDataCheck.toString().equals("교량명")){
                return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE012.getCode(), ResponseErrorCode.NDE012.getDesc(), null, null));
            }
            rowCheck = worksheet.getRow(2);
            cellDataCheck = rowCheck.getCell(1);
//            log.info("검증 2 : " + cellDataCheck.toString());
            if(!cellDataCheck.toString().equals("지역")){
                return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE012.getCode(), ResponseErrorCode.NDE012.getDesc(), null, null));
            }
            rowCheck = worksheet.getRow(2);
            cellDataCheck = rowCheck.getCell(2);
//            log.info("검증 3 : " + cellDataCheck.toString());
            if(!cellDataCheck.toString().equals("내진등급")){
                return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE012.getCode(), ResponseErrorCode.NDE012.getDesc(), null, null));
            }
            rowCheck = worksheet.getRow(2);
            cellDataCheck = rowCheck.getCell(3);
//            log.info("검증 4 : " + cellDataCheck.toString());
            if(!cellDataCheck.toString().equals("주경간장")){
                return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE012.getCode(), ResponseErrorCode.NDE012.getDesc(), null, null));
            }
            rowCheck = worksheet.getRow(2);
            cellDataCheck = rowCheck.getCell(4);
//            log.info("검증 5 : " + cellDataCheck.toString());
            if(!cellDataCheck.toString().equals("경간 구성")){
                return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE012.getCode(), ResponseErrorCode.NDE012.getDesc(), null, null));
            }
            rowCheck = worksheet.getRow(2);
            cellDataCheck = rowCheck.getCell(5);
//            log.info("검증 6 : " + cellDataCheck.toString());
            if(!cellDataCheck.toString().equals("교각기둥 구성")){
                return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE012.getCode(), ResponseErrorCode.NDE012.getDesc(), null, null));
            }
            rowCheck = worksheet.getRow(2);
            cellDataCheck = rowCheck.getCell(6);
//            log.info("검증 7 : " + cellDataCheck.toString());
            if(!cellDataCheck.toString().equals("주형 구분")){
                return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE012.getCode(), ResponseErrorCode.NDE012.getDesc(), null, null));
            }
            rowCheck = worksheet.getRow(2);
            cellDataCheck = rowCheck.getCell(7);
//            log.info("검증 8 : " + cellDataCheck.toString());
            if(!cellDataCheck.toString().equals("거더형식 구분")){
                return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE012.getCode(), ResponseErrorCode.NDE012.getDesc(), null, null));
            }
            log.info("검증 완료");
            log.info("");
        } catch (NullPointerException e) {
            return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE012.getCode(), ResponseErrorCode.NDE012.getDesc(), null, null));
        }

        // 데이터 총 길이(입력된 교량 수)
        int dataLength =  worksheet.getPhysicalNumberOfRows();
        log.info("dataLength : " + dataLength);
        log.info("");

        ArrayList<String> returnBridgeList = new ArrayList<>(); // 교량명은 있지만 속성값중 하나라도 비어있는 교량
        ArrayList<String> alreadyBridgeList = new ArrayList<>(); // 이미 등록되어있는 교량
        ArrayList<Object> excelList = new ArrayList<>(); // 저장할 객체리스트
        List<EarthQuake> earthQuakeList = new ArrayList<>();

        for (int j = 3; j < dataLength; j++) {

            int cnt = 0;
            int check = 0;

//            log.info( j+"번째 행 호출");
            EarthQuake earthQuake = new EarthQuake();
            for (int i = 0; i < 8; i++) {
                Row row = worksheet.getRow(j);
                Cell cellData = row.getCell(i);
                CellType ct = cellData.getCellType();
                if(i == 0 && ct != CellType.BLANK){
                    check = 1;
                    excelList.add(cellData);
                    cnt ++;
                }else{
                    if(ct != CellType.BLANK){
                        excelList.add(cellData);
                        cnt ++;
                    }
                }
//                log.info( i +" 셀타입 : "+ct);
//                log.info("카운트 : "+cnt);
            }

            if(check == 1 && cnt != 8 ){
                log.info("비어있는 값이 존재합니다. 교량명 : "+excelList.get(0).toString());
                returnBridgeList.add(excelList.get(0).toString());
            }

            if(cnt == 8) {
//                log.info("저장을 시작합니다.");

                Optional<EarthQuake> optionalEarthQuake = earthQuakeRepository.findByEarthQuake(excelList.get(0).toString());
                if (optionalEarthQuake.isEmpty()) {
                    String eqBridgeClassificationStr;
                    if(excelList.get(3).toString().equals("150m 이상")){
                        eqBridgeClassificationStr = "HWB1";
                    }
                    else{
                        if(excelList.get(4).toString().equals("단일 경간")){
                            eqBridgeClassificationStr = "HWB3";
                        }else{
                            if(excelList.get(5).toString().equals("다중 기둥") && excelList.get(7).toString().equals("콘크리트")){
                                eqBridgeClassificationStr = "HWB5";
                            }else if(excelList.get(5).toString().equals("단일 기둥") && excelList.get(6).toString().equals("연속교") && excelList.get(7).toString().equals("콘크리트")){
                                eqBridgeClassificationStr = "HWB8";
                            }else if(excelList.get(6).toString().equals("연속교") && excelList.get(7).toString().equals("콘크리트")){
                                eqBridgeClassificationStr = "HWB10";
                            }else if(excelList.get(5).toString().equals("다중 기둥") && excelList.get(7).toString().equals("강재")){
                                eqBridgeClassificationStr = "HWB12";
                            }else if(excelList.get(6).toString().equals("연속교") && excelList.get(7).toString().equals("강재")){
                                eqBridgeClassificationStr = "HWB15";
                            }else if(excelList.get(5).toString().equals("다중 기둥") && excelList.get(7).toString().equals("PSC")){
                                eqBridgeClassificationStr = "HWB17";
                            }else if(excelList.get(5).toString().equals("단일 기둥") && excelList.get(7).toString().equals("PSC")){
                                eqBridgeClassificationStr = "HWB20";
                            }else{
                                eqBridgeClassificationStr = "HWB28";
                            }
                        }
                    }

                    earthQuake.setEqBridge(excelList.get(0).toString());  // 교량명
                    earthQuake.setEqLocation(excelList.get(1).toString());  // 대상지역
                    if(excelList.get(2).toString().equals("내진Ⅰ등급")){
                        earthQuake.setEqRank("내진1등급");  // 내진1등급 - 내진등급
                    }else if(excelList.get(2).toString().equals("내진Ⅱ등급")){
                        earthQuake.setEqRank("내진2등급");  // 내진2등급 - 내진등급
                    }else{
                        earthQuake.setEqRank("내진특등급");  // 내진특등급 - 내진등급
                    }
                    earthQuake.setEqLength(excelList.get(3).toString().replaceAll(" ", ""));  // 주 경간장
                    earthQuake.setEqConfiguration(excelList.get(4).toString().replaceAll(" ", ""));  // 경간 구성
                    earthQuake.setEqPillar(excelList.get(5).toString().replaceAll(" ", ""));  // 교각기둥 구성
                    earthQuake.setEqDivision(excelList.get(6).toString());  // 주형 구분
                    earthQuake.setEqGirder(excelList.get(7).toString());  // 거더형식
                    earthQuake.setEqBridgeClassification(eqBridgeClassificationStr);  // 교량분류
                    earthQuake.setInsert_id(insert_id);
                    earthQuake.setInsertDateTime(LocalDateTime.now());
                    earthQuakeList.add(earthQuake);

                } else {
                    alreadyBridgeList.add(excelList.get(0).toString());
                }
            }

            excelList.clear();

//            log.info("-- 끝 --");
//            log.info("");

            if(cnt == 0){
//                log.info("-- 반복문종료 --");
                break;
            }
        }

        log.info("저장할수 없는 교량들 : "+returnBridgeList);
        log.info("이미 등록되어 있는 교량들 : "+alreadyBridgeList);

        log.info("earthQuakeList : "+earthQuakeList);
        earthQuakeRepository.saveAll(earthQuakeList);

        data.put("returnBridgeList",returnBridgeList);
        data.put("alreadyBridgeList",alreadyBridgeList);
        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    // NEWDEAL 내진성능 추정서비스 교량 리스트 호출 API
    public ResponseEntity<Map<String, Object>> list(String eqBridge) {
        log.info("list 호출");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        List<EarthQuakeListDto> earthQuakeDtoList = earthQuakeRepository.findByEarthQuakeList(eqBridge);
        data.put("gridListData",earthQuakeDtoList);

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    // NEWDEAL 내진성능 추정 서비스 호출 API + 데이터 백앤드 계산 API
    public ResponseEntity<Map<String, Object>> earthQuekePerformance(String eqBridge) {
        log.info("earthQuekePerformance 호출");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        EarthQuakeDto earthQuakeDto = earthQuakeRepository.findByEqBridge(eqBridge);
        if(earthQuakeDto == null){
            return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE006.getCode(), ResponseErrorCode.NDE006.getDesc(), ResponseErrorCode.NDE031.getCode(), ResponseErrorCode.NDE031.getDesc()));
        }else{
            String eqBridgeClassification = earthQuakeDto.getEqBridgeClassification();
            log.info("eqBridgeClassification : "+eqBridgeClassification);

            double earth_quake; // 지진구역계수
            double earth_replayCycle; // 재현주기
            double earth_risk; // 위험도계수
            double earth_design; // 설계 지반가속도

            if(earthQuakeDto.getEqLocation().equals("강원북부") || earthQuakeDto.getEqLocation().equals("제주")){
                earth_quake = 0.07;
            }else{
                earth_quake = 0.11;
            }
            log.info("지진구역계수 : "+earth_quake);

            if(earthQuakeDto.getEqRank().equals("내진특등급")){
                earth_replayCycle = 1000.0;
                earth_risk = 1.4;
            }else if(earthQuakeDto.getEqRank().equals("내진1등급")){
                earth_replayCycle = 500.0;
                earth_risk = 1.0;
            }else{
                earth_replayCycle = 200.0;
                earth_risk = 0.73;
            }
            log.info("재현주기 : "+earth_replayCycle);
            log.info("위험도계수 : "+earth_risk);

            earth_design = earth_quake * earth_risk;
            log.info("설계 지반가속도 : "+earth_design);

            double earth_scale; // 지진규모
            if(earth_design <= 0.051){
                earth_scale = 5.4;
            }else if(earth_design <= 0.07){
                earth_scale = 5.7;
            }else if(earth_design <= 0.08){
                earth_scale = 5.8;
            }else if(earth_design <= 0.098){
                earth_scale = 6.0;
            }else if(earth_design <= 0.11){
                earth_scale = 6.1;
            }else if(earth_design <= 0.154){
                earth_scale = 6.4;
            }else{
                earth_scale = 0;
            }

            String result = fuctionEarth(earthQuakeDto.getEqBridgeClassification(), earth_design);

            data.put("result",result); // result 1 -> 충족, 2 -> 미충족
            data.put("earthDesign",Math.floor(earth_design*100)/100+"g");
            data.put("earthScale",earth_scale);
            data.put("earthQuakeDto",earthQuakeDto);
        }

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    // DS0 계산함수( return 값 : 1. 충족, 2. 미충족)
    public String fuctionEarth(String eqBridgeClassification, double earth_design){
        String result;

        log.info("교량분류 : "+eqBridgeClassification);
        log.info("설계 지반가속도 : "+earth_design);
        switch (eqBridgeClassification) {
            case "HWB1":
            case "HWB3":
            case "HWB8":
            case "HWB10":
            case "HWB15":
            case "HWB20":
                if (earth_design > 0.154) {
                    result = "2";
                } else {
                    result = "1";
                }
                break;
            case "HWB5":
            case "HWB12":
            case "HWB17":
                if (earth_design >= 0.154) {
                    result = "2";
                } else {
                    result = "1";
                }
                break;
            default:
                result = "2";
                break;
        }

        return result;
    }


}
