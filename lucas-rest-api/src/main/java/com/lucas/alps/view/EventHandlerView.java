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

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.lucas.alps.viewtype.EventDetailsView;
import com.lucas.alps.viewtype.EventHandlerDetailView;
import com.lucas.alps.viewtype.MappingDetailView;
import com.lucas.entity.eai.event.EventHandler;

public class EventHandlerView implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5165812846517959721L;

    private EventHandler eventHandler;

    public EventHandlerView() {
        this.eventHandler = new EventHandler();
    }

    public EventHandlerView(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    /**
     * @return the eventHandler
     */
    @JsonIgnore
    public EventHandler getEventHandler() {
        return eventHandler;
    }

    /**
     * @param eventHandler the eventHandler to set
     */
    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    @JsonView({EventHandlerDetailView.class, MappingDetailView.class, EventDetailsView.class})
    public Long getEventHandlerId() {
        if (this.eventHandler != null) {
            return eventHandler.getEventHandlerId();
        }
        return null;
    }

    public void setEventHandlerId(Long eventHandlerId) {
        if (this.eventHandler != null) {
            eventHandler.setEventHandlerId(eventHandlerId);
        }
    }

    @JsonView({EventHandlerDetailView.class, MappingDetailView.class, EventDetailsView.class})
    public String getEventHandlerName() {
        if (this.eventHandler != null) {
            return eventHandler.getEventHandlerName();
        }
        return null;
    }

    public void setEventHandlerName(String eventHandlerName) {
        if (this.eventHandler != null) {
            eventHandler.setEventHandlerName(eventHandlerName);
        }
    }

    @JsonView({EventHandlerDetailView.class, MappingDetailView.class, EventDetailsView.class})
    public String getEventHandlerType() {
        if (this.eventHandler != null) {
            return eventHandler.getEventHandlerType();
        }
        return null;
    }

    public void setEventHandlerType(String eventHandlerType) {
        if (this.eventHandler != null) {
            eventHandler.setEventHandlerType(eventHandlerType);
        }
    }

    @JsonView({EventHandlerDetailView.class})
    public MappingView getInboundMapping() {
        if (this.eventHandler != null && this.eventHandler.getEaiMappingByInboundMappingId() != null) {
            return new MappingView(this.eventHandler.getEaiMappingByInboundMappingId());
        }
        return null;
    }

    public void setInboundMapping(
            MappingView inboundMapping) {
        if (this.eventHandler != null) {
            this.eventHandler.setEaiMappingByInboundMappingId(inboundMapping.getMapping());
        }
    }

    @JsonView({EventHandlerDetailView.class})
    public MappingView getOutboundMapping() {
        if (this.eventHandler != null && this.eventHandler.getEaiMappingByOutboundMappingId() != null) {
            return new MappingView(this.eventHandler.getEaiMappingByOutboundMappingId());
        }
        return null;
    }

    public void setOutboundMapping(
            MappingView outboundMapping) {
        if (this.eventHandler != null) {
            this.eventHandler.setEaiMappingByOutboundMappingId(outboundMapping.getMapping());
        }
    }

    @JsonView({EventHandlerDetailView.class})
    public TransportView getTransport() {
        if (this.eventHandler != null && this.eventHandler.getEaiTransport() != null) {
            return new TransportView(this.eventHandler.getEaiTransport());
        }
        return null;
    }

    public void setTransport(TransportView transportView) {
        if (this.eventHandler != null) {
            this.eventHandler.setEaiTransport(transportView.getTransport());
        }
    }

    public String getEventHandlerDescription() {
        if (this.eventHandler != null) {
            return this.eventHandler.getEventHandlerDescription();
        }
        return null;
    }

    public void setEventHandlerDescription(String eventHandlerDescription) {
        if (this.eventHandler != null) {
            this.eventHandler.setEventHandlerDescription(eventHandlerDescription);
        }
    }

    public Integer getUsageInEvent() {
        if (this.eventHandler != null) {
            return this.eventHandler.getUsageInEvent();
        }
        return null;
    }

    public void setUsageInEvent(Integer usageInEvent) {
        if (this.eventHandler != null) {
            this.eventHandler.setUsageInEvent(usageInEvent);
        }
    }

    public String getOutboundFilePattern() {
        if (this.eventHandler != null) {
            return this.eventHandler.getOutboundFilePattern();
        }
        return null;
    }

    public void setOutboundFilePattern(String outboundFilePattern) {
        if (this.eventHandler != null) {
            this.eventHandler.setOutboundFilePattern(outboundFilePattern);
        }
    }

    public String getInboundFilePattern() {
        if (this.eventHandler != null) {
            return this.eventHandler.getInboundFilePattern();
        }
        return null;
    }

    public void setInboundFilePattern(String inboundFilePattern) {
        if (this.eventHandler != null) {
            this.eventHandler.setInboundFilePattern(inboundFilePattern);
        }
    }


}
