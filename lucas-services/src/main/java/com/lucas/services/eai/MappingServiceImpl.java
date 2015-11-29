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

import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.collections.MapIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lucas.dao.eai.EventHandlerDAO;
import com.lucas.dao.eai.MappingDao;
import com.lucas.dao.eai.MappingPathDao;
import com.lucas.dao.eai.MessageDAO;
import com.lucas.dao.eai.TransformationDao;
import com.lucas.entity.eai.event.EventHandler;
import com.lucas.entity.eai.mapping.Mapping;
import com.lucas.entity.eai.mapping.MappingDefinition;
import com.lucas.entity.eai.mapping.MappingPath;
import com.lucas.exception.LucasRuntimeException;
import com.lucas.services.search.SearchAndSortCriteria;

/**
 * This class is the service layer implementation class to provide all the
 * business logic needs to be performed on the mapping
 * 
 * 
 */
@Service("mappingService")
public class MappingServiceImpl implements MappingService {

	private static Logger LOG = LoggerFactory
			.getLogger(MappingServiceImpl.class);

	private MappingDao mappingDao;
	
	private MappingPathDao mappingPathDao;
	
	private EventHandlerDAO eventHandlerDao;
	
	private MessageDAO messageDao;
	
	private TransformationDao transformationDao;

	/**
	 * 
	 */
	public MappingServiceImpl() {
	}

	@Inject
	public MappingServiceImpl(MappingDao mappingDao, MappingPathDao mappingPathDao, EventHandlerDAO eventHandlerDao, MessageDAO messageDao, TransformationDao transformationDao) {
		this.mappingDao = mappingDao;
		this.mappingPathDao = mappingPathDao;
		this.eventHandlerDao = eventHandlerDao;
		this.messageDao = messageDao;
		this.transformationDao = transformationDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lucas.services.eai.MappingService#getMappingById(java.lang.Long)
	 */
	@Transactional(readOnly = true)
	@Override
	public Mapping getMappingById(Long mappingId) {
		return mappingDao.getMappingById(mappingId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lucas.services.eai.MappingService#getMappingByName(java.lang.String)
	 */
	@Transactional(readOnly = true)
	@Override
	public Mapping getMappingByName(String mappingName) {
		return mappingDao.getMappingByName(mappingName);
	}

	/* (non-Javadoc)
	 * @see com.lucas.services.eai.MappingService#getMappingPathByMappingName(java.lang.String)
	 */
	@Transactional(readOnly = true)
	@Override
	public List<MappingPath> getMappingPathByMappingName(String mappingName) {
		return mappingPathDao.getMappingPathByMappingName(mappingName);
	}
	
	/* (non-Javadoc)
	 * @see com.lucas.services.eai.MappingService#getMappingByEventHandlerId(java.lang.Long)
	 */
	@Transactional(readOnly = true)
	@Override
	public Mapping getInboundMappingByEventHandlerId(Long eventHandlerId) {
		return mappingDao.getInboundMappingByEventHandlerId(eventHandlerId);
	}
	
	/* (non-Javadoc)
	 * @see com.lucas.services.eai.MappingService#getMappingByEventHandlerId(java.lang.Long)
	 */
	@Transactional(readOnly = true)
	@Override
	public Mapping getOutboundMappingByEventHandlerId(Long eventHandlerId) {
		return mappingDao.getOutboundMappingByEventHandlerId(eventHandlerId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lucas.services.eai.MappingService#getMappingListBySearchAndSortCriteria
	 * (com.lucas.services.search.SearchAndSortCriteria)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Mapping> getMappingListBySearchAndSortCriteria(
			SearchAndSortCriteria searchAndSortCriteria) throws ParseException,
			IllegalArgumentException, Exception {
		return mappingDao
				.getMappingListBySearchAndSortCriteria(searchAndSortCriteria);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lucas.services.eai.MappingService#getTotalCountForSearchAndSortCriteria
	 * (com.lucas.services.search.SearchAndSortCriteria)
	 */
	@Override
	@Transactional(readOnly = true)
	public Long getTotalCountForSearchAndSortCriteria(
			SearchAndSortCriteria searchAndSortCriteria) throws ParseException,
			Exception {
		return mappingDao
				.getTotalCountForMappingSearchAndSortCriteria(searchAndSortCriteria);
	}
	
    /*
     * (non-Javadoc)
     * @see com.lucas.services.eai.MappingService#getMappingsNames()
     */
    @Transactional(readOnly = true)
    @Override
    public List<String> getMappingsNames() {
        return this.mappingDao.getMappingsNames();
    }

    /*
     * (non-Javadoc)
     * @see com.lucas.services.eai.MappingService#getMappingByName()
     */
    @Transactional(readOnly = true)
    @Override
    public Mapping getMappingByName(final String mappingName,final boolean dependency){
        final Mapping mapping=this.getMappingByName(mappingName);
        if(dependency && mapping!=null){
            final List<MappingPath>mappingPaths=this.mappingPathDao.getMappingPathByMappingName(mappingName);
            mapping.setMappingPaths(new HashSet(mappingPaths));
        }
        return mapping;
    }


	/* (non-Javadoc)
	 * @see com.lucas.services.eai.MappingService#saveMapping(com.lucas.entity.eai.mapping.Mapping)
	 */
	@Transactional
	@Override
	public void saveMapping(Mapping mapping) {
		Mapping mappingExists = mappingDao.getMappingByName(mapping.getMappingName());
		if (mappingExists != null && (mappingExists.getMappingId() != mapping.getMappingId())) {
			throw new LucasRuntimeException(
					"Mapping Already exists. Existing Mapping Name"
							+ mappingExists.getMappingName() + "New Mapping Name"
							+ mapping.getMappingName());
		}
		if (mapping.getSourceMessage() != null) {
			mapping.setSourceMessage(messageDao.getMessageByName(mapping.getSourceMessage().getMessageName()));
		}
		if (mapping.getDestinationMessage() != null) {
			mapping.setDestinationMessage(messageDao.getMessageByName(mapping.getDestinationMessage().getMessageName()));
		}
		if (mapping.getMappingPaths() != null && !mapping.getMappingPaths().isEmpty()) {
			for (MappingPath mp : mapping.getMappingPaths()) {
				if (mp.getMapping() == null) {
					mp.setMapping(mapping);
				}
				if (mp.getFromTransformation() != null) {
					mp.setFromTransformation(transformationDao
							.getTransformationById(mp.getFromTransformation()
									.getTransformationId()));
				}
				if (mp.getToTransformation() != null) {
					mp.setToTransformation(transformationDao
							.getTransformationById(mp.getToTransformation()
									.getTransformationId()));
				}
			}
		}
		mappingDao.save(mapping);
	}

	/* (non-Javadoc)
	 * @see com.lucas.services.eai.MappingService#deleteMapping(java.lang.Long)
	 */
	@Override
	@Transactional
	public boolean deleteMapping(Long mappingId) {
		// TODO Auto-generated method stub
		Mapping mapping = mappingDao.load(mappingId);
		mapping = getMappingObjectsForDelete(mapping);
		try {
			mappingDao.delete(mapping);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * @param mapping
	 * @return
	 */
	private Mapping getMappingObjectsForDelete(Mapping mapping) {
		// TODO Auto-generated method stub
		
		
		mapping.setSourceMessage(null);
		mapping.setDestinationMessage(null);
		Set<MappingPath> mappingPaths = mapping.getMappingPaths();
		for (MappingPath mappingPath : mappingPaths) {
			mappingPath.setMapping(null);
			mappingPath.setFromTransformation(null);
			mappingPath.setToTransformation(null);
		}
		mapping.setMappingPaths(mappingPaths);
		
		Set<EventHandler> eventHandlerForInboundMapping = mapping.getEaiEventHandlersForInboundMappingId();
		for (EventHandler eventHandlerForDeleteMapping : eventHandlerForInboundMapping) {
			eventHandlerForDeleteMapping.setEaiMappingByInboundMappingId(null);
		}
		mapping.setEaiEventHandlersForInboundMappingId(eventHandlerForInboundMapping);
		
		Set<EventHandler> eventHandlerForOutboundMapping = mapping.getEaiEventHandlersForOutboundMappingId();
		for (EventHandler eventHandlerForDeleteMapping : eventHandlerForOutboundMapping) {
			eventHandlerForDeleteMapping.setEaiMappingByOutboundMappingId(null);
		}
		
		mapping.setEaiEventHandlersForOutboundMappingId(eventHandlerForOutboundMapping);
		
		Set<MappingDefinition> mappingDefinition = mapping.getEaiMappingDefinitions();
		for (MappingDefinition mappingDefinitionForDeleteMapping : mappingDefinition) {
			mappingDefinitionForDeleteMapping.setEaiMapping(null);
		}
		
		mapping.setEaiMappingDefinitions(mappingDefinition);
		
		return mapping;
	}
}
