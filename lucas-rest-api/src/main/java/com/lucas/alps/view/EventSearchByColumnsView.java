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

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonView;
import com.lucas.alps.viewtype.BaseView;
import com.lucas.alps.viewtype.EventSearchByColumnsDetailsView;
import com.lucas.entity.eai.event.Event;
import com.lucas.entity.ui.viewconfig.GridColumn;
import com.lucas.services.util.CollectionsUtilService;

/**
 * @Prafull
 * This Class provide the implementation for the functionality for exposing
 * the rest end points for event by columns.
 */
public class EventSearchByColumnsView implements Serializable{

    /**
	 * Serial version ID
	 */
	private static final long serialVersionUID = 1L;
	private List<Event> events;
    private Long totalRecords;

    public EventSearchByColumnsView() {
    }

    public EventSearchByColumnsView(List<Event> events) {
        this.events = events;
    }

    public EventSearchByColumnsView(List<Event> events, Long totalRecords) {
        this.events = events;
        this.totalRecords = totalRecords;
    }

    @JsonView(EventSearchByColumnsDetailsView.class)
    public Map<String, GridColumnView> getGrid() throws IOException {
        LinkedHashMap<String, GridColumnView> gridView = new LinkedHashMap<String, GridColumnView>();
        for (int i = 1; i <= BaseView.TOTAL_NO_OF_EVENT_GRID_COLUMNS; i++) {
            GridColumn gridColumn = new GridColumn();
            gridView.put(i + "", new GridColumnView(gridColumn));
        }

        for (Event event : CollectionsUtilService.nullGuard(events)) {
            gridView.get("1").getValues().add((event.getEventName() != null) ? event.getEventName() : BaseView.ZERO_LENGTH_STRING);
            gridView.get("2").getValues().add((event.getEventDescription() != null) ? event.getEventDescription() : BaseView.ZERO_LENGTH_STRING);
            gridView.get("3").getValues().add((event.getEventType() != null ) ? event.getEventType() : BaseView.ZERO_LENGTH_STRING);
            gridView.get("4").getValues().add((event.getEventSource() != null ) ? event.getEventSource() : BaseView.ZERO_LENGTH_STRING);
            gridView.get("5").getValues().add((event.getEventCategory() != null ) ? event.getEventCategory() : BaseView.ZERO_LENGTH_STRING);
            gridView.get("6").getValues().add((event.getEventSubCategory() != null ) ? event.getEventSubCategory() : BaseView.ZERO_LENGTH_STRING);
              }
        return gridView;
    }


    public void setGrid(final Map<String, GridColumnView> gridView)
            throws ParseException {
        int pageSize = gridView.get("1").getValues().size();
        events = new ArrayList<Event>();
        
        for (int i = 0; i < pageSize; i++) {
        	events.add(new Event());
        	events.get(i).setEventName(gridView.get("1").getValues().get(i));
        	events.get(i).setEventDescription(gridView.get("2").getValues().get(i));
        	events.get(i).setEventType(gridView.get("3").getValues().get(i));
        	events.get(i).setEventSource(gridView.get("4").getValues().get(i));
        	events.get(i).setEventCategory(gridView.get("5").getValues().get(i));
        	events.get(i).setEventSubCategory(gridView.get("6").getValues().get(i));
        }
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    @JsonView(EventSearchByColumnsDetailsView.class)
    public Long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Long totalRecords) {
        this.totalRecords = totalRecords;
    }
}
