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
package com.lucas.dao.user;

import com.lucas.dao.support.GenericDAO;
import com.lucas.dto.user.PermissionGroupsDTO;
import com.lucas.entity.user.PermissionGroup;
import com.lucas.services.search.SearchAndSortCriteria;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface PermissionGroupDAO extends GenericDAO<PermissionGroup> {
	
	List<PermissionGroup> getAllPermissionGroups(List<Long> groupId);
	
	PermissionGroup getPermissionGroupByName(String permissionGroupName);

	List<PermissionGroup> getUserPermissionGroupsByUsername(String username);

	List<PermissionGroup> getPermissionGroupsBySearchAndSortCriteria(
			SearchAndSortCriteria searchAndSortCriteria) throws ParseException;

    List<String> getPermissionGroupNames() throws ParseException;
    List<String> getPermissionGroupNamesByUsername(String username);

    Map<String,Long> getPermissionGroupSummary();

    List<Object[]> getPermissionGroupSummaryBySearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria) throws ParseException ;

    Long getTotalCountForSearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria) throws ParseException;
}
