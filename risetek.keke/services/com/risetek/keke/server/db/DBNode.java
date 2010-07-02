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
    private String nodeName;

    @Persistent
    private Long nodeNameID;
    
/*
    @Persistent
    private String lastName;

    @Persistent
    private Date hireDate;
*/
    @Persistent
    private Long parentNode;
    
    @Persistent
    private Long childrenNode;
    
    @Persistent
    private Long brotherNode;

    @Persistent
    private Long versionNode;
    
    @Persistent
    private Long actionNode;

    public DBNode(String nodeName) {
        this.nodeName = nodeName;
    }
}