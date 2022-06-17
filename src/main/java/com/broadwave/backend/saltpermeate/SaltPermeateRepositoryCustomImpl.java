package com.broadwave.backend.saltpermeate;

import com.broadwave.backend.saltpermeate.saltpermeateDtos.SaltPermeateListDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Minkyu
 * Date : 2022-06-13
 * Remark :
 */
@Repository
public class SaltPermeateRepositoryCustomImpl extends QuerydslRepositorySupport implements SaltPermeateRepositoryCustom {

    public SaltPermeateRepositoryCustomImpl() {
        super(SaltPermeate.class);
    }

    @Override
    public List<SaltPermeateListDto> findByStBridge(String stBridge) {

        QSaltPermeate saltPermeate = QSaltPermeate.saltPermeate;

        JPQLQuery<SaltPermeateListDto> query = from(saltPermeate)
                .select(Projections.constructor(SaltPermeateListDto.class,
                        saltPermeate.stId,
                        saltPermeate.stBridge,
                        saltPermeate.stLocation1,
                        saltPermeate.stLocation2,
                        saltPermeate.stCoordinateX,
                        saltPermeate.stCoordinateY,
                        saltPermeate.stFreeze,
                        saltPermeate.stSnow,
                        saltPermeate.stSalt
                ));

        query.where(saltPermeate.stBridge.likeIgnoreCase("%"+stBridge+"%"));
        query.orderBy(saltPermeate.stLocation1.desc(),saltPermeate.stLocation2.desc()).limit(1000);

        return query.fetch();
    }

}
