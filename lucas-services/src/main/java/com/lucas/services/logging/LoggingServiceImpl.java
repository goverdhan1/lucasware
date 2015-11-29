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


import com.google.common.io.Files;
import com.lucas.dao.logging.LogMessageDAO;
import com.lucas.dto.logging.LogMessageDTO;
import com.lucas.entity.logging.LogMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Author Avin
 * @Product Lucas.
 * This class provides the interface for logging messages and events originating from the Mobile Application
 */

@Service
public class LoggingServiceImpl implements LoggingService {
    private static Logger LOG = LoggerFactory.getLogger(LoggingServiceImpl.class);
    private final LogMessageDAO logMessageDAO;

    @Inject
    public LoggingServiceImpl(LogMessageDAO logMessageDAO) {
        this.logMessageDAO = logMessageDAO;
    }

    @Override
    @Transactional
    public void saveLogMessage(LogMessageDTO logMessage) throws IOException {
        if (logMessage.isWriteToFile()) {
            File file = new File(String.format("/LucasLogs/MobileApp/%1$s/%2$s.txt", logMessage.getTimeStamp().toLocalDate().toString(), logMessage.getUsername()));
            Files.createParentDirs(file);
            Files.append(String.format("[%1$s] %2$s (%3$s): %4$s\n", logMessage.getSeverity(), logMessage.getTimeStamp().toLocalTime().toString(), logMessage.getDeviceId(), logMessage.getMessage()),
                    file, StandardCharsets.UTF_8);
        } else {
            LogMessage entity = new LogMessage(logMessage);
            logMessageDAO.save(entity);
        }
    }

}
