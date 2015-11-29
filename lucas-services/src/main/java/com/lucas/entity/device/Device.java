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

import javax.persistence.*;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang3.builder.EqualsBuilder;


@Entity
@Table(name = "lucas_device")
public class Device implements java.io.Serializable {

    /**
     * Serial version Id
     */
    private static final long serialVersionUID = 1L;

    /**
     * Device Id
     */
    private String deviceId;

    /**
     * IP Address
     */
    private String ipAddress;

    /**
     * Qualified DNS Server Name
     */
    private String qualifiedServerName;
    
    /**
     * Building
     */
    private Building building;

    @Id
    @Column(name = "device_Id")
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Column(name="ip_address", length=20)
    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Column(name="qualified_server_name", length=50)
    public String getQualifiedServerName() {
        return qualifiedServerName;
    }

    public void setQualifiedServerName(String qualifiedServerName) {
        this.qualifiedServerName = qualifiedServerName;
    }

    @ManyToOne(cascade={CascadeType.REFRESH},fetch=FetchType.EAGER)
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
        if (!(other instanceof Device)) {
            return false;
        }
        Device castOther = (Device) other;
        return new EqualsBuilder().append(deviceId, castOther.deviceId).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder().append(deviceId).toHashCode();
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("deviceId", deviceId).toString();
    }


}
