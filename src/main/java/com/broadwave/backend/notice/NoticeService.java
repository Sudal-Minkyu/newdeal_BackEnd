package com.broadwave.backend.notice;

import com.broadwave.backend.common.AjaxResponse;
import com.broadwave.backend.Aws.AWSS3Service;
import com.broadwave.backend.notice.NoticeDtos.NoticeListDto;
import com.broadwave.backend.notice.NoticeDtos.NoticeMainListDto;
import com.broadwave.backend.notice.NoticeDtos.NoticeMapperDto;
import com.broadwave.backend.notice.NoticeDtos.NoticeViewDto;
import com.broadwave.backend.notice.NoticeFile.NoticeFile;
import com.broadwave.backend.notice.NoticeFile.NoticeFileListDto;
import com.broadwave.backend.notice.NoticeFile.NoticeFileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import com.broadwave.backend.common.ResponseErrorCode;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class NoticeService {

    @Value("${newdeal.aws.s3.bucket.url}")
    private String AWSBUCKETURL;

    private final NoticeRepository noticeRepository;
    private final NoticeFileRepository noticeFileRepository;

    private final AWSS3Service awss3Service;

    @Autowired
    public NoticeService(NoticeRepository noticeRepository, NoticeFileRepository noticeFileRepository, AWSS3Service awss3Service) {
        this.noticeRepository = noticeRepository;
        this.noticeFileRepository = noticeFileRepository;
        this.awss3Service = awss3Service;
    }

    // NEWDEAL 공지사항 저장
    @Transactional
    public ResponseEntity<Map<String, Object>> save(NoticeMapperDto noticeMapperDto, HttpServletRequest request) throws IOException {
        log.info("save 호출");

        log.info("ntTitle : "+noticeMapperDto.getNtTitle());
        log.info("ntContents : "+noticeMapperDto.getNtContents());

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        String nowDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String insert_id = request.getHeader("insert_id");

        Notice notice;
        Notice saveNotice;
        if(noticeMapperDto.getNtId() != null){
            Optional<Notice> optionalNotice = noticeRepository.findById(noticeMapperDto.getNtId());

            if(optionalNotice.isPresent()){
                if(!insert_id.equals("admin")){
                    return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE035.getCode(), "해당 글을 수정하는 "+ResponseErrorCode.NDE035.getDesc(), null, null));
                }
                log.info("공지사항 게시판 글을 수정합니다.");
                optionalNotice.get().setNtTitle(noticeMapperDto.getNtTitle());
                optionalNotice.get().setNtContents(noticeMapperDto.getNtContents());
                optionalNotice.get().setModify_id(insert_id);
                optionalNotice.get().setModifyDateTime(LocalDateTime.now());

                if(noticeMapperDto.getDeleteFileList() != null) {
                    // AWS 파일 삭제
                    List<NoticeFile> noticeFileList = noticeFileRepository.findByNoticeFileDeleteList(noticeMapperDto.getDeleteFileList());
                    for(NoticeFile noticeFile : noticeFileList){
                        String insertDate =noticeFile.getNtFileYyyymmdd();
                        String path = "/noticeFiles/"+insertDate;
                        log.info("path : "+path);
                        String filename = noticeFile.getNtFileName();
                        log.info("filename : "+filename);
                        awss3Service.deleteObject(path,filename);
                    }
                    noticeFileRepository.deleteAll(noticeFileList);
                }

                saveNotice = noticeRepository.save(optionalNotice.get());
            }else{
                return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE034.getCode(), "해당 글은 "+ResponseErrorCode.NDE034.getDesc(), null, null));
            }
        }else{
            log.info("공지사항 게시판 글이 신규입니다.");
            notice = new Notice(noticeMapperDto.getNtTitle(), noticeMapperDto.getNtContents(), nowDate, insert_id, LocalDateTime.now());
            saveNotice = noticeRepository.save(notice);
        }

        List<NoticeFile> noticeFileList = new ArrayList<>();
        // AWS 파일저장
        if(noticeMapperDto.getMultipartFileList() != null){
            NoticeFile noticeFile;
            log.info("multipartFileList.size() : "+noticeMapperDto.getMultipartFileList().size());
            for(MultipartFile multipartFile : noticeMapperDto.getMultipartFileList()){
                noticeFile = new NoticeFile();

                noticeFile.setNtId(saveNotice.getNtId());
                noticeFile.setNtFileYyyymmdd(nowDate);
                // 파일 오리지널 Name
                String originalFilename = Normalizer.normalize(Objects.requireNonNull(multipartFile.getOriginalFilename()), Normalizer.Form.NFC);
                log.info("originalFilename : "+originalFilename);
                noticeFile.setNtFileRealname(originalFilename);
                // 파일 Size
//                long fileSize = multipartFile.getSize();
//                noticeFile.setNtVolume(fileSize);
                // 확장자
                String ext;
                ext = '.'+originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
//                log.info("ext : "+ext);
                // 파일 중복명 처리
                String fileName = UUID.randomUUID().toString().replace("-", "")+ext;
//                log.info("fileName : "+fileName);
                noticeFile.setNtFileName(fileName);
                // S3에 저장 할 파일주소
                SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
                String filePath = "/noticeFiles/" + date.format(new Date());
                log.info("filePath : "+AWSBUCKETURL+filePath+"/");
                noticeFile.setNtFilePath(AWSBUCKETURL+filePath+"/");
                awss3Service.nomalFileUpload(multipartFile, fileName, filePath);
                noticeFile.setInsert_id(insert_id);
                noticeFile.setInsertDateTime(LocalDateTime.now());
                noticeFileList.add(noticeFile);
            }
            noticeFileRepository.saveAll(noticeFileList);
        }else{
            log.info("첨부파일이 존재하지 않습니다");
        }

        data.put("id",saveNotice.getNtId());

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    // NEWDEAL 공지사항 리스트
    public ResponseEntity<Map<String, Object>> list(String filterFromDt, String filterToDt, String ntTitle) {
        log.info("list 호출");

        log.info("filterFromDt : "+filterFromDt);
        log.info("filterToDt : "+filterToDt);
        log.info("ntTitle : "+ntTitle);

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        List<NoticeListDto> noticeListDtos = noticeRepository.findByNoticeList(filterFromDt, filterToDt, ntTitle);

        data.put("gridListData", noticeListDtos);
        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    // NEWDEAL 공지사항 뷰어
    public ResponseEntity<Map<String, Object>> view(Long ntId) {
        log.info("view 호출");

        log.info("ntId : "+ntId);

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        NoticeViewDto noticeViewDto = noticeRepository.findByNoticeView(ntId);
        HashMap<String,Object> noticeViewInfo = new HashMap<>();

        List<NoticeFileListDto> noticeFileListDtos = noticeFileRepository.findByNoticeFileList(ntId);
        log.info("noticeViewDto : "+noticeViewDto);
        if(noticeViewDto != null){
            noticeViewInfo.put("ntId", noticeViewDto.getId());

            noticeViewInfo.put("ntTitle", noticeViewDto.getNtTitle());
            noticeViewInfo.put("ntContents", noticeViewDto.getNtContents());
            noticeViewInfo.put("username", noticeViewDto.getUsername());
            noticeViewInfo.put("ntYyyymmdd", noticeViewDto.getNtYyyymmdd());
            noticeViewInfo.put("fileList", noticeFileListDtos);
        }

        data.put("noticeViewDto",noticeViewInfo);

        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

    //  공지사항 글삭제
    @Transactional
    public ResponseEntity<Map<String, Object>> delete(Long ntId) {
        log.info("delete 호출");

        AjaxResponse res = new AjaxResponse();

        log.info("ntId : "+ntId);

        Optional<Notice> optionalNotice = noticeRepository.findById(ntId);
        if(optionalNotice.isPresent()){

            List<NoticeFile> noticeFileList = noticeFileRepository.findByNoticeFileDelete(optionalNotice.get().getNtId());
//            log.info("tagNoticeFileList : "+tagNoticeFileList);

            for(NoticeFile noticeFile : noticeFileList){
                // AWS 파일 삭제
                String insertDate =noticeFile.getInsertDateTime().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
                String path = "/noticeFiles/"+insertDate;
                log.info("path : "+path);
                String filename = noticeFile.getNtFileName();
                log.info("filename : "+filename);
                awss3Service.deleteObject(path,filename);
//                log.info("AWS삭제성공");
            }

            noticeFileRepository.deleteAll(noticeFileList);
            noticeRepository.delete(optionalNotice.get());
        }else{
            return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE034.getCode(), "삭제 할 파일이 "+ResponseErrorCode.NDE034.getDesc(), null, null));
        }

        return ResponseEntity.ok(res.success());
    }

    // NEWDEAL 공지사항 메인리스트
    public ResponseEntity<Map<String, Object>> mainList() {
        log.info("mainList 호출");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        List<NoticeMainListDto> noticeMainListDtos = noticeRepository.findByMainNoticeList();

        data.put("gridListData", noticeMainListDtos);
        return ResponseEntity.ok(res.dataSendSuccess(data));
    }

}
