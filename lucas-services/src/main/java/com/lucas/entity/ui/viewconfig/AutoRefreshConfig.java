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

package com.lucas.entity.ui.viewconfig;

public class AutoRefreshConfig {

    //instance variables
    private boolean globalOverride;
	private boolean enabled;
	private int interval;

    public boolean getGlobalOverride() {
        return this.globalOverride;
    }

    public void setGlobalOverride(boolean globalOverride) {
        this.globalOverride = globalOverride;
    }

	public boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getInterval() {
        return this.interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }
}
