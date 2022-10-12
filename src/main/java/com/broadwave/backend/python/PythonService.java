package com.broadwave.backend.python;

import com.broadwave.backend.common.AjaxResponse;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Minkyu
 * Date : 2022-07-19
 * Time :
 * Remark : NewDeal 파이썬을 통해 센서관련 데이터 호출
 */
@Slf4j
@Service
public class PythonService {

    @Value("${newdeal.aws.postgresql.url}")
    private String awsPostgresqlUrl;

    @Value("${newdeal.aws.postgresql.username}")
    private String awsPostgresqlUsername;

    @Value("${newdeal.aws.postgresql.password}")
    private String awsPostgresqlPassword;

    @Value("${newdeal.aws.python.api.url}")
    private String awsPythonApiUrl;

    // 포스트SQL 호출 테스트
    public void postgreSqlTest() throws ClassNotFoundException {
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        // Postgresql 접근 테스트
        Class.forName("org.postgresql.Driver");

        List<HashMap<String,Object>> deviceData = new ArrayList<>();
        HashMap<String,Object> deviceDataInfo;

        try (Connection connection = DriverManager.getConnection(awsPostgresqlUrl, awsPostgresqlUsername, awsPostgresqlPassword);) {
            Statement stmt = connection.createStatement();

            // 권태호 박사 쿼리 테스트
//         URL url = new URL("http://python.bmaps.kr:8013/sensorData?sensor=Ssmartcs:2:DNAGW2111&time1=2021-10-01%2000:00:00&time2=2022-05-01%2000:00:00");
            String quary = "SELECT t, avg(\"value\") as val  ";
            quary += "FROM (SELECT time_bucket('1 days', time) as t, \"value\" FROM public.tb_static_data ";
            quary += "WHERE time >= '2021-10-01 00:00:00'   AND time <= '2022-05-01 00:00:00'  AND channel_number = '2'";
            quary += "AND device_id = 'Ssmartcs:2:DNAGW2111') AS ccc GROUP BY t ORDER BY t";
            ResultSet rs = stmt.executeQuery(quary);

//            ResultSet rs = stmt.executeQuery("SELECT * FROM tb_device");
            while (rs.next()) {
//                String deviceId = rs.getString("device_id");
//                String deviceName = rs.getString("device_name");
//                System.out.println("센서ID : "+deviceId +" 센서이름 : "+deviceName);
//
//                deviceDataInfo = new HashMap<>();
//                deviceDataInfo.put("deviceId", deviceId);
//                deviceDataInfo.put("deviceName", deviceName);
//                deviceData.add(deviceDataInfo);

                // 권태호 박사 쿼리 테스트
                String t = rs.getString("t");
                String val = rs.getString("val");
                System.out.println("시간 : "+t +" 평균 : "+val);

                deviceDataInfo = new HashMap<>();
                deviceDataInfo.put("deviceId", t);
                deviceDataInfo.put("deviceName", val);
                deviceData.add(deviceDataInfo);

            }
            data.put("chartName","test"); // 타입
            data.put("deviceData",deviceData);

            rs.close();
            stmt.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // 센서리스트, 교량리스트 가져오는 메서드 -> EC2 파이썬 FastApi /conn, /bridge 호출
    public JSONObject sensorListGet(String apiName) {

        HttpURLConnection connection;
        JSONObject responseJson;

        // 파이썬 센서리스트 가져오기
        log.info("AWS URL : "+awsPythonApiUrl);
        log.info("apiName conn : "+apiName); // "conn"인지 확인
        try {

            // 센서리스트 데이터 불러오기
            URL url = new URL(awsPythonApiUrl+apiName);

            // 센서 데이터 불러오기
//         URL url = new URL("http://python.bmaps.kr:8013/conn;
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.disconnect();

            int responseCode = connection.getResponseCode();
            log.info("responseCode : "+responseCode);

            if(responseCode == 200){
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                StringBuilder stringBuffer = new StringBuilder();
                String inputLine;

                while ((inputLine = bufferedReader.readLine()) != null) {
                    stringBuffer.append(inputLine);
                }

                bufferedReader.close();

                responseJson = new JSONObject(stringBuffer.toString());
                log.info("responseJson : " + responseJson.get("body"));

                return responseJson;
            }else {
                return null;
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 교량에 설치된 센서리스트 가져오는 메서드 -> EC2 파이썬 FastApi /bridgeSensor 호출 -> param : bridgeId(교량 UUID)
    public JSONObject bridgeSensor(String apiName, String bridgeId, String sensorTypeCode) {
        HttpURLConnection connection;
        JSONObject responseJson;

        // 파이썬 교량에 설치된 센서리스트 가져오기
        log.info("AWS URL : "+awsPythonApiUrl);
        log.info("apiName sensorAllData : "+apiName); // "sensorAllData"인지 확인
        log.info("bridgeId : "+bridgeId);
        log.info("sensorTypeCode : "+sensorTypeCode);

        try {

            // 센서리스트 데이터 불러오기
            URL url = new URL(awsPythonApiUrl+apiName+"/?bridgeId="+bridgeId+"&sensorTypeCode="+sensorTypeCode);

            // 센서 데이터 불러오기
//         URL url = new URL("http://python.bmaps.kr:8013/bridgeSensor/?bridgeId=223a0847-b7d2-b08b-8592-92b3706b8ef2");
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            connection.disconnect();

            int responseCode = connection.getResponseCode();
            log.info("responseCode : "+responseCode);

            if(responseCode == 200){
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                StringBuilder stringBuffer = new StringBuilder();
                String inputLine;

                while ((inputLine = bufferedReader.readLine()) != null)  {
                    stringBuffer.append(inputLine);
                }

                bufferedReader.close();

                responseJson = new JSONObject(stringBuffer.toString());
                log.info("responseJson : " + responseJson.get("body"));

                return responseJson;
            }else{
                return null;
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 센서 전체데이터 가져오는 메서드 -> EC2 파이썬 FastApi /sensorAllData 호출 -> param : 센서명
    public JSONObject sensorAllDataGet(String apiName, String sensor, String channelNumber) {
        HttpURLConnection connection;
        JSONObject responseJson;

        // 파이썬 센서리스트 가져오기
        log.info("AWS URL : "+awsPythonApiUrl);
        log.info("apiName sensorAllData : "+apiName); // "sensorAllData"인지 확인
        log.info("sensor : "+sensor);
        log.info("channelNumber : "+channelNumber);

        try {

            // 센서리스트 데이터 불러오기
            URL url = new URL(awsPythonApiUrl+apiName+"/?sensor="+sensor+"&channelNumber="+channelNumber);

            // 센서 데이터 불러오기
//         URL url = new URL("http://python.bmaps.kr:8013/sensorData?sensor=Ssmartcs:2:DNAGW2111;
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            connection.disconnect();

            int responseCode = connection.getResponseCode();
            log.info("responseCode : "+responseCode);

            if(responseCode == 200){
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                StringBuilder stringBuffer = new StringBuilder();
                String inputLine;

                while ((inputLine = bufferedReader.readLine()) != null)  {
                    stringBuffer.append(inputLine);
                }

                bufferedReader.close();

                responseJson = new JSONObject(stringBuffer.toString());

                return responseJson;
            }else{
                return null;
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 센서 예측데이터 가져오는 메서드 -> EC2 파이썬 FastApi /sensorData 호출 -> param : 센서명, from날짜, to날짜, 예측 할 일수
    public JSONObject futureSensorDataGet(String apiName, String sensor, String time1, String time2, String days) {
        HttpURLConnection connection;
        JSONObject responseJson;

        // 파이썬 센서리스트 가져오기
        log.info("AWS URL : "+awsPythonApiUrl);
        log.info("apiName sensorAllData : "+apiName); // "sensorData"인지 확인
        log.info("sensor : "+sensor);
        log.info("time1 : "+time1);
        log.info("time2 : "+time2);
        log.info("days : "+days);

        try {

            // 센서리스트 데이터 불러오기
            URL url = new URL(awsPythonApiUrl+apiName+"/?sensor="+sensor+"&time1="+time1+"&time2="+time2+"&days="+days);

            // 센서 데이터 불러오기
//         URL url = new URL("http://python.bmaps.kr:8013/sensorData?sensor=Ssmartcs:2:DNAGW2111&time1=2021-10-01&time2=2022-05-01&days=50
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            connection.disconnect();

            int responseCode = connection.getResponseCode();
            log.info("responseCode : "+responseCode);

            if(responseCode == 200){
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                StringBuilder stringBuffer = new StringBuilder();
                String inputLine;

                while ((inputLine = bufferedReader.readLine()) != null)  {
                    stringBuffer.append(inputLine);
                }

                bufferedReader.close();

                responseJson = new JSONObject(stringBuffer.toString());
                log.info("responseJson futureData : "+responseJson.get("futureData"));
                log.info("responseJson days : "+responseJson.get("date"));

                return responseJson;
            }else{
                return null;
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 센서 Step2 데이터 가져오는 메서드 -> EC2 파이썬 FastApi /sensorData 호출 -> param : 센서명, from날짜, to날짜, 예측 할 일수
    public JSONObject sensorStepDataGet(String apiName, String sensor, String time1, String time2, String channelNumber, String warningVal, String dangerVal) {
        HttpURLConnection connection;
        JSONObject responseJson;

        // 파이썬 센서리스트 가져오기
        log.info("AWS URL : "+awsPythonApiUrl);
        log.info("apiName sensorAllData : "+apiName); // "sensorStep"인지 확인
        log.info("sensor : "+sensor);
        log.info("time1 : "+time1);
        log.info("time2 : "+time2);
        log.info("channelNumber : "+channelNumber);
        log.info("warningVal : "+warningVal);
        log.info("dangerVal : "+dangerVal);

        try {

            // 센서리스트 데이터 불러오기
            URL url = new URL(awsPythonApiUrl+apiName+"/?sensor="+sensor+"&time1="+time1+"&time2="+time2+"&channelNumber="+channelNumber+"&warningVal="+warningVal+"&dangerVal="+dangerVal);

            // 센서 데이터 불러오기
//         URL url = new URL("http://python.bmaps.kr:8013/sensorStep?sensor=Ssmartcs:2:DNAGW2111&time1=2021-10-01&time2=2022-05-01&channelNumber=2&warningVal=0.94&warningVal=0.97
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            connection.disconnect();

            int responseCode = connection.getResponseCode();
            log.info("responseCode : "+responseCode);

            if(responseCode == 200){
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                StringBuilder stringBuffer = new StringBuilder();
                String inputLine;

                while ((inputLine = bufferedReader.readLine()) != null)  {
                    stringBuffer.append(inputLine);
                }

                bufferedReader.close();

                responseJson = new JSONObject(stringBuffer.toString());

                return responseJson;
            }else{
                return null;
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }




}
