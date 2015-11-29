package com.lucas.alps.view;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.lucas.alps.viewtype.*;
import com.lucas.entity.user.PermissionGroup;
import com.lucas.entity.user.User;
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
public class UserPermissionView implements java.io.Serializable {

    private static final long serialVersionUID = 3987853065016392051L;


    private User user;

    public UserPermissionView() {
        this.user = new User();
    }

    public UserPermissionView(User user) {
        this.user = user;
    }

    @JsonView({UserGroupPermissionDetailsView.class})
    @JsonProperty("username")
    public String getUserName() {
        return user.getUserName();
    }

    @JsonView({UserGroupPermissionDetailsView.class})
    @JsonProperty("groups")
    public List<GroupPermissionView> getGroupPermissionView(){
        List<GroupPermissionView> groupPermissionViews=new ArrayList<>();
        for(PermissionGroup permissionGroup: CollectionsUtilService.nullGuard(this.user.getUserPermissionGroupList()) ){
            groupPermissionViews.add(new GroupPermissionView(permissionGroup));
        }
        return groupPermissionViews;
    }
}
