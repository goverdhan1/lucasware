package com.lucas.services.location;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Service;

import com.lucas.entity.location.LocationPartType;
import com.lucas.entity.location.LocationScheme;

/**
 * Lists the order of the LocationParts and the separator between the parts (if any)
 * 
 * Also see {@link DefaultLocationScheme}
 * Note that this implementation is Spring dependent as it uses the {@link FactoryBean} interface
 * @author Pankaj Tandon
 *
 */

public class LinearLocationScheme extends AbstractLocationScheme implements LocationScheme<LocationPartType>, FactoryBean<LocationScheme<LocationPartType>>{

	private List<LocationPartType> schemeList;
	private String separator;

	public LinearLocationScheme(){
		schemeList = new CopyOnWriteArrayList<LocationPartType>(); //Want to be thread safe when iterating over list.
	}

	public void addLocationPartType(LocationPartType locationPartType){
		schemeList.add(locationPartType);
	}
	
	public void addLocationPartType(int position, LocationPartType locationPartType){
		schemeList.add(position, locationPartType);
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

	/**
	 * Note that since we are constructing the location scheme ourselves, we have to ensure that
	 * there are no dups in the list passed (or they will be silently ignored).
	 * Also it is up to the implementer to ensure that the separator is a non-empty string or else the 
	 */
	@Override
	public LocationScheme<LocationPartType> getObject() throws Exception {
		this.addLocationPartType(0, LocationPartType.ZONE);
		this.addLocationPartType(1, LocationPartType.BAY);
		this.addLocationPartType(2, LocationPartType.AISLE); 
		this.addLocationPartType(3, LocationPartType.LEVEL);
		this.addLocationPartType(4, LocationPartType.SLOT);
		this.setSeparator("-");
		return this;
	}

	@Override
	public Class<LinearLocationScheme> getObjectType() {
		return LinearLocationScheme.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public int getNumbeOfParts() {
		return 5;
	}

}
