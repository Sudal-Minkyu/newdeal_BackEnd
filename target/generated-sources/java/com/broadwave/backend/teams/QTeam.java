package com.broadwave.backend.teams;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QTeam is a Querydsl query type for Team
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTeam extends EntityPathBase<Team> {

    private static final long serialVersionUID = -426265361L;

    public static final QTeam team = new QTeam("team");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath insert_id = createString("insert_id");

    public final DateTimePath<java.time.LocalDateTime> insertDateTime = createDateTime("insertDateTime", java.time.LocalDateTime.class);

    public final StringPath modify_id = createString("modify_id");

    public final DateTimePath<java.time.LocalDateTime> modifyDateTime = createDateTime("modifyDateTime", java.time.LocalDateTime.class);

    public final StringPath remark = createString("remark");

    public final StringPath teamcode = createString("teamcode");

    public final StringPath teamname = createString("teamname");

    public QTeam(String variable) {
        super(Team.class, forVariable(variable));
    }

    public QTeam(Path<? extends Team> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTeam(PathMetadata metadata) {
        super(Team.class, metadata);
    }

}

