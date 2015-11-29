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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import javax.persistence.Entity;

/**
 * @Author Adarsh
 * Updated By: Adarsh kumar
 * Created On Date: 6/18/15  Time: 00:46 AM
 * This Class provide the implementation for the functionality of EquipmentTypeGridWidget
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, include = "all")
public class EquipmentTypeGridWidget extends AbstractLicensableWidget {

    public EquipmentTypeGridWidget() {
    }

    public EquipmentTypeGridWidget(String name, String shortName,
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
