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
package com.lucas.dao.ui;
import com.lucas.dao.support.GenericDAO;
import com.lucas.entity.ui.canvas.OpenUserCanvas;
import java.util.List;
import com.lucas.entity.ui.canvas.Canvas;

public interface OpenUserCanvasDAO extends GenericDAO<OpenUserCanvas> {

    OpenUserCanvas findOneOpenUserCanvasId(Long userId, Long canvasId);
    List findAllOpenUserCanvases();
	OpenUserCanvas findExistingOpenUserCanvas(Long userId, Canvas canvas);
}
