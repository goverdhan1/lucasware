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

import com.lucas.entity.ui.license.WidgetLicense;
import com.lucas.entity.ui.reacttomap.ReactToMap;

import javax.persistence.Entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.List;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, include = "all")
public class GroupMaintenanceWidget extends AbstractLicensableWidget {

    private List broadcastList;

    public GroupMaintenanceWidget(){
	}

	public GroupMaintenanceWidget(Long id, String defaultViewConfig
            , String description, WidgetLicense widgetLicense
            , String name, String title, String shortName
            , String definitionMethod, String dataUrl, List broadcastList
            , List reactToList
    ) {
		super();
        this.Id=id;
        this.defaultViewConfig = defaultViewConfig;
        this.description=description;
        this.widgetLicense=widgetLicense;
		this.name = name;
        this.title=title;
		this.shortName = shortName;
		this.widgetType = widgetType;
        this.setDefinitionMethod(definitionMethod);
        this.dataURL=dataUrl;
        this.broadcastList=broadcastList;
        this.setReactToList(reactToList);
	}
}
