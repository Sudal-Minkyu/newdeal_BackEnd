package com.broadwave.backend.costprediction;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Minkyu
 * Date : 2022-08-03
 * Time :
 * Remark : 뉴딜 유지관리 기술 선정 및 비용예측 테이블(정인수, 김종협박사)
 */
@Entity
@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Table(name="nd_cp_input")
public class CostPrediction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="cp_num")
    private String cpNum; // 교량번호

    @Column(name="cp_name")
    private String cpName; // 교량명

    @Column(name="cp_type")
    private String cpType; // 도로종류

    @Column(name="cp_address")
    private String cpAddress; // 시도+시군구+읍명동+리

    @Column(name="cp_ownership")
    private String cpOwnership; // 소유기관

    @Column(name="cp_manager")
    private String cpManager; // 관리기관

    @Column(name="cp_completion_year")
    private String cpCompletionYear; // 준공년도

    @Column(name="cp_length")
    private Double cpLength; // 교장(m)

    @Column(name="cp_width")
    private Double cpWidth; // 교폭(m)

    @Column(name="cp_available_width")
    private Double cpAvailable_width; // 유효폭(m)

    @Column(name="cp_height")
    private Double cpHeight; // 높이

    @Column(name="cp_top_form")
    private String cpTopForm; // 상부형식

    @Column(name="cp_down_form")
    private String cpDownForm; // 하부형식

    @Column(name="cp_kind")
    private String cpKind; // 종별구분

    @Column(name="cp_control_weight")
    private Double cpControlWeight; // 허용통행하중(ton)

    @Column(name="cp_control_height")
    private Double cpControlHeight; // 통행제한높이

    @Column(name="cp_traffic")
    private Integer cpTraffic; // 교통량

    @Column(name="insert_id")
    private String insert_id;

    @Column(name="insert_date")
    private LocalDateTime insertDateTime;

    @Column(name="modify_id")
    private String modify_id;

    @Column(name="modify_date")
    private LocalDateTime modifyDateTime;

}
