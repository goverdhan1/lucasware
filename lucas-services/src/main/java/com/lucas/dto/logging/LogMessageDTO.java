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
package com.lucas.dto.logging;


import com.fasterxml.jackson.databind.annotation.*;
import com.fasterxml.jackson.datatype.joda.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.joda.ser.LocalDateTimeSerializer;
import org.joda.time.LocalDateTime;

/**
 * Created by avin on 7/22/2015.
 */
public class LogMessageDTO {
    public LogMessageDTO() {
    }

    private LogSource source;
    private String message;
    private LogSeverity severity;
    private String username;
    private String deviceId;
    private boolean writeToFile;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime timeStamp;

    public boolean isWriteToFile() {
        return writeToFile;
    }

    public void setWriteToFile(boolean writeToFile) {
        this.writeToFile = writeToFile;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public LogSeverity getSeverity() {

        return severity;
    }

    public LogSource getSource() {
        return source;
    }

    public void setSource(LogSource source) {
        this.source = source;
    }

    public void setSeverity(LogSeverity severity) {
        this.severity = severity;
    }
}
