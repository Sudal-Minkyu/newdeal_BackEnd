package com.broadwave.backend.notice.NoticeFile;

import java.util.List;

/**
 * @author Minkyu
 * Date : 2021-08-02
 * Remark :
 */
public interface NoticeFileRepositoryCustom {
    List<NoticeFileListDto> findByNoticeFileList(Long ntId);
}
