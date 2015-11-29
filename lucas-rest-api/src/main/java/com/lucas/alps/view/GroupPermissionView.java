package com.lucas.alps.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.lucas.alps.viewtype.UserGroupPermissionDetailsView;
import com.lucas.entity.user.Permission;
import com.lucas.entity.user.PermissionGroup;
import com.lucas.services.util.CollectionsUtilService;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Adarsh
 * @Product Lucas.
 * Created By: Adarsh
 * Created On Date: 2/5/2015  Time: 12:40 PM
 * This Class provide the implementation for the functionality of..
 */
public class GroupPermissionView implements java.io.Serializable {

    private PermissionGroup permissionGroup;

    public GroupPermissionView() { this.permissionGroup = new PermissionGroup(); }
    public GroupPermissionView(PermissionGroup permissionGroup) {
        this.permissionGroup = permissionGroup;
    }

    @JsonView({UserGroupPermissionDetailsView.class})
    @JsonProperty("groupName")
    public String getGroupName(){
       return this.permissionGroup.getPermissionGroupName();
    }
    public void setGroupName(String groupName){
        this.permissionGroup.setPermissionGroupName(groupName);
    }

    @JsonView({UserGroupPermissionDetailsView.class})
    @JsonProperty("permission")
    public List<String> getPermissionList(){
      final   List<String> permissionList = new ArrayList<String>();
            for(Permission permission:CollectionsUtilService.nullGuard(permissionGroup.getPermissionGroupPermissionList())) {
                    permissionList.add(permission.getPermissionName());
                }
        return permissionList;
    }
}
