package com.broadwave.backend.notice.NoticeFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Minkyu
 * Date : 2022-08-02
 * Time :
 * Remark : 뉴딜 공지사항 파일 ListDto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeFileListDto {

    private Long id; // ID
    private String ntFilePath; // S3파일경로
    private String ntFileName; // S3파일명
    private String ntFileRealname; // 원래 파일 명

}
