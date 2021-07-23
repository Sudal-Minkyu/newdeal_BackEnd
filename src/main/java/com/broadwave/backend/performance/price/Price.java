package com.broadwave.backend.performance.price;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Minkyu
 * Date : 2021-07-22
 * Time :
 * Remark : 뉴딜 관리자 전용 성능개선사업평가 년도별 환율 테이블
 */
@Entity
@Data
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="nd_bs_pi_year_price")
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id; // 고유ID값(NOTNULL)

    @Column(name="pi_year")
    private Double piYear; // 년도

    @Column(name="pi_price")
    private Double piPrice; // 환율

    @Column(name="insert_date")
    private LocalDateTime insertDateTime;

    @Column(name="insert_id")
    private String insert_id;

}
