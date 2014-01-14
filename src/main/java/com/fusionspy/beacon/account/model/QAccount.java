package com.fusionspy.beacon.account.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.*;
import com.mysema.query.types.path.*;

import javax.annotation.Generated;


/**
 * QAccount is a Querydsl query type for Account
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QAccount extends EntityPathBase<Account> {

    private static final long serialVersionUID = -541457448;

    public static final QAccount account = new QAccount("account");

    public final DateTimePath<java.util.Date> createTime = createDateTime("createTime", java.util.Date.class);

    public final StringPath email = createString("email");

    public final StringPath id = createString("id");

    public final StringPath loginName = createString("loginName");

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final StringPath phone = createString("phone");

    public final StringPath status = createString("status");

    public QAccount(String variable) {
        super(Account.class, forVariable(variable));
    }

    public QAccount(Path<? extends Account> entity) {
        super(entity.getType(), entity.getMetadata());
    }

    public QAccount(PathMetadata<?> metadata) {
        super(Account.class, metadata);
    }

}

