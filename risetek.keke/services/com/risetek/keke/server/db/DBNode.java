package com.risetek.keke.server.db;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)

public class DBNode {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;

    @Persistent
    private Long StickletsID;

    @Persistent
    private String nodeName;

    @Persistent
    private String imgName;

    @Persistent
    private Long nodeNameID;
    
    @Persistent
    private Long versionNode;
    

    public DBNode(String nodeName) {
        this.nodeName = nodeName;
    }
}