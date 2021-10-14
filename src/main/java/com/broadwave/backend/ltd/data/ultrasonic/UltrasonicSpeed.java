package com.broadwave.backend.ltd.data.ultrasonic;

import com.broadwave.backend.bscodes.SeriesCode;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author InSeok
 * Date : 2019-05-08
 * Time : 13:48
 * Remark : 장기조사데이터 초음파속도 측정방식데이터
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lt_ultrasonic_speed")
public class UltrasonicSpeed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="lu_id")
    private Long id;


    @Enumerated(EnumType.STRING)
    @Column(name="lu_series_code")
    private SeriesCode seriesCode;

    @Column(name="lu_environment")
    private String environment;  //내륙환경/해안환경

    @Column(name="lu_element1")
    private String element1;

    @Column(name="lu_element2")
    private String element2;

    @Column(name="lu_period")
    private Integer period;         // 주기(day)

    @Column(name="lu_period_name")
    private String periodName;      // 주기(설명)

    @Column(name="lu_salt_rate")
    private Double saltRate;      // 초기염분함유량

    @Column(name="lu_value")
    private Double value;        // 초음파속도(km/s) -- value 1, 2, 3, 4, 의 합-> 평균계산시 나누기 4를 추가할것

    @Column(name="lu_value1")
    private Double value1;        // 초음파속도(km/s) - 1cm

    @Column(name="lu_value2")
    private Double value2;        // 초음파속도(km/s) - 2cm

    @Column(name="lu_value3")
    private Double value3;        // 초음파속도(km/s) - 3cm

    @Column(name="lu_value4")
    private Double value4;        // 초음파속도(km/s) - 4cm

    @Column(name="lst_modify_dt")
    private LocalDateTime lastModifyDateTime;

    @Column(name="lst_modify_id")
    private String lastModify_id;
}
