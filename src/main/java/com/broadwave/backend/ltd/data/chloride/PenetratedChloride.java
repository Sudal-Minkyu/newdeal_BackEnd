package com.broadwave.backend.ltd.data.chloride;

import com.broadwave.backend.bscodes.SeriesCode;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author InSeok
 * Date : 2019-05-10
 * Time : 11:03
 * Remark : 염분함유량 : Penetrated chloride
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lt_penetrated_chloride")
public class PenetratedChloride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="lp_id")
    private Long id;


    @Enumerated(EnumType.STRING)
    @Column(name="lp_series_code")
    private SeriesCode seriesCode;

    @Column(name="lp_environment")
    private String environment;  //내륙환경/해안환경

    @Column(name="lp_element1")
    private String element1;

    @Column(name="lp_element2")
    private String element2;

    @Column(name="lp_period")
    private Integer period;         // 주기(day)

    @Column(name="lp_period_name")
    private String periodName;      // 주기(설명)

    @Column(name="lp_salt_rate")
    private Double saltRate;      // 초기염분함유량

    @Column(name="lp_value")
    private Double value;         // 염분함유량(%) -- value 1, 2, 3, 4,5,6 의 합-> 평균계산시 나누기 6를 추가할것

    @Column(name="lp_value1")
    private Double value1;        // 염분함유량(%) - 0-0.15cm

    @Column(name="lp_value2")
    private Double value2;        // 염분함유량(%) - 0.15-1.5cm

    @Column(name="lp_value3")
    private Double value3;        // 염분함유량(%) - 1.5-3cm

    @Column(name="lp_value4")
    private Double value4;        // 염분함유량(%) - 3-4.5cm

    @Column(name="lp_value5")
    private Double value5;        // 염분함유량(%) - 4.5-6cm

    @Column(name="lp_value6")
    private Double value6;        // 염분함유량(%) - 10cm

    @Column(name="lst_modify_dt")
    private LocalDateTime lastModifyDateTime;

    @Column(name="lst_modify_id")
    private String lastModify_id;
}
