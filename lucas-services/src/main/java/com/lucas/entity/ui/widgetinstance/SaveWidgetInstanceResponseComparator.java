/**
 * 
 */
package com.lucas.entity.ui.widgetinstance;

import java.util.Comparator;
import java.util.Map;

/**This class is a comparator class for sorting the response of the saved 
 * widgetinstances according to their position on the canvas (/canvases/save)
 * @author Prafull
 *
 */
public class SaveWidgetInstanceResponseComparator implements Comparator<Map<String, String>>{
	
	/**
	 * 
	 */
	public SaveWidgetInstanceResponseComparator() {

	}
	
	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Map<String, String> o1, Map<String, String> o2) {
		 int pos1 = o1.get("position") != null ? Integer.parseInt(o1.get("position")):0;
		 int pos2 = o2.get("position") != null ? Integer.parseInt(o2.get("position")):0;
		 
		 if(pos1 > pos2){
			 return 1;
		 }
		 
		 if(pos1 < pos2){
			 return -1;
		 }
		 
		return 0;
	}


}
