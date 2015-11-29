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
package com.lucas.entity.eai.message;

// Generated 18 May, 2015 4:12:02 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.CascadeType;
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

import com.lucas.entity.user.User;

/**
 * Entity class for the segment definition domain, having details for the
 * message to execute the event in the system.
 */
@Entity
@Table(name = "eai_segment_definition", catalog = "lucasdb")
public class SegmentDefinition implements java.io.Serializable {

	private Long segmentFieldId;
	private Segments eaiSegments;
	private String segmentFieldDescription;
	private Integer segmentFieldEnd;
	private Integer segmentFieldLength;
	private String segmentFieldName;
	private int segmentFieldSeq;
	private Integer segmentFieldStart;
	private String segmentFieldType;
	private String segmentFieldValue;
	private Long segmentSegmentId;

	public SegmentDefinition() {
	}

	public SegmentDefinition(String segmentFieldDescription,
			String segmentFieldName, int segmentFieldSeq,
			String segmentFieldType) {
		this.segmentFieldDescription = segmentFieldDescription;
		this.segmentFieldName = segmentFieldName;
		this.segmentFieldSeq = segmentFieldSeq;
		this.segmentFieldType = segmentFieldType;
	}

	public SegmentDefinition(Segments eaiSegments,
			String segmentFieldDescription, Integer segmentFieldEnd,
			Integer segmentFieldLength, String segmentFieldName,
			int segmentFieldSeq, Integer segmentFieldStart,
			String segmentFieldType, String segmentFieldValue,
			Long segmentSegmentId) {
		this.eaiSegments = eaiSegments;
		this.segmentFieldDescription = segmentFieldDescription;
		this.segmentFieldEnd = segmentFieldEnd;
		this.segmentFieldLength = segmentFieldLength;
		this.segmentFieldName = segmentFieldName;
		this.segmentFieldSeq = segmentFieldSeq;
		this.segmentFieldStart = segmentFieldStart;
		this.segmentFieldType = segmentFieldType;
		this.segmentFieldValue = segmentFieldValue;
		this.segmentSegmentId = segmentSegmentId;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "segment_field_id", unique = true, nullable = false)
	public Long getSegmentFieldId() {
		return this.segmentFieldId;
	}

	public void setSegmentFieldId(Long segmentFieldId) {
		this.segmentFieldId = segmentFieldId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "segment_id")
	public Segments getEaiSegments() {
		return this.eaiSegments;
	}

	public void setEaiSegments(Segments eaiSegments) {
		this.eaiSegments = eaiSegments;
	}

	@Column(name = "segment_field_description", nullable = false, length = 80)
	public String getSegmentFieldDescription() {
		return this.segmentFieldDescription;
	}

	public void setSegmentFieldDescription(String segmentFieldDescription) {
		this.segmentFieldDescription = segmentFieldDescription;
	}

	@Column(name = "segment_field_end")
	public Integer getSegmentFieldEnd() {
		return this.segmentFieldEnd;
	}

	public void setSegmentFieldEnd(Integer segmentFieldEnd) {
		this.segmentFieldEnd = segmentFieldEnd;
	}

	@Column(name = "segment_field_length")
	public Integer getSegmentFieldLength() {
		return this.segmentFieldLength;
	}

	public void setSegmentFieldLength(Integer segmentFieldLength) {
		this.segmentFieldLength = segmentFieldLength;
	}

	@Column(name = "segment_field_name", nullable = false, length = 80)
	public String getSegmentFieldName() {
		return this.segmentFieldName;
	}

	public void setSegmentFieldName(String segmentFieldName) {
		this.segmentFieldName = segmentFieldName;
	}

	@Column(name = "segment_field_seq", nullable = false)
	public int getSegmentFieldSeq() {
		return this.segmentFieldSeq;
	}

	public void setSegmentFieldSeq(int segmentFieldSeq) {
		this.segmentFieldSeq = segmentFieldSeq;
	}

	@Column(name = "segment_field_start")
	public Integer getSegmentFieldStart() {
		return this.segmentFieldStart;
	}

	public void setSegmentFieldStart(Integer segmentFieldStart) {
		this.segmentFieldStart = segmentFieldStart;
	}

	@Column(name = "segment_field_type", nullable = false, length = 80)
	public String getSegmentFieldType() {
		return this.segmentFieldType;
	}

	public void setSegmentFieldType(String segmentFieldType) {
		this.segmentFieldType = segmentFieldType;
	}

	@Column(name = "segment_field_value", length = 80)
	public String getSegmentFieldValue() {
		return this.segmentFieldValue;
	}

	public void setSegmentFieldValue(String segmentFieldValue) {
		this.segmentFieldValue = segmentFieldValue;
	}

	@Column(name = "segment_segment_id")
	public Long getSegmentSegmentId() {
		return this.segmentSegmentId;
	}

	public void setSegmentSegmentId(Long segmentSegmentId) {
		this.segmentSegmentId = segmentSegmentId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SegmentDefinition)) {
			return false;
		}
		SegmentDefinition castOther = (SegmentDefinition) other;
		return new EqualsBuilder()
				.append(segmentFieldType, castOther.segmentFieldType)
				.append(segmentFieldName, castOther.segmentFieldName)
				.append(segmentFieldDescription,
						castOther.segmentFieldDescription).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(segmentFieldType)
				.append(segmentFieldName).append(segmentFieldDescription)
				.toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("segmentFieldType", segmentFieldType)
				.append("segmentFieldName", segmentFieldName)
				.append("segmentFieldDescription", segmentFieldDescription)
				.toString();
	}

}
