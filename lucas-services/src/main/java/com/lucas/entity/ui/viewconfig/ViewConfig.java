package com.lucas.entity.ui.viewconfig;

import java.util.List;



public interface ViewConfig {
	
	List<Integer> getAnchor();
	Long getMinimumWidth();
	Long getMinimumHeight();
	Long getZindex();
	
	void setAnchor(List<Integer>  anchorPoint);
	void setMinimumWidth(Long minimumWidth);
	void setMinimumHeight(Long minimumHeight);
	void setZindex(Long zindex);

}
