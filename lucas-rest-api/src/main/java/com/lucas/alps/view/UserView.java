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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lucas.alps.viewtype.AuthenticationView;
import com.lucas.alps.viewtype.FavoriteCanvasesView;
import com.lucas.alps.viewtype.UserDetailsView;
import com.lucas.alps.viewtype.UserFormView;
import com.lucas.alps.viewtype.widget.UserCreationFormWidgetDetailsView;
import com.lucas.entity.support.JsonDateSerializer;
import com.lucas.entity.ui.canvas.Canvas;
import com.lucas.entity.ui.canvas.OpenUserCanvas;
import com.lucas.entity.user.*;
import com.lucas.services.util.CollectionsUtilService;

import javax.validation.constraints.Size;

public class UserView implements java.io.Serializable {

    private static final long serialVersionUID = 3987853065016392051L;


    private User user;

    public UserView() {
        this.user = new User();
    }

    public UserView(User user) {
        this.user = user;
    }

    @JsonView({UserDetailsView.class})
    @JsonProperty("userPermissions")
    public List<String> getPermissionsSet() {
        final List<String> userPermissionList = new ArrayList<String>();
        final Set permissionsSet = user.getPermissionsSet();
        final Iterator<Permission> permissionIterator = permissionsSet.iterator();
        while (permissionIterator.hasNext()) {
            userPermissionList.add(permissionIterator.next().getPermissionName());
        }
        return userPermissionList;
    }

    @JsonView({UserFormView.class})
    @JsonProperty("groups")
    public List<String> getGroupPermissionView(){
                List<String> groupPermissionViews=new ArrayList<>();
        for(PermissionGroup permissionGroup: CollectionsUtilService.nullGuard(this.user.getUserPermissionGroupList()) ){
            groupPermissionViews.add(permissionGroup.getPermissionGroupName());
        }
        return groupPermissionViews;
    }

    public void setGroupPermissionView(List<String> groupPermissionViews) {
        List<PermissionGroup> permissionGroups = new ArrayList<>();
        for (String groupPermissionString : CollectionsUtilService.nullGuard(groupPermissionViews)) {
            permissionGroups.add(new PermissionGroup(groupPermissionString));
        }
        this.user.setUserPermissionGroupList(permissionGroups);
    }

    @JsonView({UserFormView.class, UserDetailsView.class})
    public Long getUserId() {
        return user.getUserId();
    }

    @JsonView({UserDetailsView.class, UserFormView.class})
    public String getFirstName() {
        return user.getFirstName();

    }

    @JsonView({UserFormView.class})
    public String getLastName() {
        return user.getLastName();
    }

    @JsonView({UserDetailsView.class, AuthenticationView.class, UserFormView.class})
    @JsonProperty("username")
    @Size(min = 1)
    public String getUserName() {
        return user.getUserName();
    }

    public String getHashedPassword() {
        return user.getHashedPassword();
    }

    @JsonView({UserFormView.class})
    @Size(min = 6, max = 200)
    public String getEmailAddress() {
        return user.getEmailAddress();
    }

    @JsonIgnore
    public boolean isAuthenticated() {
        return user.isAuthenticated();
    }

    @JsonIgnore
    public boolean isNeedsAuthentication() {
        return user.isNeedsAuthentication();
    }

    //@JsonIgnore
    public String getPlainTextPassword() {
        return user.getPlainTextPassword();
    }

    public void setUserId(Long userId) {
        user.setId(userId);
    }

    public void setFirstName(String firstName) {
        user.setFirstName(firstName);
    }

    public void setLastName(String lastName) {
        user.setLastName(lastName);
    }

    public void setUserName(String userName) {
        user.setUserName(userName);
    }


    public void setPermissionsSet(Set<Permission> permissionsSet) {
        user.setPermissionsSet(permissionsSet);
    }


    public void setPlainTextPassword(String plainTextPassword) {
        user.setPlainTextPassword(plainTextPassword);
    }

    public void setHashedPassword(String hashedPassword) {
        user.setHashedPassword(hashedPassword);
    }

    public void setEmailAddress(String emailAddress) {
        user.setEmailAddress(emailAddress);
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @JsonIgnore
    public String getPassword() {
        return user.getHashedPassword();
    }

    public void setPassword(String password) {
        user.setHashedPassword(password);
    }

    @JsonView({UserFormView.class})
    public String getTitle() {
        return user.getTitle();
    }

    public void setTitle(String title) {
        user.setTitle(title);
    }

    @JsonView({UserFormView.class})
    public String getEmployeeNumber() {
        return user.getEmployeeNumber();
    }

    public void setEmployeeNumber(String employeeNumber) {
        user.setEmployeeNumber(employeeNumber);
    }

    @JsonSerialize(using = JsonDateSerializer.class)
    @JsonView({UserCreationFormWidgetDetailsView.class, UserFormView.class})
    public Date getStartDate() {
        return user.getStartDate();
    }

    public void setStartDate(Date startDate) {
        user.setStartDate(startDate);
    }


    @JsonView({UserFormView.class})
    public Date getBirthDate() {
        return user.getBirthDate();
    }

    public void setBirthDate(Date birthDate) {
        user.setBirthDate(birthDate);
    }

    @JsonView({UserFormView.class})
    public String getMobileNumber() {
        return user.getMobileNumber();
    }

    public void setMobileNumber(String mobileNumber) {
        user.setMobileNumber(mobileNumber);
    }

    @JsonView({UserFormView.class})
    public SupportedLanguageView getJ2uLanguage() {
        return new SupportedLanguageView(this.user.getJ2uLanguage());
    }

    public void setJ2uLanguage(SupportedLanguage j2uLanguage) {
        this.user.setJ2uLanguage(j2uLanguage);
    }

    @JsonView({UserFormView.class})
    public SupportedLanguageView getU2jLanguage() {
        return new SupportedLanguageView(this.user.getU2jLanguage());
    }

    public void setU2jLanguage(SupportedLanguage u2jLanguage) {
        this.user.setU2jLanguage(u2jLanguage);
    }

    @JsonView({UserFormView.class})
    public SupportedLanguageView getHhLanguage() {
        return new SupportedLanguageView(this.user.getHhLanguage());
    }

    public void setHhLanguage(SupportedLanguage hhLanguage) {
        this.user.setHhLanguage(hhLanguage);
    }

    @JsonView({UserFormView.class})
    public SupportedLanguageView getAmdLanguage() {
        return new SupportedLanguageView(this.user.getAmdLanguage());
    }

    public void setAmdLanguage(SupportedLanguage amdLanguage) {
        this.user.setAmdLanguage(amdLanguage);
    }

    @JsonView({UserFormView.class})
    public ShiftView getShift() {
        return new ShiftView(this.user.getShift());
    }

    public void setShift(Shift shift) {
        this.user.setShift(shift);
    }

    //@JsonView({UserFormView.class})
    /*public WmsUser getWmsUser() {
        return user.getWmsUser();
    }

    public void setWmsUser(WmsUser wmsUser) {
        this.user.setWmsUser(wmsUser);
    }
    */


    public void setNeedsAuthentication(boolean booleanValue) {
        // TODO Auto-generated method stub

    }

    @JsonView({UserFormView.class})
    public String getSkill() {
        return user.getSkill();
    }

    public void setSkill(String skill) {
        this.user.setSkill(skill);
    }

    @JsonView({UserFormView.class})
    public Boolean getRetrainVoiceModel() {
        return user.getRetrainVoiceModel();
    }

    public void setRetrainVoiceModel(Boolean retrainVoiceModel) {
        this.user.setRetrainVoiceModel(retrainVoiceModel);
    }

    @JsonView({UserFormView.class})
    public Boolean getSeeHomeCanvasIndicator() {
        return user.getSeeHomeCanvasIndicator();
    }

    public void setSeeHomeCanvasIndicator(Boolean seeHomeCanvasIndicator) {
        this.user.setSeeHomeCanvasIndicator(seeHomeCanvasIndicator);
    }

    @JsonView({UserFormView.class})
    public Boolean getDeletedIndicator() {
        return user.getDeletedIndicator();
    }

    public void setDeletedIndicator(Boolean deletedIndicator) {
        this.user.setDeletedIndicator(deletedIndicator);
    }

    @JsonView({UserFormView.class})
    public String getCreatedByUserName() {
        return user.getCreatedByUserName();
    }

    public void setCreatedByUserName(String createdByUserName) {
        this.user.setCreatedByUserName(createdByUserName);
    }

    @JsonView({UserFormView.class})
    public String getUpdatedByUserName() {
        return user.getUpdatedByUserName();
    }

    public void setUpdatedByUserName(String updatedByUserName) {
        this.user.setUpdatedByUserName(updatedByUserName);
    }

    @JsonView({UserFormView.class})
    public Date getCreatedDateTime() {
        return user.getCreatedDateTime();
    }

    public void setCreatedDateTime(Date createdDateTime) {
        this.user.setCreatedDateTime(createdDateTime);
    }

    @JsonView({UserFormView.class})
    public Date getUpdatedDateTime() {
        return user.getUpdatedDateTime();
    }

    public void setUpdatedDateTime(Date updatedDateTime) {
        this.user.setUpdatedDateTime(updatedDateTime);
    }

	public CanvasView getActiveCanvas() {
		return new CanvasView(this.user.getActiveCanvas());
	}

	public void setActiveCanvas(CanvasView activeCanvas) {
		this.user.setActiveCanvas(activeCanvas.getCanvas());
	}


    @JsonView({UserFormView.class})
    public String getHostLogin() {
        return user.getHostLogin();
    }

    public void setHostLogin(String hostLogin) {
        this.user.setHostLogin(hostLogin);
    }

    @JsonView({UserFormView.class})
    public String getHostPassword() {
        return user.getHostPassword();
    }

    public void setHostPassword(String hostPassword) {
        this.user.setHostPassword(hostPassword);
    }
    
    public List<OpenUserCanvasesView> getOpenUserCanvases() {
    	List<OpenUserCanvasesView> openUserCanvases = new ArrayList<OpenUserCanvasesView>();
    	for(OpenUserCanvas openUserCanvas:CollectionsUtilService.nullGuard(user.getOpenUserCanvases())){
    		openUserCanvases.add(new OpenUserCanvasesView(openUserCanvas));
    	}
		return openUserCanvases;
	}

	public void setOpenUserCanvases(List<OpenUserCanvasesView> openUserCanvases) {
		List<OpenUserCanvas> openUserCanvasesList = new ArrayList<OpenUserCanvas>();
		for(OpenUserCanvasesView openUserCanvasesView:CollectionsUtilService.nullGuard(openUserCanvases)){
			openUserCanvasesList.add(openUserCanvasesView.getOpenUserCanvas());
		}
		this.user.setOpenUserCanvases(openUserCanvasesList);;
	}

    @JsonView({UserFormView.class})
    @JsonProperty("userPreferences")
    public UserPreferencesView getUserPreferences() {
            return new UserPreferencesView(this.user.getUserPreferences());
    }

    public void setUserPreferences(UserPreferences userPreferences) {
        this.user.setUserPreferences(userPreferences);
    }

    @JsonIgnore
    public String getDateFormat() { return user.getUserPreferences().getDateFormat(); }

    public void setDateFormat(String dateFormat) {
        if (user.getUserPreferences() != null) {
            this.user.getUserPreferences().setDateFormat(dateFormat);
        }
    }

    @JsonIgnore
    public String getTimeFormat() { return user.getUserPreferences().getTimeFormat(); }

    public void setTimeFormat(String timeFormat) {
        if (user.getUserPreferences() != null) {
            this.user.getUserPreferences().setTimeFormat(timeFormat);
        }
    }

    @JsonIgnore
    public Long getDataRefreshFrequency() { return user.getUserPreferences().getDataRefreshFrequency(); }

    public void setDataRefreshFrequency(Long dataRefreshFrequency) {
        if (user.getUserPreferences() != null) {
            this.user.getUserPreferences().setDataRefreshFrequency(dataRefreshFrequency);
        }
    }
}
