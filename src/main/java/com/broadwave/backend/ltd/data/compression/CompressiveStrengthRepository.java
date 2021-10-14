package com.broadwave.backend.ltd.data.compression;

import com.broadwave.backend.bscodes.SeriesCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author InSeok
 * Date : 2019-04-30
 * Time : 13:38
 * Remark :
 */
public interface CompressiveStrengthRepository extends JpaRepository<CompressiveStrength,Long> {
    List<CompressiveStrength> findBySeriesCode(SeriesCode seriesCode);
}
