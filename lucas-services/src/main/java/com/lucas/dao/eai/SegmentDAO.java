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
package com.lucas.dao.eai;

import java.text.ParseException;
import java.util.List;

import javax.swing.text.Segment;

import com.lucas.dao.support.GenericDAO;
import com.lucas.entity.eai.message.SegmentDefinition;
import com.lucas.entity.eai.message.Segments;
import com.lucas.services.search.SearchAndSortCriteria;

/**
 * @author Naveen
 * 
 */
public interface SegmentDAO extends GenericDAO<Segments> {

	/**
	 * 
	 * @param searchAndSortCriteria
	 * @return
	 */
	List<Segments> getSegmentListBySearchAndSortCriteria(
			SearchAndSortCriteria searchAndSortCriteria) throws ParseException;

	/**
	 * 
	 * @param searchAndSortCriteria
	 * @return
	 * @throws ParseException
	 */
	Long getTotalCountForSearchAndSortCriteria(
			SearchAndSortCriteria searchAndSortCriteria) throws ParseException;

	/**
	 * @param segmentName
	 * @return
	 */
	Segments getSegment(String segmentName);

	/**
	 * @param segmentId
	 * @return
	 */
	Segments findBySegmentId(Long segmentId);

}
