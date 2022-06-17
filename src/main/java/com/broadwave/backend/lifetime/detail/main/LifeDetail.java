package com.broadwave.backend.lifetime.detail.main;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Minkyu
 * Date : 2022-05-23
 * Time :
 * Remark : 생애주기 의사결전 지원서비스 관련 세부부분 테이블(권기현박사) - 마스터테이블
 */
@Entity
@Data
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="nd_lt_detail")
public class LifeDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="lt_detail_id")
    private Long id; // 고유ID값(NOTNULL)

    @Column(name="lt_detail_type")
    private String ltDetailType; // 입력타입 : 1 반발경도, 2 탄산화깊이, 3 균열깊이, 4 열화물침투량

    @Column(unique = true, name="lt_detail_auto_num")
    private String ltDetailAutoNum; // 고유코드

    @Column(name="insert_date")
    private LocalDateTime insertDateTime;

    @Column(name="insert_id")
    private String insert_id;

    @Column(name="modify_date")
    private LocalDateTime modifyDateTime;

    @Column(name="modify_id")
    private String modify_id;

}
