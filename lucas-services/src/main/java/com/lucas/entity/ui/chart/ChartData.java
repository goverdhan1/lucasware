package com.lucas.entity.ui.chart;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author Pankaj Tandon
 *
 */
public interface ChartData {
	
	ChartType getChartType();
	
	/**
	 * This method will return ChartData that will then be converted to JSON as outlined here:http://vmjira:8080/browse/PHX-360
	 * @return
	 */
	Map<String, List<String>> getChartData();

}
