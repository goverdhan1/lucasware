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

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang3.builder.EqualsBuilder;

import com.lucas.entity.eai.event.Event;

/**
 * Entity class for the Segments domain, having details for the message to
 * execute the event in the system.
 */
@Entity
@Table(name = "eai_segments")
public class Segments implements java.io.Serializable {

	/**
	 * Serial version Id
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Segment Id
	 */
	private Long segmentId;
	/**
	 * Segment description
	 */
	private String segmentDescription;
	/**
	 * Segment length
	 */
	private int segmentLength;
	/**
	 * Segment name
	 */
	private String segmentName;
	/**
	 * Set of segment definitions
	 */
	private Set<SegmentDefinition> eaiSegmentDefinitions = new HashSet<SegmentDefinition>(
			0);
	/**
	 * Set of message definitions
	 */
	private Set<MessageDefinition> eaiMessageDefinitions = new HashSet<MessageDefinition>(
			0);

	public Segments() {
	}

	public Segments(String segmentDescription, int segmentLength,
			String segmentName) {
		this.segmentDescription = segmentDescription;
		this.segmentLength = segmentLength;
		this.segmentName = segmentName;
	}

	public Segments(String segmentDescription, int segmentLength,
			String segmentName, Set<SegmentDefinition> eaiSegmentDefinitions,
			Set<MessageDefinition> eaiMessageDefinitions) {
		this.segmentDescription = segmentDescription;
		this.segmentLength = segmentLength;
		this.segmentName = segmentName;
		this.eaiSegmentDefinitions = eaiSegmentDefinitions;
		this.eaiMessageDefinitions = eaiMessageDefinitions;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "segment_id", unique = true, nullable = false)
	public Long getSegmentId() {
		return this.segmentId;
	}

	public void setSegmentId(Long segmentId) {
		this.segmentId = segmentId;
	}

	@Column(name = "segment_description", nullable = false, length = 80)
	public String getSegmentDescription() {
		return this.segmentDescription;
	}

	public void setSegmentDescription(String segmentDescription) {
		this.segmentDescription = segmentDescription;
	}

	@Column(name = "segment_length", nullable = false)
	public int getSegmentLength() {
		return this.segmentLength;
	}

	public void setSegmentLength(int segmentLength) {
		this.segmentLength = segmentLength;
	}

	@Column(name = "segment_name", nullable = false, length = 16)
	public String getSegmentName() {
		return this.segmentName;
	}

	public void setSegmentName(String segmentName) {
		this.segmentName = segmentName;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "eaiSegments", cascade=CascadeType.ALL)
	public Set<SegmentDefinition> getEaiSegmentDefinitions() {
		return this.eaiSegmentDefinitions;
	}

	public void setEaiSegmentDefinitions(
			Set<SegmentDefinition> eaiSegmentDefinitions) {
		this.eaiSegmentDefinitions = eaiSegmentDefinitions;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "eaiSegments")
	public Set<MessageDefinition> getEaiMessageDefinitions() {
		return this.eaiMessageDefinitions;
	}

	public void setEaiMessageDefinitions(
			Set<MessageDefinition> eaiMessageDefinitions) {
		this.eaiMessageDefinitions = eaiMessageDefinitions;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Segments)) {
			return false;
		}
		Segments castOther = (Segments) other;
		return new EqualsBuilder().append(segmentName, castOther.segmentName)
				.append(segmentDescription, castOther.segmentDescription)
				.append(segmentLength, castOther.segmentLength).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(segmentName)
				.append(segmentDescription).append(segmentLength).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("segmentName", segmentName)
				.append("segmentDescription", segmentDescription)
				.append("segmentLength", segmentLength).toString();
	}

}
