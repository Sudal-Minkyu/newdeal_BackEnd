package com.broadwave.backend.notice.NoticeDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Minkyu
 * Date : 2022-08-19
 * Time :
 * Remark : 뉴딜 공지사항 메인페이지용 Dto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeMainListDto {

    private Long id;
    private String ntTitle;

}
