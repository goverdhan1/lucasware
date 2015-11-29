package com.lucas.services.util;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucas.entity.ui.viewconfig.ActualViewConfig;
import com.lucas.entity.ui.widgetinstance.DefaultWidgetInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.Override;
import java.util.Comparator;

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
public class WidgetInstanceComparator implements Comparator<DefaultWidgetInstance> {

    private static final Logger LOG = LoggerFactory.getLogger(WidgetInstanceComparator.class);

    @Override
    public int compare(DefaultWidgetInstance defaultWidgetInstance1, DefaultWidgetInstance defaultWidgetInstance2) {
        if (defaultWidgetInstance1 != null && defaultWidgetInstance1.getActualViewConfig() != null
                && defaultWidgetInstance2 != null && defaultWidgetInstance2.getActualViewConfig() != null) {
            try {
                final ActualViewConfig actualViewConfig1 = this.getActualViewConfig(defaultWidgetInstance1.getActualViewConfig());
                final ActualViewConfig actualViewConfig2 = this.getActualViewConfig(defaultWidgetInstance2.getActualViewConfig());
                if (actualViewConfig1.getPosition() > actualViewConfig2.getPosition()) {
                    return 1;
                } else {
                    return -1;
                }
            } catch (Exception e) {
                LOG.error(e.getLocalizedMessage());
            }
        }
        return 0;
    }

    private ActualViewConfig getActualViewConfig(String actualViewConfig) throws Exception {
        final ObjectMapper om = new ObjectMapper();
        om.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        return om.readValue(actualViewConfig, ActualViewConfig.class);
    }

}
