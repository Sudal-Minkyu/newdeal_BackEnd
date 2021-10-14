package com.broadwave.backend.common;

import com.broadwave.backend.excel.DtoExcel;
import com.broadwave.backend.excel.ExcelConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author InSeok
 * Date : 2019-04-22
 * Time : 15:37
 * Remark : 공용 util static 함수
 */
@Slf4j
public abstract class CommonUtils {

    //IP를 가져오는 함수 -> 프록시서버 등 앞단에 다른 네트웍이 존재할경우 원래의 IP를 가져오기 위함
    public static String getIp(HttpServletRequest request) {
        log.info("접속 유저 IP를 가져오는 공통 함수 호출");

        String ip = request.getHeader("X-Forwarded-For");

        log.info(">>>> X-FORWARDED-FOR : " + ip);

        if (ip == null) {
            ip = request.getHeader("Proxy-Client-IP");
            log.info(">>>> Proxy-Client-IP : " + ip);
        }
        if (ip == null) {
            ip = request.getHeader("WL-Proxy-Client-IP"); // 웹로직
            log.info(">>>> WL-Proxy-Client-IP : " + ip);
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            log.info(">>>> HTTP_CLIENT_IP : " + ip);
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            log.info(">>>> HTTP_X_FORWARDED_FOR : " + ip);
        }
        if (ip == null) {
            ip = request.getRemoteAddr();
        }

        log.info(">>>> Result : IP Address : "+ip);

        return ip;

    }

//    //Pages 형태의 조회 API 반환 함수

//    public static ResponseEntity ResponseEntityPage(Page pages ){
//        log.info("Pages 형태의 조회 API 반환 함수 공통 함수 호출");
//
//        Map<String, Object> res = new HashMap<>();
////        HashMap<String, Object> data = new HashMap<>();
//
//        log.info("pages.getTotalPages() : "+pages.getTotalPages());
//        log.info("pages.getNumber() : "+pages.getNumber()+1);
//        log.info("total_rows : "+pages.getTotalElements());
//        log.info("current_rows : "+pages.getNumberOfElements());
//
//        res.put("status",200);
//        res.put("timestamp", new Timestamp(System.currentTimeMillis()));
//        res.put("message", "SUCCESS");
//        res.put("err_code", "");
//        res.put("err_msg", "");
//
//        if(pages.getTotalElements()> 0 ){
//            log.info("출력1");
//            res.put("datalist",pages.getContent());
//            res.put("total_page",pages.getTotalPages());
//            res.put("current_page",pages.getNumber() + 1);
//            res.put("total_rows",pages.getTotalElements());
//            res.put("current_rows",pages.getNumberOfElements());
////           
//        }else{
//            log.info("출력2");
//            res.put("total_page",pages.getTotalPages());
//            res.put("current_page",pages.getNumber() + 1);
//            res.put("total_rows",pages.getTotalElements());
//            res.put("current_rows",pages.getNumberOfElements());
////           
//        }
//        return ResponseEntity.ok(res);
//    }

    public static String getCurrentuser(HttpServletRequest request){
        log.info("현재 접속 유저 정보 가져오기 공통 함수 호출");


        //HttpSession session = request.getSession();
        //String currentuserid = (String) session.getAttribute("userid");
        String currentuserid = "system";
        Principal userPrincipal = request.getUserPrincipal();
        if (userPrincipal != null) {
            currentuserid = userPrincipal.getName();
            if (currentuserid == null || currentuserid.equals("")) {
                currentuserid = "system";
            }
        }
        return currentuserid;

    }

    //엑셀다운로드시 공용으로사용하는 함수
    public static void exceldataModel(Model model, List<String> header, List<? extends DtoExcel> excelDtos, String filename) {
        log.info("엑셀다운버튼 클릭시 최종 다운로드 처리하는 공통 함수 호출");

        List<List<String>> excelData  = new ArrayList<>();
        excelDtos.forEach(e-> excelData.add(e.toArray()));

        model.addAttribute(ExcelConstant.FILE_NAME, filename);
        model.addAttribute(ExcelConstant.HEAD, header);
        model.addAttribute(ExcelConstant.BODY,excelData);

    }
    public static String GetZeroToNullString(String str){
        log.info("문자열을받아 0.0 0.00 이면 null 문자를 반환하는 공통 함수 호출");
        if (str.equals("0.0") || str.equals("0.00")){
            return "null";
        }else{
            return str;
        }

    }
    //문자열이 Null 이면 빈문자열 반환하는 함수
    public static String GetNulltoString(String str){
        if (str == null) {
            return "";
        }
        return str;
    }

    //Double Null 이면 빈문자열 반환하는 함수
    public static Double GetNulltoDouble(Double num){
        if (num == null) {
            return 0d;
        }
        return num;
    }
}
