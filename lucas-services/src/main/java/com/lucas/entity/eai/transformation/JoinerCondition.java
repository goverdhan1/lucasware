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
 * Entity class for the filter condition, having details for the joiner
 * conditions transformations needs to be applied to execute the event in the
 * system.
 * 
 * @author Prafull
 * 
 */

@Entity
@Table(name = "eai_joiner_condition")
public class JoinerCondition implements java.io.Serializable {

	/**
	 * Serial version id
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Joiner condition id to uniquely identify the record
	 */
	private Long joinerConditionId;
	/**
	 * Transformation definition for this joiner
	 */
	private TransformationDefinition eaiTransformationDefinition;
	/**
	 * Joiner condition detail
	 */
	private String joinerConditionDetail;
	/**
	 * Joiner condition master
	 */
	private String joinerConditionMaster;
	/**
	 * Joiner condition operator
	 */
	private String joinerConditionOperator;

	public JoinerCondition() {
	}

	public JoinerCondition(
			TransformationDefinition eaiTransformationDefinition,
			String joinerConditionDetail, String joinerConditionMaster,
			String joinerConditionOperator) {
		this.eaiTransformationDefinition = eaiTransformationDefinition;
		this.joinerConditionDetail = joinerConditionDetail;
		this.joinerConditionMaster = joinerConditionMaster;
		this.joinerConditionOperator = joinerConditionOperator;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "joiner_condition_id", unique = true, nullable = false)
	public Long getJoinerConditionId() {
		return this.joinerConditionId;
	}

	public void setJoinerConditionId(Long joinerConditionId) {
		this.joinerConditionId = joinerConditionId;
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

	@Column(name = "joiner_condition_detail", nullable = false, length = 256)
	public String getJoinerConditionDetail() {
		return this.joinerConditionDetail;
	}

	public void setJoinerConditionDetail(String joinerConditionDetail) {
		this.joinerConditionDetail = joinerConditionDetail;
	}

	@Column(name = "joiner_condition_master", nullable = false, length = 256)
	public String getJoinerConditionMaster() {
		return this.joinerConditionMaster;
	}

	public void setJoinerConditionMaster(String joinerConditionMaster) {
		this.joinerConditionMaster = joinerConditionMaster;
	}

	@Column(name = "joiner_condition_operator", nullable = false, length = 2)
	public String getJoinerConditionOperator() {
		return this.joinerConditionOperator;
	}

	public void setJoinerConditionOperator(String joinerConditionOperator) {
		this.joinerConditionOperator = joinerConditionOperator;
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
		if (!(other instanceof JoinerCondition)) {
			return false;
		}
		JoinerCondition castOther = (JoinerCondition) other;
		return new EqualsBuilder()
				.append(joinerConditionId, castOther.joinerConditionId)
				.append(eaiTransformationDefinition,
						castOther.eaiTransformationDefinition)
				.append(joinerConditionDetail, castOther.joinerConditionDetail)
				.isEquals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder().append(joinerConditionId)
				.append(eaiTransformationDefinition)
				.append(joinerConditionDetail).toHashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("joinerConditionId", joinerConditionId)
				.append("eaiTransformationDefinition",
						eaiTransformationDefinition)
				.append("joinerConditionDetail", joinerConditionDetail)
				.toString();
	}

}
