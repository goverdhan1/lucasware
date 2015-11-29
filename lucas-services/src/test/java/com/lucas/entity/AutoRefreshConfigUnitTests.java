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
package com.lucas.entity; 

import com.lucas.entity.ui.viewconfig.AutoRefreshConfig;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

public class AutoRefreshConfigUnitTests {

	private AutoRefreshConfig autoRefreshConfig;

    /**
     * Test setup - initialise a new AutoRefreshConfig() object
     */
	@Before
	public void setup(){
		autoRefreshConfig = new AutoRefreshConfig();
	}

    /**
     * Test setter and getter methods for "GlobalOverride" property
     */
	@Test
	public void testGlobalOverrideConfig(){
		this.autoRefreshConfig.setGlobalOverride(true);
		Assert.assertEquals(true, this.autoRefreshConfig.getGlobalOverride());
	}

    /**
     * Test setter and getter methods for "Enabled" property
     */
    @Test
    public void testEnabledConfig(){
        this.autoRefreshConfig.setEnabled(true);
        Assert.assertEquals(true, this.autoRefreshConfig.getEnabled());
    }

    /**
     * Test setter and getter methods for "Interval" property
     */
    @Test
    public void testIntervalConfig(){
        this.autoRefreshConfig.setInterval(120);
        Assert.assertEquals(120, this.autoRefreshConfig.getInterval());
    }
}
