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

package com.lucas.dto.user;

import java.util.List;

/**
 * This is also being used for the new search and sort criteria API
 *
 * @author AvinFernando
 *
 */
public class PermissionGroupSummariesDTO {

    private List<PermissionGroupSummaryDTO> groupList;
    private Long totalRecords;

    public PermissionGroupSummariesDTO(){
    }

    public List<PermissionGroupSummaryDTO> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<PermissionGroupSummaryDTO> groupList) {
        this.groupList = groupList;
    }

    public Long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Long totalRecords) {
        this.totalRecords = totalRecords;
    }
}
