package com.broadwave.backend.notice;

import com.broadwave.backend.account.QAccount;
import com.broadwave.backend.notice.NoticeDtos.NoticeListDto;
import com.broadwave.backend.notice.NoticeDtos.NoticeMainListDto;
import com.broadwave.backend.notice.NoticeDtos.NoticeViewDto;
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
public class NoticeRepositoryCustomImpl extends QuerydslRepositorySupport implements NoticeRepositoryCustom {

    public NoticeRepositoryCustomImpl() {
        super(Notice.class);
    }

    @Override
    public List<NoticeListDto> findByNoticeList(String filterFromDt, String filterToDt, String ntTitle){

        QNotice notice = QNotice.notice;
        QAccount account = QAccount.account;

        JPQLQuery<NoticeListDto> query = from(notice)
                .innerJoin(account).on(notice.insert_id.eq(account.userid))
                .select(Projections.constructor(NoticeListDto.class,
                    notice.ntId,
                    notice.ntTitle,
                    notice.ntYyyymmdd,
                    account.username
            ));

        if(!ntTitle.isEmpty()){
            query.where(notice.ntTitle.likeIgnoreCase("%"+ntTitle+"%"));
        }

        if(!filterFromDt.isEmpty()){
            query.where(notice.ntYyyymmdd.goe(filterFromDt));
        }

        if(!filterToDt.isEmpty()){
            query.where(notice.ntYyyymmdd.loe(filterToDt));
        }

        query.orderBy(notice.ntId.desc());

        return query.fetch();
    }

    // 공지사항 메인페이지용 리스트 5개만 호출
    @Override
    public List<NoticeMainListDto> findByMainNoticeList(){

        QNotice notice = QNotice.notice;

        JPQLQuery<NoticeMainListDto> query = from(notice)
                .select(Projections.constructor(NoticeMainListDto.class,
                        notice.ntId,
                        notice.ntTitle
                ));

        query.limit(5).orderBy(notice.ntId.desc());

        return query.fetch();
    }

    // 게시물 글 데이터호출
    @Override
    public NoticeViewDto findByNoticeView(Long ntId) {

        QNotice notice = QNotice.notice;
        QAccount account = QAccount.account;

        JPQLQuery<NoticeViewDto> query =
                from(notice)
                        .innerJoin(account).on(account.userid.eq(notice.insert_id))
                        .select(Projections.constructor(NoticeViewDto.class,
                                notice.ntId,
                                notice.ntTitle,
                                notice.ntContents,
                                notice.ntYyyymmdd,
                                account.username
                        ));

        query.where(notice.ntId.eq(ntId));

        return query.fetchOne();
    }

}
