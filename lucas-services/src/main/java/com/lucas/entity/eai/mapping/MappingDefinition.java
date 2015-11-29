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
 * Entity class for the mapping definition, having definition details for the
 * mappings to execute for the event in the system.
 * 
 * @author Prafull
 * 
 */
@Entity
@Table(name = "eai_mapping_definition")
public class MappingDefinition implements java.io.Serializable {

	/**
	 * Serial version id
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Definition id to uniquely identify a mapping definition record
	 */
	private Long mappingDefinitionId;
	/**
	 * Mapping entity to define many to one relationship between both entities
	 */
	private Mapping eaiMapping;
	/**
	 * Name of the transformation entity
	 */
	private String transformationEntityName;
	/**
	 * The transformation id of the transformation which is needed to be
	 * performed
	 */
	private Long transformationId;

	public MappingDefinition() {
	}

	public MappingDefinition(Mapping eaiMapping,
			String transformationEntityName, Long transformationId) {
		this.eaiMapping = eaiMapping;
		this.transformationEntityName = transformationEntityName;
		this.transformationId = transformationId;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "mapping_definition_id", unique = true, nullable = false)
	public Long getMappingDefinitionId() {
		return this.mappingDefinitionId;
	}

	public void setMappingDefinitionId(Long mappingDefinitionId) {
		this.mappingDefinitionId = mappingDefinitionId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mapping_id")
	public Mapping getEaiMapping() {
		return this.eaiMapping;
	}

	public void setEaiMapping(Mapping eaiMapping) {
		this.eaiMapping = eaiMapping;
	}

	@Column(name = "transformation_entity_name", length = 120)
	public String getTransformationEntityName() {
		return this.transformationEntityName;
	}

	public void setTransformationEntityName(String transformationEntityName) {
		this.transformationEntityName = transformationEntityName;
	}

	@Column(name = "transformation_id")
	public Long getTransformationId() {
		return this.transformationId;
	}

	public void setTransformationId(Long transformationId) {
		this.transformationId = transformationId;
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
		if (!(other instanceof MappingDefinition)) {
			return false;
		}
		MappingDefinition castOther = (MappingDefinition) other;
		return new EqualsBuilder()
				.append(mappingDefinitionId, castOther.mappingDefinitionId)
				.append(eaiMapping, castOther.eaiMapping)
				.append(transformationEntityName,
						castOther.transformationEntityName)
				.append(transformationId, castOther.transformationId)
				.isEquals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder().append(mappingDefinitionId)
				.append(eaiMapping).append(transformationEntityName)
				.append(transformationId).toHashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("mappingDefinitionId", mappingDefinitionId)
				.append("eaiMapping", eaiMapping)
				.append("transformationEntityName", transformationEntityName)
				.append("transformationId", transformationId).toString();
	}

}
