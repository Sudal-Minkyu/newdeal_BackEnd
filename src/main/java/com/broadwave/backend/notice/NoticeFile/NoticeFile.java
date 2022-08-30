package com.broadwave.backend.notice.NoticeFile;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Minkyu
 * Date : 2022-08-02
 * Time :
 * Remark : 뉴딜 공지사항 파일업로드 테이블
 */
@Entity
@Data
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="nd_nt_upload_file")
public class NoticeFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="nt_id")
    private Long ntId;

    @Column(name="nt_file_yyyymmdd")
    private String ntFileYyyymmdd; // S3 파일업로드 날짜

    @Column(name="nt_file_path")
    private String ntFilePath; // S3 파일경로

    @Column(name="nt_file_name")
    private String ntFileName; // S3 파일명

    @Column(name="nt_file_realname")
    private String ntFileRealname; // S3 진짜이름

    @Column(name="insert_date")
    private LocalDateTime insertDateTime;

    @Column(name="insert_id")
    private String insert_id;

}
