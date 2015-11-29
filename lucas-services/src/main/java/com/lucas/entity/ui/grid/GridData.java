package com.lucas.entity.ui.grid;

import java.util.Map;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

/**
 * 
 * @author Pankaj Tandon
 *
 */
public interface GridData {
	
	/**
	 * This method will return GridData that will then be converted to JSON as outlined here: http://vmjira:8080/browse/PHX-274
	 * @return
	 */
	Map<String, GridColumn> getGridData();
	void  setGridData(Map<String, GridColumn> map);
}
