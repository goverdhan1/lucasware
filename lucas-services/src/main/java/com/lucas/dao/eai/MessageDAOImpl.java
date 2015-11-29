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
package com.lucas.dao.eai;

import java.text.ParseException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.lucas.dao.support.ResourceDAO;
import com.lucas.entity.eai.message.Message;
import com.lucas.services.search.SearchAndSortCriteria;

/**
 * DAO implementaion class for message entity object
 * 
 * @author Naveen
 * 
 */
@Repository
public class MessageDAOImpl extends ResourceDAO<Message> implements MessageDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lucas.dao.eai.message.MessageDAO#getMessageById(long)
	 */
	@Override
	public Message getMessageById(Long messageId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Message.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.add(Restrictions.eq("messageId", messageId));
		Message message = (Message) criteria.uniqueResult();
		return message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lucas.dao.eai.message.MessageDAO#getMessageByName(java.lang.String)
	 */
	@Override
	public Message getMessageByName(String messageName) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Message.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.add(Restrictions.eq("messageName", messageName));
		Message message = (Message) criteria.uniqueResult();
		return message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lucas.dao.eai.message.MessageDAO#getMessageByNames(java.util.List)
	 */
	@Override
	public List<Message> getMessageByNames(List<String> messageNames) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Message.class);
		criteria.add(Restrictions.in("messageName", messageNames));
		List<Message> messages = criteria.list();
		return messages;
	}

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.lucas.dao.eai.message.MessageDAO#getMessageListBySearchAndSortCriteria(SearchAndSortCriteria)
     */
    @Override
    public List<Message> getMessageListBySearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria) throws ParseException {
        final Session session = getSession();
        final List<Message> messageList=this.getBySearchAndSortCriteria(searchAndSortCriteria);
        return messageList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.lucas.dao.eai.message.MessageDAO#getTotalCountForSearchAndSortCriteria(SearchAndSortCriteria)
     */
    @Override
    public Long getTotalCountForSearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria) throws ParseException {
        Session session = getSession();
        Criteria criteria = session.createCriteria(entityType, "entity");
        criteria = this.buildCriteria(session, searchAndSortCriteria);
        
        criteria.setFirstResult(0);
        criteria.setMaxResults(Integer.MAX_VALUE);
        criteria.setProjection(null).setResultTransformer(Criteria.ROOT_ENTITY);
        Long totalResult = ((Number)criteria.setProjection(Projections.rowCount()).uniqueResult()).longValue();
        return totalResult;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.lucas.dao.eai.MessageDAO#getAllMessageNames()
     */
    @Override
    public List<String> getAllMessageNames() throws ParseException {
    	Session session = getSession();
    	Criteria criteria = session.createCriteria(Message.class);
    	criteria.setProjection(Projections.projectionList().add(Projections.property("messageName")));
        return criteria.list();
    }
}
