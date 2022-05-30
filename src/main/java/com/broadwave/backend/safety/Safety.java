package com.broadwave.backend.safety;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Minkyu
 * Date : 2022-04-05
 * Time :
 * Remark : 뉴딜 계측 기반 안전성 추정 데이터 제공 관련 테이블(조근희박사)
 */
@Entity
@Data
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="nd_sf_input")
public class Safety {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="sf_id")
    private Long id; // 고유ID값

    @Column(name="sf_name")
    private String sfName; // 교량명

    @Column(name="sf_form")
    private String sfForm; // 교량형식

    @Column(name="sf_rank")
    private String sfRank; // 교량등급

    @Column(name="sf_length")
    private Double sfLength; // 총 길이

    @Column(name="sf_width")
    private Double sfWidth; // 총 폭

    @Column(name="sf_num")
    private Integer sfNum; // 경간수

    @Column(name="sf_completion_year")
    private Integer sfCompletionYear; // 준공년도

    @Column(name="sf_factor")
    private Double sfFactor; // 안전율

    @Column(name="sf_file_yyyymmdd")
    private String sfFileYyyymmdd; // S3 파일업로드 날짜

    @Column(name="sf_file_path")
    private String sfFilePath; // S3 파일경로

    @Column(name="sf_file_name")
    private String sfFileName; // S3 파일명

    @Column(name="insert_date")
    private LocalDateTime insertDateTime;

    @Column(name="insert_id")
    private String insert_id;

    @Column(name="modify_date")
    private LocalDateTime modifyDateTime;

    @Column(name="modify_id")
    private String modify_id;

}
