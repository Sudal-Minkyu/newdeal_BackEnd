package com.broadwave.backend.performance.price;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

/**
 * @author Minkyu
 * Date : 2021-07-22
 * Time :
 * Remark : 뉴딜 관리자 전용 성능개선사업평가 년도별 환율 PriceRepositoryCustomImpl
 */
@Repository
public class PriceRepositoryCustomImpl extends QuerydslRepositorySupport implements PriceRepositoryCustom {

    public PriceRepositoryCustomImpl() {
        super(Price.class);
    }

    @Override
    public Page<PriceDto> findByYearPrice(Double piYear, Double piPrice, Pageable pageable) {
        QPrice price  = QPrice.price;

        JPQLQuery<PriceDto> query = from(price)
                .select(Projections.constructor(PriceDto.class,
                        price.id,
                        price.piYear,
                        price.piPrice
                ));

        if (piYear != null){
            query.where(price.piYear.goe(piYear));
        }
        if (piPrice != null){
            query.where(price.piPrice.goe(piPrice));
        }

        query.orderBy(price.id.desc());

        final List<PriceDto> prices = Objects.requireNonNull(getQuerydsl()).applyPagination(pageable, query).fetch();
        return new PageImpl<>(prices, pageable, query.fetchCount());
    }

    @Override
    public PriceDto findByPiYearCustom(Double piYear) {
        QPrice price  = QPrice.price;
        JPQLQuery<PriceDto> query = from(price)
                .select(Projections.constructor(PriceDto.class,
                        price.id,
                        price.piYear,
                        price.piPrice
                ));
        query.where(price.piYear.eq(piYear));
        return query.fetchOne();
    }

}
