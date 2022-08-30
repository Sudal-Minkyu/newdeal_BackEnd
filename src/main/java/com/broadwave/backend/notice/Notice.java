package com.broadwave.backend.notice;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Minkyu
 * Date : 2022-08-02
 * Time :
 * Remark : 뉴딜 공지사항 테이블
 */
@Entity
@Data
@EqualsAndHashCode(of = "ntId")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="nd_notice")
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="nt_id")
    private Long ntId;

    @Column(name="nt_title")
    private String ntTitle;

    @Lob
    @Column(name="nt_contents")
    private String ntContents;

    @Column(name="nt_yyyymmdd")
    private String ntYyyymmdd;

    @Column(name="insert_date")
    private LocalDateTime insertDateTime;

    @Column(name="insert_id")
    private String insert_id;

    @Column(name="modify_date")
    private LocalDateTime modifyDateTime;

    @Column(name="modify_id")
    private String modify_id;

    public Notice(String ntTitle, String ntContents, String ntYyyymmdd, String insert_id, LocalDateTime insertDateTime) {
        this.ntTitle = ntTitle;
        this.ntContents = ntContents;
        this.ntYyyymmdd = ntYyyymmdd;
        this.insert_id = insert_id;
        this.insertDateTime = insertDateTime;
    }
}
