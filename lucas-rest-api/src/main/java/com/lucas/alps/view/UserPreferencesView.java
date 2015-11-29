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

import com.fasterxml.jackson.annotation.JsonView;
import com.lucas.alps.viewtype.UserFormView;
import com.lucas.alps.viewtype.UserPreferencesFormView;
import com.lucas.entity.user.UserPreferences;


public class UserPreferencesView implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private UserPreferences userPreferences;

    public UserPreferencesView() {
        this.userPreferences = new UserPreferences();
    }

    public UserPreferencesView(UserPreferences userPreferences) {
        this.userPreferences = userPreferences;
    }


    @JsonView({UserPreferencesFormView.class, UserFormView.class})
    public String getDateFormat() {
        if (this.userPreferences != null) {
            return userPreferences.getDateFormat();
        } else {
            return null;
        }
    }

    public void setDateFormat(String dataFormat) {
        userPreferences.setDateFormat(dataFormat);
    }


    @JsonView({UserPreferencesFormView.class, UserFormView.class})
    public String getTimeFormat() {
        if (this.userPreferences != null) {
            return userPreferences.getTimeFormat();
        } else {
            return null;
        }
    }

    public void setTimeFormat(String timeFormat) {
        userPreferences.setTimeFormat(timeFormat);
    }


    @JsonView({UserPreferencesFormView.class, UserFormView.class})
    public Long getDataRefreshFrequency() {
        if (this.userPreferences != null) {
            return userPreferences.getDataRefreshFrequency();
        } else {
            return null;
        }
    }

    public void setDataRefreshFrequency(Long dataRefreshFrequency) {
        userPreferences.setDataRefreshFrequency(dataRefreshFrequency);
    }

}
