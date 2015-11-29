package com.lucas.services.location;



import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

import com.lucas.entity.location.Aisle;
import com.lucas.entity.location.Bay;
import com.lucas.entity.location.Level;
import com.lucas.entity.location.Location;
import com.lucas.entity.location.LocationPart;
import com.lucas.entity.location.LocationPartType;
import com.lucas.entity.location.LocationScheme;
import com.lucas.entity.location.Slot;
import com.lucas.entity.location.Zone;
import com.lucas.exception.InvalidConfigurationException;
import com.lucas.exception.InvalidLocationException;


public abstract class LocationServiceImpl implements LocationService {

	/**
	 * This method is injected via lookup method injection to allow for configuring the <code>Location</code> prototype bean.
	 * @return
	 */
	public abstract Location createLocation();
	
	@Override
	public Location newLocation() {
		return this.createLocation();
	}

	@Override
	public Location newLocation(LocationPart... locationParts) {
		Location l =  this.createLocation();
		l.addParts(locationParts);
		return l;
	}
	
	@Override
	public Location newLocation(String locationId) {
		Location l =  this.createLocation();
		LocationScheme<LocationPartType> scheme = l.getLocationScheme();
		String separator = scheme.getSeparator();
		if (StringUtils.isEmpty(separator)){
			throw new InvalidConfigurationException("Invalid configuration: Separator is blank or zero-length string in location scheme: " + scheme.toString());
		}
		
		int numberOfParts = scheme.getNumbeOfParts();
		//First ensure that the passed in Id does have enough separators to separate LocationParts in the configured scheme.
		int matches = StringUtils.countMatches(locationId, separator);
		if (numberOfParts - matches != 1){
			throw new InvalidLocationException("The location Id passed in: " + locationId + " is not compatible with the location scheme: " + l.getLocationScheme().toString());
		}
		
		//Parse the locationId
		List<String> list = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(locationId, separator);
		while(st.hasMoreTokens()){
			list.add(st.nextToken());
		}
		//Start matching the locationId based on the location scheme
		Iterator<LocationPartType> iterator = scheme.getIterator();
		int i = 0;
		while (iterator.hasNext()){
			LocationPartType lpt = (LocationPartType)iterator.next();
			LocationPart lp = null;
			if (lpt.equals(LocationPartType.ZONE)){
				lp = new Zone(list.get(i));
			} else if (lpt.equals(LocationPartType.AISLE)){
				lp = new Aisle(list.get(i));
			} else if (lpt.equals(LocationPartType.BAY)){
				lp = new Bay(list.get(i));
			} else if (lpt.equals(LocationPartType.LEVEL)){
				lp = new Level(list.get(i));
			} else if (lpt.equals(LocationPartType.SLOT)){
				lp = new Slot(list.get(i));
			} else {
				throw new InvalidConfigurationException("Invalid Configuration specified in the location scheme!");
			}
			l.addPart(lp);
			i++;
		}
		return l;
	}
}
