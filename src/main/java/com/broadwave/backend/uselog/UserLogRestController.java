package com.broadwave.backend.uselog;

import com.broadwave.backend.common.AjaxResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author Minkyu
 * Date : 2021-08-03
 * Remark :
 */
@Slf4j
@RestController
@RequestMapping("/api/userLog")
public class UserLogRestController {

    private final UserLogService userLogService;

    @Autowired
    public UserLogRestController(UserLogService userLogService) {
        this.userLogService = userLogService;
    }

    // NEWDEAL 메류 로그/서치 기록
    @PostMapping("logreg")
    public ResponseEntity<Map<String,Object>> logreg(@RequestParam (value="menuName1", defaultValue="") String menuName1,
                                                     @RequestParam (value="menuName2", defaultValue="") String menuName2,
                                                     @RequestParam (value="useType", defaultValue="") String useType,
                                                         @RequestParam (value="data", defaultValue="") String data,
                                                     HttpServletRequest request){
        AjaxResponse res = new AjaxResponse();

        UserLog userLog = new UserLog();

        userLog.setUseMenu1(menuName1);
        userLog.setUseMenu2(menuName2);
        userLog.setUseType(useType);
        userLog.setSearchstring(data);
        userLog.setInsertDateTime(LocalDateTime.now());
        userLog.setInsert_id("insert_id");

        userLogService.save(userLog);

        return ResponseEntity.ok(res.success());
    }

}
