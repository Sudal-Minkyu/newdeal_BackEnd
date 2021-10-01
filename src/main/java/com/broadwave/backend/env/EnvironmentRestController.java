package com.broadwave.backend.env;

import com.broadwave.backend.common.AjaxResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author InSeok
 * Date : 2019-09-06
 * Remark :
 */
@Slf4j
@RestController
@RequestMapping("/api/env")
public class EnvironmentRestController {

    @PostMapping("curveGraph")
    public ResponseEntity<Map<String,Object>> curveGraph(@RequestParam (value="carbon", defaultValue="") String carbon,
                                                         @RequestParam (value="corrosion", defaultValue="") String corrosion,
                                                         @RequestParam (value="nsVal", defaultValue="") String nsVal,
                                                         @RequestParam (value="npVal", defaultValue="") String npVal,
                                                         @RequestParam (value="neVal", defaultValue="") String neVal,
                                                         @RequestParam (value="nrVal", defaultValue="") String nrVal,
                                                         @RequestParam (value="nwVal", defaultValue="") String nwVal,
                                                         @RequestParam (value="nhVal", defaultValue="") String nhVal){
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        double a;
        double b;
        if(carbon.equals("1")){
            if(corrosion.equals("1")){
                a = 34.0;
                b = 0.65;
            }else if(corrosion.equals("2")){
                a = 80.2;
                b = 0.59;
            }else{
                a = 70.6;
                b = 0.79;
            }
        }else{
            if(corrosion.equals("1")){
                a = 33.3;
                b = 0.5;
            }else if(corrosion.equals("2")){
                a = 50.7;
                b = 0.57;
            }else{
                a = 40.2;
                b = 0.56;
            }
        }

        // 첫번째그래프
        List<Integer> graphLabel1 = new ArrayList<>();
        List<Double> graphData1 = new ArrayList<>();
        String sik1="Y="+a+"*X^"+b;

        // 두번째그래프
        List<Integer> graphLabel2 = new ArrayList<>();
        List<Double> graphData2 = new ArrayList<>();

        double Ns;
        if(nsVal.equals("1")){
            Ns = 0.91;
        }else if(nsVal.equals("2")){
            Ns = 1;
        }else{
            Ns = 1.11;
        }

        double Np;
        if(npVal.equals("1")){
            Np = 1;
        }else{
            Np = 3.333;
        }

        double Ne;
        if(neVal.equals("1")){
            Ne = 1;
        }else if(neVal.equals("2")){
            Ne = 1.428;
        }else{
            Ne = 2.5;
        }

        double Nr;
        if(nrVal.equals("1")){
            Nr = 1;
        }else{
            Nr = 0.5;
        }

        double Nw;
        if(nwVal.equals("1")){
            Nw = 1;
        }else if(nwVal.equals("2")){
            Nw = 0.667;
        }else{
            Nw = 0.5;
        }

        double Nh;
        if(nhVal.equals("1")){
            Nh = 1;
        }else if(nhVal.equals("2")){
            Nh = 1.25;
        }else if(nhVal.equals("3")){
            Nh = 1.667;
        }else{
            Nh = 2;
        }
        String sik2="Y="+Ns+"*"+Np+"*"+Ne+"*"+Nr+"*"+Nw+"*"+Nh+"*0.008*X^2";

        double graph1Val;
        double graph2Val;
        for(int i=0; i<=100; i++) {
            graphLabel1.add(i);
            graph1Val = a*Math.pow(i,b);
            graphData1.add(graph1Val);

            graphLabel2.add(i);
            graph2Val = Ns*Np*Ne*Nr*Nw*Nh*0.008*Math.pow(i,2);
            graphData2.add(graph2Val);
        }

        data.put("sik1",sik1);
        data.put("sik2",sik2);
        data.put("graphLabel1",graphLabel1);
        data.put("graphData1",graphData1);
        data.put("graphLabel2",graphLabel2);
        data.put("graphData2",graphData2);

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }
}
