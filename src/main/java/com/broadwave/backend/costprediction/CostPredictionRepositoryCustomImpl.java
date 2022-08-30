package com.broadwave.backend.costprediction;

import com.broadwave.backend.costprediction.costpredicionDtos.CostPredictionListDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Minkyu
 * Date : 2021-08-03
 * Remark :
 */
@Repository
public class CostPredictionRepositoryCustomImpl extends QuerydslRepositorySupport implements CostPredictionRepositoryCustom {

    public CostPredictionRepositoryCustomImpl() {
        super(CostPrediction.class);
    }

    @Override
    public List<CostPredictionListDto> findByCostPredicionList(String cpName) {

        QCostPrediction costPrediction  = QCostPrediction.costPrediction;

        JPQLQuery<CostPredictionListDto> query = from(costPrediction)
                .select(Projections.constructor(CostPredictionListDto.class,
                        costPrediction.id,
                        costPrediction.cpNum,
                        costPrediction.cpName,
                        costPrediction.cpType,
                        costPrediction.cpManager,
                        costPrediction.cpTopForm,
                        costPrediction.cpKind
                ));

        if(!cpName.isEmpty()){
            // 교량번호 또는 교량명으로 검색
            query.where(costPrediction.cpName.likeIgnoreCase("%"+cpName+"%").or(costPrediction.cpNum.likeIgnoreCase("%"+cpName+"%")));
        }

        return query.fetch();
    }

}
