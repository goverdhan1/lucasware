package com.lucas.entity.location;

import com.mysql.jdbc.StringUtils;

public class Slot implements LocationPart{
	private String id;
	
	protected Slot() {} //Immutable yet open for subclassing
	
	public Slot(String id){
		this.id = id;
	}

	@Override
	public String getLocationPart() {
		String s = null;
		if (!StringUtils.isNullOrEmpty(id)) {
			if (id.length() >= 3){
				s = id.substring(0,3);
			} else if (id.length() < 3){
				s = id.substring(0,1);
			}
		}
		return s;
	}

	@Override
	public LocationPartType getLocationPartType() {
		return LocationPartType.SLOT;
	}
}
