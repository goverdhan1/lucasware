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

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.lucas.alps.viewtype.SaveMessageSegmentDetailsView;
import com.lucas.entity.eai.message.MessageDefinition;
import com.lucas.entity.eai.message.SegmentDefinition;
import com.lucas.entity.eai.message.Segments;
import com.lucas.services.util.CollectionsUtilService;

/**
 * @author Naveen
 * 
 */
public class MessageSegmentView implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7598158386428974644L;
	private Segments segment;

	public MessageSegmentView() {
		this.segment = new Segments();
	}

	public MessageSegmentView(Segments segment) {
		this.segment = segment;
	}

	/**
	 * @return the segmentId
	 */
	@JsonView({ SaveMessageSegmentDetailsView.class })
	public Long getSegmentId() {
		return segment.getSegmentId();
	}

	/**
	 * @param segmentId
	 *            the segmentId to set
	 */
	public void setSegmentId(Long segmentId) {
		segment.setSegmentId(segmentId);
	}

	/**
	 * @return the segmentDescription
	 */
	@JsonView({ SaveMessageSegmentDetailsView.class })
	public String getSegmentDescription() {
		return segment.getSegmentDescription();
	}

	/**
	 * @param segmentDescription
	 *            the segmentDescription to set
	 */
	public void setSegmentDescription(String segmentDescription) {
		segment.setSegmentDescription(segmentDescription);
	}

	/**
	 * @return the segmentLength
	 */
	@JsonView({ SaveMessageSegmentDetailsView.class })
	public int getSegmentLength() {
		return segment.getSegmentLength();
	}

	/**
	 * @param segmentLength
	 *            the segmentLength to set
	 */
	public void setSegmentLength(int segmentLength) {
		segment.setSegmentLength(segmentLength);
	}

	/**
	 * @return the segmentName
	 */
	@JsonView({ SaveMessageSegmentDetailsView.class })
	public String getSegmentName() {
		return segment.getSegmentName();
	}

	/**
	 * @param segmentName
	 *            the segmentName to set
	 */
	public void setSegmentName(String segmentName) {
		segment.setSegmentName(segmentName);
	}

	/**
	 * @return the eaiSegmentDefinitions
	 */
	@JsonView({ SaveMessageSegmentDetailsView.class })
	public Set<MessageSegmentDefinitionView> getSegmentDefinitions() {
		final Set<SegmentDefinition> segmentDefinitions = this.segment
				.getEaiSegmentDefinitions();
		Set<MessageSegmentDefinitionView> eaiSegmentDefinitionsView = new HashSet<MessageSegmentDefinitionView>(
				segmentDefinitions.size());
		for (SegmentDefinition segmentDefinition : CollectionsUtilService
				.nullGuard(segmentDefinitions)) {
			eaiSegmentDefinitionsView.add(new MessageSegmentDefinitionView(
					segmentDefinition));
		}
		return eaiSegmentDefinitionsView;
	}

	/**
	 * @param eaiSegmentDefinitions
	 *            the eaiSegmentDefinitions to set
	 */
	
	public void setSegmentDefinitions(
			Set<MessageSegmentDefinitionView> segmentDefinitions) {
		if (segment != null && segmentDefinitions != null) {
			final Set<SegmentDefinition> segmentDefinitionSet = new HashSet<SegmentDefinition>();
			for (MessageSegmentDefinitionView segmentDefinitionView : segmentDefinitions) {
				segmentDefinitionSet.add(segmentDefinitionView
						.getSegmentDefinition());
			}
			this.segment.setEaiSegmentDefinitions(segmentDefinitionSet);
		}

	}

	/**
	 * @return the eaiMessageDefinitions
	 */
	
	public Set<MessageDefinitionView> getMessageDefinitions() {
		final Set<MessageDefinition> messageDefinitions = this.segment
				.getEaiMessageDefinitions();
		Set<MessageDefinitionView> eaiMessageDefinitionsView = new HashSet<MessageDefinitionView>(
				messageDefinitions.size());
		for (MessageDefinition messageDefinition : CollectionsUtilService
				.nullGuard(messageDefinitions)) {
			eaiMessageDefinitionsView.add(new MessageDefinitionView(
					messageDefinition));
		}
		return eaiMessageDefinitionsView;
	}

	/**
	 * @param eaiMessageDefinitions
	 *            the eaiMessageDefinitions to set
	 */
	public void setMessageDefinitions(
			Set<MessageDefinitionView> messageDefinitions) {
		if (segment != null && messageDefinitions != null) {
			final Set<MessageDefinition> messageDefinitionSet = new HashSet<MessageDefinition>();
			for (MessageDefinitionView messageDefinitionView : messageDefinitions) {
				messageDefinitionSet.add(messageDefinitionView
						.getMessageDefinition());
			}
			this.segment.setEaiMessageDefinitions(messageDefinitionSet);
		}

	}

	/**
	 * @return the segment
	 */
	public Segments getSegment() {
		return segment;
	}

	/**
	 * @param segment
	 *            the segment to set
	 */
	public void setSegment(Segments segment) {
		this.segment = segment;
	}

}
