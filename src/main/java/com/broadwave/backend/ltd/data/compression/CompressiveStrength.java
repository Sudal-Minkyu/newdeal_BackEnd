package com.broadwave.backend.ltd.data.compression;

import com.broadwave.backend.bscodes.SeriesCode;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author InSeok
 * Date : 2019-04-30
 * Time : 10:28
 * Remark : 장기조사 압축강도 측정방식 데이터
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lt_compressive_strength")
public class CompressiveStrength {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="lc_id")
    private Long id;


    @Enumerated(EnumType.STRING)
    @Column(name="lc_series_code")
    private SeriesCode seriesCode;

    @Column(name="lc_environment")
    private String environment;  //내륙환경/해안환경

    @Column(name="lc_element1")
    private String element1;

    @Column(name="lc_element2")
    private String element2;

    @Column(name="lc_period")
    private Integer period;         // 주기(day)

    @Column(name="lc_period_name")
    private String periodName;      // 주기(설명)

    @Column(name="lc_salt_rate")
    private Double saltRate;      // 초기염분함유량

    @Column(name="lc_value")
    private Double value;        // 압축강도 MPA

    @Column(name="lst_modify_dt")
    private LocalDateTime lastModifyDateTime;

    @Column(name="lst_modify_id")
    private String lastModify_id;




}
