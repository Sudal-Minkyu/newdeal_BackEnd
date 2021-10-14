package com.broadwave.backend.ltd.data.chloride;

import com.broadwave.backend.bscodes.SeriesCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author InSeok
 * Date : 2019-05-10
 * Time : 11:21
 * Remark :
 */
public interface PenetratedChlorideRepository extends JpaRepository<PenetratedChloride,Long> {
    List<PenetratedChloride> findBySeriesCode(SeriesCode seriesCode);


}
