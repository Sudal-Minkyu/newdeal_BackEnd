package com.broadwave.backend.notice.NoticeDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Minkyu
 * Date : 2022-04-05
 * Time :
 * Remark : NEWDEAL 공지사항등록 MapperDto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeMapperDto {

    private Long ntId; // 게시판ID
    private String ntTitle; // 게시판 제목
    private String ntContents; // 게시물 내용
    private List<MultipartFile> multipartFileList;
    private List<Long> deleteFileList; // 지울 파일들의 id 리스트

}
