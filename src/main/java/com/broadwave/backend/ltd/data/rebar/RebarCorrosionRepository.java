package com.broadwave.backend.ltd.data.rebar;

import com.broadwave.backend.bscodes.SeriesCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author InSeok
 * Date : 2019-05-08
 * Time : 17:04
 * Remark :
 */
public interface RebarCorrosionRepository extends JpaRepository<RebarCorrosion,Long> {
    List<RebarCorrosion> findBySeriesCode(SeriesCode seriesCode);
}
