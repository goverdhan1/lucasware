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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.lucas.alps.viewtype.CanvasDataView;
import com.lucas.alps.viewtype.widget.EventHandlerGridWidgetDetailsView;
import com.lucas.alps.viewtype.widget.MessageMappingGridWidgetDetailsView;
import com.lucas.alps.viewtype.widget.MessageSegmentGridWidgetDetailsView;
import com.lucas.alps.viewtype.widget.EquipmentManagerGridWidgetDetailsView;
import com.lucas.alps.viewtype.widget.MessageGridWidgetDetailsView;
import com.lucas.alps.viewtype.widget.EventGridWidgetDetailsView;
import com.lucas.alps.viewtype.widget.SearchGroupGridWidgetDetailsView;
import com.lucas.alps.viewtype.widget.EquipmentTypeGridWidgetDetailsView;
import com.lucas.alps.viewtype.widget.SearchUserGridWidgetDetailsView;
import com.lucas.entity.ui.viewconfig.AutoRefreshConfig;

public class AutoRefreshConfigView {

    private AutoRefreshConfig autoRefreshConfig;

    public AutoRefreshConfigView() {

        autoRefreshConfig = new AutoRefreshConfig();
    }

    public AutoRefreshConfigView(AutoRefreshConfig autoRefreshConfig) {

        this.autoRefreshConfig = autoRefreshConfig;
    }

    @JsonView({CanvasDataView.class,EquipmentTypeGridWidgetDetailsView.class,SearchUserGridWidgetDetailsView.class,SearchGroupGridWidgetDetailsView.class,EquipmentManagerGridWidgetDetailsView.class,MessageSegmentGridWidgetDetailsView.class
    	, EventGridWidgetDetailsView.class,MessageGridWidgetDetailsView.class,MessageMappingGridWidgetDetailsView.class,EventHandlerGridWidgetDetailsView.class})
    public boolean getGlobalOverride() {
        if (autoRefreshConfig != null) {
            return autoRefreshConfig.getGlobalOverride();
        }
        return false;
    }

    public void setGlobalOverride(boolean globalOverride) {
        this.autoRefreshConfig.setGlobalOverride(globalOverride);
    }

    @JsonView({CanvasDataView.class,EquipmentTypeGridWidgetDetailsView.class,SearchUserGridWidgetDetailsView.class,SearchGroupGridWidgetDetailsView.class,EquipmentManagerGridWidgetDetailsView.class,MessageSegmentGridWidgetDetailsView.class
    	, EventGridWidgetDetailsView.class,MessageGridWidgetDetailsView.class,MessageMappingGridWidgetDetailsView.class,EventHandlerGridWidgetDetailsView.class})
    public boolean getEnabled() {
        if (autoRefreshConfig != null) {
            return autoRefreshConfig.getEnabled();
        }
        return false;
    }

    public void setEnabled(boolean enabled) {
        this.autoRefreshConfig.setEnabled(enabled);
    }

    @JsonView({CanvasDataView.class,EquipmentTypeGridWidgetDetailsView.class,SearchUserGridWidgetDetailsView.class,SearchGroupGridWidgetDetailsView.class,EquipmentManagerGridWidgetDetailsView.class,MessageSegmentGridWidgetDetailsView.class
    	, EventGridWidgetDetailsView.class,MessageGridWidgetDetailsView.class,MessageMappingGridWidgetDetailsView.class,EventHandlerGridWidgetDetailsView.class})
    public int getInterval() {
        if (autoRefreshConfig != null) {
            return autoRefreshConfig.getInterval();
        }
        return 0;
    }

    public void setInterval(int interval) {
        this.autoRefreshConfig.setInterval(interval);
    }

    public AutoRefreshConfig getAutoRefreshConfig() {
        return autoRefreshConfig;
    }

    public void setAutoRefreshConfig(AutoRefreshConfig autoRefreshConfig) {
        this.autoRefreshConfig = autoRefreshConfig;
    }

}
