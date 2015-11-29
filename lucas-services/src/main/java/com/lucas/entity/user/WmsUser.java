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
package com.lucas.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lucas.entity.BaseEntity;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, include = "all")
@Table(name = "wms_user")
public class WmsUser extends BaseEntity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long userId;
    private String hostLogin;
    private String hostHashedPassword;
    private String hostPlainTextPassword;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", length = 20)
    @JsonProperty("id")
    @JsonIgnore
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "user_id", unique = true, length = 20)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "host_login", unique = true, length = 100)
    public String getHostLogin() {
        return hostLogin;
    }

    public void setHostLogin(String hostLogin) {
        this.hostLogin = hostLogin;
    }

    @Column(name = "host_hashed_password", unique = true, length = 100)
    public String getHostHashedPassword() {
        return hostHashedPassword;
    }

    public void setHostHashedPassword(String hostHashedPassword) {
        this.hostHashedPassword = hostHashedPassword;
    }

    @Transient
    @JsonIgnore
    public String getHostPlainTextPassword() {
        return hostPlainTextPassword;
    }

    public void setHostPlainTextPassword(String hostPlainTextPassword) {
        this.hostPlainTextPassword = hostPlainTextPassword;
    }


    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof WmsUser)) {
            return false;
        }
        WmsUser castOther = (WmsUser) other;
        return new EqualsBuilder().append(userId, castOther.userId)
                .isEquals();
    }


    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("userId", userId)
                .append("hostLogin", hostLogin).toString();
    }


}
