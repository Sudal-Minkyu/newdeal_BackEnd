package com.broadwave.backend.teams;

import com.broadwave.backend.common.AjaxResponse;
import com.broadwave.backend.common.CommonUtils;
import com.broadwave.backend.teams.teamfile.TeamFileService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Minkyu
 * Date : 2021-06-01
 * Time :
 * Remark : NewDeal Team
 */
@Slf4j
@RestController
@RequestMapping("/api/team")
public class TeamRestController {

    private final TeamService teamService;
//    private final AccountService accountService;
    private final ModelMapper modelMapper;
    private final TeamFileService teamFileService;

    @Autowired
    public TeamRestController(TeamService teamService,ModelMapper modelMapper,TeamFileService teamFileService) {
        this.teamService = teamService;
//        this.accountService = accountService;
        this.modelMapper = modelMapper;
        this.teamFileService = teamFileService;
    }

    // 부서등록 저장
    @PostMapping ("reg")
    public ResponseEntity<Map<String,Object>> teamreg(@ModelAttribute TeamMapperDto teamMapperDto,
//                                  MultipartHttpServletRequest multi,
                                  HttpServletRequest request) throws Exception {

        AjaxResponse res = new AjaxResponse();

        Team team = modelMapper.map(teamMapperDto, Team.class);

        String currentuserid = "testId";

        Optional<Team> optionalTeam = teamService.findByTeamcode(team.getTeamcode());

        //신규일때
        if ( teamMapperDto.getMode().equals("N")) {
            team.setInsertDateTime(LocalDateTime.now());
            team.setInsert_id(currentuserid);
        }else{
        //수정일때
            team.setId(optionalTeam.get().getId());
            team.setInsertDateTime(optionalTeam.get().getInsertDateTime());
            team.setInsert_id(optionalTeam.get().getInsert_id());
            team.setModifyDateTime(LocalDateTime.now());
            team.setModify_id(currentuserid);
        }

        Team teamSave = teamService.tesmSave(team);

        log.info("부서저장 성공");

//        Iterator<String> files = multi.getFileNames();
//        while(files.hasNext()) {
//            String temaFileUpload = files.next();
//            MultipartFile mFile = multi.getFile(temaFileUpload);
//            assert mFile != null;
//            if (!mFile.isEmpty()) {
//                log.info("저장할 파일이 존재합니다.");
//                teamFileService.store(mFile, teamSave);
//            }
//        }

        return ResponseEntity.ok(res.success());

    }

    @GetMapping("list")
    public ResponseEntity<Map<String,Object>> teamlist(@RequestParam (value="teamcode", defaultValue="") String teamcode,
                                                                                    @RequestParam (value="teamname", defaultValue="") String teamname ,
                                                                                    @PageableDefault Pageable pageable,
                                                                                    HttpServletRequest request){
        AjaxResponse res = new AjaxResponse();

        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
        log.info("JWT_AccessToken : "+JWT_AccessToken);

        log.info("부서 리스트 조회 / 조회조건 : teamcode / '" + teamcode + "', teamname / '" + teamname + "'");
        Page<TeamDto> teams = teamService.findAllBySearchStrings(teamcode,teamname, pageable);

        return ResponseEntity.ok(res.ResponseEntityPage(teams));
    }

    // 회원가입용 소속 리스트 호출 API
    @GetMapping("registerTeamList")
    public ResponseEntity<Map<String,Object>> registerTeamList(){

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        List<TeamListDto> teams = teamService.findByRegisterTeamList();

        data.put("teams",teams);

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    @GetMapping ("team")
    public ResponseEntity<Map<String,Object>> team(@RequestParam (value="teamcode", defaultValue="") String teamcode){

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        log.info("단일부서조회  / teamcode: '" + teamcode +"'");
        Optional<Team> optionalTeam = teamService.findByTeamcode(teamcode);

        if(optionalTeam.isPresent()){
            Team team = optionalTeam.get();
            data.put("sendTeamData",team);
        }
        log.info("단일부서 조회 성공");

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    @PostMapping("del")
    public ResponseEntity<Map<String,Object>> teamdel(@RequestParam (value="teamcode", defaultValue="") String teamcode){

        AjaxResponse res = new AjaxResponse();

        log.info("부서 삭제 / teamcode: " + teamcode );
        Optional<Team> optionalTeam = teamService.findByTeamcode(teamcode);

        Team team = optionalTeam.get();

        teamService.delete(team);
        return ResponseEntity.ok(res.success());
    }

    @PostMapping("excelDownload")
    public String getExcelByExt(Model model, HttpServletRequest request,
                                @RequestParam(value = "teamcode") String teamcode,
                                @RequestParam(value = "teamname") String teamname) {

        log.info("teamcode : "+teamcode);
        log.info("teamname : "+teamname);

        //엑셀헤더
        List<String> header = Arrays.asList("부서코드", "팀명", "비고");
        //엑셀전환할 자료
        List<TeamDto> downLoadData = teamService.findAllBySearchStringsExcel(teamcode, teamname);
        //엑셀전황
        CommonUtils.exceldataModel(model,header,downLoadData,"teamlist");
        log.info("부서 리스트 엑셀 다운로드 ( loginID : '" + CommonUtils.getCurrentuser(request) +"', IP : '" + CommonUtils.getIp(request) + "' )" );
        return "excelDownXls";
    }

}
