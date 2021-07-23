package com.broadwave.backend.performance.price;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Minkyu
 * Date : 2021-07-22
 * Time :
 * Remark : 뉴딜 관리자 전용 성능개선사업평가 년도별 환율 PriceService
 */
@Service
public class PriceService {

    @Autowired
    PriceRepository priceRepository;

    @Autowired
    PriceRepositoryCustom priceRepositoryCustom;

    public Price save(Price price){
        return this.priceRepository.save(price);
    }

    public Optional<Price> findByPiYear(Double piYear ){
        return priceRepository.findByPiYear(piYear);
    }

    public Page<PriceDto> findByYearPrice(Double piYear, Double piPrice, Pageable pageable){
        return priceRepositoryCustom.findByYearPrice(piYear,piPrice,pageable);
    }

    public void delete(Price price){
        priceRepository.delete(price);
    }

    public Optional<Price> findById(Long id) {
        return priceRepository.findById(id);
    }

    public PriceDto findByPiYearCustom(Double year) {
        return priceRepositoryCustom.findByPiYearCustom(year);
    }

}
