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


import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang3.builder.EqualsBuilder;

import com.lucas.entity.eai.mapping.MappingPath;

/**
 * Entity class for the transformation, having details for the transformations
 * needs to be applied to execute the event in the system.
 * 
 * @author Prafull
 * 
 */
@Entity
@Table(name = "eai_transformation", uniqueConstraints = @UniqueConstraint(columnNames = "transformation_channel"))
public class Transformation implements java.io.Serializable {

	/**
	 * Serial version id
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Transformation id to identify records uniquely
	 */
	private Long transformationId;
	/**
	 * The channel on which the transformer expects the message
	 */
	private String transformationChannel;
	/**
	 * The transformation name
	 */
	private String transformationName;
	/**
	 * Type of the transformation
	 */
	private String transformationType;
	/**
	 * Relationship with the transformation definition, which has the detailed
	 * definition about the transformation
	 */
	private Set<TransformationDefinition> eaiTransformationDefinitions = new HashSet<TransformationDefinition>();

	public Transformation() {
	}

	public Transformation(String transformationChannel) {
		this.transformationChannel = transformationChannel;
	}

	public Transformation(String transformationChannel,
			String transformationType, Set<TransformationDefinition> eaiTransformationDefinitions,
			String transformationName) {
		this.transformationChannel = transformationChannel;
		this.transformationType = transformationType;
		this.eaiTransformationDefinitions = eaiTransformationDefinitions;
		this.transformationName = transformationName;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "transformation_id", unique = true, nullable = false)
	public Long getTransformationId() {
		return this.transformationId;
	}

	public void setTransformationId(Long transformationId) {
		this.transformationId = transformationId;
	}

	@Column(name = "transformation_channel", unique = true, nullable = false, length = 60)
	public String getTransformationChannel() {
		return this.transformationChannel;
	}

	public void setTransformationChannel(String transformationChannel) {
		this.transformationChannel = transformationChannel;
	}
	
	@Column(name = "transformation_name", unique = true, nullable = false, length = 60)
	public String getTransformationName() {
		return this.transformationName;
	}

	public void setTransformationName(String transformationName) {
		this.transformationName = transformationName;
	}

	@Column(name = "transformation_type", length = 120)
	public String getTransformationType() {
		return this.transformationType;
	}

	public void setTransformationType(String transformationType) {
		this.transformationType = transformationType;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "eaiTransformation")
	public Set<TransformationDefinition> getEaiTransformationDefinitions() {
		return this.eaiTransformationDefinitions;
	}

	public void setEaiTransformationDefinitions(Set<TransformationDefinition> eaiTransformationDefinitions) {
		this.eaiTransformationDefinitions = eaiTransformationDefinitions;
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
		if (!(other instanceof Transformation)) {
			return false;
		}
		Transformation castOther = (Transformation) other;
		return new EqualsBuilder()
				.append(transformationId, castOther.transformationId)
				.append(transformationChannel, castOther.transformationChannel)
				.append(transformationType, castOther.transformationType)
				.isEquals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder().append(transformationId)
				.append(transformationChannel).append(transformationType)
				.toHashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("transformationId", transformationId)
				.append("transformationChannel", transformationChannel)
				.append("transformationType", transformationType).toString();
	}
}
