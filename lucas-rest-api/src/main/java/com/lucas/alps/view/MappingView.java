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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.lucas.alps.viewtype.EventHandlerDetailView;
import com.lucas.alps.viewtype.MappingDetailView;
import com.lucas.entity.eai.event.EventHandler;
import com.lucas.entity.eai.mapping.Mapping;
import com.lucas.entity.eai.mapping.MappingDefinition;
import com.lucas.entity.eai.mapping.MappingPath;
import com.lucas.entity.eai.message.Message;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author Adarsh
 * @Product Lucas.
 * Created By: Adarsh
 * Created On Date: 6/25/15  Time: 00:46 AM
 * This Class provide the implementation for the functionality of..
 */
public class MappingView {

    private Mapping mapping;

    public MappingView() {
        this.mapping = new Mapping();
    }

    public MappingView(Mapping mapping) {
        this.mapping = mapping;
    }

    @JsonView({EventHandlerDetailView.class,MappingDetailView.class})
    public Long getMappingId() {
        if (this.mapping != null) {
            return this.mapping.getMappingId();
        }
        return null;
    }

    public void setMappingId(final Long mappingId) {
        if (this.mapping != null) {
            this.mapping.setMappingId(mappingId);
        }
    }

    @JsonView({MappingDetailView.class})
    public String getMappingDescription() {
        if (this.mapping != null) {
            return this.mapping.getMappingDescription();
        }
        return null;
    }

    public void setMappingDescription(String mappingDescription) {
        if (this.mapping != null) {
            this.mapping.setMappingDescription(mappingDescription);
        }
    }

    @JsonView({EventHandlerDetailView.class,MappingDetailView.class})
    public String getMappingName() {
        if (this.mapping != null) {
            return this.mapping.getMappingName();
        }
        return null;
    }

    public void setMappingName(String mappingName) {
        if (this.mapping != null) {
            this.mapping.setMappingName(mappingName);
        }
    }



    @JsonView({MappingDetailView.class})
    public Integer getUsageInEventsHandlers() {
        if (this.mapping != null) {
            return this.mapping.getUsageInEventsHandlers();
        }
        return null;
    }

    public void setUsageInEventsHandlers(Integer usageInEventsHandlers) {
        if (this.mapping != null) {
            this.mapping.setUsageInEventsHandlers(usageInEventsHandlers);
        }
    }

    @JsonView({MappingDetailView.class})
    public MessageView getDestinationMessage() {
        if (this.mapping != null && this.mapping.getDestinationMessage() !=null) {
           return new MessageView(this.mapping.getDestinationMessage());
        }
        return null;
    }

    public void setDestinationMessage(MessageView messageView) {
        if (this.mapping != null && messageView.getMessage() !=null) {
             this.mapping.setDestinationMessage(messageView.getMessage());
        }
    }

    @JsonView({MappingDetailView.class})
    public MessageView getSourceMessage() {
        if (this.mapping != null && this.mapping.getSourceMessage() !=null) {
            return new MessageView(this.mapping.getSourceMessage());
        }
        return null;
    }

    public void setSourceMessage(Message sourceMessage) {
        if (this.mapping != null) {
            this.mapping.setSourceMessage(sourceMessage);
        }
    }

    @JsonView({MappingDetailView.class})
    public List<MappingPathView> getMappingPaths() {
        if (this.mapping != null && this.mapping.getMappingPaths() !=null) {
            final List<MappingPath> eaiMappingPaths = new ArrayList<>(this.mapping.getMappingPaths());
            if (eaiMappingPaths != null) {
                final List<MappingPathView> mappingPathViews = new ArrayList<>(eaiMappingPaths.size());
                for (MappingPath mappingPath : eaiMappingPaths) {
                    mappingPathViews.add(new MappingPathView(mappingPath));
                }
                return mappingPathViews;
            }
        }
        return null;
    }

    public void setEaiMappingPaths(final List<MappingPathView> mappingPathViews) {
        if (mappingPathViews != null && mapping != null) {
            final Set<MappingPath> pathHashSet = new HashSet<>(mappingPathViews.size());
            for (MappingPathView mappingPathView : mappingPathViews) {
                pathHashSet.add(mappingPathView.getMappingPath());
            }
            this.mapping.setMappingPaths(pathHashSet);
        }
    }

    public List<EventHandlerView> getEaiEventHandlersForOutboundMappingId() {
        if (this.mapping != null && this.mapping.getEaiEventHandlersForOutboundMappingId()!=null) {
            final List<EventHandler> eventHandlers = new ArrayList<>(this.mapping.getEaiEventHandlersForOutboundMappingId());
            if (eventHandlers != null) {
                final List<EventHandlerView> eventHandlerViews = new ArrayList<>(eventHandlers.size());
                for (EventHandler eventHandler : eventHandlers) {
                    eventHandlerViews.add(new EventHandlerView(eventHandler));
                }
                return eventHandlerViews;
            }
        }
        return null;
    }

    public void setEaiEventHandlersForOutboundMappingId(
            List<EventHandlerView> eventHandlerViews) {
        if (eventHandlerViews != null && mapping != null) {
            final Set<EventHandler> eventHandlers = new HashSet<>(eventHandlerViews.size());
            for (EventHandlerView handlerView : eventHandlerViews) {
                eventHandlers.add(handlerView.getEventHandler());
            }
            this.mapping.setEaiEventHandlersForOutboundMappingId(eventHandlers);
        }
    }

    public List<EventHandlerView> getEaiEventHandlersForInboundMappingId() {
        if (this.mapping != null && this.mapping.getEaiEventHandlersForInboundMappingId()!=null) {
            final List<EventHandler> eventHandlers = new ArrayList<>(this.mapping.getEaiEventHandlersForInboundMappingId());
            if (eventHandlers != null) {
                final List<EventHandlerView> eventHandlerViews = new ArrayList<>(eventHandlers.size());
                for (EventHandler eventHandler : eventHandlers) {
                    eventHandlerViews.add(new EventHandlerView(eventHandler));
                }
                return eventHandlerViews;
            }
        }
        return null;
    }

    public void setEaiEventHandlersForInboundMappingId(
            List<EventHandlerView> eventHandlerViews) {
        if (eventHandlerViews != null && mapping != null) {
            final Set<EventHandler> eventHandlers = new HashSet<>(eventHandlerViews.size());
            for (EventHandlerView handlerView : eventHandlerViews) {
                eventHandlers.add(handlerView.getEventHandler());
            }
            this.mapping.setEaiEventHandlersForInboundMappingId(eventHandlers);
        }
    }

    public List<MappingDefinitionView> getEaiMappingDefinitions() {
        if (this.mapping != null && this.mapping.getEaiMappingDefinitions()!=null) {
            final List<MappingDefinition> mappingDefinitions = new ArrayList<>(this.mapping.getEaiMappingDefinitions());
            if (mappingDefinitions != null) {
                final List<MappingDefinitionView> mappingDefinitionViews = new ArrayList<>(mappingDefinitions.size());
                for (MappingDefinition mappingDefinition : mappingDefinitions) {
                    mappingDefinitionViews.add(new MappingDefinitionView(mappingDefinition));
                }
                return mappingDefinitionViews;
            }
        }
        return null;
    }

    public void setEaiMappingDefinitions(List<MappingDefinitionView> mappingDefinitionViews) {
        if (mappingDefinitionViews != null && mapping != null) {
            final Set<MappingDefinition> mappingDefinitions = new HashSet<>(mappingDefinitionViews.size());
            for (MappingDefinitionView mappingDefinitionView : mappingDefinitionViews) {
                mappingDefinitions.add(mappingDefinitionView.getMappingDefinition());
            }
            this.mapping.setEaiMappingDefinitions(mappingDefinitions);
        }
    }


    @JsonIgnore
    public Mapping getMapping() {
        return mapping;
    }

    public void setMapping(Mapping mapping) {
        this.mapping = mapping;
    }
}
