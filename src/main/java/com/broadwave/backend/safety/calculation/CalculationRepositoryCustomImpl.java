package com.broadwave.backend.safety.calculation;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Minkyu
 * Date : 2022-04-05
 * Remark :
 */
@Repository
public class CalculationRepositoryCustomImpl extends QuerydslRepositorySupport implements CalculationRepositoryCustom {

    public CalculationRepositoryCustomImpl() {
        super(Calculation.class);
    }

    @Override
    public List<CalculationListDto> findByCalculationList(Long id) {

        QCalculation calculation = QCalculation.calculation;

        JPQLQuery<CalculationListDto> query = from(calculation)
                .select(Projections.constructor(CalculationListDto.class,
                        calculation.id,
                        calculation.calYyyymmdd,
                        calculation.calTemperature,
                        calculation.calCapacity
                ));

        query.where(calculation.sfId.id.eq(id));
        query.groupBy(calculation.calYyyymmdd).orderBy(calculation.calYyyymmdd.asc());

        return query.fetch();
    }

    @Override
    public List<CalculationTempDto> findByCalculationTempChart(Long id) {

        QCalculation calculation = QCalculation.calculation;

        JPQLQuery<CalculationTempDto> query = from(calculation)
                .select(Projections.constructor(CalculationTempDto.class,
                        calculation.calYyyymmdd,
                        calculation.calTemperature
                ));

        query.where(calculation.sfId.id.eq(id));
        query.groupBy(calculation.calYyyymmdd).orderBy(calculation.calYyyymmdd.asc());

        return query.fetch();
    }

    @Override
    public List<CalculationCapDto> findByCalculationCapChart(Long id) {

        QCalculation calculation = QCalculation.calculation;

        JPQLQuery<CalculationCapDto> query = from(calculation)
                .select(Projections.constructor(CalculationCapDto.class,
                        calculation.calYyyymmdd,
                        calculation.calCapacity
                ));

        query.where(calculation.sfId.id.eq(id));
        query.groupBy(calculation.calYyyymmdd).orderBy(calculation.calYyyymmdd.asc());

        return query.fetch();
    }


}
