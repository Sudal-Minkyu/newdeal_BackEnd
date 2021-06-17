package com.broadwave.backend.teams.teamfile;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTeamFile is a Querydsl query type for TeamFile
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTeamFile extends EntityPathBase<TeamFile> {

    private static final long serialVersionUID = 1376373084L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTeamFile teamFile = new QTeamFile("teamFile");

    public final StringPath fileAddress = createString("fileAddress");

    public final DateTimePath<java.time.LocalDateTime> fileInsertDate = createDateTime("fileInsertDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> fileSize = createNumber("fileSize", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.broadwave.backend.teams.QTeam teamId;

    public QTeamFile(String variable) {
        this(TeamFile.class, forVariable(variable), INITS);
    }

    public QTeamFile(Path<? extends TeamFile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTeamFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTeamFile(PathMetadata metadata, PathInits inits) {
        this(TeamFile.class, metadata, inits);
    }

    public QTeamFile(Class<? extends TeamFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.teamId = inits.isInitialized("teamId") ? new com.broadwave.backend.teams.QTeam(forProperty("teamId")) : null;
    }

}

