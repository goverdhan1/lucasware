package com.lucas.entity.location;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import com.mysql.jdbc.StringUtils;
/**
 * This class consists of a <code>map</code> of {@link LocationPart} 
 * 
 * @author Pankaj Tandon
 *
 */
public class Location implements java.io.Serializable {

	private static final long serialVersionUID = -2987371127433447174L;
	private Map<LocationPartType, LocationPart> locationPartMap;
	private LocationScheme<LocationPartType> locationScheme;

	private String id;
	private void setId(String id) {
		this.id = id;
	}
	
	public Location(){
		locationPartMap = new Hashtable<LocationPartType, LocationPart>();
	}
	
	public void addPart(LocationPart locationPart){
		locationPartMap.put(locationPart.getLocationPartType(), locationPart);
	}
	
	public void addParts(LocationPart... locationPart){
		for (int i = 0; i < locationPart.length; i++){
			locationPartMap.put(locationPart[i].getLocationPartType(), locationPart[i]);
		}
	}
	
	public String getId(){
		//Since id is immutable, therefore don't recompute if already there.
		if (StringUtils.isNullOrEmpty(id)){
			this.setId(this.generateId());
		} 
		return id;
	}
	
	public String generateId(){
		StringBuffer sb = new StringBuffer();
		LocationPartType lpt = null;
		Iterator<LocationPartType> it = locationScheme.getIterator();
		while (it.hasNext()) {
			lpt = it.next();
			LocationPart lp = locationPartMap.get(lpt);
			if (lp != null){
				sb.append(lp.getLocationPart());
				if (!StringUtils.isNullOrEmpty(locationScheme.getSeparator())){
					sb.append(locationScheme.getSeparator());
				}
			}
		}
		String s = sb.toString();
		if (!StringUtils.isNullOrEmpty(locationScheme.getSeparator())){
			if (s.endsWith(locationScheme.getSeparator())){
				s = s.substring(0, s.length() -1);
			}
		}
		return s;
	}

	
	/**
	 * Specifies if the passed in location has a boundary or a point that touches the boundary or point of the current location.
	 * Note that locations across the aisle are not considered adjacent. See <code>isAcrossAisle()</code>.
	 * 
	 * TODO: Untested and partially implemented
	 * @param that
	 * @return
	 */
	public boolean isAdjacent(Location that){
		return false;
	}
	
	/**
	 * Specifies if the passed in location is <b>ONLY ONE</b> aisle away from this location.
	 * Note that the passed in location may still be on the other end of the aisle from this one.
	 * 
	 * This is a simple implementation and assumes adjacent if the other location is in the same aisle.
	 * Subclass for changing behavior.
	 * 
	 * TODO: Untested and partially implemented
	 * 
	 * @param that
	 * @return
	 */
	public boolean isAcrossAisle(Location that){
		return (this.getAisle().equals(that.getAisle()));
	}
	
	public LocationPart getPart(LocationPartType lpt){
		return locationPartMap.get(lpt);
	}
	public Zone getZone(){
		return (Zone)locationPartMap.get(LocationPartType.ZONE);
	}
	public Bay getBay(){
		return (Bay)locationPartMap.get(LocationPartType.BAY);
	}
	public Aisle getAisle(){
		return (Aisle)locationPartMap.get(LocationPartType.AISLE);
	}
	public Level getLevel(){
		return (Level)locationPartMap.get(LocationPartType.LEVEL);
	}
	public Slot getSlot(){
		return (Slot)locationPartMap.get(LocationPartType.SLOT);
	}
	
	public void setLocationScheme(LocationScheme<LocationPartType> locationScheme) {
		this.locationScheme = locationScheme;
	}
	
	public String toString(){
		return this.getId();
	}

	/**
	 * Returns the <code>LocationScheme</code> implementation that this location has been configured with.
	 * @return
	 */
	public LocationScheme<LocationPartType> getLocationScheme() {
		return locationScheme;
	}
}
