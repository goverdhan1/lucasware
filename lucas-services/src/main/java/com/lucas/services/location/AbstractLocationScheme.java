package com.lucas.services.location;

import java.util.Iterator;

import com.lucas.entity.location.LocationPartType;
import com.lucas.entity.location.LocationScheme;

public abstract class AbstractLocationScheme implements LocationScheme<LocationPartType> {
	
	/**
	 * Returns the <code>LocationPartType</code> concatenated with the separator configured for this locationScheme
	 * For example: BAY-SLOT-AISLE
	 */
	public String toString(){
		StringBuffer sb = new StringBuffer();
		Iterator<LocationPartType> iterator = this.getIterator();
		while (iterator.hasNext()){
			LocationPartType lpt = (LocationPartType)iterator.next();
			sb.append(lpt.getName());
			sb.append(this.getSeparator());
		}
		String s = sb.toString();
		if (s.endsWith(this.getSeparator())){
			s = s.substring(0, (s.length()-1));
		}
		
		return s;
	}
}
