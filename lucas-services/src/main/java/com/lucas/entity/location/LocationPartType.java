package com.lucas.entity.location;

public enum LocationPartType {
	ZONE("Zone", "Z"),
	BAY("Bay", "B"),
	AISLE("Aisle", "A"),
	LEVEL("Level", "L"),
	SLOT("Slot", "S");
	private String name;
	private String singleCharacterMnemonic;
	
	private LocationPartType(String name, String singleCharacterMnemonic){
		this.name = name;
		this.singleCharacterMnemonic = singleCharacterMnemonic;
	}
	
	public String getName(){
		return name;
	}
	
	public String getSingleCharacterMnemonic(){
		return singleCharacterMnemonic;
	}
	
	
}
