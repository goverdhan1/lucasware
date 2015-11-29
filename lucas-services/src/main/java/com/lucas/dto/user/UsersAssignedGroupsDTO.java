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
 * This DTO is used to save users assigned groups. 
 *
 * @author DiepLe
 *
 */
public class UsersAssignedGroupsDTO {

    private String userName;
    private List<String> assignedGroups;

    public UsersAssignedGroupsDTO(){

    }

    public UsersAssignedGroupsDTO(String userName, List<String> assignedGroups){

        this.userName = userName;
        this.assignedGroups = assignedGroups;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getAssignedGroups() {
        return assignedGroups;
    }

    public void setAssignedGroups(List<String> assignedGroups) {
        this.assignedGroups = assignedGroups;
    }

    @Override
    public String toString() {
        return "UsersAssignedGroupsDTO{" +
                "userName='" + userName + '\'' +
                ", assignedGroups=" + assignedGroups +
                '}';
    }

}
