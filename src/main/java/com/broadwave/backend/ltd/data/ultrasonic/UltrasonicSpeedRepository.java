package com.broadwave.backend.ltd.data.ultrasonic;

import com.broadwave.backend.bscodes.SeriesCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author InSeok
 * Date : 2019-05-08
 * Time : 14:22
 * Remark :
 */
public interface UltrasonicSpeedRepository extends JpaRepository<UltrasonicSpeed,Long> {
    List<UltrasonicSpeed> findBySeriesCode(SeriesCode seriesCode);

}
