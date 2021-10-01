package com.broadwave.backend.env.weather;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Minkyu
 * Date : 2020-11-05
 * Time :
 * Remark :
 */
public interface WeatherRepository extends JpaRepository<Weather,Long> {

}
