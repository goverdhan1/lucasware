package com.lucas.entity.location;

import com.mysql.jdbc.StringUtils;

public class Bay implements LocationPart {
	private String id;
	
	protected Bay(){} //immutable, yet open for subclassing
	public Bay(String id){
		this.setId(id);
	}

	@Override
	public String getLocationPart() {
		String s = null;
		if (!StringUtils.isNullOrEmpty(this.getId())) {
			if (this.getId().length() == 3){
				s = this.getId().substring(0,3);
			}
		}
		return s;
	}

	@Override
	public LocationPartType getLocationPartType() {
		return LocationPartType.BAY;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
