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
package com.lucas.entity.ui.configuration;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author Adarsh
 * @Product Lucas.
 * Created By: Adarsh
 * Created On Date: 12/22/2014  Time: 3:44 PM
 * This Class provide the implementation for the functionality of
 * storing the configuration for the customer.
 */
@Entity
@Table(name = "lucas_customer_configuration")
public class CustomerConfiguration implements Serializable {

    private Integer clientId;

    private String clientName;

    private String defaultLanguage;

    private byte[] clientLogo;

    private String createdByUserName;

    private Date createdDateAndTime;

    private String updatedByUserName;

    private Date updatedDataTime;

    public CustomerConfiguration() {
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "client_id",length = 20)
    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    @Column(name = "client_name",length = 100, nullable = false ,unique = true)
    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Column(name = "default_language",length = 10,nullable = false)
    public String getDefaultLanguage() {
        return defaultLanguage;
    }

    public void setDefaultLanguage(String defaultLanguage) {
        this.defaultLanguage = defaultLanguage;
    }

    @Lob
    @Column(name = "client_logo", nullable = false, columnDefinition = "mediumblob")
    public byte[] getClientLogo() {
        return clientLogo;
    }

    public void setClientLogo(byte[] clientLogo) {
        this.clientLogo = clientLogo;
    }

    @Column(name = "created_by_username",length = 100,nullable = false)
    public String getCreatedByUserName() {
        return createdByUserName;
    }

    public void setCreatedByUserName(String createdByUserName) {
        this.createdByUserName = createdByUserName;
    }

    @Column(name = "updated_by_username",length = 100,nullable = false)
    public String getUpdatedByUserName() {
        return updatedByUserName;
    }

    public void setUpdatedByUserName(String updatedByUserName) {
        this.updatedByUserName = updatedByUserName;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_date_time", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    public Date getCreatedDateAndTime() {
        return createdDateAndTime;
    }

    public void setCreatedDateAndTime(Date createdDataAndTime) {
        this.createdDateAndTime = createdDataAndTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_date_time")
    public Date getUpdatedDataTime() {
        return updatedDataTime;
    }

    public void setUpdatedDataTime(Date updatedDataTime) {
        this.updatedDataTime = updatedDataTime;
    }
}
