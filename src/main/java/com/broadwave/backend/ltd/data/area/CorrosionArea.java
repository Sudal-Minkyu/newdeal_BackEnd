package com.broadwave.backend.ltd.data.area;

import com.broadwave.backend.bscodes.SeriesCode;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author InSeok
 * Date : 2019-05-09
 * Time : 16:32
 * Remark :  철근부식면적률 : Rebar corrosion area rate
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lt_corrosion_area")
public class CorrosionArea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="la_id")
    private Long id;


    @Enumerated(EnumType.STRING)
    @Column(name="la_series_code")
    private SeriesCode seriesCode;

    @Column(name="la_environment")
    private String environment;  //내륙환경/해안환경

    @Column(name="la_element1")
    private String element1;

    @Column(name="la_element2")
    private String element2;

    @Column(name="la_period")
    private Integer period;         // 주기(day)

    @Column(name="la_period_name")
    private String periodName;      // 주기(설명)

    @Column(name="la_salt_rate")
    private Double saltRate;      // 초기염분함유량

    @Column(name="la_value")
    private Double value;         // 철근부식면적률(%) -- value 1, 2, 3, 4, 의 합-> 평균계산시 나누기 4를 추가할것

    @Column(name="la_value1")
    private Double value1;        // 철근부식면적률(%) - 1cm

    @Column(name="la_value2")
    private Double value2;        // 철근부식면적률(%) - 2cm

    @Column(name="la_value3")
    private Double value3;        // 철근부식면적률(%) - 3cm

    @Column(name="la_value4")
    private Double value4;        // 철근부식면적률(%) - 4cm

    @Column(name="lst_modify_dt")
    private LocalDateTime lastModifyDateTime;

    @Column(name="lst_modify_id")
    private String lastModify_id;
}
