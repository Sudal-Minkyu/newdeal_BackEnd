package com.broadwave.backend.performance.uploadFile;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Minkyu
 * Date : 2022-07-05
 * Time :
 * Remark : 뉴딜 성능개선사업평가 파일업로드 엑셀파일 테이블(한대석박사)
 */
@Entity
@Data
@EqualsAndHashCode(of = "piFileId")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="nd_pi_upload_file")
public class Uploadfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="pi_file_id")
    private Long piFileId; // 고유ID값(NOTNULL)

    @Column(name="pi_auto_num")
    private String piAutoNum; // 대안 일려번호(NOTNULL)

    @Column(name="pi_file_yyyymmdd")
    private String piFileYyyymmdd; // S3 파일업로드 날짜

    @Column(name="pi_file_path")
    private String piFilePath; // S3 파일경로

    @Column(name="pi_file_name")
    private String piFileName; // S3 파일명

    @Column(name="pi_file_realname")
    private String piFileRealname; // S3 진짜이름

    @Column(name="insert_date")
    private LocalDateTime insertDateTime;

    @Column(name="insert_id")
    private String insert_id;

}
