package com.broadwave.backend.predict;

import com.broadwave.backend.common.AjaxResponse;
import com.broadwave.backend.python.PythonService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Minkyu
 * Date : 2022-07-25
 * Time :
 * Remark : NEWDEAL 인공지능기반 미래예측 서비스 (권태호박사)
 */
@Slf4j
@Service
public class PredictService {

    private final PythonService pythonService;

    @Autowired
    public PredictService(PythonService pythonService) {
        this.pythonService = pythonService;
    }

    // NEWDEAL 센서 전체리스트 호출
    public ResponseEntity<Map<String, Object>> sensorList() throws JSONException {

        log.info("sensorList 호출성공");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        JSONObject jsonObject = pythonService.sensorListGet("conn"); // 센서리스트 호출

        if(jsonObject == null){
            data.put("jsonObject", null);
        }else{
            data.put("jsonObject", jsonObject.get("body").toString());
        }

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    // NEWDEAL 교량리스트 호출
    public ResponseEntity<Map<String, Object>> bridge() throws JSONException {
        log.info("bridge 호출성공");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        JSONObject jsonObject = pythonService.sensorListGet("bridge"); // 센서리스트 호출

        if(jsonObject == null){
            data.put("jsonObject", null);
        }else{
            data.put("jsonObject", jsonObject.get("body").toString());
        }

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }


    // NEWDEAL 교량에 설치된 센서리스트 호출
    public ResponseEntity<Map<String, Object>> bridgeSensor(String bridgeId) throws JSONException {
        log.info("sensorList 호출성공");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        List<HashMap<String,Object>> listData = new ArrayList<>();
        HashMap<String,Object> infoData;
        List<HashMap<String,Object>> sublistData;
        HashMap<String,Object> subinfoData;

        List<String> sensorTypeList = new ArrayList<>();
        sensorTypeList.add("DISP"); // 횡변위
        sensorTypeList.add("CRACK"); // 균열
        sensorTypeList.add("ACC"); // 추후에 가속도(ACC) 추가예정
        sensorTypeList.add("TMP"); // 추후에 온/습도(TMP) 추가예정

        List<String> sensorTypeNameList = new ArrayList<>();
        sensorTypeNameList.add("횡변위");
        sensorTypeNameList.add("균열");
        sensorTypeNameList.add("가속도");
        sensorTypeNameList.add("온/습도");

        for(int i=0; i<2; i++){
            JSONObject jsonData = pythonService.bridgeSensor("bridgeSensor", bridgeId, sensorTypeList.get(i)); // 센서리스트 호출
            JSONArray jsonArray = jsonData.getJSONArray("body");

            if(jsonArray.length() != 0){

                infoData = new HashMap<>();
                sublistData = new ArrayList<>(); // SubList 초기화

                infoData.put("sensorType", sensorTypeList.get(i));
                infoData.put("sensorTypeName", sensorTypeNameList.get(i));
                for(int a = 0; a<jsonArray.length(); a++){
                    JSONArray jsonArrayObject = jsonArray.getJSONArray(a);
                    subinfoData = new HashMap<>();
                    subinfoData.put("deviceId", jsonArrayObject.get(0));
                    subinfoData.put("channelName",  jsonArrayObject.get(1));
                    subinfoData.put("sensorType",  jsonArrayObject.get(2));
                    subinfoData.put("channelNumber",  jsonArrayObject.get(3));
                    sublistData.add(subinfoData);
                }

                infoData.put("children", sublistData);
                listData.add(infoData);
            }
        }
        data.put("gridListData", listData);

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    // NEWDEAL 센서 전체데이터 호출
    public ResponseEntity<Map<String, Object>> sensorAllData(String sensor, String channelNumber) throws JSONException {
        log.info("sensorAllData 호출성공");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        JSONObject jsonObject = pythonService.sensorAllDataGet("sensorAllData", sensor, channelNumber); // 센서리스트 호출

        if(jsonObject == null){
            data.put("jsonObject", null);
        }else{
            data.put("jsonObject", jsonObject.get("body").toString());
        }

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    // NEWDEAL 센서 미래예측 데이터 호출
    public ResponseEntity<Map<String, Object>> futureSensorDataGet(String sensor, String time1, String time2, String days) throws JSONException {
        log.info("futureSensorDataGet 호출성공");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        JSONObject jsonObject = pythonService.futureSensorDataGet("sensorData", sensor, time1, time2, days); // 센서리스트 호출

        if(jsonObject == null){
            data.put("jsonObject1", null);
        }else{
            data.put("jsonObject1", jsonObject.get("futureData").toString());
            data.put("jsonObject2", jsonObject.get("date").toString());
        }

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    // NEWDEAL 센서 Step2 데이터 호출
    public ResponseEntity<Map<String, Object>> sensorStepDataGet(String sensor, String time1, String time2, String channelNumber, String warningVal, String dangerVal) throws JSONException {
        log.info("sensorStepDataGet 호출성공");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        JSONObject jsonObject = pythonService.sensorStepDataGet("sensorStep", sensor, time1, time2, channelNumber, warningVal, dangerVal); // 센서리스트 호출

        if(jsonObject == null){
            data.put("jsonObject", null);
        }else{
            data.put("line_x", jsonObject.get("line_x").toString());
            data.put("line_y", jsonObject.get("line_y").toString());
            data.put("bar", jsonObject.get("bar").toString());
            data.put("CI_Lv1", jsonObject.get("CI_Lv1").toString());
            data.put("CI_Lv2", jsonObject.get("CI_Lv2").toString());
            data.put("best_dist_name", jsonObject.get("best_dist_name").toString());
            data.put("dataset_x", jsonObject.get("dataset_x").toString());
            data.put("dataset_y", jsonObject.get("dataset_y").toString());
            data.put("state", jsonObject.get("state").toString());

            data.put("predict_3month_time", jsonObject.get("predict_3month_time").toString());
            data.put("predict_6month_time", jsonObject.get("predict_6month_time").toString());
            data.put("predict_9month_time", jsonObject.get("predict_9month_time").toString());
            data.put("predict_1year_time", jsonObject.get("predict_1year_time").toString());

            data.put("predict_3month_data", jsonObject.get("predict_3month_data").toString());
            data.put("predict_6month_data", jsonObject.get("predict_6month_data").toString());
            data.put("predict_9month_data", jsonObject.get("predict_9month_data").toString());
            data.put("predict_1year_data", jsonObject.get("predict_1year_data").toString());

            data.put("predict_3month_dataVal", jsonObject.get("predict_3month_dataVal").toString());
            data.put("predict_6month_dataVal", jsonObject.get("predict_6month_dataVal").toString());
            data.put("predict_9month_dataVal", jsonObject.get("predict_9month_dataVal").toString());
            data.put("predict_1year_dataVal", jsonObject.get("predict_1year_dataVal").toString());

            data.put("predict_now_dataVal", jsonObject.get("predict_now_dataVal").toString());
        }

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }
}
