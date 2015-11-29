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

import com.lucas.entity.BaseEntity;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;


@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, include = "all")
@Table(name = "supported_language")
public class SupportedLanguage extends BaseEntity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private String languageCode;
    private Boolean amdLanguage;
    private Boolean hhLanguage;
    private Boolean j2uLanguage;
    private Boolean u2jLanguage;


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "language_code", length = 10)
    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    @Column(name = "amdLanguage", nullable = false)
    public Boolean getAmdLanguage() {
        return amdLanguage;
    }

    public void setAmdLanguage(Boolean amdLanguage) {
        this.amdLanguage = amdLanguage;
    }

    @Column(name = "hhLanguage", nullable = false)
    public Boolean getHhLanguage() {
        return hhLanguage;
    }

    public void setHhLanguage(Boolean hhLanguage) {
        this.hhLanguage = hhLanguage;
    }

    @Column(name = "j2uLanguage", nullable = false)
    public Boolean getJ2uLanguage() {
        return j2uLanguage;
    }

    public void setJ2uLanguage(Boolean j2uLanguage) {
        this.j2uLanguage = j2uLanguage;
    }

    @Column(name = "u2jLanguage", nullable = false)
    public Boolean getU2jLanguage() {
        return u2jLanguage;
    }

    public void setU2jLanguage(Boolean u2jLanguage) {
        this.u2jLanguage = u2jLanguage;
    }


    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SupportedLanguage)) {
            return false;
        }
        SupportedLanguage castOther = (SupportedLanguage) other;
        return new EqualsBuilder().append(languageCode, castOther.languageCode)
                .isEquals();
    }


    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("languageCode", languageCode).toString();
    }

}