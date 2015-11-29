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
package com.lucas.alps.view;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonView;
import com.lucas.alps.viewtype.MessageSegmentDefinitionDetailsView;
import com.lucas.alps.viewtype.SaveMessageSegmentDetailsView;
import com.lucas.entity.eai.message.SegmentDefinition;
import com.lucas.entity.eai.message.Segments;

/**
 * @author Naveen
 * 
 */
public class MessageSegmentDefinitionView implements Serializable{

	private SegmentDefinition segmentDefinition;

	public MessageSegmentDefinitionView() {
		this.segmentDefinition = new SegmentDefinition();
	}

	public MessageSegmentDefinitionView(SegmentDefinition segmentDefinition) {
		this.segmentDefinition = segmentDefinition;
	}

	/**
	 * @return the segmentDefinition
	 */
	public SegmentDefinition getSegmentDefinition() {
		return segmentDefinition;
	}

	/**
	 * @param segmentDefinition
	 *            the segmentDefinition to set
	 */
	public void setSegmentDefinition(SegmentDefinition segmentDefinition) {
		this.segmentDefinition = segmentDefinition;
	}

	/**
	 * @return the segmentFieldId
	 */
	@JsonView({SaveMessageSegmentDetailsView.class})
	public Long getSegmentFieldId() {
		return segmentDefinition.getSegmentFieldId();
	}

	/**
	 * @param segmentFieldId
	 *            the segmentFieldId to set
	 */
	public void setSegmentFieldId(Long segmentFieldId) {
		segmentDefinition.setSegmentFieldId(segmentFieldId);
	}



	/**
	 * @return the segmentFieldDescription
	 */
	@JsonView({SaveMessageSegmentDetailsView.class})
	public String getSegmentFieldDescription() {
		return segmentDefinition.getSegmentFieldDescription();
	}

	/**
	 * @param segmentFieldDescription
	 *            the segmentFieldDescription to set
	 */
	public void setSegmentFieldDescription(String segmentFieldDescription) {
		segmentDefinition.setSegmentFieldDescription(segmentFieldDescription);
	}

	/**
	 * @return the segmentFieldEnd
	 */
	@JsonView({SaveMessageSegmentDetailsView.class})
	public Integer getSegmentFieldEnd() {
		return segmentDefinition.getSegmentFieldEnd();
	}

	/**
	 * @param segmentFieldEnd
	 *            the segmentFieldEnd to set
	 */
	public void setSegmentFieldEnd(Integer segmentFieldEnd) {
		segmentDefinition.setSegmentFieldEnd(segmentFieldEnd);
	}

	/**
	 * @return the segmentFieldLength
	 */
	@JsonView({SaveMessageSegmentDetailsView.class})
	public Integer getSegmentFieldLength() {
		return segmentDefinition.getSegmentFieldLength();
	}

	/**
	 * @param segmentFieldLength
	 *            the segmentFieldLength to set
	 */
	public void setSegmentFieldLength(Integer segmentFieldLength) {
		segmentDefinition.setSegmentFieldLength(segmentFieldLength);
	}

	/**
	 * @return the segmentFieldName
	 */
	@JsonView({SaveMessageSegmentDetailsView.class})
	public String getSegmentFieldName() {
		return segmentDefinition.getSegmentFieldName();
	}

	/**
	 * @param segmentFieldName
	 *            the segmentFieldName to set
	 */
	public void setSegmentFieldName(String segmentFieldName) {
		segmentDefinition.setSegmentFieldName(segmentFieldName);
	}

	/**
	 * @return the segmentFieldSeq
	 */
	@JsonView({SaveMessageSegmentDetailsView.class})
	public int getSegmentFieldSeq() {
		return segmentDefinition.getSegmentFieldSeq();
	}

	/**
	 * @param segmentFieldSeq
	 *            the segmentFieldSeq to set
	 */
	public void setSegmentFieldSeq(int segmentFieldSeq) {
		segmentDefinition.setSegmentFieldSeq(segmentFieldSeq);
	}

	/**
	 * @return the segmentFieldStart
	 */
	@JsonView({SaveMessageSegmentDetailsView.class})
	public Integer getSegmentFieldStart() {
		return segmentDefinition.getSegmentFieldStart();
	}

	/**
	 * @param segmentFieldStart
	 *            the segmentFieldStart to set
	 */
	public void setSegmentFieldStart(Integer segmentFieldStart) {
		segmentDefinition.setSegmentFieldStart(segmentFieldStart);
	}

	/**
	 * @return the segmentFieldType
	 */
	@JsonView({SaveMessageSegmentDetailsView.class})
	public String getSegmentFieldType() {
		return segmentDefinition.getSegmentFieldType();
	}

	/**
	 * @param segmentFieldType
	 *            the segmentFieldType to set
	 */
	public void setSegmentFieldType(String segmentFieldType) {
		segmentDefinition.setSegmentFieldType(segmentFieldType);
	}

	/**
	 * @return the segmentFieldValue
	 */
	@JsonView({SaveMessageSegmentDetailsView.class})
	public String getSegmentFieldValue() {
		return segmentDefinition.getSegmentFieldValue();
	}

	/**
	 * @param segmentFieldValue
	 *            the segmentFieldValue to set
	 */
	public void setSegmentFieldValue(String segmentFieldValue) {
		segmentDefinition.setSegmentFieldValue(segmentFieldValue);
	}

	/**
	 * @return the segmentSegmentId
	 */
	@JsonView({SaveMessageSegmentDetailsView.class})
	public Long getSegmentSegmentId() {
		return segmentDefinition.getSegmentSegmentId();
	}

	/**
	 * @param segmentSegmentId
	 *            the segmentSegmentId to set
	 */
	public void setSegmentSegmentId(Long segmentSegmentId) {
		segmentDefinition.setSegmentSegmentId(segmentSegmentId);
	}

}
