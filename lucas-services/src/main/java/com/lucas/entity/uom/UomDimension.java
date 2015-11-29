package com.lucas.entity.uom;

/**
 * A UOM Dimension represents a family of interchangeable UOMs.
 * Note that one UOM family is assigned one UomDimension.
 * @author Pankaj Tandon
 *
 */
public enum UomDimension {
	QUANTITY("Quantity", "Q"), 
	DISTANCE ("Distance", "D"), 
	WEIGHT ("Weight", "W"), 
	TIME ("Time", "t"), 
	TEMPRATURE ("Temperature", "T"), 
	CURRENCY ("Currency", "C"), 
	PRESSURE ("Pressure", "P"), 
	ENERGY ("Energy", "E");
	
	private String desc;
	private String id;
	
	private UomDimension(String desc, String id){
		this.desc = desc;
		this.id = id;
	}
	
	public String getDesc(){
		return desc;
	}
	
	public String getId(){
		return id;
	}
}
