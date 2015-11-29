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
import com.lucas.alps.viewtype.ShiftFormView;
import com.lucas.alps.viewtype.UserFormView;
import com.lucas.alps.viewtype.WmsUserFormView;
import com.lucas.entity.user.Shift;
import com.lucas.entity.user.WmsUser;

import java.util.Date;


public class WmsUserView implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private WmsUser wmsUser;

    public WmsUserView() {
        this.wmsUser = new WmsUser();
    }

    public WmsUserView(WmsUser wmsUser) {
        this.wmsUser = wmsUser;
    }

    @JsonView({WmsUserFormView.class, UserFormView.class})
    public Long getUserId() {
        return wmsUser.getUserId();
    }

    public void setUserId(Long userId) {
        wmsUser.setUserId(userId);
    }

    @JsonView({WmsUserFormView.class, UserFormView.class})
    public String getHostLogin() {
        return wmsUser.getHostLogin();
    }

    public void setHostLogin(String hostLogin) {
        wmsUser.setHostLogin(hostLogin);
    }

    @JsonView({WmsUserFormView.class, UserFormView.class})
    public String getHostHashedPassword() {
        return wmsUser.getHostHashedPassword();
    }

    public void setHostHashedPassword(String hostHashedPassword) {
        wmsUser.setHostHashedPassword(hostHashedPassword);
    }

    //@JsonView({WmsUserFormView.class, UserFormView.class})
    public String getHostPlainTextPassword() {
        return wmsUser.getHostPlainTextPassword();
    }

    public void setHostPlainTextPassword(String hostPlainTextPassword) {
        wmsUser.setHostPlainTextPassword(hostPlainTextPassword);
    }

}
