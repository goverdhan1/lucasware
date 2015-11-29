
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
package com.lucas.services.util;

import com.lucas.entity.ui.canvas.OpenUserCanvas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;

/**
 * @author Adarsh kumar
 * OpenUserCanvasComparator provide the functionality for comparing the OpenUserCanvas
 * based on the displayOrder of the OpenUserCanvas which is used in the
 * Collection.sort() before rendering the data to the controller
 */
public class OpenUserCanvasComparator implements Comparator<OpenUserCanvas> {

    private static final Logger LOG = LoggerFactory.getLogger(WidgetInstanceComparator.class);

    @Override
    public int compare(OpenUserCanvas openUserCanvas1, OpenUserCanvas openUserCanvas2) {
        if (openUserCanvas1 != null && openUserCanvas1.getDisplayOrder() != null
                && openUserCanvas2 != null && openUserCanvas2.getDisplayOrder() != null) {
            try {
                if (openUserCanvas1.getDisplayOrder() > openUserCanvas2.getDisplayOrder()) {
                    return 1;
                } else
                    return -1;
            } catch (Exception e) {
                LOG.error(e.getLocalizedMessage());
            }
        }
        return 0;
    }
}
