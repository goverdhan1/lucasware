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
package com.lucas.entity.ui.canvas;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.lucas.entity.BaseEntity;

@Embeddable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, include = "all")
public class OpenUserCanvas extends BaseEntity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long userId;
    private Integer displayOrder;
    private Canvas canvas;


    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }
    
    @ManyToOne
    @JoinColumn(name="canvas_id")
	public Canvas getCanvas() {
		return canvas;
	}

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}


    @Override
    public int hashCode() {
		return new HashCodeBuilder().append(userId)
				.toHashCode();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof OpenUserCanvas)) {
            return false;
        }
        OpenUserCanvas castOther = (OpenUserCanvas) other;
        return new EqualsBuilder().append(userId, castOther.userId)
                .isEquals();
    }


    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("userId", userId)
                .toString();
    }

}