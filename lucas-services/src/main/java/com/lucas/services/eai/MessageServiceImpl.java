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
package com.lucas.services.eai;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.lucas.dao.eai.MessageDAO;
import com.lucas.dao.eai.MessageDefinitionDAO;
import com.lucas.dao.eai.SegmentDAO;
import com.lucas.dao.eai.SegmentDefinitionDAO;
import com.lucas.dao.ui.WidgetDAO;
import com.lucas.entity.eai.message.Message;
import com.lucas.entity.eai.message.MessageDefinition;
import com.lucas.entity.eai.message.SegmentDefinition;
import com.lucas.entity.eai.message.Segments;
import com.lucas.services.search.SearchAndSortCriteria;
import com.lucas.services.security.SecurityService;

/**
 * Service implementation class for message entity object
 * 
 * @author Naveen
 * 
 */
@Service("eaiMessageService")
@Transactional(readOnly = false, propagation = Propagation.SUPPORTS)
public class MessageServiceImpl implements MessageService {

	private static Logger LOG = LoggerFactory.getLogger(Message.class);
	private MessageDAO messageDAO;

	private SegmentDAO segmentDAO;

	private MessageDefinitionDAO messageDefinitionDAO;

	private SegmentDefinitionDAO segmentDefinitionDAO;

	private WidgetDAO widgetDAO;

	private SecurityService securityService;

	@Inject
	public MessageServiceImpl(MessageDAO messageDAO,
			MessageDefinitionDAO messageDefinitionDAO, WidgetDAO widgetDAO,
			SecurityService securityService, SegmentDAO segmentDAO,
			SegmentDefinitionDAO segmentDefinitionDAO) {
		this.messageDAO = messageDAO;
		this.messageDefinitionDAO = messageDefinitionDAO;
		this.widgetDAO = widgetDAO;
		this.securityService = securityService;
		this.segmentDAO = segmentDAO;
		this.segmentDefinitionDAO = segmentDefinitionDAO;
	}

	public MessageServiceImpl() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lucas.services.eai.EaiMessageService#getMessageById(int)
	 */
	@Transactional(readOnly = true)
	@Override
	public Message getMessageById(long messageId) {
		return messageDAO.getMessageById(messageId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lucas.services.eai.EaiMessageService#getMessageByName(java.lang.String
	 * )
	 */
	@Transactional(readOnly = true)
	@Override
	public Message getMessageByName(String messageName)
			throws JsonParseException {
		return messageDAO.getMessageByName(messageName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lucas.services.eai.EaiMessageService#getMessageByNames(java.util.
	 * List)
	 */
	@Transactional(readOnly = true)
	@Override
	public List<Message> getMessageByNames(List<String> messageNames)
			throws JsonParseException, JsonMappingException, IOException {
		return messageDAO.getMessageByNames(messageNames);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lucas.services.eai.MessageService#
	 * getMessageDefinitionByTransformationFieldId(java.lang.Long)
	 */
	@Transactional(readOnly = true)
	@Override
	public MessageDefinition getMessageDefinitionByTransformationDefinitionId(
			Long transformationDefinitionId) {
		return messageDefinitionDAO
				.getMessageDefinitionForTransformationDefinition(transformationDefinitionId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lucas.services.eai.MessageService#
	 * getMessageListBySearchAndSortCriteria(SearchAndSortCriteria)
	 */
	@Transactional(readOnly = true)
	@Override
	public List<Message> getMessageListBySearchAndSortCriteria(
			SearchAndSortCriteria searchAndSortCriteria) throws ParseException,
			Exception {
		final List<Message> messageList = this.messageDAO
				.getMessageListBySearchAndSortCriteria(searchAndSortCriteria);
		return messageList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lucas.services.eai.MessageService#
	 * getTotalCountForSearchAndSortCriteria(SearchAndSortCriteria)
	 */
	@Override
	public Long getTotalCountForMessageSearchAndSortCriteria(
			SearchAndSortCriteria searchAndSortCriteria) throws ParseException,
			Exception {
		return this.messageDAO
				.getTotalCountForSearchAndSortCriteria(searchAndSortCriteria);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lucas.services.eai.MessageService#getSegmentListBySearchAndSortCriteria
	 * (com.lucas.services.search.SearchAndSortCriteria)
	 */
	@Transactional(readOnly = true)
	@Override
	public List<Segments> getSegmentListBySearchAndSortCriteria(
			SearchAndSortCriteria searchAndSortCriteria) throws ParseException,
			IllegalArgumentException, Exception {
		return segmentDAO
				.getSegmentListBySearchAndSortCriteria(searchAndSortCriteria);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lucas.services.eai.MessageService#getTotalCountForSearchAndSortCriteria
	 * (com.lucas.services.search.SearchAndSortCriteria)
	 */
	@Transactional(readOnly = true)
	@Override
	public Long getTotalCountForSearchAndSortCriteria(
			SearchAndSortCriteria searchAndSortCriteria) throws ParseException,
			Exception {

		return segmentDAO
				.getTotalCountForSearchAndSortCriteria(searchAndSortCriteria);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lucas.services.eai.EaiMessageService#getHydratedMessage(long
	 * messageId)
	 */
	@Transactional(readOnly = true)
	@Override
	public Message getHydratedMessage(long messageId) {
		Message msg = messageDAO.getMessageById(messageId);
		Set<MessageDefinition> eaiMessageDefinitions = msg
				.getEaiMessageDefinitions();
		msg.setEaiMessageDefinitions(eaiMessageDefinitions);
		return msg;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lucas.services.eai.MessageService#createSegment(javax.swing.text.
	 * Segment)
	 */
	@Override
	@Transactional(readOnly = false)
	public boolean createSegment(Segments segment) throws Exception {

		LOG.debug("*********************Message Service Impl********* "
				+ segment.toString());

		for (SegmentDefinition segmentDefinition : segment
				.getEaiSegmentDefinitions()) {
			if (segmentDefinition.getEaiSegments() == null) {
				segmentDefinition.setEaiSegments(segment);
			}
		}
		for (MessageDefinition messageDefinition : segment
				.getEaiMessageDefinitions()) {
			if (messageDefinition.getEaiSegments() == null) {
				messageDefinition.setEaiSegments(segment);
			}
		}
		segmentDAO.save(segment);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lucas.services.eai.MessageService#getSegment(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public Segments getSegment(String segmentName) {
		return segmentDAO.getSegment(segmentName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lucas.services.eai.MessageService#getSegmentAndSegmentDefinitions
	 * (java.lang.String, boolean)
	 */
	@Override
	@Transactional(readOnly = true)
	public Segments getSegmentAndSegmentDefinitions(String segmentName,
			boolean dependancy) {
		Segments segment = segmentDAO.getSegment(segmentName);
		if (segment != null && dependancy) {
			Set<SegmentDefinition> segmentDefinitions = segmentDefinitionDAO
					.findSegmentDefinitionBySegmentId(segment.getSegmentId());
			segment.setEaiSegmentDefinitions(segmentDefinitions);
		}
		return segment;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lucas.services.eai.MessageService#deleteSegment(java.lang.Long)
	 */
	@Override
	@Transactional
	public boolean deleteSegment(Long segmentId) {
		Segments segment = segmentDAO.load(segmentId);
		Set<MessageDefinition> messageDefinitions = segment
				.getEaiMessageDefinitions();
		for (MessageDefinition messageDefinition : messageDefinitions) {
			messageDefinition.setEaiSegments(null);
		}
		try {
			segmentDAO.delete(segment);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@Transactional(readOnly = true)
	@Override
	public Message getHydratedMessageByName(String messageName) {
		Message msg = messageDAO.getMessageByName(messageName);
		msg.setEaiMessageDefinitions(new HashSet<MessageDefinition>(
				messageDefinitionDAO.getMessageDefinitionsByMessageId(msg
						.getMessageId())));
		return msg;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lucas.services.eai.MessageService#getAllMessageNames()
	 */
	@Transactional(readOnly = true)
	@Override
	public List<String> getAllMessageNames() throws ParseException {
		return messageDAO.getAllMessageNames();
	}
}
