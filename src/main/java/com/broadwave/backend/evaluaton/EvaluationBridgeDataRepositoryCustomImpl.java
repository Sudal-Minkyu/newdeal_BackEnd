package com.broadwave.backend.evaluaton;

import com.broadwave.backend.evaluaton.dtos.EvaluationBridgeDataDto;
import com.broadwave.backend.evaluaton.entity.EvaluationBridgeData;
import com.broadwave.backend.evaluaton.entity.QEvaluationBridgeData;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class EvaluationBridgeDataRepositoryCustomImpl extends QuerydslRepositorySupport implements EvaluationBridgeDataRepositoryCustom {
    public EvaluationBridgeDataRepositoryCustomImpl() {
        super(EvaluationBridgeData.class);
    }

    @Override
    public List<EvaluationBridgeDataDto> findByBridgeDataList(String evName) {
        QEvaluationBridgeData ed = new QEvaluationBridgeData("evaluationBridgeData");
        JPQLQuery<EvaluationBridgeDataDto> query = from(ed)
                .select(Projections.constructor(EvaluationBridgeDataDto.class,
                        ed.id,
                        ed.evName, // 시설명
                        ed.evRoad1, // 도로종류
                        ed.evRoad2, // 노선명
                        ed.evRemark, //비고
                        ed.evAddr1, //시도
                        ed.evAddr2, //시군구
                        ed.evAddr3, //읍면동
                        ed.evAddr4, //리
                        ed.evLength, // 총 길이
                        ed.evWidth, // 총 폭
                        ed.evWidthEff, // 유효 폭
                        ed.evHeight, // 높이
                        ed.evSpanCnt, // 경간수
                        ed.evMaxSpanLength, // 최대 경간장길이
                        ed.evSuperstructure, // 상부구조
                        ed.evSubstructure, // 하부구조
                        ed.evDesignLoading, // 설계하중
                        ed.evTraffic, // 교통량
                        ed.evOrg1, // 기관구분1
                        ed.evOrg2, // 기관구분2
                        ed.evOrg3, // 기관구분3
                        ed.evYearComplete

                ));

        if(!evName.isEmpty()) {
            query.where(ed.evName.containsIgnoreCase(evName));
        }
        return query.fetch();
    }
}
