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
package com.lucas.services.user;

import com.lucas.dto.user.PermissionGroupSummaryDTO;
import com.lucas.dto.user.PermissionGroupsDTO;
import com.lucas.services.search.SearchAndSortCriteria;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;


public interface GroupService {

    List<String> getAllPermissionGroupNames() throws  ParseException;
    List<String> getPermissionGroupNamesByUsername(String username);

    Map<String, Long> getPermissionGroupSummary();

    // get Group Details
    public PermissionGroupsDTO getGroupDetails(String userGroup) throws Exception;
    // save Group Details
    public boolean saveGroupDetails(PermissionGroupsDTO permissionGroupDTO) throws IllegalArgumentException, Exception;
    // delete Group Details
    public boolean deleteGroupDetails(PermissionGroupsDTO permissionGroupDTO) throws IllegalArgumentException, Exception;

    public List<PermissionGroupSummaryDTO> getGroupSummaryByNewSearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria) throws IllegalArgumentException, IllegalStateException, Exception;

    public Long getTotalCountForSearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria) throws ParseException, Exception;
}
