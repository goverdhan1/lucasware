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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang3.builder.EqualsBuilder;
import com.lucas.entity.eai.message.MessageDefinition;

/**
 * Entity class for the transformation definition, having details for the
 * transformations definitions needs to be applied to execute the event in the
 * system.
 * 
 * @author Prafull
 * 
 */

@Entity
@Table(name = "eai_transformation_definition")
public class TransformationDefinition implements java.io.Serializable {

	/**
	 * Serial version id
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Transformation definition id to identify the records uniquely
	 */
	private Long transformationDefinitionId;
	/**
	 * The actual transformation to which this definition belongs to
	 */
	private Transformation eaiTransformation;
	/**
	 * The expression needed to transform the field value
	 */
	private String transformationExpression;
	/**
	 * The length of the transformation field
	 */
	private Integer transformationFieldLength;

	/**
	 * The name of the transformation field
	 */
	private String transformationFieldName;
	/**
	 * Transformation field sequence
	 */
	private int transformationFieldSeq;
	/**
	 * Transformation field type
	 */
	private String transformationFieldType;
	/**
	 * Variable field generated in the transformation
	 */
	private Boolean transformationFieldVar;
	/**
	 * All filter conditions belonging to the transformation
	 */
	private Set<FilterCondition> eaiFilterConditions = new HashSet<FilterCondition>();
	/**
	 * All joiner conditions belonging to the transformation
	 */
	private Set<JoinerCondition> eaiJoinerConditions = new HashSet<JoinerCondition>();
	/**
	 * Message definition object
	 */
	private MessageDefinition messageDefinition;

	public TransformationDefinition() {
	}

	public TransformationDefinition(Transformation eaiTransformation,
			String transformationFieldName, int transformationFieldSeq) {
		this.eaiTransformation = eaiTransformation;
		this.transformationFieldName = transformationFieldName;
		this.transformationFieldSeq = transformationFieldSeq;
	}

	public TransformationDefinition(Transformation eaiTransformation,
			String transformationExpression, Integer transformationFieldLength,
			String transformationFieldName, int transformationFieldSeq,
			String transformationFieldType, Boolean transformationFieldVar,
			Set<FilterCondition> eaiFilterConditions,
			Set<JoinerCondition> eaiJoinerConditions) {
		this.eaiTransformation = eaiTransformation;
		this.transformationExpression = transformationExpression;
		this.transformationFieldLength = transformationFieldLength;
		this.transformationFieldName = transformationFieldName;
		this.transformationFieldSeq = transformationFieldSeq;
		this.transformationFieldType = transformationFieldType;
		this.transformationFieldVar = transformationFieldVar;
		this.eaiFilterConditions = eaiFilterConditions;
		this.eaiJoinerConditions = eaiJoinerConditions;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "transformation_definition_id", unique = true, nullable = false)
	public Long getTransformationDefinitionId() {
		return this.transformationDefinitionId;
	}

	public void setTransformationDefinitionId(Long transformationDefinitionId) {
		this.transformationDefinitionId = transformationDefinitionId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "transformation_id", nullable = false)
	public Transformation getEaiTransformation() {
		return this.eaiTransformation;
	}

	public void setEaiTransformation(Transformation eaiTransformation) {
		this.eaiTransformation = eaiTransformation;
	}

	@Column(name = "transformation_expression", length = 80)
	public String getTransformationExpression() {
		return this.transformationExpression;
	}

	public void setTransformationExpression(String transformationExpression) {
		this.transformationExpression = transformationExpression;
	}

	@Column(name = "transformation_field_length")
	public Integer getTransformationFieldLength() {
		return this.transformationFieldLength;
	}

	public void setTransformationFieldLength(Integer transformationFieldLength) {
		this.transformationFieldLength = transformationFieldLength;
	}

	@Column(name = "transformation_field_name", nullable = false, length = 80)
	public String getTransformationFieldName() {
		return this.transformationFieldName;
	}

	public void setTransformationFieldName(String transformationFieldName) {
		this.transformationFieldName = transformationFieldName;
	}

	@Column(name = "transformation_field_seq", nullable = false)
	public int getTransformationFieldSeq() {
		return this.transformationFieldSeq;
	}

	public void setTransformationFieldSeq(int transformationFieldSeq) {
		this.transformationFieldSeq = transformationFieldSeq;
	}

	@Column(name = "transformation_field_type", length = 80)
	public String getTransformationFieldType() {
		return this.transformationFieldType;
	}

	public void setTransformationFieldType(String transformationFieldType) {
		this.transformationFieldType = transformationFieldType;
	}

	@Column(name = "transformation_field_var")
	public Boolean getTransformationFieldVar() {
		return this.transformationFieldVar;
	}

	public void setTransformationFieldVar(Boolean transformationFieldVar) {
		this.transformationFieldVar = transformationFieldVar;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "eaiTransformationDefinition")
	public Set<FilterCondition> getEaiFilterConditions() {
		return this.eaiFilterConditions;
	}

	public void setEaiFilterConditions(Set<FilterCondition> eaiFilterConditions) {
		this.eaiFilterConditions = eaiFilterConditions;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "eaiTransformationDefinition")
	public Set<JoinerCondition> getEaiJoinerConditions() {
		return this.eaiJoinerConditions;
	}

	public void setEaiJoinerConditions(Set<JoinerCondition> eaiJoinerConditions) {
		this.eaiJoinerConditions = eaiJoinerConditions;
	}

	/**
	 * @return the messageDefinition
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "message_field_id", nullable = true)
	public MessageDefinition getMessageDefinition() {
		return messageDefinition;
	}

	/**
	 * @param messageDefinition
	 *            the messageDefinition to set
	 */
	public void setMessageDefinition(MessageDefinition messageDefinition) {
		this.messageDefinition = messageDefinition;
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
		if (!(other instanceof TransformationDefinition)) {
			return false;
		}
		TransformationDefinition castOther = (TransformationDefinition) other;
		return new EqualsBuilder()
				.append(transformationDefinitionId,
						castOther.transformationDefinitionId)
				.append(eaiTransformation, castOther.eaiTransformation)
				.append(transformationFieldName,
						castOther.transformationFieldName).isEquals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder().append(transformationDefinitionId)
				.append(eaiTransformation).append(transformationFieldName)
				.toHashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("transformationDefinitionId",
						transformationDefinitionId)
				.append("eaiTransformation", eaiTransformation)
				.append("transformationFieldName", transformationFieldName)
				.toString();
	}
}
