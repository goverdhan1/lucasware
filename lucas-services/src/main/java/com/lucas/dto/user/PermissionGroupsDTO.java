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
import java.util.Set;

/**
 * This DTO is used for groups permission details.
 *
 * @author WWong
 *
 */
public class PermissionGroupsDTO {

    private String groupName;
    private String description;
    private List<String> assignedPermissions;
    private List<String> enrolledUsers;

    public PermissionGroupsDTO(){

    }

    public PermissionGroupsDTO(String groupName, String description, List<String> assignedPermissions, List<String> enrolledUsers){

        this.groupName = groupName;
        this.description = description;
        this.assignedPermissions = assignedPermissions;
        this.enrolledUsers = enrolledUsers;
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

    public List<String> getAssignedPermissions() {
        return assignedPermissions;
    }

    public void setAssignedPermissions(List<String> assignedPermissions) {
        this.assignedPermissions = assignedPermissions;
    }

    public List<String> getEnrolledUsers() {
        return enrolledUsers;
    }

    public void setEnrolledUsers(List<String> enrolledUsers) {
        this.enrolledUsers = enrolledUsers;
    }

    public void setPermissionGroup(PermissionGroup permissionGroup) {

        List<String> permissionsList = new ArrayList<String>();
        List<String> usersList = new ArrayList<String>();

        this.setGroupName(permissionGroup.getPermissionGroupName());
        this.setDescription(permissionGroup.getPermissionDescription());
        for (Permission perm: permissionGroup.getPermissionGroupPermissionList())
        {
            if (perm != null) {
                permissionsList.add(perm.getPermissionName());
            }
        }
        this.setAssignedPermissions(permissionsList);
        for (User user: permissionGroup.getUserSet())
        {
            if (user != null) {
                usersList.add(user.getUserName());
            }
        }
        this.setEnrolledUsers(usersList);
        return;
    }

    @Override
    public String toString() {
        return "PermissionGroupsDTO{" +
                "groupName='" + groupName + '\'' +
                ", description='" + description + '\'' +
                ", assignedPermissions=" + assignedPermissions.toString() +
                ", enrolledUsers=" + enrolledUsers.toString() +
                '}';
    }

}

