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

import com.lucas.entity.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author Adarsh
 * @Product Lucas.
 * Created By: Adarsh
 * Created On Date: 12/22/2014  Time: 3:45 PM
 * This Class provide the implementation for the functionality for
 * Lucas Configurations like logo and etc.
 */

@Entity
@Table(name = "lucas_setting")
public class LucasConfiguration implements Serializable {


    private Long id;

    private byte[] lucasLogo;

    private User user;

    private String createdByUserName;

    private Date createdDataTime;

    private String updatedByUserName;

    private Date updatedDataTime;

    public LucasConfiguration() {
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "configuration_id", length = 20)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Lob
    @Column(name = "lucas_logo", nullable = false, columnDefinition = "mediumblob")
    public byte[] getLucasLogo() {
        return lucasLogo;
    }

    public void setLucasLogo(byte[] lucasLogo) {
        this.lucasLogo = lucasLogo;
    }

    @OneToOne
    @JoinColumn(name = "super_user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "created_by_username", length = 100, nullable = false)
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
    @Column(name="created_date_time", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    public Date getCreatedDataTime() {
        return createdDataTime;
    }

    public void setCreatedDataTime(Date createdDataTime) {
        this.createdDataTime = createdDataTime;
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

