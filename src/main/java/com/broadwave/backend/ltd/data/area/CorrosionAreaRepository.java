package com.broadwave.backend.ltd.data.area;

import com.broadwave.backend.bscodes.SeriesCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author InSeok
 * Date : 2019-05-09
 * Time : 16:44
 * Remark :
 */
public interface CorrosionAreaRepository extends JpaRepository<CorrosionArea,Long> {
    List<CorrosionArea> findBySeriesCode(SeriesCode seriesCode);
}
