/*
 * Lucas Systems Inc 11279 Perry Highway Wexford PA 15090 United States
 * 
 * 
 * The information in this file contains trade secrets and confidential information which is the
 * property of Lucas Systems Inc.
 * 
 * All trademarks, trade names, copyrights and other intellectual property rights created,
 * developed, embodied in or arising in connection with this software shall remain the sole property
 * of Lucas Systems Inc.
 * 
 * Copyright (c) Lucas Systems, 2014 ALL RIGHTS RESERVED
 */
package com.lucas.dao.eai;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.lucas.dao.support.ResourceDAO;
import com.lucas.entity.eai.message.MessageDefinition;

/**
 * DAO implementation class for message definition entity object
 * 
 * @author Naveen
 * 
 */
@Repository
public class MessageDefinitionDAOImpl extends ResourceDAO<MessageDefinition> implements MessageDefinitionDAO {

    /*
     * (non-Javadoc)
     * 
     * @see com.lucas.dao.eai.MessageDefinitionDAO#getMessageDefinitionByMessageFieldId
     * (java.lang.Long)
     * 
     * @Override public MessageDefinition getMessageDefinitionByMessageFieldId( Long messageFieldId)
     * { Session session = getSession(); Criteria criteria =
     * session.createCriteria(MessageDefinition.class);
     * criteria.add(Restrictions.eq("messageFieldId", messageFieldId)); MessageDefinition
     * messageDefinition = (MessageDefinition) criteria .uniqueResult(); return messageDefinition; }
     * 
     * 
     * (non-Javadoc)
     * 
     * @see com.lucas.dao.eai.MessageDefinitionDAO#getMessageDefinitionByMessageFieldName
     * (java.lang.String)
     * 
     * @Override public MessageDefinition getMessageDefinitionByMessageFieldName( String
     * messageFieldName) { Session session = getSession(); Criteria criteria =
     * session.createCriteria(MessageDefinition.class);
     * criteria.add(Restrictions.eq("messageFieldName", messageFieldName)); MessageDefinition
     * messageDefinition = (MessageDefinition) criteria .uniqueResult(); return messageDefinition; }
     */

    /*
     * (non-Javadoc)
     * 
     * @see com.lucas.dao.eai.MessageDefinitionDAO#
     * getMessagedefinitionListForTransformationDefinition(java.lang.Long)
     */
    @Override
    public MessageDefinition getMessageDefinitionForTransformationDefinition(Long transformationDefinitionId) {
        Session session = getSession();
        Criteria criteria = session.createCriteria(MessageDefinition.class);
        criteria.createAlias("transformationDefinition", "t");
        criteria.add(Restrictions.eq("t.transformationDefinitionId", transformationDefinitionId));
        MessageDefinition messageDefinition = (MessageDefinition) criteria.uniqueResult();
        return messageDefinition;
    }

	/* (non-Javadoc)
	 * @see com.lucas.dao.eai.MessageDefinitionDAO#getMessageDefinitionsByMessageId(java.lang.Long)
	 */
	@Override
	public List<MessageDefinition> getMessageDefinitionsByMessageId(Long messageId) {
		Session session = getSession();
	    Criteria criteria = session.createCriteria(MessageDefinition.class);
	    criteria.add(Restrictions.eq("eaiMessage.messageId", messageId));
		return criteria.list();
	}

}
