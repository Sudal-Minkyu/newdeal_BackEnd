package com.broadwave.backend.safety.calculation;

import com.broadwave.backend.safety.Safety;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Minkyu
 * Date : 2022-04-05
 * Time :
 * Remark : 뉴딜 계측 기반 안전성 추정 데이터 - 온도, 내하율 기록 테이블
 */
@Entity
@Data
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="nd_sf_calculation")
public class Calculation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cal_id")
    private Long id;

    @ManyToOne(targetEntity = Safety.class,fetch = FetchType.LAZY)
    @JoinColumn(name="sf_id")
    private Safety sfId;

    @Column(name="cal_yyyymmdd")
    private String calYyyymmdd; // 계측일시

    @Column(name="cal_temperature")
    private Double calTemperature; // 온도

    @Column(name="cal_capacity")
    private Double calCapacity; // 공용 내하율

    @Column(name="insert_date")
    private LocalDateTime insertDateTime;

    @Column(name="insert_id")
    private String insert_id;

    @Column(name="modify_date")
    private LocalDateTime modifyDateTime;

    @Column(name="modify_id")
    private String modify_id;

}
