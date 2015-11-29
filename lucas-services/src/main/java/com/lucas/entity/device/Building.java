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

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "lucas_building")
public class Building implements java.io.Serializable {

    /**
     * Serial version Id
     */
    private static final long serialVersionUID = 1L;

    /**
     * Building Id
     */
    private String buildingId;

    /**
     * Building Name
     */
    private String buildingName;

    /**
     * Address
     */
    private String address1;

    /**
     * Address
     */
    private String address2;

    /**
     * City
     */
    private String city;

    /**
     * State
     */
    private String state;

    /**
     * ZipCode
     */
    private String zipCode;

    /**
     * Phone
     */
    private String phone;

    /**
     * Fax
     */
    private String fax;

    /**
     * Email
     */
    private String email;

    /**
     * Device
     */
    private List<Device> device;

    /**
     * DeviceAppOption
     */
    private List<DeviceMessageOption> deviceMessageOption;

    public Building() {

    }

    @Id
    @Column(name = "building_Id")
    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    @Column(name = "building_name", length = 50)
    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    @Column(name = "address1", length = 100)
    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    @Column(name = "address2", length = 100)
    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    @Column(name = "city", length = 50)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "state", length = 100)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Column(name = "zipcode", length = 10)
    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Column(name = "phone", length = 50)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "fax", length = 50)
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    @Column(name = "email", length = 100)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @OneToMany(mappedBy = "building")
    public List<Device> getDevice() {
        return device;
    }

    public void setDevice(List<Device> device) {
        this.device = device;
    }

    @OneToMany(mappedBy = "building")
    public List<DeviceMessageOption> getDeviceMessageOption() {
        return deviceMessageOption;
    }

    public void setDeviceMessageOption(List<DeviceMessageOption> deviceMessageOption) {
        this.deviceMessageOption = deviceMessageOption;
    }

}
