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
package com.lucas.services.logging;


import com.lucas.dto.logging.LogMessageDTO;

import java.io.IOException;

/**
 * @Author Avin
 * @Product Lucas.
 * This class provides the interface for logging messages and events originating from the Mobile Application
 */

public interface LoggingService {

    public void saveLogMessage(LogMessageDTO logMessage) throws IOException;

}
