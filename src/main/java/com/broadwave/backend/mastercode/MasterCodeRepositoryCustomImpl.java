package com.broadwave.backend.mastercode;

import com.broadwave.backend.bscodes.CodeType;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

/**
 * @author Minkyu
 * Date : 2021-08-06
 * Remark :
 */
@Repository
public class MasterCodeRepositoryCustomImpl extends QuerydslRepositorySupport implements MasterCodeRepositoryCustom {

    public MasterCodeRepositoryCustomImpl() {
        super(MasterCode.class);
    }

    @Override
    public Page<MasterCodeDto> findAllBySearchStrings(CodeType codeType, String code, String name, Pageable pageable) {
        QMasterCode qMasterCode = QMasterCode.masterCode;
        JPQLQuery<MasterCodeDto> query = from(qMasterCode)
                .select(Projections.constructor(MasterCodeDto.class,
                        qMasterCode.id,
                        qMasterCode.codeType,
                        qMasterCode.code,
                        qMasterCode.name,
                        qMasterCode.remark
                ));

        if (codeType != null){
            query.where(qMasterCode.codeType.eq(codeType));
        }
        if (code != null && !code.isEmpty()){
            query.where(qMasterCode.code.containsIgnoreCase(code));
        }
        if (name != null && !name.isEmpty()){
            query.where(qMasterCode.name.containsIgnoreCase(name));
        }

        query.orderBy(qMasterCode.codeType.asc());
        query.orderBy(qMasterCode.code.asc());

        final List<MasterCodeDto> masterCodes = Objects.requireNonNull(getQuerydsl()).applyPagination(pageable, query).fetch();
        return new PageImpl<>(masterCodes, pageable, query.fetchCount());

    }
}
