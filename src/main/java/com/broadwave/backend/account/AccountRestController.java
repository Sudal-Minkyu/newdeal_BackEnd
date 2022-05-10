package com.broadwave.backend.account;

import com.broadwave.backend.account.AccountDtos.*;
import com.broadwave.backend.common.AjaxResponse;
import com.broadwave.backend.common.ResponseErrorCode;
import com.broadwave.backend.teams.Team;
import com.broadwave.backend.teams.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Minkyu
 * Date : 2021-06-22
 * Time :
 * Remark : NewDeal Account RestController
 */
@Slf4j
@RestController
@RequestMapping("/api/account")
public class AccountRestController {

    private final AccountService accountService;
    private final TeamService teamService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AccountRepositoryCustom accountRepositoryCustom;

//    private final LoginlogService loginlogService;

    @Autowired
    public AccountRestController(ModelMapper modelMapper, TeamService teamService, AccountService accountService,PasswordEncoder passwordEncoder, AccountRepositoryCustom accountRepositoryCustom) {
        this.modelMapper = modelMapper;
        this.teamService = teamService;
        this.accountService = accountService;
        this.passwordEncoder = passwordEncoder;
        this.accountRepositoryCustom = accountRepositoryCustom;
    }

    // AccountSave API
    @PostMapping("save")
    public ResponseEntity<Map<String,Object>> accountSave(@ModelAttribute AccountMapperDto accountMapperDto, HttpServletRequest request){

        AjaxResponse res = new AjaxResponse();

        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
        log.info("JWT_AccessToken : "+JWT_AccessToken);

        Account account = modelMapper.map(accountMapperDto, Account.class);

        Optional<Team> optionalTeam = teamService.findByTeamcode(accountMapperDto.getTeamcode());

        //아이디를 입력하세요.
        if (accountMapperDto.getUserid() == null || accountMapperDto.getUserid().equals("")){
            log.info(ResponseErrorCode.NDE003.getDesc());
            return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE003.getCode(), ResponseErrorCode.NDE003.getDesc(),null,null));
        }
        //패스워드를 입력하세요.
        if (accountMapperDto.getPassword() == null || accountMapperDto.getPassword().equals("")){
            log.info(ResponseErrorCode.NDE004.getDesc());
            return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE004.getCode(), ResponseErrorCode.NDE004.getDesc(),null,null));
        }

        //부서코드가 존재하지않으면
        if (optionalTeam.isEmpty()) {
            log.info(" 선택한 부서 DB 존재 여부 체크.  부서코드: '" + accountMapperDto.getTeamcode() +"'");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE005.getCode(), ResponseErrorCode.NDE005.getDesc(),null,null));
        }else{
            Team team = optionalTeam.get();
            account.setTeam(team);
        }

        Optional<Account> optionalAccount = accountService.findByUserid(account.getUserid());
        String currentuserid = request.getHeader("insert_id");
        log.info("currentuserid : "+currentuserid);

        //신규일때
        if (accountMapperDto.getMode().equals("N")) {
            //userid 중복체크
            if (optionalAccount.isPresent()) {
                log.info("사용자 저장 실패(사용자아이디중복) 사용자아이디 : '" + account.getUserid() + "'");
                return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE001.getCode(), ResponseErrorCode.NDE001.getDesc(),ResponseErrorCode.NDE002.getCode(), ResponseErrorCode.NDE002.getDesc()));
            }
            account.setInsert_id(currentuserid);
            account.setInsertDateTime(LocalDateTime.now());
        }else{
            //수정일때
            if(optionalAccount.isEmpty()){
                log.info("사용자 정보 수정실패 사용자아이디 : '" + account.getUserid() + "'");
                return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE006.getCode(), ResponseErrorCode.NDE006.getDesc(),null,null));
            }else{
                account.setId(optionalAccount.get().getId());
                account.setInsert_id(optionalAccount.get().getInsert_id());
                account.setInsertDateTime(optionalAccount.get().getInsertDateTime());
            }
            account.setModify_id(currentuserid);
            account.setModifyDateTime(LocalDateTime.now());
        }

        Account accountSave =  this.accountService.saveAccount(account);
        log.info("사용자 저장 성공 : id '" + accountSave.getUserid() + "'");
        return ResponseEntity.ok(res.success());

    }

    // AccountList API
    @GetMapping("list")
    public ResponseEntity<Map<String,Object>> accountList(@RequestParam(value="userid", defaultValue="") String userid,
                                                          @RequestParam(value="username", defaultValue="") String username,
                                                          @RequestParam(value="teamname", defaultValue="") String teamname,
                                                          Pageable pageable,
                                                          HttpServletRequest request){
        AjaxResponse res = new AjaxResponse();

        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
        log.info("JWT_AccessToken : "+JWT_AccessToken);

        log.info("부서 리스트 조회 / 조회조건 : userid / '" + userid + "' username / '" + username + "', teamname / '" + teamname + "'");

        Page<AccountDtoWithTeam> accounts = accountService.findAllBySearchStrings(userid, username, teamname, pageable);

        return ResponseEntity.ok(res.ResponseEntityPage(accounts));
    }

    // AccountInfo API
    @PostMapping("account")
    public ResponseEntity<Map<String,Object>> account(@RequestParam (value="userid", defaultValue="") String userid,HttpServletRequest request){

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
        log.info("JWT_AccessToken : "+JWT_AccessToken);

        log.info("단일사용자조회  / userid: '" + userid + "'");
        Optional<Account> optionalAccount = accountService.findByUserid(userid);

        if(optionalAccount.isEmpty()){
            log.info("단일사용자조회실패 : 조회할 데이터가 존재하지않음 , 조회대상 userid : '" + userid +"'");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE006.getCode(),ResponseErrorCode.NDE006.getDesc(),null,null));
        }
        Account account = optionalAccount.get();

        data.put("accountData",account);
        log.info("단일사용자 조회 성공 : id '" + account.getUserid() + "'");

        return ResponseEntity.ok(res.dataSendSuccess(data));

    }

    // AccountDel API
    @PostMapping("del")
    public  ResponseEntity<Map<String,Object>> accountdel(@RequestParam (value="userid", defaultValue="") String userid,
                                     HttpServletRequest request){
        AjaxResponse res = new AjaxResponse();

        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
        log.info("JWT_AccessToken : "+JWT_AccessToken);

        log.info("사용자 삭제 / userid: " + userid );
        Optional<Account> optionalAccount = accountService.findByUserid(userid);

        //정보가있는지 체크
        if (optionalAccount.isEmpty()){
            log.info("사용자삭제실패 : 삭제할 데이터가 존재하지않음 , 삭제대상 userid : '" + userid + "'");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE006.getCode(),ResponseErrorCode.NDE006.getDesc(),null,null));
        }else{
            Account account = optionalAccount.get();
//            //사용중인지체크
//            if( loginlogService.countByLoginAccount(account) > 0){
//                log.info("사용자삭제실패 : LoginLog에서 사용중인데이터 , 삭제대상 userid : '" + userid + "'");
//                return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE007.getCode(),ResponseErrorCode.NDE007.getDesc(),null,null));
//            }
            accountService.delete(account);
        }
        return ResponseEntity.ok(res.success());
    }

    //  AccountModifyPassword API(비밀번호 변경)
    @PostMapping("modifypassword")
    public ResponseEntity<Map<String,Object>> accountSavepassword(@RequestParam(value="userid", defaultValue="") String userid,
                                              @RequestParam(value="oldPassword", defaultValue="") String oldPassword,
                                              @RequestParam(value="changePassword", defaultValue="") String changePassword,
                                              @RequestParam(value="confirmPassword", defaultValue="") String confirmPassword){

        Account account = new Account();

        AjaxResponse res = new AjaxResponse();

        //아이디를 입력하세요.
        if (userid == null || userid.equals("")){
            return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE008.getCode(), ResponseErrorCode.NDE008.getDesc(),ResponseErrorCode.NDE009.getCode(), ResponseErrorCode.NDE009.getDesc()));
        }

        Optional<Account> optionalAccount = accountService.findByUserid(userid);

        //수정일때
        if(optionalAccount.isEmpty()){
            log.info("사용자정보(이메일)수정실패 : 사용자아이디: '" + userid + "'");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE006.getCode(), ResponseErrorCode.NDE006.getDesc(),null,null));
        }else{

            //현재암호비교
            if (!passwordEncoder.matches(oldPassword,optionalAccount.get().getPassword())){
                log.info("현재 비밀번호가 일치하지 않습니다.");
                return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE010.getCode(),ResponseErrorCode.NDE010.getDesc(),null,null));
            }
            //암호검토
            if( !changePassword.equals(confirmPassword) ){
                log.info("변경하려는 비밀번호와 검토비밀번호와 일치하지 않습니다.");
                return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE011.getCode(),ResponseErrorCode.NDE011.getDesc(),null,null));
            }

            account.setId(optionalAccount.get().getId());
            account.setInsert_id(optionalAccount.get().getInsert_id());
            account.setInsertDateTime(optionalAccount.get().getInsertDateTime());
            account.setEmail(optionalAccount.get().getEmail());
            account.setTeam(optionalAccount.get().getTeam());
            account.setRole(optionalAccount.get().getRole());
            account.setUsername(optionalAccount.get().getUsername());
            account.setUserid(userid);
            account.setPassword(changePassword);
        }

        account.setModify_id(userid);
        account.setModifyDateTime(LocalDateTime.now());

        accountService.saveAccount(account);
        return ResponseEntity.ok(res.success());
    }

//    @PostMapping("modifyemail")
//    public ResponseEntity accountSaveEmail(@ModelAttribute AccountMapperDtoModify accountMapperDto, HttpServletRequest request){
//
//        Account account = modelMapper.map(accountMapperDto, Account.class);
//
//        //아이디를 입력하세요.
//        if (accountMapperDto.getUserid() == null || accountMapperDto.getUserid().equals("")){
//            log.info(ResponseErrorCode.E009.getDesc());
//            return ResponseEntity.ok(res.fail(ResponseErrorCode.E009.getCode(), ResponseErrorCode.E009.getDesc()));
//        }
//
//        Optional<Account> optionalAccount = accountService.findByUserid(account.getUserid());
//
//
//
//        //수정일때
//        if(!optionalAccount.isPresent()){
//            log.info("사용자정보(이메일)수정실패 : 사용자아이디: '" + account.getUserid() + "'");
//            return ResponseEntity.ok(res.fail(ResponseErrorCode.E004.getCode(), ResponseErrorCode.E004.getDesc()));
//        }else{
//            account.setId(optionalAccount.get().getId());
//            account.setInsert_id(optionalAccount.get().getInsert_id());
//            account.setInsertDateTime(optionalAccount.get().getInsertDateTime());
//            account.setPassword(optionalAccount.get().getPassword());
//            account.setTeam(optionalAccount.get().getTeam());
//            account.setRole(optionalAccount.get().getRole());
//            account.setUsername(optionalAccount.get().getUsername());
//
//        }
//        account.setModify_id(currentuserid);
//        account.setModifyDateTime(LocalDateTime.now());
//
//        Account accountSave =  this.accountService.modifyAccount(account);
//
//        log.info("사용자 저장 성공 : id '" + account.getUserid() + "'");
//        return ResponseEntity.ok(res.success());
//
//    }

    // NEWDEAL AccountInfo rolename 가져오기위헌 API
    @PostMapping("rolename")
    public ResponseEntity<Map<String,Object>> rolename(@RequestParam (value="userid", defaultValue="") String userid){

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        AccountBaseDto account = accountService.findByAcountBase(userid);
        data.put("accountData",account);

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    // NEWDEAL 회원가입페이지
    @PostMapping("register")
    public ResponseEntity<Map<String,Object>> accountRegister(@ModelAttribute AccountRegisterMapperDto accountRegisterMapperDto, HttpServletRequest request){

        AjaxResponse res = new AjaxResponse();

        Account account = modelMapper.map(accountRegisterMapperDto, Account.class);
        Optional<Team> optionalTeam = teamService.findByTeamcode("T00003");

//        log.info("account ; "+account);

        //패스워드를 입력하세요.
        if (accountRegisterMapperDto.getPassword() == null || accountRegisterMapperDto.getPassword().equals("")){
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E006.getCode(), ResponseErrorCode.E006.getDesc(),null,null));
        }else{
            //암호검토
            if(!accountRegisterMapperDto.getPassword().equals(accountRegisterMapperDto.getCheck_password()) ){
                log.info("비밀번호와 비밀번호 확인과 일치하지 않습니다.");
                return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE026.getCode(),ResponseErrorCode.NDE026.getDesc(),null,null));
            }
        }

        //아이디를 입력하세요.
        if (accountRegisterMapperDto.getUserid() == null || accountRegisterMapperDto.getUserid().equals("")){
            log.info(ResponseErrorCode.E007.getDesc());
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E007.getCode(), ResponseErrorCode.E007.getDesc(),null,null));
        }

        //부서코드가 존재하지않으면
        if (optionalTeam.isEmpty()) {
            return ResponseEntity.ok(res.fail(ResponseErrorCode.E005.getCode(), ResponseErrorCode.E005.getDesc(),null,null));
        }else{
            Team team = optionalTeam.get();
            account.setTeam(team);
        }

        Optional<Account> optionalAccount = accountService.findByUserid(account.getUserid());

        //userid 중복체크
        if (optionalAccount.isPresent()) {
                log.info("사용자저장실패(사용자아이디중복) 사용자아이디: '" + account.getUserid() + "'");
                return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE001.getCode(), ResponseErrorCode.NDE001.getDesc(),null,null));
        }else{
            account.setRole(AccountRole.ROLE_ADMIN);
            account.setInsert_id(accountRegisterMapperDto.getUserid());
            account.setInsertDateTime(LocalDateTime.now());
        }

        Account accountSave =  accountService.registerAccount(account);

        log.info("회원가입 성공 : id '" + accountSave.getUserid() + "'");

        return ResponseEntity.ok(res.success());
    }

    // NEWDEAL 유저수가져오기
    @GetMapping("count")
    public ResponseEntity<Map<String,Object>> userCount(){

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        Long userCount = accountRepositoryCustom.findByAccountCount();
        
//        log.info("회원가입 성공 : id '" + accountSave.getUserid() + "'");
//        log.info("유저수 : "+userCount);
        data.put("userCount",userCount);

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }



}
