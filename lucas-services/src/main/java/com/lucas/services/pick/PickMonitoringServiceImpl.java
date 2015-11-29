/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.services.pick;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lucas.dao.pick.PicklineDAO;
import com.lucas.entity.wave.Wave;
import com.lucas.services.search.SearchAndSortCriteria;

@Service("pickMonitoringService")
public class PickMonitoringServiceImpl implements PickMonitoringService {

	private static Logger LOG = LoggerFactory
			.getLogger(PickMonitoringServiceImpl.class);
	private final PicklineDAO picklineDAO;

	@Inject
	public PickMonitoringServiceImpl(PicklineDAO picklineDAO) {
		this.picklineDAO = picklineDAO;

	}

	@Override
	@Transactional(readOnly = true)
	public MultiMap getPicklinesByWave(
			SearchAndSortCriteria searchAndSortCriteria) {
		Map<String, List<Object>> picklineByWaveMap = picklineDAO
				.getPicklinesByWave(searchAndSortCriteria);
		MultiMap picklineByWaveMultiMap = new MultiValueMap();
		
		for(Entry<String, List<Object>> entry: picklineByWaveMap.entrySet()){
			List<Object> values = entry.getValue();
			
			
			  for(Object value:values){
			  
			  Object[] obj = (Object[]) value; 
			  
			  Map<String, String> picklinesAndWaveMap = new HashMap<String, String>();
			  picklinesAndWaveMap.put("label", ((Wave)obj[0]).getWaveName());
			  picklinesAndWaveMap.put("value", obj[1].toString());
			  picklineByWaveMultiMap.put(entry.getKey(), picklinesAndWaveMap);
			  }
			 
			
		}
		
		
		return picklineByWaveMultiMap;
	}


}
