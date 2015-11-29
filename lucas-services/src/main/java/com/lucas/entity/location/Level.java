package com.lucas.entity.location;

import com.mysql.jdbc.StringUtils;

public class Level implements LocationPart{
	private String id;
	private int ordinal;
	
	protected Level() {} //Immutable yet open for subclassing
	public Level(String id){
		this.id = id;
		this.ordinal = 0;
	}
	
	public boolean isHigherThan(Level that){
		boolean boo = false;
		if (this.getOrdinal() > that.getOrdinal()) {
			boo = true;
		}
		return boo;
	}
	
	public boolean isLowerThan(Level that){
		boolean boo = false;
		if (this.getOrdinal() < that.getOrdinal()) {
			boo = true;
		}
		return boo;
	}
	
	public boolean isAtSameLevel(Level that){
		boolean boo = false;
		if (this.getOrdinal() == that.getOrdinal()) {
			boo = true;
		}
		return boo;
	}
	
	public Level(String id, int ordinal){
		this(id);
		this.ordinal = ordinal;
	}

	@Override
	public LocationPartType getLocationPartType() { 
		return LocationPartType.LEVEL;
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
	public String getId() {
		return id;
	}
	public int getOrdinal() {
		return ordinal;
	}
}
