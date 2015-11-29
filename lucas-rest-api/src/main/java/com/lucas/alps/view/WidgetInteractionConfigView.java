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
package com.lucas.alps.view;

import com.lucas.entity.ui.widgetinstance.WidgetInteractionConfig;

public class WidgetInteractionConfigView {

    private WidgetInteractionConfig widgetInteractionConfig;

    public WidgetInteractionConfigView() {
        this.widgetInteractionConfig = new WidgetInteractionConfig();
    }

    public WidgetInteractionConfigView(WidgetInteractionConfig widgetInteractionConfig) {
        this.widgetInteractionConfig = widgetInteractionConfig;
    }

    public WidgetInteractionConfig getWidgetInteractionConfig() {
        return widgetInteractionConfig;
    }

    public void setWidgetInteractionConfig(WidgetInteractionConfig widgetInteractionConfig) {
        this.widgetInteractionConfig = widgetInteractionConfig;
    }

}
