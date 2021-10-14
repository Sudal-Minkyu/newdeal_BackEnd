package com.broadwave.backend.ltd.data.carbonation;

import com.broadwave.backend.bscodes.SeriesCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author InSeok
 * Date : 2019-05-08
 * Time : 11:10
 * Remark :
 */
public interface CarbonationDepthRepository extends JpaRepository<CarbonationDepth,Long> {
    List<CarbonationDepth> findBySeriesCode(SeriesCode seriesCode);

}
