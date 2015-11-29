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

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, include = "all")
@Table(name = "shift")
public class Shift extends BaseEntity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private Long shiftId;
    private String shiftName;
    private String startTime;
    private String endTime;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    //@JsonProperty("id")
    @Column(name = "shift_id")
    public Long getShiftId() {
        return shiftId;
    }

    public void setShiftId(Long shiftId) {
        this.shiftId = shiftId;
    }

    @Column(name = "shift_name", unique = true, length = 50, nullable = false)
    public String getShiftName() {
        return shiftName;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }


    @Column(name = "start_time", length = 50)
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Column(name = "end_time", length = 50)
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Shift)) {
            return false;
        }
        Shift castOther = (Shift) other;
        return new EqualsBuilder().append(shiftId, castOther.shiftId)
                .isEquals();
    }


    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("shiftId", shiftId)
                .append("shiftName", shiftName).toString();
    }


}