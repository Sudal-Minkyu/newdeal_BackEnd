package com.broadwave.backend.ltd.data.rebar;

import com.broadwave.backend.bscodes.SeriesCode;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author InSeok
 * Date : 2019-05-08
 * Time : 15:51
 *  Remark : 철근부식량 : Rebar corrosion weigth
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lt_rebar_corrosion")
public class RebarCorrosion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="lr_id")
    private Long id;


    @Enumerated(EnumType.STRING)
    @Column(name="lr_series_code")
    private SeriesCode seriesCode;

    @Column(name="lr_environment")
    private String environment;  //내륙환경/해안환경

    @Column(name="lr_element1")
    private String element1;

    @Column(name="lr_element2")
    private String element2;

    @Column(name="lr_period")
    private Integer period;         // 주기(day)

    @Column(name="lr_period_name")
    private String periodName;      // 주기(설명)

    @Column(name="lr_salt_rate")
    private Double saltRate;      // 초기염분함유량

    @Column(name="lr_value")
    private Double value;        // 철근부식량(g) -- value 1, 2, 3, 4, 의 합-> 평균계산시 나누기 4를 추가할것

    @Column(name="lr_value1")
    private Double value1;        // 철근부식량(g) - 1cm

    @Column(name="lr_value2")
    private Double value2;        // 철근부식량(g) - 2cm

    @Column(name="lr_value3")
    private Double value3;        // 철근부식량(g) - 3cm

    @Column(name="lr_value4")
    private Double value4;        // 철근부식량(g) - 4cm

    @Column(name="lst_modify_dt")
    private LocalDateTime lastModifyDateTime;

    @Column(name="lst_modify_id")
    private String lastModify_id;
}
