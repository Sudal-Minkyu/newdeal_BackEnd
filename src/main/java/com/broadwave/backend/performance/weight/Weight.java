package com.broadwave.backend.performance.weight;

import com.broadwave.backend.performance.Performance;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Minkyu
 * Date : 2021-07-14
 * Time :
 * Remark : 뉴딜 성능개선사업평가 가중치 테이블
 */
@Entity
@Data
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="nd_pi_weight")
public class Weight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id; // 고유ID값(NOTNULL)

    @Column(name="pi_auto_num")
    private String piAutoNum; // 대안 일려번호(NOTNULL)

    @Column(name="pi_weight_safe")
    private Double piWeightSafe; // 안정성 가중치(NOTNULL)

    @Column(name="pi_weight_usability")
    private Double piWeightUsability; // 사용성 가중치(NOTNULL)

    @Column(name="pi_weight_old")
    private Double piWeightOld; // 노후도 가중치(NULL)

    @Column(name="pi_weight_urgency")
    private Double piWeightUrgency; // 시급성 가중치(NOTNULL)

    @Column(name="pi_weight_goal")
    private Double piWeightGoal; // 목표달성도 가중치(NOTNULL)

    @Column(name="pi_weight_safe_utility")
    private Double piWeightSafeUtility; // 안전효용 개선 가중치(NOTNULL)

    @Column(name="pi_weight_cost_utility")
    private Double piWeightCostUtility; // 자산가치 개선 가중치(NULL)

    @Column(name="pi_weight_business")
    private Double piWeightBusiness; // 사업추진 타당성 가중치(NOTNULL)

    @Column(name="pi_weight_complaint")
    private Double piWeightComplaint; // 민원 및 사고 대응성 가중치(NOTNULL)

    @Column(name="pi_weight_business_effect")
    private Double piWeightBusinessEffect; // 사업효과 범용성 가중치(NOTNULL)

    @Column(name="pi_weight_critical_score")
    private Double piWeightCriticalScore; // 사업추진 임계점수(NOTNULL)

    @Column(name="pi_weight_technicality")
    private Double piWeightTechnicality; // 유형_기술성(NOTNULL)

    @Column(name="pi_weight_economy")
    private Double piWeightEconomy; // 유형_경제성(NOTNULL)

    @Column(name="pi_weight_policy")
    private Double piWeightPolicy; // 유형_정책성(NOTNULL)

    @Column(name="insert_date")
    private LocalDateTime insertDateTime;

    @Column(name="insert_id")
    private String insert_id;

    @Column(name="modify_date")
    private LocalDateTime modifyDateTime;

    @Column(name="modify_id")
    private String modify_id;

}
