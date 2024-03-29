package com.broadwave.backend.notice.NoticeFile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface NoticeFileRepository extends JpaRepository<NoticeFile,Long>, NoticeFileRepositoryCustom {

    // 게시물ID로 파일리스트 찾기
    @Query("select a from NoticeFile a where a.ntId = :ntId")
    List<NoticeFile> findByNoticeFileDelete(Long ntId);

    // 게시물ID로 삭제할 파일만 골라 찾기
    @Query("select a from NoticeFile a where a.id in :deleteFileList")
    List<NoticeFile> findByNoticeFileDeleteList(List<Long> deleteFileList);

    // 게시물 파일 일괄삭제
    @Transactional
    @Modifying
    @Query("delete from NoticeFile a where a.ntId in :deleteFileList")
    void noticeFileListDelete(List<Long> deleteFileList);
}