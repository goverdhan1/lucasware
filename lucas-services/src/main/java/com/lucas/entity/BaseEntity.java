/*
Lucas Systems Inc
11279 Perry Highway
Wexford
PA 15090
United States


The information in this file contains trade secrets and confidential
information which is the property of Lucas Systems Inc.

All trademarks, trade names, copyrights and other intellectual property
rights created, developed, embodied in or arising in connection with
this software shall remain the sole property of Lucas Systems Inc.

Copyright (c) Lucas Systems, 2014
ALL RIGHTS RESERVED

 */
package com.lucas.entity;

import com.lucas.services.security.SecurityService;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class BaseEntity implements java.io.Serializable{

    private static final long serialVersionUID = 1L;
    
    private String createdByUserName;
    private Date createdDateTime;
    private String updatedByUserName;
    private Date updatedDateTime;
    private static String loggedInUserName = null;

    public BaseEntity() {}

    @Column(name="created_by_username",length=100, unique=false ,nullable=false)
    public String getCreatedByUserName() {
        return createdByUserName;
    }

    public void setCreatedByUserName(String createdByUserName) {
        this.createdByUserName = createdByUserName;
    }

    @Column(name = "updated_by_username", length = 100, nullable = false)
    public String getUpdatedByUserName() {
        return updatedByUserName;
    }

    public void setUpdatedByUserName(String updatedByUserName) {
        this.updatedByUserName = updatedByUserName;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    public Date getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(Date createdDataAndTime) {
        this.createdDateTime = createdDataAndTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_date_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", nullable = false)
    public Date getUpdatedDateTime() {
        return updatedDateTime;
    }

    public void setUpdatedDateTime(Date updatedDateTime) {
        this.updatedDateTime = updatedDateTime;
    }


    @PrePersist
    protected void onCreate() {
        if (loggedInUserName != null && loggedInUserName.length() > 0) {
            createdByUserName = loggedInUserName;
        }
        updatedDateTime = createdDateTime = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        if (loggedInUserName != null && loggedInUserName.length() > 0) {
            updatedByUserName = loggedInUserName;
            updatedDateTime = new Date();
        }
    }

    public void setAuditUsername (SecurityService securityService) {
        if (securityService != null && securityService.getLoggedInUsername() != null && securityService.getLoggedInUsername().length() > 0) {
            loggedInUserName = securityService.getLoggedInUsername();
        }
        else {
            loggedInUserName = null;
        }
    }

}

