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

import com.lucas.entity.user.Permission;
import com.lucas.entity.user.PermissionGroup;
import com.lucas.entity.user.User;

import java.util.ArrayList;
import java.util.List;

/**
 * This is also being used for the new search and sort criteria API
 *
 * @author DiepLe
 *
 */
public class PermissionGroupSummaryDTO {

    private String groupName;
    private String description;
    private Long userCount;

    public PermissionGroupSummaryDTO(){

    }

    public PermissionGroupSummaryDTO(String groupName,
                                     String description,
                                     Long userCount){

        this.groupName = groupName;
        this.description = description;
        this.userCount = userCount;
    }


    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Long getUserCount() {
        return userCount;
    }

    public void setUserCount(Long userCount) {
        this.userCount = userCount;
    }

    @Override
    public String toString() {
        return "PermissionGroupSummaryDTO{" +
                "groupName='" + groupName + '\'' +
                ", description='" + description + '\'' +
                ", userCount='" + userCount + '\'' +
                '}';
    }
}
