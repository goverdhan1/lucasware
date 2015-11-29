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

import javax.persistence.Entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The widget class for the event grid widget
 * @author Prafull
 *
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, include = "all")
public class EventGridWidget extends AbstractLicensableWidget {
	
	
	public EventGridWidget(){
		
	}
	
	public EventGridWidget(String name, String shortName,
			WidgetType widgetType, String defaultViewConfig,
			 String title) {

		super();
		this.name = name;
		this.shortName = shortName;
		this.widgetType = widgetType;
		this.defaultViewConfig = defaultViewConfig;
		this.title = title;
	}

}
