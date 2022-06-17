package com.broadwave.backend.earthquake;

import com.broadwave.backend.earthquake.EarthQuakeDtos.EarthQuakeDto;
import com.broadwave.backend.earthquake.EarthQuakeDtos.EarthQuakeListDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Minkyu
 * Date : 2022-06-10
 * Remark :
 */
@Repository
public class EarthQuakeRepositoryCustomImpl extends QuerydslRepositorySupport implements EarthQuakeRepositoryCustom {

    public EarthQuakeRepositoryCustomImpl() {
        super(EarthQuake.class);
    }

    @Override
    public List<EarthQuakeListDto> findByEarthQuakeList(String eqBridge) {

        QEarthQuake earthQuake = QEarthQuake.earthQuake;

        JPQLQuery<EarthQuakeListDto> query = from(earthQuake)
                .select(Projections.constructor(EarthQuakeListDto.class,
                        earthQuake.eqBridge,
                        earthQuake.eqLocation,
                        earthQuake.eqRank,
                        earthQuake.eqLength
                ));

        if (!eqBridge.equals("")) {
            query.where(earthQuake.eqBridge.likeIgnoreCase("%"+eqBridge+"%"));
        }

        return query.fetch();
    }
    @Override
    public EarthQuakeDto findByEqBridge(String eqBridge) {

        QEarthQuake earthQuake = QEarthQuake.earthQuake;

        JPQLQuery<EarthQuakeDto> query = from(earthQuake)
                .where(earthQuake.eqBridge.eq(eqBridge))
                .select(Projections.constructor(EarthQuakeDto.class,
                        earthQuake.id,
                        earthQuake.eqBridge,
                        earthQuake.eqLocation,
                        earthQuake.eqRank,
                        earthQuake.eqLength,
                        earthQuake.eqConfiguration,
                        earthQuake.eqPillar,
                        earthQuake.eqDivision,
                        earthQuake.eqGirder,
                        earthQuake.eqBridgeClassification
                ));

        return query.fetchOne();
    }
}
