package com.broadwave.backend.ltd.data.deformation;

import com.broadwave.backend.bscodes.SeriesCode;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author InSeok
 * Date : 2019-05-07
 * Time : 13:49
 * Remark :  길이변형율 (DeformationTest 이지만 Test란 문자때문에 DeformationRate로 변경)
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lt_deformation_test")
public class DeformationRate {
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
    private Double value;        // 길이변형률(µ) -- value 1, 2, 3, 4, 의 합-> 평균계산시 나누기 4를 추가할것

    @Column(name="ld_value1")
    private Double value1;        // 길이변형률(µ) - 1cm

    @Column(name="ld_value2")
    private Double value2;        // 길이변형률(µ) - 2cm

    @Column(name="ld_value3")
    private Double value3;        // 길이변형률(µ) - 3cm

    @Column(name="ld_value4")
    private Double value4;        // 길이변형률(µ) - 4cm

    @Column(name="lst_modify_dt")
    private LocalDateTime lastModifyDateTime;

    @Column(name="lst_modify_id")
    private String lastModify_id;
}
