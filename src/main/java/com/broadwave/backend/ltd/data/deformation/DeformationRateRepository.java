package com.broadwave.backend.ltd.data.deformation;

import com.broadwave.backend.bscodes.SeriesCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author InSeok
 * Date : 2019-05-07
 * Time : 14:44
 * Remark :
 */
public interface DeformationRateRepository extends JpaRepository<DeformationRate,Long> {
    List<DeformationRate> findBySeriesCode(SeriesCode seriesCode);
}
