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
import com.lucas.alps.viewtype.SupportedLanguageFormView;
import com.lucas.alps.viewtype.UserFormView;
import com.lucas.entity.user.SupportedLanguage;

import java.util.Date;


public class SupportedLanguageView implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private SupportedLanguage supportLanguage;

    public SupportedLanguageView() {
        this.supportLanguage = new SupportedLanguage();
    }

    public SupportedLanguageView(SupportedLanguage supportLanguage) {
        this.supportLanguage = supportLanguage;
    }


    @JsonView({SupportedLanguageFormView.class, UserFormView.class})
    public String getLanguageCode() {
        if (this.supportLanguage != null) {
            return supportLanguage.getLanguageCode();
        } else {
            return null;
        }
    }

    public void setLanguageCode(String languageCode) {
        supportLanguage.setLanguageCode(languageCode);
    }

    @JsonView({SupportedLanguageFormView.class, UserFormView.class})
    public Boolean getAmdLanguage() {
        if (this.supportLanguage != null) {
            return supportLanguage.getAmdLanguage();
        } else {
            return null;
        }
    }

    public void setAmdLanguage(Boolean amdLanguage) {
        supportLanguage.setAmdLanguage(amdLanguage);
    }

    @JsonView({SupportedLanguageFormView.class, UserFormView.class})
    public Boolean getHhLanguage() {
        if (this.supportLanguage != null) {
            return supportLanguage.getHhLanguage();
        } else {
            return null;
        }
    }

    public void setHhLanguage(Boolean hhLanguage) {
        supportLanguage.setHhLanguage(hhLanguage);
    }

    @JsonView({SupportedLanguageFormView.class, UserFormView.class})
    public Boolean getJ2uLanguage() {
        if (this.supportLanguage != null) {
            return supportLanguage.getJ2uLanguage();
        } else {
            return null;
        }
    }

    public void setJ2uLanguage(Boolean j2uLanguage) {
        supportLanguage.setJ2uLanguage(j2uLanguage);
    }

    @JsonView({SupportedLanguageFormView.class, UserFormView.class})
    public Boolean getU2jLanguage() {
        if (this.supportLanguage != null) {
            return supportLanguage.getU2jLanguage();
        } else {
            return null;
        }
    }

    public void setU2jLanguage(Boolean u2jLanguage) {
        supportLanguage.setU2jLanguage(u2jLanguage);
    }

    @JsonView({SupportedLanguageFormView.class})
    public String getCreatedByUserName() {
        if (this.supportLanguage != null) {
            return supportLanguage.getCreatedByUserName();
        } else {
            return null;
        }
    }

    public void setCreatedByUserName(String createdByUserName) {
        this.supportLanguage.setCreatedByUserName(createdByUserName);
    }

    @JsonView({SupportedLanguageFormView.class})
    public String getUpdatedByUserName() {
        if (this.supportLanguage != null) {
            return supportLanguage.getUpdatedByUserName();
        } else {
            return null;
        }
    }

    public void setUpdatedByUserName(String updatedByUserName) {
        this.supportLanguage.setUpdatedByUserName(updatedByUserName);
    }

    @JsonView({SupportedLanguageFormView.class})
    public Date getCreatedDateTime() {
        if (this.supportLanguage != null) {
            return supportLanguage.getCreatedDateTime();
        } else {
            return null;
        }
    }

    public void setCreatedDateTime(Date createdDateTime) {
        this.supportLanguage.setCreatedDateTime(createdDateTime);
    }

    @JsonView({SupportedLanguageFormView.class})
    public Date getUpdatedDateTime() {
        if (this.supportLanguage != null) {
            return supportLanguage.getUpdatedDateTime();
        } else {
            return null;
        }
    }

    public void setUpdatedDateTime(Date updatedDateTime) {
        this.supportLanguage.setUpdatedDateTime(updatedDateTime);
    }
}
