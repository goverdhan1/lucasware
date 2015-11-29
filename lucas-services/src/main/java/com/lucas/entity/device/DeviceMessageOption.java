/*
 * Lucas Systems Inc 11279 Perry Highway Wexford PA 15090 United States
 * 
 * 
 * The information in this file contains trade secrets and confidential information which is the
 * property of Lucas Systems Inc.
 * 
 * All trademarks, trade names, copyrights and other intellectual property rights created,
 * developed, embodied in or arising in connection with this software shall remain the sole property
 * of Lucas Systems Inc.
 * 
 * Copyright (c) Lucas Systems, 2014 ALL RIGHTS RESERVED
 */
package com.lucas.entity.device;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "lucas_mobile_app_message_option")
public class DeviceMessageOption implements java.io.Serializable {

    /**
     * Serial version Id
     */
    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    private String Id;

    /**
     * Password Required
     */
    private boolean requirePassword;

    /**
     * Current Server Time UTC
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date currentServerUTC;

    /**
     * Application Name
     */
    private String appName;

    /**
     * Application Version
     */
    private String appVersion;

    /**
     * SPAPI Messaging Version
     */
    private String spapiMessagingVersion;

    /**
     * Default Display Language Code
     */
    private String defaultDisplayLangCode;

    /**
     * Additional Options
     */
    private String additionalOptions;

    /**
     * Building
     */
    private Building building;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "mobile_app_option_id", unique = true, nullable = false)
    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    
    @Column(name = "require_password", length = 1)
    public boolean getRequirePassword() {
        return requirePassword;
    }

    
    public void setRequirePassword(boolean requirePassword) {
        this.requirePassword = requirePassword;
    }


    @Column(name = "current_server_utc", columnDefinition = "TIMESTAMP")
    public Date getCurrentServerUTC() {
        return currentServerUTC;
    }
    
    public void setCurrentServerUTC(Date currentServerUTC) {
        this.currentServerUTC = currentServerUTC;
    }

    @Column(name = "application_name", length = 50)
    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Column(name = "application_version", length = 20)
    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    @Column(name = "spapi_messaging_version", length = 2)
    public String getSpapiMessagingVersion() {
        return spapiMessagingVersion;
    }

    public void setSpapiMessagingVersion(String spapiMessagingVersion) {
        this.spapiMessagingVersion = spapiMessagingVersion;
    }

    @Column(name = "default_display_language_code", length = 5)
    public String getDefaultDisplayLangCode() {
        return defaultDisplayLangCode;
    }

    public void setDefaultDisplayLangCode(String defaultDisplayLangCode) {
        this.defaultDisplayLangCode = defaultDisplayLangCode;
    }

    @Column(name = "additional_options", length = 255)
    public String getAdditionalOptions() {
        return additionalOptions;
    }

    public void setAdditionalOptions(String additionalOptions) {
        this.additionalOptions = additionalOptions;
    }

    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "building_Id")
    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DeviceMessageOption)) {
            return false;
        }
        DeviceMessageOption castOther = (DeviceMessageOption) other;
        return new EqualsBuilder().append(Id, castOther.Id).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder().append(Id).toHashCode();
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("Id", Id).toString();
    }


}
