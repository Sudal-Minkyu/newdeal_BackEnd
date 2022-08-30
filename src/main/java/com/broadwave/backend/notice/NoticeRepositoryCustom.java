package com.broadwave.backend.notice;

import com.broadwave.backend.notice.NoticeDtos.NoticeListDto;
import com.broadwave.backend.notice.NoticeDtos.NoticeMainListDto;
import com.broadwave.backend.notice.NoticeDtos.NoticeViewDto;

import java.util.List;

/**
 * @author Minkyu
 * Date : 2021-08-02
 * Remark :
 */
public interface NoticeRepositoryCustom {
    List<NoticeListDto> findByNoticeList(String filterFromDt, String filterToDt, String ntTitle);

    List<NoticeMainListDto> findByMainNoticeList();  // 공지사항 메인페이지용 리스트 5개만 호출

    NoticeViewDto findByNoticeView(Long hnId);
}
