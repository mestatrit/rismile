package com.risetek.keke.server.db;

import java.util.Date;
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
    private String firstName;

    @Persistent
    private String lastName;

    @Persistent
    private Date hireDate;

    @Persistent
    private Long parentNode;
    
    @Persistent
    private Long childrenNode;
    
    @Persistent
    private Long brotherNode;
    
    public DBNode(String firstName, String lastName, Date hireDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.hireDate = hireDate;
    }

    // Accessors for the fields.  JDO doesn't use these, but your application does.

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    } 

    // ... other accessors...
}