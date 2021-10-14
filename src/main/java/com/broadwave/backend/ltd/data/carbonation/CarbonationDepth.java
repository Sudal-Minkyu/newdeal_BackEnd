package com.broadwave.backend.ltd.data.carbonation;

import com.broadwave.backend.bscodes.SeriesCode;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author InSeok
 * Date : 2019-05-08
 * Time : 10:52
 * Remark : 장기조사 탄산화깊이 측정방식 데이터
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lt_carbonation_depth")
public class CarbonationDepth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ld_id")
    private Long id;


    @Enumerated(EnumType.STRING)
    @Column(name="ld_series_code")
    private SeriesCode seriesCode;

    @Column(name="ld_environment")
    private String environment;  //내륙환경/해안환경

    @Column(name="ld_element1")
    private String element1;

    @Column(name="ld_element2")
    private String element2;

    @Column(name="ld_period")
    private Integer period;         // 주기(day)

    @Column(name="ld_period_name")
    private String periodName;      // 주기(설명)

    @Column(name="ld_salt_rate")
    private Double saltRate;      // 초기염분함유량

    @Column(name="ld_value")
    private Double value;        // 압축강도 MPA

    @Column(name="lst_modify_dt")
    private LocalDateTime lastModifyDateTime;

    @Column(name="lst_modify_id")
    private String lastModify_id;
}
