package com.broadwave.backend.env.tunnel;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author InSeok
 * Date : 2019-06-21
 * Time : 14:58
 * Remark :
 */
public interface TunnelRepository extends JpaRepository<Tunnel,Long> {
    List<Tunnel> findByFacilityNameContaining(String facilityName);

}
