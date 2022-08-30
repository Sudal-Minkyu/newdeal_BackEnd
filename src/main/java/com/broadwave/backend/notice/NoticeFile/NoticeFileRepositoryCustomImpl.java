package com.broadwave.backend.notice.NoticeFile;

import com.broadwave.backend.account.QAccount;
import com.broadwave.backend.notice.Notice;
import com.broadwave.backend.notice.NoticeDtos.NoticeListDto;
import com.broadwave.backend.notice.NoticeRepositoryCustom;
import com.broadwave.backend.notice.QNotice;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Minkyu
 * Date : 2021-08-02
 * Remark :
 */
@Repository
public class NoticeFileRepositoryCustomImpl extends QuerydslRepositorySupport implements NoticeFileRepositoryCustom {

    public NoticeFileRepositoryCustomImpl() {
        super(NoticeFile.class);
    }

    @Override
    public List<NoticeFileListDto> findByNoticeFileList(Long ntId) {

        QNoticeFile noticeFile  = QNoticeFile.noticeFile;

        JPQLQuery<NoticeFileListDto> query = from(noticeFile)
                .where(noticeFile.ntId.eq(ntId))
                .select(Projections.constructor(NoticeFileListDto.class,
                        noticeFile.id,
                        noticeFile.ntFilePath,
                        noticeFile.ntFileName,
                        noticeFile.ntFileRealname
                ));

        return query.fetch();
    }

}
