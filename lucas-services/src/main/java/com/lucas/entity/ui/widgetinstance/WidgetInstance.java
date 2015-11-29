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

package com.lucas.entity.ui.widgetinstance;

import com.lucas.entity.ui.widget.AbstractLicensableWidget;


public interface WidgetInstance<T> {

	AbstractLicensableWidget  getWidget();
	void setWidget(AbstractLicensableWidget widget);
	
	String getActualViewConfig();
	void setActualViewConfig(String vc);

	String getDataURL();
	void setDataURL(String dataURL);

    String getWidgetInteractionConfig();
    void setWidgetInteractionConfig(String widgetInteractionConfig);
}
