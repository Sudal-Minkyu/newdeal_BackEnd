package com.broadwave.backend.notice.NoticeDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Minkyu
 * Date : 2022-08-02
 * Time :
 * Remark : 뉴딜 공지사항 리스트조회 Dto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeListDto {

    private Long id;
    private String ntTitle;
    private String ntYyyymmdd;
    private String username;

}
