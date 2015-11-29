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
package com.lucas.entity.logging;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.joda.ser.LocalDateTimeSerializer;
import com.lucas.dto.logging.LogMessageDTO;
import com.lucas.dto.logging.LogSeverity;
import com.lucas.dto.logging.LogSource;
import com.lucas.entity.BaseEntity;
import com.lucas.entity.reporting.Printable;
import com.lucas.entity.support.Identifiable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by avin on 7/22/2015.
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, include = "all")
@Table(name = "log_message")
public class LogMessage implements Identifiable<Long>, java.io.Serializable{
    private Long id;
    private LogSource source;
    private String message;
    private LogSeverity severity;
    private String username;
    private String deviceId;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime timeStamp;

    public LogMessage() {}
    public LogMessage(LogMessageDTO messageDTO) {
        this.source = messageDTO.getSource();
        this.message = messageDTO.getMessage();
        this.severity = messageDTO.getSeverity();
        this.username = messageDTO.getUsername();
        this.deviceId = messageDTO.getDeviceId();
        this.timeStamp = messageDTO.getTimeStamp();
    }

    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="log_message_id")
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "source", length = 12)
    @Enumerated(EnumType.STRING)
    public LogSource getSource() {
        return source;
    }

    public void setSource(LogSource source) {
        this.source = source;
    }

    @Column(name = "message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Column(name = "severity", length = 12)
    @Enumerated(EnumType.STRING)
    public LogSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(LogSeverity severity) {
        this.severity = severity;
    }

    @Column(name = "username", length = 100)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "device_id", length = 100)
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Column(name = "log_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getPersistibleTimestamp() { return timeStamp.toDate(); }

    public void setPersistibleTimestamp(Date timeStamp) { this.timeStamp = LocalDateTime.fromDateFields(timeStamp); }

    @Transient
    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}
