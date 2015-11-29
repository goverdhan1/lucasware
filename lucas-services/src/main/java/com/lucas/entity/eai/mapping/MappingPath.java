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
package com.lucas.entity.eai.mapping;


import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang3.builder.EqualsBuilder;

import com.lucas.entity.eai.transformation.Transformation;

/**
 * Entity class for all the mapping paths, having details for all the
 * transformations needed to process the event in the system.
 * 
 * @author Prafull
 * 
 */

@Entity
@Table(name = "eai_mapping_path")
public class MappingPath implements java.io.Serializable {

	/**
	 * Serial version id
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Id for uniquely identifying one mapping path 
	 */
	private Long mappingPathId;
	/**
	 * Mapping entity establishing the many to one relationship between both the
	 * entities
	 */
	private Mapping mapping;
	/**
	 * Source transformation id in the mapping path
	 */
	private Transformation fromTransformation;
	/**
	 * The sequence at which the path should be executed
	 */
	private Integer mappingPathSeq;
	/**
	 * Destination transformation id in the mapping path
	 */
	private Transformation toTransformation;

	public MappingPath() {
	}

	public MappingPath(Mapping mapping) {
		this.mapping = mapping;
	}

	public MappingPath(Mapping mapping, Transformation fromTransformation,
			Integer mappingPathSeq, Transformation toTransformation) {
		this.mapping = mapping;
		this.fromTransformation = fromTransformation;
		this.mappingPathSeq = mappingPathSeq;
		this.toTransformation = toTransformation;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "mapping_path_id", unique = true, nullable = false)
	public Long getMappingPathId() {
		return this.mappingPathId;
	}

	public void setMappingPathId(Long mappingPathId) {
		this.mappingPathId = mappingPathId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mapping_id", nullable = false)
	public Mapping getMapping() {
		return this.mapping;
	}

	public void setMapping(Mapping mapping) {
		this.mapping = mapping;
	}

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "from_transformation_id")
	public Transformation getFromTransformation() {
		return this.fromTransformation;
	}

	public void setFromTransformation(Transformation fromTransformation) {
		this.fromTransformation = fromTransformation;
	}

	@Column(name = "mapping_path_seq")
	public Integer getMappingPathSeq() {
		return this.mappingPathSeq;
	}

	public void setMappingPathSeq(Integer mappingPathSeq) {
		this.mappingPathSeq = mappingPathSeq;
	}

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "to_transformation_id")
	public Transformation getToTransformation() {
		return this.toTransformation;
	}

	public void setToTransformation(Transformation toTransformation) {
		this.toTransformation = toTransformation;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MappingPath)) {
			return false;
		}
		MappingPath castOther = (MappingPath) other;
		return new EqualsBuilder()
				.append(mappingPathId, castOther.mappingPathId)
				.append(fromTransformation, castOther.fromTransformation)
				.append(toTransformation, castOther.toTransformation)
				.append(mapping, castOther.mapping).isEquals();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder().append(mappingPathId)
				.append(fromTransformation)
				.append(toTransformation)
				.append(mapping).toHashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("mappingPathId", mappingPathId)
				.append("fromTransformationId", fromTransformation)
				.append("toTransformationId", toTransformation)
				.append("mapping", mapping).toString();
	}
}
