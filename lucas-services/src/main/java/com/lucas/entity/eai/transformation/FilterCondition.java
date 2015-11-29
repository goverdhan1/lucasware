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
package com.lucas.entity.eai.transformation;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * Entity class for the filter condition, having details for the filter
 * conditions transformations needs to be applied to execute the event in the
 * system.
 * 
 * @author Prafull
 * 
 */

@Entity
@Table(name = "eai_filter_condition")
public class FilterCondition implements java.io.Serializable {

	/**
	 * Serial version id
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Filter condition id to uniquely identify the filter condition
	 */
	private Long filterConditionId;
	/**
	 * The transformation definition for the filter condition
	 */
	private TransformationDefinition eaiTransformationDefinition;
	/**
	 * The actual filter condition
	 */
	private String filterCondition;

	public FilterCondition() {
	}

	public FilterCondition(
			TransformationDefinition eaiTransformationDefinition,
			String filterCondition) {
		this.eaiTransformationDefinition = eaiTransformationDefinition;
		this.filterCondition = filterCondition;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "filter_condition_id", unique = true, nullable = false)
	public Long getFilterConditionId() {
		return this.filterConditionId;
	}

	public void setFilterConditionId(Long filterConditionId) {
		this.filterConditionId = filterConditionId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "transformation_definition_id", nullable = false)
	public TransformationDefinition getEaiTransformationDefinition() {
		return this.eaiTransformationDefinition;
	}

	public void setEaiTransformationDefinition(
			TransformationDefinition eaiTransformationDefinition) {
		this.eaiTransformationDefinition = eaiTransformationDefinition;
	}

	@Column(name = "filter_condition", nullable = false, length = 256)
	public String getFilterCondition() {
		return this.filterCondition;
	}

	public void setFilterCondition(String filterCondition) {
		this.filterCondition = filterCondition;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FilterCondition)) {
			return false;
		}
		FilterCondition castOther = (FilterCondition) other;
		return new EqualsBuilder()
				.append(filterConditionId, castOther.filterConditionId)
				.append(eaiTransformationDefinition,
						castOther.eaiTransformationDefinition)
				.append(filterCondition, castOther.filterCondition).isEquals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder().append(filterConditionId)
				.append(eaiTransformationDefinition).append(filterCondition)
				.toHashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("filterConditionId", filterConditionId)
				.append("eaiTransformationDefinition",
						eaiTransformationDefinition)
				.append("filterConditionId", filterConditionId).toString();
	}

}
