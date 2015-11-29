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
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.lucas.alps.viewtype.BaseView;
import com.lucas.alps.viewtype.MappingSearchByColumnsDetailsView;
import com.lucas.entity.eai.mapping.Mapping;
import com.lucas.entity.eai.message.Message;
import com.lucas.entity.ui.viewconfig.GridColumn;
import com.lucas.services.eai.MessageService;
import com.lucas.services.util.CollectionsUtilService;

/**
 * @author Naveen
 * 
 */
public class MappingSearchByColumnsView implements Serializable {

	private List<Mapping> mappingList;
	private Long totalRecords;

	public MappingSearchByColumnsView() {

	}

	public MappingSearchByColumnsView(List<Mapping> mappingList) {
		this.mappingList = mappingList;
	}

	public MappingSearchByColumnsView(List<Mapping> mappingList,
			Long totalRecords) {
		this.mappingList = mappingList;
		this.totalRecords = totalRecords;
	}

	@JsonView(MappingSearchByColumnsDetailsView.class)
	public Map<String, GridColumnView> getGrid() throws IOException {
		LinkedHashMap<String, GridColumnView> gridView = new LinkedHashMap<String, GridColumnView>();
		for (int i = 1; i <= BaseView.TOTAL_NO_OF_MAPPING_GRID_COLUMNS; i++) {
			GridColumn gridColumn = new GridColumn();
			gridView.put(i + "", new GridColumnView(gridColumn));
		}
		for (Mapping mapping : CollectionsUtilService.nullGuard(mappingList)) {
			gridView.get("1")
					.getValues()
					.add((mapping != null && mapping.getMappingName() != null && !mapping.getMappingName().isEmpty()) ? mapping
							.getMappingName() : BaseView.ZERO_LENGTH_STRING);
			gridView.get("2")
					.getValues()
					.add((mapping != null && !mapping.getMappingDescription().isEmpty() && mapping.getMappingDescription() != null) ? mapping
							.getMappingDescription()
							: BaseView.ZERO_LENGTH_STRING);
			
			gridView.get("3").getValues().add((mapping != null && !mapping.getSourceMessage().getMessageName().isEmpty() && mapping.getSourceMessage().getMessageName() !=null) ? mapping.getSourceMessage().getMessageName() : BaseView.ZERO_LENGTH_STRING);
			gridView.get("4").getValues().add((mapping != null && !mapping.getDestinationMessage().getMessageName().isEmpty()  && mapping.getDestinationMessage().getMessageName() !=null) ? mapping.getDestinationMessage().getMessageName() : BaseView.ZERO_LENGTH_STRING);

		}

		return gridView;
	}

	public void setGrid(final Map<String, GridColumnView> gridView)
			throws ParseException {
		int pageSize = gridView.get("1").getValues().size();
		mappingList = new ArrayList<Mapping>();
		 for (int index = 0; index < pageSize; index++) {
	            final int i = index;
			mappingList.add(new Mapping());
			mappingList.get(i).setMappingName(
					gridView.get("1").getValues().get(i));
			mappingList.get(i).setMappingDescription(
					gridView.get("2").getValues().get(i));
			mappingList.get(i).setSourceMessage(new Message(){
				{
				setMessageName(gridView.get("3").getValues().get(i));
				}
			});
			mappingList.get(i).setDestinationMessage(new Message(){
				{
				setMessageName(gridView.get("4").getValues().get(i));
				}
			});
		}

	}

	/**
	 * @return the mappingList
	 */
	public List<Mapping> getMappingList() {
		return mappingList;
	}

	/**
	 * @param mappingList
	 *            the mappingList to set
	 */
	public void setMappingList(List<Mapping> mappingList) {
		this.mappingList = mappingList;
	}

	/**
	 * @return the totalRecords
	 */
	@JsonView(MappingSearchByColumnsDetailsView.class)
	public Long getTotalRecords() {
		return totalRecords;
	}

	/**
	 * @param totalRecords
	 *            the totalRecords to set
	 */
	public void setTotalRecords(Long totalRecords) {
		this.totalRecords = totalRecords;
	}

}
