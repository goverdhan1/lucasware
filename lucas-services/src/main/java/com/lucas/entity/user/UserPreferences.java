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
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * @author DiepLe
 *
 */

@Embeddable
public class UserPreferences implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private String dateFormat;
    private String timeFormat;
    private Long dataRefreshFrequency;

    public UserPreferences() {
    }

    public UserPreferences(String dateFormat, String timeFormat, Long dataRefreshFrequency) {
        this.dateFormat = dateFormat;
        this.timeFormat = timeFormat;
        this.dataRefreshFrequency = dataRefreshFrequency;
    }


    @Column(name = "date_format_pref", length = 10, nullable = false, columnDefinition = "DD-MM-YYYY", insertable=false)
    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Column(name = "time_format_pref", length = 10, nullable = false, columnDefinition = "24HOUR", insertable=false)
    public String getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }

    @Column(name = "data_refresh_frequency_pref", length = 10, nullable = false, columnDefinition = "24HOUR", insertable=false)
    public Long getDataRefreshFrequency() {
        return dataRefreshFrequency;
    }

    public void setDataRefreshFrequency(Long dataRefreshFrequency) {
        this.dataRefreshFrequency = dataRefreshFrequency;
    }

    @Override
    public String toString() {
        return "UserPreferences{" +
                "dateFormat='" + dateFormat + '\'' +
                ", timeFormat='" + timeFormat + '\'' +
                ", dataRefreshFrequency=" + dataRefreshFrequency +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserPreferences that = (UserPreferences) o;

        if (!dataRefreshFrequency.equals(that.dataRefreshFrequency)) return false;
        if (!dateFormat.equals(that.dateFormat)) return false;
        if (!timeFormat.equals(that.timeFormat)) return false;

        return true;
    }

}
