package com.broadwave.backend.performance.price;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Minkyu
 * Date : 2021-07-22
 * Time :
 * Remark : 뉴딜 관리자 전용 성능개선사업평가 년도별 환율 PriceRepositoryCustom
 */
public interface PriceRepositoryCustom {
    Page<PriceDto> findByYearPrice(Double piYear, Double piPrice,Pageable pageable);

    PriceDto findByPiYearCustom(Double year);
}
