/*
Lucas Systems Inc
11279 Perry Highway
Wexford
PA 15090
United States


The information in this file contains trade secrets and confidential
information which is the property of Lucas Systems Inc.

All trademarks, trade names, copyrights and other intellectual property
rights created, developed, embodied in or arising in connection with
this software shall remain the sole property of Lucas Systems Inc.

Copyright (c) Lucas Systems, 2014
ALL RIGHTS RESERVED

*/

package com.lucas.entity.ui.widget;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.lucas.entity.ui.license.WidgetLicense;

import java.io.IOException;


public interface LicenseableWidget<T> {
	
	Long getId();
    void setId(Long Id);
    
    WidgetType getWidgetType();
    String getTitle();
	String getName();
	String getShortName();
	String getDescription();
	WidgetLicense getLicense();
	String getDataURL();
	String getDefaultViewConfig() throws JsonParseException, JsonMappingException, IOException;

	void setDefaultViewConfig(String viewConfig) throws JsonProcessingException;	
	void setWidgetType(WidgetType widgetType);
	void setName(String name);
	void setShortName(String shortName);
	void setDescription(String description);
	void setLicense(WidgetLicense widgetLicense);
	void setTitle(String title);
	void setDataURL(String dataURL);
}
