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
package com.lucas.entity.application;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.util.Date;

import javax.persistence.*;

/**
 * The persistent class for the Code_Lookup database table.
 *
 * @author DiepLe
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, include = "all")
@Table(name = "code_lookup")
public class CodeLookup implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    // composite primary keys for fields code_name & code_value
    @EmbeddedId
    private CodeLookupPK id;

    private Long displayOrder;
    private String updatedBy;
    private Date updatedTimestamp;


    @EmbeddedId
    @AttributeOverrides( { @AttributeOverride(name = "codeName", column = @Column(name = "code_name", nullable = false)),
            @AttributeOverride(name = "codeValue", column = @Column(name = "code_value", nullable = false))})
    public CodeLookupPK getId() {
        return id;
    }

    public void setId(CodeLookupPK id) {
        this.id = id;
    }

    @Column(name = "display_order", nullable = false)
    public Long getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Long displayOrder) {
        this.displayOrder = displayOrder;
    }

    @Column(name = "updated_by", length = 100, nullable = true)
    public String getUpdatedBy() {
        return updatedBy;
    }


    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_timestamp", nullable = true)
    public Date getUpdatedTimestamp() {
        return updatedTimestamp;
    }

    public void setUpdatedTimestamp(Date updatedTimestamp) {
        this.updatedTimestamp = updatedTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CodeLookup that = (CodeLookup) o;

        if (!displayOrder.equals(that.displayOrder)) return false;
        if (!id.equals(that.id)) return false;
        if (updatedBy != null ? !updatedBy.equals(that.updatedBy) : that.updatedBy != null) return false;
        if (updatedTimestamp != null ? !updatedTimestamp.equals(that.updatedTimestamp) : that.updatedTimestamp != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + displayOrder.hashCode();
        result = 31 * result + (updatedBy != null ? updatedBy.hashCode() : 0);
        result = 31 * result + (updatedTimestamp != null ? updatedTimestamp.hashCode() : 0);
        return result;
    }
}