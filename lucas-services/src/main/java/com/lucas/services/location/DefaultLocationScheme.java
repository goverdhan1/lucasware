package com.lucas.services.location;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.FactoryBean;

import com.lucas.entity.location.LocationPart;
import com.lucas.entity.location.LocationPartType;
import com.lucas.entity.location.LocationScheme;
import com.lucas.exception.InvalidConfigurationException;
import com.lucas.services.util.CollectionsUtilService;

/**
 * Lists the order of the LocationParts and the separator between the parts (if any)
 * 
 * The only constructor of this class accepts a pre populated list of <code>LocationPartType</code> objects.
 * Also see {@link LinearLocationScheme}
 * Note {@link LinearLocationScheme} is Spring dependent in that it implements {@link FactoryBean}.
 * This class offers a Spring independent implementation of the {@link LocationScheme} interface.
 * @author Pankaj Tandon
 *
 */

public class DefaultLocationScheme extends AbstractLocationScheme implements LocationScheme<LocationPartType>{

	private List<LocationPartType> schemeList;
	private String separator;

	public DefaultLocationScheme(List<LocationPartType> partTypeList, String separator){ 
		//Ensure that there are no dups in the config
		Map<String, LocationPartType> map = new HashMap<String, LocationPartType>();
		for (LocationPartType locationPartType : CollectionsUtilService.nullGuard(partTypeList)) {
			map.put(locationPartType.getName(), locationPartType); 
		}
		if (map.size() < partTypeList.size()){
			throw new InvalidConfigurationException("Invalid configuration: LocationScheme configuration contains duplicates!");
		}
		if (StringUtils.isEmpty(separator)){
			throw new InvalidConfigurationException("Invalid configuration: Separator is blank or zero-length string!");
		}
		
		schemeList = new CopyOnWriteArrayList<LocationPartType>(); //Want to be thread safe when iterating over list.
		for (LocationPartType locationPartType : CollectionsUtilService.nullGuard(partTypeList)) {
			schemeList.add(locationPartType);
		}
		this.setSeparator(separator);
	}

	@Override
	public String getSeparator() {
		return separator;
	}
	@Override
	public Iterator<LocationPartType> getIterator(){
		return schemeList.iterator();
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

	@Override
	public int getNumbeOfParts() {
		return schemeList.size();
	}
}
