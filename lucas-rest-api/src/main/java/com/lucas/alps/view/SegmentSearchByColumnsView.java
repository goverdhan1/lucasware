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
import com.lucas.alps.viewtype.SegmentSearchByColumnsDetailsView;
import com.lucas.entity.eai.message.Segments;
import com.lucas.entity.ui.viewconfig.GridColumn;
import com.lucas.entity.user.User;
import com.lucas.services.util.CollectionsUtilService;

/**
 * @author Naveen
 * 
 */
public class SegmentSearchByColumnsView implements Serializable {

	private List<Segments> segmentList;
	private Long totalRecords;

	public SegmentSearchByColumnsView() {

	}

	public SegmentSearchByColumnsView(List<Segments> segmentList) {
		this.segmentList = segmentList;
	}

	public SegmentSearchByColumnsView(List<Segments> segmentList,
			Long totalRecords) {
		this.segmentList = segmentList;
		this.totalRecords = totalRecords;
	}

	@JsonView(SegmentSearchByColumnsDetailsView.class)
	public Map<String, GridColumnView> getGrid() throws IOException {
		LinkedHashMap<String, GridColumnView> gridView = new LinkedHashMap<String, GridColumnView>();
		for (int i = 1; i <= BaseView.TOTAL_NO_OF_SEGMENT_GRID_COLUMNS; i++) {
			GridColumn gridColumn = new GridColumn();
			gridView.put(i + "", new GridColumnView(gridColumn));
		}
		for (Segments segment : CollectionsUtilService.nullGuard(segmentList)) {
			gridView.get("1")
					.getValues()
					.add((segment.getSegmentName() != null) ? segment
							.getSegmentName() : BaseView.ZERO_LENGTH_STRING);
			gridView.get("2")
					.getValues()
					.add((segment.getSegmentDescription() != null) ? segment
							.getSegmentDescription()
							: BaseView.ZERO_LENGTH_STRING);
			gridView.get("3")
					.getValues()
					.add(segment.getSegmentLength()
							+ BaseView.ZERO_LENGTH_STRING);
		}

		return gridView;
	}

	public void setGrid(Map<String, GridColumnView> gridView)
			throws ParseException {
		int pageSize = gridView.get("1").getValues().size();
		segmentList = new ArrayList<Segments>();
		for (int i = 0; i < pageSize; i++) {
			segmentList.add(new Segments());
			segmentList.get(i).setSegmentName(
					gridView.get("1").getValues().get(i));
			segmentList.get(i).setSegmentDescription(
					gridView.get("2").getValues().get(i));
			segmentList.get(i).setSegmentLength(
					new Integer(gridView.get("3").getValues().get(i))
							.intValue());
		}

	}

	/**
	 * @return the segmentList
	 */
	public List<Segments> getSegmentList() {
		return segmentList;
	}

	/**
	 * @param segmentList
	 *            the segmentList to set
	 */
	public void setSegmentList(List<Segments> segmentList) {
		this.segmentList = segmentList;
	}

	/**
	 * @return the totalRecords
	 */
	@JsonView(SegmentSearchByColumnsDetailsView.class)
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
