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

import com.lucas.dao.eai.EventHandlerDAO;
import com.lucas.dao.eai.MappingDao;
import com.lucas.dao.eai.TransportDao;
import com.lucas.entity.eai.event.EventHandler;
import com.lucas.entity.eai.mapping.Mapping;
import com.lucas.entity.eai.transport.Transport;
import com.lucas.exception.LucasRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * @Author Adarsh
 * Updated By: Adarsh
 * Created On Date: 7/8/15  Time: 00:46 AM
 * This Class provide the implementation for the functionality for provide
 * service specification implementation for Event Handler
 */
@Service("eventHandlerService")
@Transactional(readOnly = false, propagation = Propagation.SUPPORTS)
public class EventHandlerServiceImpl implements EventHandlerService {

    private static Logger LOG = LoggerFactory.getLogger(EventHandlerServiceImpl.class);

    private EventHandlerDAO eventHandlerDAO;
    private MappingDao mappingDao;
    private TransportDao transportDao;

    @Inject
    public EventHandlerServiceImpl(EventHandlerDAO eventHandlerDAO, MappingDao mappingDao,TransportDao transportDao) {
        this.eventHandlerDAO = eventHandlerDAO;
        this.mappingDao = mappingDao;
        this.transportDao=transportDao;
    }

    /*
     * (non-Javadoc)
	 *
	 * @see com.lucas.services.eai.EventHandlerService#getEventHandlerByName()
	 */
    @Transactional(readOnly = true)
    @Override
    public EventHandler getEventHandlerByName(String eventHandlerName, Boolean dependency) {
        final EventHandler eventHandler = this.eventHandlerDAO.getEventHandlerByName(eventHandlerName);
        if (dependency && eventHandler != null) {
            LOG.info("Event Handler is Found for Name " + eventHandlerName);
            final Mapping inBoundMapping = this.mappingDao.getInboundMappingByEventHandlerId(eventHandler.getEventHandlerId());
            eventHandler.setEaiMappingByInboundMappingId(inBoundMapping);
            final Mapping outBoundMapping = this.mappingDao.getOutboundMappingByEventHandlerId(eventHandler.getEventHandlerId());
            eventHandler.setEaiMappingByOutboundMappingId(outBoundMapping);
        }
        return eventHandler;
    }

    /*
    * (non-Javadoc)
    *
    * @see com.lucas.services.eai.EventHandlerService#saveEventHandler()
    */
    @Transactional(readOnly = false)
    @Override
    public Boolean saveEventHandler(EventHandler eventHandler) throws LucasRuntimeException {
        try{
            final EventHandler retrieveEventHandler=this.eventHandlerDAO.getEventHandlerByName(eventHandler.getEventHandlerName());
            if(retrieveEventHandler==null){
                // Insert Operation
                LOG.info("EventHandler Found for Insert ");

                // getting the InBoundEventHandler
                if(eventHandler != null && eventHandler.getEaiMappingByInboundMappingId() != null){
                   final Mapping inBoundMapping= this.mappingDao.getInboundMappingByEventHandlerId(eventHandler.getEaiMappingByInboundMappingId().getMappingId());
                    eventHandler.setEaiMappingByInboundMappingId(inBoundMapping);
                }

                // getting the OutBoundEventHandler
                if(eventHandler != null && eventHandler.getEaiMappingByOutboundMappingId() != null){
                    final Mapping outBoundMapping= this.mappingDao.getOutboundMappingByEventHandlerId(eventHandler.getEaiMappingByOutboundMappingId().getMappingId());
                    eventHandler.setEaiMappingByOutboundMappingId(outBoundMapping);
                }

                // getting the Transport
                if(eventHandler!= null && eventHandler.getEaiTransport()!=null){
                    final Transport transport=this.transportDao.getTransportByName(eventHandler.getEaiTransport().getTransportName());
                    eventHandler.setEaiTransport(transport);
                }

                //calling save
                if(this.eventHandlerDAO.save(eventHandler)!=null){
                    return Boolean.TRUE;
                }else{
                    return Boolean.FALSE;
                }
            }else{
                // Update Operation
                LOG.info("EventHandler Found for Update ");

                // getting the InBoundEventHandler
                if(eventHandler != null && eventHandler.getEaiMappingByInboundMappingId() != null){
                    final Mapping inBoundMapping= this.mappingDao.getInboundMappingByEventHandlerId(eventHandler.getEaiMappingByInboundMappingId().getMappingId());
                    retrieveEventHandler.setEaiMappingByInboundMappingId(inBoundMapping);
                }

                // getting the OutBoundEventHandler
                if(eventHandler != null && eventHandler.getEaiMappingByOutboundMappingId() != null){
                    final Mapping outBoundMapping= this.mappingDao.getOutboundMappingByEventHandlerId(eventHandler.getEaiMappingByOutboundMappingId().getMappingId());
                    retrieveEventHandler.setEaiMappingByOutboundMappingId(outBoundMapping);
                }

                // getting the Transport
                if(eventHandler!= null && eventHandler.getEaiTransport()!=null){
                    final Transport transport=this.transportDao.getTransportByName(eventHandler.getEaiTransport().getTransportName());
                    retrieveEventHandler.setEaiTransport(transport);
                }

               // setting up other properties
                retrieveEventHandler.setEventHandlerName(eventHandler.getEventHandlerName());
                retrieveEventHandler.setEventHandlerDescription(eventHandler.getEventHandlerDescription());
                retrieveEventHandler.setEventHandlerType(eventHandler.getEventHandlerType());
                retrieveEventHandler.setInboundFilePattern(eventHandler.getInboundFilePattern());
                retrieveEventHandler.setOutboundFilePattern(eventHandler.getOutboundFilePattern());
                retrieveEventHandler.setUsageInEvent(eventHandler.getUsageInEvent());

                //calling update
                if(this.eventHandlerDAO.save(retrieveEventHandler)!=null){
                    return Boolean.TRUE;
                }else{
                    return Boolean.FALSE;
                }
            }
        }catch (Exception e){
             LOG.error("Exception Generated While Updating or Inserting Event Handler ");
              throw new LucasRuntimeException("Exception Generated While Updating Or Insert Event Handler "+e.getMessage());
        }
    }
}
