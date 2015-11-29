package com.lucas.entity.location;

import com.mysql.jdbc.StringUtils;

public class Aisle implements LocationPart{
	private String id;
	
	protected Aisle() {}; //immutable, yet open for subclassing
	public Aisle(String id){
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
		return LocationPartType.AISLE;
	}
}
