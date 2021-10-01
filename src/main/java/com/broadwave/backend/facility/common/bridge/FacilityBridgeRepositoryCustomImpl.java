package com.broadwave.backend.facility.common.bridge;

import com.broadwave.backend.facility.common.Facility;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Minkyu
 * Date : 2019-10-15
 * Remark :
 */
@Repository
public class FacilityBridgeRepositoryCustomImpl extends QuerydslRepositorySupport implements FacilityBridgeRepositoryCustom{

    public FacilityBridgeRepositoryCustomImpl() {
        super(Facility.class);
    }

    @Override
    public List<FacilityBridgeDto> findById(Long id) {
        JPQLQuery<FacilityBridgeDto> query =  findByIdQuerydsl(id);

        return query.fetch();
    }

    public JPQLQuery<FacilityBridgeDto> findByIdQuerydsl(Long id) {

        QFacilityBridge qFacilityBridge = QFacilityBridge.facilityBridge;

        return from(qFacilityBridge)
                .select(Projections.constructor(FacilityBridgeDto.class,
                        qFacilityBridge.id,
                        qFacilityBridge.faPscBridgeNumber,
                        qFacilityBridge.faPscBridgeType,
                        qFacilityBridge.faPscBridgeWidth,
                        qFacilityBridge.faPscBridgeMaxspanlength,
                        qFacilityBridge.faPscBridgeBridgeStep,
                        qFacilityBridge.faPscBridgeHeight,
                        qFacilityBridge.faPscBridgeSeaheight,
                        qFacilityBridge.faPscBridgeSlabThickness,
                        qFacilityBridge.faPscBridgeSlabWidth,
                        qFacilityBridge.faPscBridgePierType,
                        qFacilityBridge.faPscBridgePierNumber,
                        qFacilityBridge.faPscBridgePierSupportNumber,
                        qFacilityBridge.faPscBridgeExpansionjointNumber
                ));
    }

}
