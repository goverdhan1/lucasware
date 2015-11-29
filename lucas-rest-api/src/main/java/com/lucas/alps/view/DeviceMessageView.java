/*
 * Lucas Systems Inc 11279 Perry Highway Wexford PA 15090 United States
 * 
 * 
 * The information in this file contains trade secrets and confidential information which is the
 * property of Lucas Systems Inc.
 * 
 * All trademarks, trade names, copyrights and other intellectual property rights created,
 * developed, embodied in or arising in connection with this software shall remain the sole property
 * of Lucas Systems Inc.
 * 
 * Copyright (c) Lucas Systems, 2014 ALL RIGHTS RESERVED
 */
package com.lucas.alps.view;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lucas.alps.viewtype.MobileMessageView;
import com.lucas.entity.device.Building;
import com.lucas.entity.device.DeviceMessageOption;
import com.lucas.entity.support.DeviceDateSerializer;

public class DeviceMessageView implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private DeviceMessageOption deviceMsgOption;

    public DeviceMessageView() {

        this.deviceMsgOption = new DeviceMessageOption();
    }

    public DeviceMessageView(DeviceMessageOption deviceMsgOption) {
        this.deviceMsgOption = deviceMsgOption;
    }

    @JsonView({MobileMessageView.class})
    public boolean getRequirepassword() {
        if (deviceMsgOption != null) {
            return deviceMsgOption.getRequirePassword();
        }
        return false;
    }

    public void setRequirepassword(boolean requirepassword) {
        if (deviceMsgOption != null) {
            this.deviceMsgOption.setRequirePassword(requirepassword);
        }
    }


    @JsonSerialize(using = DeviceDateSerializer.class)
    @JsonView({MobileMessageView.class})
    public Date getCurrentServerUTC() {
        if (deviceMsgOption != null) {
            return deviceMsgOption.getCurrentServerUTC();
        }
        return null;
    }

    
    public void setCurrentServerUTC(Date CurrentServerUTC) {
        if (deviceMsgOption != null) {
            this.deviceMsgOption.setCurrentServerUTC(CurrentServerUTC);
        }
    }

    @JsonView({MobileMessageView.class})
    public String getAppName() {
        if (deviceMsgOption != null) {
            return deviceMsgOption.getAppName();
        }
        return null;
    }

    public void setAppName(String appName) {
        if (deviceMsgOption != null) {
            this.deviceMsgOption.setAppName(appName);
        }
    }

    @JsonView({MobileMessageView.class})
    public String getAppVersion() {
        if (deviceMsgOption != null) {
            return deviceMsgOption.getAppVersion();
        }
        return null;
    }

    public void setAppVersion(String appVersion) {
        if (deviceMsgOption != null) {
            this.deviceMsgOption.setAppVersion(appVersion);
        }
    }

    @JsonView({MobileMessageView.class})
    public String getSpapiMessagingVersion() {
        if (deviceMsgOption != null) {
            return deviceMsgOption.getSpapiMessagingVersion();
        }
        return null;
    }

    public void setSpapiMessagingVersion(String spapiMessagingVersion) {
        if (deviceMsgOption != null) {
            this.deviceMsgOption.setSpapiMessagingVersion(spapiMessagingVersion);
        }
    }

    @JsonView({MobileMessageView.class})
    public String getDefaultDisplayLangCode() {
        if (deviceMsgOption != null) {
            return deviceMsgOption.getDefaultDisplayLangCode();
        }
        return null;
    }

    public void setdefaultDisplayLangCode(String defaultDisplayLangCode) {
        if (deviceMsgOption != null) {
            this.deviceMsgOption.setDefaultDisplayLangCode(defaultDisplayLangCode);
        }
    }

    @JsonView({MobileMessageView.class})
    public String getAdditionalOptions() {
        if (deviceMsgOption != null) {
            return deviceMsgOption.getAdditionalOptions();
        }
        return null;
    }

    public void setAdditionalOptions(String additionalOptions) {
        if (deviceMsgOption != null) {
            this.deviceMsgOption.setAdditionalOptions(additionalOptions);
        }
    }

    public Building getBuilding() {
        if (deviceMsgOption != null) {
            return deviceMsgOption.getBuilding();
        }
        return null;
    }

    public void setBuilding(Building building) {
        if (deviceMsgOption != null) {
            this.deviceMsgOption.setBuilding(building);
        }
    }


}
