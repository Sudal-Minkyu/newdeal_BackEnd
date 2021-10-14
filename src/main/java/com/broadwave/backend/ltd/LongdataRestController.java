package com.broadwave.backend.ltd;

import com.broadwave.backend.bscodes.CommonCode;
import com.broadwave.backend.bscodes.MeasurementItemCode;
import com.broadwave.backend.bscodes.SeriesCode;
import com.broadwave.backend.bscodes.TreatmentCondition;
import com.broadwave.backend.common.AjaxResponse;
import com.broadwave.backend.common.CommonUtils;
import com.broadwave.backend.excel.ErrorExcelDto;
import com.broadwave.backend.ltd.data.area.CorrosionAreaDto;
import com.broadwave.backend.ltd.data.area.CorrosionAreaService;
import com.broadwave.backend.ltd.data.carbonation.CarbonationDepthDto;
import com.broadwave.backend.ltd.data.carbonation.CarbonationDepthService;
import com.broadwave.backend.ltd.data.chloride.PenetratedChlorideDto;
import com.broadwave.backend.ltd.data.chloride.PenetratedChlorideService;
import com.broadwave.backend.ltd.data.compression.CompressiveStrengthDto;
import com.broadwave.backend.ltd.data.compression.CompressiveStrengthService;
import com.broadwave.backend.ltd.data.deformation.DeformationRateDto;
import com.broadwave.backend.ltd.data.deformation.DeformationRateService;
import com.broadwave.backend.ltd.data.rebar.RebarCorrosionDto;
import com.broadwave.backend.ltd.data.rebar.RebarCorrosionService;
import com.broadwave.backend.ltd.data.ultrasonic.UltrasonicSpeedDto;
import com.broadwave.backend.ltd.data.ultrasonic.UltrasonicSpeedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author InSeok
 * Date : 2019-05-03
 * Time : 14:24
 * Remark :
 */
@Slf4j
@RestController
@RequestMapping("/api/ltd/common")
public class LongdataRestController {

    @Autowired
    CompressiveStrengthService compressiveStrengthService;
    @Autowired
    DeformationRateService deformationRateService;
    @Autowired
    CarbonationDepthService carbonationDepthService;
    @Autowired
    UltrasonicSpeedService ultrasonicSpeedService;
    @Autowired
    RebarCorrosionService rebarCorrosionService;
    @Autowired
    CorrosionAreaService corrosionAreaService;
    @Autowired
    PenetratedChlorideService penetratedChlorideService;

    //분석조건코드 반환
    @PostMapping("tccode")
    public ResponseEntity<Map<String,Object>> treatmentConditions(
                                    @RequestParam(value="seriescode", defaultValue="") String seriescode,
                                    @RequestParam(value="measurementitemcode", defaultValue="") String measurementitemcode,
                                    @RequestParam (value="periodtype", defaultValue="") String periodtype
                                    ){


        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        List<CommonCode> commonCodes = new ArrayList<>();

        Arrays.stream(TreatmentCondition.values())
                .filter(v -> v.getSeriesCode().equals(seriescode))
                .filter(v -> v.getMeasurementItemCode().equals(measurementitemcode))
                .filter(v -> v.getPeriodType().equals(periodtype))
                .forEach(v->{
                    CommonCode commonCode = new CommonCode(v.getCode(),v.getDesc());
                    commonCodes.add(commonCode);
                });

        data.put("datalist",commonCodes);

        return ResponseEntity.ok(res.dataSendSuccess(data));

    }


    //엑셀다운
    @RequestMapping(value="/graphrowdata.xls" , params = {"seriescode","measurementitemcode"})
    public String getExcelCompressiveStrength(Model model, HttpServletRequest request
            , @RequestParam(value = "seriescode") SeriesCode seriescode
            , @RequestParam(value = "measurementitemcode") MeasurementItemCode measurementitemcode

    ){

        //압축강도 측정데이터 다운로드
        if (measurementitemcode.equals(MeasurementItemCode.M001)) {
            List<String> header = Arrays.asList("환경", "항목1", "항목2", "주기", "초기염분함유량(%)"
                    , measurementitemcode.getDesc() + "(" + measurementitemcode.getUnit() + ")");


            List<CompressiveStrengthDto> downloadData = compressiveStrengthService.findBySeriesCode(seriescode);
            CommonUtils.exceldataModel(model, header, downloadData, "CompressiveStrength_ExcelDown");

            log.info("압축강도(" + seriescode.getDesc() + ") 측정 데이터 엑셀 다운로드 ( loginID : '" + CommonUtils.getCurrentuser(request) + "', IP : '" + CommonUtils.getIp(request) + "' )");
            return  "excelDownXls";
        }
        //탄산화깊이 측정데이터 다운로드
        else if (measurementitemcode.equals(MeasurementItemCode.M002)) {
            List<String> header = Arrays.asList("환경", "항목1", "항목2", "주기", "초기염분함유량(%)"
                    , measurementitemcode.getDesc() + "(" + measurementitemcode.getUnit() + ")");


            List<CarbonationDepthDto> downloadData = carbonationDepthService.findBySeriesCode(seriescode);
            CommonUtils.exceldataModel(model, header, downloadData, "CarconationDepth_ExcelDown");

            log.info("탄산화갚이(" + seriescode.getDesc() + ") 측정 데이터 엑셀 다운로드 ( loginID : '" + CommonUtils.getCurrentuser(request) + "', IP : '" + CommonUtils.getIp(request) + "' )");
            return  "excelDownXls";
        }

        //길이변형률 측정데이터 다운로드
        else if (measurementitemcode.equals(MeasurementItemCode.M003)) {
            List<String> header = Arrays.asList("환경", "항목1", "항목2","주기", "초기염분함유량(%)"
                    ,"1cm" + "(" + measurementitemcode.getUnit() + ")"
                    ,"2cm" + "(" + measurementitemcode.getUnit() + ")"
                    ,"3cm" + "(" + measurementitemcode.getUnit() + ")"
                    ,"4cm" + "(" + measurementitemcode.getUnit() + ")"
            );


            List<DeformationRateDto> downloadData = deformationRateService.findBySeriesCode(seriescode);
            CommonUtils.exceldataModel(model, header, downloadData, "DeformationTest_ExcelDown");

            log.info("길이변형률(" + seriescode.getDesc() + ") 측정 데이터 엑셀 다운로드 ( loginID : '" + CommonUtils.getCurrentuser(request) + "', IP : '" + CommonUtils.getIp(request) + "' )");
            return  "excelDownXls";
        }
        //초음파속도 측정데이터 다운로드
        else if (measurementitemcode.equals(MeasurementItemCode.M004)) {
            List<String> header = Arrays.asList("환경", "항목1", "항목2","주기", "초기염분함유량(%)"
                    ,"1cm" + "(" + measurementitemcode.getUnit() + ")"
                    ,"2cm" + "(" + measurementitemcode.getUnit() + ")"
                    ,"3cm" + "(" + measurementitemcode.getUnit() + ")"
                    ,"4cm" + "(" + measurementitemcode.getUnit() + ")"
            );


            List<UltrasonicSpeedDto> downloadData = ultrasonicSpeedService.findBySeriesCode(seriescode);
            CommonUtils.exceldataModel(model, header, downloadData, "UltrasonicSpeed_ExcelDown");

            log.info("초음파속도(" + seriescode.getDesc() + ") 측정 데이터 엑셀 다운로드 ( loginID : '" + CommonUtils.getCurrentuser(request) + "', IP : '" + CommonUtils.getIp(request) + "' )");
            return  "excelDownXls";
        }
        //철근부식량 측정데이터 다운로드
        else if (measurementitemcode.equals(MeasurementItemCode.M005)) {
            List<String> header = Arrays.asList("환경", "항목1", "항목2","주기", "초기염분함유량(%)"
                    ,"1cm" + "(" + measurementitemcode.getUnit() + ")"
                    ,"2cm" + "(" + measurementitemcode.getUnit() + ")"
                    ,"3cm" + "(" + measurementitemcode.getUnit() + ")"
                    ,"4cm" + "(" + measurementitemcode.getUnit() + ")"
            );


            List<RebarCorrosionDto> downloadData = rebarCorrosionService.findBySeriesCode(seriescode);
            CommonUtils.exceldataModel(model, header, downloadData, "RebarCorrosionWeight_ExcelDown");

            log.info("철근부식량(" + seriescode.getDesc() + ") 측정 데이터 엑셀 다운로드 ( loginID : '" + CommonUtils.getCurrentuser(request) + "', IP : '" + CommonUtils.getIp(request) + "' )");
            return  "excelDownXls";
        }
        //철근부식면적률 측정데이터 다운로드
        else if (measurementitemcode.equals(MeasurementItemCode.M006)) {
            List<String> header = Arrays.asList("환경", "항목1", "항목2","주기", "초기염분함유량(%)"
                    ,"1cm" + "(" + measurementitemcode.getUnit() + ")"
                    ,"2cm" + "(" + measurementitemcode.getUnit() + ")"
                    ,"3cm" + "(" + measurementitemcode.getUnit() + ")"
                    ,"4cm" + "(" + measurementitemcode.getUnit() + ")"
            );


            List<CorrosionAreaDto> downloadData = corrosionAreaService.findBySeriesCode(seriescode);
            CommonUtils.exceldataModel(model, header, downloadData, "RebarCorrosionArea_ExcelDown");

            log.info("철근부식면적률(" + seriescode.getDesc() + ") 측정 데이터 엑셀 다운로드 ( loginID : '" + CommonUtils.getCurrentuser(request) + "', IP : '" + CommonUtils.getIp(request) + "' )");
            return  "excelDownXls";
        }
        //염분함유량 측정데이터 다운로드
        else if (measurementitemcode.equals(MeasurementItemCode.M007)) {
            List<String> header = Arrays.asList("환경", "항목1", "항목2","주기", "초기염분함유량(%)"
                    ,"0-0.15cm" + "(" + measurementitemcode.getUnit() + ")"
                    ,"0.15-1.5cm" + "(" + measurementitemcode.getUnit() + ")"
                    ,"1.5-3cm" + "(" + measurementitemcode.getUnit() + ")"
                    ,"3-4.5cm" + "(" + measurementitemcode.getUnit() + ")"
                    ,"4.5-6cm" + "(" + measurementitemcode.getUnit() + ")"
                    ,"10cm" + "(" + measurementitemcode.getUnit() + ")"
            );


            List<PenetratedChlorideDto> downloadData = penetratedChlorideService.findBySeriesCode(seriescode);
            CommonUtils.exceldataModel(model, header, downloadData, "PenetratedChloride_ExcelDown");

            log.info("염분함유량(" + seriescode.getDesc() + ") 측정 데이터 엑셀 다운로드 ( loginID : '" + CommonUtils.getCurrentuser(request) + "', IP : '" + CommonUtils.getIp(request) + "' )");
            return  "excelDownXls";
        }


        else{

            List<String> header = Collections.singletonList("엑셀다운 에러메세지");

            ErrorExcelDto errorExcelDto = ErrorExcelDto.builder().msssage("해당 옵션으로 엑셀다운로드중 에러가 발생하였습니다.").build();
            List<ErrorExcelDto> downloadData = Collections.singletonList(errorExcelDto);
            CommonUtils.exceldataModel(model, header, downloadData, "Error_ExcelDown");

            log.info("압축강도(" + seriescode.getDesc() + ") 측정 데이터 엑셀 다운로드 ( loginID : '" + CommonUtils.getCurrentuser(request) + "', IP : '" + CommonUtils.getIp(request) + "' )");

            return "excelDownXls";
        }
    }



}
