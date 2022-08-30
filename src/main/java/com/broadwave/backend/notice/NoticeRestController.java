package com.broadwave.backend.notice;

import com.broadwave.backend.notice.NoticeDtos.NoticeMapperDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * @author Minkyu
 * Date : 2022-04-05
 * Remark : NEWDEAL 공지사항 RestController
 */
@Slf4j
@RestController
@RequestMapping("/api/notice")
public class NoticeRestController {

    private final NoticeService noticeService;

    @Autowired
    public NoticeRestController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    // NEWDEAL 공지사항 저장
    @PostMapping("save")
    @ApiOperation(value = "공지사항 등록", notes = "관리자가 공지사항을 등록한다.")
    @ApiImplicitParams({@ApiImplicitParam(name="insert_id", value = "insert_id", required = true, dataType = "string", paramType = "header")})
    public ResponseEntity<Map<String,Object>> save(@ModelAttribute NoticeMapperDto noticeMapperDto, HttpServletRequest request) throws IOException {
        return noticeService.save(noticeMapperDto, request);
    }

    // NEWDEAL 공지사항 리스트
    @GetMapping("list")
    public ResponseEntity<Map<String,Object>> list(@RequestParam("filterFromDt")String filterFromDt, @RequestParam("filterToDt")String filterToDt, @RequestParam("ntTitle")String ntTitle) {
        return noticeService.list(filterFromDt, filterToDt, ntTitle);
    }

    // NEWDEAL 공지사항 메인리스트
    @GetMapping("mainList")
    public ResponseEntity<Map<String,Object>> mainList() {
        return noticeService.mainList();
    }

    // NEWDEAL 공지사항 뷰어
    @GetMapping("view")
    public ResponseEntity<Map<String,Object>> view(@RequestParam("ntId")Long ntId) {
        return noticeService.view(ntId);
    }

    // NEWDEAL 공지사항 삭제
    @PostMapping("delete")
    @ApiOperation(value = "공지사항 삭제", notes = "관리자가 공지사항 글을 삭제한다.")
    public ResponseEntity<Map<String,Object>> delete(@RequestParam("ntId")Long ntId) {
        return noticeService.delete(ntId);
    }

}


