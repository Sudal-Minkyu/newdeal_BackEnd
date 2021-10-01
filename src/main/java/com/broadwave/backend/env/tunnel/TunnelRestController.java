package com.broadwave.backend.env.tunnel;

import com.broadwave.backend.common.AjaxResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author InSeok
 * Date : 2019-06-21
 * Time : 15:19
 * Remark :
 */
@RestController
@Slf4j
@RequestMapping("/api/env/tunnel")
public class TunnelRestController {

    private final TunnelService tunnelService;


    @Autowired
    public TunnelRestController(TunnelService tunnelService) {
        this.tunnelService = tunnelService;
    }

    @PostMapping("list")
    public ResponseEntity<Map<String,Object>> findAll(HttpServletRequest request){

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

//        String currentuserid = CommonUtils.getCurrentuser(request);
//        log.info("터널정보조회 / 조회자 :'" + currentuserid);

        List<TunnelDto> tunnels = tunnelService.findAll();
        List<List<String>> tunnelsArray = new ArrayList<>();

        tunnels.forEach(tunnelDto -> {
            tunnelsArray.add(tunnelDto.toArray());
        });
//        log.info("datalist : "+tunnelsArray);
        data.put("datalist",tunnelsArray);
        data.put("total_rows",tunnelsArray.size());

        log.info("터널정보 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));


    }


}
