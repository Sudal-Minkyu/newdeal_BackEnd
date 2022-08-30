package com.broadwave.backend.notice.NoticeDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Minkyu
 * Date : 2022-08-02
 * Time :
 * Remark : 뉴딜 공지사항 파일업로드 뷰 Dto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeViewDto {

    private Long id;
    private String ntTitle;
    private String ntContents;
    private String ntYyyymmdd;
    private String username;

}
